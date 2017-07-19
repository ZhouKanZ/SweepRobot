package com.gps.sweeprobot.model.main.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.model.main.presenter.IpPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : xxx
 */

public class IpActivity extends BaseActivity<IpPresenter> implements IpContract.View {

    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected IpPresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initListener() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ip;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        MainActivity.startSelf(this,MainActivity.class,null);
        this.finish();
    }
}
