package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.utils.DegreeManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/29 0029
 * @Descriptiong :
 * 思路 ：
 * aim : 外层图层不可矩阵变换，内存图层继承 gestureImageView 可矩阵变化
 * 在当前图层中绘制出激光点和机器人位置，作为参考，移动内存也就是gestureView来拟合外层图层
 *
 * 具体细节 : imageView
 *
 * 得到内部图层的imageView --- > 对应的bitmap 并创建一个类似的透明bitmap --- > 绘制激光点等等组件
 *
 */
public class GpsView extends GestureImageView
{

    private Paint mPaint;
    private Bitmap robot;
    private List<LaserPose.DataBean> laserPoints;
    private Matrix mMatrix;
    private Matrix finalMatrix;

    /* 宽高 */
    private int height;
    private int width;
    private float robotX;
    private float robotY;
    private State state = State.FIT;


    private enum State {
        FIT,          // 正在拟合的状态
        NORMAL       // 拟合完成
    }

    public GpsView(Context context) {
        this(context,null);
    }

    public GpsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GpsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2); // px

        robot = BitmapFactory.decodeResource(getResources(), R.mipmap.sweeprobot);

        laserPoints = new ArrayList<>();

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (state == State.NORMAL){
            finalMatrix = getImageMatrix();
        }
        // 1.drawRobotBitmap
        if (checkBitmapIsValid(robot) && robotX * robotY != 0 && finalMatrix != null) {
            PointF pointF = DegreeManager.changeAbsolutePoint(robotX, robotY, finalMatrix);
            canvas.drawBitmap(robot, pointF.x, pointF.y, mPaint);
        }
        // 2.drawLaserPoint
        if (laserPoints != null && laserPoints.size() > 0) {
            Flowable
                    .fromIterable(laserPoints)
                    .subscribe(new Consumer<LaserPose.DataBean>() {
                        @Override
                        public void accept(@NonNull LaserPose.DataBean laserPoint) throws Exception {
                            PointF pointF = DegreeManager.changeAbsolutePoint((float) laserPoint.getX(), (float) laserPoint.getY(), finalMatrix);
                            canvas.drawPoint(pointF.x, pointF.y, mPaint);
                        }
                    });
        }
    }

    /**
     * 检查bitmap是否有效
     *
     * @param bitmap
     * @return
     */
    private boolean checkBitmapIsValid(Bitmap bitmap) {
        if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
            return true;
        }
        return false;
    }

    private Matrix caculateMatrix(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        width = getWidth();
        height = getHeight();
        float widthRatio = width / (float) w;
        float heightRatio = height / (float) h;
        /**
         *  宽度刚好铺满的情况
         */
        if (h * widthRatio <= height) {
            matrix.postScale(widthRatio, widthRatio);
            matrix.postTranslate(0, (getHeight() - h * widthRatio) / 2);
            return matrix;
        }
        /**
         *  高度刚好铺满的情况
         */
        if (w * heightRatio <= width) {
            matrix.postScale(heightRatio, heightRatio);
            matrix.postTranslate((getWidth() - w * heightRatio) / 2, 0);
            return matrix;
        }
        return null;
    }

    public void setRobot(Bitmap robot) {
        this.robot = robot;
        postInvalidate();
    }
    public void setLaserPoints(List<LaserPose.DataBean> laserPoints) {
        if (laserPoints == null || laserPoints.size() <= 0)
            return;
        this.laserPoints.clear();
        this.laserPoints.addAll(laserPoints);
        postInvalidate();
    }
    public void setRobotPosition(double x, double y){
        this.robotX = (float) x;
        this.robotY = (float) y;
        postInvalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mMatrix = getImageMatrix();
        finalMatrix = new Matrix(mMatrix);
        Log.d("tag", "setImageBitmap: " + mMatrix);
    }

    /**
     *  完成拟合 返回图片的 Matrix
     */
    public Matrix completeFit(){
        state = State.NORMAL;
        return getImageMatrix();
    }
}

