package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/29.
 */

public class MyMessageActivity extends BaseActivity {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindView(R.id.my_message_empty)
    public EmptyLayout emptyLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("我的消息");
        emptyLayout.setErrorMessage("亲,目前没有消息数据哦");
    }
}

