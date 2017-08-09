package com.gps.sweeprobot.model.createmap.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.ros.response.LaserPose;
import com.gps.ros.response.PicturePose;
import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.model.MapModel;
import com.gps.sweeprobot.model.createmap.model.RosResponseLisenter;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.RosProtrocol;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */
public class CreateMapPresenter extends BasePresenter<CreateMapContract.View> implements CreateMapContract.Presenter ,RosResponseLisenter {

    private static String modelKey = "CreateMapPresenter";
    private Disposable disposable;
    private MapModel model;

    public CreateMapPresenter() {
        model =  ((MapModel)getiModelMap().get(modelKey));
    }

    /**
     * 开启地图扫描
     */
    @Override
    public void startScanMap() {
        disposable = model.startScan(getIView().getConsumer());
    }

    @Override
    public void stopScanMap() {
        model.stopScan(disposable);
    }

    @Override
    public void sendCommandToRos() {
        model.sendVelocityToRos(iView.getVelocity()[0], (float) iView.getVelocity()[1]);
    }

    @Override
    public void getRosPosition() {
        /**
         *  subscribe
         */
        Subscribe subscribe = new Subscribe();
        subscribe.topic = RosProtrocol.NaviPosition.TOPIC;
        subscribe.type  = RosProtrocol.NaviPosition.TYPE;
        WebSocketHelper.send(subscribe.toJSON());

    }

    @Override
    public void saveMap(GpsMap map) {
        model.saveMap(null);
    }

    @Override
    public void subscribe() {
        model.subscribe();
    }

    @Override
    public void finishScanMap() {
        iView.showIutInfoDialog();
    }

    /* --------- 这两个方法之后必须得删掉 --------- */
    @Override
    public HashMap<String, IModel> getiModelMap() {
        model = new MapModel();
        model.setRosLisenter(this);
        return loadModelMap(model);
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put(modelKey,models[0]);
        return map;
    }

    /* --------- 这两个方法之后必须得删掉 --------- */

    /* unused method */
    @Override
    public RecyclerView.Adapter initAdapter() {
        return null;
    }

    @Override
    public void setData() {
    }


    @Override
    public void registerRxBus() {
        model.registerRxBus();
    }

    @Override
    public void unregisterRxBus() {
        model.unregisterRxBus();
    }


    /* rxBus 传递过来的数据 */
    @Override
    public void OnReceiverPicture(PicturePose pose) {
        iView.changeRobotPos(pose.getPosition().getX(),pose.getPosition().getY());
    }

    @Override
    public void onReceiVerLaserPose(List<LaserPose.DataBean> lasers) {
        iView.showLaserPoints(lasers);
    }
}
