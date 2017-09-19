package com.gps.sweeprobot.bean;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/31 0031
 * @Descriptiong : xxx
 */

public class Task {

//    int32 map_id
//    string map_name
//    int32 type 1 删除 2 添加 3更新
//    int32[] nav_id
//    int32 task_id

    private int map_id;
    private String map_name;
    private int type;
    private List<Integer> nav_id;
    private int task_id;

    public Task(int map_id, String map_name, int type, List<Integer> nav_id, int task_id) {
        this.map_id = map_id;
        this.map_name = map_name;
        this.type = type;
        this.nav_id = nav_id;
        this.task_id = task_id;
    }

    public Task() {
    }



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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getNav_id() {
        return nav_id;
    }

    public void setNav_id(List<Integer> nav_id) {
        this.nav_id = nav_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
