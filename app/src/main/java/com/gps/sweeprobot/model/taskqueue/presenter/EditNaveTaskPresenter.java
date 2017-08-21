package com.gps.sweeprobot.model.taskqueue.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.model.taskqueue.contract.EditNaveTaskContract;
import com.gps.sweeprobot.mvp.IModel;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.HashMap;
import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */

public class EditNaveTaskPresenter extends BasePresenter<EditNaveTaskContract.View> implements EditNaveTaskContract.Presenter {
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

    @Override
    public void initData(int mapId) {

        DataSupport.where("mapId = ?",String.valueOf(mapId)).findAsync(PointBean.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                iView.notifyCandidateAdapter((List<PointBean>) t);
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
    }
}
