package com.gps.sweeprobot.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */

public class GpsMap {

    // 地图id
    private int map_id;
    // 数据库存储
    private int mapResId;
    // 地图名称
    private String mapName;
    // 创建日期
    private String createDate;
    // 地图状态
    private int mapStatus;

    public GpsMap(int map_id, int mapResId, String mapName, String createDate, int mapStatus) {
        this.map_id = map_id;
        this.mapResId = mapResId;
        this.mapName = mapName;
        this.createDate = createDate;
        this.mapStatus = mapStatus;
    }

    public GpsMap() {
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getMapResId() {
        return mapResId;
    }

    public void setMapResId(int mapResId) {
        this.mapResId = mapResId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(int mapStatus) {
        this.mapStatus = mapStatus;
    }
}
