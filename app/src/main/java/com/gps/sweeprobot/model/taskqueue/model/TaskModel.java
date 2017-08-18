package com.gps.sweeprobot.model.taskqueue.model;

import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.model.taskqueue.contract.TaskQueueContract;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : 任务task
 */

public class TaskModel implements TaskQueueContract.Model {

    public TaskModel() {
    }

    @Override
    public void findAllTask(FindMultiCallback callback) {
        DataSupport.findAllAsync(Task.class).listen(callback);
    }

    @Override
    public void findAllMap(FindMultiCallback callback) {
        DataSupport.findAllAsync(GpsMapBean.class).listen(callback);
    }

    @Override
    public void findPointBeansByMapId(int mapId, FindMultiCallback callback) {
        DataSupport.where("mapId = ?",mapId+"").findAsync(PointBean.class).listen(callback);
    }

    @Override
    public void createNewTask(Task task) {
        task.save();
    }

    @Override
    public void executeTask() {
        // 向服务器发送数据
    }


}
