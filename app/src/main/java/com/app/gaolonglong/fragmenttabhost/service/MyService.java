package com.app.gaolonglong.fragmenttabhost.service;

import okhttp3.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

import com.app.gaolonglong.fragmenttabhost.bean.CompanyInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.bean.RequestPostBody;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.bean.UpdateIdCardBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<GetCodeBean> setCarsInfo(@Field("MethodName") String method,
                                        @Field("PageName") String page,
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
}