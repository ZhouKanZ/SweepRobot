package com.gps.sweeprobot.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/28 0028
 * @Descriptiong : xxx
 */

public class NaviPosition {

    /**
     * birthtime :
     * angle : 0
     * gridX : 0
     * gridY : 0
     * id : 0
     * name : zk
     * mapid : 12
     * mapname : mapname
     * type : 1
     * worldposition : {"position":{"x":0,"y":0,"z":0},"orientation":{"x":0,"y":0,"z":0,"w":0}}
     */

    private String birthtime;
    private int angle;
    private int gridX;
    private int gridY;
    private int id;
    private String name;
    private int mapid;
    private String mapname;
    private int type;
    private WorldpositionBean worldposition;

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WorldpositionBean getWorldposition() {
        return worldposition;
    }

    public void setWorldposition(WorldpositionBean worldposition) {
        this.worldposition = worldposition;
    }

    public static class WorldpositionBean {
        /**
         * position : {"x":0,"y":0,"z":0}
         * orientation : {"x":0,"y":0,"z":0,"w":0}
         */

        private PositionBean position;
        private OrientationBean orientation;

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public OrientationBean getOrientation() {
            return orientation;
        }

        public void setOrientation(OrientationBean orientation) {
            this.orientation = orientation;
        }

        public static class PositionBean {
            /**
             * x : 0
             * y : 0
             * z : 0
             */

            private int x;
            private int y;
            private int z;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getZ() {
                return z;
            }

            public void setZ(int z) {
                this.z = z;
            }
        }

        public static class OrientationBean {
            /**
             * x : 0
             * y : 0
             * z : 0
             * w : 0
             */

            private int x;
            private int y;
            private int z;
            private int w;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getZ() {
                return z;
            }

            public void setZ(int z) {
                this.z = z;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }
        }
    }
}
