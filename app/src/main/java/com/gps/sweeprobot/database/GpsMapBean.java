package com.gps.sweeprobot.database;

import com.gps.sweeprobot.model.taskqueue.bean.TaskPoint;

import org.litepal.crud.DataSupport;

import java.sql.Date;
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
    private Date data;

    /* 构建地图的Image对应的地址 */
    private String creatingMapUrl;

    /* 地图构建成功后Image对应的地址 */
    private String completedMapUrl;

    /* 任务  导航任务  路径任务 清洁任务 */
    private List<Task> tasks;

    /* 任务点 */
    private List<TaskPoint> taskPoints;

    public GpsMapBean(int id, String name,
                      int status, Date data,
                      String creatingMapUrl,
                      String completedMapUrl,
                      List<Task> tasks,
                      List<TaskPoint> taskPoints) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.data = data;
        this.creatingMapUrl = creatingMapUrl;
        this.completedMapUrl = completedMapUrl;
        this.tasks = tasks;
        this.taskPoints = taskPoints;
    }

    public GpsMapBean(int id, String name, int status, Date data, String creatingMapUrl, String completedMapUrl) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.data = data;
        this.creatingMapUrl = creatingMapUrl;
        this.completedMapUrl = completedMapUrl;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<TaskPoint> getTaskPoints() {
        return taskPoints;
    }

    public void setTaskPoints(List<TaskPoint> taskPoints) {
        this.taskPoints = taskPoints;
    }
}
