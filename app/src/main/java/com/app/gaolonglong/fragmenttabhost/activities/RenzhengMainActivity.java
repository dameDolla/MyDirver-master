package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.BaseActivity;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/8/22.
 */

public class RenzhengMainActivity extends BaseActivity implements View.OnClickListener{

    @BindViews({R.id.rl_personal,R.id.rl_cargroup,R.id.rl_tiaodu})
    public List<RelativeLayout> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renzheng_main);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mList.get(0).setOnClickListener(this);
        mList.get(1).setOnClickListener(this);
        mList.get(2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_personal:
                startActivity(new Intent(RenzhengMainActivity.this,PersonalRenzhengActivity.class));
                break;
            case R.id.rl_cargroup:
                startActivity(new Intent(RenzhengMainActivity.this,CarGroupRenzhengActivity.class));
                break;
            case R.id.rl_tiaodu:
                startActivity(new Intent(RenzhengMainActivity.this,DiaoDuRenzhengActivity.class));
                break;
        }
    }
}

