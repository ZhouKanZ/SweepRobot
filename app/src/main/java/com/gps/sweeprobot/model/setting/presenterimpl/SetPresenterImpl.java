package com.gps.sweeprobot.model.setting.presenterimpl;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.model.setting.adapter.item.SettingItem;
import com.gps.sweeprobot.model.setting.contact.SetContract;
import com.gps.sweeprobot.model.setting.model.SettingModel;
import com.gps.sweeprobot.model.setting.model.SettingTemplate;
import com.gps.sweeprobot.model.setting.presenter.SetPresenter;
import com.gps.sweeprobot.model.view.adapter.CommonRcvAdapter;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.mvp.IModel;

import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/8/9
 */

public class SetPresenterImpl extends SetPresenter implements SettingItem.OnSetItemClickListener {

    private static final String MAP_KEY = "map_key";
    private List<SettingTemplate> setList;
    private SetContract.view view;

    public SetPresenterImpl(SetContract.view view) {
        this.view = view;
    }

    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new SettingModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {

        HashMap<String,IModel> map = new HashMap<>();
        map.put(MAP_KEY,models[0]);
        return map;
    }

    @Override
    public RecyclerView.Adapter initAdapter() {
        return new CommonRcvAdapter<SettingTemplate>(setList) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new SettingItem(SetPresenterImpl.this);
            }
        };
    }
    @Override
    public void setData() {

        ((SettingModel) getiModelMap().get(MAP_KEY)).getSettingData(new SettingModel.InfoHint() {
            @Override
            public void infoSuccess(List<SettingTemplate> settingTemplates) {
                setList = settingTemplates;
            }

            @Override
            public void infoFail() {

            }
        });
    }

    @Override
    public void onSetItemClick(int id) {

        switch (id) {

            case SettingModel.TAG_SPEED:
                view.onSpeedItemClick();
                break;

            case SettingModel.TAG_INFO:
                view.onInfoItemClick();
                break;

            case SettingModel.TAG_VERSION:
                view.onVersionItemClick();
                break;
        }
    }
}
