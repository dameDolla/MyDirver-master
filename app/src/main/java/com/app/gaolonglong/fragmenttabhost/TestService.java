package com.app.gaolonglong.fragmenttabhost;

import com.app.gaolonglong.fragmenttabhost.bean.TestBean;
import com.app.gaolonglong.fragmenttabhost.bean.UserBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.POST;

/**
 * Created by yanqi on 2017/8/21.
 */

public  interface TestService {

    @POST(Config.APPLOGIN_SMS)
    Call<TestBean> getTestData(@Field("MethodName") String method,
                               @Field("MDSValue") String mdVal,
                               @Field("JsonVlue") String jsonVal);

}
