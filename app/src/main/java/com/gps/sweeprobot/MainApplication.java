package com.gps.sweeprobot;

import android.app.Application;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class MainApplication extends Application {

    private static MainApplication app;
    // 服务端ip
    public static String ip = "";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MainApplication getContext() {
        return app;
    }

}
