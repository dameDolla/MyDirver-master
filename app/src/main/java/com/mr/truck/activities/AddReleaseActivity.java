package com.mr.truck.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.adapter.BaojiaPopAdapter;
import com.mr.truck.bean.CarTeamBean;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by yanqi on 2017/8/7.
 */

public class AddReleaseActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private View contentView;
    private PopupWindow popMenu;

    private int flag = 0;

    @BindViews({R.id.top_title, R.id.car_num,
            R.id.car_type, R.id.car_status,
            R.id.release_backtime_tv, R.id.release_rl_emptytime_tv,
            R.id.release_start_tv, R.id.release_finish_tv})
    public List<TextView> mText;

    @BindViews({R.id.import_weight, R.id.import_tiji,
            R.id.import_baojia, R.id.et_leave_message})
    public List<EditText> mEdit;

    @BindView(R.id.fabu_now)
    public Button fabu;
    private View carcontentView;
    private PopupWindow carpopMenu;
    private BaojiaPopAdapter popadapter;
    private List<Map<String, String>> time1map;
    private List<Map<String, String>> time2map;
    private TextView timecancel;
    private TextView timetxt;
    private TextView timesure,cancel;
    private ListView timeListview1;
    private ListView timeListview2;
    private SimpleAdapter time1adapter;
    private SimpleAdapter time2adapter;

    @OnClick({R.id.title_back})
    public void back() {
        finish();
    }

    @OnClick(R.id.title_back_txt)
    public void backs() {
        finish();
    }

    @BindViews({R.id.release_begin_addr, R.id.release_finish_addr,
            R.id.release_rl_emptytime, R.id.release_rl_backtime,
            R.id.release_rl_carstatus})
    public List<RelativeLayout> mRelat;

    @BindView(R.id.add_release_carnum_ll)
    public RelativeLayout carnumll;

    @BindView(R.id.release_parent)
    public LinearLayout parent;

    @BindView(R.id.release_return_rg)
    public RadioGroup rg;

    private String car_num;
    private String carType;
    private String carStatus;
    private String weight;
    private String tiji;
    private String baojia;
    private String msg;
    private String guid;
    private String mobile;
    private String key;
    private ListView popListView;
    private SimpleAdapter adapter;
    private WindowManager.LayoutParams params;
    private List<Map<String, String>> list;
    private List<String> strs = null;
    private int GETADDR = 100;
    private String start;
    private String finish;
    private String backtime;
    private String emptyTime;
    private String status;

    private List<CarTeamBean.DataBean> carList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_return);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initView();
        initPopwindow();
        initCarPopwindow();
    }

    private void initView() {

        // ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
        mText.get(0).setText("发布空程");
        mRelat.get(0).setOnClickListener(this);
        mRelat.get(1).setOnClickListener(this);
        fabu.setOnClickListener(this);
        mRelat.get(2).setOnClickListener(this);
        mRelat.get(3).setOnClickListener(this);
        carnumll.setOnClickListener(this);

        guid = ToolsUtils.getString(AddReleaseActivity.this, Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(AddReleaseActivity.this, Constant.MOBILE, "");
        key = ToolsUtils.getString(AddReleaseActivity.this, Constant.KEY, "");

        if (GetUserInfoUtils.getUserType(AddReleaseActivity.this).equals("2")) {
            carnumll.setEnabled(false);
            getCarTeam(initCarJsonData());
        }

       /* if (GetUserInfoUtils.getUserType(AddReleaseActivity.this).equals("1"))
        {
           // mText.get(1).setText();
            carnumll.setVisibility(View.VISIBLE);
        }else {
            carnumll.setVisibility(View.GONE);
        }*/
    }

    private void submit() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                status = rb.getText().toString();
            }
        });
        car_num = mText.get(1).getText().toString();
        carType = mText.get(2).getText().toString();
        carStatus = mText.get(3).getText().toString();

        start = mText.get(6).getText().toString();
        finish = mText.get(7).getText().toString();
        backtime = mText.get(4).getText().toString();
        emptyTime = mText.get(5).getText().toString();
        //status = mText.get(2).getText().toString();

        weight = mEdit.get(0).getText().toString();
        tiji = mEdit.get(1).getText().toString();
        baojia = mEdit.get(2).getText().toString();
        msg = mEdit.get(3).getText().toString();


        if (TextUtils.isEmpty(carType)){
            ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this, "请填写车辆信息");
            return;
        }
        if (TextUtils.isEmpty(finish)){
            ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this, "请选择目的地");
            return;
        }
        if (TextUtils.isEmpty(start)){
            ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this, "请选择始发地");
            return;
        }
        if (TextUtils.isEmpty(baojia))
        {
            ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this, "请填写您的报价");
            return;
        }
        JSONObject mJson = new JSONObject();
        try {
            mJson.put("GUID", guid);
            mJson.put(Constant.MOBILE, mobile);
            mJson.put(Constant.KEY, key);


            mJson.put("SurplusTon", weight + "吨");
            mJson.put("TransportOffer", baojia + "元");
            mJson.put("SurplusPower", tiji + "方");
            mJson.put("MyMessage", msg);
            mJson.put("fromSite", start);
            mJson.put("toSite", finish);
            mJson.put("emptytime", emptyTime);//计划返程时间
            mJson.put("backtime", backtime);
            mJson.put("boardingtime", backtime);
            if (!TextUtils.isEmpty(carType)) {
                String[] cartype = carType.split("/");
                mJson.put("trucklength", cartype[1]);
                mJson.put("trucktype", cartype[0]);
                mJson.put("truckno", car_num);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtils.getRetrofitService()
                .addRelease(Constant.MYINFO_PAGENAME, Config.ADD_RELEASE, mJson.toString())
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
                        ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this, getCodeBean.getErrorMsg());
                        setResult(2);
                        AddReleaseActivity.this.finish();
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

    private void showPop(List<Map<String, String>> l, int i) {

        list = l;
        List<String> time1 = new ArrayList<>();
        List<String> time2 = new ArrayList<>();


        String[] s = {"上午", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00",};
        String[] s2 = {"下午", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00",};
        for (String x : s) {
            time1.add(x);
        }
        for (String y : s2) {
            time2.add(y);
        }
        time1map = initPopData(time1);
        time2map = initPopData(time2);
        timecancel = (TextView) contentView.findViewById(R.id.find_time_cancel);
        timetxt = (TextView) contentView.findViewById(R.id.find_time_timetxt);
        timesure = (TextView) contentView.findViewById(R.id.find_time_sure);
        cancel = (TextView) contentView.findViewById(R.id.find_time_cancel);
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        timeListview1 = (ListView) contentView.findViewById(R.id.find_time_listview1);
        timeListview2 = (ListView) contentView.findViewById(R.id.find_time_listview2);
        popListView.setOnItemClickListener(this);
        timeListview1.setOnItemClickListener(this);
        timeListview2.setOnItemClickListener(this);
        adapter = new SimpleAdapter(AddReleaseActivity.this, list, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        time1adapter = new SimpleAdapter(AddReleaseActivity.this, time1map, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        time2adapter = new SimpleAdapter(AddReleaseActivity.this, time2map, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        popListView.setAdapter(adapter);
        timeListview1.setAdapter(time1adapter);
        timeListview2.setAdapter(time2adapter);
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
        timesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selecttime = timetxt.getText().toString();
                if (selecttime.length() != 17){
                    ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this,"请选择具体的日期时间");
                    return;
                }
                popMenu.dismiss();

                if (flag == 3) {
                    mText.get(4).setText(selecttime);

                } else if (flag == 2) {
                    mText.get(5).setText(selecttime);

                }
                //getSrcFromside(initJsonData(flag, addrs, "", ""));
                //ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this,flag+"");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popMenu.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.release_begin_addr:
                flag = 4;

                 startActivityForResult(new Intent(AddReleaseActivity.this,AddressThressActivity.class),GETADDR);
                break;
            case R.id.release_finish_addr:
                flag = 5;
                /*addrDialogs = new BottomDialog(AddReleaseActivity.this);
                addrDialogs.setOnAddressSelectedListener(this);
                addrDialogs.show();*/
                 startActivityForResult(new Intent(AddReleaseActivity.this,AddressThressActivity.class),200);
                break;
            case R.id.fabu_now:
                submit();
                break;
            case R.id.release_rl_backtime:
                flag = 3;
                strs = new ArrayList<String>();
                showPop(initPopData(ToolsUtils.get7date()), flag);
                break;
            case R.id.release_rl_emptytime:
                flag = 2;

                showPop(initPopData(ToolsUtils.get7date()), flag);
                break;

            case R.id.add_release_carnum_ll:
                showCarPop();
                break;

        }
        // popMenu.dismiss();
    }

    String time1 = "";
    String time2 = "";
    String time3 = "";

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // popMenu.dismiss();
        String str = list.get(i).get("name");

        switch (adapterView.getId()) {
            case R.id.find_pop_listview:
                if (!TextUtils.isEmpty(time1)) {
                    time2 = "";
                    time3 = "";
                    time1 = list.get(i).get("name");
                } else {
                    time1 = list.get(i).get("name");
                }
                break;
            case R.id.find_time_listview1:
                if (!TextUtils.isEmpty(time2) || !TextUtils.isEmpty(time3)) {
                    time2 = "";
                    time3 = "";
                    time2 = time1map.get(i).get("name");
                } else {
                    time2 = time1map.get(i).get("name");
                }
                break;
            case R.id.find_time_listview2:
                if (!TextUtils.isEmpty(time2) || !TextUtils.isEmpty(time3)) {
                    time2 = "";
                    time3 = "";
                    time3 = time2map.get(i).get("name");
                } else {
                    time3 = time2map.get(i).get("name");
                }
                break;
        }

        timetxt.setText(time1 + " " + time2 + " " + time3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String start = data.getStringExtra("address");
        //String finish = data.getStringExtra("finish");
        if (requestCode == 100 ) {
            mText.get(6).setText(start);
            //ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this,start);
        } else if (requestCode == 200 ) {
            mText.get(7).setText(start);
        }

    }

    private String initCarJsonData() {
        String guid = GetUserInfoUtils.getGuid(AddReleaseActivity.this);
        String mobile = GetUserInfoUtils.getMobile(AddReleaseActivity.this);
        String key = GetUserInfoUtils.getKey(AddReleaseActivity.this);
        String companyguid = GetUserInfoUtils.getCompanyGuid(AddReleaseActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        if (GetUserInfoUtils.getUserType(AddReleaseActivity.this).equals("2")) {
            map.put("driverGUID", guid);
        } else {
            map.put("companyGUID", companyguid);
        }
        map.put("companyGUID", companyguid);

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
                        Log.e("addrelease", carTeamBean.getErrorCode() + "--" + carTeamBean.getErrorMsg());
                        if (GetUserInfoUtils.getUserType(AddReleaseActivity.this).equals("2")) {

                            CarTeamBean.DataBean data = carTeamBean.getData().get(0);
                            mText.get(1).setText(data.getTruckno() + "");
                            mText.get(2).setText(data.getTrucktype() + "/" + data.getTrucklength());

                        } else {
                            Log.e("addRelease", carTeamBean.getErrorCode() + "--" + carTeamBean.getErrorMsg());
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

    private void initCarPopwindow() {
        //initPopData();
        carcontentView = getLayoutInflater().inflate(R.layout.text_listview_item, null);
        carpopMenu = new PopupWindow(carcontentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        carpopMenu.setOutsideTouchable(true);
        carpopMenu.setBackgroundDrawable(dw);
        carpopMenu.setFocusable(true);
        carpopMenu.setTouchable(true);
        carpopMenu.setAnimationStyle(R.style.mypopwindow_anim_style);

    }


    private void showCarPop() {
        //list = l;
        ListView popListView = (ListView) carcontentView.findViewById(R.id.text_item_listview);
        popListView.setOnItemClickListener(new MyItemclick());
        popadapter = new BaojiaPopAdapter(AddReleaseActivity.this, carList);

        popListView.setAdapter(popadapter);
        getCarTeam(initCarJsonData());
        carpopMenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        carpopMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private class MyItemclick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mText.get(1).setText(carList.get(i).getTruckno() + "");
            mText.get(2).setText(carList.get(i).getTrucktype() + "/" + carList.get(i).getTrucklength());
            carpopMenu.dismiss();
        }
    }


}
