package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/8.
 */

public class SplashActivity extends BaseActivity {

    private String guid;
    private String mobile;
    private String key;
    private Map<String,String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);

    }
    private void checkLogin()
    {
        guid = ToolsUtils.getString(SplashActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(SplashActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(SplashActivity.this, Constant.KEY,"");
        map = new HashMap<String,String>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        String jsonVal = JsonUtils.getInstance().getJsonStr(map);
        RetrofitUtils.getRetrofitService()
                .checkLogin("Login", Config.CHECK_LOGIN,jsonVal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        if (getCodeBean.getErrorCode().equals("202"))
                        {
                            ToolsUtils.getInstance().loginOut(SplashActivity.this);
                        }
                    }
                });
    }
}
