package com.mr.truck.bean;

/**
 * Created by yanqi on 2017/8/21.
 */

public class TestBean {

    /**
     * errorCode : 1
     * errorMsg : 发送成功
     * data : null
     */

    private String from;
    private String to;
    public TestBean(String from,String to)
    {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
