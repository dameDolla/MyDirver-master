package com.app.gaolonglong.fragmenttabhost.bean;

import java.io.Serializable;

/**
 * Created by yanqi on 2017/9/15.
 */

public class ToSrcDetailBean implements Serializable {
    private String ownername;
    private String cargotype;
    private String preloadtime;
    private String AvatarAddress;
    private String creditlevel;
    private String qty;
    private String unit;
    private String tel;
    private String ownerguid;
    private String billsGUID;
    private String driverGUID;
    private String drivername;
    private String companyGUID;
    private String truckno;
    private String trucklength;
    private String FromDetailedAddress;
    private String ToDetailedAddress;
    private String trucklengthHZ;
    private String trucktypeHZ;
    private String trucktype;
    private String loadaddHZ;
    private String arrivedaddHZ;
    private String InvoiceType; //是否开发票
    private String UploadReceipt; //上传签收单
    private String PaperReceipt; //纸质回单
    private String company;
    private String myPriceStatus;
    private String ownerbill;
    private String remark; //货主留言


    public ToSrcDetailBean(String ownername,String cargotype,String preloadtime,String avatarAddress,String creditlevel,String qty,String unit,String tel, String ownerguid,String billsGUID,String driverGUID,String drivername,String companyGUID,String truckno,String trucklength,String FromDetailedAddress,
                           String ToDetailedAddress,String trucklengthHZ,String trucktypeHZ,String trucktype,String loadaddHZ,String arrivedaddHZ,String InvoiceType,String UploadReceipt,String PaperReceipt,String company,String myPriceStatus,String ownerbill,String remark)
    {
        this.ownername = ownername;
        this.cargotype = cargotype;
        this.preloadtime = preloadtime;
        this.AvatarAddress = avatarAddress;
        this.creditlevel = creditlevel;
        this.qty = qty;
        this.unit = unit;
        this.tel = tel;
        this.ownerguid = ownerguid;
        this.billsGUID = billsGUID;
        this.driverGUID = driverGUID;
        this.drivername = drivername;
        this.companyGUID = companyGUID;
        this.truckno = truckno;
        this.trucklength = trucklength;
        this.FromDetailedAddress = FromDetailedAddress;
        this.ToDetailedAddress = ToDetailedAddress;
        this.trucklengthHZ = trucklengthHZ;
        this.trucktypeHZ = trucktypeHZ;
        this.trucktype = trucktype;
        this.loadaddHZ = loadaddHZ;
        this.arrivedaddHZ = arrivedaddHZ;
        this.InvoiceType = InvoiceType;
        this.UploadReceipt = UploadReceipt;
        this.PaperReceipt = PaperReceipt;
        this.company = company;
        this.myPriceStatus = myPriceStatus;
        this.ownerbill = ownerbill;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getOwnerbill() {
        return ownerbill;
    }

    public String getMyPriceStatus() {
        return myPriceStatus;
    }

    public String getCompany() {
        return company;
    }

    public String getUploadReceipt() {
        return UploadReceipt;
    }

    public String getPaperReceipt() {
        return PaperReceipt;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public String getArrivedaddHZ() {
        return arrivedaddHZ;
    }

    public String getLoadaddHZ() {
        return loadaddHZ;
    }

    public String getTrucktype() {
        return trucktype;
    }

    public String getTrucktypeHZ() {
        return trucktypeHZ;
    }

    public String getTrucklengthHZ() {
        return trucklengthHZ;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getCargotype() {
        return cargotype;
    }

    public String getPreloadtime() {
        return preloadtime;
    }

    public String getAvatarAddress() {
        return AvatarAddress;
    }

    public String getCreditlevel() {
        return creditlevel;
    }

    public String getQty() {
        return qty;
    }

    public String getUnit() {
        return unit;
    }

    public String getTel() {
        return tel;
    }

    public String getBillsGUID() {
        return billsGUID;
    }

    public String getCompanyGUID() {
        return companyGUID;
    }

    public String getDriverGUID() {
        return driverGUID;
    }

    public String getDrivername() {
        return drivername;
    }

    public String getOwnerguid() {
        return ownerguid;
    }

    public String getTrucklength() {
        return trucklength;
    }

    public String getTruckno() {
        return truckno;
    }

    public String getFromDetailedAddress() {
        return FromDetailedAddress;
    }

    public String getToDetailedAddress() {
        return ToDetailedAddress;
    }
}
