package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.BaojiaListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.BaojiaInfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

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

public class BaojiaUpdateActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_title)
    public TextView title;
    private String guid;
    private String mobile;
    private String key;
    private BaojiaInfoBean bean;

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

    @BindView(R.id.baojia_submit)
    public Button submit;

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
        title.setText("修改报价");
        submit.setText("确定修改报价");

        bean = (BaojiaInfoBean) getIntent().getSerializableExtra("baojia");

    }
    private void initJsonData()
    {

        guid = ToolsUtils.getString(BaojiaUpdateActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(BaojiaUpdateActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(BaojiaUpdateActivity.this, Constant.KEY,"");

        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        Map<String,String> map = new HashMap<String,String>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);


    }
    private void submit(String json)
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

                    }
                });
    }
    @Override
    public void onClick(View view) {
        submit("");
    }
}
