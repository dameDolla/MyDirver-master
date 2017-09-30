package com.app.gaolonglong.fragmenttabhost.bean;

import java.io.Serializable;

/**
 * Created by yanqi on 2017/9/29.
 */

public class MissionDetailBean implements Serializable{

    private String billsGUID;
    private String ownername;
    private String ownerphone;
    private String preloadtime;  //预计装车时间
    private String FromDetailedAddress;
    private String ToDetailedAddress;
    private String billNumber;  //运单号
    private String dealprice;  //运费
    private String loadfee;
    private String unloadfee;
    private String AvatarAddress;
    private String status;
    private String signtime;

    public MissionDetailBean(String billsGUID,String ownername,String ownerphone,String preloadtime,String FromDetailedAddress,
                             String ToDetailedAddress,String billNumber,String dealprice,String loadfee,
                             String unloadfee,String AvatarAddress,String status,String signtime)
    {
        this.billsGUID = billsGUID;
        this.ownername = ownername;
        this.ownerphone = ownerphone;
        this.preloadtime = preloadtime;
        this.FromDetailedAddress = FromDetailedAddress;
        this.ToDetailedAddress = ToDetailedAddress;
        this.billNumber = billNumber;
        this.dealprice = dealprice;
        this.loadfee = loadfee;
        this.unloadfee = unloadfee;
        this.AvatarAddress = AvatarAddress;
        this.status = status;
        this.signtime = signtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSigntime() {
        return signtime;
    }

    public String getStatus() {
        return status;
    }

    public String getToDetailedAddress() {
        return ToDetailedAddress;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getAvatarAddress() {
        return AvatarAddress;
    }

    public String getBillsGUID() {
        return billsGUID;
    }

    public String getDealprice() {
        return dealprice;
    }

    public String getFromDetailedAddress() {
        return FromDetailedAddress;
    }

    public String getLoadfee() {
        return loadfee;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getOwnerphone() {
        return ownerphone;
    }

    public String getPreloadtime() {
        return preloadtime;
    }

    public String getUnloadfee() {
        return unloadfee;
    }
}
