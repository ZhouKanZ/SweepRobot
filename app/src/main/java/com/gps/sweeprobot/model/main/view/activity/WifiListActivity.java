package com.gps.sweeprobot.model.main.view.activity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.utils.WifiAdmin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/31 0031
 * @Descriptiong : 自定义wifi连接界面
 */

public class WifiListActivity extends BaseActivity {


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

    private ArrayList<WifiInfo> wifiArray;
//    private WiFiInfoAdapter wifiInfoAdapter;

    private ProgressBar updateProgress;
    private String wifiPassword = null;

    private WifiManager wifiManager;
    private WifiAdmin wiFiAdmin;
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private StringBuffer sb = new StringBuffer();

    final Handler refreshWifiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                  case 1:
                    getAllNetWorkList();
                    break;

                default:
                    break;
            }
        }
    };

    public class refreshWifiThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                Message message = new Message();
                message.what = 1;
                refreshWifiHandler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "Wifi连接";
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        wiFiAdmin = new WifiAdmin(WifiListActivity.this);

        getAllNetWorkList();
    }

    private void getAllNetWorkList() {

        wifiArray = new ArrayList<WifiInfo>();
        if (sb != null) {
            sb = new StringBuffer();
        }
        wiFiAdmin.startScan();
        list = wiFiAdmin.getWifiList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                mScanResult = list.get(i);
            }

            wiFiAdmin.getConfiguration();

        }
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

}
