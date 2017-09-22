package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

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

public class ImportPayCodeActivity extends BaseActivity {

    @OnClick(R.id.import_paycode_submit)
    public void submit()
    {
        submits();
    }

   @BindView(R.id.passwordInputView)
   public PasswordInputView password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_import_password);
        ButterKnife.bind(this);
    }
    private void submits()
    {
        Intent intent = new Intent(ImportPayCodeActivity.this,RetypePayCodeActivity.class);
        intent.putExtra("password",password.getText().toString());
        startActivity(intent);
    }
}
