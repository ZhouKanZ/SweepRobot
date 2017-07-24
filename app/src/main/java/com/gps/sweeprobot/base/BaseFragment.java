package com.gps.sweeprobot.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gps.sweeprobot.mvp.IView;

import butterknife.ButterKnife;

/**
 * Create by WangJun on 2017/7/18
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView{

    private Activity mActivity;
    private View rootView;
    public BasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,rootView);
        mPresenter=loadPresenter();
        initData(getArguments());
        initListener();
        return rootView;
    }

    protected abstract P loadPresenter();

    protected abstract void initData(Bundle arguments);

    protected abstract int getLayoutId();

    protected abstract void initListener();

    public  <T extends View>T findView(int viewId){

        if (rootView == null){
            return null;
        }
        return (T) rootView.findViewById(viewId);

    }

    public void finishActivity(){
        mActivity.finish();
    }
}
