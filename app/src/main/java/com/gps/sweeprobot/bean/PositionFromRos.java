package com.gps.sweeprobot.bean;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/27 0027
 * @Descriptiong : xxx
 */

public class PositionFromRos {


    /**
     * angle : 0
     * createdAt : 2017-03-25 15:57:58
     * gridX : 604
     * gridY : 223
     * id : 0
     * mapId : 4fec88ea-3c2c-4640-a6fd-ac8a728e9ddd
     * mapName : 1
     * name : Origin
     * type : 0
     * worldPose : {"orientation":{"w":1,"x":0,"y":0,"z":0},"position":{"x":-0.155,"y":0,"z":0}}
     */

    private int angle;
    private String createdAt;
    private int gridX;
    private int gridY;
    private int id;
    private String mapId;
    private String mapName;
    private String name;
    private int type;
    private WorldPoseBean worldPose;

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WorldPoseBean getWorldPose() {
        return worldPose;
    }

    public void setWorldPose(WorldPoseBean worldPose) {
        this.worldPose = worldPose;
    }

    public static class WorldPoseBean {
        /**
         * orientation : {"w":1,"x":0,"y":0,"z":0}
         * position : {"x":-0.155,"y":0,"z":0}
         */

        private OrientationBean orientation;
        private PositionBean position;

        public OrientationBean getOrientation() {
            return orientation;
        }

        public void setOrientation(OrientationBean orientation) {
            this.orientation = orientation;
        }

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public static class OrientationBean {
            /**
             * w : 1
             * x : 0
             * y : 0
             * z : 0
             */

            private int w;
            private int x;
            private int y;
            private int z;

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

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

        public static class PositionBean {
            /**
             * x : -0.155
             * y : 0
             * z : 0
             */

            private double x;
            private int y;
            private int z;

            public double getX() {
                return x;
            }

            public void setX(double x) {
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
    }
}
