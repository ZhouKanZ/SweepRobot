package com.gps.sweeprobot.database;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/20 0020
 * @Descriptiong : xxx
 */

public class GpsMapBean extends DataSupport {

    /* 地图的id也是table的主键 */
    private int id;

    /* 地图的名称 */
    private String name;

    /**
     *  地图的当前的状态
     *  0 正在构建
     *  1 表示已经构建完成
     */
    private int status;

    /* 地图的创建日期 */
    private String date;

    /* 构建地图的Image对应的地址 */
    private String creatingMapUrl;

    /* 地图构建成功后Image对应的地址 */
    private String completedMapUrl;

    /* 障碍物 */
    private List<VirtualObstacleBean> virtualObstacleBeanList;

    /* 标记点 */
    private List<PointBean> pointBeanList;



    public GpsMapBean() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = sdf.format(date);
    }

    public String getCreatingMapUrl() {
        return creatingMapUrl;
    }

    public void setCreatingMapUrl(String creatingMapUrl) {
        this.creatingMapUrl = creatingMapUrl;
    }

    public String getCompletedMapUrl() {
        return completedMapUrl;
    }

    public void setCompletedMapUrl(String completedMapUrl) {
        this.completedMapUrl = completedMapUrl;
    }

    public List<VirtualObstacleBean> getVirtualObstacleBeanList() {
        return virtualObstacleBeanList;
    }

    public void setVirtualObstacleBeanList(List<VirtualObstacleBean> virtualObstacleBeanList) {
        this.virtualObstacleBeanList = virtualObstacleBeanList;
    }

    public List<PointBean> getPointBeanList() {
        return pointBeanList;
    }

    public void setPointBeanList(List<PointBean> pointBeanList) {
        this.pointBeanList = pointBeanList;
    }


}
