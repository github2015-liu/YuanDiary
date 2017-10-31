package com.home.we.rxjavademo;

import java.util.List;

/**
 * Created by pactera on 2017/8/15.
 */

public class QA {

    /**
     * flag : 001
     * data : [{"id":12,"uid":2,"issue":"为什么这么多人喜欢玩德玛西亚","picstr":"https://vi2.6rooms.com/live/2016/11/11/23/1010v1478876963004558337_s.jpg,https://vi2.6rooms.com/live/2017/05/18/10/1010v1495076009451267897_s.jpg,https://vi0.6rooms.com/live/2017/06/28/03/1010v1498590079354057266_s.jpg","praisenum":0,"tm":1499827260},{"id":13,"uid":8,"issue":"为什么这么多人喜欢玩德玛西亚","picstr":"https://vi2.6rooms.com/live/2016/11/11/23/1010v1478876963004558337_s.jpg,https://vi2.6rooms.com/live/2017/05/18/10/1010v1495076009451267897_s.jpg,https://vi0.6rooms.com/live/2017/06/28/03/1010v1498590079354057266_s.jpg","praisenum":0,"tm":1499827260},{"id":14,"uid":4,"issue":"为什么这么多人喜欢玩德玛西亚","picstr":"https://vi2.6rooms.com/live/2016/11/11/23/1010v1478876963004558337_s.jpg,https://vi2.6rooms.com/live/2017/05/18/10/1010v1495076009451267897_s.jpg,https://vi0.6rooms.com/live/2017/06/28/03/1010v1498590079354057266_s.jpg","praisenum":0,"tm":1499827260},{"id":15,"uid":1,"issue":"为什么这么多人喜欢玩德玛西亚","picstr":"https://vi2.6rooms.com/live/2016/11/11/23/1010v1478876963004558337_s.jpg,https://vi2.6rooms.com/live/2017/05/18/10/1010v1495076009451267897_s.jpg,https://vi0.6rooms.com/live/2017/06/28/03/1010v1498590079354057266_s.jpg","praisenum":0,"tm":1499827260}]
     * msg :
     */

    private String flag;
    private String msg;
    private List<DataBean> data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * uid : 2
         * issue : 为什么这么多人喜欢玩德玛西亚
         * picstr : https://vi2.6rooms.com/live/2016/11/11/23/1010v1478876963004558337_s.jpg,https://vi2.6rooms.com/live/2017/05/18/10/1010v1495076009451267897_s.jpg,https://vi0.6rooms.com/live/2017/06/28/03/1010v1498590079354057266_s.jpg
         * praisenum : 0
         * tm : 1499827260
         */

        private int id;
        private int uid;
        private String issue;
        private String picstr;
        private int praisenum;
        private int tm;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getPicstr() {
            return picstr;
        }

        public void setPicstr(String picstr) {
            this.picstr = picstr;
        }

        public int getPraisenum() {
            return praisenum;
        }

        public void setPraisenum(int praisenum) {
            this.praisenum = praisenum;
        }

        public int getTm() {
            return tm;
        }

        public void setTm(int tm) {
            this.tm = tm;
        }
    }
}
