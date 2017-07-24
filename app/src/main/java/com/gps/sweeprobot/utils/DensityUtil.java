package com.gps.sweeprobot.utils;

import android.content.Context;
import android.support.annotation.DimenRes;

import com.gps.sweeprobot.MainApplication;

/**
 * @Author : WangJun
 * @CreateDate : 2017/6/28.
 * @Desc : xxx
 */

public class DensityUtil {

    public static int dip2px(float dip)
    {
        return (int)(dip * MainApplication.getContext().getResources().getDisplayMetrics().density +0.5f);
    }

    public static int dip2px(Context context, float dip)
    {
        return (int)(dip * context.getResources().getDisplayMetrics().density +0.5f);
    }

    public static int getDimen(@DimenRes int dimenRes)
    {
        return getDimen(MainApplication.getContext(), dimenRes);
    }

    public static int getDimen(Context context, @DimenRes int dimenRes)
    {
        return (int)(context.getResources().getDimension(dimenRes) +0.5f);
    }

    public static int px2dip(Context context, float px)
    {
        return (int)(px / context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2sp(Context context, float px)
    {
        return (int)(px / context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
    }

    public static int sp2px(Context context, float sp)
    {
        return (int)(sp * context.getResources().getDisplayMetrics().scaledDensity + 0.5F);
    }
}
