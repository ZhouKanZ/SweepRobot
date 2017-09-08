package com.gps.sweeprobot.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        mainPositionRes = defStyleRes;
        initPaint();
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleRes) {
        super(context, attrs);
        mainPositionRes = defStyleRes;
        initPaint();
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mainPositionRes = defStyleRes;
        initPaint();
    }


    private int computeMaxStringWidth(String str, Paint paint) {
        return (int) (Math.max(paint.measureText(str), 0.0F) + 0.5D);
    }

    private void initPaint() {

        ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(DensityUtil.dip2px(getContext(), 10.0F),
                DensityUtil.dip2px(getContext(), 10.0F));

        setBackgroundColor(0x00f);

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                setX(locationX - moveX);
                setY(locationY - moveY);
                return true;
            }
        });

        mainPosition = new ImageView(getContext());
        mainPosition.setImageResource(mainPositionRes);
        mainPosition.setScaleType(ImageView.ScaleType.FIT_XY);
        mainPosition.setLayoutParams(localLayoutParams);

        addView(mainPosition);
        textPaint = new TextPaint(1);
        textPaint.setColor(getResources().getColor(R.color.color_activity_blue_bg));
        textPaint.setTextSize(DensityUtil.getDimen(R.dimen.coordinate_text_size));
        setWillNotDraw(false);
        mArrow = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);
        mArrowWidth = mArrow.getWidth();

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isShowPointName) {
            int i = 0;
            if (textWidth < mArrowWidth * 2)
                i = mArrowWidth - textWidth / 2;
            canvas.drawText(positionName, i, Math.abs(textPaint.getFontMetrics().top), textPaint);
        }
        if (isShowArrow) {
            canvas.save();
            canvas.rotate(-angle + matrixRotateAngle, moveX, moveY);
            canvas.drawBitmap(mArrow, mainPosition.getLeft() - mainPosition.getWidth(),
                    mainPosition.getTop(), new Paint());
            canvas.restore();

        }
    }

    public float getAngle() {
        return angle;
    }

    public float getLocationX() {
        return locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public ImageView getMainPositionView() {
        return mainPosition;
    }

    public String getPositionName() {
        return positionName;
    }

    public boolean isShowArrow() {
        return isShowArrow;
    }

    public boolean isShowPointName() {
        return isShowPointName;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        getChildAt(0).layout(getMeasuredWidth() / 2 - DensityUtil.dip2px(getContext(), 10.0F) / 2,
                getMeasuredHeight() - DensityUtil.dip2px(getContext(), 10.0F) - DensityUtil.dip2px(paddingBottom),
                getMeasuredWidth() / 2 + DensityUtil.dip2px(getContext(), 10.0F) / 2,
                getMeasuredHeight() - DensityUtil.dip2px(paddingBottom));

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthMeasureSpec = textWidth;
        heightMeasureSpec = widthMeasureSpec;
        if (widthMeasureSpec < mArrowWidth * 2) {

            heightMeasureSpec = mArrowWidth * 2;
        }
        widthMeasureSpec = mainPosition.getMeasuredHeight()
                + DensityUtil.dip2px(getContext(), padding)
                + DensityUtil.dip2px(getContext(), textSize)
                + DensityUtil.dip2px(paddingBottom);

        setMeasuredDimension(heightMeasureSpec, widthMeasureSpec);

        moveX = (heightMeasureSpec / 2);
        moveY = (widthMeasureSpec
                - DensityUtil.dip2px(getContext(), 10.0F) / 2
                - DensityUtil.dip2px(paddingBottom));
        LogManager.i("coordinate on measure movex========" + moveX + "movey=========" + moveY);

/*        if (textWidth > DensityUtil.dip2px(getContext(), 10.0F));
        for (widthMeasureSpec = textWidth; ; widthMeasureSpec = DensityUtil.dip2px(getContext(), 10.0F))
        {
            heightMeasureSpec = widthMeasureSpec;
            if (widthMeasureSpec < mArrowWidth * 2)
                heightMeasureSpec = mArrowWidth * 2;

            widthMeasureSpec = mainPosition.getMeasuredHeight() + DensityUtil.dip2px(getContext(), padding)
                    + DensityUtil.dip2px(getContext(), textSize) + DensityUtil.dip2px(paddingBottom);
            setMeasuredDimension(heightMeasureSpec, widthMeasureSpec);
            moveX = (heightMeasureSpec / 2);
            moveY = (widthMeasureSpec - DensityUtil.dip2px(getContext(), 10.0F) / 2 - DensityUtil.dip2px(paddingBottom));
            LogManager.i("coordinate on measure movex========"+moveX+"movey========="+moveY);
            return;
        }*/

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public void resumeMainImage() {
        mainPosition.setImageResource(mainPositionRes);
    }

    public void setAngle(float angle) {
        if (isShowArrow) {
            this.angle = angle;
            invalidate();
        }
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public void setMainImage(int imageRes) {
        mainPosition.setImageResource(imageRes);
    }

    public void setMatrixRotateAngle(float matrixRotateAngle) {
        if (isShowArrow) {
            this.matrixRotateAngle = matrixRotateAngle;
            invalidate();
        }
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
        textWidth = computeMaxStringWidth(positionName, textPaint);
    }

    public void setPositionViewX(float positionViewX) {
        setX(positionViewX - moveX);
    }

    public void setPositionViewY(float positionViewY) {
        setY(positionViewY - moveY);
    }

    public void setShowArrow(boolean isShowArrow) {
        this.isShowArrow = isShowArrow;
        postInvalidate();
    }

    public void setShowPointName(boolean isShowPointName) {
        if (isShowPointName == isShowPointName)
            return;
        this.isShowPointName = isShowPointName;
        invalidate();
    }

    public void setmArrow(Bitmap mArrow) {
        this.mArrow = mArrow;
    }
}
