package com.app.gaolonglong.fragmenttabhost.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yanqi on 2017/11/22.
 */

public class WXPayBean {

    /**
     * errorCode : 200
     * errorMsg : 成功
     * data : [{"appid":"wx610da168ed9584f1","partnerid":"1491282102","prepayid":"wx20171122144639fa408c68880572210773","noncestr":"841PG3AW2CB4CQHF41PF","timestamp":"1511333072","package":"Sign=WXPay","sign":"DA3662D97DD273F29BEB4B73E5017A1F"}]
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
         * appid : wx610da168ed9584f1
         * partnerid : 1491282102
         * prepayid : wx20171122144639fa408c68880572210773
         * noncestr : 841PG3AW2CB4CQHF41PF
         * timestamp : 1511333072
         * package : Sign=WXPay
         * sign : DA3662D97DD273F29BEB4B73E5017A1F
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;
        @SerializedName("package")
        private String packageX;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
