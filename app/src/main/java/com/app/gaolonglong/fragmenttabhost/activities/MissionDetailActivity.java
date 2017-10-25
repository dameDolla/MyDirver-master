package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * Created by yanqi on 2017/9/28.
 */

public class MissionDetailActivity extends BaseActivity  implements View.OnClickListener{
    private String STATUS1 = "运单已生成";
    private String STATUS2 = "司机已前往装货地点";
    private String STATUS3 = "运输中";

    private String STATUS5 = "卸货已完成";
    private String STATUS6 = "已签收";
    private String STATUS7 = "已完成";

    private String BUTTONTXT1 = "前往装货地";
    private String BUTTONTXT2 = "开始装货";
    private String BUTTONTXT3 = "开始卸货";
    private String BUTTONTXT4 = "收货人签收";
    private MissionDetailBean bean;
    private String methodName = null;
    private int REQUESTCODE = 110;
    private String infos = "";

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

    @BindViews({R.id.mission_statustwo_nowaddrtxt,R.id.mission_statustwo_nowaddr,R.id.mission_status_two,
                R.id.mission_status_three,R.id.mission_statusthree_tositetxt,R.id.mission_statusthree_tosite,
                R.id.mission_status_four,R.id.mission_statusfour_txt,
                R.id.mission_status_five,R.id.mission_statusfive_txt,R.id.mission_statussix_txt,
                R.id.mission_status_six})
    public List<TextView> status;

    @BindView(R.id.mission_detail_step_addr)
    public TextView zcaddr;

    @BindViews({R.id.mission_detail_shrname,R.id.mission_detail_shrtel,R.id.mission_status_tosite,
                R.id.mission_detail_drivername,R.id.mission_detail_drivertel,R.id.mission_detail_truckno})
    public List<TextView> info;

    @BindViews({R.id.mission_detail_changestatus,R.id.mission_detail_copy})
    public List<Button> mButton;
    @BindView(R.id.mission_detail_icon)
    public SimpleDraweeView icon;

    @BindView(R.id.mission_status_verticalline)
    public ImageView vertical;

    @BindViews({R.id.mission_status1_time,R.id.mission_status2_time,R.id.mission_status3_time,
                R.id.mission_status4_time,R.id.mission_status5_time,R.id.mission_status6_time})
    public List<TextView> mTime;

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
        mText.get(3).setText(bean.getFromDetailedAddress());
        mText.get(4).setText("200");
        mText.get(5).setText(bean.getDealprice());
        mText.get(6).setText(bean.getLoadfee());
        mText.get(7).setText(bean.getUnloadfee());
        mText.get(8).setText(bean.getBillNumber());
        info.get(0).setText(bean.getSignby()+"");
        //info.get(1).setText(bean.get);
        info.get(2).setText(bean.getToDetailedAddress());
        info.get(3).setText(bean.getDrivername());
        info.get(4).setText(bean.getDriverphone());
        info.get(5).setText(bean.getTruckno());
        icon.setImageURI(Uri.parse(bean.getAvatarAddress()));
        String usertype = GetUserInfoUtils.getUserType(MissionDetailActivity.this);
        /*else{*/
            if (bean.getStatus().equals("0")){
                if (usertype.equals("4")&& !bean.getDrivername().equals("")){
                    mText.get(0).setText("已调度");
                }else {
                    mText.get(0).setText(STATUS1);
                }

                methodName = Config.MISSION_STATUS_YUBAO;
                mButton.get(0).setText(BUTTONTXT1);
                infos = BUTTONTXT1;
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
            }else if (bean.getStatus().equals("1")){
                infos= BUTTONTXT2;
                mText.get(0).setText(STATUS2);
                methodName = Config.MISSION_STATUS_ZHIXING;
                vertical.setImageResource(R.drawable.vertical_line_2);
                mButton.get(0).setText(BUTTONTXT2);
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
                status.get(0).setTextColor(Color.BLACK);
                status.get(1).setTextColor(Color.BLACK);
                status.get(2).setTextColor(Color.BLACK);

            }else if (bean.getStatus().equals("2")){
                infos= BUTTONTXT3;
                mText.get(0).setText(STATUS3);
                methodName = Config.MISSION_STATUS_XIEHUO;
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
                mButton.get(0).setText(BUTTONTXT3);
                vertical.setImageResource(R.drawable.vertical_line_3);
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(0).setTextColor(Color.BLACK);
                status.get(1).setTextColor(Color.BLACK);
                status.get(2).setTextColor(Color.BLACK);
                status.get(3).setTextColor(Color.BLACK);
                status.get(4).setTextColor(Color.BLACK);
                status.get(5).setTextColor(Color.BLACK);

            }else if (bean.getStatus().equals("3")){
                infos= BUTTONTXT4;
                mText.get(0).setText(STATUS5);
                methodName = Config.MISSION_STATUS_QIANSHOU;
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
                mButton.get(0).setText(BUTTONTXT4);
                vertical.setImageResource(R.drawable.vertical_line_4);
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(0).setTextColor(Color.BLACK);
                status.get(1).setTextColor(Color.BLACK);
                status.get(2).setTextColor(Color.BLACK);
                status.get(3).setTextColor(Color.BLACK);
                status.get(4).setTextColor(Color.BLACK);
                status.get(5).setTextColor(Color.BLACK);
                status.get(6).setTextColor(Color.BLACK);
                status.get(7).setTextColor(Color.BLACK);
            }else if (bean.getStatus().equals("4")){
                mText.get(0).setText(STATUS6);
                mButton.get(0).setText(STATUS6);
                mButton.get(0).setEnabled(false);
                vertical.setImageResource(R.drawable.vertical_line_4);
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
                status.get(0).setTextColor(Color.BLACK);
                status.get(1).setTextColor(Color.BLACK);
                status.get(2).setTextColor(Color.BLACK);
                status.get(3).setTextColor(Color.BLACK);
                status.get(4).setTextColor(Color.BLACK);
                status.get(5).setTextColor(Color.BLACK);
                status.get(6).setTextColor(Color.BLACK);
                status.get(7).setTextColor(Color.BLACK);
                status.get(8).setTextColor(Color.RED);
                status.get(9).setTextColor(Color.RED);
            }else if (bean.getStatus().equals(9)){
                mText.get(0).setText(STATUS7);
                mButton.get(0).setText(STATUS7);
                mButton.get(0).setEnabled(false);
                vertical.setImageResource(R.drawable.vertical_line_5);
                zcaddr.setText(bean.getFromDetailedAddress());
                status.get(5).setText(bean.getToDetailedAddress());
                status.get(1).setText(ToolsUtils.getString(MissionDetailActivity.this,Constant.ADDRESS,""));
                mText.get(9).setText(bean.getSigntime());
                status.get(0).setTextColor(Color.BLACK);
                status.get(1).setTextColor(Color.BLACK);
                status.get(2).setTextColor(Color.BLACK);
                status.get(3).setTextColor(Color.BLACK);
                status.get(4).setTextColor(Color.BLACK);
                status.get(5).setTextColor(Color.BLACK);
                status.get(6).setTextColor(Color.BLACK);
                status.get(7).setTextColor(Color.BLACK);
                status.get(8).setTextColor(Color.RED);
                status.get(9).setTextColor(Color.RED);
                status.get(10).setTextColor(Color.RED);
                status.get(11).setTextColor(Color.RED);
            }
        if (usertype.equals("4")){
            if (bean.getDrivername().equals("")){
                mButton.get(0).setText("选择运输司机");
            }else {
                mButton.get(0).setText("更换司机");

            }

        }
       /* }*/
        /*if (bean.getDriverGUID().equals("") && GetUserInfoUtils.getVcompany(MissionDetailActivity.this).equals("9"))
        {
            mButton.get(0).setText("选择运输司机");
        }else {

        }*/



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
            mButton.get(0).setText(STATUS5);
            mButton.get(0).setEnabled(false);
        }else if (bean.getStatus().equals(9)){
            mButton.get(0).setText(STATUS5);
            mButton.get(0).setEnabled(false);
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
                if (GetUserInfoUtils.getUserType(MissionDetailActivity.this).equals("4"))
                {
                    Intent intent = new Intent(MissionDetailActivity.this,SelectDriverActivity.class);
                    intent.putExtra("missionguid",bean.getBillsGUID());
                    intent.putExtra("flags",Constant.MISSIONFLAGS);
                    startActivityForResult(intent,REQUESTCODE);
                }else {
                    new CommomDialog(MissionDetailActivity.this, R.style.dialog, "您确定要执行"+infos+"这个操作吗?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            if (confirm){
                                changeStatus(methodName);
                            }
                        }
                    }).setTitle("提示").show();

                }
               /* if(bean.getDriverGUID().equals("") && GetUserInfoUtils.getVcompany(MissionDetailActivity.this).equals("9"))
                {

                }else{

                }*/

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == 1){
            final String name = data.getStringExtra("drivername");

            new CommomDialog(MissionDetailActivity.this, R.style.dialog, "您选择的司机为"+name, new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                    if (confirm){
                        mButton.get(0).setText(name);
                    }
                }
            }).setTitle("提示").show();

        }
    }
}
