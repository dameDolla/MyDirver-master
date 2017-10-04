package com.app.gaolonglong.fragmenttabhost;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.xdd.pay.xddPay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by yanqi on 2017/8/1.
 */

public class DiverApplication extends Application {

    public static DiverApplication mDiver;
    private List<Activity> mList;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mDiver == null) {
            mDiver = this;
        }
        mList = new ArrayList<Activity>();
        Fresco.initialize(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Set<String> set = new HashSet<>();
        set.add(GetUserInfoUtils.getGuid(this));//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签

    }

    /**
     * 添加Activity
     * @return
     */
    public void addActivity(Activity activity)
    {
        if(!mList.contains(activity))
        {
            mList.add(activity);
        }
    }

    /**
     * 移除单个activity
     * @return
     */
    public void removeActivity(Activity activity)
    {
        if(mList.contains(activity))
        {
            mList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除所有的activity
     * @return
     */
    public void removeAllActivity()
    {
        for(Activity activity : mList)
        {
            activity.finish();
        }
    }

    public static Context getContext() {
        return mDiver.getApplicationContext();
    }

    /**
     * 获取应用版本号
     * 先获取Pakageinfo
     * 默认返回1
     */
    public static int getAppVersionCode() {
        Context context = getContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  1;
    }

}
