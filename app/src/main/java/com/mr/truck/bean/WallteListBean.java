package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/11/7.
 */

public class WallteListBean {

    /**
     * errorCode : 200
     * errorMsg : 
     * data : [{"tradeid":119,"trademarksGUID":"5BABA2071A694DCC9BC9573D519B0AB7","userGUID":"3914F8E3FC76459F8AC9EE8D9AC631CF","tradetime":"2017-11-06 18:19:00","tradetype":"1","tradeamount":169.3,"beforebalance":0,"lastbalance":169.3,"remark":"帐户余额充值","OrderNumber":"149008667220171106181900161","States":"1","BeforeFrozenCapital":"0.00","LastFrozenCapital":"0.00","userid":0,"GUID":null,"username":"","usertype":"","truename":"","mobile":"","loginterminal":"","logintime":"","logintimes":0,"wechatid":"","idcard":"","companyGUID":"","company":"","creditlevel":"","cargocount":0,"ownerbill":0,"ownerscore":0,"driverScoreNumber":0,"driverTotalScore":0,"driverbill":0,"driverscore":0,"online":"","credit":0,"money":0,"deposit":0,"bank":"","banktype":"","branch":"","account":"","regtime":"2017-01-01 00:00:00","loginip":"","vtruename":"","vcompany":"","support":"","inviter":"","SMSCode":"","SMSDatetime":"2017-01-01 00:00:00","SecreKey":"","SecreKeyDateTime":"0001-01-01T00:00:00","AvatarAddress":null,"PageNum":0,"FrozenMoney":0,"MInvoiceType":0,"InvoiceHead":null,"InvoiceNumber":null,"InvoiceAddress":null,"InvoicePhone":null,"MoneyDateTime":"0001-01-01T00:00:00","FrozenMoneyDateTime":"0001-01-01T00:00:00","PC":null}]
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
         * tradeid : 119
         * trademarksGUID : 5BABA2071A694DCC9BC9573D519B0AB7
         * userGUID : 3914F8E3FC76459F8AC9EE8D9AC631CF
         * tradetime : 2017-11-06 18:19:00
         * tradetype : 1
         * tradeamount : 169.3
         * beforebalance : 0.0
         * lastbalance : 169.3
         * remark : 帐户余额充值
         * OrderNumber : 149008667220171106181900161
         * States : 1
         * BeforeFrozenCapital : 0.00
         * LastFrozenCapital : 0.00
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
         * companyGUID : 
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
         * FrozenMoney : 0.0
         * MInvoiceType : 0
         * InvoiceHead : null
         * InvoiceNumber : null
         * InvoiceAddress : null
         * InvoicePhone : null
         * MoneyDateTime : 0001-01-01T00:00:00
         * FrozenMoneyDateTime : 0001-01-01T00:00:00
         * PC : null
         */

        private String tradeid;
        private String trademarksGUID;
        private String userGUID;
        private String tradetime;
        private String tradetype;
        private String tradeamount;
        private String beforebalance;
        private String lastbalance;
        private String remark;
        private String OrderNumber;
        private String States;
        private String BeforeFrozenCapital;
        private String LastFrozenCapital;
        private String userid;
        private String GUID;
        private String username;
        private String usertype;
        private String truename;
        private String mobile;
        private String loginterminal;
        private String logintime;
        private String logintimes;
        private String wechatid;
        private String idcard;
        private String companyGUID;
        private String company;
        private String creditlevel;
        private String cargocount;
        private String ownerbill;
        private String ownerscore;
        private String driverScoreNumber;
        private String driverTotalScore;
        private String driverbill;
        private String driverscore;
        private String online;
        private String credit;
        private String money;
        private String deposit;
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
        private String PageNum;
        private String FrozenMoney;
        private String MInvoiceType;
        private String InvoiceHead;
        private String InvoiceNumber;
        private String InvoiceAddress;
        private String InvoicePhone;
        private String MoneyDateTime;
        private String FrozenMoneyDateTime;
        private String PC;

        public String getTradeid() {
            return tradeid;
        }

        public void setTradeid(String tradeid) {
            this.tradeid = tradeid;
        }

        public String getTrademarksGUID() {
            return trademarksGUID;
        }

        public void setTrademarksGUID(String trademarksGUID) {
            this.trademarksGUID = trademarksGUID;
        }

        public String getUserGUID() {
            return userGUID;
        }

        public void setUserGUID(String userGUID) {
            this.userGUID = userGUID;
        }

        public String getTradetime() {
            return tradetime;
        }

        public void setTradetime(String tradetime) {
            this.tradetime = tradetime;
        }

        public String getTradetype() {
            return tradetype;
        }

        public void setTradetype(String tradetype) {
            this.tradetype = tradetype;
        }

        public String getTradeamount() {
            return tradeamount;
        }

        public void setTradeamount(String tradeamount) {
            this.tradeamount = tradeamount;
        }

        public String getBeforebalance() {
            return beforebalance;
        }

        public void setBeforebalance(String beforebalance) {
            this.beforebalance = beforebalance;
        }

        public String getLastbalance() {
            return lastbalance;
        }

        public void setLastbalance(String lastbalance) {
            this.lastbalance = lastbalance;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getOrderNumber() {
            return OrderNumber;
        }

        public void setOrderNumber(String OrderNumber) {
            this.OrderNumber = OrderNumber;
        }

        public String getStates() {
            return States;
        }

        public void setStates(String States) {
            this.States = States;
        }

        public String getBeforeFrozenCapital() {
            return BeforeFrozenCapital;
        }

        public void setBeforeFrozenCapital(String BeforeFrozenCapital) {
            this.BeforeFrozenCapital = BeforeFrozenCapital;
        }

        public String getLastFrozenCapital() {
            return LastFrozenCapital;
        }

        public void setLastFrozenCapital(String LastFrozenCapital) {
            this.LastFrozenCapital = LastFrozenCapital;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
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

        public String getLogintimes() {
            return logintimes;
        }

        public void setLogintimes(String logintimes) {
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

        public String getCreditlevel() {
            return creditlevel;
        }

        public void setCreditlevel(String creditlevel) {
            this.creditlevel = creditlevel;
        }

        public String getCargocount() {
            return cargocount;
        }

        public void setCargocount(String cargocount) {
            this.cargocount = cargocount;
        }

        public String getOwnerbill() {
            return ownerbill;
        }

        public void setOwnerbill(String ownerbill) {
            this.ownerbill = ownerbill;
        }

        public String getOwnerscore() {
            return ownerscore;
        }

        public void setOwnerscore(String ownerscore) {
            this.ownerscore = ownerscore;
        }

        public String getDriverScoreNumber() {
            return driverScoreNumber;
        }

        public void setDriverScoreNumber(String driverScoreNumber) {
            this.driverScoreNumber = driverScoreNumber;
        }

        public String getDriverTotalScore() {
            return driverTotalScore;
        }

        public void setDriverTotalScore(String driverTotalScore) {
            this.driverTotalScore = driverTotalScore;
        }

        public String getDriverbill() {
            return driverbill;
        }

        public void setDriverbill(String driverbill) {
            this.driverbill = driverbill;
        }

        public String getDriverscore() {
            return driverscore;
        }

        public void setDriverscore(String driverscore) {
            this.driverscore = driverscore;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
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

        public String getPageNum() {
            return PageNum;
        }

        public void setPageNum(String PageNum) {
            this.PageNum = PageNum;
        }

        public String getFrozenMoney() {
            return FrozenMoney;
        }

        public void setFrozenMoney(String FrozenMoney) {
            this.FrozenMoney = FrozenMoney;
        }

        public String getMInvoiceType() {
            return MInvoiceType;
        }

        public void setMInvoiceType(String MInvoiceType) {
            this.MInvoiceType = MInvoiceType;
        }

        public String getInvoiceHead() {
            return InvoiceHead;
        }

        public void setInvoiceHead(String InvoiceHead) {
            this.InvoiceHead = InvoiceHead;
        }

        public String getInvoiceNumber() {
            return InvoiceNumber;
        }

        public void setInvoiceNumber(String InvoiceNumber) {
            this.InvoiceNumber = InvoiceNumber;
        }

        public String getInvoiceAddress() {
            return InvoiceAddress;
        }

        public void setInvoiceAddress(String InvoiceAddress) {
            this.InvoiceAddress = InvoiceAddress;
        }

        public String getInvoicePhone() {
            return InvoicePhone;
        }

        public void setInvoicePhone(String InvoicePhone) {
            this.InvoicePhone = InvoicePhone;
        }

        public String getMoneyDateTime() {
            return MoneyDateTime;
        }

        public void setMoneyDateTime(String MoneyDateTime) {
            this.MoneyDateTime = MoneyDateTime;
        }

        public String getFrozenMoneyDateTime() {
            return FrozenMoneyDateTime;
        }

        public void setFrozenMoneyDateTime(String FrozenMoneyDateTime) {
            this.FrozenMoneyDateTime = FrozenMoneyDateTime;
        }

        public String getPC() {
            return PC;
        }

        public void setPC(String PC) {
            this.PC = PC;
        }
    }
}
