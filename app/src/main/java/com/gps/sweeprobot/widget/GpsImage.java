package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.serializer.BeanContext;
import com.gps.ros.response.LaserPose;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.utils.DegreeManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/11 0011
 * @Descriptiong : xxx
 */

public class GpsImage extends View {

    private static final String TAG = "GpsImage";

    /* 雷射点数据 */
    private List<LaserPose.DataBean> laserPoints;
    /* 标记点 */
    private List<PointBean> pointBeanList;
    /* 选中的点 */
    private List<PointBean> selectedPointList;
    private Bitmap robot;
    private Bitmap map;
    private
    @IdRes
    int laserPointColor;
    private int laserRadius;

    private Paint mPaint;
    private Paint pathPaint;
    private Path p;

    /* 宽高 */
    private int height;
    private int width;

    /* 是否显示机器人 */
    private boolean isRobotShow = false;
    /* 是否显示雷射点 */
    private boolean isLaserShow = false;
    /* 是否显示导航点 */
    private boolean isShowPointBean = false;
    /* 是否显示path */
    private boolean isShowSelectedPath = false;

    private float robotX;
    private float robotY;

    private Canvas canvasBitmap;
    private Matrix matrix;


    public GpsImage(Context context) {
        this(context, null);
    }

    public GpsImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GpsImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         *  设置inscaled =  false, 时bitmap的pix不会产生改变
         */
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inScaled = false;
        map = BitmapFactory.decodeResource(getResources(), R.mipmap.testmap, op);

        canvasBitmap = new Canvas();

        robot = BitmapFactory.decodeResource(getResources(), R.mipmap.sweeprobot);
        laserPoints = new ArrayList<>();
        pointBeanList = new ArrayList<>();
        selectedPointList = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 关闭抗锯齿 节省性能
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);

        p = new Path();
        pathPaint = new Paint();
        pathPaint.setAntiAlias(true); // 关闭抗锯齿 节省性能
        pathPaint.setColor(getResources().getColor(R.color.colorAccent));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(1);

    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        if (null != map) {
            matrix = caculateMatrix(map);
            canvas.drawBitmap(map, matrix, mPaint);
        }

        if (isRobotShow) {
            PointF pointF = DegreeManager.changeAbsolutePoint(robotX, robotY, matrix);
            canvas.drawBitmap(robot, pointF.x, pointF.y, mPaint);
        }

        /* 激光点 */
        if (isLaserShow) {
            Flowable
                    .fromIterable(laserPoints)
                    .subscribe(new Consumer<LaserPose.DataBean>() {
                        @Override
                        public void accept(@NonNull LaserPose.DataBean laserPoint) throws Exception {
                            PointF pointF = DegreeManager.changeAbsolutePoint((float) laserPoint.getX(), (float) laserPoint.getY(), matrix);
                            canvas.drawPoint(pointF.x, pointF.y, mPaint);
                        }
                    });
        }

        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        /* 点没有被绘制上去 */
        if (isShowPointBean){
            Flowable
                    .fromIterable(pointBeanList)
                    .subscribe(new Consumer<PointBean>() {
                        @Override
                        public void accept(@NonNull PointBean pointBean) throws Exception {
                            PointF pointF = DegreeManager.changeAbsolutePoint(pointBean.getX(), pointBean.getY(), matrix);
                            canvas.drawPoint(pointF.x, pointF.y, mPaint);
                            String name = pointBean.getPointName();
                            canvas.drawText(name,pointF.x,pointF.y,mPaint);
                        }
                    });
        }

        if (isShowSelectedPath){

            if (null != selectedPointList && selectedPointList.size() >0){

                for (int i = 0; i < selectedPointList.size(); i++) {
                    PointBean pointBean  = selectedPointList.get(i);
                    PointF pointF = DegreeManager.changeAbsolutePoint(pointBean.getX(), pointBean.getY(), matrix);
                    if (i == 0){
                        p.moveTo(pointF.x,pointF.y);
                    }else {
                        p.lineTo(pointF.x,pointF.y);
                    }
                }
                canvas.drawPath(p,pathPaint);
                p.reset();
            }

        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }


    public void setRobot(Bitmap robot) {
        this.robot = robot;
        invalidate();
    }

    public void setMap(Bitmap map) {
        this.map = map;
        invalidate();
    }

    public void setLaserPointColor(int laserPointColor) {
        this.laserPointColor = laserPointColor;
    }

    public void setLaserPoints(List<LaserPose.DataBean> laserPoints) {
        isLaserShow = true;
        this.laserPoints.clear();
        this.laserPoints.addAll(laserPoints);
        invalidate();
    }

    /**
     * 计算bitmap在以fitCenter模式时图片的变换矩阵
     *
     * @param bitmap
     * @return
     */
    private Matrix caculateMatrix(Bitmap bitmap) {

        Matrix matrix = new Matrix();

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Log.d(TAG, "caculateMatrix: w:" + w + "\nh:"+h);

        width = getWidth();
        height = getHeight();

        Log.d(TAG, "caculateMatrix: width:" + width + "\nheight:"+height);

        float widthRatio = width / (float) w;
        float heightRatio = height / (float) h;

        /**
         *  宽度刚好铺满的情况
         */
        if (h * widthRatio <= height) {
            matrix.postScale(widthRatio, widthRatio);
            matrix.postTranslate(0,(getHeight() - h * widthRatio) / 2);
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


    /**
     * 设置机器人位置
     *
     * @param robotX
     * @param robotY
     */
    public void setRobotPosition(float robotX, float robotY) {
        isRobotShow = true;
        this.robotX = robotX;
        this.robotY = robotY;
        invalidate();
    }

    /**
     *  设置标记点
     * @param pointBeanList
     */
    public void setPointBeanList(List<PointBean> pointBeanList) {
        isShowPointBean = true;
        this.pointBeanList.clear();
        this.pointBeanList.addAll(pointBeanList);
        invalidate();
    }

    public void pathAddPoint(PointBean pointBean) {
        isShowSelectedPath = true;
        selectedPointList.add(pointBean);
        invalidate();
    }

    public void pathRemovePoint(int position) {
        isShowSelectedPath = true;
        selectedPointList.remove(position);
        invalidate();
    }
}
