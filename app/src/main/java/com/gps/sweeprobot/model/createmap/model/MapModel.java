package com.gps.sweeprobot.model.createmap.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.gps.ros.android.RxBus;
import com.gps.ros.rosbridge.operation.Advertise;
import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.utils.JsonCreator;
import com.gps.sweeprobot.utils.RosProtrocol;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/21 0021
 * @Descriptiong : 地图的model
 */

public class MapModel implements CreateMapContract.Model {


    private static final String TAG = "MapModel";
    private Disposable dispose;
    private Disposable rxBusDispose;
    private RosResponseLisenter rosLisenter;

    /* 图片的刷新频率 */
    private final static int INTERVAL = 2;

    public MapModel() {}

    /**
     * 扫描地图 http
     */
    @Override
    public Disposable startScan(Consumer<Bitmap> consumer) {

        askStart();

        /**
         *  rxjava 实现轮询
         */
        dispose = Observable
                .interval(INTERVAL, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(@NonNull Long aLong) throws Exception {
                        return Http.getHttpService().downImage();
                    }
                })
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

        return dispose;
    }

    @Override
    public void stopScan(Disposable dispose) {
        if (null != dispose) {
            dispose.dispose();
        }
        askStop();
    }

    @Override
    public void saveMap(GpsMap gpsMap) {

    }

    /**
     *  控制机器人行走
     * @param angel    角度
     * @param length   长度
     */
    @Override
    public void sendVelocityToRos(double angel, float length) {

        JSONObject velocity = JsonCreator.convertToRosSpeed(angel, length);
        WebSocketHelper.send(velocity.toJSONString());

    }

    @Override
    public void subscribe() {

        /* 订阅机器人位置 */
        Subscribe subscribe = new Subscribe();
        subscribe.topic     = RosProtrocol.PicturePose.TOPIC;
        subscribe.type      = RosProtrocol.PicturePose.TYPE;
        WebSocketHelper.send(subscribe);

        /* 创建机器人可控制 advertise  */
        Advertise advertise = new Advertise();
        advertise.topic     = RosProtrocol.Speed.TOPIC;
        advertise.type      = RosProtrocol.Speed.TYPE;
        WebSocketHelper.send(advertise);

        /*  订阅机器人激光点 */
        Subscribe laserPose = new Subscribe();
        laserPose.topic     = RosProtrocol.LaserPose.TOPIC;
        laserPose.type      = RosProtrocol.LaserPose.TYPE;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic",RosProtrocol.LaserPose.TOPIC);
        jsonObject.put("type",RosProtrocol.LaserPose.TYPE);

        WebSocketHelper.send(laserPose);
    }

    /**
     * ask ros start
     */
    public void askStart() {
        WebSocketHelper.send(JsonCreator.mappingStatus(0).toJSONString());
    }

    /**
     * ask ros stop
     */
    public void askStop() {
        WebSocketHelper.send(JsonCreator.mappingStatus(2).toJSONString());
    }


    @Override
    public void registerRxBus(Consumer<JSONObject> consumer) {
        // do nothing
    }

    @Override
    public void registerRxBus() {
        rxBusDispose =  RxBus.getDefault()
                .toObservable(JSONObject.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(@NonNull JSONObject jsonObject) throws Exception {

                        Log.d(TAG, "accept: " + jsonObject);

                        /**
                         * 接收到service的数据     ( 开始 -- 结束 )
                         * 接收到subscribe的数据
                         */
                        if (null == rosLisenter){
                            return;
                        }

                        String op = jsonObject.getString("op");
                        String topic = jsonObject.getString("topic");
                        switch (op){
                            case "publish":

                                if (topic.equals("")){

                                }

                                break;
                            case "":
                                break;
                        }
                        // TODO: 2017/8/8 0008 机器人导航点
                    }
                });
    }

    @Override
    public void unregisterRxBus(){

        if (null != rxBusDispose){
            rxBusDispose.dispose();
        }
    }

    public void setRosLisenter(RosResponseLisenter rosLisenter) {
        this.rosLisenter = rosLisenter;
    }
}
