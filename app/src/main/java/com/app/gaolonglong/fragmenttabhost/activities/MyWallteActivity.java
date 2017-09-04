package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/29.
 */

public class MyWallteActivity extends BaseActivity implements View.OnClickListener {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindViews({R.id.wallte_to_coupon})
    public List<LinearLayout> mLinear;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallte);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("钱包");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.wallte_to_coupon:
                startActivity(new Intent(MyWallteActivity.this,YouHuiQuanActivity.class));
                break;
        }
    }
}
