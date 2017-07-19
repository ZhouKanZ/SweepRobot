package com.gps.sweeprobot.model.createmap.presenter;

import android.content.Context;

import com.gps.ros.android.RosService;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.view.activity.CreateActivity;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.mvp.IView;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */
public class CreateMapPresenter extends BasePresenter<CreateActivity> implements CreateMapContract.Presenter {

    private Context mContext;

    public CreateMapPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void attachView(IView iView) {
        super.attachView(iView);
        /* 执行RxBus注册 */

    }

    @Override
    public void detachView() {
        super.detachView();
        /* 执行RxBus解绑 */

    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    @Override
    public void startScanMap() {
        RosService.getRosBridgeClient().send("");
    }

    @Override
    public void stopScanMap() {
        RosService.getRosBridgeClient().send("");
    }

    @Override
    public void sendCommandToRos() {
        RosService.getRosBridgeClient().send("");
    }
}
