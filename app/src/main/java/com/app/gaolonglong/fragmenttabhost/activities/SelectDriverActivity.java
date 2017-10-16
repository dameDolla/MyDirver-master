package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.DriverListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.DriverBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/10/9.
 */

public class SelectDriverActivity extends BaseActivity {
    private List<DriverBean.DataBean> list;
    private DriverListAdapter adapter;

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
    @BindView(R.id.top_title)
    public TextView title;

    @BindView(R.id.select_driver_rcv)
    public RecyclerView rcv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_driver);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("选择调度司机");
        list = new ArrayList<DriverBean.DataBean>();
        adapter = new DriverListAdapter(SelectDriverActivity.this,list);
        rcv.setAdapter(adapter);
        kjClick();
        getDriverInfo(initJsonData());
    }
    private void kjClick()
    {
        adapter.setDriverOnclick(new DriverListAdapter.DriverOnclick() {
            @Override
            public void driverOnclick(String driverguid, String flag) {
                if (flag.equals("driver_select")){
                    bindDriver();
                }else if (flag.equals("driver_jb")){

                }
            }
        });
    }
    private void bindDriver()
    {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getDriverToMission(Constant.MYINFO_PAGENAME,Config.UPDATEDRIVER,"")
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
                                Intent intent = new Intent(SelectDriverActivity.this,MissionDetailActivity.class);
                                intent.putExtra("drivername","");
                                intent.putExtra("drivertel","");
                                startActivity(intent);
                            }
                        });
            }
        });
    }
    private String  initJsonData()
    {
        String guid = GetUserInfoUtils.getGuid(SelectDriverActivity.this);
        String mobile = GetUserInfoUtils.getMobile(SelectDriverActivity.this);
        String key = GetUserInfoUtils.getKey(SelectDriverActivity.this);
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void getDriverInfo(final String json)
    {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getDriverInfo(Constant.PLATFORM_PAGENAME, Config.GETDRIVERS,json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<DriverBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(DriverBean driverBean) {
                                list.clear();
                                list.addAll(driverBean.getData());
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        });
    }
}
