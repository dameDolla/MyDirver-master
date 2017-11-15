package com.app.gaolonglong.fragmenttabhost.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.app.gaolonglong.fragmenttabhost.activities.SplashActivity;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/10/30.
 */

public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        new MyThread().start();
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread().start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //this.mLocationClient.stopLocation();
        this.flag = false;

    }
    private boolean flag = true;
    private class MyThread extends Thread{
        @Override
        public void run() {
            while (flag){
                try {
                    Thread.sleep(10000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                initLocation();
            }
        }
    }
    /**
     * 定位
     */
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mLocationClient;

    private void initLocation() {
        //Log.e("locaitionservice","66666");
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setInterval(10000);
        //给定位客户端对象设置定位参数mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);


        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                Log.e("location-service",amapLocation.getAddress());
                String location = amapLocation.getLatitude()+","+amapLocation.getLongitude();
                ToolsUtils.putString(LocationService.this, Constant.CITY, amapLocation.getCity());
                ToolsUtils.putString(LocationService.this, Constant.ADDRESS, amapLocation.getAddress());
                uploadLat(initJsonData(location));
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
                        .uploadLatLng(Constant.TRUCK_PAGENAME, Config.UPLOADLATLNG,json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GetCodeBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                               // Log.e("locationservice",e.getMessage());
                            }

                            @Override
                            public void onNext(GetCodeBean getCodeBean) {
                                Log.e("location-service",getCodeBean.getErrorMsg());
                            }
                        });
            }
        });

    }



    private String initJsonData(String latLng)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(LocationService.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(LocationService.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(LocationService.this));
        map.put("NewLoad",latLng);
        return JsonUtils.getInstance().getJsonStr(map);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        //mLocationClient.onDestroy();
        return super.onUnbind(intent);
    }
    public class MyBinder extends Binder{
        public LocationService getLocationService(){
            return LocationService.this;
        }


    }
}
