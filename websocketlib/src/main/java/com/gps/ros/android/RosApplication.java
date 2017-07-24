package com.gps.ros.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/11 0011
 * @Descriptiong : xxx
 */
public class RosApplication extends Application {

    private Intent serviceIntent;

    public RosApplication() {
        
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ROS", "onCreate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        stopService();
    }

    public void startService(Context ctz,Bundle bundle, Class clz){
        serviceIntent =  RosService.startSelf(ctz,bundle,clz);
    }

    public void stopService(){
        if (serviceIntent != null){
            BaseService.stopService(this,serviceIntent);
        }
    }

}
