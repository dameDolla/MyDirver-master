package com.mr.truck.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mr.truck.R;
import com.mr.truck.activities.FindDetailActivity;
import com.mr.truck.adapter.FindSrcAdapter;
import com.mr.truck.adapter.InvitedSrcAdapter;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.InvitedSrcBean;
import com.mr.truck.bean.ToSrcDetailBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.view.EmptyLayout;
import com.mr.truck.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/11/14.
 */



public class InvitedSrcFragment extends Fragment implements View.OnClickListener{
    private List<InvitedSrcBean.DataBean> invitedlist = new ArrayList<InvitedSrcBean.DataBean>();
    private View mRootView;
    private InvitedSrcAdapter invitedSrcAdapter;
    private FindSrcAdapter adapter;
    private EmptyLayout empty;
    private RecyclerView rcv;
    private LinearLayout main;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.invited_fagment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }
    private void init()
    {
        loadingDialog = LoadingDialog.showDialog(getContext());
        loadingDialog.show();
        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.invited_refresh);
        empty = (EmptyLayout) mRootView.findViewById(R.id.invited_empty);
        rcv = (RecyclerView) mRootView.findViewById(R.id.invited_rcvinvited);
        main = (LinearLayout) mRootView.findViewById(R.id.invited_fragment_main);
        invitedSrcAdapter = new InvitedSrcAdapter(getContext(), invitedlist);
        empty.setOnClickListener(this);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        //manager.setScrollEnabled(false);
        rcv.setNestedScrollingEnabled(false);
        rcv.setLayoutManager(manager);
        //rcv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        rcv.setAdapter(invitedSrcAdapter);
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                getSrcData();
            }
        });
        invitedSrcAdapter.setOnInvitedItemClickListener(new InvitedSrcAdapter.OnInvitedItemClick() {
            @Override
            public void invitedClick(View view, ToSrcDetailBean bean) {
                Intent intent = new Intent(getContext(), FindDetailActivity.class);
                intent.putExtra("findSrc", bean);
                startActivity(intent);
            }
        });
        invitedSrcAdapter.setOnInvitedClickListener(new InvitedSrcAdapter.OnInvitedClickListener() {
            @Override
            public void onInvitedClick(String id, int position) {
                cancelSrcByKc(invitedlist.get(position).getInvitationID());
            }
        });
        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.invited_empty:
                init();
                break;
        }
    }

    private void getSrcData(){
        Map<String,String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(getContext()));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(getContext()));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(getContext()));
        //Log.e("back-jsondata",JsonUtils.getInstance().getJsonStr(map));
        RetrofitUtils.getRetrofitService()
                .getSrcByKc(Constant.MYINFO_PAGENAME, Config.SELECTSRCBYKC, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InvitedSrcBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onNext(InvitedSrcBean getSRCBean) {
                        loadingDialog.dismiss();
                        Log.i("back-f ragment",getSRCBean.getErrorCode()+"-"+getSRCBean.getErrorMsg());
                        GetUserInfoUtils.checkKeyValue(getContext(),getSRCBean.getErrorCode());
                        if (getSRCBean.getErrorCode().equals("203")){
                            empty.setVisibility(View.VISIBLE);
                            main.setVisibility(View.GONE);
                            empty.setErrorImag(R.drawable.nosrc,"暂无邀请数据");
                        }else {
                            invitedlist.clear();
                            invitedlist.addAll(getSRCBean.getData());
                            empty.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);
                            invitedSrcAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
    private void cancelSrcByKc(String id)
    {
        Log.e("InvitationID",id);
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(getContext()));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(getContext()));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(getContext()));
        map.put("InvitationID",id);

        RetrofitUtils.getRetrofitService()
                .cancelSRCByKc(Constant.MYINFO_PAGENAME,Config.CANCELSRCBYKC,JsonUtils.getInstance().getJsonStr(map))
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
                        Log.e("cancel-kcsrc",getCodeBean.getErrorCode()+"-"+getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode().equals("200")){
                            onResume();
                        }
                    }
                });
    }
}
