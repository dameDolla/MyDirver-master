package com.app.gaolonglong.fragmenttabhost.config;

/**
 * Created by yanqi on 2017/7/21.
 */

public class Config {

    //服务器根地址
    public static final String baseURL ="http://192.168.1.102:8013";
    //服务器路径
    public static final String host = "/Handler/App.ashx";
    //上传图片
    public static  final String UPLOAD_URL = "/Handler/img.ashx";
    //登录接口
    public static final String LOGIN_API_URL = "appLogin";
    //获取短信验证码
    public static final String APPLOGIN_SMS = "appLoginSMS";
    //车队司机认证的姓名身份证的提交
    public static final String CARGROUP_ONE = "UpdateIdcard";
    //获取公司信息
    public static final String GET_COMPANYINFO = "SelectByCompanyOrID";
    //绑定公司
    public static final String BIND_COMPANY = "BindingCompany";
    //绑定车辆信息
    public static final String BIND_TRUCK = "UpdateTruck";
    //调度公司验证
    public static final String DIAODU_INFO = "DDCompany";
    //获取订阅线路
    public static final String MYROUTE_LIST = "SelectLineByGUID";
    //添加空程
    public static final String ADD_RELEASE = "AddTruckplan";


}
