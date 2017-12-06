package com.mr.truck.bean;

import java.io.Serializable;

/**
 * Created by yanqi on 2017/9/21.
 */

public class BaojiaInfoBean implements Serializable {
    private String  totalchargeM;  //报价总金额
    private String  priceM;      //司机提的运费
    private String  loadfeeM;    //司机提的装车费
    private String  imforfee;    //信息费
    private String  feeremarkM; //费用说明 相当于是留言
    private String  UpdatePriceTime; //报价更新时间
    private String  cargopricesGUID; //报价表唯一码
    private String  unloadfeeM; //司机提的卸车费
    private String  otherfeeM;   //司机提的其他费
    private String  price;      //货主提的运费
    private String  loadfee;    //货主提的装车费
    private String  unloadfee;  //货主提的卸车费
    private String  totalcharge; //货主报价总费用
    private String  AvatarAddress;//货主头像
    private String  ownername;  //货主的名称
    private String  ownerphone; //货主的电话
    private String  feeremark;
    private String  status; //报价状态
    private String  ownerbill;//发货发货运单总数
    private String  Bidder; // 出价人
    private String  cargopriceState;//报价状态

    public BaojiaInfoBean(String totalchargeM,String priceM,String loadfeeM,String imforfee,String feeremarkM,
                          String UpdatePriceTime,String cargopricesGUID,String unloadfeeM,String otherfeeM,String price,
                          String loadfee,String unloadfee,String totalcharge,String AvatarAddress,String ownername,
                          String ownerphone,String feeremark,String status,String ownerbill,String Bidder,String cargopriceState)
    {
        this.totalchargeM = totalchargeM;
        this.priceM = priceM;
        this.loadfeeM = loadfeeM;
        this.imforfee = imforfee;
        this.feeremarkM = feeremarkM;
        this.UpdatePriceTime = UpdatePriceTime;
        this.cargopricesGUID = cargopricesGUID;
        this.unloadfeeM = unloadfeeM;
        this.otherfeeM = otherfeeM;
        this.price = price;
        this.loadfee = loadfee;
        this.unloadfee = unloadfee;
        this.totalcharge = totalcharge;
        this.AvatarAddress = AvatarAddress;
        this.ownername = ownername;
        this.ownerphone = ownerphone;
        this.feeremark = feeremark;
        this.status = status;
        this.ownerbill = ownerbill;
        this.Bidder = Bidder;
        this.cargopriceState = cargopriceState;

    }

    public String getBidder() {
        return Bidder;
    }

    public String getCargopriceState() {
        return cargopriceState;
    }

    public String getOwnerbill() {
        return ownerbill;
    }

    public String getStatus() {
        return status;
    }

    public String getFeeremark() {
        return feeremark;
    }

    public String getCargopricesGUID() {
        return cargopricesGUID;
    }

    public String getFeeremarkM() {
        return feeremarkM;
    }

    public String getImforfee() {
        return imforfee;
    }

    public String getLoadfeeM() {
        return loadfeeM;
    }

    public String getOtherfeeM() {
        return otherfeeM;
    }

    public String getPriceM() {
        return priceM;
    }

    public String getTotalchargeM() {
        return totalchargeM;
    }

    public String getUnloadfeeM() {
        return unloadfeeM;
    }

    public String getUpdatePriceTime() {
        return UpdatePriceTime;
    }

    public String getLoadfee() {
        return loadfee;
    }

    public String getPrice() {
        return price;
    }

    public String getUnloadfee() {
        return unloadfee;
    }

    public String getTotalcharge() {
        return totalcharge;
    }

    public String getAvatarAddress() {
        return AvatarAddress;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getOwnerphone() {
        return ownerphone;
    }
}
