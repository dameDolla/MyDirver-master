package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/11.
 */

public class DriverBean {
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
    public static class DataBean{
        private String boundcomsGUID;
        private String boundid;
        private String companyGUID;
        private String company;
        private String userGUID;
        private String username;
        private String asktime;
        private String remark;
        private String answerby;
        private String answertime;
        private String result;

        public String getAnswerby() {
            return answerby;
        }

        public void setAnswerby(String answerby) {
            this.answerby = answerby;
        }

        public String getAnswertime() {
            return answertime;
        }

        public void setAnswertime(String answertime) {
            this.answertime = answertime;
        }

        public String getAsktime() {
            return asktime;
        }

        public void setAsktime(String asktime) {
            this.asktime = asktime;
        }

        public String getBoundcomsGUID() {
            return boundcomsGUID;
        }

        public void setBoundcomsGUID(String boundcomsGUID) {
            this.boundcomsGUID = boundcomsGUID;
        }

        public String getBoundid() {
            return boundid;
        }

        public void setBoundid(String boundid) {
            this.boundid = boundid;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getUserGUID() {
            return userGUID;
        }

        public void setUserGUID(String userGUID) {
            this.userGUID = userGUID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }
}
