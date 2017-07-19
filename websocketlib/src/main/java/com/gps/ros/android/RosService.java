package com.gps.ros.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/11 0011
 * @Descriptiong : 用来与Ros通信的组件 跟随Application的生命周期
 */

public class RosService extends  BaseService {

    private static final String TAG = "RosService";
    // ros的key
    public static final String ROS_URI_KEY = "ros_uri_key";
    private static WebSocketClient rosBridgeClient = null;

    public static WebSocketClient getRosBridgeClient(){
        if (rosBridgeClient != null && !rosBridgeClient.isClosed()){
            return rosBridgeClient;
        }else {
            // ros websocket 未连接或者未进行初始化
            return null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    /**
     *  通过EventBus来向外发送
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Bundle bundle = getBundle(RosService.class);
        String  uriStr =  bundle.getString(ROS_URI_KEY);
        URI uri = null;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "onStartCommand: " + e.toString());
        }
        rosBridgeClient = new MyWebSocket(uri);
        rosBridgeClient.connect();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( null != rosBridgeClient && !rosBridgeClient.isClosed()){
            rosBridgeClient.close();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

}
