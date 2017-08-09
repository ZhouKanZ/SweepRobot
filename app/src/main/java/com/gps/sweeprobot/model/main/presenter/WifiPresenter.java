package com.gps.sweeprobot.model.main.presenter;

import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.contract.WifiContract;
import com.gps.sweeprobot.model.main.view.activity.WifiActivity;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;

/**
 * Create by WangJun on 2017/7/13
 */

public abstract class WifiPresenter extends BasePresenter<WifiActivity>implements WifiContract.Presenter {

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return null;
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        return null;
    }

    public abstract void refreshListener();

    public abstract boolean connectWifi(int position,String password);

}
