package com.gps.ros.android;

import android.content.Intent;
import android.content.ServiceConnection;
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
 * @Author :        zhoukan
 * @CreateDate :    2017/7/11 0011
 * @Descriptiong :  用来与Ros通信的组件 跟随Application的生命周期
 */

public class RosService extends  BaseService {

    private static final String TAG = "RosService";

    /**
     *  表示服务是否启动
     */
    public static boolean isServiceBind = false;

    // ros的key
    public static final String ROS_URI_KEY = "ros_uri_key";

    private final IBinder binder = new MyBinder();

    private static MyWebSocket rosBridgeClient = null;

    public class MyBinder extends Binder {
        public RosService getService(){
            return RosService.this;
        }
    }

    public static WebSocketClient getRosBridgeClient(){
        if (rosBridgeClient != null && !rosBridgeClient.isClosed()){
            return rosBridgeClient;
        }else {
           return null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " );
        if ( null != rosBridgeClient && !rosBridgeClient.isClosed()){
            rosBridgeClient.close();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");

        isServiceBind = true;

        Bundle bundle = intent.getBundleExtra("bundle");
        String  uriStr =  bundle.getString(ROS_URI_KEY);
        URI uri = null;
        try {
            uri = new URI("ws://" +uriStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "onStartCommand: " + e.toString());
        }
        rosBridgeClient = new MyWebSocket(uri);
        rosBridgeClient.connect();

        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        isServiceBind = false;
        if ( null != rosBridgeClient && !rosBridgeClient.isClosed()){
            rosBridgeClient.close();
        }

    }

    public void setWebsocketLifeCycle(MyWebSocket.WebsocketLifeCycle lifeCycle){
        rosBridgeClient.setLifeCycle(lifeCycle);
    }

}
