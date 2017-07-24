package com.gps.sweeprobot.model.createmap.model;

import com.gps.sweeprobot.http.Constant;
import com.gps.sweeprobot.mvp.IModel;

import jrosbridge.Ros;
import jrosbridge.Service;
import jrosbridge.services.ServiceRequest;
import jrosbridge.services.ServiceResponse;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/21 0021
 * @Descriptiong : 基于ROS的操作
 * @see jrosbridge.Ros
 */

public class RosModel implements IModel {

    private Ros ros;

    public RosModel() {
        ros = new Ros(Constant.JiaoJian);
        ros.connect();
    }

    public String send(){

        reConnect();
        Service addTwoInts = new Service(ros, "/add_two_ints", "rospy_tutorials/AddTwoInts");
        ServiceRequest request = new ServiceRequest("{\"a\": 30, \"b\": 20}");
        ServiceResponse response = addTwoInts.callServiceAndWait(request);

        return response.toString();
    }

    /**
     *  重连
     */
    private void reConnect(){

        if (!ros.isConnected()){
            ros.connect();
        }

    }

}
