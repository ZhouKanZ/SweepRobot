package com.gps.sweeprobot.model.mapmanager.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.mapmanager.service.CommonService;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;
import com.gps.sweeprobot.utils.LogUtils;
import com.gps.sweeprobot.utils.ToastManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create by WangJun on 2017/7/19
 * 获取地图集合的数据
 */

public class MapListModel implements IModel {

    /***
     * 获取地图图片
     * @param infoHint
     */
    public void downMapImg(final InfoHint infoHint) {

        Http.getHttpService()
                .downImage()
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {

                        Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //切断事件
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        infoHint.successInfo(bitmap);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastManager.showShort("have a error by request map data from server");
                        infoHint.failInfo(e);
                    }

                    @Override
                    public void onComplete() {
                        LogManager.i("on complete");
                    }
                });
    }



    public void test(final InfoHint infoHint){

        Http.getRetrofit()
                .create(CommonService.class)
                .downImage("https://img6.bdstatic.com/img/image/smallpic/yangmixiaotugengxin.jpg")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        LogManager.i(MainApplication.getContext().getThreadName());

                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        LogUtils.e("123",t.getMessage());
                    }
                });

    }

    /**
     *
     * @param infoHint
     */
    public void requestMapListData(final InfoHint infoHint) {

        Http.getHttpService()
                .getMapList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MapListBean>>() {
                    @Override
                    public void accept(@NonNull List<MapListBean> mapListBeen) throws Exception {
                        infoHint.successMapListData(mapListBeen);
                    }
                });
    }

    public void getMapListDataFromDatabase(){

    }


    public interface InfoHint {

        void successInfo(Drawable drawable);

        void successInfo(Bitmap map);

        void successMapListData(List<MapListBean> data);

        void failInfo(Throwable e);
    }
}
