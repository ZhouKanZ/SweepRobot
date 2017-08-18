package com.gps.sweeprobot.model.taskqueue.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.model.taskqueue.contract.EditNaveTaskContract;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;

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
