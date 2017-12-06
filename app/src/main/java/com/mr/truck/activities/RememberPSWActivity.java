package com.mr.truck.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mr.truck.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/20.
 */

public class RememberPSWActivity extends BaseActivity {
    @BindView(R.id.top_title)
    public TextView title;

    @OnClick(R.id.remember_forget)
    public void forget()
    {

    }
    @OnClick(R.id.remember_jide)
    public void jide()
    {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_paycode);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        title.setText("设置支付密码");
    }
}
