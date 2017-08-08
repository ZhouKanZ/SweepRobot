package com.gps.sweeprobot.http;

import com.gps.ros.rosbridge.ROSBridgeClient;
import com.gps.ros.rosbridge.operation.Operation;
import com.gps.sweeprobot.MainApplication;

import org.java_websocket.client.WebSocketClient;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/2 0002
 * @Descriptiong : xxx
 */

public class WebSocketHelper {

    /**
     *  发送
     * @param operation
     */
    public static void send(Operation operation){
        rosBridgeClient().send(operation);
    }

    /**
     *  发送
     * @param publisher
     */
    public static void send(String publisher){
        getWebsocket().send(publisher);
    }

    /**
     *  得到rosbridgeclient
     * @return
     */
    private static ROSBridgeClient rosBridgeClient(){
        return MainApplication.getContext().getRosBridgeClient();
    }

    /**
     *  得到websocket
     * @return
     */
    private static WebSocketClient getWebsocket(){
        return rosBridgeClient().getClient();
    }

}
