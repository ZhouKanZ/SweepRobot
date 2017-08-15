package com.gps.sweeprobot.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.gps.sweeprobot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/10 0010
 * @Descriptiong : xxx
 */

public class TestActivity2 extends Activity {

    @BindView(R.id.gps_layout)
    GestureFrameLayout gpsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_test2);
        ButterKnife.bind(this);

        gpsLayout.getController().getSettings().setRotationEnabled(true);
    }
}
