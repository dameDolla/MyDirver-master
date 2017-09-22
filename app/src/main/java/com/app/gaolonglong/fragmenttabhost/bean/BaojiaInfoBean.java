package com.app.gaolonglong.fragmenttabhost.bean;

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

    public BaojiaInfoBean(String totalchargeM,String priceM,String loadfeeM,String imforfee,String feeremarkM,
                          String UpdatePriceTime,String cargopricesGUID,String unloadfeeM,String otherfeeM,String price,
                          String loadfee,String unloadfee)
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
}
