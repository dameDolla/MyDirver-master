package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.RouteListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by yanqi on 2017/9/1.
 */

public class MyRouteListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.top_title)
    public TextView title;

    @BindView(R.id.myroute_submit)
    public Button submit;

    @BindView(R.id.recycler_route)
    public RecyclerView recyclerView;

    @BindView(R.id.route_refresh)
    public SwipeRefreshLayout refresh;



    @BindView(R.id.myroute_parent)
    public LinearLayout parent;

    @BindView(R.id.myroutelist_empty)
    public EmptyLayout emptylayout;

    private String mobile;
    private String key;
    private String guid;
    List<RouteListBean.DataBean> list = new ArrayList<RouteListBean.DataBean>();
    private RouteListAdapter adapter;
    private LoadingDialog dialog;


    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myroute_lists);
        ButterKnife.bind(this);
        init();
    }



    @Override
    protected void onStart() {
        super.onStart();
        getRouteListInfo();
    }

    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("我的订阅线路");
        submit.setOnClickListener(this);
        guid = ToolsUtils.getString(MyRouteListActivity.this, Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(MyRouteListActivity.this, Constant.KEY,"");
        mobile = ToolsUtils.getString(MyRouteListActivity.this, Constant.MOBILE,"");
        dialog = LoadingDialog.showDialog(MyRouteListActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(MyRouteListActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new RouteListAdapter(list,MyRouteListActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        getRouteListInfo();
        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getRouteListInfo();
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });
        adapter.checkBoxCheckChange(new RouteListAdapter.CheckBoxInterface() {
            @Override
            public void change(int position, String lineGuid,Map<Integer,String> map) {

                //ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,lineGuid);
                setMain(lineGuid,position);
            }
        });


    }

    /**
     *获取线路信息
     */

    private void getRouteListInfo()
    {
        dialog.show();
        JSONObject mJson = new JSONObject();
        try {
            mJson.put("GUID",guid);
            mJson.put(Constant.KEY,key);
            mJson.put(Constant.MOBILE,mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtils.getRetrofitService()
                .getMyRouteList(Constant.MYINFO_PAGENAME, Config.MYROUTE_LIST,mJson.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteListBean>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(RouteListBean routeListBean) {
                        if(routeListBean.getData().size() == 0)
                        {
                            emptylayout.setErrorType(EmptyLayout.NODATA);
                        }else {
                            emptylayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            list.clear();
                            list.addAll(routeListBean.getData());
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    /**
     * 设置主线路
     * @param lguid
     */
    private void setMain(String lguid, final int i)
    {
        ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,i+"");
        Map<String,String> map = new HashMap<String,String>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("LinesGUID",lguid);
        String json = JsonUtils.getInstance().getJsonStr(map);
        RetrofitUtils.getRetrofitService()
                .setRouteMain(Constant.MYINFO_PAGENAME,Config.ROUTE_UPDATE,json)
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
                        ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,getSRCBean.getErrorMsg());
                        if (getSRCBean.getErrorCode().equals("200")){
                            onStart();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.myroute_submit:
                Map<Integer,String> map = adapter.getMap();
                //ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,map.get(0));
                startActivity(new Intent(MyRouteListActivity.this,AddRouteActivity.class));

                break;
        }
    }
}