package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.RouteListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        getRouteListInfo();


    }

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
                        //ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,routeListBean.getErrorCode());
                        dialog.dismiss();
                        list.addAll(routeListBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.myroute_submit:
                Map<Integer,String> map = adapter.getMap();
                ToolsUtils.getInstance().toastShowStr(MyRouteListActivity.this,map.get(0));
                break;
        }
    }
}
