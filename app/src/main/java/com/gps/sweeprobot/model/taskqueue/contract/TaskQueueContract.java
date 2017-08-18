package com.gps.sweeprobot.model.taskqueue.contract;

import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.mvp.IView;

import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/14 0014
 * @Descriptiong : taskQuene 契约
 */

public class TaskQueueContract {

    public interface View extends IView{

        /*
        *  刷新
        */
        void refresh();

        /*
         *  隐藏刷新
         */
        void hideRefresh();

        /*
         *  显示创建新任务的新窗口
         */
        void showCreateNewTaskPopup();

        /*
         *  隐藏popupWindow
         */
        void hidePopup();

        void notifyData(List<Task> tasks);
    }

    public interface Model extends IModel{

        /**
         *  查找所有的task
         */
        void findAllTask(FindMultiCallback callback);

        /**
         *  查找所有的地图
         */
        void findAllMap(FindMultiCallback callback);

        /**
         *  查找地图上所有的点
         * @param mapId
         */
        void findPointBeansByMapId(int mapId,FindMultiCallback callback);

        /**
         *  创建新的任务
         * @param task
         */
        void createNewTask(Task task);

        /**
         *  执行任务
         */
        void executeTask();

    }

    public interface Presenter{

        /*
         *  查找所有的任务
         */
        void findAllTask();

        void findAllMap();

        void findPointBeansByMapId(int mapId);

        void createNewTask();

        void executeTask();

    }

}
