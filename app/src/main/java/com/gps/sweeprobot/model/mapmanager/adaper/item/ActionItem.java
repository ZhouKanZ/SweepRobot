package com.gps.sweeprobot.model.mapmanager.adaper.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.bean.IAction;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.utils.LogManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by WangJun on 2017/7/24
 */

public class ActionItem implements AdapterItem<IAction>{

    @BindView(R.id.item_map_edit_icon)
    ImageView icon;

    @BindView(R.id.item_map_edit_point_name)
    TextView actionName;

    private ActionOnItemListener listener;

    public ActionItem(ActionOnItemListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_map_edit;
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

                String name = actionName.getText().toString();
                if (name == null){

                    LogManager.e("action name is null");
                    return;
                }
                listener.onItemClick(v,name);
            }
        });
    }

    @Override
    public void handleData(IAction iAction, int position) {

        if (iAction instanceof PointBean){

            icon.setImageResource(R.mipmap.point);
            actionName.setText(((PointBean) iAction).getPointName());
        }

    }

    public interface ActionOnItemListener{

        void onItemClick(View view,String pointName);
    }

}
