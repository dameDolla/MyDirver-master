package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/28.
 */

public class MissionDetailActivity extends BaseActivity  implements View.OnClickListener{
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
    @BindView(R.id.top_title)
    public TextView title;
    @BindViews({R.id.mission_detail_status,R.id.mission_detail_time,R.id.missison_detail_name,
                R.id.mission_detail_addr,R.id.mission_detail_bzj,R.id.mission_detail_yun,
                R.id.mission_detail_zhuang,R.id.mission_detail_xie,R.id.mission_detail_missionnum,
                R.id.mission_detail_donetime})
    public List<TextView> mText;
    @BindViews({R.id.mission_detail_changestatus,R.id.mission_detail_copy})
    public List<Button> mButton;
    @BindView(R.id.mission_detail_icon)
    public SimpleDraweeView icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misssion_status);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("运单详情");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.mission_detail_copy:
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText(mText.get(8).getText().toString());
                ToolsUtils.getInstance().toastShowStr(MissionDetailActivity.this,"复制成功");
                break;
        }
    }
}
