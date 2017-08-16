package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.utils.CommunicationUtil;
import com.gps.sweeprobot.utils.DegreeManager;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.RGBUtil;
import com.gps.sweeprobot.utils.ToastManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import retrofit2.http.HEAD;

import static com.gps.sweeprobot.R.mipmap.point;

/**
 * Created by admin on 2017/6/16.
 */

public class GpsImageView extends FrameLayout {

    private static final String TAG = "GpsImageView";
    private PinchImageView mapView;

    //存放标记点集合
    private List<CoordinateView> pointsList;
    //存放虚拟墙view集合
    private List<VirtualObstacleView> obstacleViews;

    VirtualObstacleView obstacleView;
    private int locationViewSize = 15;

    private FrameLayout.LayoutParams commonParams;

    private OnObstacleViewClick obstacleListener;

    private ImageView robotPoint;

    /* 镭射点集合 */
    private List<CoordinateView> laserPoses;


    public PinchImageView getMapView() {
        return mapView;
    }

    public GpsImageView(Context context) {
        super(context);
        initView(context);
        Log.i(TAG, "GpsImageView: 1");
    }

    public GpsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        Log.i(TAG, "GpsImageView: 2");
    }

    public GpsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        Log.i(TAG, "GpsImageView: 3");
    }

    private void initView(Context context) {

        mapView = new PinchImageView(context);
        pointsList = new ArrayList<>();
        obstacleViews = new ArrayList<>();
        laserPoses = new ArrayList<>();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        commonParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        mapView.setLayoutParams(layoutParams);
        mapView.setImageResource(R.mipmap.fb_map);
        mapView.setLayoutParams(layoutParams);
        addView(mapView);

        this.mapView.addOuterMatrixChangedListener(new PinchImageView.OuterMatrixChangedListener() {
            @Override
            public void onOuterMatrixChanged(PinchImageView pinchImageView) {
                changePoint(pinchImageView.getCurrentImageMatrix());
            }
        });

        obstacleView = new VirtualObstacleView(getContext());
        obstacleView.setLayoutParams(commonParams);
        addView(obstacleView);

    }

    /**
     * 矩阵变化监听
     *
     * @param matrix
     */
    private void changePoint(final Matrix matrix) {

        Flowable.fromIterable(pointsList)
                .subscribe(new Consumer<CoordinateView>() {
                    @Override
                    public void accept(@NonNull CoordinateView coordinateView) throws Exception {

                        PointF pointF = DegreeManager.changeAbsolutePoint(coordinateView.imageViewX, coordinateView.imageViewY, matrix);
                        coordinateView.setPositionViewX(pointF.x);
                        coordinateView.setPositionViewY(pointF.y);
//                        LogManager.i("change point pointx========="+pointF.x+"\npointy============"+pointF.y);
                    }
                });

        Flowable.fromIterable(obstacleViews)
                .subscribe(new Consumer<VirtualObstacleView>() {
                    @Override
                    public void accept(@NonNull VirtualObstacleView virtualObstacleView) throws Exception {
                        virtualObstacleView.setmMatrix(matrix);
                    }
                });
        obstacleView.setmMatrix(matrix);

        Flowable.fromIterable(laserPoses)
                .subscribe(new Consumer<CoordinateView>() {
                    @Override
                    public void accept(@NonNull CoordinateView coor) throws Exception {
                        PointF pointF = DegreeManager.changeAbsolutePoint(coor.imageViewX, coor.imageViewY, matrix);
                        coor.setPositionViewX(pointF.x);
                        coor.setPositionViewY(pointF.y);
                    }
                });

        if (robotPoint != null) {
            PointF pointF = DegreeManager.changeAbsolutePoint(robotPoint.getX(), robotPoint.getY(), matrix);
            // scrollTo 看不见机器人的位置
//            robotPoint.scrollTo((int) pointF.x,(int) pointF.y);
            robotPoint.setX(pointF.x);
            robotPoint.setY(pointF.y);
            robotPoint.postInvalidate();
        }
    }

    /**
     * 在添加标记点之前先判断像素点的颜色值RGB565，白色的点才能添加标记点
     *
     * @param screenX
     * @param screenY
     * @param pointName
     */
    public PointBean addPointWrapper(float screenX, float screenY, String pointName) {

        PointF pointF = DegreeManager.changeRelativePoint(screenX, screenY, mapView.getCurrentImageMatrix());
        int redValue = RGBUtil.getRedValue(mapView, pointF);
        if (redValue == 254) {

            LogManager.i("pointx=======" + pointF.x + "\npointy=========" + pointF.y);
            addPoint(pointF.x, pointF.y, pointName);
            return addPointData(pointF, pointName);

        } else {
            ToastManager.showShort(MainApplication.getContext(), R.string.un_effective);
        }
        return null;
    }

    /**
     * 添加标记点
     *
     * @param locationX
     * @param locationY
     * @param positionName
     */
    public void addPoint(float locationX, float locationY, String positionName) {
        pointsList.add(addMarkPoint(locationX, locationY, positionName, null));
        LogManager.i(mapView.getCurrentImageMatrix().toString());
    }


    private PointBean addPointData(PointF pointF, String pointName) {

        //将标记点数据存进数据库
        PointBean pointBean = new PointBean();
        pointBean.setMapName("testMap");
        pointBean.setX(pointF.x);
        pointBean.setY(pointF.y);
        pointBean.setPointName(pointName);
        pointBean.save();

        //通知服务器
        CommunicationUtil.sendPointData2Server(pointBean);
        return pointBean;
    }



    /*
     * 更新标记点的名称
     * @param newName 名称
     * @param index   对应标记点view的index
     */
    public void updateName(String newName, int index) {

        pointsList.get(index).setPositionName(newName);
        pointsList.get(index).postInvalidate();
    }

    /**
     * 删除所有标记点
     */
    public void removeAllView() {
        Iterator<CoordinateView> iterator = pointsList.iterator();
        while (iterator.hasNext()) {
            removeView(iterator.next());
            DataSupport.deleteAll(PointBean.class);
        }
        pointsList.clear();
        postInvalidate();
    }

    /**
     * 删除单个标记点
     *
     * @param name
     */
    public void removePoint(String name, int index) {

        if (pointsList.get(index).getPositionName().equals(name)) {

            removeView(pointsList.get(index));
            pointsList.remove(pointsList.get(index));
            postInvalidate();
        }
    }

    public void scanAddPoint(String name, int i) {
    }

    public void setImageView(Bitmap bitmap) {
        mapView.setImageBitmap(bitmap);
    }

    public void setImageView(Drawable drawable) {
        mapView.setImageDrawable(drawable);
    }

    public void setLocation(float locationX, float locationY, double d) {
    }

    /**
     * 设置虚拟墙的多边形顶点
     *
     * @param pointF
     */
    public void setObstacleRect(PointF pointF) {

        obstacleView.addPoint(pointF);
    }

    /**
     * 设置虚拟墙的名字
     *
     * @param name
     */
    public VirtualObstacleBean setObstacleName(String name) {

        obstacleView.setName(name);
        return obstacleView.saveObstacleBean();
    }

    /**
     * 添加虚拟墙控件并显示
     *
     * @param pointFs
     */
    public void addObstacleView(List<MyPointF> pointFs, String name) {

        VirtualObstacleView obstacleView = new VirtualObstacleView(getContext());
        obstacleView.setLayoutParams(commonParams);
        obstacleView.setmyPointFs(pointFs);
        obstacleView.setName(name);
        addView(obstacleView);
        obstacleViews.add(obstacleView);
    }

    public void removeObstacleView(String name, int position) {
        removeView(obstacleViews.get(position));
        obstacleViews.remove(position);
        postInvalidate();
    }

    public interface OnObstacleViewClick {
        void obstacleViewOnClick();
    }

    /**
     * 添加标记点
     *
     * @param locationX
     * @param locationY
     * @param positionName
     */
    private CoordinateView addMarkPoint(float locationX, float locationY, String positionName, Bitmap mArrow) {

        CoordinateView coordinateView = new CoordinateView(MainApplication.getContext(), 0);
        coordinateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        coordinateView.setPositionName(positionName);
        coordinateView.setShowPointName(true);
        //求出标记点相对于图片的坐标
        Log.d(TAG, "addPoint: " + System.currentTimeMillis());
        Log.d(TAG, "addPoint: " + mapView.getCurrentImageMatrix());
        Log.d(TAG, "addPoint: locationX" + locationX + "locationY:" + locationY);
        coordinateView.setLayoutParams(commonParams);
        coordinateView.setPositionName(positionName);
        coordinateView.setShowPointName(true);

        if (mArrow != null) {
            coordinateView.setShowArrow(true);
            coordinateView.setmArrow(mArrow);
        }

        //求出标记点相对于图片的坐标
        PointF pointF = DegreeManager.changeAbsolutePoint(locationX, locationY, this.mapView.getCurrentImageMatrix());
        coordinateView.setLocationX(pointF.x);
        coordinateView.setLocationY(pointF.y);
//        coordinateView.imageViewX = locationX;
//        coordinateView.imageViewY = locationY;
        addView(coordinateView);
        return coordinateView;
    }
}
