package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/27.
 */

public class MissionListBean {

    /**
     * errorCode : 200
     * errorMsg :
     * data : [{"billid":0,"billsGUID":null,"iscargo":null,"owneridGUID":null,"ownername":null,"ownerphone":"13823793863","fromSite":"深圳市","toSite":"广州市","cargotype":"家具/10吨/5方/","qty":10,"unit":"5","companyGUID":null,"company":null,"driverGUID":null,"drivername":null,"driverphone":null,"truckno":"null","trucklength":null,"trucktype":null,"dealprice":null,"deposit":null,"imforfee":null,"remark":null,"load":null,"loadfee":null,"unload":null,"unloadfee":null,"waitfee":null,"otherfee":null,"feeremark":null,"totalcharge":null,"preloadtime":null,"loadtime":null,"loadadd":null,"prearrivetime":null,"arrivedtime":null,"arrivedadd":null,"signby":null,"signtime":null,"signadd":null,"loadwaittime":null,"unloadwaittime":null,"status":"0","trucktypea":null,"trucktypeb":null,"trucktypec":null,"trucktyped":null,"trucktypef":null,"ownerprice":null,"driverdeposit":null,"ReleaseTime":null,"trucklengthHZ":null,"trucktypeHZ":null,"loadaddHZ":null,"arrivedaddHZ":null,"TypeKCLX":null,"userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","creditlevel":"","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01 00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","SMSCode":"","SMSDatetime":"2017-01-01 00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","AvatarAddress":null,"PageNum":0}]
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
         * billid : 0
         * billsGUID : null
         * iscargo : null
         * owneridGUID : null
         * ownername : null
         * ownerphone : 13823793863
         * fromSite : 深圳市
         * toSite : 广州市
         * cargotype : 家具/10吨/5方/
         * qty : 10
         * unit : 5
         * companyGUID : null
         * company : null
         * driverGUID : null
         * drivername : null
         * driverphone : null
         * truckno : null
         * trucklength : null
         * trucktype : null
         * dealprice : null
         * deposit : null
         * imforfee : null
         * remark : null
         * load : null
         * loadfee : null
         * unload : null
         * unloadfee : null
         * waitfee : null
         * otherfee : null
         * feeremark : null
         * totalcharge : null
         * preloadtime : null
         * loadtime : null
         * loadadd : null
         * prearrivetime : null
         * arrivedtime : null
         * arrivedadd : null
         * signby : null
         * signtime : null
         * signadd : null
         * loadwaittime : null
         * unloadwaittime : null
         * status : 0
         * trucktypea : null
         * trucktypeb : null
         * trucktypec : null
         * trucktyped : null
         * trucktypef : null
         * ownerprice : null
         * driverdeposit : null
         * ReleaseTime : null
         * trucklengthHZ : null
         * trucktypeHZ : null
         * loadaddHZ : null
         * arrivedaddHZ : null
         * TypeKCLX : null
         * userid : 0
         * GUID : null
         * username :
         * usertype :
         * truename :
         * mobile :
         * loginterminal :
         * logintime :
         * logintimes : 0
         * wechatid :
         * idcard :
         * creditlevel :
         * cargocount : 0
         * ownerbill : 0
         * ownerscore : 0.0
         * driverScoreNumber : 0
         * driverTotalScore : 0.0
         * driverbill : 0
         * driverscore : 0.0
         * online :
         * credit : 0.0
         * money : 0.0
         * bank :
         * banktype :
         * branch :
         * account :
         * regtime : 2017-01-01 00:00:00
         * loginip :
         * vtruename :
         * vcompany :
         * support :
         * inviter :
         * SMSCode :
         * SMSDatetime : 2017-01-01 00:00:00
         * SecreKey :
         * SecreKeyDateTime : 0001-01-01T00:00:00
         * AvatarAddress : null
         * PageNum : 0
         */

        private int billid;
        private String billsGUID;
        private String iscargo;
        private String owneridGUID;
        private String ownername;
        private String ownerphone;
        private String fromSite;
        private String toSite;
        private String cargotype;
        private int qty;
        private String unit;
        private String companyGUID;
        private String company;
        private String driverGUID;
        private String drivername;
        private String driverphone;
        private String truckno;
        private String trucklength;
        private String trucktype;
        private String dealprice;
        private String deposit;
        private String imforfee;
        private String remark;
        private String load;
        private String loadfee;
        private String unload;
        private String unloadfee;
        private String waitfee;
        private String otherfee;
        private String feeremark;
        private String totalcharge;
        private String preloadtime;
        private String loadtime;
        private String loadadd;
        private String prearrivetime;
        private String arrivedtime;
        private String arrivedadd;
        private String signby;
        private String signtime;
        private String  signadd;
        private String loadwaittime;
        private String unloadwaittime;
        private String status;
        private String trucktypea;
        private String trucktypeb;
        private String trucktypec;
        private String trucktyped;
        private String trucktypef;
        private String ownerprice;
        private String driverdeposit;
        private String ReleaseTime;
        private String trucklengthHZ;
        private String trucktypeHZ;
        private String loadaddHZ;
        private String arrivedaddHZ;
        private String TypeKCLX;
        private int userid;
        private String GUID;
        private String username;
        private String usertype;
        private String truename;
        private String mobile;
        private String loginterminal;
        private String logintime;
        private int logintimes;
        private String wechatid;
        private String idcard;
        private String creditlevel;
        private int cargocount;
        private int ownerbill;
        private double ownerscore;
        private int driverScoreNumber;
        private double driverTotalScore;
        private int driverbill;
        private double driverscore;
        private String online;
        private double credit;
        private double money;
        private String bank;
        private String banktype;
        private String branch;
        private String account;
        private String regtime;
        private String loginip;
        private String vtruename;
        private String vcompany;
        private String support;
        private String inviter;
        private String SMSCode;
        private String SMSDatetime;
        private String SecreKey;
        private String SecreKeyDateTime;
        private String AvatarAddress;
        private int PageNum;
        private String billNumber;
        private String Newload;
        private String FromDetailedAddress;
        private String ToDetailedAddress;
        private String Consignee;
        private String ConsigneePhone;
        private String InsertDate;//运单生成的时间
        private String ArrivalLoadingTime;//到达装货时间
        private String DepartureTime;//出发时间
        private String DeparturePlace;//出发时间
        private String ArrivalLoadingPlace;//出发时间

        public String getDeparturePlace() {
            return DeparturePlace;
        }

        public String getArrivalLoadingPlace() {
            return ArrivalLoadingPlace;
        }


        public String getDepartureTime() {
            return DepartureTime;
        }

        public void setDepartureTime(String departureTime) {
            DepartureTime = departureTime;
        }

        public String getConsigneePhone() {
            return ConsigneePhone;
        }

        public String getConsignee() {
            return Consignee;
        }

        public String getInsertDate() {
            return InsertDate;
        }

        public void setInsertDate(String insertDate) {
            InsertDate = insertDate;
        }

        public String getArrivalLoadingTime() {
            return ArrivalLoadingTime;
        }

        public void setArrivalLoadingTime(String arrivalLoadingTime) {
            ArrivalLoadingTime = arrivalLoadingTime;
        }

        public String getToDetailedAddress() {
            return ToDetailedAddress;
        }

        public String getFromDetailedAddress() {
            return FromDetailedAddress;
        }

        public String getNewload() {
            return Newload;
        }

        public void setNewload(String newload) {
            Newload = newload;
        }

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public int getBillid() {
            return billid;
        }

        public void setBillid(int billid) {
            this.billid = billid;
        }

        public String getBillsGUID() {
            return billsGUID;
        }

        public void setBillsGUID(String billsGUID) {
            this.billsGUID = billsGUID;
        }

        public String getIscargo() {
            return iscargo;
        }

        public void setIscargo(String iscargo) {
            this.iscargo = iscargo;
        }

        public String getOwneridGUID() {
            return owneridGUID;
        }

        public void setOwneridGUID(String owneridGUID) {
            this.owneridGUID = owneridGUID;
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

        public String getCompanyGUID() {
            return companyGUID;
        }

        public void setCompanyGUID(String companyGUID) {
            this.companyGUID = companyGUID;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDriverGUID() {
            return driverGUID;
        }

        public void setDriverGUID(String driverGUID) {
            this.driverGUID = driverGUID;
        }

        public String getDrivername() {
            return drivername;
        }

        public void setDrivername(String drivername) {
            this.drivername = drivername;
        }

        public String getDriverphone() {
            return driverphone;
        }

        public void setDriverphone(String driverphone) {
            this.driverphone = driverphone;
        }

        public String getTruckno() {
            return truckno;
        }

        public void setTruckno(String truckno) {
            this.truckno = truckno;
        }

        public String getTrucklength() {
            return trucklength;
        }

        public void setTrucklength(String trucklength) {
            this.trucklength = trucklength;
        }

        public String getTrucktype() {
            return trucktype;
        }

        public void setTrucktype(String trucktype) {
            this.trucktype = trucktype;
        }

        public String getDealprice() {
            return dealprice;
        }

        public void setDealprice(String dealprice) {
            this.dealprice = dealprice;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getImforfee() {
            return imforfee;
        }

        public void setImforfee(String imforfee) {
            this.imforfee = imforfee;
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

        public String getLoadfee() {
            return loadfee;
        }

        public void setLoadfee(String loadfee) {
            this.loadfee = loadfee;
        }

        public String getUnload() {
            return unload;
        }

        public void setUnload(String unload) {
            this.unload = unload;
        }

        public String getUnloadfee() {
            return unloadfee;
        }

        public void setUnloadfee(String unloadfee) {
            this.unloadfee = unloadfee;
        }

        public String getWaitfee() {
            return waitfee;
        }

        public void setWaitfee(String waitfee) {
            this.waitfee = waitfee;
        }

        public String getOtherfee() {
            return otherfee;
        }

        public void setOtherfee(String otherfee) {
            this.otherfee = otherfee;
        }

        public String getFeeremark() {
            return feeremark;
        }

        public void setFeeremark(String feeremark) {
            this.feeremark = feeremark;
        }

        public String getTotalcharge() {
            return totalcharge;
        }

        public void setTotalcharge(String totalcharge) {
            this.totalcharge = totalcharge;
        }

        public String getPreloadtime() {
            return preloadtime;
        }

        public void setPreloadtime(String preloadtime) {
            this.preloadtime = preloadtime;
        }

        public String getLoadtime() {
            return loadtime;
        }

        public void setLoadtime(String loadtime) {
            this.loadtime = loadtime;
        }

        public String getLoadadd() {
            return loadadd;
        }

        public void setLoadadd(String loadadd) {
            this.loadadd = loadadd;
        }

        public String getPrearrivetime() {
            return prearrivetime;
        }

        public void setPrearrivetime(String prearrivetime) {
            this.prearrivetime = prearrivetime;
        }

        public String getArrivedtime() {
            return arrivedtime;
        }

        public void setArrivedtime(String arrivedtime) {
            this.arrivedtime = arrivedtime;
        }

        public String getArrivedadd() {
            return arrivedadd;
        }

        public void setArrivedadd(String arrivedadd) {
            this.arrivedadd = arrivedadd;
        }

        public String getSignby() {
            return signby;
        }

        public void setSignby(String signby) {
            this.signby = signby;
        }

        public String getSigntime() {
            return signtime;
        }

        public void setSigntime(String signtime) {
            this.signtime = signtime;
        }

        public String getSignadd() {
            return signadd;
        }

        public void setSignadd(String signadd) {
            this.signadd = signadd;
        }

        public String getLoadwaittime() {
            return loadwaittime;
        }

        public void setLoadwaittime(String loadwaittime) {
            this.loadwaittime = loadwaittime;
        }

        public String getUnloadwaittime() {
            return unloadwaittime;
        }

        public void setUnloadwaittime(String unloadwaittime) {
            this.unloadwaittime = unloadwaittime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getOwnerprice() {
            return ownerprice;
        }

        public void setOwnerprice(String ownerprice) {
            this.ownerprice = ownerprice;
        }

        public String getDriverdeposit() {
            return driverdeposit;
        }

        public void setDriverdeposit(String driverdeposit) {
            this.driverdeposit = driverdeposit;
        }

        public String getReleaseTime() {
            return ReleaseTime;
        }

        public void setReleaseTime(String ReleaseTime) {
            this.ReleaseTime = ReleaseTime;
        }

        public String getTrucklengthHZ() {
            return trucklengthHZ;
        }

        public void setTrucklengthHZ(String trucklengthHZ) {
            this.trucklengthHZ = trucklengthHZ;
        }

        public String getTrucktypeHZ() {
            return trucktypeHZ;
        }

        public void setTrucktypeHZ(String trucktypeHZ) {
            this.trucktypeHZ = trucktypeHZ;
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

        public String getTypeKCLX() {
            return TypeKCLX;
        }

        public void setTypeKCLX(String TypeKCLX) {
            this.TypeKCLX = TypeKCLX;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getGUID() {
            return GUID;
        }

        public void setGUID(String GUID) {
            this.GUID = GUID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLoginterminal() {
            return loginterminal;
        }

        public void setLoginterminal(String loginterminal) {
            this.loginterminal = loginterminal;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public int getLogintimes() {
            return logintimes;
        }

        public void setLogintimes(int logintimes) {
            this.logintimes = logintimes;
        }

        public String getWechatid() {
            return wechatid;
        }

        public void setWechatid(String wechatid) {
            this.wechatid = wechatid;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getCreditlevel() {
            return creditlevel;
        }

        public void setCreditlevel(String creditlevel) {
            this.creditlevel = creditlevel;
        }

        public int getCargocount() {
            return cargocount;
        }

        public void setCargocount(int cargocount) {
            this.cargocount = cargocount;
        }

        public int getOwnerbill() {
            return ownerbill;
        }

        public void setOwnerbill(int ownerbill) {
            this.ownerbill = ownerbill;
        }

        public double getOwnerscore() {
            return ownerscore;
        }

        public void setOwnerscore(double ownerscore) {
            this.ownerscore = ownerscore;
        }

        public int getDriverScoreNumber() {
            return driverScoreNumber;
        }

        public void setDriverScoreNumber(int driverScoreNumber) {
            this.driverScoreNumber = driverScoreNumber;
        }

        public double getDriverTotalScore() {
            return driverTotalScore;
        }

        public void setDriverTotalScore(double driverTotalScore) {
            this.driverTotalScore = driverTotalScore;
        }

        public int getDriverbill() {
            return driverbill;
        }

        public void setDriverbill(int driverbill) {
            this.driverbill = driverbill;
        }

        public double getDriverscore() {
            return driverscore;
        }

        public void setDriverscore(double driverscore) {
            this.driverscore = driverscore;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public double getCredit() {
            return credit;
        }

        public void setCredit(double credit) {
            this.credit = credit;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBanktype() {
            return banktype;
        }

        public void setBanktype(String banktype) {
            this.banktype = banktype;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
        }

        public String getVtruename() {
            return vtruename;
        }

        public void setVtruename(String vtruename) {
            this.vtruename = vtruename;
        }

        public String getVcompany() {
            return vcompany;
        }

        public void setVcompany(String vcompany) {
            this.vcompany = vcompany;
        }

        public String getSupport() {
            return support;
        }

        public void setSupport(String support) {
            this.support = support;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public String getSMSCode() {
            return SMSCode;
        }

        public void setSMSCode(String SMSCode) {
            this.SMSCode = SMSCode;
        }

        public String getSMSDatetime() {
            return SMSDatetime;
        }

        public void setSMSDatetime(String SMSDatetime) {
            this.SMSDatetime = SMSDatetime;
        }

        public String getSecreKey() {
            return SecreKey;
        }

        public void setSecreKey(String SecreKey) {
            this.SecreKey = SecreKey;
        }

        public String getSecreKeyDateTime() {
            return SecreKeyDateTime;
        }

        public void setSecreKeyDateTime(String SecreKeyDateTime) {
            this.SecreKeyDateTime = SecreKeyDateTime;
        }

        public String getAvatarAddress() {
            return AvatarAddress;
        }

        public void setAvatarAddress(String AvatarAddress) {
            this.AvatarAddress = AvatarAddress;
        }

        public int getPageNum() {
            return PageNum;
        }

        public void setPageNum(int PageNum) {
            this.PageNum = PageNum;
        }
    }
}
