package com.gps.sweeprobot.model.mapmanager.bean;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapListBean {

    private String createAt;
    private String id;
    private String name;
    private MapInfoBean mapInfo;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MapInfoBean getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfoBean mapInfo) {
        this.mapInfo = mapInfo;
    }
}
