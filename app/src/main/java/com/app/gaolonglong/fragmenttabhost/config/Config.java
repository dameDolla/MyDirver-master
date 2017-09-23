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
    //检查登录接口
    public static final String CHECK_LOGIN = "Login";
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
    //获取空程计划 已发布
    public static final String RELEASE_FABU = "SelectByTruckplanStatus_1_7";
    //获取空程计划  已取消和已完成
    public static final String RELEASE_CANCEL = "SelectByTruckplanStatus_02";
    //修改空车计划
    public static final String RELEASE_UPDATE = "UpdateTruckplan";
    //删除空程计划
    public static final String RELEASE_DEL = "DeleteTruckplan";
    //添加订阅路线
    public static final String ROUTE_ADD = "AddLineInfo";
    //修改订阅路线
    public static final String ROUTE_UPDATE = "UpdateLineMainLineState";
    //删除订阅路线
    public static final String ROUTE_DEL = "DeleteLineInfo";
    //根据始发地查询货源信息
    public static final String SRC_FROMSIDE = "SelectCargoInfo";
    //根据空程GUID查询货源信息
    public static final String SRC_KONGCHENG = "SelectCargoXQBytruckplansGUID";
    //根据订阅线路查询货源信息
    public static final String SRC_ROUTE = "SelectCargoXQBXL";
    //根据司机（车队）GUID查询所有的报价信息
    public static final String GET_BAOJIALIST = "SelectCargoprice";
    //根据添加报价信息
    public static final String ADD_BAOJIA = "AddCargoprice";
    //根据司机修改报价
    public static final String UPDATE_BAOJIA = "UpdateCargoprice";
    //取消报价
    public static final String CANCEL_BAOJIA = "CancelCargoPrice";
    //确认报价
    public static final String AGREE_BAOJIA = "OKCargoprice";


}
