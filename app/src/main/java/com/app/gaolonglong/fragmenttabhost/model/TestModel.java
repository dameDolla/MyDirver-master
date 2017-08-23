package com.app.gaolonglong.fragmenttabhost.model;

import com.app.gaolonglong.fragmenttabhost.TestService;
import com.app.gaolonglong.fragmenttabhost.bean.TestBean;
import com.app.gaolonglong.fragmenttabhost.bean.UserBean;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import retrofit.Call;

/**
 * Created by yanqi on 2017/8/18.
 */

public class TestModel {

    public static volatile TestModel mTestModel=null;

    public static TestModel getInstance()
    {
        if(mTestModel == null)
        {
            synchronized (TestModel.class)
            {
                if(mTestModel == null)
                {
                    mTestModel = new TestModel();
                    return mTestModel;
                }
            }
        }
        return mTestModel;
    }


    public Call getTestData()
    {
        Call<TestBean> call =  RetrofitUtils.getInstance().
                create(TestService.class).
                getTestData("appLoginSMS",
                        /*ToolsUtils.getInstance().getMD5Val("{'mobile':'15908690321'}")*/
                        "Uib51EeXDiGbjGMbaRDhEA==",
                        "{'mobile':'17620336613'}");
        if(call == null)
        {
            return null;
        }
        return call;
    }

}
