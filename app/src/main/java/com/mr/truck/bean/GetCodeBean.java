package com.mr.truck.bean;

/**
 * Created by yanqi on 2017/8/22.
 */

public class GetCodeBean {

    /**
     * errorCode : 1
     * errorMsg : 发送成功
     * data : null
     */

    private String errorCode;
    private String errorMsg;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
