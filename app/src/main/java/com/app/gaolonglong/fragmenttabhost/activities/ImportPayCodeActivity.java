package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.PasswordInputView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Intent intent = new Intent(ImportPayCodeActivity.this,RetypePayCodeActivity.class);
        intent.putExtra("password",password.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        submits();
    }
}
