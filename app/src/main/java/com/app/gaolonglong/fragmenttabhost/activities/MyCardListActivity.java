package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.BankInfo;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/30.
 */

public class MyCardListActivity extends BaseActivity {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindView(R.id.title_right_icon)
    public ImageView icon;

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

    @BindViews({R.id.my_card_listname,R.id.my_card_listnum})
    public List<TextView> carlist;

    @BindView(R.id.my_card_bg)
    public RelativeLayout bg;

    @BindView(R.id.my_card_listempty)
    public EmptyLayout empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_card_list);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        icon.setVisibility(View.VISIBLE);
        icon.setOnClickListener(onClickListener);
        mText.get(0).setText("我的银行卡");
        String num = GetUserInfoUtils.getBankCardNum(MyCardListActivity.this);
        String type = GetUserInfoUtils.getBankCardType(MyCardListActivity.this);
        if (TextUtils.isEmpty(num)){
            bg.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            empty.setErrorImag(R.drawable.nokongcheng,"您还没有添加银行卡");
        }else {
            empty.setVisibility(View.GONE);
            bg.setVisibility(View.VISIBLE);
            carlist.get(0).setText(type);
            carlist.get(1).setText(num);
        }
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

           startActivity(new Intent(MyCardListActivity.this,CheckBankCardActivity.class));
            finish();
        }
    };
}
