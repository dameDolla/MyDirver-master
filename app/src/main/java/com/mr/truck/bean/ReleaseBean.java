package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/6.
 */

public class ReleaseBean {

    /**
     * errorCode : 220
     * errorMsg :
     * data : [{"truckid":14,"TruckplansGUID":"123456789012345678901234567890AA","truckno":"粤123456","trucklength":"3.5米","trucktype":"大巴","companyGUID":"","driverGUID":"46560782C299458389969A0DA76B12BE","boardingtime":"2017-09-01T00:00:00","status":"1","fromSite":"深圳","emptytime":"2017-09-02","backtime":"2017-09-03T00:00:00","toSite":"广州","changeable":"0","SurplusTon":"1","SurplusPower":"2","TransportOffer":"3","MyMessage":"测试留言信息","ReleaseDate":"2017-09-03T00:00:00","TruckplanStatus":"1","FromDetailedAddress":null,"ToDetailedAddress":null,"userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","company":"","creditlevel":"","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"deposit":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01T00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","remark":"","SMSCode":"","SMSDatetime":"2017-01-01T00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00"}]
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
         * truckid : 14
         * TruckplansGUID : 123456789012345678901234567890AA
         * truckno : 粤123456
         * trucklength : 3.5米
         * trucktype : 大巴
         * companyGUID :
         * driverGUID : 46560782C299458389969A0DA76B12BE
         * boardingtime : 2017-09-01T00:00:00
         * status : 1
         * fromSite : 深圳
         * emptytime : 2017-09-02
         * backtime : 2017-09-03T00:00:00
         * toSite : 广州
         * changeable : 0
         * SurplusTon : 1
         * SurplusPower : 2
         * TransportOffer : 3
         * MyMessage : 测试留言信息
         * ReleaseDate : 2017-09-03T00:00:00
         * TruckplanStatus : 1
         * FromDetailedAddress : null
         * ToDetailedAddress : null
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
         * company :
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
         * regtime : 2017-01-01T00:00:00
         * loginip :
         * vtruename :
         * vcompany :
         * support :
         * inviter :
         * remark :
         * SMSCode :
         * SMSDatetime : 2017-01-01T00:00:00
         * SecreKey :
         * SecreKeyDateTime : 0001-01-01T00:00:00
         */

        private int truckid;
        private String TruckplansGUID;
        private String truckno;
        private String trucklength;
        private String trucktype;
        private String companyGUID;
        private String driverGUID;
        private String boardingtime;
        private String status;
        private String fromSite;
        private String emptytime;
        private String backtime;
        private String toSite;
        private String changeable;
        private String SurplusTon;
        private String SurplusPower;
        private String TransportOffer;
        private String MyMessage;
        private String ReleaseDate;
        private String TruckplanStatus;
        private Object FromDetailedAddress;
        private Object ToDetailedAddress;
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
        private String company;
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

        public int getTruckid() {
            return truckid;
        }

        public void setTruckid(int truckid) {
            this.truckid = truckid;
        }

        public String getTruckplansGUID() {
            return TruckplansGUID;
        }

        public void setTruckplansGUID(String TruckplansGUID) {
            this.TruckplansGUID = TruckplansGUID;
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

        public String getDriverGUID() {
            return driverGUID;
        }

        public void setDriverGUID(String driverGUID) {
            this.driverGUID = driverGUID;
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

        public String getFromSite() {
            return fromSite;
        }

        public void setFromSite(String fromSite) {
            this.fromSite = fromSite;
        }

        public String getEmptytime() {
            return emptytime;
        }

        public void setEmptytime(String emptytime) {
            this.emptytime = emptytime;
        }

        public String getBacktime() {
            return backtime;
        }

        public void setBacktime(String backtime) {
            this.backtime = backtime;
        }

        public String getToSite() {
            return toSite;
        }

        public void setToSite(String toSite) {
            this.toSite = toSite;
        }

        public String getChangeable() {
            return changeable;
        }

        public void setChangeable(String changeable) {
            this.changeable = changeable;
        }

        public String getSurplusTon() {
            return SurplusTon;
        }

        public void setSurplusTon(String SurplusTon) {
            this.SurplusTon = SurplusTon;
        }

        public String getSurplusPower() {
            return SurplusPower;
        }

        public void setSurplusPower(String SurplusPower) {
            this.SurplusPower = SurplusPower;
        }

        public String getTransportOffer() {
            return TransportOffer;
        }

        public void setTransportOffer(String TransportOffer) {
            this.TransportOffer = TransportOffer;
        }

        public String getMyMessage() {
            return MyMessage;
        }

        public void setMyMessage(String MyMessage) {
            this.MyMessage = MyMessage;
        }

        public String getReleaseDate() {
            return ReleaseDate;
        }

        public void setReleaseDate(String ReleaseDate) {
            this.ReleaseDate = ReleaseDate;
        }

        public String getTruckplanStatus() {
            return TruckplanStatus;
        }

        public void setTruckplanStatus(String TruckplanStatus) {
            this.TruckplanStatus = TruckplanStatus;
        }

        public Object getFromDetailedAddress() {
            return FromDetailedAddress;
        }

        public void setFromDetailedAddress(Object FromDetailedAddress) {
            this.FromDetailedAddress = FromDetailedAddress;
        }

        public Object getToDetailedAddress() {
            return ToDetailedAddress;
        }

        public void setToDetailedAddress(Object ToDetailedAddress) {
            this.ToDetailedAddress = ToDetailedAddress;
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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
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
    }
}
