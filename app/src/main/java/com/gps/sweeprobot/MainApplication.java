package com.gps.sweeprobot;

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
    public ROSBridgeClient rosBridgeClient;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        /* 初始化LitePal数据库 */
        LitePal.initialize(this);
    }

    public static MainApplication getContext() {
        return app;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public ROSBridgeClient getRosBridgeClient() {
        return rosBridgeClient;
    }

    public void setRosBridgeClient(ROSBridgeClient rosBridgeClient) {
        this.rosBridgeClient = rosBridgeClient;
    }

    public String getThreadName() {
        return "name=================" + Thread.currentThread().getName();
    }
}
