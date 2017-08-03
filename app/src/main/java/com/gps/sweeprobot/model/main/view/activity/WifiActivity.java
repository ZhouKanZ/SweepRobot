package com.gps.sweeprobot.model.main.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.brocastreceiver.WifiBrocastReceiver;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by WangJun on 2017/7/13
 */

public class WifiActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener,
        WifiBrocastReceiver.WifiStateChangeListener {

    private static final String TAG = "WifiActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_wifi)
    RecyclerView rvWifi;
    @BindView(R.id.layout_swipe)
    SwipeRefreshLayout layoutSwipe;

    private TextView tvSsid;
    private EditText etPwd;
    private Button btnConn;

    private List<ScanResult> scanResults;
    private WifiManager mWifiManager;
    private CommonAdapter<ScanResult> adapter;
    private WifiInfo wifiifo;
    private WifiConfiguration wifiConfig;
    private Dialog dialog;

    private WifiBrocastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    int[] resIds = {R.mipmap.ic_setting_wifi_1,
            R.mipmap.ic_setting_wifi_2,
            R.mipmap.ic_setting_wifi_3,
            R.mipmap.ic_setting_wifi_4
    };

    // 存放ssid的集合
    List<String> ssids = new ArrayList<>();

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "选择机器人WI-FI";
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        setLeftVisiable(true);

        initWifiData();
        initDialog();

        mReceiver = new WifiBrocastReceiver();
        mReceiver.setWifiStateChangeListener(this);


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);


        wifiConfig = new WifiConfiguration();
        layoutSwipe.setOnRefreshListener(this);
    }

    @Override
    public ImageView getLeftImageView() {
        return ivBack;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_wifi;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void initDialog() {
        dialog = new Dialog(this);
        RelativeLayout re = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.dialog, null);

        tvSsid = (TextView) re.findViewById(R.id.tv_ssid);
        etPwd = (EditText) re.findViewById(R.id.et_pwd);
        btnConn = (Button) re.findViewById(R.id.btn_conn);
        btnConn.setOnClickListener(this);

        dialog.setContentView(re);
    }

    private void initWifiData() {

        layoutSwipe.setRefreshing(false);

        scanResults = new ArrayList<>();
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);


        /**
         *  踢出相同的ssid
         */
        scanResults.clear();
        List<ScanResult> results = mWifiManager.getScanResults();
        for (ScanResult result : results) {
            List<String> tempSsids = getSSIDs(scanResults);
            if (!hasSSID(tempSsids, result.SSID)) {
                scanResults.add(result);
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            initAdapter();
        }

    }


    private void initAdapter() {


        wifiifo = mWifiManager.getConnectionInfo();
        adapter = new CommonAdapter<ScanResult>(this, R.layout.item_wifi, scanResults) {

            @Override
            protected void convert(ViewHolder holder, final ScanResult scanResult, int position) {

                holder.setText(R.id.tv_wifi_name, scanResult.SSID);
                Log.d(TAG, "convert: origin" + scanResult.level);
                int level = WifiManager.calculateSignalLevel(scanResult.level, 4);
                Log.d(TAG, "convert: translated" + level);
                int resId = resIds[level];
                holder.setImageBitmap(R.id.iv_wifi_signal, BitmapFactory.decodeResource(getResources(), resId));

                Log.i(TAG, "convert111: " + wifiifo.getSSID() + "ssid : " + scanResult.SSID);
                /**
                 *  表明已经连接
                 */
                if (wifiifo != null && wifiifo.getSSID().equals('"' + scanResult.SSID + '"')) {
                    holder.setText(R.id.tv_conn, "已连接");
                } else {
                    holder.setText(R.id.tv_conn, "");
                }

                holder.setOnClickListener(R.id.item_wifi, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 弹出输入框 -- 属于异步操作
                        showDialog(scanResult.SSID);
                        // 需要用progressBar
                        // ssid + pwd
                    }
                });
            }
        };

        rvWifi.setLayoutManager(new LinearLayoutManager(this));
        rvWifi.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        // 得到更多的wifi
        // 加载更多
        initWifiData();
    }

    private void showDialog(String ssid) {
        tvSsid.setText(ssid);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        dialog.dismiss();

        String ssid = (String) tvSsid.getText();
        String pwd = etPwd.getEditableText().toString();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssid + "\"";

        // WEP
//        conf.wepKeys[0] = "\"" + pwd + "\"";
//        conf.wepTxKeyIndex = 0;
//        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        // WPA
        conf.preSharedKey = "\"" + pwd + "\"";

        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        mWifiManager.addNetwork(conf);

        List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                mWifiManager.disconnect();
                boolean isConn = mWifiManager.enableNetwork(i.networkId, true);
                Log.d(TAG, "onViewClicked: " + isConn);
                mWifiManager.reconnect();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onLinkSuccess() {
        wifiifo = mWifiManager.getConnectionInfo();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLinkError() {

    }

    /**
     * 得到 xx
     *
     * @param scans
     * @return
     */
    private List<String> getSSIDs(List<ScanResult> scans) {

        ssids.clear();

        for (ScanResult res :
                scans) {
            ssids.add(res.SSID);
        }

        return ssids;
    }

    /**
     * 判断是否存在
     *
     * @param temps
     * @param ssid
     * @return
     */
    private boolean hasSSID(List<String> temps, String ssid) {
        for (String str : temps) {
            if (str.equals(ssid)) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        this.finish();
    }
}
