package com.gps.sweeprobot.model.main.view.activity;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.data.ConfigParams;
import com.gps.sweeprobot.model.main.contract.WelcomeContract;
import com.gps.sweeprobot.model.main.presenter.WelcomePresenter;
import com.gps.sweeprobot.model.main.view.frag.WelcomeFragment;

import butterknife.BindView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : 欢迎页
 */

public class WelcomeActivity extends BaseActivity<WelcomePresenter,WelcomeContract.View> implements WelcomeContract.View {

    @BindView(R.id.vp)
    ViewPager vp;

    private FragmentPagerAdapter adapter;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected WelcomePresenter loadPresenter() {
        return null;
    }


    @Override
    protected void initData() {

        boolean fristInstall = ConfigParams.isFristInstall(this);

        if (!fristInstall){
            IpActivity.startSelf(this,IpActivity.class,null);
            this.finish();
            return;
        }


        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return WelcomeFragment.newInstance(position+"");
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        vp.setAdapter(adapter);

        Log.d("xx", "initData: " +ConfigParams.setIsFristInstall(false));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void otherViewClick(View view) {

    }

}
