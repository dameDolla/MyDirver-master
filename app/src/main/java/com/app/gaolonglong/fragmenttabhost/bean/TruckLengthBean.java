package com.app.gaolonglong.fragmenttabhost.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/11/20.
 */

public class TruckLengthBean {
    private String errorCode;
    private String errorMsg;
    private List<TruckLengthBean.DataBean> data;

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

    public List<TruckLengthBean.DataBean> getData() {
        return data;
    }

    public void setData(List<TruckLengthBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String TruckLengthID;
        private String TruckLengthText;
        private String IsEnabled;
        private String LimitWeight;
        private String LimitVolume;
        private String MileageCost;
        private String Remarks;

        public String getIsEnabled() {
            return IsEnabled;
        }

        public String getLimitVolume() {
            return LimitVolume;
        }

        public String getLimitWeight() {
            return LimitWeight;
        }

        public String getMileageCost() {
            return MileageCost;
        }

        public String getRemarks() {
            return Remarks;
        }

        public String getTruckLengthID() {
            return TruckLengthID;
        }

        public String getTruckLengthText() {
            return TruckLengthText;
        }
    }
}
