package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.widget.helper.LaserPoint;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/10 0010
 * @Descriptiong : framelayout -- 中执行所有操作 ---
 */

public class GpsLayout extends GestureFrameLayout {

    private static final String TAG = "GpsLayout";
    
    private GestureController controller;
    private Bitmap robotView;
    private Bitmap gpsMap;
    private Paint mPaint;

    private boolean isRobotShow = false;
    private float robotX;
    private float robotY;

    private boolean isLaserShow = false;
    private List<LaserPoint> laserPoints;

    /* 宽高 */
    private int height;
    private int width;

    public GpsLayout(Context context) {
        this(context,null);
    }

    public GpsLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GpsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        controller = this.getController();
        controller.addOnStateChangeListener(new GestureController.OnStateChangeListener() {
            @Override
            public void onStateChanged(State state) {
                Matrix m = new Matrix();
                state.get(m);
                Log.d(TAG, "onStateChanged: " + m);
            }

            @Override
            public void onStateReset(State oldState, State newState) {
                Matrix old = new Matrix();
                oldState.get(old);
                Log.d(TAG, "onStateReset: old " + old);

                Matrix newly = new Matrix();
                newState.get(newly);
                Log.d(TAG, "onStateReset: new " + newly);
            }
        });

        setWillNotDraw(false);


        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inScaled = false;
        gpsMap    = BitmapFactory.decodeResource(getResources(),R.mipmap.testmap,op).copy(Bitmap.Config.ARGB_8888, true);
        robotView = BitmapFactory.decodeResource(getResources(),R.mipmap.sweeprobot);
        laserPoints = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(false); // 关闭抗锯齿 节省性能
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        // 以fitcenter的模式画Bitmap


        Canvas canvasBitmap = new Canvas(gpsMap);
        canvasBitmap.drawPoint(1120,1286,mPaint);


        Matrix matrix = caculateMatrix(gpsMap);
        canvas.drawBitmap(gpsMap,matrix,mPaint);

        // 绘制机器人位置   (drawable)
        if (isRobotShow)
            canvas.drawBitmap(robotView,robotX,robotY,mPaint);

        if (isLaserShow)
            Flowable
                    .fromIterable(laserPoints)
            .subscribe(new Consumer<LaserPoint>() {
                @Override
                public void accept(@NonNull LaserPoint laserPoint) throws Exception {
                    canvas.drawPoint(laserPoint.getX(),laserPoint.getY(),mPaint);
                }
            });
    }

    public void setRobotView(Bitmap robotView) {
        this.robotView = robotView;
    }

    public void setRobotPosition(float robotX,float robotY) {
        isRobotShow = true;
        this.robotX = robotX;
        this.robotY = robotY;
        invalidate();
    }

    public void setLaserPoints(List<LaserPoint> laserPoints) {
        isLaserShow = true;
        this.laserPoints = laserPoints;
        invalidate();
    }

    /**
     * 计算bitmap在以fitCenter模式时图片的变换矩阵
     * @param bitmap
     * @return
     */
    private Matrix caculateMatrix(Bitmap bitmap){

        Matrix matrix = new Matrix();

        int w  = bitmap.getWidth();
        int h  = bitmap.getHeight();

        Log.d(TAG, "caculateMatrix: " + w + "h:" +h);

        width = getWidth();
        height = getHeight();

        float widthRatio = width / (float)w;
        float heightRatio = height / (float)h;

        if (h*widthRatio <= heightRatio){
            matrix.postScale(widthRatio,widthRatio);
            matrix.postTranslate((getWidth()-w*widthRatio)/2,0);
            return matrix;
        }

        if (w*heightRatio <= width){
            matrix.postScale(heightRatio,heightRatio);
            matrix.postTranslate((getWidth()-w*heightRatio)/2,0);
            return matrix;
        }

        return null;
    }

}
