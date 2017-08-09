package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

/**
 * Created by yanqi on 2017/8/9.
 */

public class SettingActivity extends Activity {

    private ImageView back;
    private TextView title;
    private RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_content);
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
