package com.mr.truck.bean;

import java.io.Serializable;

/**
 * Created by yanqi on 2017/11/17.
 */

public class ToJiaoYiDetailBean implements Serializable {

    private String tradeid;
    private String trademarksGUID;
    private String tradetime;
    private String tradetype;
    private String tradeamount;
    private String remark;
    private String OrderNumber;
    private String States;

    public ToJiaoYiDetailBean(String tradeid,String trademarksGUID,String tradetime,String tradetype,
                              String tradeamount,String remark,String OrderNumber,String States)
    {
        this.tradeid = tradeid;
        this.trademarksGUID = trademarksGUID;
        this.tradetime = tradetime;
        this.tradetype = tradetype;
        this.tradeamount = tradeamount;
        this.remark = remark;
        this.OrderNumber = OrderNumber;
        this.States = States;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public String getRemark() {
        return remark;
    }

    public String getStates() {
        return States;
    }

    public String getTradeamount() {
        return tradeamount;
    }

    public String getTradeid() {
        return tradeid;
    }

    public String getTrademarksGUID() {
        return trademarksGUID;
    }

    public String getTradetime() {
        return tradetime;
    }

    public String getTradetype() {
        return tradetype;
    }
}
