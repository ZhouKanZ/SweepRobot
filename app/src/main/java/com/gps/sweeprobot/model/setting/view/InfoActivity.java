package com.gps.sweeprobot.model.setting.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;

/**
 * Create by WangJun on 2017/8/10
 */

public class InfoActivity extends BaseActivity {


    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {

        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.set_info;
        setToolBar(R.id.toolbar,options);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_info;
    }

    @Override
    protected void otherViewClick(View view) {

    }
}
