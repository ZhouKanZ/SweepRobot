package com.gps.sweeprobot.bean;

public class Position {
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

            private float w;
            private float x;
            private float y;
            private float z;

            public OrientationBean(float w, float x, float y, float z) {
                this.w = w;
                this.x = x;
                this.y = y;
                this.z = z;
            }

            public float getW() {
                return w;
            }

            public void setW(float w) {
                this.w = w;
            }

            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            public float getY() {
                return y;
            }

            public void setY(float y) {
                this.y = y;
            }

            public float getZ() {
                return z;
            }

            public void setZ(float z) {
                this.z = z;
            }
        }

        public static class PositionBean {
            /**
             * x : -0.155
             * y : 0
             * z : 0
             */

            private float x;
            private float y;
            private float z;

            public PositionBean(float x, float y, float z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }

            public float getX() {
                return x;
            }

            public void setX(float x) {
                this.x = x;
            }

            public float getY() {
                return y;
            }

            public void setY(float y) {
                this.y = y;
            }

            public float getZ() {
                return z;
            }

            public void setZ(float z) {
                this.z = z;
            }
        }
    }

}
