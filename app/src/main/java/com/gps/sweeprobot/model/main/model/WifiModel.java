package com.gps.sweeprobot.model.main.model;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.model.main.bean.NetworkEntity;
import com.gps.sweeprobot.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/7/13
 */

public class WifiModel implements IModel{

    private List<NetworkEntity> netList;

    public void scanWifiConf(InfoHint infoHint){

        WifiManager wifiManager = (WifiManager) MainApplication.
                getContext().getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> scanResults = wifiManager.getScanResults();

        netList=new ArrayList<>();

        for (ScanResult result : scanResults) {

            netList.add(new NetworkEntity(result.SSID,result.level));
        }

        infoHint.successInfo(netList);

    }

    //通过接口把信息回调到presenter层
    public interface InfoHint{

        void successInfo(List<NetworkEntity> data);

        void failInfo(String str);
    }
}
