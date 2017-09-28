package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/26.
 */

public class ResetPSWOneActivity extends BaseActivity {
    @OnClick(R.id.resetpsw_one_forget)
    public void forgets()
    {
        forget();
    }
    @OnClick(R.id.resetpsw_one_remember)
    public void remembers()
    {
        remember();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpsw_one);
        ButterKnife.bind(this);
    }

    private void forget()
    {
        startActivity(new Intent(ResetPSWOneActivity.this,ResetPSWForgetTwoActivity.class));
    }
    private void remember()
    {
        startActivity(new Intent(ResetPSWOneActivity.this,ResetPSWRemOneActivity.class));
    }
}
