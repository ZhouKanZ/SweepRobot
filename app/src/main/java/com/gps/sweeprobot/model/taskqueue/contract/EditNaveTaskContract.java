package com.gps.sweeprobot.model.taskqueue.contract;

import android.graphics.Bitmap;

import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.mvp.IView;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */

public class EditNaveTaskContract {

    public interface Presenter{

        /**
         * 根据地图ip,获取标记点数据
         * @param mapId
         */
        void initData(int mapId);

        void addPoint();

        void removePoint();

        void saveTask(Task task);

    }


    /* 暂时不用Model了 */
    public interface Model extends IModel{

    }

    public interface View extends IView{

        /**
         *  通知待选点adapter更新
         * @param pointBeens
         */
        void notifyCandidateAdapter(List<PointBean> pointBeens);

        /**
         *  通知已选的adapter更新
         * @param pointBeens
         */
        void notifySelectedAdapter(List<PointBean> pointBeens);

        /**
         *  gpsImage 添加pose
         * @param point
         */
        void addNavePose(PointBean point);

        /**
         *  gpsImage 移除pose
         * @param point
         */
        void removeNavePose(PointBean point);

        /**
         *  显示编辑layout
         */
        void showPoseEditLayout();

        /**
         *  隐藏编辑layout
         */
        void hidePoseEditLayout();

        /**
         * 设置点
         * @param t
         */
        void setPoints(List<PointBean> t);

        /**
         *  设置gpsImage的图片
         * @param bitmap
         */
        void setImage(Bitmap bitmap);

        /**
         *  结束当前的Activity
         */
        void finishActivity();
    }

}
