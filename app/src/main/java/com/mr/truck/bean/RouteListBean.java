package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/1.
 */

public class RouteListBean {

    /**
     * data : [{"LinesGUID":"A68FC38E53764C51860AD2689ECDFA0C","SMSCode":"","SMSDatetime":"2017-01-01T00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","account":"","bank":"","banktype":"","branch":"","cargocount":0,"company":"","companyGUID":"","credit":0,"creditlevel":"","deposit":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"fromSite":"深圳","idcard":"","inviter":"","lineid":1,"loginip":"","loginterminal":"","logintime":"","logintimes":0,"mobile":"","money":0,"online":"","ownerbill":0,"ownerscore":0,"regtime":"2017-01-01T00:00:00","remark":"","support":"","toSite":"广州","truename":"","userGUID":"46560782C299458389969A0DA76B12BE","userName":"严琪","userid":0,"username":"","usertype":"","vcompany":"","vtruename":"","wechatid":""}]
     * errorCode : 200
     * errorMsg :
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
         * LinesGUID : A68FC38E53764C51860AD2689ECDFA0C
         * SMSCode :
         * SMSDatetime : 2017-01-01T00:00:00
         * SecreKey :
         * SecreKeyDateTime : 0001-01-01T00:00:00
         * account :
         * bank :
         * banktype :
         * branch :
         * cargocount : 0
         * company :
         * companyGUID :
         * credit : 0.0
         * creditlevel :
         * deposit : 0.0
         * driverScoreNumber : 0
         * driverTotalScore : 0.0
         * driverbill : 0
         * driverscore : 0.0
         * fromSite : 深圳
         * idcard :
         * inviter :
         * lineid : 1
         * loginip :
         * loginterminal :
         * logintime :
         * logintimes : 0
         * mobile :
         * money : 0.0
         * online :
         * ownerbill : 0
         * ownerscore : 0.0
         * regtime : 2017-01-01T00:00:00
         * remark :
         * support :
         * toSite : 广州
         * truename :
         * userGUID : 46560782C299458389969A0DA76B12BE
         * userName : 严琪
         * userid : 0
         * username :
         * usertype :
         * vcompany :
         * vtruename :
         * wechatid :
         */

        private String LinesGUID;
        private String SMSCode;
        private String SMSDatetime;
        private String SecreKey;
        private String SecreKeyDateTime;
        private String account;
        private String bank;
        private String banktype;
        private String branch;
        private int cargocount;
        private String company;
        private String companyGUID;
        private double credit;
        private String creditlevel;
        private double deposit;
        private int driverScoreNumber;
        private double driverTotalScore;
        private int driverbill;
        private double driverscore;
        private String fromSite;
        private String idcard;
        private String inviter;
        private int lineid;
        private String loginip;
        private String loginterminal;
        private String logintime;
        private int logintimes;
        private String mobile;
        private double money;
        private String online;
        private int ownerbill;
        private double ownerscore;
        private String regtime;
        private String remark;
        private String support;
        private String toSite;
        private String truename;
        private String userGUID;
        private String userName;
        private int userid;
        private String username;
        private String usertype;
        private String vcompany;
        private String vtruename;
        private String wechatid;
        private String trucklength;
        private String trucktype;
        private String MainLin;


        public String getLinesGUID() {
            return LinesGUID;
        }

        public void setLinesGUID(String LinesGUID) {
            this.LinesGUID = LinesGUID;
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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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

        public int getCargocount() {
            return cargocount;
        }

        public void setCargocount(int cargocount) {
            this.cargocount = cargocount;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompanyGUID() {
            return companyGUID;
        }

        public void setCompanyGUID(String companyGUID) {
            this.companyGUID = companyGUID;
        }

        public double getCredit() {
            return credit;
        }

        public void setCredit(double credit) {
            this.credit = credit;
        }

        public String getCreditlevel() {
            return creditlevel;
        }

        public void setCreditlevel(String creditlevel) {
            this.creditlevel = creditlevel;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
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

        public String getFromSite() {
            return fromSite;
        }

        public void setFromSite(String fromSite) {
            this.fromSite = fromSite;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public int getLineid() {
            return lineid;
        }

        public void setLineid(int lineid) {
            this.lineid = lineid;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
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

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSupport() {
            return support;
        }

        public void setSupport(String support) {
            this.support = support;
        }

        public String getToSite() {
            return toSite;
        }

        public void setToSite(String toSite) {
            this.toSite = toSite;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getUserGUID() {
            return userGUID;
        }

        public void setUserGUID(String userGUID) {
            this.userGUID = userGUID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

        public String getVcompany() {
            return vcompany;
        }

        public void setVcompany(String vcompany) {
            this.vcompany = vcompany;
        }

        public String getVtruename() {
            return vtruename;
        }

        public void setVtruename(String vtruename) {
            this.vtruename = vtruename;
        }

        public String getWechatid() {
            return wechatid;
        }

        public void setWechatid(String wechatid) {
            this.wechatid = wechatid;
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
        public String getMainLin() {
            return MainLin;
        }

        public void setMainLin(String MainLin) {
            this.MainLin = MainLin;
        }
    }
}
