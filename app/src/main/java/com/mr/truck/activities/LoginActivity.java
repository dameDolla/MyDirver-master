package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mr.truck.DiverApplication;
import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.LoginBean;
import com.mr.truck.config.Constant;
import com.mr.truck.model.LoginModel;
import com.mr.truck.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    private DiverApplication mApplication;

    @BindView(R.id.bt_getCode)
    public TextView bt_getCode;

    @BindView(R.id.login_invitecode_ll)
    public LinearLayout invitecode_ll;

    @BindView(R.id.login_invite_code)
    public EditText invitecode;

    @BindViews({R.id.login_phone, R.id.login_code})
    public List<EditText> mEditText;
    private String result;
    @BindView(R.id.login_versionname)
    public TextView versionname;

    @OnClick(R.id.login_now)
    public void login() {

        String c = mEditText.get(1).getText().toString().trim();
        num = mEditText.get(0).getText().toString().trim();
        String invite = TextUtils.isEmpty(invitecode.getText().toString())?"":invitecode.getText().toString();
        //ToolsUtils.getInstance().toastShowStr(LoginActivity.this,invite);

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
            mJson.put("StreetAgentCode", invite);
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
                String errorcode = login.getErrorCode();
                //ToolsUtils.getInstance().toastShowStr(LoginActivity.this, login.getErrorMsg());
                if (login.getData() != null) {
                    //跳转页面并自动登录
                    List<LoginBean.DataBean> data = login.getData();
                    String guid = data.get(0).getGUID();
                    //ToolsUtils.getInstance().toastShowStr(LoginActivity.this, login.getErrorMsg());
                    try {
                        ToolsUtils.putString(LoginActivity.this, Constant.LOGIN_GUID, guid);
                        ToolsUtils.putString(LoginActivity.this, Constant.USERNAME, data.get(0).getUsername());
                        ToolsUtils.putString(LoginActivity.this, Constant.USRE_TYPE, data.get(0).getUsertype());
                        ToolsUtils.putString(LoginActivity.this, Constant.KEY, data.get(0).getSecreKey());
                        ToolsUtils.putString(LoginActivity.this, Constant.VTRUENAME, data.get(0).getVtruename());
                        ToolsUtils.putString(LoginActivity.this, Constant.MOBILE, data.get(0).getMobile());
                        ToolsUtils.putString(LoginActivity.this, Constant.HEADLOGO, data.get(0).getAvatarAddress());
                        ToolsUtils.putString(LoginActivity.this,Constant.COUNT,data.get(0).getMoney()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.COMPANYGUID,data.get(0).getCompanyGUID()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.USERNAME,data.get(0).getTruename()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.DRIVERBILL,data.get(0).getCargocount()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.BANKCARDNUM,data.get(0).getAccount()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.BANKCARDUSERNAME,data.get(0).getBankUserName()+"");
                        ToolsUtils.putString(LoginActivity.this,Constant.BANKCARDTYPE,data.get(0).getBanktype()+"");
                        ToolsUtils.putString(LoginActivity.this,"idcard",data.get(0).getIdcard()+"");
                        ToolsUtils.putString(LoginActivity.this,"money",data.get(0).getMoney()+"");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("flag","splash");
                    startActivity(intent);
                    finish();
                } else {
                    if (errorcode.equals("220")){
                        ToolsUtils.getInstance().toastShowStr(LoginActivity.this,login.getErrorMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
               // ToolsUtils.getInstance().toastShowStr(LoginActivity.this, t.getMessage());
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
                    String errorCode = code.getErrorCode();
                    if (errorCode.equals("1")){
                        String s = msg.substring(msg.length()-1);
                        if (s.equals("0")){
                            invitecode_ll.setVisibility(View.VISIBLE);
                            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, msg.substring(0,msg.length()-1));
                        }else {
                            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, msg);
                        }

                    }else {
                        ToolsUtils.getInstance().toastShowStr(LoginActivity.this, code.getErrorMsg());
                    }
                    Log.e("getCodeInfo",code.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Call<GetCodeBean> call, Throwable t) {
                //ToolsUtils.getInstance().toastShowStr(LoginActivity.this, t.getMessage());
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
        initExit();
    }

    private void initView() {
        versionname.setText("司机版 "+ToolsUtils.getVersionName(LoginActivity.this));
        mEditText.get(0).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11){
                    String tel = mEditText.get(0).getText().toString();
                    if (!ToolsUtils.isChinaPhoneLegal(tel)){
                        ToolsUtils.getInstance().toastShowStr(LoginActivity.this,"请输入正确格式的手机号");
                        mEditText.get(0).selectAll();
                    }
                }
            }
        });
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

    /**
     * 双击退出
     */
    private long time = 0;

    private void exits() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            ToolsUtils.getInstance().toastShowStr(LoginActivity.this, Constant.EXIT_STR);
        } else {
            mApplication.removeAllActivity();
        }
    }
    private void initExit() {

        if (mApplication == null) {
            mApplication = (DiverApplication) getApplication();
        }
        mApplication.addActivity(LoginActivity.this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) exits();
        return true;
    }
}
