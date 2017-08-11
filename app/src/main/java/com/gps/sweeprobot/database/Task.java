package com.gps.sweeprobot.database;

import com.gps.sweeprobot.model.taskqueue.bean.TaskPoint;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/20 0020
 * @Descriptiong : 任务
 */

public class Task extends DataSupport{

    /* 任务id */
    private int id;

    /* 任务状态 excute prepare finish  执行中 待执行 任务完成 */
    private String status;

    /* 任务类型  0 1 2 分别表示 导航 路径 清洁*/
    private int type;

    /* 该任务的点 */
    private List<TaskPoint> taskPoints;

    public Task(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TaskPoint> getTaskPoints() {
        return taskPoints;
    }

    public void setTaskPoints(List<TaskPoint> taskPoints) {
        this.taskPoints = taskPoints;
    }
}
