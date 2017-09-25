package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.model.LoginModel;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yanqi on 2017/8/16.
 */

public class LoginActivity extends BaseActivity {

    private String num;

    @BindView(R.id.bt_getCode)
    public TextView bt_getCode;

    @BindViews({R.id.login_phone, R.id.login_code})
    public List<EditText> mEditText;
    private String result;


    @OnClick(R.id.login_now)
    public void login() {

        String c = mEditText.get(1).getText().toString().trim();
        num = mEditText.get(0).getText().toString().trim();
        if (TextUtils.isEmpty(c)) {
            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, "请填入验证码");
            return;
        }
        if (TextUtils.isEmpty(num)) {
            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, "请填入手机号码");
            return;
        }
        JSONObject mJson = new JSONObject();
        try {
            mJson.put(Constant.MOBILE, num);
            mJson.put("SMSCode", c);
            mJson.put(Constant.KEY, c);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String md5 = ToolsUtils.getInstance().getMD5Val(mJson.toString());
        Call<LoginBean> call = LoginModel.getInstance().getLoginInfo(mJson.toString(), "Login");

        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean login = response.body();
                ToolsUtils.getInstance().toastShowStr(LoginActivity.this, login.getErrorMsg());
                if (login.getData() != null) {
                    //跳转页面并自动登录
                    List<LoginBean.DataBean> data = login.getData();
                    String guid = data.get(0).getGUID();
                    try {
                        ToolsUtils.putString(LoginActivity.this, Constant.LOGIN_GUID, guid);
                        ToolsUtils.putString(LoginActivity.this, Constant.USERNAME, data.get(0).getUsername());
                        ToolsUtils.putString(LoginActivity.this, Constant.USRE_TYPE, data.get(0).getUsertype());
                        ToolsUtils.putString(LoginActivity.this, Constant.KEY, data.get(0).getSecreKey());
                        ToolsUtils.putString(LoginActivity.this, Constant.VTRUENAME, data.get(0).getVtruename());
                        ToolsUtils.putString(LoginActivity.this, Constant.MOBILE, data.get(0).getMobile());
                        ToolsUtils.putString(LoginActivity.this, Constant.HEADLOGO, data.get(0).getAvatarAddress());
                        ToolsUtils.putString(LoginActivity.this,Constant.COUNT,data.get(0).getMoney()+"");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("flag","splash");
                    startActivity(intent);
                } else {

                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                ToolsUtils.getInstance().toastShowStr(LoginActivity.this, t.getMessage());
            }


        });
    }

    @OnClick(R.id.bt_getCode)
    public void getCode() {
        num = mEditText.get(0).getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, "请填入手机号码");
            return;
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put(Constant.MOBILE, num);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String md5Str = ToolsUtils.getInstance().getMD5Val(obj.toString());
        //mEditText.get(1).setText(md5Str);
        Call<GetCodeBean> codeCall = LoginModel.getInstance().getCode(obj.toString(), "Login");
        codeCall.enqueue(new Callback<GetCodeBean>() {
            @Override
            public void onResponse(Call<GetCodeBean> call, Response<GetCodeBean> response) {
                if (response.isSuccessful()) {
                    GetCodeBean code = response.body();
                    String msg = code.getErrorMsg();
                    ToolsUtils.getInstance().toastShowStr(LoginActivity.this, code.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Call<GetCodeBean> call, Throwable t) {
                ToolsUtils.getInstance().toastShowStr(LoginActivity.this, t.getMessage());
            }


        });
        MyCounDownTimer timer = new MyCounDownTimer(60000, 1000);
        timer.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    private class MyCounDownTimer extends CountDownTimer {
        public MyCounDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            bt_getCode.setClickable(false);
            bt_getCode.setText(l / 1000 + "s");
            bt_getCode.setTextSize(18);
        }

        @Override
        public void onFinish() {
            //重新给Button设置文字
            bt_getCode.setText("重新获取验证码");
            bt_getCode.setTextSize(10);
            //设置可点击
            bt_getCode.setClickable(true);
        }
    }
}
