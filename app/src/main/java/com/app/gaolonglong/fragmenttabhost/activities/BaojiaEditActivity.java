package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/15.
 */

public class BaojiaEditActivity extends BaseActivity {

    @BindView(R.id.top_title)
    public TextView title;
    private String needPay;
    private String userguid;
    private String mobile;
    private String key;
    private String yunshu;
    private String fuwu;
    private String other;
    private String zhuang,xie;
    private String sum;
    private ToSrcDetailBean bean;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @OnClick(R.id.title_back_txt)
    public void back2()
    {
        finish();
    }

    @OnClick(R.id.baojia_submit)
    public void submit()
    {
        submitBaojia();
    }
    @BindViews({R.id.baojia_et_baozheng,R.id.baojia_et_xinxi,R.id.baojia_fuwu_fee,R.id.baojia_need_pay,
                R.id.baojia_other_fee,R.id.baojia_ownwename,R.id.baojia_shuijin,R.id.baojia_sum_fee,
                R.id.baojia_yunshu_fee,R.id.baojia_zhuang_fee,R.id.baojia_xie_fee})
    public List<TextView> mText;
    @BindView(R.id.baojia_logo)
    public SimpleDraweeView logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baojia_edit);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("正在报价");

        bean = (ToSrcDetailBean) getIntent().getSerializableExtra("srcdetail");
        ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this, bean.getOwnerguid());
       // initJsonData();
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));
        mText.get(5).setText("货主: "+bean.getOwnername());

    }
    private String initJsonData()
    {
        yunshu = mText.get(8).getText().toString();
        fuwu = mText.get(2).getText().toString();
        needPay = mText.get(3).getText().toString();
        other = mText.get(4).getText().toString();
        zhuang = mText.get(9).getText().toString();
        xie = mText.get(10).getText().toString();
        // String needPay = mText.get(3).getText().toString();
        sum = mText.get(7).getText().toString();
        String totol = Integer.parseInt(yunshu)+Integer.parseInt(zhuang)+Integer.parseInt(other)+Integer.parseInt(xie)+"";
        userguid = ToolsUtils.getString(BaojiaEditActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(BaojiaEditActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(BaojiaEditActivity.this, Constant.KEY,"");
        Map<String,String> map = new HashMap<String,String>();
        map.put("GUID",userguid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("ownerguid",bean.getOwnerguid());
        map.put("billsGUID",bean.getBillsGUID());
        map.put("driverGUID",bean.getDriverGUID());
        map.put("drivername",bean.getDrivername());
        map.put("companyGUID",bean.getCompanyGUID());
        map.put("company",bean.getCompany());
        map.put("truckno",bean.getTruckno());
        map.put("trucklength",bean.getTrucklength());
        map.put("cargoGUID",bean.getBillsGUID());
        map.put("imforfeeM","0");
        map.put("priceM",yunshu);
        map.put("loadfeeM",zhuang);
        map.put("unloadfeeM",xie);
        map.put("otherfeeM",other);
        map.put("totalchargeM",totol);
        map.put("trucktype","火车");

        map.put("price","0");
        map.put("loadfee","0");
        map.put("unloadfee","0");
        map.put("otherfee","0");
        map.put("totalcharge","0");
        map.put("feeremark","");
        return JsonUtils.getInstance().getJsonStr(map);
    }
    /**
     * 执行提交报价的动作
     */
    private void addBaojia(String json)
    {
        RetrofitUtils.getRetrofitService()
                .AddBaojia("Prices", Config.ADD_BAOJIA,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetSRCBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetSRCBean getSRCBean) {
                        ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,getSRCBean.getErrorMsg());
                    }
                });
    }
    /**
     * 提交报价
     */
    private void submitBaojia()
    {
        //addBaojia(initJsonData());
        /*String count = ToolsUtils.getString(BaojiaEditActivity.this, Constant.COUNT,"");
        int money = Integer.parseInt(count);
        ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,count);*/
        /*if(money < Integer.parseInt(needPay))
        {
            new CommomDialog(BaojiaEditActivity.this, R.style.dialog, "您的账户余额不足", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                }
            }).setTitle("提示d").show();
        }*/
    }
}
