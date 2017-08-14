package com.home.we.yuandiary.bean;

/**
 * Created by pactera on 2017/8/14.
 */

public class LoginFailed {


    /**
     * flag : 402
     * data : 密码不正确
     * msg :
     */

    private String flag;
    private String data;
    private String msg;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
