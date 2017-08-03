package com.gps.sweeprobot;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.gps.ros.android.RosService;
import com.gps.ros.rosbridge.ROSBridgeClient;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class MainApplication extends LitePalApplication {

    private static final String TAG = "application";
    private static MainApplication app;
    private Intent serviceIntent;
    public ROSBridgeClient rosBridgeClient;

    private ServiceConnection conn;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        /* 初始化LitePal数据库 */
        LitePal.initialize(this);
//        startService("");
    }

    public static MainApplication getContext() {
        return app;
    }


    public String getThreadName() {
        return "name=================" + Thread.currentThread().getName();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService();
    }

    public void startService(String url , ServiceConnection conn) {

        this.conn = conn;

        Bundle bundle = new Bundle();
        bundle.putString(RosService.ROS_URI_KEY, url);
        Intent i = new Intent(this, RosService.class);
        i.putExtra("bundle", bundle);
        bindService(i, conn, Context.BIND_AUTO_CREATE);

    }

    public void stopService() {
        unbindService(conn);
    }

    public ROSBridgeClient getRosBridgeClient() {
        return rosBridgeClient;
    }

    public void setRosBridgeClient(ROSBridgeClient rosBridgeClient) {
        this.rosBridgeClient = rosBridgeClient;
    }
}
