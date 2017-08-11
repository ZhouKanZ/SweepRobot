package com.gps.sweeprobot.model.setting.adapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.model.setting.model.SettingTemplate;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by WangJun on 2017/8/10
 */

public class SettingItem implements AdapterItem<SettingTemplate> {

    @BindView(R.id.item_setting_icon)
    ImageView icon;

    @BindView(R.id.item_setting_title)
    TextView title;

    @BindView(R.id.item_setting_icon_right)
    ImageView iconRight;

    private OnSetItemClickListener listener;
    private int tagId;

    public SettingItem(OnSetItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_setting;
    }

    @Override
    public void bindViews(View root) {

        ButterKnife.bind(this,root);
    }

    @Override
    public void setViews(RecyclerView.ViewHolder holder) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetItemClick(tagId);
            }
        });
    }

    @Override
    public void handleData(SettingTemplate settingTemplate, int position) {

        tagId = settingTemplate.getId();
        icon.setImageResource(settingTemplate.getIcon());
        title.setText(settingTemplate.getTitle());
        iconRight.setImageResource(settingTemplate.getRightIcon());
    }

    public interface OnSetItemClickListener{

        void onSetItemClick(int id);
    }
}
