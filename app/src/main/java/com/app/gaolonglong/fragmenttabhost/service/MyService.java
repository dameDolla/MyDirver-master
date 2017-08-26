package com.app.gaolonglong.fragmenttabhost.service;

import okhttp3.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;

import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.bean.RequestPostBody;
import com.app.gaolonglong.fragmenttabhost.config.Config;

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
                          @Query("MDSValue") String md5,
                          @Query("JsonValue") String val,
                          @Query("PageName") String page
                          );

    /**
     * 获取短信验证码接口
     */
    @GET(Config.host)
    Call<GetCodeBean> getCode(@Query("MethodName") String method,
                              @Query("MDSValue") String md5,
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
    @POST(Config.host)
    Observable<GetCodeBean> upload_cargoupinfo_one(@Body RequestPostBody body);

    /**
     * 获取车队公司信息
     */
    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST(Config.host)
    Observable<GetCodeBean> getConpanyInfo(@Body RequestPostBody body);

    @Headers({"Content-type:application/json","Content-Length:59"})
    @POST(Config.host)
    Call<GetCodeBean> getConpanyInfos(@Field("MethodName") String name,
                                      @Field("PageName") String page,
                                      @Field("JsonValue") String json,
                                      @Field("MDSValue") String md5);

    /**
     * 获取公司信息
     */
    @GET(Config.host)
    Call<GetCodeBean> getCompany(@Query("MethodName") String method,
                              @Query("MDSValue") String md5,
                              @Query("JsonValue") String val,
                              @Query("PageName") String page);

}
