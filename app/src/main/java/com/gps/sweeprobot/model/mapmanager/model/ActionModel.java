package com.gps.sweeprobot.model.mapmanager.model;

import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
import com.gps.sweeprobot.http.Constant;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.CommunicationUtil;
import com.gps.sweeprobot.utils.LogManager;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
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
     *
     * @param messager 回调接口
     */
    public void getActionData(final int mapid, final InfoMessager messager) {

        //从数据库获取数据
        Observable.fromArray(DataSupport.where("mapId = ?", String.valueOf(mapid)).find(PointBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PointBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PointBean> pointBeen) {

                        messager.successInfo(pointBeen);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogManager.i("action on error");
                        messager.failInfo(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getObstacleData(final int mapid, final ObstacleInfo info) {

        Observable.fromArray(DataSupport.where("mapId = ?", String.valueOf(mapid)).find(VirtualObstacleBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VirtualObstacleBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //拦截
                    }

                    @Override
                    public void onNext(@NonNull List<VirtualObstacleBean> obstacleBeans) {

                        info.successInfo(obstacleBeans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        info.errorInfo(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

    }

    public void sendObstacle2Ros(VirtualObstacleBean bean) {

        CommunicationUtil.sendObstacle2Ros(bean, Constant.ADD);
    }

    public interface InfoMessager {

        void successInfo(List<PointBean> data);

        void failInfo(Throwable e);

    }

    public interface ObstacleInfo {

        void successInfo(List<VirtualObstacleBean> data);

        void errorInfo(Throwable e);
    }
}
