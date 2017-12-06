package com.mr.truck.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.utils.GetUserInfoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/22.
 */

public class RenzhengMainActivity extends BaseActivity implements View.OnClickListener{

    @BindViews({R.id.rl_person,R.id.rl_cargroup,R.id.rl_tiaodu})
    public List<LinearLayout> mList;

    @BindViews({R.id.renzheng_main_person,R.id.renzheng_main_cargroup,R.id.renzheng_main_diaodu})
    public List<TextView> mText;

    @BindView(R.id.top_title)
    public TextView title;

    @OnClick(R.id.title_back_txt)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back)
    public void backs()
    {
        finish();
    }

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
        String usertype = GetUserInfoUtils.getUserType(RenzhengMainActivity.this);
        boolean isRenzheng = GetUserInfoUtils.isRenzheng(RenzhengMainActivity.this);
        title.setText("实名认证");
        if (isRenzheng){
            if (usertype.equals("2")){//个体司机
                mList.get(1).setEnabled(false);
                mList.get(2).setEnabled(false);
                mText.get(1).setTextColor(Color.GRAY);
                mText.get(2).setTextColor(Color.GRAY);
            }else if (usertype.equals("3")){
                mList.get(0).setEnabled(false);
                mList.get(2).setEnabled(false);
                mText.get(0).setTextColor(Color.GRAY);
                mText.get(2).setTextColor(Color.GRAY);
            }else if (usertype.equals("4")){
                mList.get(1).setEnabled(false);
                mList.get(0).setEnabled(false);
                mText.get(1).setTextColor(Color.GRAY);
                mText.get(0).setTextColor(Color.GRAY);
            }
        }

        mList.get(0).setOnClickListener(this);
        mList.get(1).setOnClickListener(this);
        mList.get(2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_person:
                startActivity(new Intent(RenzhengMainActivity.this,PersonalRenzhengActivity.class));
                finish();
                break;
            case R.id.rl_cargroup:
                startActivity(new Intent(RenzhengMainActivity.this,CarGroupRenzhengActivity.class));
                finish();
                break;
            case R.id.rl_tiaodu:
                startActivity(new Intent(RenzhengMainActivity.this,DiaoDuRenzhengActivity.class));
                finish();
                break;
        }
    }
}

