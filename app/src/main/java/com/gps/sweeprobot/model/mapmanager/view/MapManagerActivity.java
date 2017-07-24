package com.gps.sweeprobot.model.mapmanager.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.presenter.MapManagerPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapManagerPresenterImpl;

import butterknife.BindView;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerActivity extends BaseActivity<MapManagerPresenter> implements
        MapListItem.MOnItemClickListener {

    @BindView(R.id.activity_map_manager_rv)
    RecyclerView recyclerView;

    @Override
    protected MapManagerPresenter loadPresenter() {

        return new MapManagerPresenterImpl(this);
    }

    @Override
    protected void initData() {

 /*       FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_map_manager_fl, MapFragment.newInstance())
                .commit();*/


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mPresenter.initAdapter());
        mPresenter.setData();

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

//        startSelf(this,);
    }
}
