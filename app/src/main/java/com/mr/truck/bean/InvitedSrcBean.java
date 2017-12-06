package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/11/14.
 */

public class InvitedSrcBean {


    /**
     * errorCode : 200
     * errorMsg : 查询完成！
     * data : [{"ownername":"何学龙","ownerphone":"18820930217","owneridGUID":"AE0BF6EEA2A54CE9A82A4A3EA02DE8F3","creditlevel":"0","AvatarAddress":"http://truck.pwings.net/uploadA\\2017\\11\\6\\AE0BF6EEA2A54CE9A82A4A3EA02DE8F320171106125735574.jpeg","billsGUID":"DB432844BDAC437DA37002F8D433A2E7","fromSite":"深圳市","toSite":"广州市","cargotype":"木箱家具","deposit":0,"qty":0,"unit":"0","preloadtime":"2017/11/15 6:00:00","trucktypea":"","trucktypeb":"","trucktypec":"","trucktyped":"","trucktypef":"","remark":" ","load":"","unload":"","FromDetailedAddress":"广东省深圳市龙华区龙华汽车站广场","ToDetailedAddress":"广东省广州市天河区广州东站","trucktypeHZ":"箱式带尾板","trucklengthHZ":"45英尺（HQ）","loadaddHZ":"114.030639,22.669979","arrivedaddHZ":"113.3249,23.150566","MyPriceStatus":"0","InvoiceType":1,"UploadReceipt":1,"PaperReceipt":1,"ownerbill":0,"dealprice":0,"FromSite1":"广东深圳市罗湖区市","ToSite1":"青海海东地区平安县市"}]
     */

    private String errorCode;
    private String errorMsg;
    private List<DataBean> data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ownername : 何学龙
         * ownerphone : 18820930217
         * owneridGUID : AE0BF6EEA2A54CE9A82A4A3EA02DE8F3
         * creditlevel : 0
         * AvatarAddress : http://truck.pwings.net/uploadA\2017\11\6\AE0BF6EEA2A54CE9A82A4A3EA02DE8F320171106125735574.jpeg
         * billsGUID : DB432844BDAC437DA37002F8D433A2E7
         * fromSite : 深圳市
         * toSite : 广州市
         * cargotype : 木箱家具
         * deposit : 0.0
         * qty : 0
         * unit : 0
         * preloadtime : 2017/11/15 6:00:00
         * trucktypea :
         * trucktypeb :
         * trucktypec :
         * trucktyped :
         * trucktypef :
         * remark :
         * load :
         * unload :
         * FromDetailedAddress : 广东省深圳市龙华区龙华汽车站广场
         * ToDetailedAddress : 广东省广州市天河区广州东站
         * trucktypeHZ : 箱式带尾板
         * trucklengthHZ : 45英尺（HQ）
         * loadaddHZ : 114.030639,22.669979
         * arrivedaddHZ : 113.3249,23.150566
         * MyPriceStatus : 0
         * InvoiceType : 1
         * UploadReceipt : 1
         * PaperReceipt : 1
         * ownerbill : 0
         * dealprice : 0.0
         * FromSite1 : 广东深圳市罗湖区市
         * ToSite1 : 青海海东地区平安县市
         */

        private String ownername;
        private String ownerphone;
        private String owneridGUID;
        private String creditlevel;
        private String AvatarAddress;
        private String billsGUID;
        private String fromSite;
        private String toSite;
        private String cargotype;
        private double deposit;
        private int qty;
        private String unit;
        private String preloadtime;
        private String trucktypea;
        private String trucktypeb;
        private String trucktypec;
        private String trucktyped;
        private String trucktypef;
        private String remark;
        private String load;
        private String unload;
        private String FromDetailedAddress;
        private String ToDetailedAddress;
        private String trucktypeHZ;
        private String trucklengthHZ;
        private String loadaddHZ;
        private String arrivedaddHZ;
        private String MyPriceStatus;
        private int InvoiceType;
        private int UploadReceipt;
        private int PaperReceipt;
        private int ownerbill;
        private double dealprice;
        private String FromSite1;
        private String ToSite1;
        private String emptytime;
        private String InvitationID;
        private String prearrivetime;

        public String getPrearrivetime() {
            return prearrivetime;
        }

        public String getInvitationID() {
            return InvitationID;
        }

        public String getEmptytime() {
            return emptytime;
        }

        public String getOwnername() {
            return ownername;
        }

        public void setOwnername(String ownername) {
            this.ownername = ownername;
        }

        public String getOwnerphone() {
            return ownerphone;
        }

        public void setOwnerphone(String ownerphone) {
            this.ownerphone = ownerphone;
        }

        public String getOwneridGUID() {
            return owneridGUID;
        }

        public void setOwneridGUID(String owneridGUID) {
            this.owneridGUID = owneridGUID;
        }

        public String getCreditlevel() {
            return creditlevel;
        }

        public void setCreditlevel(String creditlevel) {
            this.creditlevel = creditlevel;
        }

        public String getAvatarAddress() {
            return AvatarAddress;
        }

        public void setAvatarAddress(String AvatarAddress) {
            this.AvatarAddress = AvatarAddress;
        }

        public String getBillsGUID() {
            return billsGUID;
        }

        public void setBillsGUID(String billsGUID) {
            this.billsGUID = billsGUID;
        }

        public String getFromSite() {
            return fromSite;
        }

        public void setFromSite(String fromSite) {
            this.fromSite = fromSite;
        }

        public String getToSite() {
            return toSite;
        }

        public void setToSite(String toSite) {
            this.toSite = toSite;
        }

        public String getCargotype() {
            return cargotype;
        }

        public void setCargotype(String cargotype) {
            this.cargotype = cargotype;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPreloadtime() {
            return preloadtime;
        }

        public void setPreloadtime(String preloadtime) {
            this.preloadtime = preloadtime;
        }

        public String getTrucktypea() {
            return trucktypea;
        }

        public void setTrucktypea(String trucktypea) {
            this.trucktypea = trucktypea;
        }

        public String getTrucktypeb() {
            return trucktypeb;
        }

        public void setTrucktypeb(String trucktypeb) {
            this.trucktypeb = trucktypeb;
        }

        public String getTrucktypec() {
            return trucktypec;
        }

        public void setTrucktypec(String trucktypec) {
            this.trucktypec = trucktypec;
        }

        public String getTrucktyped() {
            return trucktyped;
        }

        public void setTrucktyped(String trucktyped) {
            this.trucktyped = trucktyped;
        }

        public String getTrucktypef() {
            return trucktypef;
        }

        public void setTrucktypef(String trucktypef) {
            this.trucktypef = trucktypef;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getLoad() {
            return load;
        }

        public void setLoad(String load) {
            this.load = load;
        }

        public String getUnload() {
            return unload;
        }

        public void setUnload(String unload) {
            this.unload = unload;
        }

        public String getFromDetailedAddress() {
            return FromDetailedAddress;
        }

        public void setFromDetailedAddress(String FromDetailedAddress) {
            this.FromDetailedAddress = FromDetailedAddress;
        }

        public String getToDetailedAddress() {
            return ToDetailedAddress;
        }

        public void setToDetailedAddress(String ToDetailedAddress) {
            this.ToDetailedAddress = ToDetailedAddress;
        }

        public String getTrucktypeHZ() {
            return trucktypeHZ;
        }

        public void setTrucktypeHZ(String trucktypeHZ) {
            this.trucktypeHZ = trucktypeHZ;
        }

        public String getTrucklengthHZ() {
            return trucklengthHZ;
        }

        public void setTrucklengthHZ(String trucklengthHZ) {
            this.trucklengthHZ = trucklengthHZ;
        }

        public String getLoadaddHZ() {
            return loadaddHZ;
        }

        public void setLoadaddHZ(String loadaddHZ) {
            this.loadaddHZ = loadaddHZ;
        }

        public String getArrivedaddHZ() {
            return arrivedaddHZ;
        }

        public void setArrivedaddHZ(String arrivedaddHZ) {
            this.arrivedaddHZ = arrivedaddHZ;
        }

        public String getMyPriceStatus() {
            return MyPriceStatus;
        }

        public void setMyPriceStatus(String MyPriceStatus) {
            this.MyPriceStatus = MyPriceStatus;
        }

        public int getInvoiceType() {
            return InvoiceType;
        }

        public void setInvoiceType(int InvoiceType) {
            this.InvoiceType = InvoiceType;
        }

        public int getUploadReceipt() {
            return UploadReceipt;
        }

        public void setUploadReceipt(int UploadReceipt) {
            this.UploadReceipt = UploadReceipt;
        }

        public int getPaperReceipt() {
            return PaperReceipt;
        }

        public void setPaperReceipt(int PaperReceipt) {
            this.PaperReceipt = PaperReceipt;
        }

        public int getOwnerbill() {
            return ownerbill;
        }

        public void setOwnerbill(int ownerbill) {
            this.ownerbill = ownerbill;
        }

        public double getDealprice() {
            return dealprice;
        }

        public void setDealprice(double dealprice) {
            this.dealprice = dealprice;
        }

        public String getFromSite1() {
            return FromSite1;
        }

        public void setFromSite1(String FromSite1) {
            this.FromSite1 = FromSite1;
        }

        public String getToSite1() {
            return ToSite1;
        }

        public void setToSite1(String ToSite1) {
            this.ToSite1 = ToSite1;
        }
    }
}
