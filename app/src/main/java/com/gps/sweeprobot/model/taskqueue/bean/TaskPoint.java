package com.gps.sweeprobot.model.taskqueue.bean;

import org.litepal.crud.DataSupport;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/19 0019
 * @Descriptiong : 任务点 ： 是任务的基本组成部分
 * 1.标记点任务  由任务点散点构成
 * 2.路径任务    由任务点连成第一个点到最后一个点的折线
 * 3.覆盖物任务  由任务点的第一个连到最后一个点形成的闭合多边形
 *
 */

public class TaskPoint extends DataSupport {

    private String taskName;
    // 屏幕上的x坐标
    private float x;
    // 屏幕上的y坐标
    private float y;
    // 表示该任务点已经被分配
    private boolean isChecked;

    public TaskPoint(float x, float y,String taskName) {
        this.x = x;
        this.y = y;
        this.taskName = taskName;
    }

    public TaskPoint() {
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
