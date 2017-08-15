package com.gps.sweeprobot.model.taskqueue.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.taskqueue.contract.TaskQueueContract;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/14 0014
 * @Descriptiong : xxx
 */

public class TaskQuenePresenter extends BasePresenter<TaskQueueContract.View> implements  TaskQueueContract.Presenter{

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
}
