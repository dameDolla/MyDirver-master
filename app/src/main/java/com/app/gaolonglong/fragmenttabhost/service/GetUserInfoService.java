package com.app.gaolonglong.fragmenttabhost.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.gaolonglong.fragmenttabhost.activities.SplashActivity;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/10/12.
 */

public class GetUserInfoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        MyThread myThread = new MyThread();
        myThread.start();
        super.onCreate();
    }

    private void getInfo(final Context context)
    {
        {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("GUID", GetUserInfoUtils.getGuid(context));
            map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(context));
            map.put(Constant.KEY, GetUserInfoUtils.getKey(context));
            final String jsonVal = JsonUtils.getInstance().getJsonStr(map);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    RetrofitUtils.getRetrofitService()
                            .checkLogin("Login", Config.CHECK_LOGIN, jsonVal)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<LoginBean>() {
                                @Override
                                public void onCompleted() {

                                }


                                @Override
                                public void onError(Throwable e) {
                                    Log.e("splasherror",e.getMessage());
                                    //ToolsUtils.getInstance().loginOut(context);
                                }

                                @Override
                                public void onNext(LoginBean loginBean) {
                                     Log.e("myservice",loginBean.getErrorMsg()+"---"+loginBean.getErrorCode());

                                    //ToolsUtils.getInstance().toastShowStr(context,loginBean.getErrorCode());
                                    if (loginBean.getErrorCode().equals("202")) {
                                        ToolsUtils.getInstance().loginOut(context);

                                    } else if (loginBean.getErrorCode().equals("200")) {
                                        ToolsUtils.putString(context,Constant.LOGIN_GUID,loginBean.getData().get(0).getGUID()+"");
                                        ToolsUtils.putString(context, Constant.KEY, loginBean.getData().get(0).getSecreKey()+"");
                                        ToolsUtils.putString(context, Constant.USRE_TYPE, loginBean.getData().get(0).getUsertype()+"");
                                        ToolsUtils.putString(context, Constant.VTRUENAME, loginBean.getData().get(0).getVtruename()+"");
                                        ToolsUtils.putString(context, Constant.HEADLOGO, loginBean.getData().get(0).getAvatarAddress()+"");
                                        ToolsUtils.putString(context,Constant.COUNT,loginBean.getData().get(0).getMoney()+"");
                                        ToolsUtils.putString(context,Constant.VCOMPANY,loginBean.getData().get(0).getVcompany()+"");
                                        ToolsUtils.putString(context,"money",loginBean.getData().get(0).getMoney()+"");
                                        //ToolsUtils.getInstance().toastShowStr(context,loginBean.getData().get(0).getAvatarAddress());
                                    }
                                    //  toMain();
                                }
                            });
                }
            }).start();

        }
    }

    boolean flag = true;
    private class MyThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                System.out.println("发送请求");
                try {
                    // 每个10秒向服务器发送一次请求
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //下面写请求服务器的代码
                //getInfo(GetUserInfoService.this);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.flag = false;
    }
     @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{
        public GetUserInfoService getService(){
            return GetUserInfoService.this;
        }

    }

}
