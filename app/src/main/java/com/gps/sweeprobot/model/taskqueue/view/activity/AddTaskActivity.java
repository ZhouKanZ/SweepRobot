package com.gps.sweeprobot.model.taskqueue.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.base.BaseActivity;
import com.gps.sweeprobot.base.BasePresenter;
import com.gps.sweeprobot.database.GpsMapBean;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/8/18 0018
 * @Descriptiong : xxx
 */

public class AddTaskActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_task_name)
    EditText etTaskName;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.sp_map)
    Spinner spMap;
    @BindView(R.id.btn_edit)
    Button btnEdit;

    ArrayAdapter<CharSequence> taskAdapter;
    List<GpsMapBean> gpsMapBeanList;
    private int taskType = -1;
    private int mapId = -1;


    @Override
    protected TextView getTitleTextView() {
        return title;
    }

    @Override
    protected String getTitleText() {
        return "新建任务";
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

        setLeftVisiable(true);

        taskAdapter = ArrayAdapter.createFromResource(this,
                R.array.taskType, android.R.layout.simple_spinner_item);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(taskAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                taskType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                taskType = 0;
            }
        });

        gpsMapBeanList = DataSupport.findAll(GpsMapBean.class);
        spMap.setAdapter(new GpsMapSpinnerAdapter(gpsMapBeanList, this));
        spMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mapId = gpsMapBeanList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mapId = gpsMapBeanList.get(0).getId();
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addtask;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.iv_back, R.id.btn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.btn_edit:

                /* todo typeId mapId != -1 */

                Bundle bundle = new Bundle();
                bundle.putInt(EditNaveTaskActivity.MAP_ID_KEY,mapId);
                bundle.putInt(EditNaveTaskActivity.TYPE_ID,taskType);
                EditNaveTaskActivity.startSelf(this,EditNaveTaskActivity.class,bundle);
                break;
        }
    }


    public class GpsMapSpinnerAdapter extends BaseAdapter {


        private List<GpsMapBean> gpsMapBeanList;
        private Context ctx;
        private LayoutInflater inflater;

        public GpsMapSpinnerAdapter(List<GpsMapBean> gpsMapBeanList, Context ctx) {
            this.gpsMapBeanList = gpsMapBeanList;
            this.ctx = ctx;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount() {
            return gpsMapBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return gpsMapBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GpsMapBean gpsMapBean = (GpsMapBean) getItem(position);

            convertView = inflater.inflate(R.layout.spinner_item_gpsmap, parent, false);
            if (null != convertView) {
                TextView tvGps = (TextView) convertView.findViewById(R.id.tv_gps);
                tvGps.setText(gpsMapBean.getName());
            }

            return convertView;
        }

    }

}
