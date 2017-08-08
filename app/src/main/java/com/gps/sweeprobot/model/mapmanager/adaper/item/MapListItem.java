package com.gps.sweeprobot.model.mapmanager.adaper.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapListItem implements AdapterItem<MapListBean> {

    @BindView(R.id.layout_map_list_iv)
    ImageView mapDrawable;

    @BindViews({R.id.layout_map_list_create_time,R.id.layout_map_list_name})
    List<TextView> names;

    private MapListItem.MOnItemClickListener listener;

    public MapListItem(MOnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_map_list;
    }

    @Override
    public void bindViews(View root) {

        ButterKnife.bind(this,root);
    }

    @Override
    public void setViews(final RecyclerView.ViewHolder holder) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapListItem.this.listener.onItemClickListener(view,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public void handleData(MapListBean mapListBean, int position) {

        mapDrawable.setImageResource(R.mipmap.fb_map);
        names.get(1).setText(mapListBean.getName());
        names.get(0).setText(mapListBean.getCreateAt());
    }

    public interface MOnItemClickListener{

        void onItemClickListener(View view,int position);
    }
}
