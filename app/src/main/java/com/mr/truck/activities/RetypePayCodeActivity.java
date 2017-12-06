package com.mr.truck.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mr.truck.R;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.PasswordInputView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/18.
 */

public class RetypePayCodeActivity extends BaseActivity {

    @BindView(R.id.retype_passwordInputView)
    public PasswordInputView password;

    @OnClick(R.id.retype_import_paycode_submit)
    public void submit()
    {
        submits();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retype_input_paycode);
        ButterKnife.bind(this);
    }



    private void submits()
    {
        String retype_password = password.getText().toString();
        if(retype_password.equals(getIntent().getStringExtra("password")))
        {
            Intent intent  = new Intent(RetypePayCodeActivity.this,PayCodeYZMActivity.class);
            intent.putExtra("psw",password.getText().toString());
            startActivity(intent);
           // setPayCode("");
        }else{
            ToolsUtils.getInstance().toastShowStr(RetypePayCodeActivity.this,"两次密码不一致");
        }
    }

}
