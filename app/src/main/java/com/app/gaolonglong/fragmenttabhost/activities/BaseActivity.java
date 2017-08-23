package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.gaolonglong.fragmenttabhost.DiverApplication;

/**
 * Created by yanqi on 2017/8/21.
 */

public class BaseActivity extends Activity {
    private DiverApplication mApplication;

    private BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mApplication == null)
        {
            mApplication = (DiverApplication) getApplication();

        }
        mContext = this;
        addActivitu();
    }

    public  void addActivitu()
    {
        mApplication.addActivity(mContext);
    }

    public void removeActivity()
    {
        mApplication.removeActivity(mContext);
    }
    public void removeAllActivity()
    {
        mApplication.removeAllActivity();
    }
}
