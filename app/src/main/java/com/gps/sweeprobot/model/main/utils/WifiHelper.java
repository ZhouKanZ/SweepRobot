package com.gps.sweeprobot.model.main.utils;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.Iterator;

/**
 * Created by admin on 2017/4/17.
 */

public class WifiHelper {

    /**
     * 配置wificonfiguration，通过conf去连接wifi
     * @param wifiManager
     * @param ssid
     * @param password
     * @param paramInt
     * @param wifiType
     * @return
     */
    public static WifiConfiguration createWificonf(WifiManager wifiManager,String ssid,String password
            ,int paramInt,String wifiType){

        WifiConfiguration config1=new WifiConfiguration();
        config1.allowedAuthAlgorithms.clear();
        config1.allowedGroupCiphers.clear();
        config1.allowedKeyManagement.clear();
        config1.allowedPairwiseCiphers.clear();
        config1.allowedProtocols.clear();

        if ("wifi".equals(wifiType)){
            config1.SSID=("\""+ssid+"\"");
            WifiConfiguration config2=isExists(wifiManager,ssid);

            if (config2!=null){
                if (wifiManager!=null){
                    wifiManager.removeNetwork(config2.networkId);
                }
            }

            if (paramInt==1){
                config1.wepKeys[0]="";
                config1.allowedKeyManagement.set(0);
                config1.wepTxKeyIndex=0;
                return config1;

            }
            else if (paramInt==1){
                config1.hiddenSSID=true;
                config1.wepKeys[0]=("\""+password+"\"");
                return config1;
            }
            else {
                config1.preSharedKey = ("\"" + password + "\"");
                config1.hiddenSSID = true;
                config1.allowedAuthAlgorithms.set(0);
                config1.allowedGroupCiphers.set(2);
                config1.allowedKeyManagement.set(1);
                config1.allowedPairwiseCiphers.set(1);
                config1.allowedGroupCiphers.set(3);
                config1.allowedPairwiseCiphers.set(2);
                return config1;
            }

        }else {
            WifiConfiguration wifiApConfig = new WifiConfiguration();
            wifiApConfig.allowedAuthAlgorithms.clear();
            wifiApConfig.allowedGroupCiphers.clear();
            wifiApConfig.allowedKeyManagement.clear();
            wifiApConfig.allowedPairwiseCiphers.clear();
            wifiApConfig.allowedProtocols.clear();

            wifiApConfig.SSID = ssid;

            if (paramInt == 1) // WIFICIPHER_NOPASS 不加密
            {
                wifiApConfig.wepKeys[0] = "";
                wifiApConfig.allowedKeyManagement
                        .set(WifiConfiguration.KeyMgmt.NONE);
                wifiApConfig.wepTxKeyIndex = 0;
                return wifiApConfig;
            }
            if (paramInt == 2) // WIFICIPHER_WEP WEP加密
            {
                wifiApConfig.hiddenSSID = true;
                wifiApConfig.wepKeys[0] = password;
                wifiApConfig.allowedAuthAlgorithms
                        .set(WifiConfiguration.AuthAlgorithm.SHARED);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.CCMP);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.TKIP);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.WEP40);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.WEP104);
                wifiApConfig.allowedKeyManagement
                        .set(WifiConfiguration.KeyMgmt.NONE);
                wifiApConfig.wepTxKeyIndex = 0;
                return wifiApConfig;
            }
            if (paramInt == 3) // WIFICIPHER_WPA wpa加密
            {
                wifiApConfig.preSharedKey = password;
                wifiApConfig.hiddenSSID = true;
                wifiApConfig.allowedAuthAlgorithms
                        .set(WifiConfiguration.AuthAlgorithm.OPEN);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.TKIP);
                wifiApConfig.allowedKeyManagement
                        .set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wifiApConfig.allowedPairwiseCiphers
                        .set(WifiConfiguration.PairwiseCipher.TKIP);
                // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wifiApConfig.allowedGroupCiphers
                        .set(WifiConfiguration.GroupCipher.CCMP);
                wifiApConfig.allowedPairwiseCiphers
                        .set(WifiConfiguration.PairwiseCipher.CCMP);
                wifiApConfig.status = WifiConfiguration.Status.ENABLED;
                return wifiApConfig;
            }
        }
        return null;

    }

    /**
     * 连接wifi
     * @param wifiManager
     * @param wifiConfiguration
     * @return
     */
    public static boolean connectWifi(WifiManager wifiManager,WifiConfiguration wifiConfiguration){

        int network = wifiManager.addNetwork(wifiConfiguration);

        if (network<0)
            return false;

        return wifiManager.enableNetwork(network,true);
    }

    public static WifiConfiguration isExists(WifiManager wifiManager,String ssid){
        Iterator<WifiConfiguration> iterator = wifiManager.getConfiguredNetworks().iterator();
        WifiConfiguration configuration=null;
        do {
            if (iterator.hasNext()){
                configuration=iterator.next();
            }
        }while(!configuration.SSID.equals("\""+ssid+"\""));

        return configuration;
    }
}
