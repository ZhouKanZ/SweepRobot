package com.gps.sweeprobot.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.utils.LogUtils;

import butterknife.ButterKnife;


/**
 * Created by GaoSheng on 2016/9/13.
 */

public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity implements
        IView, View.OnClickListener {
    protected View view;

    protected P mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        ButterKnife.bind(this);

        mPresenter = loadPresenter();
        initCommonData();
//        initView();
        initListener();
        initData();
    }


    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

//    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }


    /**
     * @param str 显示一个内容为str的toast
     */
    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param contentId 显示一个内容为contentId指定的toast
     */
    public void toast(int contentId) {
        Toast.makeText(this, contentId, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param str 日志的处理
     */
    public void LogE(String str) {
        LogUtils.e(getClass(), str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    /**
     *  启动Activity
     * @param ctz
     * @param act
     * @param bundle
     */
    public static void startSelf(Context ctz, Class act,Bundle bundle){
        Intent i = new Intent(ctz,act);
        i.putExtra(act.getSimpleName(),bundle);
        ctz.startActivity(i);
    }

    public  <T extends View>T findView(int viewId){

        if (view == null){
            return null;
        }
        return (T) view.findViewById(viewId);

    }
}
