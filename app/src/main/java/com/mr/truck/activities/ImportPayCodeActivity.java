package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.PasswordInputView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/9/18.
 */

public class ImportPayCodeActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.import_paycode_submit)
    public Button submit;

   @BindView(R.id.passwordInputView)
   public PasswordInputView password;
    @BindView(R.id.top_title)
    public TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_import_password);
        ButterKnife.bind(this);
        initView();
    }



    private void initView()
    {
        title.setText("设置支付密码");
        submit.setText("下一步");
        submit.setOnClickListener(this);
    }
    private void submits()
    {
        String psw = password.getText().toString();
        if (!TextUtils.isEmpty(psw)){
            Intent intent = new Intent(ImportPayCodeActivity.this,RetypePayCodeActivity.class);
            intent.putExtra("password",psw);
            startActivity(intent);
        }else {
            ToolsUtils.getInstance().toastShowStr(ImportPayCodeActivity.this,"请输入密码");
        }

    }

    @Override
    public void onClick(View view) {
        submits();
    }
}
