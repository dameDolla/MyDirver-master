package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaInfoBean;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/9/21.
 */

public class BaojiaDetailActivity extends BaseActivity {
    @BindView(R.id.top_title)
    public TextView title;
    private BaojiaInfoBean bean;

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

    /*@BindViews({R.id.baojia_detail_money,R.id.baojia_detail_yun,R.id.baojia_detail_zhuang,
            R.id.baojia_detail_baozheng,R.id.baojia_detail_msg,R.id.baojia_detail_time,
            R.id.baojia_detail_xinxi,R.id.baojia_detail_xie})
    public List<TextView> mText;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baojia_detail);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("报价详情");
        bean = (BaojiaInfoBean) getIntent().getSerializableExtra("baojaiInfo");
       /* mText.get(0).setText(bean.getTotalchargeM());
        mText.get(1).setText(bean.getPrice());
        mText.get(2).setText(bean.getLoadfee());
        mText.get(4).setText(bean.getFeeremarkM());
        mText.get(5).setText(bean.getUpdatePriceTime());
        mText.get(7).setText(bean.getUnloadfee());*/
    }
    private void toBaojiaEdit()
    {
        ToolsUtils.getInstance().toastShowStr(BaojiaDetailActivity.this,bean.getPriceM());
        Intent intent = new Intent(BaojiaDetailActivity.this,BaojiaUpdateActivity.class);
        intent.putExtra("baojia",bean);
       // startActivity(intent);
    }
}
