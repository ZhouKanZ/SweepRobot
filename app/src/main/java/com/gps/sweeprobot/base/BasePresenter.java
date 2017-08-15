package com.gps.sweeprobot.base;


import android.support.v7.widget.RecyclerView;

import com.gps.sweeprobot.mvp.IModel;
import com.gps.sweeprobot.mvp.IPresenter;
import com.gps.sweeprobot.mvp.IView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by GaoSheng on 2016/11/26.
 * 17:21
 * view 的弱引用避免内存泄漏
 *
 */

public abstract class BasePresenter<V extends IView> implements IPresenter {

    private WeakReference actReference;
    protected V iView;

    @Override
    public void attachView(IView iView) {
        actReference = new WeakReference(iView);
        this.iView = (V) iView;
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getIView() {
        return (V) actReference.get();
    }

    public abstract HashMap<String, IModel> getiModelMap();

    /**
     * @param models
     * @return
     * 添加多个model,如有需要
     */
    public abstract HashMap<String, IModel> loadModelMap(IModel... models);

    public abstract RecyclerView.Adapter initAdapter();

    public abstract void setData();


}
