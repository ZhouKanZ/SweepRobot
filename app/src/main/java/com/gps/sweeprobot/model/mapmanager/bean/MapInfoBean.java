package com.gps.sweeprobot.model.mapmanager.bean;

import org.litepal.crud.DataSupport;

/**
 * Create by WangJun on 2017/7/19
 */

public class MapInfoBean extends DataSupport{

    private int gridHeight;
    private int gridWidth;
    private double originX;
    private double originY;
    private double resolution;

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public double getOriginX() {
        return originX;
    }

    public void setOriginX(double originX) {
        this.originX = originX;
    }

    public double getOriginY() {
        return originY;
    }

    public void setOriginY(double originY) {
        this.originY = originY;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }
}
