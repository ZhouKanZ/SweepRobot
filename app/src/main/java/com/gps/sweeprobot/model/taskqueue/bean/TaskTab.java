package com.gps.sweeprobot.model.taskqueue.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/18 0018
 * @Descriptiong : 任务标签 如 ： 标记点 路径 覆盖物 等等
 */

public class TaskTab {

    // tab的名称
    private String tab_name;

    public TaskTab(String tab_name) {
        this.tab_name = tab_name;
    }


    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }
}
