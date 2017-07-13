package com.gps.sweeprobot.model.main.view.activity;

import android.view.View;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.model.main.presenter.IpPresenter;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class IpActivity extends BaseActivity<IpPresenter> implements IpContract.View{

    @Override
    protected IpPresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ip;
    }

    @Override
    protected void otherViewClick(View view) {

    }
}
