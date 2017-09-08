package com.gps.sweeprobot.model.taskqueue.model;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/9/6 0006
 * @Descriptiong : xxx
 */

public class ExecuteTaskModel {
//    int32 map_id
//    string map_name
//    int32 task_id
//    int32 rate

    private int map_id;
    private String map_name;
    private int task_id;
    private int rate;
//    private int nav_flag;

    public ExecuteTaskModel(int map_id, String map_name, int task_id, int rate) {
        this.map_id = map_id;
        this.map_name = map_name;
        this.task_id = task_id;
        this.rate = rate;
    }

    public ExecuteTaskModel() {
    }

//    public int getNav_flag() {
//        return nav_flag;
//    }
//
//    public void setNav_flag(int nav_flag) {
//        this.nav_flag = nav_flag;
//    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public String getMap_name() {
        return map_name;
    }

    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
