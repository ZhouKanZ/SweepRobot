package com.gps.sweeprobot.database;

import org.litepal.crud.DataSupport;

/**
 * Create by WangJun on 2017/8/4
 */

public class MyPointF extends DataSupport{

    private float x;
    private float y;

    public MyPointF() {
    }

    public MyPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
