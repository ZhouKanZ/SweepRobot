package com.gps.sweeprobot.model.mapmanager.bean;

import org.litepal.crud.DataSupport;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapListBean extends DataSupport{

    private String createAt;
    private String id;
    private String name;
    private String imgUrl;
    private MapInfoBean mapInfo;

    public MapListBean() {
    }

    public MapListBean(String createAt, String name) {
        this.createAt = createAt;
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

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
