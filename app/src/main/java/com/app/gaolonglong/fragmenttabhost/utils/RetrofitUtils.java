package com.app.gaolonglong.fragmenttabhost.utils;

import com.app.gaolonglong.fragmenttabhost.config.Config;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by yanqi on 2017/8/15.
 * 各个接口的网络请求类
 */

public class RetrofitUtils {

    public static volatile RetrofitUtils instance = null;
    private final Retrofit retrofit;

    private RetrofitUtils(){
        //增加返回值为实体类的支持
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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

    public <T> T create(final Class<T>  service)
    {
        return retrofit.create(service);
    }

}
