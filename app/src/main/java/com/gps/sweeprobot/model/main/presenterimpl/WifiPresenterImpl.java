package com.gps.sweeprobot.model.main.presenterimpl;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.model.main.adapter.item.WifiItem;
import com.gps.sweeprobot.model.main.bean.NetworkEntity;
import com.gps.sweeprobot.model.main.model.WifiModel;
import com.gps.sweeprobot.model.main.presenter.WifiPresenter;
import com.gps.sweeprobot.model.main.utils.WifiHelper;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/13
 */

public class WifiPresenterImpl extends WifiPresenter {

    private CommonRcvAdapter<NetworkEntity> mAdapter;

    private List<NetworkEntity> wifiData;

    private WifiItem.MOnItemClickListener listener;

    private WifiManager wifiManager;

    public WifiPresenterImpl(WifiItem.MOnItemClickListener listener) {
        this.listener = listener;
        wifiData = new ArrayList<>();

    }

    @Override
    public RecyclerView.Adapter initAdapter() {


        mAdapter = new CommonRcvAdapter<NetworkEntity>(wifiData) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new WifiItem(listener);
            }

        };

        return mAdapter;
    }

    @Override
    public void setData() {

        ((WifiModel) getiModelMap().get("wifi")).scanWifiConf(new WifiModel.InfoHint() {
            @Override
            public void successInfo(List<NetworkEntity> data) {
                wifiData.addAll(data);
            }

            @Override
            public void failInfo(String str) {

            }
        });

    }

    @Override
    public HashMap<String, IModel> getiModelMap() {

        return loadModelMap(new WifiModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String, IModel> map = new HashMap<>();
        map.put("wifi", models[0]);
        return map;
    }

    @Override
    public void refreshListener() {

        wifiData.clear();
        setData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean connectWifi(final int position, final String password) {

        wifiManager = (WifiManager) MainApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wificonf =
                WifiHelper.createWificonf(wifiManager, wifiData.get(position).getName(), password, 3, "wifi");

        return WifiHelper.connectWifi(wifiManager, wificonf);

    }
}
