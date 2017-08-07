package com.app.gaolonglong.fragmenttabhost;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by yanqi on 2017/8/1.
 */

public class DiverApplication extends Application {

    public static DiverApplication mDiver;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mDiver == null) {
            mDiver = this;
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
