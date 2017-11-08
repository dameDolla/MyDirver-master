package com.app.gaolonglong.fragmenttabhost;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;

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
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLYAPPID,true);
        Set<String> set = new HashSet<>();
        Log.e("application",GetUserInfoUtils.getGuid(this));
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

}
