package com.gps.sweeprobot.model.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.model.view.adapter.util.ItemTypeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by WangJun on 2017/7/13
 */

public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter<CommonRcvAdapter.RcvAdapterItem> implements IAdapter<T>{

    private List<T> mDataList;

    private Object mType;

    private ItemTypeUtil util;

    private int currentPos;

    public CommonRcvAdapter(List<T> data) {

        if (data==null){
            data=new ArrayList<>();
        }
        mDataList=data;
        util=new ItemTypeUtil();
    }

    public void setTypePool(HashMap<Object,Integer> typePool){

        util.setTypePool(typePool);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void setData(@NonNull List<T> data) {

        mDataList=data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    /**
     * instead by{@link #getItemType(Object)}
     * <p>
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     */
    @Override
    public int getItemViewType(int position) {

        this.currentPos=position;
        mType=getItemType(mDataList.get(position));
        return util.getIntType(mType);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItemType(T t) {
        return -1;//default
    }

    @Override
    public RcvAdapterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(),parent,createItem(viewType));
    }

    @Override
    public void onBindViewHolder(RcvAdapterItem holder, int position) {

        holder.item.handleData(getConvertedData(mDataList.get(position),mType),position);

    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }

    public static class RcvAdapterItem extends RecyclerView.ViewHolder{

        protected AdapterItem item;

        public RcvAdapterItem(Context context,ViewGroup parent,AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(),parent,false));
            this.item=item;
            this.item.bindViews(itemView);
            this.item.setViews(this);

        }

    }
}
