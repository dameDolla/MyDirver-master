package com.mr.truck.utils;

import com.mr.truck.config.Config;
import com.mr.truck.service.MyService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


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
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(MyService.class);
    }
    private class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody,Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }
}
