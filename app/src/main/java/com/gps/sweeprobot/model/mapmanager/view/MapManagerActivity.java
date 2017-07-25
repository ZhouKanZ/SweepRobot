package com.gps.sweeprobot.model.mapmanager.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.presenter.MapManagerPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapManagerPresenterImpl;
import com.gps.sweeprobot.model.mapmanager.view.activity.MapEditActivity;
import com.gps.sweeprobot.mvp.IView;

import butterknife.BindView;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerActivity extends BaseActivity<MapManagerPresenter,IView> implements
        MapListItem.MOnItemClickListener {

    @BindView(R.id.activity_map_manager_rv)
    RecyclerView recyclerView;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected MapManagerPresenter loadPresenter() {

        return new MapManagerPresenterImpl(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {

 /*       FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_map_manager_fl, MapFragment.newInstance())
                .commit();*/


        //set toolbar
        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.activity_edit_map_title;
        setToolBar(R.id.toolbar,options);

        mPresenter.setData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mPresenter.initAdapter());


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_manager;
    }

    @Override
    protected void otherViewClick(View view) {


    }

    @Override
    public void onItemClickListener(View view, int position) {

        startSelf(this,MapEditActivity.class,null);
    }
}
