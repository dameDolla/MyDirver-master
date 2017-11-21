package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.FindDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.FindSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.InvitedSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.InvitedSrcBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.app.gaolonglong.fragmenttabhost.view.RecycleViewDivider;

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



public class InvitedSrcFragment extends Fragment {
    private List<InvitedSrcBean.DataBean> invitedlist = new ArrayList<InvitedSrcBean.DataBean>();
    private View mRootView;
    private InvitedSrcAdapter invitedSrcAdapter;
    private FindSrcAdapter adapter;
    private EmptyLayout empty;
    private RecyclerView rcv;
    private LinearLayout main;

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
        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) mRootView.findViewById(R.id.invited_refresh);
        empty = (EmptyLayout) mRootView.findViewById(R.id.invited_empty);
        rcv = (RecyclerView) mRootView.findViewById(R.id.invited_rcvinvited);
        main = (LinearLayout) mRootView.findViewById(R.id.invited_fragment_main);
        invitedSrcAdapter = new InvitedSrcAdapter(getContext(), invitedlist);
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

                    }

                    @Override
                    public void onNext(InvitedSrcBean getSRCBean) {
                       // Log.i("back-fragment",getSRCBean.getErrorCode()+"-"+getSRCBean.getErrorMsg());

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
