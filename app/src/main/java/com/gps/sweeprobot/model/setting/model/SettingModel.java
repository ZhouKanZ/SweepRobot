package com.gps.sweeprobot.model.setting.model;

import com.gps.sweeprobot.MainApplication;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.mvp.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/8/10
 */

public class SettingModel implements IModel {

    public static final int TAG_SPEED = 1;
    public static final int TAG_INFO = 2;
    public static final int TAG_VERSION = 3;

    public void getSettingData(SettingModel.InfoHint infoHint){

        SettingTemplate speed = new SettingTemplate();
        speed.setId(TAG_SPEED);
//        speed.setIcon(R.mipmap.speed1);
        speed.setTitle(MainApplication.getContext().getString(R.string.set_speed));

        SettingTemplate info = new SettingTemplate();
        info.setId(TAG_INFO);
//        info.setIcon(R.mipmap.info);
        info.setTitle(MainApplication.getContext().getString(R.string.set_info));

        SettingTemplate version = new SettingTemplate();
        version.setId(TAG_VERSION);
        version.setTitle(MainApplication.getContext().getString(R.string.set_version));

        List<SettingTemplate> settingTemplates = new ArrayList<>();
        settingTemplates.add(speed);
        settingTemplates.add(info);
        settingTemplates.add(version);

        infoHint.infoSuccess(settingTemplates);
    }
    public interface InfoHint{

        void infoSuccess(List<SettingTemplate> settingTemplates);
        void infoFail();
    }
}
