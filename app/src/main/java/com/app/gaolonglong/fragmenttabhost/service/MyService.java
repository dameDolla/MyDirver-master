package com.app.gaolonglong.fragmenttabhost.service;

import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import rx.Observable;

import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.app.gaolonglong.fragmenttabhost.bean.CarinfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.DriverBean;
import com.app.gaolonglong.fragmenttabhost.bean.InvitedSrcBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionListBean;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaListBean;
import com.app.gaolonglong.fragmenttabhost.bean.CompanyInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.bean.ParametersBean;
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.bean.TruckLengthBean;
import com.app.gaolonglong.fragmenttabhost.bean.VersionCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.WXPayBean;
import com.app.gaolonglong.fragmenttabhost.bean.WallteListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yanqi on 2017/8/21.
 */

public interface MyService {
    /**
     * 登录接口
     * 手机号和验证码
     * mobile  SMSCode
     */
    @GET(Config.host)
    Call<LoginBean> login(@Query("MethodName") String method,
                          @Query("JsonValue") String val,
                          @Query("PageName") String page
    );

    /**
     * 检查登录
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<LoginBean> checkLogin(@Field(Constant.PAGENAME) String page,
                                              @Field(Constant.METHOD) String method,
                                              @Field("JsonValue") String json);

    /**
     * 获取短信验证码接口
     */
    @GET(Config.host)
    Call<GetCodeBean> getCode(@Query("MethodName") String method,
                              @Query("JsonValue") String val,
                              @Query("PageName") String page);

    /**
     * 上传单张图片
     */
    @POST(Config.UPLOAD_URL)
    Observable<ResponseBody> upload_avatar(@Body RequestBody body);

    /**
     * 车队司机认证提交姓名 身份证号码
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> upload_cargoupinfo_one(/*@Body UpdateIdCardBean body,*/
                                                   @Field(Constant.PAGENAME) String page,
                                                   @Field(Constant.METHOD) String method,
                                                   @Field("JsonValue") String json);

    /**
     * 获取车队公司信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<CompanyInfoBean> getConpanyInfo(@Field(Constant.PAGENAME) String page,
                                               @Field(Constant.METHOD) String method,
                                               @Field("JsonValue") String json);

    /**
     * 提交绑定公司
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> bindCompany(@Field(Constant.PAGENAME) String page,
                                               @Field(Constant.METHOD) String method,
                                               @Field("JsonValue") String json);

    /**
     * 个体司机认证提交车辆信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> setCarsInfo(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);

    /**
     * 调度公司验证
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean>  setDiaoduInfo(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 获取个人的订阅路线
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<RouteListBean>  getMyRouteList(@Field(Constant.PAGENAME) String page,
                                              @Field(Constant.METHOD) String method,
                                              @Field("JsonValue") String json);
    /**
     * 发布空程
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> addRelease(@Field(Constant.PAGENAME) String page,
                                       @Field(Constant.METHOD) String method,
                                       @Field("JsonValue") String json);
    /**
     * 添加线路
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> addRoute(@Field(Constant.PAGENAME) String page,
                                     @Field(Constant.METHOD) String method,
                                     @Field("JsonValue") String json);
    /**
     * 获取个人发布的空程
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<ReleaseBean> getFabuRelease(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 获取个人取消或者完成的空程
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<ReleaseBean> getCancleRelease(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 取消空程计划
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> cancleRelease(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 删除空程
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> delRelease(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 根据始发地查询货源信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetSRCBean> getSRCWithFromside(@Field(Constant.PAGENAME) String page,
                                                 @Field(Constant.METHOD) String method,
                                                 @Field("JsonValue") String json);


    /**
     * 修改订阅线路的默认线路
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetSRCBean> setRouteMain(@Field(Constant.PAGENAME) String page,
                                              @Field(Constant.METHOD) String method,
                                              @Field("JsonValue") String json);
    /**
     * 添加报价
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetSRCBean> AddBaojia(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);
    /**
     * 设置支付密码
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> setPayCode(@Field(Constant.PAGENAME) String page,
                                     @Field(Constant.METHOD) String method,
                                     @Field("JsonValue") String json);
    /**
     * 获取报价列表
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<BaojiaListBean> getBaojiaList(@Field(Constant.PAGENAME) String page,
                                             @Field(Constant.METHOD) String method,
                                             @Field("JsonValue") String json);
    /**
     * 修改报价
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> updateBaojia(@Field(Constant.PAGENAME) String page,
                                             @Field(Constant.METHOD) String method,
                                             @Field("JsonValue") String json);
    /**
     * 确认报价
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> agreeBaojia(@Field(Constant.PAGENAME) String page,
                                         @Field(Constant.METHOD) String method,
                                         @Field("JsonValue") String json);
    /**
     * 取消报价
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> cancelBaojia(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);
    /**
     * 添加支付密码 获取验证码
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> getMSG(@Field(Constant.PAGENAME) String page,
                                         @Field(Constant.METHOD) String method,
                                         @Field("JsonValue") String json);
    /**
     *
     * 获取运单列表
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<MissionListBean> getMissionList(@Field(Constant.PAGENAME) String page,
                                               @Field(Constant.METHOD) String method,
                                               @Field("JsonValue") String json);
    /**
     * 获取所有的参数
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<ParametersBean> getParameters(@Field(Constant.PAGENAME) String page,
                                             @Field(Constant.METHOD) String method,
                                             @Field("JsonValue") String json);
    /**
     * 改变运单状态
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> changeMissionStatus(@Field(Constant.PAGENAME) String page,
                                             @Field(Constant.METHOD) String method,
                                             @Field("JsonValue") String json);
    /**
     * 获取车队车辆列表
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<CarTeamBean> getCarTeamList(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 添加车辆
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> addCars(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 实时上传车辆位置
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> uploadLatLng(@Field(Constant.PAGENAME) String page,
                                    @Field(Constant.METHOD) String method,
                                    @Field("JsonValue") String json);
    /**
     * 检查版本更新
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<VersionCodeBean> checkUpdate(@Field(Constant.PAGENAME) String page,
                                            @Field(Constant.METHOD) String method);
    /**
     * 获取一个全新guid
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> getGuid(@Field(Constant.PAGENAME) String page,
                                            @Field(Constant.METHOD) String method);
    /**
     * 获取车辆信息  个体司机获取一辆车的信息  平台获取所有车的信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<CarinfoBean> getCarInfo(@Field(Constant.PAGENAME) String page,
                                       @Field(Constant.METHOD) String method,
                                       @Field("JsonValue") String json);
    /**
     * 根据车辆GUID删除车辆信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> delCar(@Field(Constant.PAGENAME) String page,
                                       @Field(Constant.METHOD) String method,
                                       @Field("JsonValue") String json);
    /**
     * 车队指派司机配送运单
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> getDriverToMission(@Field(Constant.PAGENAME) String page,
                                   @Field(Constant.METHOD) String method,
                                   @Field("JsonValue") String json);
    /**
     * 获取车队司机
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<LoginBean> getDriverInfo(@Field(Constant.PAGENAME) String page,
                                         @Field(Constant.METHOD) String method,
                                         @Field("JsonValue") String json);
    /**
     * 根据billsGUID查找货源详情
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetSRCBean> getSrcDetail(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);
    /**
     * 运单绑定车辆
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> bindCar(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);
    /**
     * 取消运单
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> cancelMission(@Field(Constant.PAGENAME) String page,
                                    @Field(Constant.METHOD) String method,
                                    @Field("JsonValue") String json);
    /**
     * 发票设置
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> fapiaoSetting(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 发票设置
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> setBillNewload(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 获取账户余额 冻结资金
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> getMoneyInfo(@Field(Constant.PAGENAME) String page,
                                           @Field(Constant.METHOD) String method,
                                           @Field("JsonValue") String json);
    /**
     * 获取交易记录列表
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<WallteListBean> getJiaoyiList(@Field(Constant.PAGENAME) String page,
                                             @Field(Constant.METHOD) String method,
                                             @Field("JsonValue") String json);
    /**
     * 查询空程邀请表关联的货源数据
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<InvitedSrcBean> getSrcByKc(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 拒绝空程邀请
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> cancelSRCByKc(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 添加银行卡
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> addBankCard(@Field(Constant.PAGENAME) String page,
                                          @Field(Constant.METHOD) String method,
                                          @Field("JsonValue") String json);
    /**
     * 提现
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> tiXian(@Field(Constant.PAGENAME) String page,
                                        @Field(Constant.METHOD) String method,
                                        @Field("JsonValue") String json);
    /**
     * 提现
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<GetCodeBean> getBindCardSMSCode(@Field(Constant.PAGENAME) String page,
                                   @Field(Constant.METHOD) String method,
                                   @Field("JsonValue") String json);
    /**
     * 提现
     */
    @FormUrlEncoded
    @POST(Config.WXPAYHost)
    Observable<WXPayBean> chongZhi(@Field("Money") String method, @Field("MD5Key") String json,
                                    @Field("GUID") String guid);

    /**
     * 提现
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<TruckLengthBean> getTruckLength(@Field(Constant.PAGENAME) String page,
                                               @Field(Constant.METHOD) String method,
                                               @Field("JsonValue") String json);
    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST(Config.host)
    Observable<LoginBean> getUserInfo(@Field(Constant.PAGENAME) String page,
                                               @Field(Constant.METHOD) String method,
                                               @Field("JsonValue") String json);
}
