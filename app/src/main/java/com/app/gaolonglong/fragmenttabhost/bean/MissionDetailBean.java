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
    private String signby;
    private String drivername;
    private String driverphone;
    private String driverGUID;
    private String truckno;
    private String signPhone;
    private String Consignee; //收货人
    private String ConsigneePhone;//收货人电话
    private String InsertDate;//运单生成的时间
    private String ArrivalLoadingTime;//到达装货时间
    private String loadtime;//实际装车时间
    private String arrivedtime;//司机提交的到达时间
    private String DepartureTime; //司机出发的时间
    private String cargotype; //货物详情
    private String loadaddHZ; //装车地点经纬度
    private String arrivedaddHZ; //到达地点经纬度


    public MissionDetailBean(String billsGUID,String ownername,String ownerphone,String preloadtime,String FromDetailedAddress,
                             String ToDetailedAddress,String billNumber,String dealprice,String loadfee,
                             String unloadfee,String AvatarAddress,String status,String signtime,String signby,String drivername,
                             String driverphone,String driverGUID,String truckno,String signPhone,String Consignee,String ConsigneePhone,
                             String InsertDate,String ArrivalLoadingTime,String loadtime,String arrivedtime,String DepartureTime,String cargotype,
                             String loadaddHZ,String arrivedaddHZ)
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
        this.signby = signby;
        this.drivername = drivername;
        this.driverphone = driverphone;
        this.driverGUID = driverGUID;
        this.truckno = truckno;
        this.signPhone = signPhone;
        this.Consignee = Consignee;
        this.ConsigneePhone = ConsigneePhone;
        this.InsertDate = InsertDate;
        this.ArrivalLoadingTime = ArrivalLoadingTime;
        this.loadtime = loadtime;
        this.arrivedtime = arrivedtime;
        this.DepartureTime = DepartureTime;
        this.cargotype = cargotype;
        this.arrivedaddHZ = arrivedaddHZ;
        this.loadaddHZ = loadaddHZ;
    }

    public String getArrivedaddHZ() {
        return arrivedaddHZ;
    }

    public String getLoadaddHZ() {
        return loadaddHZ;
    }

    public String getCargotype() {
        return cargotype;
    }

    public void setCargotype(String cargotype) {
        this.cargotype = cargotype;
    }

    public void setTruckno(String truckno) {
        this.truckno = truckno;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setArrivalLoadingTime(String arrivalLoadingTime) {
        ArrivalLoadingTime = arrivalLoadingTime;
    }

    public String getArrivalLoadingTime() {
        return ArrivalLoadingTime;
    }

    public void setArrivedtime(String arrivedtime) {
        this.arrivedtime = arrivedtime;
    }

    public String getArrivedtime() {
        return arrivedtime;
    }

    public String getInsertDate() {
        return InsertDate;
    }

    public void setLoadtime(String loadtime) {
        this.loadtime = loadtime;
    }

    public String getLoadtime() {
        return loadtime;
    }

    public String getConsignee() {
        return Consignee;
    }

    public String getConsigneePhone() {
        return ConsigneePhone;
    }

    public String getSignPhone() {
        return signPhone;
    }

    public String getTruckno() {
        return truckno;
    }

    public String getDriverGUID() {
        return driverGUID;
    }

    public String getSignby() {
        return signby;
    }

    public void setSignby(String signby) {
        this.signby = signby;
    }

    public void setSignPhone(String signPhone) {
        this.signPhone = signPhone;
    }

    public String getDrivername() {
        return drivername;
    }

    public String getDriverphone() {
        return driverphone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
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
