package com.app.gaolonglong.fragmenttabhost.service;

import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

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
   /* @Multipart
    @POST()
    Call<String> uploadImg(@Part("description") RequestBody description,
                           @Part Multipart);*/
}
