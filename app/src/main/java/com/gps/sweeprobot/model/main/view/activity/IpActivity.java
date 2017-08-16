package com.gps.sweeprobot.model.main.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.http.Constant;
import com.gps.sweeprobot.model.main.contract.IpContract;
import com.gps.sweeprobot.model.main.presenter.IpPresenter;
import com.gps.sweeprobot.url.UrlHelper;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/12 0012
 * @Descriptiong : IpActivity
 */

public class IpActivity extends BaseActivity<IpPresenter, IpContract.View> implements IpContract.View,
        DialogInterface.OnClickListener {

    private static final String TAG = "ip";
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private ProgressDialog pd;
    private AlertDialog dialog;

    @Override
    protected TextView getTitleTextView() {
        return null;
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected IpPresenter loadPresenter() {
        return new IpPresenter();
    }

    @Override
    protected void initData() {

        etIp.setText(Constant.JiaoJian);

        pd = new ProgressDialog(this);
        pd.setMessage("正在连接机器人请稍后");
        pd.setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder
                .setMessage("未连接机器人wifi或机器人服务未正常开启")
                .setNegativeButton("取消", this)
                .setPositiveButton("设置wifi", this)
                .create();

    }

    @Override
    protected void initListener() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ip;
    }

    @Override
    protected void otherViewClick(View view) {
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.connectToRos();
    }

    @Override
    public String getIpText() {
        return etIp.getEditableText().toString();
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: " + Thread.currentThread().getName());
        if (null != pd) {
            pd.show();
        }
    }

    @Override
    public void hideProgress() {
        if (null != pd) {
            pd.hide();
        }
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void startAct() {
//        UrlHelper.BASE_URL = "http://" + getIpText()+":82";
        String url = getIpText();
        String[] netInfo = url.split(":");
        UrlHelper.BASE_URL = "http://" + netInfo[0] +":" + UrlHelper.DEFAULT_PORT;
        MainActivity.startSelf(this, MainActivity.class, null);
        this.finish();
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    /**
     * dialog的点击事件
     *
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            // pos  -- 确认  进入wifi页面
            case -1:
                WifiActivity.startSelf(this, WifiActivity.class, null);
                break;
            // neg  -- 取消
            case -2:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        pd.dismiss();
    }
}
