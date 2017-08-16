package com.gps.sweeprobot.model.mapmanager.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.url.UrlHelper;
import com.gps.sweeprobot.utils.LogManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Create by WangJun on 2017/7/17
 * 主要做一些数据处理呀,网路请求呀
 */

public class MapManagerModel implements IModel,MapListItem.RequestMapListener{


    /**
     * 网络请求图片
     * @param gpsMapBean
     */
    public void requestMapBitmap(GpsMapBean gpsMapBean, final MapInfo mapInfo){

        LogManager.i(UrlHelper.BASE_URL + gpsMapBean.getCompletedMapUrl());

        Http.getHttpService()
                .getMapList(UrlHelper.BASE_URL + gpsMapBean.getCompletedMapUrl())
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        Bitmap map = BitmapFactory.decodeStream(responseBody.byteStream());
                        return map;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {

                        mapInfo.successInfo(bitmap);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        mapInfo.failInfo(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     *
     * @param infoHint
     */
    public void requestMapListData(final MapInfoModel.InfoHint infoHint) {


    }

    /**
     *  从数据库获取地图数据
     * @param infoHint
     */
    public void getMapsFromDatabase(final InfoHint infoHint){

        Observable.just(DataSupport.findAll(GpsMapBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GpsMapBean>>() {
                    @Override
                    public void accept(@NonNull List<GpsMapBean> gpsMapBeen) throws Exception {

                        infoHint.successInfo(gpsMapBeen);
                    }
                });
    }

    @Override
    public void requestMap(GpsMapBean gpsMapBean,MapInfo mapInfo) {
        requestMapBitmap(gpsMapBean,mapInfo);
    }

    public interface InfoHint{

        void successInfo(List<GpsMapBean> maps);

        void failInfo();
    }

    public interface MapInfo{

        void successInfo(Bitmap bitmap);
        void failInfo(Throwable e);
    }
}
