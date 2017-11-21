package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.ToJiaoYiDetailBean;

/**
 * Created by yanqi on 2017/11/17.
 */

public class JiaoYiDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView remark;
    private TextView money;
    private TextView status;
    private TextView paytype;
    private TextView intro;
    private TextView time;
    private TextView ordernum;
    private TextView reaseon;
    private LinearLayout reasonll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi_detail);
        initView();
    }



    private void initView()
    {
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView back_txt = (TextView) findViewById(R.id.title_back_txt);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        remark = (TextView) findViewById(R.id.jiaoyi_detail_remark);
        money = (TextView) findViewById(R.id.jiaoyi_detail_money);
        status = (TextView) findViewById(R.id.jiaoyi_detail_status);
        paytype = (TextView) findViewById(R.id.jiaoyi_detail_paytype);
        intro = (TextView) findViewById(R.id.jiaoyi_detail_intro);
        time = (TextView) findViewById(R.id.jiaoyi_detail_time);
        ordernum = (TextView) findViewById(R.id.jiaoyi_detail_ordernum);
        reaseon = (TextView) findViewById(R.id.jiaoyi_detail_reason);
        reasonll = (LinearLayout) findViewById(R.id.jiaoyi_detail_reasonll);

        title.setText("交易详情");
        back.setOnClickListener(this);
        back_txt.setOnClickListener(this);

        setView();
    }
    private void setView()
    {
        ToJiaoYiDetailBean bean = (ToJiaoYiDetailBean) getIntent().getSerializableExtra("jiaoyi");
        remark.setText(bean.getRemark());
        money.setText("+"+bean.getTradeamount()+"元");
        ordernum.setText(bean.getOrderNumber());
        time.setText(bean.getTradetime());
        intro.setText(bean.getRemark());
        String s = bean.getStates();
        if (s.equals("0") && bean.getTradetype().equals("9")){
            status.setVisibility(View.VISIBLE);
            status.setText("提现申请已提交");
        }else if (s.equals("1") && bean.getTradetype().equals("9")){
            status.setVisibility(View.VISIBLE);
            status.setText("提现成功");
        }else if (s.equals("0") && bean.getTradetype().equals("9")){
            status.setVisibility(View.VISIBLE);
            reasonll.setVisibility(View.VISIBLE);
            status.setText("提现被取消");
        }
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
