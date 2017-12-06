package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mr.truck.R;

import butterknife.BindView;
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
    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back_txt)
    public void backs()
    {
        finish();
    }
    @BindView(R.id.top_title)
    public TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_paycode);
        ButterKnife.bind(this);
        title.setText("设置支付密码");
    }


}
