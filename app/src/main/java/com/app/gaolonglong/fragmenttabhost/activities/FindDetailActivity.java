package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.luoxudong.app.threadpool.ThreadTaskObject;

import java.util.ArrayList;
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
 * Created by yanqi on 2017/9/13.
 */

public class FindDetailActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_title)
    public TextView title;
    private ToSrcDetailBean bean;
    private GetSRCBean.DataBean data;
    private List<GetSRCBean.DataBean> list = new ArrayList<>();
    private String billGuid;
    private String flag;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back_txt)
    public void backs(){
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
        bean = (ToSrcDetailBean) getIntent().getSerializableExtra("findSrc");
        flag = TextUtils.isEmpty(getIntent().getStringExtra("flag"))?"":getIntent().getStringExtra("flag");
        billGuid = bean.getBillsGUID();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //mText.get(5).setText("测试测试拉");
                //ToolsUtils.getInstance().toastShowStr(FindDetailActivity.this,"sskksksj");
                getDetail(initJsonData());
            }
        }).start();
        title.setText("货源详情");
        submit.setOnClickListener(this);
        mLinear.get(0).setOnClickListener(this);
        mLinear.get(1).setOnClickListener(this);
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));
        if (GetUserInfoUtils.getUserType(FindDetailActivity.this).equals("3"))
        {
            submit.setVisibility(View.GONE);
        }
        if (flag.equals("baojiaFragment")){
            submit.setVisibility(View.GONE);
        }

    }
    private void setViewVal(GetSRCBean src)
    {
        data = src.getData().get(0);
        String need = "";
        mText.get(0).setText(data.getFromDetailedAddress());
        mText.get(1).setText(data.getToDetailedAddress());
        mText.get(4).setText(data.getPreloadtime());
        mText.get(5).setText(data.getCargotype());
        mText.get(7).setText(data.getTrucklengthHZ()+"/"+bean.getTrucktypeHZ());
        mText.get(10).setText(data.getOwnername()+"");
        mText.get(11).setText(data.getOwnerphone()+"");
        mText.get(9).setText(data.getRemark()+"");
        mText.get(12).setText("已发货"+data.getOwnerbill()+"次");
        if (data.getPaperReceipt().equals("1")){
            need = need+"纸质回单/";
        }
        if (data.getUploadReceipt().equals("1")){
            need = need+"上传签收单/";
        }
        if (data.getInvoiceType().equals("1")){
            need = need+"开发票";
        }
        mText.get(8).setText(need);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.src_detail_submit:
                if (!GetUserInfoUtils.getVtrueName(FindDetailActivity.this).equals("9")){
                    ToolsUtils.getInstance().toastShowStr(FindDetailActivity.this,"请认证完成后再报价");
                }else {
                    if (bean.getMyPriceStatus().equals("1")){
                        String str = "您已对该货源进行过报价，请到报价列表进行查看";
                        ToolsUtils.getInstance().toastShowStr(FindDetailActivity.this,str);
                    }else {
                        Intent intent = new Intent(FindDetailActivity.this,BaojiaEditActivity.class);
                        intent.putExtra("srcdetail",bean);
                        startActivity(intent);
                    }

                }
                break;
            case R.id.find_detail_fromsitell:
                Intent intent = new Intent(FindDetailActivity.this,RouteMapActivity.class);
                intent.putExtra("fromsitelatlng",data.getLoadaddHZ());
                intent.putExtra("tositelatlng",data.getArrivedaddHZ());
                startActivity(intent);
                //Log.e("detail",data.getArrivedaddHZ());
                break;
            case R.id.find_detail_tositell:
                Intent intents = new Intent(FindDetailActivity.this,RouteMapActivity.class);
                intents.putExtra("fromsitelatlng",data.getLoadaddHZ());
                intents.putExtra("tositelatlng",data.getArrivedaddHZ());
                startActivity(intents);
                break;
        }

    }
    private String initJsonData()
    {
        String guid = GetUserInfoUtils.getGuid(FindDetailActivity.this);
        String mobile = GetUserInfoUtils.getMobile(FindDetailActivity.this);
        String key = GetUserInfoUtils.getKey(FindDetailActivity.this);
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("billsGUID",billGuid);

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void getDetail(String json){
        RetrofitUtils.getRetrofitService()
                .getSrcDetail(Constant.MYINFO_PAGENAME, Config.GETSRCDETAIL,json)
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
                        Log.e("findDetail",getSRCBean.getErrorCode()+"--"+getSRCBean.getErrorMsg());
                        setViewVal(getSRCBean);
                    }
                });
    }
}
