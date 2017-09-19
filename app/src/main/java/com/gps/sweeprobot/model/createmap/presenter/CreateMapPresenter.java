package com.gps.sweeprobot.model.createmap.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.ros.response.LaserPose;
import com.gps.ros.response.PicturePose;
import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.model.MapModel;
import com.gps.sweeprobot.model.createmap.model.RosResponseLisenter;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.JsonCreator;
import com.gps.sweeprobot.utils.RosProtrocol;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */
public class CreateMapPresenter extends BasePresenter<CreateMapContract.View> implements CreateMapContract.Presenter, RosResponseLisenter {

    private static String modelKey = "CreateMapPresenter";
    private Disposable disposable;
    private Disposable loopDispose;
    private MapModel model;

    public CreateMapPresenter() {
        model = ((MapModel) getiModelMap().get(modelKey));
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
        subscribe.type = RosProtrocol.NaviPosition.TYPE;
        WebSocketHelper.send(subscribe.toJSON());

    }

    @Override
    public void saveMap(GpsMapBean map) {
        model.sendMapInfoToRos(map);
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
        map.put(modelKey, models[0]);
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


    /**
     *  根据上面的订阅来用RxBus接收消息
     */
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
        iView.changeRobotPos(pose.getPosition().getX(), pose.getPosition().getY());
    }

    @Override
    public void onReceiVerLaserPose(List<LaserPose.DataBean> lasers) {
        iView.showLaserPoints(lasers);
    }

    @Override
    public void loopSendCommandToRos() {
        // 开始轮询给机器人发送指令
        loopDispose = Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                               @Override
                               public void accept(@NonNull Long aLong) throws Exception {
                                   model.sendVelocityToRos(iView.getVelocity()[0], (float) iView.getVelocity()[1]);
                               }
                           }
                );
    }

    @Override
    public void stopLoop() {
        loopDispose.dispose();
    }

    @Override
    public void cancel(GpsMapBean gpsbean) {
        model.cancel(gpsbean);
    }

    @Override
    public GpsMapBean getGpsMapBean(){
        return model.getGpsMapBean();
    }

    @Override
    public void clearId() {
        model.clearCurrentId();
    }

    @Override
    public void saveTempMap(GpsMapBean gpsbean) {
        model.saveTempMap(gpsbean);
    }

    @Override
    public void clearMap(GpsMapBean gpsMapBean) {
        model.clearCurrentMap(gpsMapBean);
    }


}
