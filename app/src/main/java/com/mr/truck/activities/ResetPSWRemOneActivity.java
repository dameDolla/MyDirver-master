package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.view.PasswordInputView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/9/26.
 */

public class ResetPSWRemOneActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.reset_psw_txt1)
    public TextView txt;
    @BindView(R.id.top_title)
    public TextView title;
    @BindView(R.id.passwordInputView)
    public PasswordInputView inputView;
    @BindView(R.id.import_paycode_submit)
    public Button submit;
    @BindView(R.id.import_paycode_tel)
    public TextView tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_import_password);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        txt.setText("输入原支付密码");
        title.setText("重置支付密码");
        submit.setText("下一步");
        tel.setText("请为手机号码为 "+ GetUserInfoUtils.getMobile(ResetPSWRemOneActivity.this));
        submit.setOnClickListener(this);
    }
    private void submit()
    {
        //ToolsUtils.getInstance().toastShowStr(ResetPSWRemOneActivity.this,inputView.getText().toString());
        Intent intent = new Intent(ResetPSWRemOneActivity.this,ResetPSWRemTwoActivity.class);
        intent.putExtra("password",inputView.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        submit();
    }
}
