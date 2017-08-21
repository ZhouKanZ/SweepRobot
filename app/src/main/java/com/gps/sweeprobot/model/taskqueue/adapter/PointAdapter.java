package com.gps.sweeprobot.model.taskqueue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.PointBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */

public class PointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PointBean> pointBeanList;
    private Context ctz;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    /* 有item */
    private static final int ITEM_NORMAL = 0X00;
    /* 无task的item */
    private static final int ITEM_NULL = 0X01;

    public PointAdapter(List<PointBean> pointBeanList, Context ctz) {
        this.pointBeanList = pointBeanList;
        this.ctz = ctz;
        inflater = LayoutInflater.from(ctz);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_NORMAL) {
            return new NormalViewHolder(inflater.inflate(R.layout.item_point, parent, false));
        } else {
            return new NullViewHolder(inflater.inflate(R.layout.item_point_null, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_NORMAL:
                onBindNormalViewHolder((NormalViewHolder) holder, position);
                break;
            case ITEM_NULL:
                onBindNullViewHolder((NullViewHolder) holder, position);
                break;
        }
    }

    /**
     * 为空的时候
     *
     * @param holder
     * @param position
     */
    private void onBindNullViewHolder(NullViewHolder holder, int position) {

    }

    /**
     * 不为空时
     *
     * @param holder
     * @param position
     */
    private void onBindNormalViewHolder(final NormalViewHolder holder, final int position) {

        final PointBean point = pointBeanList.get(position);
        if (point.getType() == 0){
            Bitmap add =  BitmapFactory.decodeResource(ctz.getResources(),R.mipmap.add);
            holder.ivItemControl.setImageBitmap(add);
        }else if (point.getType() == 1){
            Bitmap sub =  BitmapFactory.decodeResource(ctz.getResources(),R.mipmap.sub);
            holder.ivItemControl.setImageBitmap(sub);
        }

        holder.tvPointName.setText(point.getPointName());

        if (onItemClickListener != null){
            holder.ivItemControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (point.getType() == 0){
                        onItemClickListener.onAddClick(holder.itemView,position);
                    }else if (point.getType() == 1){
                        onItemClickListener.onSubClick(holder.itemView,position);
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (pointBeanList.size() == 0) {
            return ITEM_NULL;
        } else {
            return ITEM_NORMAL;
        }

    }

    @Override
    public int getItemCount() {
        return pointBeanList.size() == 0 ? 1 : pointBeanList.size();
    }

    /**
     * 可能有多个ViewHolder --> 跟ViewType是一一对应的
     */
    public class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_point_name)
        TextView tvPointName;
        @BindView(R.id.iv_item_control)
        ImageView ivItemControl;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class NullViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_null)
        TextView tvNull;

        public NullViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener{

        /**
         * 当添加被点击
         * @param view
         * @param position
         */
        void onAddClick(View view,int position);

        /**
         * 当减少被点击
         * @param view
         * @param position
         */
        void onSubClick(View view,int position);
    }

}
