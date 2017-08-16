package com.gps.sweeprobot.bean;

import java.util.List;

/**
 * Create by WangJun on 2017/8/11
 */

public class ObstaclePose {

    private String birthtime;
    private String name;
    private String mapname;
    private float gridX;
    private float gridY;
    private int id;
    private int mapid;
    private int type;
    private List<NavPose.Pose> worldposition;

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapname() {
        return mapname;
    }

    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    public float getGridX() {
        return gridX;
    }

    public void setGridX(float gridX) {
        this.gridX = gridX;
    }

    public float getGridY() {
        return gridY;
    }

    public void setGridY(float gridY) {
        this.gridY = gridY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapid() {
        return mapid;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<NavPose.Pose> getWorldposition() {
        return worldposition;
    }

    public void setWorldposition(List<NavPose.Pose> worldposition) {
        this.worldposition = worldposition;
    }
}
