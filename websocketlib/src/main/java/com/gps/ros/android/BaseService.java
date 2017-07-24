package com.gps.ros.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/11 0011
 * @Descriptiong : xxx
 */

public abstract class BaseService extends Service{

    private static Intent intent;

    /**
     *  开启服务
     * @param ctz
     * @param bundle
     * @param clz
     * @return
     */
    public static Intent startSelf(Context ctz,Bundle bundle,Class clz) {

        Intent i = new Intent(ctz,clz);
        i.putExtra(clz.getSimpleName(),bundle);
        ctz.startService(i);
        intent = i;
        return i;

    }

    /**
     *  停止服务
     * @param ctz
     * @param serviceIntent
     */
    public static void stopService(Context ctz,Intent serviceIntent){
        ctz.stopService(serviceIntent);
    }

    /**
     *  返回Bundle
     * @param clz
     * @return
     */
    static Bundle getBundle(Class clz){
        if (intent != null)
            return intent.getBundleExtra(clz.getSimpleName());
        return null;
    }

}
