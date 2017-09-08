package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;

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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

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
