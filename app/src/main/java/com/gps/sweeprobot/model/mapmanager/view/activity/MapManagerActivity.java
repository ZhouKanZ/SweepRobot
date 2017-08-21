package com.gps.sweeprobot.model.mapmanager.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.model.mapmanager.adaper.item.MapListItem;
import com.gps.sweeprobot.model.mapmanager.contract.MapManagerContract;
import com.gps.sweeprobot.model.mapmanager.presenter.MapManagerPresenter;
import com.gps.sweeprobot.model.mapmanager.presenterimpl.MapManagerPresenterImpl;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.mvp.IView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Create by WangJun on 2017/7/17
 */

public class MapManagerActivity extends BaseActivity<MapManagerPresenter,IView> implements
        MapListItem.MOnItemClickListener ,MapManagerContract.view{

    public static final String BUNDLE_KEY = "bundle_key";
    @BindView(R.id.activity_map_manager_rv)
    RecyclerView recyclerView;

    private DialogPlus dialogPlus;

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

        //set toolbar
        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.activity_map_manager_title;
        setToolBar(R.id.toolbar,options);

        mPresenter.setView(this);
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

  /*      switch (view.getId()) {

            case R.id.dialog_map_manager_delete:

                break;
        }*/
    }

    @Override
    public void onItemClickListener(View view, int position) {

        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY,mPresenter.getMapId(position));

        MapEditActivity.startSelf(this,MapEditActivity.class,bundle);

        Log.d("test", "onItemClickListener: " + MapEditActivity.class.getSimpleName() +"id:"+ mPresenter.getMapId(position));
    }

    /**
     * 长按删除
     * @param view
     * @param position
     */
    @Override
    public void onItemLongClickListener(View view, final int position) {

        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.dialog_map_manager_delete))
                .setContentWidth(1300)
                .setOutAnimation(R.anim.fade_out_center)
                .setInAnimation(R.anim.fade_in_center)
                .setExpanded(true, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();

        dialogPlus.show();

        setDialogViewListener(dialogPlus.getHolderView(), R.id.dialog_map_manager_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteViewOnClick(position);
                dialogPlus.dismiss();
            }
        });
    }

    private void setDialogViewListener(View contentView, int viewId, final View.OnClickListener listener){

        Flowable.just(contentView.findViewById(viewId))
                .subscribe(new Consumer<View>() {
                    @Override
                    public void accept(@NonNull View view) throws Exception {
                        view.setOnClickListener(listener);
                    }
                });
    }

    /**
     * 刷新adapter
     * @param data
     */
    @Override
    public void notifyAdapterDataChanged(List<GpsMapBean> data) {

        ((CommonRcvAdapter) recyclerView.getAdapter()).setData(data);
    }
}
