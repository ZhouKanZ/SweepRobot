package com.gps.sweeprobot.database;

import com.gps.sweeprobot.bean.IAction;

/**
 * Create by WangJun on 2017/7/24
 */

public class PointBean extends IAction{

    /* 地图的id也是table的主键 */
    private int id;

    //标记点相对于图片的横坐标x
    private float x;

    //标记点相对于图片的横坐标y
    private float y;

    //标记点名字
    private String pointName;

    //标记点所在地图的名字
    private String mapName;

    /* 外键id */
    private int mapId;

    public PointBean() {
    }

    public PointBean(float x, float y, String pointName, String mapName) {
        this.x = x;
        this.y = y;
        this.pointName = pointName;
        this.mapName = mapName;
    }

    public int getId() {
        return id;
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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }
}
