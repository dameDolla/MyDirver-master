package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.LoginBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/10/24.
 */

public class NoNetWorkActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout main;
    private ImageView iv;
    private TextView again;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_network);
        main = (RelativeLayout) findViewById(R.id.no_network_main);
        iv = (ImageView) findViewById(R.id.no_network_iv);
        again = (TextView) findViewById(R.id.no_network_tv2);
        main.setOnClickListener(this);
        iv.setOnClickListener(this);
        again.setOnClickListener(this);
        if (!ToolsUtils.getString(NoNetWorkActivity.this, Constant.LOGIN_GUID, "").equals("")) {
            GetUserInfoUtils.checkLogin(getApplicationContext());

        }
    }



    private void check()
    {
        if (ToolsUtils.getInstance().isNetworkAvailable(NoNetWorkActivity.this)){
            Intent intent = new Intent(NoNetWorkActivity.this,MainActivity.class);
            intent.putExtra("flag", "splash");
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.no_network_main:
                check();
                break;
            case R.id.no_network_iv:
                check();
                break;
            case R.id.no_network_tv2:
                check();
                break;
        }
    }
}
