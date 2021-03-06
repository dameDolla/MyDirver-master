package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/3.
 */

public class CarTeamBean {

    /**
     * errorCode : 200
     * errorMsg :
     * data : [{"truckid":3,"TrucksGUID":"86561A522EEB4E0EA7A70547D153AFC3","truckno":"粤B888888","trucklength":"1.2米","trucktype":"1","companyGUID":"","company":"","driverGUID":"548355B961194753AF2C64864D461009","drivername":"严琪","trucklicence":"","boardingtime":"2017/10/3 0:00:00","status":"0","registertime":"2017/10/3 12:04:57","Vtruck":"0","TruckImg":"","userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","creditlevel":"","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"deposit":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01 00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","remark":"","SMSCode":"","SMSDatetime":"2017-01-01 00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","AvatarAddress":null,"PageNum":0}]
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
         * truckid : 3
         * TrucksGUID : 86561A522EEB4E0EA7A70547D153AFC3
         * truckno : 粤B888888
         * trucklength : 1.2米
         * trucktype : 1
         * companyGUID :
         * company :
         * driverGUID : 548355B961194753AF2C64864D461009
         * drivername : 严琪
         * trucklicence :
         * boardingtime : 2017/10/3 0:00:00
         * status : 0
         * registertime : 2017/10/3 12:04:57
         * Vtruck : 0
         * TruckImg :
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
         * deposit : 0.0
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
         * remark :
         * SMSCode :
         * SMSDatetime : 2017-01-01 00:00:00
         * SecreKey :
         * SecreKeyDateTime : 0001-01-01T00:00:00
         * AvatarAddress : null
         * PageNum : 0
         */

        private int truckid;
        private String TrucksGUID;
        private String truckno;
        private String trucklength;
        private String trucktype;
        private String companyGUID;
        private String company;
        private String driverGUID;
        private String drivername;
        private String trucklicence;
        private String boardingtime;
        private String status;
        private String registertime;
        private String Vtruck;
        private String TruckImg;
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
        private double deposit;
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
        private String remark;
        private String SMSCode;
        private String SMSDatetime;
        private String SecreKey;
        private String SecreKeyDateTime;
        private Object AvatarAddress;
        private int PageNum;
        private String TruckWeight;
        private String TruckWidth;
        private String TruckHeight;
        private String Volume;

        public String getVolume() {
            return Volume;
        }

        public String getTruckHeight() {
            return TruckHeight;
        }

        public String getTruckWidth() {
            return TruckWidth;
        }

        public String getTruckWeight() {
            return TruckWeight;
        }

        public int getTruckid() {
            return truckid;
        }

        public void setTruckid(int truckid) {
            this.truckid = truckid;
        }

        public String getTrucksGUID() {
            return TrucksGUID;
        }

        public void setTrucksGUID(String TrucksGUID) {
            this.TrucksGUID = TrucksGUID;
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

        public String getTrucklicence() {
            return trucklicence;
        }

        public void setTrucklicence(String trucklicence) {
            this.trucklicence = trucklicence;
        }

        public String getBoardingtime() {
            return boardingtime;
        }

        public void setBoardingtime(String boardingtime) {
            this.boardingtime = boardingtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRegistertime() {
            return registertime;
        }

        public void setRegistertime(String registertime) {
            this.registertime = registertime;
        }

        public String getVtruck() {
            return Vtruck;
        }

        public void setVtruck(String Vtruck) {
            this.Vtruck = Vtruck;
        }

        public String getTruckImg() {
            return TruckImg;
        }

        public void setTruckImg(String TruckImg) {
            this.TruckImg = TruckImg;
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

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public Object getAvatarAddress() {
            return AvatarAddress;
        }

        public void setAvatarAddress(Object AvatarAddress) {
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
