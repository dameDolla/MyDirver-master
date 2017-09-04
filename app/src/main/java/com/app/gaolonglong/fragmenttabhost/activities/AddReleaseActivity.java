package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/8/7.
 */

public class AddReleaseActivity extends BaseActivity implements View.OnClickListener{

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindViews({R.id.title_back})
    public List<ImageView> mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_return);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("发布空程");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
