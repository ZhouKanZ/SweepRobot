package com.gps.sweeprobot.model.mapmanager.contract;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import com.gps.sweeprobot.database.MyPointF;
import com.gps.sweeprobot.model.mapmanager.bean.MapListBean;
import com.gps.sweeprobot.mvp.IView;

import java.util.List;

/**
 * Create by WangJun on 2017/8/3
 */

public class MapEditContract {

    public static final int TYPE_POINT = 1;
    public static final int TYPE_OBSTACLE = 2;

    public interface presenter{}

    public interface view extends IView {
        /**
         * 经过矩阵变化的相对于图片的坐标值x,y
         */
        void addPoint(float x,float y,String name);

        //
        void getData(Drawable drawable, List<MapListBean> data);

        //获得地图图片
        void getData(Bitmap map);

        //加载dialog
        void inflateActionView();

        //标记点列表
        void setActionRecyclerView();

        //加载命名的dialog
        void createInputNameDialog(String titleText);

        //设置移除动画
        void setRemoveAnimation();

        /**
         * 未经过矩阵变化的相对于屏幕的坐标值x,y
         */
        void addPointWrapper(float screenX, float screenY, String name);

        //刷新adapter
        void updateAdapter(List actionList);

        void showToast();

        //设置虚拟墙的多边形顶点
        void setObstacleRect(PointF pointF);

        //更新标记点名称
        void updateName(String name,int position,int type);

        //移除标记点
        void removePoint(String name,int position);

        //设置虚拟墙名称
        void setObstacleName(String name);

        //添加虚拟墙
        void addObstacle(List<MyPointF> data,String name);

        //删除虚拟墙
        void removeObstacle(String name,int position);

    }
}
