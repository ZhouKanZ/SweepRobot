package com.gps.sweeprobot.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/9/1 0001
 * @Descriptiong : xxx
 */

public class FitParams {

//    int32 map_id
//    string map_name
//    int32 nav_flag
//    basic_msgs/gridpose gridpose
//  float64 x
//  float64 y
//  float64 angle

    private int map_id;
    private String map_name;
    private int nav_flag;  // 1 表示做出了改变
//    private int task_id;
    private Gridpose gridpose;

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

    public int getNav_flag() {
        return nav_flag;
    }

    public void setNav_flag(int nav_flag) {
        this.nav_flag = nav_flag;
    }

    public Gridpose getGridpose() {
        return gridpose;
    }

    public void setGridpose(Gridpose gridpose) {
        this.gridpose = gridpose;
    }

//    public int getTask_id() {
//        return task_id;
//    }
//
//    public void setTask_id(int task_id) {
//        this.task_id = task_id;
//    }

    public static class Gridpose {
        float x;
        float y;
        float angle;

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

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }
    }
}
