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
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.utils.DegreeManager;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.utils.LogManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/8/1
 */

public class VirtualObstacleView extends View {

    private List<MyPointF> myPointFs;
    private Matrix mMatrix;
    private Paint linePaint;
    private Paint polygonPaint;
    private Paint circlePaint;
    private TextPaint textPaint;
    private String name;

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

        myPointFs = new ArrayList<>();
        mMatrix = new Matrix();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(getResources().getColor(R.color.color_yellow_b39729));
        linePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        polygonPaint = new Paint();
        polygonPaint.setStyle(Paint.Style.STROKE);
        polygonPaint.setColor(getResources().getColor(R.color.color_yellow_b39729));
        polygonPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2.0f));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 5.0f));

        textPaint = new TextPaint(1);
        textPaint.setColor(getResources().getColor(R.color.color_yellow_b39729));
        textPaint.setTextSize(DensityUtil.getDimen(R.dimen.text_size_12));

//        setBackgroundColor(getResources().getColor(R.color.color_activity_blue_bg));
    }

    public void setmMatrix(Matrix mMatrix) {
        this.mMatrix = mMatrix;
        LogManager.i("virtual ============="+mMatrix.toString());
        invalidate();
    }

    public void setmyPointFs(List<MyPointF> myPointFs) {
        this.myPointFs.clear();
        this.myPointFs.addAll(myPointFs);
        invalidate();
    }

    public void addPoint(PointF point){

        PointF pointF = DegreeManager.changeRelativePoint(point.x, point.y, mMatrix);
        MyPointF f = new MyPointF();
        f.setX(pointF.x);
        f.setY(pointF.y);
        myPointFs.add(f);
        invalidate();
    }

    public void drawPolygons(Canvas canvas) {

        LogManager.i("drawPolygons size==========" + myPointFs.size());

        Path path = new Path();
        PointF start = null;
        PointF intermediate;
        for (int i = 0; i < myPointFs.size(); i++) {

            if (i == 0) {
                start = DegreeManager.changeAbsolutePoint(myPointFs.get(i).getX(), myPointFs.get(i).getY(), mMatrix);
                LogManager.i("start =========[" + start.x + "," + start.y + "]");
                path.moveTo(start.x, start.y);
            } else {

                intermediate = DegreeManager.changeAbsolutePoint(myPointFs.get(i).getX(), myPointFs.get(i).getY(), mMatrix);
                LogManager.i("intermediate =========[" + intermediate.x + "," + intermediate.y + "]");
                path.lineTo(intermediate.x, intermediate.y);
            }
        }
        canvas.drawPath(path, linePaint);
        if (start != null && getName() != null){

            canvas.drawText(getName(),start.x,start.y+DensityUtil.dip2px(getContext(),15.0f),textPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawPolygons(canvas);
    }

    public void setName(String name){

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public VirtualObstacleBean saveObstacleBean(){

        //将虚拟墙数据存进数据库
        DataSupport.saveAll(myPointFs);
        VirtualObstacleBean virtualObstacleBean = new VirtualObstacleBean();
        virtualObstacleBean.setName(name);
        virtualObstacleBean.setMyPointFs(myPointFs);
        virtualObstacleBean.save();

        //通知服务器
        return virtualObstacleBean;
    }
}
