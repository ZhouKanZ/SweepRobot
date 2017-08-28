package com.gps.sweeprobot.model.main.presenter;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.gps.ros.rosbridge.ROSBridgeClient;
import com.gps.ros.rosbridge.ROSClient;
import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.model.main.model.IpModel;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */

public class IpPresenter extends BasePresenter<IpContract.View> implements IpContract.Presenter {


    private static final String ip_model_key = "ip_model_key";
    private Handler mHandler = new Handler();

    private ROSBridgeClient client;

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new IpModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String,IModel> modelMap = new HashMap<>();
        modelMap.put(ip_model_key,models[0]);
        return modelMap;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {
        return null;
    }

    @Override
    public void setData() {
    }

    @Override
    public void connectToRos(){

        String ip = iView.getIpText();
        if (TextUtils.isEmpty(ip)){
            iView.showToast("请设置ip");
            return;
        }

        iView.showProgress();

        client = ((IpModel)getiModelMap().get(ip_model_key)).connect(ip, new ROSClient.ConnectionStatusListener() {
            @Override
            public void onConnect() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainApplication.getContext().setRosBridgeClient(client);
                        iView.hideProgress();
                        iView.startAct();
                    }
                });

            }

            @Override
            public void onDisconnect(boolean normal, String reason, int code) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.hideProgress();
                    }
                });

            }

            @Override
            public void onError(Exception ex) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.hideProgress();
                        iView.showDialog();
                    }
                });

                LogManager.i(ex.getMessage()+"connect fail");

            }
        });

    }

}
