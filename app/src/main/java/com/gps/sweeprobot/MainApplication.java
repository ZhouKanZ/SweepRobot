package com.gps.sweeprobot;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gps.ros.android.BaseService;
import com.gps.ros.android.RosService;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class MainApplication extends Application {

    private Intent serviceIntent;

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        stopService();
    }

    public void startService(Context ctz, Bundle bundle, Class clz){
        serviceIntent =  RosService.startSelf(ctz,bundle,clz);
    }

    public void stopService(){
        if (serviceIntent != null){
            BaseService.stopService(this,serviceIntent);
        }
    }


}
