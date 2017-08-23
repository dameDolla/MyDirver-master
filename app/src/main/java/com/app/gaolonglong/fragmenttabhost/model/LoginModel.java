package com.app.gaolonglong.fragmenttabhost.model;

import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.service.MyService;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;

import retrofit.Call;

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

    public Call<LoginBean> getLoginInfo(String md5Str,String json,String pageName)
    {
        Call<LoginBean> call = RetrofitUtils.getInstance().create(MyService.class).
                login(Config.LOGIN_API_URL,md5Str,json,pageName);

        return call;
    }

    //获取短信验证码
    public Call<GetCodeBean> getCode(String md5Str,String json,String pageName)
    {
        Call<GetCodeBean> call = RetrofitUtils.getInstance().create(MyService.class).
                getCode(Config.APPLOGIN_SMS,md5Str,json,pageName);

        return call;
    }
}
