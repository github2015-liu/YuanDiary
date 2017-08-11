package com.home.we.yuandiary.bean;

/**
 * Created by pactera on 2017/8/10.
 */

public class LoginFedBack {


    /**
     * flag : 001
     * data : {"uid":53,"alias":"小宝","sex":0,"avatar":"","company":"","job":"","reputation":0,"mobile":"","email":null,"city":"","address":null,"marry":0,"credit":300,"idcard":"0","age":0,"money":0,"worktm":0,"info":null,"tm":1501139895}
     * msg :
     */

    private String flag;
    private DataBean data;
    private String msg;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * uid : 53
         * alias : 小宝
         * sex : 0
         * avatar :
         * company :
         * job :
         * reputation : 0
         * mobile :
         * email : null
         * city :
         * address : null
         * marry : 0
         * credit : 300
         * idcard : 0
         * age : 0
         * money : 0
         * worktm : 0
         * info : null
         * tm : 1501139895
         */

        private int uid;
        private String alias;
        private int sex;
        private String avatar;
        private String company;
        private String job;
        private int reputation;
        private String mobile;
        private Object email;
        private String city;
        private Object address;
        private int marry;
        private int credit;
        private String idcard;
        private int age;
        private int money;
        private int worktm;
        private Object info;
        private int tm;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public int getMarry() {
            return marry;
        }

        public void setMarry(int marry) {
            this.marry = marry;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getWorktm() {
            return worktm;
        }

        public void setWorktm(int worktm) {
            this.worktm = worktm;
        }

        public Object getInfo() {
            return info;
        }

        public void setInfo(Object info) {
            this.info = info;
        }

        public int getTm() {
            return tm;
        }

        public void setTm(int tm) {
            this.tm = tm;
        }
    }
}
