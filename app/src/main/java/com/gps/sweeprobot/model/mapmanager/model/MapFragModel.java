package com.gps.sweeprobot.model.mapmanager.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.mvp.IModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapFragModel implements IModel {

    public void downMapJpg(String mapUrl, final InfoHint infoHint){

        Http.getHttpService().downImage()
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {

                        Bitmap bitmap=BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(@NonNull Bitmap bitmap) throws Exception {
                        infoHint.successInfo(bitmap);
                    }
                });
    }

    public interface InfoHint{

        void successInfo(Bitmap bitmap);

        void failInfo(String str);
    }
}
