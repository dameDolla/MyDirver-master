package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.BaojiaPopAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.facebook.drawee.view.SimpleDraweeView;

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
    private String yunshu;
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

    @OnClick(R.id.baojia_submit)
    public void submit() {
        submitBaojia();
    }

    @BindViews({R.id.baojia_et_baozheng, R.id.baojia_fuwu_fee, R.id.baojia_need_pay,
            R.id.baojia_ownwename, R.id.baojia_shuijin, R.id.baojia_sum_fee,})
    public List<TextView> mText;

    @BindViews({R.id.baojia_yunshu_fee, R.id.baojia_zhuang_fee, R.id.baojia_xie_fee, R.id.baojia_other_fee})
    public List<EditText> mEdit;
    @BindView(R.id.baojia_logo)
    public SimpleDraweeView logo;
    @BindViews({R.id.baojia_edit_load_ll, R.id.baojia_edit_unload_ll, R.id.baojia_edit_other_ll})
    public List<LinearLayout> ll;

    @BindView(R.id.baojia_yunshu_carinfo)
    public TextView carinfo;
    @BindView(R.id.baojia_yunshu_carinfo2)
    public TextView carinfo2;

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
        title.setText("正在报价");
        usertype = GetUserInfoUtils.getUserType(BaojiaEditActivity.this);
        bean = (ToSrcDetailBean) getIntent().getSerializableExtra("srcdetail");
        logo.setImageURI(Uri.parse(bean.getAvatarAddress()));
        mText.get(3).setText("货主: " + bean.getOwnername());
        ll.get(2).setVisibility(View.GONE);
        if (bean.getLoad().equals("0")) {
            ll.get(0).setVisibility(View.GONE);
        }
        if (bean.getUnload().equals("0")) {
            ll.get(1).setVisibility(View.GONE);
        }
        if (usertype.equals("2")) {
            /*carinfo.setText(bean.getTruckno());
            carinfo2.setText(bean.getTrucktype() + "/" + bean.getTrucklength());*/
            carinfo.setEnabled(false);
            getCarTeam(initCarJsonData());
        }
        textWatch();
        carinfo.setOnClickListener(this);

    }

    private String initJsonData() {
        yunshu = mEdit.get(0).getText().toString();
        needPay = mText.get(2).getText().toString();
        other = mEdit.get(3).getText().toString();
        zhuang = mEdit.get(1).getText().toString();
        xie = mEdit.get(2).getText().toString();
        truckno = carinfo.getText() + "";
        String carinfos = carinfo2.getText() + "";

        Map<String, String> map = new HashMap<String, String>();
        sum = mText.get(5).getText().toString();
        userguid = ToolsUtils.getString(BaojiaEditActivity.this, Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(BaojiaEditActivity.this, Constant.MOBILE, "");
        key = ToolsUtils.getString(BaojiaEditActivity.this, Constant.KEY, "");
        if (carinfos.isEmpty()){
            ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"请选择一辆车进行报价");
        }else {
            String[] infos = carinfos.split("/");
            map.put("GUID", userguid);
            map.put(Constant.MOBILE, mobile);
            map.put(Constant.KEY, key);
            map.put("ownerguid", bean.getOwnerguid());
            map.put("billsGUID", bean.getBillsGUID());
            map.put("driverGUID", bean.getDriverGUID());
            map.put("drivername", bean.getDrivername());
            map.put("companyGUID", bean.getCompanyGUID());
            map.put("company", bean.getCompany());

            map.put("cargoGUID", bean.getBillsGUID());
            map.put("imforfeeM", "0");
            map.put("priceM", yunshu);
            map.put("otherfeeM", "0");
            map.put("totalchargeM", sum);
            map.put("feeremarkM", "");

            map.put("price", "0");
            map.put("loadfee", "0");
            map.put("unloadfee", "0");
            map.put("otherfee", "0");
            map.put("totalcharge", "0");
            map.put("feeremark", "");

            map.put("truckno", truckno);
            map.put("trucklength", infos[1]);
            map.put("trucktype", infos[0]);

            if (bean.getLoad().equals("1")) {
                //ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"zhuang:"+zhuang);
                if (zhuang.equals("")){
                    //ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"请输入装车费");
                    map.put("loadfeeM", "0");
                }else {
                    map.put("loadfeeM", zhuang);
                }
            } else {
                map.put("loadfeeM", "0");
            }
            if (bean.getUnload().equals("1")) {
                //ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"xie:"+xie);
                if (xie.equals("")){
                    /*ToolsUtils.getInstance().toastShowStr(BaojiaEditActivity.this,"请输入卸车费");*/
                    map.put("unloadfeeM","0");
                }else {
                    map.put("unloadfeeM", xie);
                }
            } else {
                map.put("unloadfeeM", "0");
            }
        }

        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 执行提交报价的动作
     */
    private void addBaojia(String json) {
        RetrofitUtils.getRetrofitService()
                .AddBaojia("Prices", Config.ADD_BAOJIA, json)
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
                        Log.e("baojia222", getSRCBean.getErrorMsg());
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
        new CommomDialog(BaojiaEditActivity.this, R.style.dialog, "您确定要对这个货源进行报价吗??", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
                if (confirm){
                    Log.e("Baojia", initJsonData());
                    addBaojia(initJsonData());
                    //initJsonData();
                }
            }
        }).setTitle("提示").show();

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
        mEdit.get(2).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showTotalPrice();
            }
        });
        mEdit.get(3).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if ((Float.parseFloat(mEdit.get(1).getText() + "") + "").equals("")) {
                    String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(2).getText() + "") +
                            Float.parseFloat(mEdit.get(3).getText() + "") + "";
                }
                if ((Float.parseFloat(mEdit.get(2).getText() + "") + "").equals("")) {
                    String sum = Float.parseFloat(mEdit.get(0).getText() + "") + Float.parseFloat(mEdit.get(3).getText() + "") + "";
                }
                mText.get(5).setText(sum);*/
                showTotalPrice();
            }
        });
    }
    private void showTotalPrice()
    {
        String price1 = mEdit.get(0).getText() + "";
        String price2 = mEdit.get(1).getText() + "";
        String price3 = mEdit.get(2).getText() + "";
        String sum= "";
        if (price1.equals("")&&price2.equals("")&&price3.equals(""))
        {
            sum =  "0.00";
        }else if (price2.equals("") && price3.equals("")){
            sum =  Float.parseFloat(price1)+"";
        }else if (price3.equals("")&& price2.equals("")){
            sum =  Float.parseFloat(price1) +  "";
        }else if (price1.equals("") && price2.equals("")){
            sum = Float.parseFloat(price3)+"";
        }else if (price1.equals("") && price3.equals("")){
            sum = Float.parseFloat(price2)+"";
        }else if (price2.equals("") && price3.equals("")){
            sum = Float.parseFloat(price1)+"";
        }else if (price3.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price2)+"";
        }else if (price2.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price3)+"";
        }else if (!price1.equals("") && !price2.equals("") && !price3.equals("")){
            sum = Float.parseFloat(price1)+Float.parseFloat(price3)+Float.parseFloat(price2)+"";
        }

        mText.get(5).setText(sum);
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
        if (usertype.equals("2"))
        {
            map.put("driverGUID", guid);
        }else {
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
                        if (usertype.equals("2")){
                            CarTeamBean.DataBean data = carTeamBean.getData().get(0);
                            carinfo.setText(data.getTruckno()+"");
                            carinfo2.setText(data.getTrucktype()+"/"+data.getTrucklength());
                        }else {
                            carList.clear();
                            int sizes = carTeamBean.getData().size();
                            for (int i=0;i < sizes;i++){
                                if ((carTeamBean.getData().get(i).getVtruck()).equals("9")){
                                    Log.e("iiiisize",i+"");
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
        contentView = getLayoutInflater().inflate(R.layout.find_poplist, null);
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
        ListView popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
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
