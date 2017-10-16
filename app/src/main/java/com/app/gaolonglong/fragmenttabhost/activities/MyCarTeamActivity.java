package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.CarTeamAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
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

public class MyCarTeamActivity extends BaseActivity implements View.OnClickListener{

    @BindViews({R.id.top_title})
    public List<TextView> mText;
    private CarTeamAdapter adapter;

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
        init();
    }
    private void init()
    {
        initView();

    }
    private void initView()
    {

        icon.setVisibility(View.VISIBLE);
        icon.setOnClickListener(this);
        mText.get(0).setText("车队信息");
        adapter = new CarTeamAdapter(MyCarTeamActivity.this,list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(MyCarTeamActivity.this);
        gv.setLayoutManager(manager);
        gv.setAdapter(adapter);

        //gv.setAdapter(adapter);
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                getCarTeam(initJsonData());
            }
        });

        adapter.setCarteamClick(new CarTeamAdapter.carteamClick() {
            @Override
            public void OnCarteamClick(String truckguid) {
                delCar(truckguid);
            }
        });

        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(null);

            }
        });


    }
    private String initJsonData()
    {
        String guid = GetUserInfoUtils.getGuid(MyCarTeamActivity.this);
        String mobile = GetUserInfoUtils.getMobile(MyCarTeamActivity.this);
        String key = GetUserInfoUtils.getKey(MyCarTeamActivity.this);

        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("companyGUID",guid);

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void getCarTeam(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getCarTeamList(Constant.TRUCK_PAGENAME, Config.GETTRUCKS,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarTeamBean>() {
                    @Override
                    public void onCompleted() {
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("carteamerror",e.getMessage());
                    }

                    @Override
                    public void onNext(CarTeamBean carTeamBean) {
                        Log.e("CarTEM",carTeamBean.getData().size()+"");
                            list.clear();
                            list.addAll(carTeamBean.getData());
                            adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);

                    }
                });
    }

    /**
     * 删除车辆信息
     */
    private void delCar(String truck)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(MyCarTeamActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(MyCarTeamActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(MyCarTeamActivity.this));
        map.put("TrucksGUID",truck);
        RetrofitUtils.getRetrofitService()
                .delCar(Constant.TRUCK_PAGENAME,Config.DELCAR,JsonUtils.getInstance().getJsonStr(map))
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
                        ToolsUtils.getInstance().toastShowStr(MyCarTeamActivity.this,getCodeBean.getErrorMsg());
                        onCreate(null);
                    }
                });
    }
    private void addCars()
    {
        startActivity(new Intent(MyCarTeamActivity.this,AddCarActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_right_icon:
                addCars();
                break;
        }
    }
}
