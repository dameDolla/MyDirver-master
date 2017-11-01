package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.MissionDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.MissionListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CancelMissionDialog;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/11.
 */

public class MissionDoing extends Fragment {
    private View mRootView;
    private List<MissionListBean.DataBean> list;

    @BindView(R.id.mission_doing_rcv)
    public RecyclerView rcv;

    @BindView(R.id.mission_doing_main)
    public LinearLayout main;

    @BindView(R.id.mission_doing_fresh)
    public SwipeRefreshLayout fresh;

    @BindView(R.id.mission_doing_empty)
    public EmptyLayout empty;
    private MissionListAdapter adapter;
    private String guid;
    private String mobile;
    private String key;
    private CancelMissionDialog cancelDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.mission_doint,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if(parent != null)
        {
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        guid = GetUserInfoUtils.getGuid(getContext());
        mobile = GetUserInfoUtils.getMobile(getContext());
        key = GetUserInfoUtils.getKey(getContext());
        list = new ArrayList<>();
        adapter = new MissionListAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        rcv.setNestedScrollingEnabled(false);

        adapter.setOnMissionItemClick(new MissionListAdapter.OnMissionItemClick() {
            @Override
            public void onMissionItemClick(View view, MissionDetailBean bean) {
                toDetail(bean);
            }
        });
        adapter.setOnMissionClick(new MissionListAdapter.OnMissionClick() {



            @Override
            public void onMissionClick(final int position, final String missionnum, String flag, String status) {
                if (flag.equals("cancel"))
                {
                    cancelDialog = new CancelMissionDialog(getActivity(), R.style.dialog, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId())
                            {
                                case R.id.cancel_mission_dialog_cancel:
                                    String reason = cancelDialog.reason.getText().toString();
                                    cancel(missionnum,position);
                                    break;
                            }
                        }
                    });

                    cancelDialog.show();
                    if (status.equals("0")){
                        cancelDialog.status.setText("运单已生成");
                    }else if (status.equals("1")){
                        cancelDialog.status.setText("司机出发接货");
                    }


                }else if (flag.equals("caozuo")){
                   // toDetail();
                    //ToolsUtils.getInstance().toastShowStr(getContext(),missionnum);
                }else if (flag.equals("tel")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+missionnum));
                    startActivity(intent);
                }
            }
        });

        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                getList(initJsonData());
            }
        });


        fresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onActivityCreated(null);
                fresh.setRefreshing(false);
            }
        });
    }

    private void cancel(final String billsGUID, final int position){
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = new HashMap<String, String>();
                map.put("GUID",guid);
                map.put(Constant.MOBILE,mobile);
                map.put(Constant.KEY,key);
                map.put("billsGUID",billsGUID);
                RetrofitUtils.getRetrofitService()
                        .cancelMission("",Config.CANCELMISSION,JsonUtils.getInstance().getJsonStr(map))
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
                                ToolsUtils.getInstance().toastShowStr(getContext(),getCodeBean.getErrorMsg());
                                if (getCodeBean.getErrorCode().equals("200")){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }
    private void getList(final String json)
    {
                RetrofitUtils.getRetrofitService()
                        .getMissionList(Constant.MYINFO_PAGENAME, Config.MISSION_DOING,json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<MissionListBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(MissionListBean missionListBean) {
                                list.clear();
                                list.addAll(missionListBean.getData());
                                if (missionListBean.getData().size() == 0){
                                    empty.setVisibility(View.VISIBLE);
                                    main.setVisibility(View.GONE);
                                    empty.setErrorImag(R.drawable.nomission,"您还没有运单哦");
                                }else {
                                    empty.setVisibility(View.GONE);
                                    main.setVisibility(View.VISIBLE);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        });

    }
    private void toDetail(MissionDetailBean bean)
    {
        Intent intent = new Intent(getContext(), MissionDetailActivity.class);
        intent.putExtra("missionDetail",bean);
        startActivity(intent);
    }
    private String initJsonData()
    {

        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        return JsonUtils.getInstance().getJsonStr(map);
    }

    @Override
    public void onResume() {
        super.onResume();
        onActivityCreated(null);
    }
}
