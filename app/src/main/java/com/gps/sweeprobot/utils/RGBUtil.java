package com.gps.sweeprobot.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * @Author : WangJun
 * @CreateDate : 2017/7/4.
 * @Desc : 获取坐标点的颜色值
 */

public class RGBUtil {

    /**
     * 获取像素点的颜色值rgb565
     * @param imageView
     * @param pointF
     * @return
     */
    public static int getRedValue(ImageView imageView, PointF pointF){
        int pixel = getPixel(imageView, pointF);
        return Color.red(pixel);
    }

    public static int getGreenValue(ImageView imageView, PointF pointF){
        int pixel = getPixel(imageView, pointF);
        return Color.green(pixel);
    }

    public static int getBlueValue(ImageView imageView, PointF pointF){
        int pixel = getPixel(imageView, pointF);
        return Color.blue(pixel);
    }

    /**
     * 获取图像中的像素点
     * @param imageView
     * @param pointF
     * @return
     */
    private static int getPixel(ImageView imageView, PointF pointF){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        if (bitmap.getWidth() < pointF.x)
            return -1;
        int pixel = bitmap.getPixel(((int) pointF.x), ((int) pointF.y));
        return pixel;
    }



}
