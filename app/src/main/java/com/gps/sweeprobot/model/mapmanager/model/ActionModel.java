package com.gps.sweeprobot.model.mapmanager.model;

import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.mvp.IModel;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by WangJun on 2017/7/24
 */

public class ActionModel implements IModel {


    /**
     * 先从数据库中读取数据，若无则从服务器获取
     * @param messager 回调接口
     */
    public void getActionData(final InfoMessager messager) {

        //从服务器获取数据 rosclient ?

        //从数据库获取数据
        Observable.create(new ObservableOnSubscribe<List<PointBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<PointBean>> e) throws Exception {
                e.onNext(DataSupport.findAll(PointBean.class));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PointBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PointBean> pointBeen) {

                        if (pointBeen.size() > 0){

                            messager.successInfo(pointBeen);
                        }else {
                            getDataFromServer();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        messager.failInfo(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer(){

    }

    public interface InfoMessager {

        void successInfo(List<PointBean> data);

        void failInfo(Throwable e);

    }
}
