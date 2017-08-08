package com.gps.sweeprobot.model.createmap.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.model.createmap.model.MapModel;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.RosProtrocol;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;


/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/14 0014
 * @Descriptiong : xxx
 */
public class CreateMapPresenter extends BasePresenter<CreateMapContract.View> implements CreateMapContract.Presenter {

    private static String modelKey = "CreateMapPresenter";
    private Disposable disposable;

    /**
     * 开启地图扫描
     */
    @Override
    public void startScanMap() {
        disposable =((MapModel)getiModelMap().get(modelKey)).startScan(getIView().getConsumer());
    }

    @Override
    public void stopScanMap() {
        ((MapModel)getiModelMap().get(modelKey)).stopScan(disposable);
    }

    @Override
    public void sendCommandToRos() {
        ((MapModel)getiModelMap().get(modelKey)).sendVelocityToRos(
                iView.getVelocity()[0],
                (float) iView.getVelocity()[1]);
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
        ((MapModel)getiModelMap().get(modelKey)).saveMap(null);
    }

    @Override
    public void subscribe() {
        ((MapModel)getiModelMap().get(modelKey)).subscribe();
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MapModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put(modelKey,models[0]);
        return map;
    }

    /* unused method */
    @Override
    public RecyclerView.Adapter initAdapter() {
        return null;
    }

    @Override
    public void setData() {}

}
