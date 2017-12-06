package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.utils.GetUserInfoUtils;

import butterknife.BindView;
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
    @BindView(R.id.resetpsw_one_mobile)
    public TextView mobile;
    @BindView(R.id.top_title)
    public TextView title;
    @OnClick(R.id.title_back_txt)
    public void backs(){
        finish();
    }
    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpsw_one);
        ButterKnife.bind(this);
        title.setText("重置密码");
        mobile.setText("您是否还记得手机"+ GetUserInfoUtils.getMobile(ResetPSWOneActivity.this));
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
