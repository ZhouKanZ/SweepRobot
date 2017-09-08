package com.gps.sweeprobot.model.taskqueue.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gps.sweeprobot.bean.FitParams;
import com.gps.sweeprobot.bean.WebSocketResult;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.http.WebSocketHelper;
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
    public void executeTask(Task task) {
//        // 向服务器发送数据
////        int32 map_id
////        string map_name
////        int32 task_id
////        int32 rate
//
////        ---float64[] data
////        string errormsg
////        bool successed
//
////        execute_nav_task
//        JSONObject jsonTask = new JSONObject();
//        jsonTask.put("map_id",task.getMapId());
//        jsonTask.put("map_name",task.getName());
//        jsonTask.put("task_id",task.getId());
//        jsonTask.put("rate",1);


        WebSocketResult<ExecuteTaskModel> execute = new WebSocketResult();
        execute.setService("/execute_nav_task");
        execute.setOp("call_service");
        ExecuteTaskModel exe = new ExecuteTaskModel();
        GpsMapBean gmb = DataSupport.find(GpsMapBean.class,task.getMapId());
        exe.setMap_name(gmb.getName());
        exe.setTask_id(task.getId());
        exe.setMap_id(task.getMapId());
        exe.setRate(1);
//        exe.setNav_flag(1);

        execute.setArgs(exe);

        Log.d("tag", "executeTask: " + JSON.toJSONString(execute));

        WebSocketHelper.send(JSON.toJSONString(execute));
    }


}
