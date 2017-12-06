package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.adapter.MyWallteAdapter;
import com.mr.truck.bean.ToJiaoYiDetailBean;
import com.mr.truck.bean.WallteListBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/12/1.
 */

public class SZMXActivity extends BaseActivity implements View.OnClickListener{
    private List<WallteListBean.DataBean> list = new ArrayList<>();
    private MyWallteAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_szmx);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        TextView title = (TextView) findViewById(R.id.top_title);
        TextView back_txt = (TextView) findViewById(R.id.title_back_txt);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        RecyclerView rcv = (RecyclerView) findViewById(R.id.mine_szmx_rcv);
        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) findViewById(R.id.mine_szmx_refresh);
        title.setText("收支明细");
        back.setOnClickListener(this);
        back_txt.setOnClickListener(this);
        adapter = new MyWallteAdapter(SZMXActivity.this,list);
        MyLinearLayoutManager manager  = new MyLinearLayoutManager(SZMXActivity.this);
        rcv.setLayoutManager(manager);
        rcv.setNestedScrollingEnabled(false);
        rcv.setAdapter(adapter);
        getData();
        adapter.setJiaoYiItemClickListener(new MyWallteAdapter.jiaoyiItemClickListener() {
            @Override
            public void jiaoyiItemClick(ToJiaoYiDetailBean bean) {
                Intent intent = new Intent(SZMXActivity.this,JiaoYiDetailActivity.class);
                intent.putExtra("jiaoyi",bean);
                startActivity(intent);
            }
        });
        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refresh.setRefreshing(false);
            }
        });
    }
    private void getData()
    {
        final Map<String,String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(SZMXActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(SZMXActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(SZMXActivity.this));
       /* ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {*/
                RetrofitUtils.getRetrofitService()
                        .getJiaoyiList(Constant.MYINFO_PAGENAME, Config.GETJIAOYILIST, JsonUtils.getInstance().getJsonStr(map))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<WallteListBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(WallteListBean wallteListBean) {
                                if (wallteListBean.getErrorCode().equals(Constant.NODATABUTSUCCESS)){

                                }else if (wallteListBean.getErrorCode().equals(Constant.HAVEDATAANDSUCCESS)){
                                    list.clear();
                                    list.addAll(wallteListBean.getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
       /*     }
        });*/
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
}
