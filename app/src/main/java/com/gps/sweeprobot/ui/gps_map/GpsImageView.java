package com.gps.sweeprobot.ui.gps_map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.utils.DegreeManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2017/6/16.
 */

public class GpsImageView extends FrameLayout {

    private static final String TAG = "GpsImageView";
    private PinchImageView mapView;
    private boolean isAddPoint;
    //存放标记点集合
    private List<CoordinateView> pointsList;

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

    private void initView(Context context){
        mapView=new PinchImageView(context);
        pointsList=new ArrayList<>();

        LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(layoutParams);

        addView(mapView);

    /*    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_map);
        LogManager.i(TAG,bitmap.getWidth()+"");
        LogManager.i(TAG,bitmap.getHeight()+"");*/

 /*       int width = mapView.getDrawable().getIntrinsicWidth();
        int hei = mapView.getDrawable().getIntrinsicHeight();
        Log.i(TAG, "initView: width========="+width+"     height============"+hei);*/

        this.mapView.addOuterMatrixChangedListener(new PinchImageView.OuterMatrixChangedListener() {
            @Override
            public void onOuterMatrixChanged(PinchImageView pinchImageView) {
                changePoint(pinchImageView.getCurrentImageMatrix());
            }
        });

        mapView.setAddPointListener(new PinchImageView.AddPointListener() {
            @Override
            public void addPoint(PinchImageView pinchImageView, float relativeX, float relativeY) {
                GpsImageView.this.addPoint(relativeX,relativeY,"Test");
                Log.i(TAG, "addPoint: x========"+relativeX+"   y========"+relativeY);            }
        });
    }

    private void changePoint(Matrix matrix){

        CoordinateView coordinateView=null;
        matrix=this.mapView.getCurrentImageMatrix();
        if (pointsList!=null){
            Iterator<CoordinateView> iterator = pointsList.iterator();
            while (iterator.hasNext()){
                coordinateView = iterator.next();
                PointF pointF = DegreeManager.changeAbslutePoint(coordinateView.imageViewX, coordinateView.imageViewY, matrix);
                coordinateView.setPositionViewX(pointF.x);
                coordinateView.setPositionViewY(pointF.y);
            }
        }
    }


    public void addPoint(float locationX,float locationY,String positionName){

        CoordinateView coordinateView=new CoordinateView(MainApplication.getContext(), R.mipmap.ic_launcher);
        coordinateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        coordinateView.setPositionName(positionName);
        coordinateView.setShowPointName(true);
        //求出标记点相对于图片的坐标
        PointF pointF = DegreeManager.changeAbslutePoint(locationX, locationY, this.mapView.getCurrentImageMatrix());
        coordinateView.setLocationX(pointF.x);
        coordinateView.setLocationY(pointF.y);
        coordinateView.imageViewX=locationX;
        coordinateView.imageViewY=locationY;
        pointsList.add(coordinateView);
        addView(coordinateView);

    }

    public void removeAllView(){
        Iterator<CoordinateView> iterator = pointsList.iterator();
        while (iterator.hasNext()){
            removeView(iterator.next());
        }
        pointsList.clear();
    }

    public void scanAddPoint(String name,int i){


    }

    public void setImageView(Bitmap bitmap/*,boolean b*/){

        mapView.setImageBitmap(bitmap);
    }

    public void setLocation(float locationX,float locationY,double d){

    }

}
