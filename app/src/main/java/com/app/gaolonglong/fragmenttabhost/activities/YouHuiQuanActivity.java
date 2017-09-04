package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/29.
 */

public class YouHuiQuanActivity extends BaseActivity {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_list);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("优惠券");
    }
}
