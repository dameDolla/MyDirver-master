package com.mr.truck.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocationListener;
import com.mr.truck.DiverApplication;
import com.mr.truck.R;

/**
 * Created by yanqi on 2017/8/21.
 */

public class BaseActivity extends AppCompatActivity {
    private DiverApplication mApplication;

    private BaseActivity mContext;
    private AMapLocationListener mAMapLocationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        if (mApplication == null) {
            mApplication = (DiverApplication) getApplication();

        }
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        //rootView.setPadding(0, getStatusBarHeight(), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.shen_blue));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.shen_blue));
            decorView.addView(statusBarView, lp);
        }
        mContext = this;
        addActivitu();
       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);*/
        //initLocation();
        //ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
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
    public void addActivitu() {
        mApplication.addActivity(mContext);
    }

    public void removeActivity() {
        mApplication.removeActivity(mContext);
    }

    public void removeAllActivity() {
        mApplication.removeAllActivity();
    }


}
