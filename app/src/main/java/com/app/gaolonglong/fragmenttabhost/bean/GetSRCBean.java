package com.app.gaolonglong.fragmenttabhost.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/7.
 */

public class GetSRCBean {

    /**
     * errorCode : 200
     * errorMsg :
     * data : [{"billid":0,"billsGUID":null,"iscargo":null,"owneridGUID":null,"ownername":null,"ownerphone":null,"fromSite":"深圳","toSite":"广州","cargotype":"1","qty":null,"unit":null,"companyGUID":null,"company":null,"driverGUID":null,"drivername":null,"driverphone":null,"truckno":null,"trucklength":null,"trucktype":null,"dealprice":null,"deposit":null,"imforfee":null,"remark":null,"load":null,"loadfee":null,"unload":null,"unloadfee":null,"waitfee":null,"otherfee":null,"feeremark":null,"totalcharge":null,"preloadtime":"2017/9/14 星期四 下午 12:00:00","loadtime":null,"loadadd":null,"prearrivetime":null,"arrivedtime":null,"arrivedadd":null,"signby":null,"signtime":null,"signadd":null,"loadwaittime":null,"unloadwaittime":null,"status":null,"trucktypea":"3.5米","trucktypeb":"4米","trucktypec":"","trucktyped":"","trucktypef":"","ownerprice":null,"driverdeposit":null,"ReleaseTime":null,"TypeKCLX":null,"userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"15908690321","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","creditlevel":"0","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01 00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","SMSCode":"","SMSDatetime":"2017-01-01 00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","AvatarAddress":"http://192.168.1.102:8013/uploadA\\2017\\9\\12\\46560782C299458389969A0DA76B12BE20170912190656362.jpg","PageNum":0},{"billid":0,"billsGUID":null,"iscargo":null,"owneridGUID":null,"ownername":null,"ownerphone":null,"fromSite":"深圳","toSite":"广州","cargotype":"1","qty":null,"unit":null,"companyGUID":null,"company":null,"driverGUID":null,"drivername":null,"driverphone":null,"truckno":null,"trucklength":null,"trucktype":null,"dealprice":null,"deposit":null,"imforfee":null,"remark":null,"load":null,"loadfee":null,"unload":null,"unloadfee":null,"waitfee":null,"otherfee":null,"feeremark":null,"totalcharge":null,"preloadtime":"2017/9/15 星期五 下午 12:00:00","loadtime":null,"loadadd":null,"prearrivetime":null,"arrivedtime":null,"arrivedadd":null,"signby":null,"signtime":null,"signadd":null,"loadwaittime":null,"unloadwaittime":null,"status":null,"trucktypea":"3.5米","trucktypeb":"4.5米","trucktypec":"","trucktyped":"","trucktypef":"","ownerprice":null,"driverdeposit":null,"ReleaseTime":null,"TypeKCLX":null,"userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"18820930217","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","creditlevel":"0","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01 00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","SMSCode":"","SMSDatetime":"2017-01-01 00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","AvatarAddress":"http://192.168.1.102:8013/uploadA\\2017\\9\\12\\20170912104757.jpg","PageNum":0}]
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
         * ownerphone : null
         * fromSite : 深圳
         * toSite : 广州
         * cargotype : 1
         * qty : null
         * unit : null
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
         * preloadtime : 2017/9/14 星期四 下午 12:00:00
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
         * status : null
         * trucktypea : 3.5米
         * trucktypeb : 4米
         * trucktypec :
         * trucktyped :
         * trucktypef :
         * ownerprice : null
         * driverdeposit : null
         * ReleaseTime : null
         * TypeKCLX : null
         * userid : 0
         * GUID : null
         * username :
         * usertype :
         * truename :
         * mobile : 15908690321
         * loginterminal :
         * logintime :
         * logintimes : 0
         * wechatid :
         * idcard :
         * creditlevel : 0
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
         * AvatarAddress : http://192.168.1.102:8013/uploadA\2017\9\12\46560782C299458389969A0DA76B12BE20170912190656362.jpg
         * PageNum : 0
         */

        private int billid;
        private Object billsGUID;
        private Object iscargo;
        private Object owneridGUID;
        private Object ownername;
        private Object ownerphone;
        private String fromSite;
        private String toSite;
        private String cargotype;
        private Object qty;
        private Object unit;
        private Object companyGUID;
        private Object company;
        private Object driverGUID;
        private Object drivername;
        private Object driverphone;
        private Object truckno;
        private Object trucklength;
        private Object trucktype;
        private Object dealprice;
        private Object deposit;
        private Object imforfee;
        private Object remark;
        private Object load;
        private Object loadfee;
        private Object unload;
        private Object unloadfee;
        private Object waitfee;
        private Object otherfee;
        private Object feeremark;
        private Object totalcharge;
        private String preloadtime;
        private Object loadtime;
        private Object loadadd;
        private Object prearrivetime;
        private Object arrivedtime;
        private Object arrivedadd;
        private Object signby;
        private Object signtime;
        private Object signadd;
        private Object loadwaittime;
        private Object unloadwaittime;
        private Object status;
        private String trucktypea;
        private String trucktypeb;
        private String trucktypec;
        private String trucktyped;
        private String trucktypef;
        private Object ownerprice;
        private Object ReleaseTime;
        private Object TypeKCLX;
        private int userid;
        private Object GUID;
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
        private String driverdeposit;

        public int getBillid() {
            return billid;
        }

        public void setBillid(int billid) {
            this.billid = billid;
        }

        public Object getBillsGUID() {
            return billsGUID;
        }

        public void setBillsGUID(Object billsGUID) {
            this.billsGUID = billsGUID;
        }

        public Object getIscargo() {
            return iscargo;
        }

        public void setIscargo(Object iscargo) {
            this.iscargo = iscargo;
        }

        public Object getOwneridGUID() {
            return owneridGUID;
        }

        public void setOwneridGUID(Object owneridGUID) {
            this.owneridGUID = owneridGUID;
        }

        public Object getOwnername() {
            return ownername;
        }

        public void setOwnername(Object ownername) {
            this.ownername = ownername;
        }

        public Object getOwnerphone() {
            return ownerphone;
        }

        public void setOwnerphone(Object ownerphone) {
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

        public Object getQty() {
            return qty;
        }

        public void setQty(Object qty) {
            this.qty = qty;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public Object getCompanyGUID() {
            return companyGUID;
        }

        public void setCompanyGUID(Object companyGUID) {
            this.companyGUID = companyGUID;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public Object getDriverGUID() {
            return driverGUID;
        }

        public void setDriverGUID(Object driverGUID) {
            this.driverGUID = driverGUID;
        }

        public Object getDrivername() {
            return drivername;
        }

        public void setDrivername(Object drivername) {
            this.drivername = drivername;
        }

        public Object getDriverphone() {
            return driverphone;
        }

        public void setDriverphone(Object driverphone) {
            this.driverphone = driverphone;
        }

        public Object getTruckno() {
            return truckno;
        }

        public void setTruckno(Object truckno) {
            this.truckno = truckno;
        }

        public Object getTrucklength() {
            return trucklength;
        }

        public void setTrucklength(Object trucklength) {
            this.trucklength = trucklength;
        }

        public Object getTrucktype() {
            return trucktype;
        }

        public void setTrucktype(Object trucktype) {
            this.trucktype = trucktype;
        }

        public Object getDealprice() {
            return dealprice;
        }

        public void setDealprice(Object dealprice) {
            this.dealprice = dealprice;
        }

        public Object getDeposit() {
            return deposit;
        }

        public void setDeposit(Object deposit) {
            this.deposit = deposit;
        }

        public Object getImforfee() {
            return imforfee;
        }

        public void setImforfee(Object imforfee) {
            this.imforfee = imforfee;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getLoad() {
            return load;
        }

        public void setLoad(Object load) {
            this.load = load;
        }

        public Object getLoadfee() {
            return loadfee;
        }

        public void setLoadfee(Object loadfee) {
            this.loadfee = loadfee;
        }

        public Object getUnload() {
            return unload;
        }

        public void setUnload(Object unload) {
            this.unload = unload;
        }

        public Object getUnloadfee() {
            return unloadfee;
        }

        public void setUnloadfee(Object unloadfee) {
            this.unloadfee = unloadfee;
        }

        public Object getWaitfee() {
            return waitfee;
        }

        public void setWaitfee(Object waitfee) {
            this.waitfee = waitfee;
        }

        public Object getOtherfee() {
            return otherfee;
        }

        public void setOtherfee(Object otherfee) {
            this.otherfee = otherfee;
        }

        public Object getFeeremark() {
            return feeremark;
        }

        public void setFeeremark(Object feeremark) {
            this.feeremark = feeremark;
        }

        public Object getTotalcharge() {
            return totalcharge;
        }

        public void setTotalcharge(Object totalcharge) {
            this.totalcharge = totalcharge;
        }

        public String getPreloadtime() {
            return preloadtime;
        }

        public void setPreloadtime(String preloadtime) {
            this.preloadtime = preloadtime;
        }

        public Object getLoadtime() {
            return loadtime;
        }

        public void setLoadtime(Object loadtime) {
            this.loadtime = loadtime;
        }

        public Object getLoadadd() {
            return loadadd;
        }

        public void setLoadadd(Object loadadd) {
            this.loadadd = loadadd;
        }

        public Object getPrearrivetime() {
            return prearrivetime;
        }

        public void setPrearrivetime(Object prearrivetime) {
            this.prearrivetime = prearrivetime;
        }

        public Object getArrivedtime() {
            return arrivedtime;
        }

        public void setArrivedtime(Object arrivedtime) {
            this.arrivedtime = arrivedtime;
        }

        public Object getArrivedadd() {
            return arrivedadd;
        }

        public void setArrivedadd(Object arrivedadd) {
            this.arrivedadd = arrivedadd;
        }

        public Object getSignby() {
            return signby;
        }

        public void setSignby(Object signby) {
            this.signby = signby;
        }

        public Object getSigntime() {
            return signtime;
        }

        public void setSigntime(Object signtime) {
            this.signtime = signtime;
        }

        public Object getSignadd() {
            return signadd;
        }

        public void setSignadd(Object signadd) {
            this.signadd = signadd;
        }

        public Object getLoadwaittime() {
            return loadwaittime;
        }

        public void setLoadwaittime(Object loadwaittime) {
            this.loadwaittime = loadwaittime;
        }

        public Object getUnloadwaittime() {
            return unloadwaittime;
        }

        public void setUnloadwaittime(Object unloadwaittime) {
            this.unloadwaittime = unloadwaittime;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
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

        public Object getOwnerprice() {
            return ownerprice;
        }

        public void setOwnerprice(Object ownerprice) {
            this.ownerprice = ownerprice;
        }

        public String getDriverdeposit() {
            return driverdeposit;
        }

        public void setDriverdeposit(String driverdeposit) {
            this.driverdeposit = driverdeposit;
        }

        public Object getReleaseTime() {
            return ReleaseTime;
        }

        public void setReleaseTime(Object ReleaseTime) {
            this.ReleaseTime = ReleaseTime;
        }

        public Object getTypeKCLX() {
            return TypeKCLX;
        }

        public void setTypeKCLX(Object TypeKCLX) {
            this.TypeKCLX = TypeKCLX;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public Object getGUID() {
            return GUID;
        }

        public void setGUID(Object GUID) {
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
