package com.mr.truck.config;

/**
 * Created by yanqi on 2017/7/21.
 */

public class Config {

    //服务器根地址  http://120.78.77.63:8023
    //public static final String baseURL ="http://192.168.1.102:8013";
    public static final String baseURL ="http://120.78.77.63:8023";
    //服务器路径
    public static final String host = "/Handler/App.ashx";
    //微信支付路径
    public static final String WXPAYHost = "/WX/WX_APP.ashx";
    //上传图片
    public static  final String UPLOAD_URL = "/Handler/img.ashx";
    //获取图片
    public static final String GET_IMG = "/Handler/GetImg.ashx";
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
    //添加支付密码获取验证码
    public static final String GET_MSG = "GetSMS";
    //添加支付密码
    public static final String ADD_PSW = "AddPwdBySMS";
    //验证是否设置支付密码是否正确
    public static final String CHECK_PAYCODE = "YZPwdKey";
    //根据短信验证码修改密码
    public static final String YZM_UPDATE_PAYCODE = "UpdateNewPwdBySMS";
    //根据旧密码修改密码
    public static final String OLD_UPDATE_PAYCODE = "UpdateNewPwd";
    //查询已完成的运单
    public static final String MISSION_DONE = "GetBillsByComplete";
    //查询取消的运单
    public static final String MISSION_CANCEL = "GetBillsByCancel";
    //查询执行中的运单
    public static final String MISSION_DOING = "GetBillsByProcess";
    //获取所有的参数
    public static final String GET_PARAMETERS = "GetParameterByModel";
    //运单修改状态为已预报
    public static final String MISSION_STATUS_YUBAO = "UpdateStatus1";
    //运单状态修改为到达装货地
    public static final String MISSION_STATUS_ARRIVED = "UpdateStatus2";
    //运单修改状态为已执行
    public static final String MISSION_STATUS_ZHIXING = "UpdateStatus3";
    //运单修改状态为已卸货
    public static final String MISSION_STATUS_XIEHUO = "UpdateStatus4";
    //运单修改状态为已签收
    public static final String MISSION_STATUS_QIANSHOU = "UpdateStatus5";
    //添加车辆信息
    public static final String ADDTRUCK = "AddInfos";
    //获取车队车辆列表
    public static final String GETTRUCKS = "SelectInfo";
    //实时上传车辆位置
    public static final String UPLOADLATLNG = "UpdateNewLoad";
    //检查版本更新
    public static final String CHECKVERSION = "GetVersion";
    //获取一个全新guid
    public static final String GETNEWGUID = "GetGUID";
    //根据司机GUID查询车辆信息
    public static final String GETCARINFO = "SelecTruckByMemberGUID";
    //根据车辆GUID删除车辆信息
    public static final String DELCAR = "DeleteTruckByGUID";
    //根据运单GUID修改司机GUID、司机名称、司机手机
    public static final String UPDATEDRIVER = "UpdateDriverByGUID";
    //根据车队（公司）查询司机信息
    public static final String GETDRIVERS = "SelectInfoByCompanyGUID";
    //根据billsGuid查找货源详情
    public static final String GETSRCDETAIL = "SelectCargoXQByCGUID";
    //绑定运单的车辆
    public static final String BINDCAR = "UpdateBillInfoByMemberGUID";
    //取消运单
    public static final String CANCELMISSION  = "CancellationOfOrder";
    //发票设置
    public static final String FAPIAOSETTING = "UpdateInvoiceTypeByBill";
    //更新运单的newLoad
    public static final String SETBILLNEWLOAD = "UpdateBillNewLoad";
    //更新用户的账户余额
    public static final String GETMONEY = "SelectMyMoney";
    //获取用户的交易记录
    public static final String GETJIAOYILIST = "SelectInfoByGUID";
    //查询空程邀请表关联的货源数据
    public static final String SELECTSRCBYKC = "SelectInfo_Invitation";
    //拒绝空程货源报价邀请
    public static final String CANCELSRCBYKC = "UpdateState_Invitation";
    //添加银行卡
    public static final String ADDBANKCARD = "UpdateBank";
    //提现
    public static final String TIXIAN = "Withdrawals";
    //获取绑定银行卡的验证码
    public static final String GETBINDCARDCODE = "GetBankSMSCode";
    //获取车长信息
    public static final String GETTRUCKLENGTH = "GetTruckLengthInfoNull";
    //用户获取自己的用户信息
    public static final String GETUSERINFO = "GetMyInfo";
    //验证通过手机号修改密码时的验证码是否正确
    public static final String CHECKSMS = "SelectNewPwdSMS";
}
