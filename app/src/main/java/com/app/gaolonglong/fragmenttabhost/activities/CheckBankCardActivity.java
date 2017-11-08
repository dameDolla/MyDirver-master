package com.app.gaolonglong.fragmenttabhost.activities;

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

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.BankInfo;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.nio.charset.CharacterCodingException;

/**
 * Created by yanqi on 2017/11/2.
 */

public class CheckBankCardActivity extends BaseActivity implements View.OnClickListener{

    private TextView tel;
    private TextView getCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_bank_card);
        init();
    }
    private void init()
    {
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView title_back = (TextView) findViewById(R.id.title_back_txt);
        ImageView title_backs = (ImageView) findViewById(R.id.title_back);
        TextView username = (TextView) findViewById(R.id.check_bank_username);
        tel = (TextView) findViewById(R.id.check_bank_tel);
        final TextView name = (TextView) findViewById(R.id.check_bank_name);
        final EditText num = (EditText) findViewById(R.id.check_bank_num);
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
                    name.setText(BankInfo.getNameOfBank(CheckBankCardActivity.this,Long.parseLong(cardNum)));
                }
            }
        });
    }
    private void getCode()
    {
        String mobile = tel.getText().toString();
        if (!TextUtils.isEmpty(mobile)){
            //请求获取验证码的接口
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
}
