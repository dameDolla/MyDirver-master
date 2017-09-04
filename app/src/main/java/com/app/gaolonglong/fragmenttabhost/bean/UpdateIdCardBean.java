package com.app.gaolonglong.fragmenttabhost.bean;

/**
 * Created by yanqi on 2017/8/25.
 */

public class UpdateIdCardBean {

    public String mobile;
    public String GUID;
    public String idcard;
    public String SecreKey;
    public String truname;
    public String usertype;

    public UpdateIdCardBean(String mobile,String GUID,String idcard,String secrekey,String truename,String usertype)
    {
        this.mobile = mobile;
        this.GUID = GUID;
        this.idcard = idcard;
        this.SecreKey = secrekey;
        this.truname = truename;
        this.usertype = usertype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    public String getSecreKey() {
        return SecreKey;
    }

    public void setSecreKey(String secreKey) {
        this.SecreKey = secreKey;
    }

    public String getTruname(){ return truname; }

    public void setTruname(String truname){ this.truname = truname; }

    public String getUsertype(){ return usertype; }

    public void setUsertype(String usertype){ this.usertype = usertype; }
}
