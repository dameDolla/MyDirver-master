package com.mr.truck.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.adapter.BaojiaPopAdapter;
import com.mr.truck.bean.CarTeamBean;
import com.mr.truck.bean.GetSRCBean;
import com.mr.truck.bean.ToSrcDetailBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.CommomDialog;

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
 * Created by yanqi on 2017/9/15.
 */

public class BaojiaEditActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.top_title)
    public TextView title;
    private String needPay;
    private String userguid;
    private String mobile;
    private String key;
    private String yunshu,yanshi,shuifee;
    private String fuwu;
    private String other;
    private String zhuang, xie;
    private String sum;
    private ToSrcDetailBean bean;
    private PopupWindow popMenu;
    private View contentView;
    private WindowManager.LayoutParams params;
    private List<CarTeamBean.DataBean> carList = new ArrayList<>();
    private BaojiaPopAdapter popadapter;
    private String truckno;
    private String usertype;
    private String isFapiao,qianshoudan,huidan;
    private String isFapiao1;
    private String fapiaoType;
    private String flag;

    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.title_back_txt)
    public void back2() {
        finish();
    }

    @BindView(R.id.baojia_edit_parent)
    public LinearLayout parent;

    @BindView(R.id.baojia_submit)
    public Button submit;

    @OnClick(R.id.baojia_submit)
    public void submit() {
        submitBaojia();
    }

    @BindViews({R.id.baojia_ownwename, R.id.baojia_ownerphone, R.id.baojia_zhuang, R.id.baojia_xie,
            R.id.baojia_carinfo, R.id.baojia_time, R.id.baojia_otherneed, R.id.baojia_sum_fee})
    public List<TextView> mText;

    @BindViews({R.id.baojia_yunshu_fee, R.id.baojia_other_fee})
    public List<EditText> mEdit;

    @BindView(R.id.baojia_yanshi_fee)
    public TextView yanshifee;

    @BindViews({R.id.baojia_yj_fee, R.id.baojia_sj_fee})
    public List<TextView> fee;


    @BindView(R.id.baojia_yunshu_carinfo)
    public TextView carinfo;
    @BindView(R.id.baojia_yunshu_carinfo2)
    public TextView carinfo2;

    @BindViews({R.id.baojia_edit_sjtxt,R.id.baojia_sj_fee})
    public List<TextView>  sjItem;

    @BindView(R.id.baojia_edit_status_ll)
    public LinearLayout status_ll;

    @BindView(R.id.baojia_edit_intro)
    public TextView intro;

    @BindView(R.id.baojia_xie_time)
    public TextView xietime;

    @BindViews({R.id.baojia_edit_baojiastatus,R.id.baojia_edit_reason})
    public List<TextView> statustxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baojia_edit);
        ButterKnife.bind(this);
        init();
    }



    private void init() {
        initView();
        initPopwindow();
    }

    private void initView() {
        String need = "";
        title.setText("正在报价");
        //ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
        usertype = GetUserInfoUtils.getUserType(BaojiaEditActivity.this);
        bean = (ToSrcDetailBean) getIntent().getSerializableExtra("srcdetail");
        // baojiaFragment
        flag = TextUtils.isEmpty(getIntent().getStringExtra("flag"))?"detail":getIntent().getStringExtra("flag");
        isFapiao = bean.getInvoiceType();
        huidan = bean.getPaperReceipt();
        qianshoudan = bean.getUploadReceipt();
        xietime.setText(TextUtils.isEmpty(bean.getPrearrivetime())?"":bean.getPrearrivetime());
        fapiaoType = GetUserInfoUtils.getFapiaoType(BaojiaEditActivity.this);
        //Log.e("missiondetail",isFapiao);
        mText.get(0).setText(bean.getOwnername());
        mText.get(1).setText("");
        mText.get(2).setText(bean.getFromDetailedAddress());
        mText.get(3).setText(bean.getToDetailedAddress());
        mText.get(4).setText(bean.getCargotype() + "\\" + bean.getTrucklengthHZ() + "\\" + bean.getTrucktypeHZ());
        mText.get(5).setText(bean.getPreloadtime());

        Log.e("baojiaeditdddd",isFapiao+"--"+huidan+"--"+qianshoudan);
        if (isFapiao.equals("0")){
            sjItem.get(0).setVisibility(View.GONE);
            sjItem.get(1).setVisibility(View.GONE);
        }else if (isFapiao.equals("1")){
            need = "开发票/";
        }
        if (huidan.equals("1")){
            need = need+"纸质回单/";
        }
        if (qianshoudan.equals("1")){
            need = need+"上传签收单";
        }
        mText.get(6).setText(need);
        if (usertype.equals("2")) {
            carinfo.setEnabled(false);
            getCarTeam(initCarJsonData());
        }
        if (flag.equals("baojiaFragment")){
            String s = getIntent().getStringExtra("msg");
            String status =  getIntent().getStringExtra("baojiastatus");
            String bidder =  getIntent().getStringExtra("Bidder");

            status_ll.setVisibility(View.VISIBLE);
            if (status.equals("0")&&bidder.equals("0")){
                statustxt.get(0).setVisibility(View.VISIBLE);
            }
            submit.setText("重新报价");
            statustxt.get(1).setText(s);
        }
        String str = "";
        if (isFapiao.equals("1")){
            if (fapiaoType.equals("1")){
                str = "您委托平台代开发票";
            }else if (fapiaoType.equals("0")){
                str = "您自己开具发票";
            }
        }else if (isFapiao.equals("0")){
            str = "客户不要求开发票";
        }
        intro.setText(str);
        textWatch();
        carinfo.setOnClickListener(this);

    }



    /**
     * 执行提交报价的动作
     */
    private void addBaojia(String json,String method) {
        RetrofitUtils.getRetrofitService()
                .AddBaojia("Prices", method, json)
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
                        ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this, getSRCBean.getErrorMsg());
                        if (getSRCBean.getErrorCode().equals("200")) {
                            Intent intent = new Intent(BaojiaEditActivity.this, MainActivity.class);
                            intent.putExtra("flag", "baojiaEdit");
                            startActivity(intent);
                        }

                    }
                });
    }

    /**
     * 提交报价
     */
    private void submitBaojia() {
        String s = mText.get(7).getText() + "";
        yunshu = TextUtils.isEmpty(mEdit.get(0).getText().toString()) ? "0" : mEdit.get(0).getText().toString();
        other = TextUtils.isEmpty(mEdit.get(1).getText().toString()) ? "0" : mEdit.get(1).getText().toString();
        yanshi = TextUtils.isEmpty(yanshifee.getText().toString()) ? "0" : yanshifee.getText().toString();
        shuifee = TextUtils.isEmpty(fee.get(1).getText().toString()) ? "0" : fee.get(1).getText().toString();
        truckno = (carinfo.getText()).equals("选择车辆")?"":carinfo.getText().toString();
        String carinfos = carinfo2.getText() + "";
        final Map<String, String> map = new HashMap<String, String>();
        if (yunshu.equals("0")){
            ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"请输入运输费");
        }else {

            userguid = ToolsUtils.getString(BaojiaEditActivity.this, Constant.LOGIN_GUID, "");
            mobile = ToolsUtils.getString(BaojiaEditActivity.this, Constant.MOBILE, "");
            key = ToolsUtils.getString(BaojiaEditActivity.this, Constant.KEY, "");
            if (!carinfos.isEmpty()) {
                String[] infos = carinfos.split("/");
                map.put("trucklength", infos[1]);
                map.put("trucktype", infos[0]);
            } else {
                if (GetUserInfoUtils.getUserType(BaojiaEditActivity.this).equals("2")){
                    ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"您尚未添加车辆信息");
                    return;
                }else {
                    map.put("trucklength", "");
                    map.put("trucktype", "");
                }

            }
            if (TextUtils.isEmpty(bean.getInvitationID())){
                map.put("InvitationID","");
            }else {
                map.put("InvitationID",bean.getInvitationID());
            }
            map.put("GUID", userguid);
            map.put(Constant.MOBILE, mobile);
            map.put(Constant.KEY, key);
            map.put("ownerguid", bean.getOwnerguid());
            map.put("billsGUID", bean.getBillsGUID());
            map.put("driverGUID", bean.getDriverGUID());
            map.put("drivername", bean.getDrivername());
            map.put("companyGUID", bean.getCompanyGUID());
            map.put("totalchargeM", s);
            map.put("company", bean.getCompany());
            map.put("DelayFee",yanshi);
            map.put("PlatformCommission","55");
            map.put("PlatformTax",shuifee);
            if (isFapiao.equals("1")){
                map.put("CInvoiceType","1");
            }else {
                map.put("CInvoiceType","-1");
            }

            map.put("cargoGUID", bean.getBillsGUID());
            map.put("imforfeeM", "0");
            map.put("priceM", yunshu);
            map.put("otherfeeM", other);

            map.put("feeremarkM", "");

            map.put("price", "0");
            map.put("loadfee", "0");
            map.put("unloadfee", "0");
            map.put("otherfee", "0");
            map.put("totalcharge", "0");
            map.put("feeremark", "");

            map.put("truckno", truckno);

            if (flag.equals("baojiaFragment")){
                map.put("cargopricesGUID",getIntent().getStringExtra("cargopricesGUID")+"");
                map.put("UpdatePriceTime",getIntent().getStringExtra("UpdatePriceTime"));
            }
            String str ="";

            Log.e("fapiaotype",fapiaoType);

            new CommomDialog(BaojiaEditActivity.this, R.style.dialog,"您确定要对此货源进行报价吗", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                    if (confirm) {
                        if (flag.equals("baojiaFragment")){
                            addBaojia(JsonUtils.getInstance().getJsonStr(map),Config.UPDATE_BAOJIA);
                        }else {
                            addBaojia(JsonUtils.getInstance().getJsonStr(map),Config.ADD_BAOJIA);
                        }

                        //initJsonData();
                    }
                }
            }).setTitle("提示").show();
        }



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

    private void textWatch() {
        mEdit.get(0).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                /*String price = mEdit.get(0).getText() + "";
                if (price.equals("")) {
                    mText.get(5).setText("0.00");
                } else {
                    mText.get(5).setText(Float.parseFloat(price) + "");
                }*/
                showTotalPrice();

            }
        });
        mEdit.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(1).getText() + "") + "";
                mText.get(5).setText(sum);*/
                showTotalPrice();
            }
        });
        /*mEdit.get(2).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               *//* String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(1).getText() + "") + "";
                mText.get(5).setText(sum);*//*
                showTotalPrice();
            }
        });*/
       /* mEdit.get(3).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               *//* String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(1).getText() + "") + "";
                mText.get(5).setText(sum);*//*
                showTotalPrice();
            }
        });
        mEdit.get(4).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               *//* String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(1).getText() + "") + "";
                mText.get(5).setText(sum);*//*
                showTotalPrice();
            }
        });*/


    }

    private void showTotalPrice() {
        String price1 = TextUtils.isEmpty(mEdit.get(0).getText()) ? "0" : mEdit.get(0).getText() + "";
        String price2 = TextUtils.isEmpty(mEdit.get(1).getText()) ? "0" : mEdit.get(1).getText() + "";
        String price3 = TextUtils.isEmpty(yanshifee.getText()) ? "0" : yanshifee.getText() + "";
        String price4 = TextUtils.isEmpty(fee.get(0).getText()) ? "0" : fee.get(0).getText() + "";
        String price5 = TextUtils.isEmpty(fee.get(1).getText()) ? "0" : fee.get(1).getText() + "";
        String sum = "";
        float shuijin = (float) 0.00;
        float yj = Float.parseFloat("55");
        if (price1.equals("") && price2.equals("") && price3.equals("")) {
            sum = "0.00";
        } else if (price2.equals("") && price3.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price1) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price1) + shuijin + yj) + "";
        } else if (price1.equals("") && price2.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price3) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price3) + shuijin + yj) + "";
        } else if (price1.equals("") && price3.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price2) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price2) + shuijin + yj) + "";
        } else if (price3.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price1) + Float.parseFloat(price2) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price1) + Float.parseFloat(price2) + shuijin + yj) + "";
        } else if (price2.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price1) + Float.parseFloat(price3) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price1) + Float.parseFloat(price3) + shuijin + yj) + "";
        } else if (price1.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price2) + Float.parseFloat(price3) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price2) + Float.parseFloat(price3) + shuijin + yj) + "";
        } else if (!price1.equals("") && !price2.equals("") && !price3.equals("")) {
            if (isFapiao.equals("1")){
                shuijin = (float) ((Float.parseFloat(price1) + Float.parseFloat(price2) + Float.parseFloat(price3) + yj) * 0.06);
            }
            sum = (Float.parseFloat(price1) + Float.parseFloat(price2) + Float.parseFloat(price3) + shuijin + yj) + "";
        }

        /*if (price1.equals("")&&price2.equals("")&&price3.equals("")&&price4.equals("")&&price5.equals(""))
        {
            sum =  "0.00";
        }else if (price2.equals("") && price3.equals("")&&price4.equals("")&&price5.equals("")){
            sum =  Float.parseFloat(price1)+"";
            shuijin = (Float.parseFloat(price1))*0.06+"";
        }else if (price1.equals("") && price2.equals("")&&price4.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price3)+"";
            shuijin = (Float.parseFloat(price3))*0.06+"";
        }else if (price1.equals("") && price3.equals("")&&price4.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price2)+"";
        }else if (price1.equals("")&&price2.equals("")&&price3.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price4)+"";
        }else if (price1.equals("")&&price2.equals("")&&price3.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price2.equals("")&&price3.equals("")){
            sum = Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price2.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price3)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price2.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price4)+Float.parseFloat(price3)+"";
        }else if (price1.equals("")&&price3.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price3.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price4)+"";
        }else if (price1.equals("")&&price4.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price3)+"";
        }else if (price2.equals("")&&price3.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price5)+"";
        }else if (price2.equals("")&&price3.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price4)+"";
        }else if (price3.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price2.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price3)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price4.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price3)+Float.parseFloat(price5)+"";
        }else if (price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price3)+Float.parseFloat(price4)+"";
        }else if (price2.equals("")&&price1.equals("")){
            sum = Float.parseFloat(price3)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price3.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price3)+Float.parseFloat(price5)+"";
        }else if (price1.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price2)+Float.parseFloat(price4)+Float.parseFloat(price3)+"";
        }else if (price2.equals("")&&price3.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }else if (price2.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price3)+Float.parseFloat(price5)+"";
        }else if (price2.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price4)+Float.parseFloat(price3)+"";
        }else if (price3.equals("")&&price4.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price5)+"";
        }else if (price3.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price4)+"";
        }else if (price4.equals("")&&price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+Float.parseFloat(price3)+"";
        }else if (!price1.equals("") && !price2.equals("") && !price3.equals("") && !price4.equals("") && !price5.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price3)+Float.parseFloat(price2)+Float.parseFloat(price4)+Float.parseFloat(price5)+"";
        }*/

        mText.get(7).setText(sum);//总价
        fee.get(1).setText(shuijin + "");
    }

    private String initCarJsonData() {
        String guid = GetUserInfoUtils.getGuid(BaojiaEditActivity.this);
        String mobile = GetUserInfoUtils.getMobile(BaojiaEditActivity.this);
        String key = GetUserInfoUtils.getKey(BaojiaEditActivity.this);
        String companyguid = GetUserInfoUtils.getCompanyGuid(BaojiaEditActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        if (usertype.equals("2")) {
            map.put("driverGUID", guid);
        } else {
            map.put("companyGUID", companyguid);
        }


        return JsonUtils.getInstance().getJsonStr(map);
    }

    private void getCarTeam(String json) {
        RetrofitUtils.getRetrofitService()
                .getCarTeamList(Constant.TRUCK_PAGENAME, Config.GETTRUCKS, json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarTeamBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CarTeamBean carTeamBean) {
                        if (usertype.equals("2")) {
                            CarTeamBean.DataBean data = carTeamBean.getData().get(0);
                            if (data.getVtruck().equals("9")){
                                carinfo.setText(data.getTruckno() + "");
                                carinfo2.setText(data.getTrucktype() + "/" + data.getTrucklength());
                            }

                        } else {
                            carList.clear();
                            int sizes = carTeamBean.getData().size();
                            for (int i = 0; i < sizes; i++) {
                                if ((carTeamBean.getData().get(i).getVtruck()).equals("9")) {
                                    Log.e("iiiisize", i + "");
                                    carList.add(carTeamBean.getData().get(i));
                                }
                            }

                            popadapter.notifyDataSetChanged();
                        }


                    }
                });
    }

    private void initPopwindow() {
        //initPopData();
        contentView = getLayoutInflater().inflate(R.layout.text_listview_item, null);
        popMenu = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(dw);
        popMenu.setFocusable(true);
        popMenu.setTouchable(true);
        popMenu.setAnimationStyle(R.style.mypopwindow_anim_style);

    }

    private List<Map<String, String>> initPopData(List<String> str) {
        List<Map<String, String>> menuData1 = new ArrayList<Map<String, String>>();
        List<String> menuStr1 = str;
        Map<String, String> map1;
        for (int i = 0, len = menuStr1.size(); i < len; ++i) {
            map1 = new HashMap<String, String>();
            map1.put("name", menuStr1.get(i));
            menuData1.add(map1);
        }
        return menuData1;
    }

    private List<Map<String, String>> list;

    private void showPop() {
        //list = l;
        ListView popListView = (ListView) contentView.findViewById(R.id.text_item_listview);
        popListView.setOnItemClickListener(this);
        popadapter = new BaojiaPopAdapter(BaojiaEditActivity.this, carList);

        popListView.setAdapter(popadapter);
        getCarTeam(initCarJsonData());
        popMenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        carinfo.setText(carList.get(i).getTruckno());
        carinfo2.setText(carList.get(i).getTrucktype() + "/" + carList.get(i).getTrucklength());
        popMenu.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.baojia_yunshu_carinfo:

                showPop();
                break;
        }
    }
}
