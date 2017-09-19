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

    /* 是否在创建地图 */
    public static boolean iscreateMapping = true;

    /* 用来表示在一次生命周期中，进入CreateActivity的次数，以便控制ros的启动次数 */
    public static int startRos = 0;

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
