package com.gps.sweeprobot.model.taskqueue.presenter;

import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.model.taskqueue.contract.TaskQueueContract;
import com.gps.sweeprobot.model.taskqueue.model.TaskModel;
import com.gps.sweeprobot.mvp.IModel;

import org.litepal.crud.callback.FindMultiCallback;

import java.util.HashMap;
import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/14 0014
 * @Descriptiong : xxx
 */

public class TaskQuenePresenter extends BasePresenter<TaskQueueContract.View> implements  TaskQueueContract.Presenter{

    public static final String TASK_MODEL_KEY = "TaskModelKey";

    private TaskModel taskModel;

    public TaskQuenePresenter() {
        taskModel = (TaskModel) getiModelMap().get(TASK_MODEL_KEY);
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new TaskModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String,IModel> taskModelHashMap = new HashMap();
        taskModelHashMap.put(TASK_MODEL_KEY,models[0]);
        return taskModelHashMap;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {
        return null;
    }

    @Override
    public void setData() {}

    @Override
    public void findAllTask() {

        iView.refresh();
        taskModel.findAllTask(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                iView.notifyData((List<Task>) t);
                iView.hideRefresh();
            }
        });
    }

    @Override
    public void findAllMap() {
        taskModel.findAllMap(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                // 得到地图列表
            }
        });
    }

    @Override
    public void findPointBeansByMapId(int mapId) {
        taskModel.findPointBeansByMapId(mapId, new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {

            }
        });
    }

    @Override
    public void createNewTask() {

        Task task = new Task();
        taskModel.createNewTask(task);
    }

    @Override
    public void executeTask(Task task) {
        taskModel.executeTask(task);
    }
}
