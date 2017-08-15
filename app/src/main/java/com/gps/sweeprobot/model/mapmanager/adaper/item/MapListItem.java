package com.gps.sweeprobot.model.mapmanager.adaper.item;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gps.sweeprobot.R;
import com.gps.sweeprobot.database.GpsMapBean;
import com.gps.sweeprobot.model.mapmanager.model.MapManagerModel;
import com.gps.sweeprobot.model.view.adapter.item.AdapterItem;
import com.gps.sweeprobot.utils.ToastManager;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Create by WangJun on 2017/7/18
 */

public class MapListItem implements AdapterItem<GpsMapBean> {

    @BindView(R.id.layout_map_list_iv)
    ImageView mapDrawable;

    @BindViews({R.id.layout_map_list_create_time, R.id.layout_map_list_name})
    List<TextView> names;

    private MapListItem.MOnItemClickListener listener;

    private MapListItem.RequestMapListener requestMapListener;

    public MapListItem(MOnItemClickListener listener) {
        this.listener = listener;
    }

    public void setRequestMapListener(RequestMapListener requestMapListener) {
        this.requestMapListener = requestMapListener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_map_list;
    }

    @Override
    public void bindViews(View root) {

        ButterKnife.bind(this, root);
    }

    @Override
    public void setViews(final RecyclerView.ViewHolder holder) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapListItem.this.listener.onItemClickListener(view, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public void handleData(GpsMapBean gpsMapBean, int position) {

        if (requestMapListener != null){
            requestMapListener.requestMap(gpsMapBean, new MapManagerModel.MapInfo() {
                @Override
                public void successInfo(Bitmap bitmap) {
                    mapDrawable.setImageBitmap(bitmap);
//                    bitmap.recycle();
                }

                @Override
                public void failInfo(Throwable e) {
                    ToastManager.showShort(e.getMessage());
                }
            });
        }
        names.get(1).setText(gpsMapBean.getName());
        names.get(0).setText(gpsMapBean.getDate());
    }

    public interface MOnItemClickListener {

        void onItemClickListener(View view, int position);
    }

    /**
     * 网络请求图片接口
     */
    public interface RequestMapListener{

        void requestMap(GpsMapBean gpsMapBean, MapManagerModel.MapInfo mapInfo);
    }
}
