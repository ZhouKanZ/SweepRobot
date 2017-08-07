package com.gps.ros.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
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

    public static WebSocketClient getRosBridgeClient() {
        if (rosBridgeClient != null && !rosBridgeClient.isClosed()){
            return rosBridgeClient;
        }else {
            return null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    public class RosBinder extends Binder{

        RosService getService(){return RosService.this;}
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

        Log.i(TAG, "onStartCommand: " + intent);
        URI uri = null;
        try {
            uri = new URI("ws://192.168.2.128:9090");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "onStartCommand: " + e.toString());
        }
        rosBridgeClient = new MyWebSocket(uri);
        rosBridgeClient.connect();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");

        return mBinder;
    }

    private final IBinder mBinder = new RosBinder();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( null != rosBridgeClient && !rosBridgeClient.isClosed()){
            rosBridgeClient.close();
        }
    }



}
