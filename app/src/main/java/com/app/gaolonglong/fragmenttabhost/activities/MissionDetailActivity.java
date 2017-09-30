package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
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
 * Created by yanqi on 2017/9/28.
 */

public class MissionDetailActivity extends BaseActivity  implements View.OnClickListener{
    private String STATUS1 = "已接单";
    private String STATUS2 = "装货完成后请点击运往目的地";
    private String STATUS3 = "卸货完成请点击完成";
    private String STATUS4 = "等待货主确认收款";
    private String STATUS5 = "运单已完成";

    private String BUTTONTXT1 = "现在去装货";
    private String BUTTONTXT2 = "运往目的地";
    private String BUTTONTXT3 = "卸货完成";
    private String BUTTONTXT4 = "q确认收款";
    private MissionDetailBean bean;
    private String methodName = null;

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
        mButton.get(0).setOnClickListener(this);
        mButton.get(1).setOnClickListener(this);
        showData();

    }
    private void showData()
    {
        bean = (MissionDetailBean) getIntent().getSerializableExtra("missionDetail");
        mText.get(1).setText("预计装车时间: "+ bean.getPreloadtime());
        mText.get(2).setText(bean.getOwnername());
        mText.get(3).setText(bean.getToDetailedAddress());
        mText.get(4).setText("200");
        mText.get(5).setText(bean.getDealprice());
        mText.get(6).setText(bean.getLoadfee());
        mText.get(7).setText(bean.getUnloadfee());
        mText.get(8).setText(bean.getBillNumber());
        icon.setImageURI(Uri.parse(bean.getAvatarAddress()));
        if (bean.getStatus().equals("0")){
            mText.get(0).setText(STATUS1);
            methodName = Config.MISSION_STATUS_YUBAO;
            mButton.get(0).setText(BUTTONTXT1);
        }else if (bean.getStatus().equals("1")){
            mText.get(0).setText(STATUS2);
            methodName = Config.MISSION_STATUS_ZHIXING;
            mButton.get(0).setText(BUTTONTXT2);
        }else if (bean.getStatus().equals("2")){
            mText.get(0).setText(STATUS3);
            methodName = Config.MISSION_STATUS_XIEHUO;
            mButton.get(0).setText(BUTTONTXT3);
        }else if (bean.getStatus().equals("3")){
            mText.get(0).setText(STATUS4);
            methodName = Config.MISSION_STATUS_QIANSHOU;
            mButton.get(0).setText(BUTTONTXT4);
        }else if (bean.getStatus().equals("4")){
            mText.get(0).setText(STATUS5);
        }else if (bean.getStatus().equals(9)){
            mText.get(0).setText(STATUS5);
            mText.get(9).setText(bean.getSigntime());
        }

    }
    private void change()
    {
        if (bean.getStatus().equals("0")){
            bean.setStatus("1");
            mButton.get(0).setText(BUTTONTXT2);
        }else if (bean.getStatus().equals("1")){
            bean.setStatus("2");
            mButton.get(0).setText(BUTTONTXT3);
        }else if (bean.getStatus().equals("2")){
            bean.setStatus("3");
            mButton.get(0).setText(BUTTONTXT4);
        }else if (bean.getStatus().equals("3")){
            bean.setStatus("4");
        }else if (bean.getStatus().equals("4")){

        }else if (bean.getStatus().equals(9)){

        }
        onCreate(null);

    }
    private void changeStatus(String method)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(MissionDetailActivity.this));
        map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(MissionDetailActivity.this));
        map.put(Constant.KEY, GetUserInfoUtils.getKey(MissionDetailActivity.this));
        map.put("billsGUID",bean.getBillsGUID());
        RetrofitUtils.getRetrofitService()
                .changeMissionStatus(Constant.MYINFO_PAGENAME, method, JsonUtils.getInstance().getJsonStr(map))
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
                        if (getCodeBean.getErrorCode().equals("200"))
                        {
                            change();
                        }
                    }
                });
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
            case R.id.mission_detail_changestatus:
                changeStatus(methodName);
                break;
        }
    }
}
