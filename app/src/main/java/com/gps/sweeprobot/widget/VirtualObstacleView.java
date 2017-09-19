package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.utils.DegreeManager;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.utils.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/8/1
 */

public class VirtualObstacleView extends View {

    //保存一个完整的虚拟墙顶点数
    private List<MyPointF> finishedList;

    //保存还在绘制的虚拟墙顶点数
    private List<MyPointF> drawingList;

    //pinchImageView 的变换矩阵
    private Matrix mMatrix;
    private Paint linePaint;
    private Paint polygonPaint;
    private Paint circlePaint;
    private TextPaint textPaint;
    //虚拟墙名字
    private String name;
    //顶点是否设置完成
    private boolean isFinish;
    //该view是否已被添加
    private boolean isAdd;
    //是否取消添加虚拟墙
    private boolean isCancel;

    private Path path;

    private AttributeSet attr;

    private int width;
    private int height;

    public VirtualObstacleView(Context context) {
        super(context);
        init();
    }

    public VirtualObstacleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogManager.i("obstacle 22222222222222");
        this.attr = attrs;
        init();
    }

    public VirtualObstacleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        finishedList = new ArrayList<>();
        drawingList = new ArrayList<>();
        mMatrix = new Matrix();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(0xccfa3c55);
        linePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        polygonPaint = new Paint();
        polygonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        polygonPaint.setColor(0xff00ae8c);
        polygonPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        textPaint = new TextPaint(1);
        textPaint.setColor(0xff333333);
        textPaint.setTextSize(DensityUtil.getDimen(R.dimen.text_size_12));

        isCancel = false;
//        setBackgroundColor(0x55101010);
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }


    public void setmMatrix(Matrix mMatrix) {
        this.mMatrix = mMatrix;
        LogManager.i("virtual =============" + mMatrix.toString());
        invalidate();
    }

    public void setFinishedList(List<MyPointF> pointFList) {
        isFinish = true;
        finishedList = pointFList;
        invalidate();
    }

    public void addPoint(PointF point) {

        isFinish = false;
        PointF pointF = DegreeManager.changeRelativePoint(point.x, point.y, mMatrix);
        MyPointF f = new MyPointF();
        f.setX(pointF.x);
        f.setY(pointF.y);
        drawingList.add(f);
        invalidate();
    }

    private void drawPolygons(Canvas canvas) {

        if (isFinish) {
            drawPath(finishedList, canvas);
        } else {
            if (isCancel){
                drawingList.clear();
            }
            drawPath(drawingList, canvas);
        }
    }

    private void drawPath(List<MyPointF> pointFList, Canvas canvas) {

        Path path = new Path();

        float nameX = 0, nameY = 0;

        for (int i = 0; i < pointFList.size(); i++) {

            if (i == 0) {
                PointF start = DegreeManager.
                        changeAbsolutePoint(pointFList.get(i).getX(), pointFList.get(i).getY(), mMatrix);
                LogManager.i("start =========[" + start.x + "," + start.y + "]");
                path.moveTo(start.x, start.y);
                nameX = start.x;
                nameY = start.y;
                canvas.drawPoint(start.x,start.y,polygonPaint);

            } else {
                PointF intermediate = DegreeManager.
                        changeAbsolutePoint(pointFList.get(i).getX(), pointFList.get(i).getY(), mMatrix);
//                LogManager.i("intermediate =========[" + intermediate.x + "," + intermediate.y + "]");
                path.lineTo(intermediate.x, intermediate.y);
                nameX += intermediate.x;
                nameY += intermediate.y;
            }
        }
        path.close();

//        setViewPosition();
        canvas.drawPath(path, polygonPaint);

        if (getName() != null) {

            canvas.drawText(getName(), nameX / pointFList.size(),
                    nameY / pointFList.size() - DensityUtil.dip2px(getContext(), 15.0f), textPaint);
        }

    }

/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        LogManager.i("onMeasure   width=========" + width + "  height===========" + height);

        setMeasuredDimension(width, height);

    }*/

    private void setViewPosition() {

        if (finishedList.size() > 0) {

            float[] x = new float[finishedList.size()];
            float[] y = new float[finishedList.size()];

            for (int i = 0; i < finishedList.size(); i++) {

                PointF pointF = DegreeManager.changeAbsolutePoint(finishedList.get(i).getX(),
                        finishedList.get(i).getY(), mMatrix);

                x[i] = pointF.x;
                y[i] = pointF.y;
            }

            float[] sequenceX = sequence(x);
            float[] sequenceY = sequence(y);

            int marginLeft = (int) sequenceX[0];
            int marginTop = (int) sequenceY[0];
            int marginRight = width - (int) sequenceX[sequenceX.length - 1];
            int marginBottom = height - (int) sequenceY[sequenceY.length - 1];

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.leftMargin = marginLeft;
            params.topMargin = marginTop;
            params.rightMargin = marginRight;
            params.bottomMargin = marginBottom;

            setLayoutParams(params);
            setMeasuredDimension(width - marginLeft - marginRight + DensityUtil.dip2px(10),
                    height - marginTop - marginBottom + DensityUtil.dip2px(10));
        }
    }

    /**
     * 冒泡排序
     *
     * @param coordinates
     */
    private float[] sequence(float[] coordinates) {

        float[] coords;
        for (int i = 0; i < coordinates.length - 1; i++) {

            for (int j = 0; j < coordinates.length - i - 1; j++) {
                if (coordinates[j] > coordinates[j + 1]) {
                    float temp = coordinates[j];
                    coordinates[j] = coordinates[j + 1];
                    coordinates[j + 1] = temp;
                }
            }
        }
        coords = coordinates;

        return coords;
    }

    public static int getDefaultSize(int size, int measureSpec) {

        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {

            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        LogManager.i("obstacle on draw");
        drawPolygons(canvas);

    }

    public void setName(String name) {

        this.name = name;

    }

    private String getName() {
        return name;
    }

    public void saveObstacleBean(SaveObstacleListener listener) {

        // 虚拟墙绘制完成
        isFinish = true;
        finishedList = drawingList;
        listener.onSaveObstacle(finishedList, name);
        LogManager.i("finished list size =======" + finishedList.size());
        postInvalidate();
    }

    public void cancelAddView(){
        isCancel = true;
        invalidate();
    }

    public interface SaveObstacleListener {

        void onSaveObstacle(List<MyPointF> FinishedList, String name);
    }
}
