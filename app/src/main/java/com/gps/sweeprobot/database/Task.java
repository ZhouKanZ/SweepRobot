package com.gps.sweeprobot.database;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/20 0020
 * @Descriptiong : 任务
 */

public class Task extends DataSupport{

    /* primary key */
    private int id;

    private String name;

    private String createTime;

    /* 任务类型  0(导航点) 1(轨迹) 2()*/
    private int type;

    private boolean isExecuting = false;

    private String mapUrl;

    /* 外键id
    *  标识机器人执行任务所处的环境
    */
    private int mapId;

    /*
     * 导航点数据   length = 1  表示导航点 length > 1 轨迹任务;
     */
    private List<PointBean> pointBeanList;

    public List<PointBean> getPointBeanList() {
        return pointBeanList;
    }

    public void setPointBeanList(List<PointBean> pointBeanList) {
        this.pointBeanList = pointBeanList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    /**
     *  设置时间，不需要传入参数
     */
    public void setCreateTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = sdf.format(date);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExecuting() {
        return isExecuting;
    }

    public void setExecuting(boolean executing) {
        isExecuting = executing;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }
}
