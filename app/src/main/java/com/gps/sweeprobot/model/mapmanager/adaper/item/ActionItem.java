package com.gps.sweeprobot.model.mapmanager.adaper.item;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.bean.IAction;
import com.gps.sweeprobot.database.PointBean;
import com.gps.sweeprobot.database.VirtualObstacleBean;
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

    private Animator anim;
    private View root;

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
        this.root = root;
    }

    @Override
    public void setViews(final RecyclerView.ViewHolder holder) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = actionName.getText().toString();
                if (name == null){

                    LogManager.e("action name is null");
                    return;
                }
                listener.onItemClick(v,name,holder.getLayoutPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v,holder.getLayoutPosition());
                return true;
            }
        });
    }

    public void startAnim(){

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

        /*        int cx = (root.getLeft() + root.getRight())/2;
                int cy = (root.getTop() + root.getBottom())/2;
                int initialRadius = root.getWidth();
                anim = ViewAnimationUtils.createCircularReveal(root, cx, cy, initialRadius, 0);
                anim.start();*/
                Animation animation = AnimationUtils.loadAnimation(root.getContext(), R.anim.fade_out_center);
                root.setAnimation(animation);
            }
        });
    }


    @Override
    public void handleData(IAction iAction, int position) {

        if (iAction instanceof PointBean){

            LogManager.i("handle data successful");
            actionName.setText(((PointBean) iAction).getPointName());
        }else {
            actionName.setText(((VirtualObstacleBean) iAction).getName());
        }

    }

    public void setIcon(int resId){
        icon.setImageResource(resId);
    }

    public interface ActionOnItemListener{

        void onItemClick(View view,String pointName,int position);

        void onItemLongClick(View item,int position);
    }

}