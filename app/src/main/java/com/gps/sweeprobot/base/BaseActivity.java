package com.gps.sweeprobot.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gps.sweeprobot.mvp.IView;
import com.gps.sweeprobot.utils.LogUtils;

import butterknife.ButterKnife;


/**
 * Created by GaoSheng on 2016/9/13.
 */

public abstract class BaseActivity<P extends BasePresenter,V extends IView> extends FragmentActivity implements
        IView, View.OnClickListener  {

    private static final String TAG = "BaseActivity";

    protected View view;

    protected P mPresenter;

    public Context mCtz;

    private ImageView leftImageView,rightImageView;
    private TextView tv_title;
    private String title;
    private boolean leftImageViewVisiable = false;
    private boolean rightImageViewVisiable = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        Log.d("BaseActivity", "onCreate: ");

        ButterKnife.bind(this);

        mCtz = this;

        mPresenter = loadPresenter();
        initCommonData();
        initListener();
        initData();

        leftImageView = getLeftImageView();
        rightImageView = getRightImageView();
        tv_title = getTitleTextView();
        title = getTitleText();
        setTitle();

    }


    private void setTitle() {

        title = getTitleText();

        if (leftImageView != null){
            if (leftImageViewVisiable){
                leftImageView.setVisibility(View.VISIBLE);
            }
        }

        if (rightImageView != null){
            if (rightImageViewVisiable){
                rightImageView.setVisibility(View.VISIBLE);
            }
        }

        if (tv_title != null){
            tv_title.setText(title);
        }

    }

    /**
     *  重写该方法的时候 再super() 方法之前执行
     * @param visiable
     */
    public void setLeftVisiable(boolean visiable) {
        leftImageViewVisiable = visiable;
    }

    public void setRightVisiable(boolean visiable) {
        rightImageViewVisiable = visiable;
    }

    public ImageView getRightImageView(){
        return null;
    }

    public ImageView getLeftImageView(){
        return null;
    }

    protected abstract TextView getTitleTextView();

    protected abstract String getTitleText();

    protected abstract P loadPresenter();

    private void initCommonData() {

        if (mPresenter != null)
            mPresenter.attachView((V)this);
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

        Log.d(TAG, "onDestroy: ");
        
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
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

    public String getStringByRes(@StringRes int resId){
        return getResources().getString(resId);
    }

}
