package com.gps.sweeprobot.model.main.model;

import com.gps.ros.rosbridge.ROSBridgeClient;
import com.gps.ros.rosbridge.ROSClient;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.mvp.IModel;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/2 0002
 * @Descriptiong : xxx
 */

public class IpModel implements IModel,IpContract.Model {

    ROSBridgeClient rosClient;

    @Override
    public ROSBridgeClient connect(String domain , final ROSClient.ConnectionStatusListener listener){

        rosClient = new ROSBridgeClient("ws://" + domain);

        new Thread(new Runnable() {
            @Override
            public void run() {
                rosClient.connect(listener);
            }
        }).start();

        return rosClient;
    }

}