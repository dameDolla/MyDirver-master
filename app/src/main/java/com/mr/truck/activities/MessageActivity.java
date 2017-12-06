package com.mr.truck.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mr.truck.R;

/**
 * Created by yanqi on 2017/8/9.
 */

public class MessageActivity extends Activity{

    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title = (TextView) findViewById(R.id.top_title);
        title.setText("消息");
    }
}
