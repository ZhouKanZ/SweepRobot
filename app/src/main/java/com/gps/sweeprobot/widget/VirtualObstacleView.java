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
    private List<MyPointF> FinishedList;
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

    private Path path;

    public VirtualObstacleView(Context context) {
        super(context);
        init();
    }

    public VirtualObstacleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VirtualObstacleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        FinishedList = new ArrayList<>();
        drawingList = new ArrayList<>();
        mMatrix = new Matrix();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(getResources().getColor(R.color.color_red_ccfa3c55));
        linePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        polygonPaint = new Paint();
        polygonPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        polygonPaint.setColor(getResources().getColor(R.color.color_yellow_b39729));
        polygonPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 5.0f));

        textPaint = new TextPaint(1);
        textPaint.setColor(getResources().getColor(R.color.color_red_f04c62));
        textPaint.setTextSize(DensityUtil.getDimen(R.dimen.text_size_12));

//        setBackgroundColor(getResources().getColor(R.color.color_activity_blue_bg));
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
        FinishedList = pointFList;
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

    public List<MyPointF> getDrawingList() {
        return drawingList;
    }

    public void drawPolygons(Canvas canvas) {

        if (isFinish){
            drawPath(FinishedList,canvas);
        }else {
            drawPath(drawingList,canvas);
        }
    }

    public void drawPath(List<MyPointF> pointFList, Canvas canvas) {

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
                continue;
            }


            PointF intermediate = DegreeManager.
                    changeAbsolutePoint(pointFList.get(i).getX(), pointFList.get(i).getY(), mMatrix);
            LogManager.i("intermediate =========[" + intermediate.x + "," + intermediate.y + "]");
            path.lineTo(intermediate.x, intermediate.y);
            nameX += intermediate.x;
            nameY += intermediate.y;

        }
        path.close();

        canvas.drawPath(path, circlePaint);

        if (getName() != null) {

            canvas.drawText(getName(), nameX / pointFList.size(),
                    nameY / pointFList.size() - DensityUtil.dip2px(getContext(), 15.0f), textPaint);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {

        drawPolygons(canvas);
    }

    public void setName(String name) {

        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void saveObstacleBean(SaveObstacleListener listener) {

        LogManager.i("drawing list size =====" + drawingList.size());
        // 虚拟墙绘制完成
        isFinish = true;
        drawingList.clear();
        listener.onSaveObstacle(FinishedList, name);
        LogManager.i("finished list size =======" + FinishedList.size());
        postInvalidate();
    }

    public interface SaveObstacleListener {

        void onSaveObstacle(List<MyPointF> FinishedList, String name);
    }
}
