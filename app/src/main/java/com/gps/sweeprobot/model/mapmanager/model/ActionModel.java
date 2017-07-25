package com.gps.sweeprobot.model.mapmanager.model;

import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.utils.LogManager;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Create by WangJun on 2017/7/24
 */

public class ActionModel implements IModel {


    public void getActionData(InfoMessager messager){

        //从数据库获取数据
        List<PointBean> all = DataSupport.findAll(PointBean.class);
        if (all != null){
            messager.successInfo(all);
            return;
        }
        LogManager.e("can not get data form database");
        //从服务器获取数据 rosclient ?

    }

    public interface InfoMessager{

        void successInfo(List<PointBean> data);

    }
}
