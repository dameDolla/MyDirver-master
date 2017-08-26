package com.app.gaolonglong.fragmenttabhost.utils;

import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.service.MyService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yanqi on 2017/8/15.
 * 各个接口的网络请求类
 */

public class RetrofitUtils {

    public static volatile RetrofitUtils instance = null;
    private static OkHttpClient okHttpClient;

    private RetrofitUtils(){
        //设置okhttp的响应式时间
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())             //添加拦截器未全局设置header
                .addInterceptor(new LogInterceptor());               //添加拦截器打印日志
    }
    /**
     * 单利模式
     */
    public static RetrofitUtils getInstance()
    {
        if(instance == null)
        {
            synchronized (RetrofitUtils.class)
            {
                if(instance == null)
                {
                    instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }
    public static MyService getRetrofitService() {
        return getInstance().innGetRetrofitService();
    }

    private MyService innGetRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(MyService.class);
    }

}
