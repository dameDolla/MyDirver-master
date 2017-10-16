package com.app.gaolonglong.fragmenttabhost.bean;

import java.io.Serializable;

/**
 * Created by yanqi on 2017/9/15.
 */

public class ToSrcDetailBean implements Serializable {
    private String fromSite;
    private String toSite;
    private String ownername;
    private String cargotype;
    private String preloadtime;
    private String AvatarAddress;
    private String creditlevel;
    private String qty;
    private String unit;
    private String tel;
    private String driverdeposit;
    private String ownerguid;
    private String billsGUID;
    private String driverGUID;
    private String drivername;
    private String companyGUID;
    private String company;
    private String truckno;
    private String trucklength;
    private String caragoGUID;
    private String load;
    private String unload;
    private String FromDetailedAddress;
    private String ToDetailedAddress;
    private String trucklengthHZ;
    private String trucktypeHZ;
    private String trucktype;
    private String loadaddHZ;
    private String arrivedaddHZ;

    public ToSrcDetailBean(String fromSite,String toSite,String ownername,String cargotype,String preloadtime,String avatarAddress,String creditlevel,String qty,String unit,String tel,String driverdeposit,
                           String ownerguid,String billsGUID,String driverGUID,String drivername,String companyGUID,String company,String truckno,String trucklength,String load,String unload,String FromDetailedAddress,
                           String ToDetailedAddress,String trucklengthHZ,String trucktypeHZ,String trucktype,String loadaddHZ,String arrivedaddHZ)
    {
        this.fromSite = fromSite;
        this.toSite = toSite;
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
        this.company = company;
        this.truckno = truckno;
        this.trucklength = trucklength;
        this.driverdeposit = driverdeposit;
        this.load = load;
        this.unload = unload;
        this.FromDetailedAddress = FromDetailedAddress;
        this.ToDetailedAddress = ToDetailedAddress;
        this.trucklengthHZ = trucklengthHZ;
        this.trucktypeHZ = trucktypeHZ;
        this.trucktype = trucktype;
        this.loadaddHZ = loadaddHZ;
        this.arrivedaddHZ = arrivedaddHZ;
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

    public String getFromSite() {
        return fromSite;
    }

    public String getToSite() {
        return toSite;
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

    public String getDriverdeposit() {
        return driverdeposit;
    }

    public String getBillsGUID() {
        return billsGUID;
    }

    public String getCompany() {
        return company;
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

    public String getCaragoGUID() {
        return caragoGUID;
    }

    public String getLoad() {
        return load;
    }

    public String getUnload() {
        return unload;
    }

    public String getFromDetailedAddress() {
        return FromDetailedAddress;
    }

    public String getToDetailedAddress() {
        return ToDetailedAddress;
    }
}
