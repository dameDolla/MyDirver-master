package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/18.
 */

public class SetPayCodeMainActivity extends BaseActivity {
    @OnClick(R.id.main_paycode_add)
    public void add()
    {
        startActivity(new Intent(SetPayCodeMainActivity.this,ImportPayCodeActivity.class));
    }
    @OnClick(R.id.main_paycode_reset)
    public void reset()
    {
        startActivity(new Intent(SetPayCodeMainActivity.this,ResetPSWOneActivity.class));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_paycode);
        ButterKnife.bind(this);
    }


}
