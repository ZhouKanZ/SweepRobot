package com.gps.sweeprobot.model.createmap.presenter;

import android.content.Context;

import com.gps.ros.android.RosService;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.model.RosModel;
import com.gps.sweeprobot.model.createmap.view.activity.CreateActivity;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.mvp.IView;

import java.util.HashMap;

import jrosbridge.Ros;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */
public class CreateMapPresenter extends BasePresenter<CreateMapContract.View> implements CreateMapContract.Presenter {

    private Context mContext;

    private HashMap<String, IModel> modelHashMap;

    private RosModel rosModel;

    public CreateMapPresenter(Context mContext) {
        this.mContext = mContext;
        this.modelHashMap = new HashMap<>();
        this.rosModel = new RosModel();
    }

    @Override
    public void attachView(IView iView) {
        super.attachView(iView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    /**
     * 开启地图扫描
     */
    @Override
    public void startScanMap() {
        iView.showGetRobotsAnimation();

//        if (null != RosService.getRosBridgeClient() && !RosService.getRosBridgeClient().isClosed()){
//            RosService.getRosBridgeClient().send("");
//            Ros
//        }
        rosModel.send();

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
