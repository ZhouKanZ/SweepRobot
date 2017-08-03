package com.gps.sweeprobot.model.main.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.model.createmap.view.activity.CreateActivity;
import com.gps.sweeprobot.model.main.bean.MainTab;
import com.gps.sweeprobot.model.mapmanager.view.activity.MapEditActivity;
import com.gps.sweeprobot.model.mapmanager.view.MapManagerActivity;
import com.gps.sweeprobot.model.taskqueue.view.activity.TaskQueueActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/13 0013
 * @Descriptiong : adapte
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainTab> tabs;
    private Context mContext;
    private LayoutInflater inflater;

    public MainAdapter(List<MainTab> tabs, Context mContext) {
        this.tabs = tabs;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_main_tab, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MainTab tab = tabs.get(position);
        holder.tabDesc.setText(tab.getTabDesc());
        holder.tabImg.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), tab.getTabImageRes()));
        holder.tabName.setText(tab.getTabName());
        holder.layoutTab.setOnClickListener(new MainOnClickListener(position,mContext));
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tab_img)
        ImageView tabImg;
        @BindView(R.id.tab_name)
        TextView tabName;
        @BindView(R.id.tab_desc)
        TextView tabDesc;
        @BindView(R.id.layout_tab)
        RelativeLayout layoutTab;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MainOnClickListener implements View.OnClickListener {

        private int position;
        private Context context;

        public MainOnClickListener(int position, Context context) {
            this.position = position;
            this.context  = context;
        }

        @Override
        public void onClick(View view) {

            switch (position) {
                case 0:
                    CreateActivity.startSelf(context,CreateActivity.class,null);
                    break;
                case 1:
//                    MapManagerActivity.startSelf(context,MapManagerActivity.class,null);
                    break;
                case 2:
                    TaskQueueActivity.startSelf(context,TaskQueueActivity.class,null);
                    break;
                case 3:
                    break;
            }

        }
    }

}
