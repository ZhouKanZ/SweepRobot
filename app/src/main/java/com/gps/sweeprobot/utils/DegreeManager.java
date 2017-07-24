package com.gps.sweeprobot.utils;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by wangjun on 2017/6/27.
 */

public class DegreeManager {

    private static final String TAG = "DegreeManager";

    public static float getRotation(MotionEvent event){
        double delta_x=event.getX(0)-event.getX(1);
        double delta_y=event.getY(0)-event.getY(1);
        //将矩形坐标 (x, y) 转换成极坐标 (r, theta)，返回所得角 theta。
        double radians = Math.atan2(delta_x, delta_y);
        Log.i(TAG, "getRotation: degree=========="+Math.toDegrees(radians));
        return (float) Math.toDegrees(radians)*-1;
    }


    /**
     * 基于原点得到矩阵变化后的坐标点
     * @param locationX 原点x
     * @param locationY 原点y
     * @param matrix
     * @return
     */
    public static PointF changeAbslutePoint(float locationX, float locationY, Matrix matrix)
    {
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = locationX;
        arrayOfFloat[1] = locationY;
        matrix.mapPoints(arrayOfFloat);
        int x = Integer.valueOf((int)arrayOfFloat[0]).intValue();
        int y = Integer.valueOf((int)arrayOfFloat[1]).intValue();
        return new PointF(x,y);
    }

    /**
     * 基于原点求出相对于图片坐标系的坐标点
     * @param locationX 原点x
     * @param locationY 原点y
     * @param matrix
     * @return
     */
    public static PointF changeRelativePoint(float locationX,float locationY, Matrix matrix){

        float[] arrayOfFloat= new float[2];
        arrayOfFloat[0]=locationX;
        arrayOfFloat[1]=locationY;
        Matrix invertMatrix=new Matrix();
        //得到逆矩阵
        matrix.invert(invertMatrix);
        //将此矩阵应用于2D点阵列，并将转换的点写入数组
        invertMatrix.mapPoints(arrayOfFloat);
        float x = arrayOfFloat[0];
        float y = arrayOfFloat[1];
        return new PointF(x,y);
    }
}
