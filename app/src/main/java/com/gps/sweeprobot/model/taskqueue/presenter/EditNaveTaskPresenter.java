package com.gps.sweeprobot.model.taskqueue.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.http.Http;
import com.gps.sweeprobot.http.WebSocketHelper;
import com.gps.sweeprobot.model.taskqueue.contract.EditNaveTaskContract;
import com.gps.sweeprobot.mvp.IModel;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */
public class EditNaveTaskPresenter extends BasePresenter<EditNaveTaskContract.View> implements EditNaveTaskContract.Presenter {

    private static final String TAG = "EditNaveTaskPresenter";

    private GpsMapBean gpsMapBean;
    private Context ctz;

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {
        return null;
    }

    @Override
    public void setData() {
    }

    public EditNaveTaskPresenter(Context ctz) {
        this.ctz = ctz;
    }

    @Override
    public void initData(int mapId) {

        /* 图片 */
        DataSupport.findAsync(GpsMapBean.class, mapId).listen(new FindCallback() {
            @Override
            public <T> void onFinish(T t) {
                gpsMapBean = (GpsMapBean) t;
                String url = gpsMapBean.getId() + "/"+gpsMapBean.getId()+".jpg";
                Log.d(TAG, "onFinish: " + url);
                Http.getHttpService()
                        .downInetImage(url)
                        .map(new Function<ResponseBody, Bitmap>() {
                            @Override
                            public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                                return BitmapFactory.decodeStream(responseBody.byteStream());
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(new Function<Throwable, Bitmap>() {
                            @Override
                            public Bitmap apply(@NonNull Throwable throwable) throws Exception {
                                return BitmapFactory.decodeResource(ctz.getResources(), R.mipmap.testmap);
                            }
                        })
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void accept(@NonNull Bitmap bitmap) throws Exception {
                                iView.setImage(bitmap);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.d("xx", "accept: ");
                            }
                        });
            }
        });

        /* 绘制点 */
        DataSupport.where("mapId = ?",String.valueOf(mapId)).findAsync(PointBean.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                iView.notifyCandidateAdapter((List<PointBean>) t);
                iView.setPoints((List<PointBean>) t);
            }
        });

    }

    @Override
    public void addPoint() {
    }

    @Override
    public void removePoint() {

    }

    @Override
    public void saveTask(Task task) {

        task.setMapUrl(gpsMapBean.getCompletedMapUrl());
        task.save();

        com.gps.sweeprobot.bean.Task  task1 = new com.gps.sweeprobot.bean.Task();
        task1.setType(2);
        task1.setMap_id(task.getMapId());
        task1.setMap_name(task.getName());
        task1.setNav_id(task.getPointBeanList());

        task1.setTask_id(task.getId());
        WebSocketResult<com.gps.sweeprobot.bean.Task> taskWeb = new WebSocketResult<>();

        taskWeb.setOp("call_service");
        taskWeb.setService("/edit_nav_task");
        taskWeb.setArgs(task1);

        String taskStr = JSON.toJSONString(taskWeb);

        WebSocketHelper.send(taskStr);
        iView.finishActivity();
    }
}
