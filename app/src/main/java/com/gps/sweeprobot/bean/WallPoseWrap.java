package com.gps.sweeprobot.bean;

import java.util.List;

/**
 * Create by WangJun on 2017/8/25
 */

public class WallPoseWrap {

    private WallPose edit_wall;

    public WallPose getEdit_wall() {
        return edit_wall;
    }

    public void setEdit_wall(WallPose edit_wall) {
        this.edit_wall = edit_wall;
    }

    public static class WallPose{

        private int mapid;
        private String mapname;
        private List<Integer> wall_id;

        public int getMapid() {
            return mapid;
        }

        public void setMapid(int mapid) {
            this.mapid = mapid;
        }

        public String getMapname() {
            return mapname;
        }

        public void setMapname(String mapname) {
            this.mapname = mapname;
        }

        public List<Integer> getWall_id() {
            return wall_id;
        }

        public void setWall_id(List<Integer> wall_id) {
            this.wall_id = wall_id;
        }
    }
}
