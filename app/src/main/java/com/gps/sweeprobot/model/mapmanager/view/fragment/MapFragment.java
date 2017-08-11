package com.gps.sweeprobot.model.mapmanager.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseFragment;
import com.gps.sweeprobot.model.mapmanager.presenter.MapFragmentPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapFragPresenterImpl;
import com.gps.sweeprobot.widget.GpsImageView;

import butterknife.BindView;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapFragment extends BaseFragment<MapFragmentPresenter> implements
        MapFragPresenterImpl.MapDownListener{

    @BindView(R.id.frag_map_giv)
    GpsImageView gpsImageView;

/*    @BindViews({R.id.frag_map_name,R.id.frag_map_create_at})
    TextView mapName,createAt;*/

    @BindView(R.id.frag_map_edit)
    ImageView editMap;

    public static MapFragment newInstance(){

        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected MapFragmentPresenter loadPresenter() {

        return new MapFragPresenterImpl();
    }

    @Override
    protected void initData(Bundle arguments) {

        mPresenter.setData();
        ((MapFragPresenterImpl) mPresenter).setMapDownListenr(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void mapDownListener(Bitmap bitmap) {

        gpsImageView.setImageView(bitmap);
    }
}
