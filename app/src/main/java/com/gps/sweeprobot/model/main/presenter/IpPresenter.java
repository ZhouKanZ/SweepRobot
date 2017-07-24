package com.gps.sweeprobot.model.main.presenter;


import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.model.main.view.activity.IpActivity;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : xxx
 */

public abstract class IpPresenter extends BasePresenter<IpActivity> implements IpContract.Presenter {

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }
}
