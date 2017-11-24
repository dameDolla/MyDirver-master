package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.MyWallteAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToJiaoYiDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.WallteListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.service.GetUserInfoService;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.app.gaolonglong.fragmenttabhost.view.TiXianDialog;

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

public class MyWallteActivity extends BaseActivity implements View.OnClickListener {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindViews({R.id.wallte_to_coupon,R.id.wallte_tixian,R.id.wallte_chongzhi})
    public List<LinearLayout> mLinear;

    @BindView(R.id.my_balance)
    public TextView  money;

    @BindView(R.id.mywallte_fresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.my_dongjiecount)
    public TextView dongjie;

    @BindView(R.id.my_wallte_recycle)
    public RecyclerView recyclerView;
    private MyWallteAdapter adapter;
    private TiXianDialog dialog;


    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    private List<WallteListBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallte);
        ButterKnife.bind(this);

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
        //Intent intent = new Intent(MyWallteActivity.this,GetUserInfoService.class);
        //bindService(intent,conn, Context.BIND_AUTO_CREATE);

        getInfo();
        MyLinearLayoutManager manager = new MyLinearLayoutManager(MyWallteActivity.this);
        adapter = new MyWallteAdapter(MyWallteActivity.this,list);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        getJiaoyiList();
        mText.get(0).setText("钱包");
        mLinear.get(1).setOnClickListener(this);
        mLinear.get(2).setOnClickListener(this);
        adapter.setJiaoYiItemClickListener(new MyWallteAdapter.jiaoyiItemClickListener() {
            @Override
            public void jiaoyiItemClick(ToJiaoYiDetailBean bean) {
                Intent intent = new Intent(MyWallteActivity.this,JiaoYiDetailActivity.class);
                intent.putExtra("jiaoyi",bean);
                startActivity(intent);
            }
        });

        //dialog.num.setText(GetUserInfoUtils.getBankCardNum(MyWallteActivity.this));
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

    @Override
    protected void onDestroy() {
        //this.unbindService(conn);
        super.onDestroy();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.wallte_to_coupon:
                startActivity(new Intent(MyWallteActivity.this,YouHuiQuanActivity.class));
                break;
            case R.id.wallte_tixian:
                dialog = new TiXianDialog(this, R.style.dialog,clickListener);
                dialog.show();
                break;
            case R.id.wallte_chongzhi:
                startActivity(new Intent(MyWallteActivity.this,RechargeSelectMoneyActivity.class));
                break;
        }
    }
    private void getInfo()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(MyWallteActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(MyWallteActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(MyWallteActivity.this));
        RetrofitUtils.getRetrofitService()
                .getMoneyInfo(Constant.MYINFO_PAGENAME, Config.GETMONEY, JsonUtils.getInstance().getJsonStr(map))
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
                        GetUserInfoUtils.checkKeyValue(MyWallteActivity.this,getCodeBean.getErrorCode());
                        String msg = getCodeBean.getErrorMsg();
                        String[] str = msg.split(",");
                        money.setText(str[0]);
                        dongjie.setText("冻结金额:"+str[1]+"元");
                    }
                });
    }
    private void getJiaoyiList()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(MyWallteActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(MyWallteActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(MyWallteActivity.this));
        RetrofitUtils.getRetrofitService()
                .getJiaoyiList(Constant.MYINFO_PAGENAME,Config.GETJIAOYILIST,JsonUtils.getInstance().getJsonStr(map))
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
                    public void onNext(WallteListBean getCodeBean) {
                        GetUserInfoUtils.checkKeyValue(MyWallteActivity.this,getCodeBean.getErrorCode());
                        Log.i("mywallte",getCodeBean.getErrorCode()+"--"+getCodeBean.getErrorMsg());
                        /*if (getCodeBean.getErrorCode().equals("200")){*/
                            list.clear();
                            list.addAll(getCodeBean.getData());
                            adapter.notifyDataSetChanged();
                        /*}*/
                    }
                });
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tixian_submit:
                    final String money = dialog.money.getText().toString().trim();
                    String s = "您确定要进行此操作吗？\n"+"提现申请成功后，资金会尽快转入到您的账户，请耐心等待";
                    final CommomDialog tixianDialog = new CommomDialog(MyWallteActivity.this, R.style.dialog, s, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            if (confirm){
                                tixian(money);
                            }
                        }
                    });
                    tixianDialog.show();
                    tixianDialog.setNegativeButton("再想想");
                    tixianDialog.setPositiveButton("确定提现");
                    break;
            }
        }
    };
    private void tixian(String money)
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(MyWallteActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(MyWallteActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(MyWallteActivity.this));
        map.put("WithdrawalsMoney",money);

        RetrofitUtils.getRetrofitService()
                .tiXian(Constant.MYINFO_PAGENAME,Config.TIXIAN,JsonUtils.getInstance().getJsonStr(map))
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
                        dialog.dismiss();
                        ToolsUtils.getInstance().toastShowStr(MyWallteActivity.this,getCodeBean.getErrorMsg());
                        init();
                    }
                });
    }
}
