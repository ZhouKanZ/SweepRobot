package com.gps.sweeprobot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gps.ros.android.BaseService;
import com.gps.ros.android.RosService;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class MainApplication extends LitePalApplication {

    private Intent serviceIntent;

    private static MainApplication app;
    // 服务端ip
    public static String ip = "";

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
