package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/9.
 */

public class SettingActivity extends Activity {

    private ImageView back;
    private TextView title;
    private RelativeLayout rl;

    @OnClick(R.id.exit_account)
    public void exit()
    {

        ToolsUtils.getInstance().loginOut(SettingActivity.this);
        startActivity(new Intent(SettingActivity.this,MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_content);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private  void initView()
    {
        back = (ImageView)findViewById(R.id.title_back);
        title = (TextView)findViewById(R.id.top_title);
        rl = (RelativeLayout)findViewById(R.id.title_rl);

        rl.setBackgroundColor(Color.WHITE);
        title.setText("设置");
        title.setTextColor(Color.parseColor("#000000"));
    }
}
