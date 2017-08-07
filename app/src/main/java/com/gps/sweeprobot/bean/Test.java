package com.gps.sweeprobot.bean;

public class Test{

    /**
     * header : {}
     * goal_id : {}
     * goal : {"target_pose":{"header":{"frame_id":"xxx"},"pose":{"position":{"x":0,"y":0,"z":0},"orientation":{"x":0,"y":0,"z":0,"w":0}}}}
     */

    private HeaderBean header;
    private GoalIdBean goal_id;
    private GoalBean goal;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public GoalIdBean getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(GoalIdBean goal_id) {
        this.goal_id = goal_id;
    }

    public GoalBean getGoal() {
        return goal;
    }

    public void setGoal(GoalBean goal) {
        this.goal = goal;
    }

    public static class HeaderBean {

        private String frame_id;

        public String getFrame_id() {
            return frame_id;
        }

        public void setFrame_id(String frame_id) {
            this.frame_id = frame_id;
        }
    }

    public static class GoalIdBean {
    }

    public static class GoalBean {
        /**
         * target_pose : {"header":{"frame_id":"xxx"},"pose":{"position":{"x":0,"y":0,"z":0},"orientation":{"x":0,"y":0,"z":0,"w":0}}}
         */

        private TargetPoseBean target_pose;

        public TargetPoseBean getTarget_pose() {
            return target_pose;
        }

        public void setTarget_pose(TargetPoseBean target_pose) {
            this.target_pose = target_pose;
        }

        public static class TargetPoseBean {
            /**
             * header : {"frame_id":"xxx"}
             * pose : {"position":{"x":0,"y":0,"z":0},"orientation":{"x":0,"y":0,"z":0,"w":0}}
             */

            private HeaderBeanX header;
            private PoseBean pose;

            public HeaderBeanX getHeader() {
                return header;
            }

            public void setHeader(HeaderBeanX header) {
                this.header = header;
            }

            public PoseBean getPose() {
                return pose;
            }

            public void setPose(PoseBean pose) {
                this.pose = pose;
            }

            public static class HeaderBeanX {
                /**
                 * frame_id : xxx
                 */

                private String frame_id;

                public String getFrame_id() {
                    return frame_id;
                }

                public void setFrame_id(String frame_id) {
                    this.frame_id = frame_id;
                }
            }

            public static class PoseBean {
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

                    private float x;
                    private float y;
                    private float z;

                    public PositionBean() {
                    }

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

                public static class OrientationBean {
                    /**
                     * x : 0
                     * y : 0
                     * z : 0
                     * w : 0
                     */

                    private float x;
                    private float y;
                    private float z;
                    private float w;

                    public OrientationBean() {
                    }

                    public OrientationBean(float x, float y, float z, float w) {
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
}