package com.mr.truck.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.adapter.CarTeamAdapter;
import com.mr.truck.bean.CarTeamBean;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.CommomDialog;
import com.mr.truck.view.MyLinearLayoutManager;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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
 * Created by yanqi on 2017/8/29.
 */

public class MyCarTeamActivity extends BaseActivity implements View.OnClickListener {

    @BindViews({R.id.top_title})
    public List<TextView> mText;
    private CarTeamAdapter adapter;
    private String missionguid;
    private String flag;

    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.title_back_txt)
    public void backs() {
        finish();
    }

    @BindView(R.id.carteam_gv)
    public RecyclerView gv;

    @BindView(R.id.carteam_fresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.title_right_icon)
    public ImageView icon;


    private List<CarTeamBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cargroup);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        initView();

    }

    private void initView() {
        flag = TextUtils.isEmpty(getIntent().getStringExtra("flag"))?"mine":getIntent().getStringExtra("flag");
        missionguid = getIntent().getStringExtra("missionguid");
        icon.setVisibility(View.VISIBLE);
        icon.setOnClickListener(this);
        mText.get(0).setText("车队信息");
        adapter = new CarTeamAdapter(MyCarTeamActivity.this, list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(MyCarTeamActivity.this);
        gv.setLayoutManager(manager);
        gv.setAdapter(adapter);
        gv.setNestedScrollingEnabled(false);

        //gv.setAdapter(adapter);
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                getCarTeam(initJsonData());
            }
        });
        if (!TextUtils.isEmpty(flag)) {
            adapter.setFlag(flag);
        } else {
            adapter.setFlag("");
        }
        adapter.setCarteamClick(new CarTeamAdapter.carteamClick() {
            @Override
            public void OnCarteamClick(final String trucktype, final String truckno, final String trucklength, final String truckguid, String flags) {
                //ToolsUtils.getInstance().toastShowStr(MyCarTeamActivity.this,flags);
                String str = "";
                if (flags.equals("del")) {
                    str = "您确定要删除这辆车吗?";
                    new CommomDialog(MyCarTeamActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            if (confirm) {
                                delCar(truckguid);
                            }
                        }
                    }).show();

                } else if (flags.equals("bind")) {
                    str = "您确定要调度这台车辆吗?";
                    new CommomDialog(MyCarTeamActivity.this, R.style.dialog, str, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            if (confirm) {
                                bindCar(truckno,trucktype,trucklength);
                            }
                        }
                    }).setTitle("提示").show();

                }
            }
        });

        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // onCreate(null);
                init();
            }
        });

        setResult(0,new Intent());
    }

    private String initJsonData() {
        String guid = GetUserInfoUtils.getGuid(MyCarTeamActivity.this);
        String mobile = GetUserInfoUtils.getMobile(MyCarTeamActivity.this);
        String key = GetUserInfoUtils.getKey(MyCarTeamActivity.this);
        String companyguid = GetUserInfoUtils.getCompanyGuid(MyCarTeamActivity.this);
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
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
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Log.e("carteamerror",e.getMessage());
                    }

                    @Override
                    public void onNext(CarTeamBean carTeamBean) {
                        GetUserInfoUtils.checkKeyValue(MyCarTeamActivity.this,carTeamBean.getErrorCode());
                        Log.e("CarTEM", carTeamBean.getErrorCode() + "--" + carTeamBean.getErrorMsg());
                        list.clear();
                        if (flag.equals("missiondetail")) {
                            for (int i = 0; i < carTeamBean.getData().size(); i++) {
                                if (carTeamBean.getData().get(i).getVtruck().equals("9")) {
                                    list.add(carTeamBean.getData().get(i));
                                }

                            }
                        } else if (flag.equals("mine")){
                            list.addAll(carTeamBean.getData());
                        }
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);

                    }
                });
    }

    /**
     * 删除车辆信息
     */
    private void delCar(String truck) {
        Map<String, String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(MyCarTeamActivity.this));
        map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(MyCarTeamActivity.this));
        map.put(Constant.KEY, GetUserInfoUtils.getKey(MyCarTeamActivity.this));
        map.put("TrucksGUID", truck);
        RetrofitUtils.getRetrofitService()
                .delCar(Constant.TRUCK_PAGENAME, Config.DELCAR, JsonUtils.getInstance().getJsonStr(map))
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
                        ToolsUtils.getInstance().toastShowStr(MyCarTeamActivity.this, getCodeBean.getErrorMsg());
                        init();
                    }
                });
    }

    private void addCars() {
        startActivity(new Intent(MyCarTeamActivity.this, AddCarActivity.class));
        finish();
    }

    private void bindCar(final String truckno, String trucktype, String trucklength) {
        Map<String, String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(MyCarTeamActivity.this));
        map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(MyCarTeamActivity.this));
        map.put(Constant.KEY, GetUserInfoUtils.getKey(MyCarTeamActivity.this));
        map.put("billsGUID", missionguid);
        map.put("truckno", truckno);
        map.put("trucktype", trucktype);
        map.put("trucklength", trucklength);
        RetrofitUtils.getRetrofitService()
                .bindCar(Constant.MYINFO_PAGENAME, Config.BINDCAR, JsonUtils.getInstance().getJsonStr(map))
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
                        ToolsUtils.getInstance().toastShowStr(MyCarTeamActivity.this, getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200")) {
                            Intent intent = new Intent();
                            intent.putExtra("truckno", truckno);
                            setResult(111, intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_icon:
                addCars();
                break;
        }
    }
}
