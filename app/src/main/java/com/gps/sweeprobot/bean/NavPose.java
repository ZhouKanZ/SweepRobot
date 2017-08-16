package com.gps.sweeprobot.bean;

/**
 * Create by WangJun on 2017/8/9
 */

public class NavPose {

    private String birthtime;
    private String name;
    private String mapname;
    private float gridX;
    private float gridY;
    private int id;
    private int mapid;
    private int type;
    private Pose worldposition;

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapname() {
        return mapname;
    }

    public void setMapname(String mapname) {
        this.mapname = mapname;
    }

    public float getGridX() {
        return gridX;
    }

    public void setGridX(float gridX) {
        this.gridX = gridX;
    }

    public float getGridY() {
        return gridY;
    }

    public void setGridY(float gridY) {
        this.gridY = gridY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapid() {
        return mapid;
    }

    public void setMapid(int mapid) {
        this.mapid = mapid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Pose getWorldposition() {
        return worldposition;
    }

    public void setWorldposition(Pose worldposition) {
        this.worldposition = worldposition;
    }

    public static class Pose{

        private Point position;
        private Quaternion orientation;

        public Point getPosition() {
            return position;
        }

        public void setPosition(Point position) {
            this.position = position;
        }

        public Quaternion getOrientation() {
            return orientation;
        }

        public void setOrientation(Quaternion orientation) {
            this.orientation = orientation;
        }

        public static class Point{

            private float x,y,z=0;

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

        public static class Quaternion{

            private float x=0,y=0,z=0,w=1;

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

            public float getW() {
                return w;
            }

            public void setW(float w) {
                this.w = w;
            }
        }
    }

}
