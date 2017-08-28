package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.model.mapmanager.contract.MapEditContract;
import com.gps.sweeprobot.utils.DegreeManager;
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

/**
 * Created by admin on 2017/6/16.
 */

public class GpsImageView extends FrameLayout {

    private static final String TAG = "GpsImageView";
    private PinchImageView mapView;

    //存放标记点集合
    private List<CoordinateView> coordList;
    //存放虚拟墙view集合
    private List<VirtualObstacleView> obstacleList;

    VirtualObstacleView obstacleView;
    private int locationViewSize = 15;

    private FrameLayout.LayoutParams commonParams;

    //保存标记点的监听对象
    private SavePointDataListener onSaveListener;


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

    public void setOnSaveListener(SavePointDataListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    private void initView(Context context) {

        mapView = new PinchImageView(context);
        coordList = new ArrayList<>();
        obstacleList = new ArrayList<>();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        commonParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        mapView.setLayoutParams(layoutParams);
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
        obstacleView.setAdd(true);

    }

    /**
     * 矩阵变化监听
     * @param matrix
     */
    private void changePoint(final Matrix matrix) {
        LogManager.i("change point");
        Flowable.fromIterable(coordList)
                .subscribe(new Consumer<CoordinateView>() {
                    @Override
                    public void accept(@NonNull CoordinateView coordinateView) throws Exception {

                        PointF pointF = DegreeManager.changeAbsolutePoint(coordinateView.imageViewX, coordinateView.imageViewY, matrix);
                        coordinateView.setPositionViewX(pointF.x);
                        coordinateView.setPositionViewY(pointF.y);
                        
//                        LogManager.i("change point pointx========="+pointF.x+"\npointy============"+pointF.y);
                    }
                });

        Flowable.fromIterable(obstacleList)
                .subscribe(new Consumer<VirtualObstacleView>() {
                    @Override
                    public void accept(@NonNull VirtualObstacleView virtualObstacleView) throws Exception {
                        virtualObstacleView.setmMatrix(matrix);
//                        virtualObstacleView.setSaveObstacleListener(onSaveObstacleListener);
                    }
                });
        obstacleView.setmMatrix(matrix);

    }

    /**
     * 在添加标记点之前先判断像素点的颜色值RGB565，白色的点才能添加标记点
     *
     * @param screenX
     * @param screenY
     * @param pointName
     */
    public void addPointWrapper(float screenX, float screenY, String pointName) {

        PointF pointF = DegreeManager.changeRelativePoint(screenX, screenY, mapView.getCurrentImageMatrix());

        int redValue = RGBUtil.getRedValue(mapView, pointF);
        if (redValue == 254) {

            LogManager.i("pointx=======" + pointF.x + "\npointy=========" + pointF.y);
            addPoint(pointF.x, pointF.y, pointName);
            savePointData(pointF, pointName);

        } else {
            ToastManager.showShort(MainApplication.getContext(), R.string.un_effective);
        }
    }

    /**
     * 添加标记点
     *
     * @param locationX
     * @param locationY
     * @param positionName
     */
    public void addPoint(float locationX, float locationY, String positionName) {

        CoordinateView coordView = new CoordinateView(MainApplication.getContext(), R.mipmap.coordinate);
        coordView.setLayoutParams(commonParams);
        coordView.setPositionName(positionName);
        coordView.setShowPointName(true);
//        coordView.setShowArrow(true);
//        coordView.setAngle(30);

        //求出标记点相对于图片的坐标
        PointF pointF = DegreeManager.changeAbsolutePoint(locationX, locationY, this.mapView.getCurrentImageMatrix());
        coordView.setLocationX(pointF.x);
        coordView.setLocationY(pointF.y);
        coordView.imageViewX = locationX;
        coordView.imageViewY = locationY;
        coordList.add(coordView);
        addView(coordView);


        /*LogManager.i(mapView.getCurrentImageMatrix().toString());
        LogManager.i("add point x========="+pointF.x+"\ny============="+pointF.y);*/

    }

    /**
     * @param pointF
     * @param pointName
     * @return
     */
    private void savePointData(PointF pointF, String pointName) {

        onSaveListener.onSavePoint(pointF,pointName);
    }

    public interface SavePointDataListener{

        void onSavePoint(PointF pointF,String name);
    }

    /**
     * 更新标记点的名称
     * @param newName 名称
     * @param index   对应标记点view的index
     */
    public void updateName(String newName, int index,int type) {

        if (type == MapEditContract.TYPE_POINT){

            coordList.get(index).setPositionName(newName);
            coordList.get(index).postInvalidate();
        }

        if (type == MapEditContract.TYPE_OBSTACLE ){

            obstacleList.get(index).setName(newName);
            obstacleList.get(index).postInvalidate();
        }
    }

    /**
     * 删除所有标记点
     */
    public void removeAllView() {

        Iterator<CoordinateView> iterator = coordList.iterator();
        while (iterator.hasNext()) {
            removeView(iterator.next());
            DataSupport.deleteAll(PointBean.class);
        }
        coordList.clear();

        postInvalidate();
    }

    /**
     * 删除单个标记点
     *
     * @param name
     */
    public void removePoint(String name, int index) {

        if (coordList.get(index).getPositionName().equals(name)) {

            removeView(coordList.get(index));
            coordList.remove(coordList.get(index));
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

        if (obstacleView.isAdd() == false){

            addView(obstacleView);
        }
        obstacleView.addPoint(pointF);

    }

    /**
     * 设置虚拟墙的名字,新建了一个虚拟墙
     *
     * @param name
     */
    public void setObstacleName(String name, VirtualObstacleView.SaveObstacleListener listener) {

        VirtualObstacleView wall = new VirtualObstacleView(getContext(), null);
        wall.setLayoutParams(commonParams);
        wall.setName(name);
        wall.setFinishedList(obstacleView.getDrawingList());
        wall.saveObstacleBean(listener);
        addView(wall);
        obstacleList.add(wall);

        removeView(obstacleView);
        obstacleView.setAdd(false);
    }

    /**
     * 添加虚拟墙控件并显示
     *
     * @param pointFs
     */
    public void addObstacleView(List<MyPointF> pointFs, String name) {

        VirtualObstacleView obstacleView = new VirtualObstacleView(getContext());
        obstacleView.setLayoutParams(commonParams);
        obstacleView.setFinishedList(pointFs);
        obstacleView.setName(name);
        addView(obstacleView);
        obstacleList.add(obstacleView);
    }

    public void removeObstacleView(String name, int position) {
        removeView(obstacleList.get(position));
        obstacleList.remove(position);
        postInvalidate();
    }

    public interface OnSetVertice{

        void setVertice(PointF pointF);
    }

}
