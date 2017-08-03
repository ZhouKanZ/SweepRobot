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
     *  将角度个长度转成速度
     * @param angle
     * @param length
     * @return
     */
    public static JSONObject convertToRosSpeed(double angle, float length) {


        Log.d(TAG, "convertToRosSpeed: " + "angle :" + angle + ",length :" + length);

        JSONObject msg = new JSONObject();
        JSONObject linear = new JSONObject();
        linear.put("x", -length * Math.sin(convertAngleToRadians(angle)) * 0.5);
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
    public static double convertAngleToRadians(double angle) {
        return angle * (2 * (Math.PI / 360));
    }

}
