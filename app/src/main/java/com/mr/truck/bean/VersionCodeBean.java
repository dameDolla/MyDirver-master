package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/10/10.
 */

public class VersionCodeBean {

    /**
     * errorCode : 200
     * errorMsg :
     * data : [{"VersionNumber":"1","AppAddress":"http://120.78.77.63:8023/SD.apk"}]
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
         * VersionNumber : 1
         * AppAddress : http://120.78.77.63:8023/SD.apk
         */

        private String VersionNumber;
        private String AppAddress;

        public String getVersionNumber() {
            return VersionNumber;
        }

        public void setVersionNumber(String VersionNumber) {
            this.VersionNumber = VersionNumber;
        }

        public String getAppAddress() {
            return AppAddress;
        }

        public void setAppAddress(String AppAddress) {
            this.AppAddress = AppAddress;
        }
    }
}
