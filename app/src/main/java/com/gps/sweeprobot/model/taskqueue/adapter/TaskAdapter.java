package com.gps.sweeprobot.model.taskqueue.adapter;

import android.content.Context;
import android.databinding.repacked.org.antlr.runtime.tree.RewriteRuleElementStream;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.database.Task;
import com.gps.sweeprobot.url.UrlHelper;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/17 0017
 * @Descriptiong : 任务adapter
 */

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> tasks;
    private Context ctz;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;

    /* 有item */
    private static final int ITEM_NORMAL = 0X00;
    /* 无task的item */
    private static final int ITEM_NULL = 0X01;

    public TaskAdapter(List<Task> tasks, Context ctz) {
        this.tasks = tasks;
        this.ctz = ctz;
        inflater = LayoutInflater.from(ctz);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_NORMAL) {
            return new NormalViewHolder(inflater.inflate(R.layout.item_task, parent, false));
        } else {
            return new NullViewHolder(inflater.inflate(R.layout.item_task_null, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM_NORMAL:
                onBindNormalViewHolder((NormalViewHolder) holder,position);
                break;
            case ITEM_NULL:
                onBindNullViewHolder((NullViewHolder) holder,position);
                break;
        }
    }

    /**
     *  为空的时候
     * @param holder
     * @param position
     */
    private void onBindNullViewHolder(NullViewHolder holder, int position) {

        /* 新建任务 */
        if (onClickListener != null)
            holder.btn.setOnClickListener(onClickListener);

    }

    /**
     *  不为空时
     * @param holder
     * @param position
     */
    private void onBindNormalViewHolder(NormalViewHolder holder, int position) {

        Task task = tasks.get(position);

        /* 查看 */
        if (onClickListener != null){
            holder.btnCheck.setOnClickListener(onClickListener);
            /* 执行 */
            holder.btnExecute.setOnClickListener(onClickListener);
        }

        holder.tvTaskTime.setText(task.getCreateTime());
        holder.tvTaskType.setText(task.getType() == 0 ? "导航点任务" : "轨迹任务");
        holder.tvTitle.setText(task.getName());

        GpsMapBean gpsMapBean = DataSupport.find(GpsMapBean.class,task.getMapId());
        Glide.with(ctz)
                .setDefaultRequestOptions(RequestOptions.placeholderOf(R.mipmap.map_test))
                .load(UrlHelper.BASE_URL + gpsMapBean.getId()+ "/" +gpsMapBean.getId()+".jpg")
                .into(holder.ivMap);

    }


    @Override
    public int getItemViewType(int position) {
        if (tasks.size() == 0){
            return ITEM_NULL;
        }else {
            return ITEM_NORMAL;
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size() == 0 ? 1 : tasks.size();
    }

    /**
     * 可能有多个ViewHolder --> 跟ViewType是一一对应的
     */
    public class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_map)
        ImageView ivMap;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_task_type)
        TextView tvTaskType;
        @BindView(R.id.tv_task_time)
        TextView tvTaskTime;
        @BindView(R.id.btn_execute)
        TextView btnExecute;
        @BindView(R.id.btn_check)
        TextView btnCheck;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class NullViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_)
        Button btn;

        public NullViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
