package com.gps.sweeprobot.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.model.taskqueue.view.activity.TaskDetailActivity;
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
    private Toolbar toolbar;
    private boolean leftImageViewVisiable = false;
    private boolean rightImageViewVisiable = false;

    public Bundle savedInstanceState;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());

        if (null != savedInstanceState){
            this.savedInstanceState = savedInstanceState;
        }

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setToolBar(int toolBarId, ToolbarOptions options) {

        toolbar = (Toolbar) findViewById(toolBarId);
        if (options.titleId != 0) {
            toolbar.setTitle(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            toolbar.setTitle(options.titleString);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        setActionBar(toolbar);

        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigateUpClicked();
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setToolBar(int toolbarId, int titleId) {

        toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setTitle(titleId);
        setActionBar(toolbar);
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public int getToolBarHeight() {
        if (toolbar != null) {
            return toolbar.getHeight();
        }

        return 0;
    }

    public void onNavigateUpClicked() {
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTitle(CharSequence title) {

        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
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
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
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
