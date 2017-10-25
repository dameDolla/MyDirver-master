package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by yanqi on 2017/10/19.
 */

public class CompanyInfoActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_info);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        TextView title = (TextView) findViewById(R.id.top_title);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        TextView backs = (TextView) findViewById(R.id.title_back_txt);
        TextView name = (TextView) findViewById(R.id.company_info_name);
        TextView status = (TextView) findViewById(R.id.company_info_status);
        TextView tel = (TextView) findViewById(R.id.company_info_tel);
        SimpleDraweeView logo = (SimpleDraweeView) findViewById(R.id.company_info_logo);
        SimpleDraweeView yyzz = (SimpleDraweeView) findViewById(R.id.company_info_yyzz);
        SimpleDraweeView dlysxk = (SimpleDraweeView) findViewById(R.id.company_info_ysxkz);
        SimpleDraweeView sqwt = (SimpleDraweeView) findViewById(R.id.company_info_sqwt);

        title.setText("公司信息");
        back.setOnClickListener(this);
        backs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
}
