package com.gps.sweeprobot.model.mapmanager.model;

import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
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
     * @param messager 回调接口
     */
    public void getActionData(final InfoMessager messager) {

        //从数据库获取数据
        Observable.fromArray(DataSupport.findAll(PointBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PointBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PointBean> pointBeen) {

                        if (pointBeen.size() > 0){
                            LogManager.i("action on next"+pointBeen.size());
                            messager.successInfo(pointBeen);
                        }else {
                            getDataFromServer();
                        }

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

    public void getObstacleData(final ObstacleInfo info){

        Observable.fromArray(DataSupport.findAll(VirtualObstacleBean.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VirtualObstacleBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //拦截
                    }

                    @Override
                    public void onNext(@NonNull List<VirtualObstacleBean> virtualObstacleBeen) {

                        if (virtualObstacleBeen.size()>0){
                            info.successInfo(virtualObstacleBeen);
                        }
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
    private void getDataFromServer(){

    }

    public void setObstacle2Ros(VirtualObstacleBean bean){

        CommunicationUtil.sendObstacle2Ros(bean);
    }

    public interface InfoMessager {

        void successInfo(List<PointBean> data);

        void failInfo(Throwable e);

    }

    public interface ObstacleInfo{

        void successInfo(List<VirtualObstacleBean> data);

        void errorInfo(Throwable e);
    }
}
