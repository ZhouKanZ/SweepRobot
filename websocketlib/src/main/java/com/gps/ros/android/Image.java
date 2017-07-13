package com.gps.ros.android;

import java.util.List;

/**
 * @Author : zhoukan
 * @CreateDate : 2017/7/6 0006
 * @Descriptiong : xxx
 */

public class Image {


    /**
     * topic : camera/image
     * msg : {"encoding":"bgr8","height":800,"header":{"stamp":{"secs":0,"nsecs":0},"frame_id":"","seq":2522},"step":4311,"data":[]}
     */

    private String topic;
    private MsgBean msg;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * encoding : bgr8
         * height : 800
         * header : {"stamp":{"secs":0,"nsecs":0},"frame_id":"","seq":2522}
         * step : 4311
         * data : []
         */

        private String encoding;
        private int height;
        private HeaderBean header;
        private int step;
        private List<String> data;

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public HeaderBean getHeader() {
            return header;
        }

        public void setHeader(HeaderBean header) {
            this.header = header;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public static class HeaderBean {
            /**
             * stamp : {"secs":0,"nsecs":0}
             * frame_id :
             * seq : 2522
             */

            private StampBean stamp;
            private String frame_id;
            private int seq;

            public StampBean getStamp() {
                return stamp;
            }

            public void setStamp(StampBean stamp) {
                this.stamp = stamp;
            }

            public String getFrame_id() {
                return frame_id;
            }

            public void setFrame_id(String frame_id) {
                this.frame_id = frame_id;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public static class StampBean {
                /**
                 * secs : 0
                 * nsecs : 0
                 */

                private int secs;
                private int nsecs;

                public int getSecs() {
                    return secs;
                }

                public void setSecs(int secs) {
                    this.secs = secs;
                }

                public int getNsecs() {
                    return nsecs;
                }

                public void setNsecs(int nsecs) {
                    this.nsecs = nsecs;
                }
            }
        }
    }
}
