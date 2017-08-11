package com.gps.sweeprobot.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.utils.DensityUtil;
import com.gps.sweeprobot.utils.LogManager;

/**
 * @Author : WangJun
 * @CreateDate : 2017/6/28.
 * @Desc : xxx
 */

public class CoordinateView extends ViewGroup {

    private static final String TAG = "CoordinateView";
    private static final int VIEWSIZE = 10;

    private float angle;
    public float imageViewX;
    public float imageViewY;
    private boolean isShowArrow = false;
    private boolean isShowPointName = true;
    private float locationX;
    private float locationY;
    private Bitmap mArrow;
    private int mArrowWidth;
    private ImageView mainPosition;
    private int mainPositionRes;
    private float matrixRotateAngle;
    private float moveX;
    private float moveY;
    private int padding = 5;
    private int paddingBottom = 12;
    private String positionName;
    private TextPaint textPaint;
    private int textSize = 25;
    private int textWidth;

    public CoordinateView(Context context, int defStyleRes) {
        super(context);
        this.mainPositionRes = defStyleRes;
        initPaint();
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleRes) {
        super(context, attrs);
        this.mainPositionRes = defStyleRes;
        initPaint();
    }

    public CoordinateView(Context paramContext, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(paramContext, attrs, defStyleAttr);
        this.mainPositionRes = defStyleRes;
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CoordinateView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, int paramInt3) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
        this.mainPositionRes = paramInt3;
        initPaint();
    }

    private int computeMaxStringWidth(String paramString, Paint paramPaint) {
        return (int)(Math.max(paramPaint.measureText(paramString), 0.0F) + 0.5D);
    }

    private void initPaint() {

        ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(DensityUtil.dip2px(getContext(), 10.0F),
                DensityUtil.dip2px(getContext(), 10.0F));

        setBackgroundColor(0x00f);

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            public boolean onPreDraw()
            {
                getViewTreeObserver().removeOnPreDrawListener(this);
                setX(locationX - moveX);
                setY(locationY - moveY);
                return true;
            }
        });

        this.mainPosition = new ImageView(getContext());
        this.mainPosition.setImageResource(this.mainPositionRes);
        this.mainPosition.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mainPosition.setLayoutParams(localLayoutParams);
        addView(this.mainPosition);
        this.textPaint = new TextPaint(1);
        this.textPaint.setColor(getResources().getColor(R.color.color_activity_blue_bg));
        this.textPaint.setTextSize(DensityUtil.getDimen(R.dimen.coordinate_text_size));
        setWillNotDraw(false);
        this.mArrow = BitmapFactory.decodeResource(getResources(),R.mipmap.arrow);
        this.mArrowWidth = this.mArrow.getWidth();

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.isShowPointName)
        {
            int i = 0;
            if (this.textWidth < this.mArrowWidth * 2)
                i = this.mArrowWidth - this.textWidth / 2;
            canvas.drawText(this.positionName, i, Math.abs(this.textPaint.getFontMetrics().top), this.textPaint);
        }
        if (this.isShowArrow)
        {
            canvas.save();
            canvas.rotate(-this.angle + this.matrixRotateAngle, this.moveX, this.moveY);
            canvas.drawBitmap(this.mArrow, this.moveX, this.mainPosition.getTop(), new Paint());
            canvas.restore();
        }
    }

    public float getAngle()
    {
        return this.angle;
    }

    public float getLocationX()
    {
        return this.locationX;
    }

    public float getLocationY()
    {
        return this.locationY;
    }

    public ImageView getMainPositionView()
    {
        return this.mainPosition;
    }

    public String getPositionName()
    {
        return this.positionName;
    }

    public boolean isShowArrow()
    {
        return this.isShowArrow;
    }

    public boolean isShowPointName()
    {
        return this.isShowPointName;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        getChildAt(0).layout(getMeasuredWidth() / 2 - DensityUtil.dip2px(getContext(), 10.0F) / 2,
                getMeasuredHeight() - DensityUtil.dip2px(getContext(), 10.0F) - DensityUtil.dip2px(this.paddingBottom),
                getMeasuredWidth() / 2 + DensityUtil.dip2px(getContext(), 10.0F) / 2,
                getMeasuredHeight() - DensityUtil.dip2px(this.paddingBottom));

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.textWidth > DensityUtil.dip2px(getContext(), 10.0F));
        for (widthMeasureSpec = this.textWidth; ; widthMeasureSpec = DensityUtil.dip2px(getContext(), 10.0F))
        {
            heightMeasureSpec = widthMeasureSpec;
            if (widthMeasureSpec < this.mArrowWidth * 2)
                heightMeasureSpec = this.mArrowWidth * 2;

            widthMeasureSpec = this.mainPosition.getMeasuredHeight() + DensityUtil.dip2px(getContext(), this.padding)
                    + DensityUtil.dip2px(getContext(), this.textSize) + DensityUtil.dip2px(this.paddingBottom);
            setMeasuredDimension(heightMeasureSpec, widthMeasureSpec);
            this.moveX = (heightMeasureSpec / 2);
            this.moveY = (widthMeasureSpec - DensityUtil.dip2px(getContext(), 10.0F) / 2 - DensityUtil.dip2px(this.paddingBottom));
            LogManager.i("coordinate on measure movex========"+moveX+"movey========="+moveY);
            return;
        }
    }

    public void resumeMainImage()
    {
        this.mainPosition.setImageResource(this.mainPositionRes);
    }

    public void setAngle(float angle) {
        if (this.isShowArrow)
        {
            this.angle = angle;
            invalidate();
        }
    }

    public void setLocationX(float locationX)
    {
        this.locationX = locationX;
    }

    public void setLocationY(float locationY)
    {
        this.locationY = locationY;
    }

    public void setMainImage(int imageRes)
    {
        this.mainPosition.setImageResource(imageRes);
    }

    public void setMatrixRotateAngle(float matrixRotateAngle) {
        if (this.isShowArrow)
        {
            this.matrixRotateAngle = matrixRotateAngle;
            invalidate();
        }
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
        this.textWidth = computeMaxStringWidth(positionName, this.textPaint);
    }

    public void setPositionViewX(float positionViewX)
    {
        setX(positionViewX - this.moveX);
    }

    public void setPositionViewY(float positionViewY)
    {
        setY(positionViewY - this.moveY);
    }

    public void setShowArrow(boolean isShowArrow) {
        this.isShowArrow = isShowArrow;
        postInvalidate();
    }

    public void setShowPointName(boolean isShowPointName) {
        if (this.isShowPointName == isShowPointName)
            return;
        this.isShowPointName = isShowPointName;
        invalidate();
    }

    public void setmArrow(Bitmap mArrow) {
        this.mArrow = mArrow;
    }
}
