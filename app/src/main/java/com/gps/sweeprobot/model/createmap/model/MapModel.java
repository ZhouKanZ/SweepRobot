package com.gps.sweeprobot.model.createmap.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gps.ros.android.RxBus;
import com.gps.ros.response.LaserPose;
import com.gps.ros.response.PicturePose;
import com.gps.ros.response.SubscribeResponse;
import com.gps.ros.rosbridge.operation.Advertise;
import com.gps.ros.rosbridge.operation.Subscribe;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.utils.JsonCreator;
import com.gps.sweeprobot.utils.RosProtrocol;

import java.util.List;
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
    private Bitmap bitmap;

    /* 图片的刷新频率 */
    private final static int INTERVAL = 2;

    public MapModel() {
    }

    /**
     * 扫描地图 http
     */
    @Override
    public Disposable startScan(Consumer<Bitmap> consumer) {

        askStart();

        if (dispose == null || dispose.isDisposed()){
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
                            bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                            return bitmap;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);
        }
        return dispose;
    }

    @Override
    public void stopScan(Disposable dispose) {
        if (dispose != null && !dispose.isDisposed()) {
            dispose.dispose();
        }
        askStop();
    }


    /**
     * 控制机器人行走
     *
     * @param angel  角度
     * @param length 长度
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
        subscribe.topic = RosProtrocol.PicturePose.TOPIC;
        subscribe.type = RosProtrocol.PicturePose.TYPE;
        WebSocketHelper.send(subscribe);

        /* 创建机器人可控制 advertise  */
        Advertise advertise = new Advertise();
        advertise.topic = RosProtrocol.Speed.TOPIC;
        advertise.type = RosProtrocol.Speed.TYPE;
        WebSocketHelper.send(advertise);

        /*  订阅机器人激光点 */
        Subscribe laserPose = new Subscribe();
        laserPose.topic = RosProtrocol.LaserPose.TOPIC;
        laserPose.type = RosProtrocol.LaserPose.TYPE;
        WebSocketHelper.send(laserPose);
    }

    @Override
    public void sendMapInfoToRos(GpsMapBean gpsMapBean) {

//        WebSocketHelper.send(
//                JsonCreator
//                .postMapInfo(gpsMapBean.getId(),
//                        gpsMapBean.getName())
//                .toJSONString());
//
        gpsMapBean.save();
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
        WebSocketHelper.send(JsonCreator.mappingStatus(1).toJSONString());
    }


    @Override
    public void registerRxBus(Consumer<JSONObject> consumer) {
        // do nothing
    }

    @Override
    public void registerRxBus() {
        rxBusDispose = RxBus.getDefault()
                .toObservable(JSONObject.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(@NonNull JSONObject jsonObject) throws Exception {

                        if (null == rosLisenter) {
                            return;
                        }

                        String op = jsonObject.getString("op");
                        String topic = jsonObject.getString("topic");
                        switch (op) {
                            case "publish":
                                postMsgByTopic(topic, jsonObject);
                                break;
                            case "service_response":
                                postMsgByService();
                                break;
                        }
                    }

                    
                });
    }

    /**
     *  根据接收到的消息来做消息分发和ui上的处理
     */
    private void postMsgByService() {
    }

    @Override
    public void unregisterRxBus() {

        if (null != rxBusDispose) {
            rxBusDispose.dispose();
        }
    }

    public void setRosLisenter(RosResponseLisenter rosLisenter) {
        this.rosLisenter = rosLisenter;
    }

    /**
     * 根据topic的值  以不同的class来post出去
     *
     * @param topic
     */
    private void postMsgByTopic(String topic, JSONObject jsonObject) {
        switch (topic) {
            case RosProtrocol.PicturePose.TOPIC:
                SubscribeResponse<PicturePose> picturePose = JSON.parseObject(jsonObject.toJSONString(),
                        new TypeReference<SubscribeResponse<PicturePose>>(){});
                rosLisenter.OnReceiverPicture(picturePose.getMsg());

                break;
            case RosProtrocol.LaserPose.TOPIC:

                SubscribeResponse<LaserPose> laserPose = JSON.parseObject(jsonObject.toJSONString(),
                        new TypeReference<SubscribeResponse<LaserPose>>(){});
                List<LaserPose.DataBean> lasers = laserPose.getMsg().getData();
                rosLisenter.onReceiVerLaserPose(lasers);

                break;
        }
    }
}
