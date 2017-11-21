package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/26.
 */

public class ResetPSWForgetTwoActivity extends BaseActivity {
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
    @BindView(R.id.reset_forget_shenfenzheng)
    public EditText sfz;
    @OnClick(R.id.reset_forget_next)
    public void next()
    {
        Intent intent = new Intent(ResetPSWForgetTwoActivity.this,ResetPSWForgetOneActivity.class);
        intent.putExtra("YZM",getIntent().getStringExtra("YZM"));
        intent.putExtra("SFZ",sfz.getText().toString());
        startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_paycode_shenfenzheng);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("重置密码");
    }
}
