package com.mr.truck.bean;

import java.util.List;

/**
 * Created by yanqi on 2017/9/29.
 */

public class ParametersBean {

    /**
     * errorCode : 200
     * errorMsg : 无数据
     * data : [{"ParameterID":1,"ParameterValue":"1","ParameterText":"货主","ParameterType":"UserType"},{"ParameterID":2,"ParameterValue":"2","ParameterText":"个体司机","ParameterType":"UserType"},{"ParameterID":3,"ParameterValue":"3","ParameterText":"公司司机","ParameterType":"UserType"},{"ParameterID":4,"ParameterValue":"4","ParameterText":"调度","ParameterType":"UserType"},{"ParameterID":5,"ParameterValue":"1","ParameterText":"头像","ParameterType":"ImgType"},{"ParameterID":6,"ParameterValue":"2","ParameterText":"身份证(正)","ParameterType":"ImgType"},{"ParameterID":7,"ParameterValue":"3","ParameterText":"身份证(反)","ParameterType":"ImgType"},{"ParameterID":8,"ParameterValue":"4","ParameterText":"驾驶证","ParameterType":"ImgType"},{"ParameterID":9,"ParameterValue":"5","ParameterText":"车辆照片(前脸)","ParameterType":"ImgType"},{"ParameterID":10,"ParameterValue":"6","ParameterText":"车辆照片(侧面)","ParameterType":"ImgType"},{"ParameterID":11,"ParameterValue":"7","ParameterText":"车辆照片(尾部)","ParameterType":"ImgType"},{"ParameterID":12,"ParameterValue":"8","ParameterText":"车辆照片(车厢内部)","ParameterType":"ImgType"},{"ParameterID":13,"ParameterValue":"9","ParameterText":"营业执照","ParameterType":"ImgType"},{"ParameterID":14,"ParameterValue":"10","ParameterText":"行驶证","ParameterType":"ImgType"},{"ParameterID":15,"ParameterValue":"11","ParameterText":"法人代表身份证(正)","ParameterType":"ImgType"},{"ParameterID":16,"ParameterValue":"12","ParameterText":"法人代表身份证(反)","ParameterType":"ImgType"},{"ParameterID":17,"ParameterValue":"0","ParameterText":"注册用户","ParameterType":"LevelType"},{"ParameterID":18,"ParameterValue":"1","ParameterText":"认证用户","ParameterType":"LevelType"},{"ParameterID":19,"ParameterValue":"2","ParameterText":"诚信用户","ParameterType":"LevelType"},{"ParameterID":20,"ParameterValue":"0","ParameterText":"空车","ParameterType":"CarStatus"},{"ParameterID":21,"ParameterValue":"1","ParameterText":"运输中","ParameterType":"CarStatus"},{"ParameterID":22,"ParameterValue":"0","ParameterText":"不需要","ParameterType":"DemandStatus"},{"ParameterID":23,"ParameterValue":"1","ParameterText":"需要","ParameterType":"DemandStatus"},{"ParameterID":24,"ParameterValue":"0","ParameterText":"已发布","ParameterType":"TransactionStatus"},{"ParameterID":25,"ParameterValue":"1","ParameterText":"洽谈中","ParameterType":"TransactionStatus"},{"ParameterID":26,"ParameterValue":"2","ParameterText":"已成交","ParameterType":"TransactionStatus"},{"ParameterID":27,"ParameterValue":"3","ParameterText":"已关闭","ParameterType":"TransactionStatus"},{"ParameterID":28,"ParameterValue":"0","ParameterText":"已生成","ParameterType":"SubbillStatus"},{"ParameterID":29,"ParameterValue":"1","ParameterText":"已预报","ParameterType":"SubbillStatus"},{"ParameterID":30,"ParameterValue":"2","ParameterText":"已执行","ParameterType":"SubbillStatus"},{"ParameterID":31,"ParameterValue":"3","ParameterText":"已签收","ParameterType":"SubbillStatus"},{"ParameterID":32,"ParameterValue":"9","ParameterText":"已完成","ParameterType":"SubbillStatus"},{"ParameterID":33,"ParameterValue":"1","ParameterText":"充值","ParameterType":"TransactionType"},{"ParameterID":34,"ParameterValue":"2","ParameterText":"支付诚意金","ParameterType":"TransactionType"},{"ParameterID":35,"ParameterValue":"3","ParameterText":"支付信息费","ParameterType":"TransactionType"},{"ParameterID":36,"ParameterValue":"4","ParameterText":"支付运费","ParameterType":"TransactionType"},{"ParameterID":37,"ParameterValue":"5","ParameterText":"扣罚金","ParameterType":"TransactionType"},{"ParameterID":38,"ParameterValue":"9","ParameterText":"提现","ParameterType":"TransactionType"},{"ParameterID":39,"ParameterValue":"0","ParameterText":"申请中","ParameterType":"BindingStatus"},{"ParameterID":40,"ParameterValue":"1","ParameterText":"绑定成功","ParameterType":"BindingStatus"},{"ParameterID":41,"ParameterValue":"2","ParameterText":"取消绑定","ParameterType":"BindingStatus"},{"ParameterID":42,"ParameterValue":"0","ParameterText":"未认证","ParameterType":"VcompanyStatus"},{"ParameterID":43,"ParameterValue":"1","ParameterText":"提交","ParameterType":"VcompanyStatus"},{"ParameterID":44,"ParameterValue":"2","ParameterText":"不合格","ParameterType":"VcompanyStatus"},{"ParameterID":45,"ParameterValue":"9","ParameterText":"已认证","ParameterType":"VcompanyStatus"},{"ParameterID":46,"ParameterValue":"1","ParameterText":"企业或车队","ParameterType":"CompanyType"},{"ParameterID":47,"ParameterValue":"2","ParameterText":"信息部","ParameterType":"CompanyType"},{"ParameterID":48,"ParameterValue":"0","ParameterText":"不允许","ParameterType":"ChangeableType"},{"ParameterID":49,"ParameterValue":"1","ParameterText":"允许","ParameterType":"ChangeableType"},{"ParameterID":50,"ParameterValue":"0","ParameterText":"已取消","ParameterType":"TruckplanStatus"},{"ParameterID":51,"ParameterValue":"1","ParameterText":"已发布","ParameterType":"TruckplanStatus"},{"ParameterID":52,"ParameterValue":"1","ParameterText":"已完成","ParameterType":"TruckplanStatus"}]
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
         * ParameterID : 1
         * ParameterValue : 1
         * ParameterText : 货主
         * ParameterType : UserType
         */

        private int ParameterID;
        private String ParameterValue;
        private String ParameterText;
        private String ParameterType;

        public int getParameterID() {
            return ParameterID;
        }

        public void setParameterID(int ParameterID) {
            this.ParameterID = ParameterID;
        }

        public String getParameterValue() {
            return ParameterValue;
        }

        public void setParameterValue(String ParameterValue) {
            this.ParameterValue = ParameterValue;
        }

        public String getParameterText() {
            return ParameterText;
        }

        public void setParameterText(String ParameterText) {
            this.ParameterText = ParameterText;
        }

        public String getParameterType() {
            return ParameterType;
        }

        public void setParameterType(String ParameterType) {
            this.ParameterType = ParameterType;
        }
    }
}
