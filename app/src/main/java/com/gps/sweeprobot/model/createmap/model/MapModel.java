package com.gps.sweeprobot.model.createmap.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSONObject;
import com.gps.sweeprobot.bean.GpsMap;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.createmap.contract.CreateMapContract;
import com.gps.sweeprobot.utils.JsonCreator;

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

    private Disposable dispose;

    /* 图片的刷新频率 */
    private final static int INTERVAL = 1;

    public MapModel() {
    }

    /**
     * 扫描地图 http
     */
    @Override
    public Disposable startScan(Consumer<Bitmap> consumer) {

        dispose = Observable
                .interval(2, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(@NonNull Long aLong) throws Exception {
                        return Http.getHttpService().downImage();
                    }
                })
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        Bitmap bitmap=BitmapFactory.decodeStream(responseBody.byteStream());
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
        if (null != dispose){
            dispose.dispose();
        }
    }

    @Override
    public void saveMap(GpsMap gpsMap) {

    }

    @Override
    public void sendVelocityToRos(double angel, float length) {

        JSONObject velocity = JsonCreator.convertToRosSpeed(angel,length);
        WebSocketHelper.send(velocity.toJSONString());

    }

    @Override
    public void subscribe() {
//        Advertise advertise = new Advertise();
//        advertise.topic = "/cmd_vel";
//        WebSocketHelper.send(advertise);
    }


}
