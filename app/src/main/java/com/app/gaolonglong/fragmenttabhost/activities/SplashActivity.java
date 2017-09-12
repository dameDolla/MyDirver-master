package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
        if(!ToolsUtils.getString(SplashActivity.this,Constant.LOGIN_GUID,"").equals(""))
        {
            checkLogin();
        }

        toMain();
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
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                        //ToolsUtils.getInstance().toastShowStr(SplashActivity.this,loginBean.getData().toString());
                        if (loginBean.getErrorCode().equals("202"))
                        {
                            ToolsUtils.getInstance().loginOut(SplashActivity.this);

                        }else if (loginBean.getErrorCode().equals("200"))
                        {
                            ToolsUtils.putString(SplashActivity.this,Constant.KEY,loginBean.getData().get(0).getSecreKey());
                            ToolsUtils.putString(SplashActivity.this,Constant.USRE_TYPE,loginBean.getData().get(0).getUsertype());
                            ToolsUtils.putString(SplashActivity.this,Constant.VTRUENAME,loginBean.getData().get(0).getVtruename());
                            ToolsUtils.putString(SplashActivity.this,Constant.HEADLOGO,loginBean.getData().get(0).getAvatarAddress());
                            ToolsUtils.getInstance().toastShowStr(SplashActivity.this,loginBean.getData().get(0).getAvatarAddress());
                        }
                      //  toMain();
                    }
                });
    }
    /**
     * 3秒后跳转
     */
    private void toMain()
    {
        final Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(task,1000*3);
    }
}
