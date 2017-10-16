package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/13.
 */

public class FindDetailActivity extends BaseActivity implements View.OnClickListener{

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

    @BindView(R.id.src_detail_submit)
    public LinearLayout submit;

    @BindViews({R.id.find_detail_fromsitell,R.id.find_detail_tositell})
    public List<LinearLayout> mLinear;

    @OnClick(R.id.src_detail_phone)
    public void calls()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+bean.getTel()));
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
        mText.get(0).setText(bean.getFromDetailedAddress());
        mText.get(1).setText(bean.getToDetailedAddress());
        mText.get(4).setText(bean.getPreloadtime());
        mText.get(5).setText(bean.getCargotype());
        mText.get(7).setText(bean.getTrucklengthHZ()+"/"+bean.getTrucktypeHZ());
        mText.get(10).setText(bean.getOwnername());
        mText.get(11).setText(bean.getTel());
        submit.setOnClickListener(this);
        mLinear.get(0).setOnClickListener(this);
        mLinear.get(1).setOnClickListener(this);
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));
        if (GetUserInfoUtils.getUserType(FindDetailActivity.this).equals("3"))
        {
            submit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.src_detail_submit:
                if (!GetUserInfoUtils.getVtrueName(FindDetailActivity.this).equals("9")){
                    ToolsUtils.getInstance().toastShowStr(FindDetailActivity.this,"请认证完成后再报价");
                }else {
                    Intent intent = new Intent(FindDetailActivity.this,BaojiaEditActivity.class);
                    intent.putExtra("srcdetail",bean);
                    startActivity(intent);
                }
                break;
            case R.id.find_detail_fromsitell:
                Intent intent = new Intent(FindDetailActivity.this,RouteMapActivity.class);
                intent.putExtra("fromsitelatlng",bean.getLoadaddHZ());
                intent.putExtra("tositelatlng",bean.getArrivedaddHZ());
                startActivity(intent);
                break;
            case R.id.find_detail_tositell:
                Intent intents = new Intent(FindDetailActivity.this,RouteMapActivity.class);
                intents.putExtra("fromsitelatlng",bean.getLoadaddHZ());
                intents.putExtra("tositelatlng",bean.getArrivedaddHZ());
                startActivity(intents);
                break;
        }

    }
}
