package com.app.gaolonglong.fragmenttabhost.bean;

/**
 * Created by yanqi on 2017/8/25.
 */

public class RequestPostBody {

    public String PageName;
    public String MethodName;
    public String JsonValue;
    public String SecreKey;

    public RequestPostBody(String page,String method,String json,String md5)
    {
        this.PageName = page;
        this.MethodName = method;
        this.JsonValue = json;
        this.SecreKey = md5;
    }

    public String getPageName() {
        return PageName;
    }

    public void setPageName(String PageName) {
        this.PageName = PageName;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName = MethodName;
    }

    public String getJsonValue() {
        return JsonValue;
    }

    public void setJsonValue(String JsonValue) {
        this.JsonValue = JsonValue;
    }
    public String getMDSValue() {
        return SecreKey;
    }

    public void setMDSValue(String MDSValue) {
        this.SecreKey = MDSValue;
    }
}
