package com.gps.sweeprobot.model.main.view.activity;

import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected BasePresenter loadPresenter() {
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
        return R.layout.activity_main_;
    }

    @Override
    protected void otherViewClick(View view) {

    }

}
