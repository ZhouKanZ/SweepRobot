package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.utils.DegreeManager;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.RGBUtil;
import com.gps.sweeprobot.utils.ToastManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.gps.sweeprobot.R.mipmap.point;

/**
 * Created by admin on 2017/6/16.
 */

public class GpsImageView extends FrameLayout {

    private static final String TAG = "GpsImageView";
    private PinchImageView mapView;
    private boolean isAddPoint;
    //存放标记点集合
    private List<CoordinateView> pointsList;

    private CoordinateView robotPoint;



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

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(layoutParams);
        addView(mapView);


        this.mapView.addOuterMatrixChangedListener(new PinchImageView.OuterMatrixChangedListener() {
            @Override
            public void onOuterMatrixChanged(PinchImageView pinchImageView) {
                changePoint(pinchImageView.getCurrentImageMatrix());
            }
        });

        mapView.setAddPointListener(new PinchImageView.AddPointListener() {
            @Override
            public void addPoint(PinchImageView pinchImageView, float relativeX, float relativeY) {
                Log.i(TAG, "addPoint: x========" + relativeX + "   y========" + relativeY);
            }
        });
    }

    private void changePoint(Matrix matrix) {

        CoordinateView coordinateView;
        matrix = this.mapView.getCurrentImageMatrix();
        if (pointsList != null) {
            Iterator<CoordinateView> iterator = pointsList.iterator();
            while (iterator.hasNext()) {
                coordinateView = iterator.next();
                PointF pointF = DegreeManager.changeAbslutePoint(coordinateView.imageViewX, coordinateView.imageViewY, matrix);
                coordinateView.setPositionViewX(pointF.x);
                coordinateView.setPositionViewY(pointF.y);
            }
        }
    }

    /**
     * 添加标记点
     *
     * @param locationX
     * @param locationY
     * @param positionName
     */
    public void addPoint(final float locationX, final float locationY, final String positionName) {

        /**
         *  设置一定的延时保证view在加载完成后 能够成功获取到Matrix再去添加点
         */
        mapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                CoordinateView coordinateView = new CoordinateView(MainApplication.getContext(), point);
                coordinateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                coordinateView.setPositionName(positionName);
                coordinateView.setShowPointName(true);
                //求出标记点相对于图片的坐标
                Log.d(TAG, "addPoint: " + System.currentTimeMillis());
                Log.d(TAG, "addPoint: " + mapView.getCurrentImageMatrix());
                PointF pointF = DegreeManager.changeAbslutePoint(locationX, locationY, mapView.getCurrentImageMatrix());
                coordinateView.setLocationX(pointF.x);
                coordinateView.setLocationY(pointF.y);
                coordinateView.imageViewX = locationX;
                coordinateView.imageViewY = locationY;
                pointsList.add(coordinateView);
                addView(coordinateView);
            }
        },100);

    }


    /**
     *  添加机器人点
     * @param locationX
     * @param locationY
     * @param positionName
     * @param rosBitmap
     */
    public void addRobotPoint(float locationX, float locationY, String positionName, Bitmap rosBitmap) {

        if (null == robotPoint) {
            robotPoint = new CoordinateView(MainApplication.getContext(), point);
        }

        robotPoint.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        robotPoint.setPositionName(positionName);
        robotPoint.setShowPointName(true);
        //求出标记点相对于图片的坐标
        PointF pointF = DegreeManager.changeAbslutePoint(locationX, locationY, this.mapView.getCurrentImageMatrix());
        robotPoint.setLocationX(pointF.x);
        robotPoint.setLocationY(pointF.y);
        robotPoint.imageViewX = locationX;
        robotPoint.imageViewY = locationY;
        robotPoint.setmArrow(rosBitmap);
        addView(robotPoint);
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

            addPoint(pointF.x, pointF.y, pointName);
            //将标记点数据存进数据库
            PointBean pointBean = new PointBean();
            pointBean.setMapName("testMap");
            pointBean.setX(pointF.x);
            pointBean.setY(pointF.y);
            pointBean.setPointName(pointName);
            pointBean.save();
        } else {
            ToastManager.showShort(MainApplication.getContext(), R.string.un_effective);
        }
        LogManager.i("pointx=======" + pointF.x + "\npointy=========" + pointF.y);

    }

    public void removeAllView() {
        Iterator<CoordinateView> iterator = pointsList.iterator();
        while (iterator.hasNext()) {
            removeView(iterator.next());
        }
        pointsList.clear();
    }

    public void removePoint(String name) {

        for (CoordinateView coordinateView : pointsList) {
            if (coordinateView.getPositionName().equals(name)) {
                pointsList.remove(coordinateView);
                removeView(coordinateView);
//                postInvalidate();
                break;
            }
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

}
