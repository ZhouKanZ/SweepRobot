package com.gps.sweeprobot.bean;


import java.util.Date;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/27 0027
 * @Descriptiong : xxx
 */

public class Position {

    private Header header;
    private GoalID goal_id;
    private MoveBaseGoal goal;

    public Position(Header header, GoalID goal_id, MoveBaseGoal goal) {
        this.header = header;
        this.goal_id = goal_id;
        this.goal = goal;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public GoalID getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(GoalID goal_id) {
        this.goal_id = goal_id;
    }

    public MoveBaseGoal getGoal() {
        return goal;
    }

    public void setGoal(MoveBaseGoal goal) {
        this.goal = goal;
    }

    public class Header{
        private Date stamp;
        private String frame_id;

        public Header(Date stamp, String frame_id) {
            this.stamp = stamp;
            this.frame_id = frame_id;
        }

        public Date getStamp() {
            return stamp;
        }

        public void setStamp(Date stamp) {
            this.stamp = stamp;
        }

        public String getFrame_id() {
            return frame_id;
        }

        public void setFrame_id(String frame_id) {
            this.frame_id = frame_id;
        }
    }

    public class GoalID{
        private Date stamp;
        private String id;

        public GoalID(Date stamp, String id) {
            this.stamp = stamp;
            this.id = id;
        }

        public Date getStamp() {
            return stamp;
        }

        public void setStamp(Date stamp) {
            this.stamp = stamp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class MoveBaseGoal{

        private PoseStamped target_pose;

        public MoveBaseGoal(PoseStamped target_pose) {
            this.target_pose = target_pose;
        }

        public PoseStamped getTarget_pose() {
            return target_pose;
        }

        public void setTarget_pose(PoseStamped target_pose) {
            this.target_pose = target_pose;
        }
    }

    public class PoseStamped{
        private Header header;
        private Pose pose;

        public class Pose{

            private Point position;
            private Quaternion orientation;

            public Pose(Point position, Quaternion orientation) {
                this.position = position;
                this.orientation = orientation;
            }

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

            public class Point{

                private float x;
                private float y;
                private float z;

                public Point(float x, float y, float z) {
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

            public class Quaternion {
                private float x;
                private float y;
                private float z;
                private float w;

                public Quaternion(float x, float y, float z, float w) {
                    this.x = x;
                    this.y = y;
                    this.z = z;
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

                public float getW() {
                    return w;
                }

                public void setW(float w) {
                    this.w = w;
                }
            }

        }

    }

}
