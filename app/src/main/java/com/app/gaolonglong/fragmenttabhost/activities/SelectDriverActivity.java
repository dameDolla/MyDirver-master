package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.DriverListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.DriverBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.LoginBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
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
    private List<LoginBean.DataBean> list;
    private DriverListAdapter adapter;
    private String flags;
    private String billsGuid;

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

    @BindView(R.id.select_driver_empty)
    public EmptyLayout empty;

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
        title.setText("调度司机");
        flags = getIntent().getStringExtra("flags");
        billsGuid = getIntent().getStringExtra("missionguid");
        list = new ArrayList<LoginBean.DataBean>();
        adapter = new DriverListAdapter(SelectDriverActivity.this,list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(SelectDriverActivity.this);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        rcv.setNestedScrollingEnabled(false);
        adapter.getFlags(flags);

        kjClick();
        getDriverInfo(initJsonData());
        setResult(0,new Intent());
    }
    private void kjClick()
    {
        adapter.setDriverOnclick(new DriverListAdapter.DriverOnclick() {
            @Override
            public void driverOnclick(String driverguid, String flag,String drivername,String drivertel) {

                if (flag.equals("driver_select")){
                    Map<String,String> map = new HashMap<String, String>();
                    ToolsUtils.getInstance().toastShowStr(SelectDriverActivity.this,driverguid);
                    map.put("GUID",GetUserInfoUtils.getGuid(SelectDriverActivity.this));
                    map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(SelectDriverActivity.this));
                    map.put(Constant.KEY,GetUserInfoUtils.getKey(SelectDriverActivity.this));
                    map.put("billsGUID",billsGuid);
                    map.put("driverGUID",driverguid);
                    map.put("drivername",drivername);
                    map.put("driverphone",drivertel);
                    bindDriver(JsonUtils.getInstance().getJsonStr(map),drivername);
                }else if (flag.equals("driver_jb")){

                }
            }
        });
    }
    private void bindDriver(final String json, final String name)
    {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getDriverToMission(Constant.MYINFO_PAGENAME,Config.UPDATEDRIVER,json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GetCodeBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("binddrivererror",e.getMessage());
                            }

                            @Override
                            public void onNext(GetCodeBean getCodeBean) {
                                Log.e("binddriver",getCodeBean.getErrorCode()+"--"+getCodeBean.getErrorMsg());
                                if (getCodeBean.getErrorCode().equals("200")){
                                    Intent intent = new Intent();
                                    intent.putExtra("drivername",name);
                                    intent.putExtra("drivertel","159000000");
                                    //startActivity(intent);
                                    setResult(1,intent);
                                    finish();
                                }
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
                        .subscribe(new Subscriber<LoginBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(LoginBean driverBean) {
                                Log.e("selectDriver",driverBean.getErrorCode()+"--"+driverBean.getErrorMsg());
                                if (driverBean.getErrorCode().equals(Constant.HAVEDATAANDSUCCESS))
                                {
                                    list.clear();
                                    int size = driverBean.getData().size();
                                    if (flags.equals("mission_detail")){
                                        for (int i=0;i<size;i++){
                                            if ((driverBean.getData().get(i).getVtruename()).equals("9")){
                                                list.add(driverBean.getData().get(i));
                                            }
                                        }
                                    }else {
                                        list.addAll(driverBean.getData());
                                    }
                                    adapter.notifyDataSetChanged();
                                    Log.e("driversize",list.size()+"");
                                }
                                if (driverBean.getErrorCode().equals(Constant.NODATABUTSUCCESS))
                                {
                                    empty.setVisibility(View.VISIBLE);
                                    empty.setErrorType(EmptyLayout.NODATA);
                                }else {
                                    empty.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
}
