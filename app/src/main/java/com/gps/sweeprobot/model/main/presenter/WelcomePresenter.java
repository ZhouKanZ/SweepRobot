package com.gps.sweeprobot.model.main.presenter;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.contract.WelcomeContract;
import com.gps.sweeprobot.model.main.view.activity.WelcomeActivity;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public abstract class WelcomePresenter extends BasePresenter<WelcomeActivity> implements WelcomeContract.Presenter {

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }
}
