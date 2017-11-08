package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.bean.ParametersBean;
import com.app.gaolonglong.fragmenttabhost.bean.VersionCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import java.util.HashMap;
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
    private Map<String, String> map;
    private Intent intent;
    private String downloadurls = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);

        getVersionConde();
        ToolsUtils.getInstance().requestPermission(SplashActivity.this);

        toMain();
        guid = GetUserInfoUtils.getGuid(SplashActivity.this);
        mobile = GetUserInfoUtils.getMobile(SplashActivity.this);
        key = GetUserInfoUtils.getKey(SplashActivity.this);
        if (!ToolsUtils.getString(SplashActivity.this, Constant.LOGIN_GUID, "").equals("")) {
            checkLogin();

        }

        initLocation();
    }

    private void checkLogin() {

        map = new HashMap<String, String>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
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
                               //Log.e("splasherror",e.getMessage());
                                //ToolsUtils.getInstance().loginOut(SplashActivity.this);
                            }

                            @Override
                            public void onNext(LoginBean loginBean) {
                               // Log.e("splashinfo",loginBean.getErrorMsg()+"---"+loginBean.getErrorCode());
                               // ToolsUtils.getInstance().toastShowStr(SplashActivity.this,loginBean.getErrorCode());
                                if (loginBean.getErrorCode().equals("202")||loginBean.getErrorCode().equals("220")||loginBean.getErrorCode().equals("203")) {
                                    ToolsUtils.getInstance().loginOut(SplashActivity.this);
                                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));

                                } else if (loginBean.getErrorCode().equals("200")) {
                                    ToolsUtils.putString(SplashActivity.this,Constant.LOGIN_GUID,loginBean.getData().get(0).getGUID()+"");
                                    ToolsUtils.putString(SplashActivity.this, Constant.KEY, loginBean.getData().get(0).getSecreKey()+"");
                                    ToolsUtils.putString(SplashActivity.this, Constant.USRE_TYPE, loginBean.getData().get(0).getUsertype()+"");
                                    ToolsUtils.putString(SplashActivity.this, Constant.VTRUENAME, loginBean.getData().get(0).getVtruename()+"");
                                    ToolsUtils.putString(SplashActivity.this, Constant.HEADLOGO, loginBean.getData().get(0).getAvatarAddress()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.COUNT,loginBean.getData().get(0).getMoney()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.VCOMPANY,loginBean.getData().get(0).getVcompany()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.USERNAME,loginBean.getData().get(0).getTruename()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.COMPANYGUID,loginBean.getData().get(0).getCompanyGUID()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.DRIVERBILL,loginBean.getData().get(0).getCargocount()+"");
                                    ToolsUtils.putString(SplashActivity.this,Constant.FAPIAOTYPE,loginBean.getData().get(0).getMInvoiceType()+"");
                                    ToolsUtils.putString(SplashActivity.this,"money",loginBean.getData().get(0).getMoney()+"");
                                    ToolsUtils.putString(SplashActivity.this,"idcard",loginBean.getData().get(0).getIdcard()+"");

                                    //ToolsUtils.getInstance().toastShowStr(SplashActivity.this,loginBean.getData().get(0).getAvatarAddress());
                                }
                                //  toMain();
                            }
                        });
            }
        }).start();

    }
    /**
     * 获取参数数据
     */
    private void getParamtersData()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getParameters(Constant.PARAMETER_PAGENAME,Config.GET_PARAMETERS,"")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ParametersBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ParametersBean parametersBean) {
                                if (parametersBean.getErrorCode().equals("200"))
                                {
                                    ToolsUtils.getInstance().saveDataToFile(SplashActivity.this,parametersBean.getData().toString(),"paramter");
                                }
                            }
                        });
            }
        }).start();
    }
    /**
     * 3秒后跳转
     */
    private void toMain() {
        boolean isFirst = ToolsUtils.getBoolean(SplashActivity.this,Constant.iSFIRST,true);
        if (ToolsUtils.getInstance().isNetworkAvailable(SplashActivity.this)){
            /*if (isFirst){
                intent = new Intent(SplashActivity.this, GuidActivity.class);
            }else {*/
                if (ToolsUtils.getInstance().isLogin(SplashActivity.this))
                {
                    intent = new Intent(SplashActivity.this,MainActivity.class);

                }else {
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                }

            /*}*/
        }else {
            intent = new Intent(SplashActivity.this,NoNetWorkActivity.class);

        }

        /*if (!ToolsUtils.getInstance().isNetworkAvailable(SplashActivity.this)){

        }*/
        //intent.putExtra("downloadUrl",downloadurls);
        intent.putExtra("flag", "splash");
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 1000 * 3);
    }

    /**
     * 定位
     */
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mLocationClient;

    private void initLocation() {

        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
       // mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);


        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                Log.e("location",amapLocation.getAddress());
                String location = amapLocation.getLatitude()+","+amapLocation.getLongitude();
                uploadLat(initJsonData(location));
                ToolsUtils.putString(SplashActivity.this, Constant.CITY, amapLocation.getCity());
                ToolsUtils.putString(SplashActivity.this, Constant.ADDRESS, amapLocation.getAddress());
                //ToolsUtils.getInstance().toastShowStr(SplashActivity.this,amapLocation.getErrorInfo());
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 上传位置信息
     * @param json
     */
    private void uploadLat(final String json)
    {
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .uploadLatLng(Constant.TRUCK_PAGENAME,Config.UPLOADLATLNG,json)
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
                                Log.e("location",getCodeBean.getErrorMsg());
                            }
                        });
            }
        });

    }
    private String initJsonData(String latLng)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(SplashActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(SplashActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(SplashActivity.this));
        map.put("NewLoad",latLng);
        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void  getVersionConde(){
        String versionCode = null;
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .checkUpdate(Constant.PARAMETER_PAGENAME,Config.CHECKVERSION)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<VersionCodeBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(VersionCodeBean versionCodeBean) {
                                checkUpdate(versionCodeBean);


                            }
                        });
            }
        });

    }
    private void checkUpdate(VersionCodeBean versionCodeBean)
    {
        String versionCode = versionCodeBean.getData().get(0).getVersionNumber();
        String downloadUrl = versionCodeBean.getData().get(0).getAppAddress();
        if ((ToolsUtils.getApkVersionCode(SplashActivity.this)+"").equals(versionCode))
        {
            Log.e("versioncode","版本号相同");
            ToolsUtils.putString(SplashActivity.this,Constant.DOWNLOADURL,"");
        }else{
           // new CommomDialog(SplashActivity.this,"有版本更新").setTitle("检测版本更新").show();
            ToolsUtils.putString(SplashActivity.this,Constant.DOWNLOADURL,downloadUrl);
            //downloadurls = downloadUrl;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}
