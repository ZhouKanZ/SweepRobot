package com.gps.sweeprobot.model.main.brocastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/1 0001
 * @Descriptiong : xxx
 */

public class WifiBrocastReceiver extends BroadcastReceiver {


    private static final String TAG = "WifiBrocastReceiver";

    private WifiStateChangeListener wifiStateChangeListener;

    public void setWifiStateChangeListener(WifiStateChangeListener wifiStateChangeListener) {
        this.wifiStateChangeListener = wifiStateChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);

        // 这个监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e(TAG, "wifiState" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    error();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    error();
                    break;
                //
            }
        }

        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                Log.e(TAG, "isConnected" + isConnected);
                if (isConnected) {
                    // 表示连接成功
                    success();
                } else {
                    // 表示连接中
                    error();
                }
            }
        }

//        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
//        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
//        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
//        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
//            ConnectivityManager manager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo gprs = manager
//                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            NetworkInfo wifi = manager
//                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            Log.i(TAG, "网络状态改变:" + wifi.isConnected() + " 3g:" + gprs.isConnected());
//            NetworkInfo info = intent
//                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//            if (info != null) {
//                Log.e(TAG, "info.getTypeName()" + info.getTypeName());
//                Log.e(TAG, "getSubtypeName()" + info.getSubtypeName());
//                Log.e(TAG, "getState()" + info.getState());
//                Log.e(TAG, "getDetailedState()"+ info.getDetailedState().name());
//                Log.e(TAG, "getDetailedState()" + info.getExtraInfo());
//                Log.e(TAG, "getType()" + info.getType());
//
//                if (NetworkInfo.State.CONNECTED == info.getState()) {
//                } else if (info.getType() == 1) {
//                    if (NetworkInfo.State.DISCONNECTING == info.getState()) {
//
//                    }
//                }
//            }
//        }
    }

    private void success(){
        if (wifiStateChangeListener != null){
            wifiStateChangeListener.onLinkSuccess();
        }
    }

    private void error(){
        if (wifiStateChangeListener != null){
            wifiStateChangeListener.onLinkError();
        }
    }

    public interface WifiStateChangeListener{

        void onLinkSuccess();

        void onLinkError();

    }

}
