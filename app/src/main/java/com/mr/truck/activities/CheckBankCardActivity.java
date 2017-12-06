package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.BankInfo;
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
 * Created by yanqi on 2017/11/2.
 */

public class CheckBankCardActivity extends BaseActivity implements View.OnClickListener{

    private TextView tel;
    private TextView getCode;
    private String guid;
    private String mobile;
    private String key;
    private TextView bankType;
    private EditText name;
    private EditText num;
    private EditText username,smsCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_bank_card);
        init();
    }



    private void init()
    {
        guid = GetUserInfoUtils.getGuid(this);
        mobile = GetUserInfoUtils.getMobile(this);
        key = GetUserInfoUtils.getKey(this);
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView title_back = (TextView) findViewById(R.id.title_back_txt);
        ImageView title_backs = (ImageView) findViewById(R.id.title_back);
        username = (EditText) findViewById(R.id.check_bank_username);
        bankType = (TextView) findViewById(R.id.check_bankcard_type);
        tel = (TextView) findViewById(R.id.check_bank_tel);
        tel.setText(GetUserInfoUtils.getMobile(this));
        name = (EditText) findViewById(R.id.check_bank_name);
        num = (EditText) findViewById(R.id.check_bank_num);
        smsCode = (EditText) findViewById(R.id.check_bank_code);
        Button  submit = (Button) findViewById(R.id.check_bank_submit);
        getCode = (TextView) findViewById(R.id.check_bank_getcode);
        title.setText("验证银行卡");
        getCode.setOnClickListener(this);
        submit.setOnClickListener(this);
        title_back.setOnClickListener(this);
        title_backs.setOnClickListener(this);

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int size = editable.length();
                if (size == 6){
                    String cardNum = num.getText().toString();
                    bankType.setText(((BankInfo.getNameOfBank(CheckBankCardActivity.this,Long.parseLong(cardNum))).split("-"))[0]);
                }
            }
        });
    }
    private void getCode()
    {
        String mobile = tel.getText().toString();
        if (!TextUtils.isEmpty(mobile)){
            //请求获取验证码的接口
            getBindCode();
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.check_bank_getcode:
                getCode();
                MyCountDownTimer timer = new MyCountDownTimer(60000,1000);
                timer.start();
                break;
            case R.id.check_bank_submit:
                addCard();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }

    private class MyCountDownTimer extends CountDownTimer{
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            getCode.setEnabled(false);
            getCode.setText(l/1000+"s");
        }

        @Override
        public void onFinish() {
            getCode.setEnabled(true);
            getCode.setText("重新获取验证码");
        }
    }

    /**
     * 添加银行卡
     */
    private void addCard()
    {
        final String usernames = username.getText().toString();
        final String carNum = num.getText().toString();
        String code = smsCode.getText().toString();
        if (TextUtils.isEmpty(usernames)||TextUtils.isEmpty(carNum)||TextUtils.isEmpty(code)){
            ToolsUtils.getInstance().toastShowStr(CheckBankCardActivity.this,"请输入完整信息");
        }else {
            Map<String,String> map = new HashMap<>();
            map.put("GUID",guid);
            map.put(Constant.MOBILE,mobile);
            map.put(Constant.KEY,key);
            map.put("BankMobile",mobile);
            map.put("account",carNum);
            map.put("BankUserName",usernames);
            map.put("banktype",bankType.getText().toString());
            map.put("branch",name.getText().toString());
            map.put("BankSMSCode",code);

            RetrofitUtils.getRetrofitService()
                    .addBankCard(Constant.MYINFO_PAGENAME, Config.ADDBANKCARD, JsonUtils.getInstance().getJsonStr(map))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GetCodeBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetCodeBean getCodeBean) {
                            if (getCodeBean.getErrorCode().equals("200")){
                                ToolsUtils.putString(CheckBankCardActivity.this,Constant.BANKCARDTYPE,bankType.getText().toString());
                                ToolsUtils.putString(CheckBankCardActivity.this,Constant.BANKCARDNUM,carNum);
                                ToolsUtils.putString(CheckBankCardActivity.this,Constant.BANKCARDUSERNAME,usernames);
                                startActivity(new Intent(CheckBankCardActivity.this,MyCardListActivity.class));
                                finish();
                            }else {
                                ToolsUtils.getInstance().toastShowStr(CheckBankCardActivity.this,getCodeBean.getErrorMsg());
                            }
                        }
                    });
        }

    }
    private void getBindCode()
    {
        Map<String,String> codeMap = new HashMap<>();
        codeMap.put("BankMobile",mobile);
        codeMap.put("GUID",guid);
        codeMap.put(Constant.MOBILE,mobile);
        codeMap.put(Constant.KEY,key);
        RetrofitUtils.getRetrofitService()
                .getBindCardSMSCode(Constant.MYINFO_PAGENAME,Config.GETBINDCARDCODE,JsonUtils.getInstance().getJsonStr(codeMap))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        ToolsUtils.getInstance().toastShowStr(CheckBankCardActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }
}
