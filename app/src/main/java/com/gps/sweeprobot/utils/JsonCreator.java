package com.gps.sweeprobot.utils;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/3 0003
 * @Descriptiong : Json 构造器
 */

public class JsonCreator {

    private static final String TAG = "JsonCreator";

    /**
     *  ros的最大速度
     */
    private static final double MAX_VELOCITY = 0.5;

    /**
     *  将角度个长度转成速度
     * @param angle
     * @param length
     * @return
     */
    public static JSONObject convertToRosSpeed(double angle, float length) {

        Log.d(TAG, "convertToRosSpeed: " + "angle :" + angle + ",length :" + length);

        JSONObject msg = new JSONObject();
        JSONObject linear = new JSONObject();
        linear.put("x", -length * Math.sin(convertAngleToRadians(angle)) * MAX_VELOCITY);
        linear.put("y", 0);
        linear.put("z", 0);
        JSONObject angular = new JSONObject();
        angular.put("x", 0);
        angular.put("y", 0);
        angular.put("z", -length * (Math.cos(convertAngleToRadians(angle))));

        if ((0 < angle && angle <= 30) || (150 < angle && angle <= 210) || (330 < angle && angle <= 360)) {
            linear.put("x", 0);
        }

        if ((60 < angle && angle <= 120) || (240 < angle && angle <= 300)) {
            angular.put("z", 0);
        }

        msg.put("linear", linear);
        msg.put("angular", angular);
        JSONObject publish = new JSONObject();
        publish.put("op", "publish");
        publish.put("topic", "/cmd_vel");
        publish.put("msg", msg);

        Log.d(TAG, "convertToRosSpeed: " + publish.toString());

        return publish;
    }

    /**
     * 将角度转换成弧度
     *
     * @param angle
     * @return
     */
    private static double convertAngleToRadians(double angle) {
        return angle * (2 * (Math.PI / 360));
    }

    /**
     *
     * @param   type 0 开始  1暂停  2 结束
     * @return  返回
     */
    public static JSONObject mappingStatus(int type){
        /* 0表示开始  1 暂停 2 结束*/
        JSONObject msg = new JSONObject();
        msg.put("data",type);

        JSONObject mappingStatus = new JSONObject();
        mappingStatus.put("op","call_service");
        mappingStatus.put("service","/mapping_status");
        mappingStatus.put("args",msg);

        return mappingStatus;
    }

    /**
     * 给机器人发送地图相关的信息
     * @param id    地图id
     * @param name  地图的名字
     * @return
     */
    public static JSONObject postMapInfo(int id,String name){

       /* 0表示开始  1 暂停 2 结束*/
        JSONObject msg = new JSONObject();
        msg.put("id",id);
        msg.put("name",name);

        JSONObject mappingStatus = new JSONObject();
        mappingStatus.put("op","call_service");
        mappingStatus.put("service","/post_mapinfo");
        mappingStatus.put("args",msg);

        return msg;
    }

}
