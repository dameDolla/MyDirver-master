package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/13.
 */

public class FindDetailActivity extends BaseActivity {

    @BindView(R.id.top_title)
    public TextView title;
    private ToSrcDetailBean bean;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @BindViews({R.id.src_detail_fromsite,R.id.src_detail_tosite,
                R.id.src_detail_juli,R.id.src_detail_to_juli,
                R.id.src_detail_time,R.id.src_detail_srctype,
                R.id.src_detail_guige,R.id.src_detail_cartype,
                R.id.src_detail_other,R.id.src_detail_message,
                R.id.src_detail_ownername,R.id.src_detail_tel,
                R.id.src_detail_info})
    public List<TextView> mText;

    @OnClick(R.id.src_detail_submit)
    public void submit()
    {
        Intent intent = new Intent(FindDetailActivity.this,BaojiaEditActivity.class);
        intent.putExtra("srcdetail",bean);
        startActivity(intent);
    }

    @BindView(R.id.src_detail_logo)
    public ImageView logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_src_detail);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }

    private void initView()
    {
        title.setText("货源详情");
        bean = (ToSrcDetailBean) getIntent().getSerializableExtra("findSrc");
        mText.get(0).setText(bean.getFromSite());
        mText.get(1).setText(bean.getToSite());
        mText.get(4).setText(bean.getPreloadtime());
        mText.get(5).setText(bean.getCargotype());
        mText.get(10).setText(bean.getOwnername());
        mText.get(11).setText(bean.getTel());
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));
        ToolsUtils.getInstance().toastShowStr(FindDetailActivity.this,bean.getCaragoGUID());
    }
}
