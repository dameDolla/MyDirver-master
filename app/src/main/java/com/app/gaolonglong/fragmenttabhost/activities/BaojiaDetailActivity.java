package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Created by yanqi on 2017/9/21.
 */

public class BaojiaDetailActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.top_title)
    public TextView title;
    private BaojiaInfoBean bean;
    private String guid;
    private String mobile;
    private String key;

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

    @OnClick(R.id.baojia_detail_phone)
    public void phone()
    {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + bean.getOwnerphone()));//跳转到拨号界面，同时传递电话号码
        startActivity(dialIntent);
    }

    @BindViews({R.id.baojia_detail_hzyun,R.id.baojia_detail_hzzhuang,
                R.id.baojia_detail_hzxie,R.id.baojia_detail_hzmsg,R.id.baojia_detail_cztotal,
                R.id.baojia_detail_hztotal,R.id.baojia_detail_name})
    public List<TextView> mText;
    @BindViews({R.id.baojia_detail_czyun,R.id.baojia_detail_czzhuang,R.id.baojia_detail_czxie,
            R.id.baojia_detail_czmsg,R.id.baojia_detail_czother})
    public List<EditText> mEdit;
    @BindViews({R.id.baojia_detail_tijiao,R.id.baojia_detail_queren})
    public List<LinearLayout> mLL;
    @BindView(R.id.baojia_detail_logo)
    public SimpleDraweeView logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baojia_details);
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
        mText.get(0).setText(bean.getPrice());
        mText.get(1).setText(bean.getLoadfee());
        mText.get(2).setText(bean.getUnloadfee());
        mText.get(3).setText(bean.getFeeremarkM());
        mText.get(5).setText(bean.getTotalcharge());
        mText.get(4).setText(bean.getTotalchargeM());
        mText.get(6).setText(bean.getOwnername());

        mEdit.get(0).setText(bean.getPriceM());
        mEdit.get(1).setText(bean.getLoadfeeM());
        mEdit.get(2).setText(bean.getUnloadfeeM());
        mEdit.get(3).setText(bean.getFeeremarkM());
        mEdit.get(4).setText(bean.getOtherfeeM());
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));

        mLL.get(0).setOnClickListener(this);
        mLL.get(1).setOnClickListener(this);
        //ToolsUtils.getInstance().toastShowStr(BaojiaDetailActivity.this,bean.getCargopricesGUID());
        guid = ToolsUtils.getString(BaojiaDetailActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(BaojiaDetailActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(BaojiaDetailActivity.this, Constant.KEY,"");
    }
    private String initJaonData()
    {

        Map<String,String> map = new HashMap<String,String>();

        float i = Float.parseFloat(mEdit.get(0).getText().toString())+
                Float.parseFloat(mEdit.get(1).getText().toString())+
                Float.parseFloat(mEdit.get(2).getText().toString())+
                Float.parseFloat(mEdit.get(4).getText().toString());
        mText.get(4).setText(i+"");
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("cargopricesGUID",bean.getCargopricesGUID()+"");
        map.put("UpdatePriceTime",bean.getUpdatePriceTime());
        map.put("priceM",mEdit.get(0).getText().toString());
        map.put("loadfeeM",mEdit.get(1).getText().toString());
        map.put("unloadfeeM",mEdit.get(2).getText().toString());
        map.put("otherfeeM",mEdit.get(4).getText().toString());
        map.put("totalchargeM",mText.get(4).getText()+"");
        map.put("feeremarkM",mEdit.get(3).getText().toString());
        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void queren(String json)
    {
        RetrofitUtils.getRetrofitService()
                .agreeBaojia(Constant.PRICE_PAGENAME,Config.AGREE_BAOJIA,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        Log.e("qurenquern",getCodeBean.getErrorMsg());
                    }
                });
    }
    private void tijiao(String json)
    {
        RetrofitUtils.getRetrofitService()
                .updateBaojia(Constant.PRICE_PAGENAME, Config.UPDATE_BAOJIA,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCodeBean getCodeBean) {
                        Log.e("22222",getCodeBean.getErrorMsg());
                        ToolsUtils.getInstance().toastShowStr(BaojiaDetailActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.baojia_detail_tijiao:
               tijiao(initJaonData());

                break;
            case R.id.baojia_detail_queren:
                Map<String,String> map = new HashMap<String,String>();
                map.put("GUID",guid);
                map.put(Constant.MOBILE,mobile);
                map.put(Constant.KEY,key);
                map.put("cargopricesGUID",bean.getCargopricesGUID()+"");
                map.put("UpdatePriceTime",bean.getUpdatePriceTime());
                queren(JsonUtils.getInstance().getJsonStr(map));
                break;
        }
    }
}
