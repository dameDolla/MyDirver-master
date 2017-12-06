package com.mr.truck.model;

import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.LoginBean;
import com.mr.truck.config.Config;
import com.mr.truck.utils.RetrofitUtils;

import retrofit2.Call;


/**
 * Created by yanqi on 2017/8/21.
 */

public class LoginModel {

    public static volatile LoginModel mLoginModel = null;

    public static LoginModel getInstance()
    {
        if(mLoginModel == null)
        {
            synchronized (LoginModel.class)
            {
                if (mLoginModel == null)
                {
                    mLoginModel = new LoginModel();
                }
            }
        }
        return mLoginModel;
    }

    public Call<LoginBean> getLoginInfo( String json, String pageName)
    {
        Call<LoginBean> call = RetrofitUtils.getRetrofitService().
                login(Config.LOGIN_API_URL,json,pageName);

        return call;
    }

    //获取短信验证码
    public Call<GetCodeBean> getCode(String json,String pageName)
    {
        Call<GetCodeBean> call = RetrofitUtils.getRetrofitService().
                getCode(Config.APPLOGIN_SMS,json,pageName);

        return call;
    }
}
