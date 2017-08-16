package com.gps.sweeprobot.model.setting.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.model.setting.contact.SetContract;
import com.gps.sweeprobot.model.setting.presenter.SetPresenter;
import com.gps.sweeprobot.model.setting.presenterimpl.SetPresenterImpl;
import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.utils.ToastManager;

import butterknife.BindView;

/**
 * Create by WangJun on 2017/8/9
 */

public class SettingActivity extends BaseActivity<SetPresenter,IView> implements SetContract.view{

    @BindView(R.id.activity_set_recycler)
    RecyclerView setRecyclerView;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected SetPresenter loadPresenter() {
        return new SetPresenterImpl(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {

        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.set_title;
        setToolBar(R.id.toolbar,options);

        mPresenter.setData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        setRecyclerView.setLayoutManager(layoutManager);
        setRecyclerView.setAdapter(mPresenter.initAdapter());

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * contract view
     */
    @Override
    public void onSpeedItemClick() {

        SpeedActivity.startSelf(this,SpeedActivity.class,null);
    }

    @Override
    public void onInfoItemClick() {

        InfoActivity.startSelf(this,InfoActivity.class,null);
    }

    @Override
    public void onVersionItemClick() {

        ToastManager.showShort(getString(R.string.version_description));
    }
}
