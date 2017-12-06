package com.mr.truck.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.LoginBean;
import com.mr.truck.bean.ParametersBean;
import com.mr.truck.bean.VersionCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.utils.ToolsUtils;

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
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        //rootView.setPadding(0, getStatusBarHeight(), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.splashbg_color));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.splashbg_color));
            decorView.addView(statusBarView, lp);
        }
        getVersionConde();
        ToolsUtils.getInstance().requestPermission(SplashActivity.this);

        toMain();
        guid = GetUserInfoUtils.getGuid(SplashActivity.this);
        mobile = GetUserInfoUtils.getMobile(SplashActivity.this);
        key = GetUserInfoUtils.getKey(SplashActivity.this);
        if (!ToolsUtils.getString(SplashActivity.this, Constant.LOGIN_GUID, "").equals("")) {
           GetUserInfoUtils.checkLogin(getApplicationContext());

        }
        ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.splashbg_color));
        initLocation();
    }
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
