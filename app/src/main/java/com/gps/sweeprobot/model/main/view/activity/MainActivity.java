package com.gps.sweeprobot.model.main.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.adapter.MainAdapter;
import com.gps.sweeprobot.model.main.bean.MainTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_cate)
    RecyclerView rvCate;

    private MainAdapter adapter;
    private List<MainTab> tabs;

    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "SweepRobot";
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        tabs = new ArrayList<>();
        MainTab tab_create_map = new MainTab(getStringByRes(R.string.createMap),R.mipmap.createmap,getStringByRes(R.string.createMap_desc));
        MainTab tab_manage_map = new MainTab(getStringByRes(R.string.manageMap),R.mipmap.manage,getStringByRes(R.string.manageMap_desc));
        MainTab tab_taskqueue = new MainTab(getStringByRes(R.string.taskqueue),R.mipmap.taskqueue,getStringByRes(R.string.taskqueue_desc));
        MainTab tab_setting = new MainTab(getStringByRes(R.string.setting),R.mipmap.settings,getStringByRes(R.string.setting_desc));
        tabs.add(tab_create_map);
        tabs.add(tab_manage_map);
        tabs.add(tab_taskqueue);
        tabs.add(tab_setting);
        adapter = new MainAdapter(tabs,this);

        rvCate.setLayoutManager(new LinearLayoutManager(this));
        rvCate.setAdapter(adapter);

    }

    @Override
    protected void initListener() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_;
    }

    @Override
    protected void otherViewClick(View view) {}



}
