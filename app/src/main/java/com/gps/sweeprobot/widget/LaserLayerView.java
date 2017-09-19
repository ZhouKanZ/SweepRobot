package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/29 0029
 * @Descriptiong : 镭射层
 */

public class LaserLayerView extends View {

    private int bw;
    private int bh;

    /* 底部图层传递过来的rect */
    private Rect contentRect;
    private Paint mPaint;

    public LaserLayerView(Context context) {
        this(context,null);
    }

    public LaserLayerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LaserLayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#bcbcbc"));
        mPaint.setStyle(Paint.Style.FILL);

        contentRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
