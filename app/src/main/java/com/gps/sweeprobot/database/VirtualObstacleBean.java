package com.gps.sweeprobot.database;

import com.gps.sweeprobot.bean.IAction;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJun on 2017/8/4
 * 虚拟墙数据类
 */

public class VirtualObstacleBean extends IAction{

    private int id;
    private int mapId;
    private String name;
    private String createDate;
    private String description;
    private List<MyPointF> myPointFs = new ArrayList<MyPointF>();

    public int getId() {
        return id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MyPointF> getMyPointFs() {
            return DataSupport.where("VirtualObstacleBean_id = ?",String.valueOf(id)).find(MyPointF.class);
    }

    public void setMyPointFs(List<MyPointF> myPointFs) {
        this.myPointFs = myPointFs;
    }
}
