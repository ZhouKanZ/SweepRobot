package com.gps.sweeprobot.model.main.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.model.main.adapter.item.WifiItem;
import com.gps.sweeprobot.model.main.contract.WifiContract;
import com.gps.sweeprobot.model.main.presenter.WifiPresenter;
import com.gps.sweeprobot.model.main.presenterimpl.WifiPresenterImpl;
import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.utils.ToastManager;

import butterknife.BindView;

/**
 * Create by WangJun on 2017/7/13
 */

public class WifiActivity extends BaseActivity<WifiPresenter,IView> implements WifiContract.View
,SwipeRefreshLayout.OnRefreshListener,WifiItem.MOnItemClickListener{

    @BindView(R.id.wifi_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.wifi_recycle_view)
    RecyclerView mRecycler;

    //dialog
    EditText inputPasswordEt;
    Button cancel,sure;


    private WifiPresenter mPresenter;

    private AlertDialog dialog;

    private int position;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected WifiPresenter loadPresenter() {
        mPresenter=new WifiPresenterImpl(this);
        return mPresenter;
    }

    @Override
    protected void initData() {

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mPresenter.initAdapter());
        mPresenter.setData();

//        mSwipeRefreshLayout.setColorSchemeColors(getColor(R.color.color_activity_blue_bg));

    }

    @Override
    protected void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi;
    }

    @Override
    protected void otherViewClick(View view) {

        switch (view.getId()) {

            case R.id.wifi_item:

                break;
            case R.id.dialog_input_wifi_cancel:
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                break;

            case R.id.dialog_input_wifi_sure:
                if (dialog.isShowing()){
                    dialog.dismiss();
                }

                boolean isConnect = mPresenter.connectWifi(position, inputPasswordEt.getText().toString().trim());
                if (isConnect){

                    ToastManager.showShort(this,R.string.connect_success);
                }else {
                    ToastManager.showShort(this,R.string.connect_failed);
                }

                break;
        }
    }


    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.refreshListener();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        this.position=position;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_input_wifi_pw, null);

        builder.setView(inflate);
        dialog= builder.create();

        inputPasswordEt= (EditText) inflate.findViewById(R.id.dialog_input_wifi_pw_et);

        sure= (Button) inflate.findViewById(R.id.dialog_input_wifi_sure);
        cancel= (Button) inflate.findViewById(R.id.dialog_input_wifi_cancel);

        sure.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.show();
        dialog.getWindow().setLayout(1500,800);
    }
}
