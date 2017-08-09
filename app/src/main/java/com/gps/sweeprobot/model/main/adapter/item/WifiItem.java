package com.gps.sweeprobot.model.main.adapter.item;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.model.main.bean.NetworkEntity;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;

/**
 * Create by WangJun on 2017/7/14
 */

public class WifiItem implements AdapterItem<NetworkEntity> {

    AppCompatTextView name;
    AppCompatImageView signal;
    MOnItemClickListener listener;
    private int position;

    public WifiItem() {
    }

    public WifiItem(MOnItemClickListener listener) {

        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_wifi_list_item;
    }

    @Override
    public void bindViews(View root) {

        name=findView(root,R.id.wifi_name_tv);
        signal=findView(root,R.id.wifi_signal_iv);
    }

    @Override
    public void setViews(RecyclerView.ViewHolder holder) {

        position=holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(view,position);
            }
        });

    }

    @Override
    public void handleData(NetworkEntity networkEntity, int position) {

        name.setText(networkEntity.getName());
        //检测到的信号电平强度
        int level = Math.abs(networkEntity.getLevel());

        if (level>=70){
            signal.setImageResource(R.mipmap.ic_setting_wifi_4);
        }
        else if (level>=60&&level<70){
            signal.setImageResource(R.mipmap.ic_setting_wifi_3);
        }
        else if (level>=50&&level<60){
            signal.setImageResource(R.mipmap.ic_setting_wifi_2);
        }
        else{
            signal.setImageResource(R.mipmap.ic_setting_wifi_1);
        }

    }

    public <T extends View> T findView(View rootView,int viewId) {

        View view = rootView.findViewById(viewId);
        return (T) view;
    }

    public interface MOnItemClickListener{

        void onItemClickListener(View view,int position);
    }
}
