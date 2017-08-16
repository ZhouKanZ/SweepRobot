package com.gps.sweeprobot.model.setting.view;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.model.main.bean.ToolbarOptions;
import com.gps.sweeprobot.utils.ToastManager;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Create by WangJun on 2017/8/10
 */

public class SpeedActivity extends BaseActivity {


    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {

        ToolbarOptions options = new ToolbarOptions();
        options.titleId = R.string.set_speed;
        setToolBar(R.id.toolbar,options);

        OptionPicker picker = new OptionPicker(this, new String[]{"低速", "中速", "高速"});
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED,40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                ToastManager.showShort("index="+index+",item=="+item);
//                SpeedActivity.this.finish();
            }
        });
        picker.show();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speed_set;
    }

    @Override
    protected void otherViewClick(View view) {

    }
}
