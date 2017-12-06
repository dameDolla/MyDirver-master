package com.mr.truck.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mr.truck.R;
import com.mr.truck.activities.MissionDetailActivity;
import com.mr.truck.adapter.MissionListAdapter;
import com.mr.truck.bean.MissionDetailBean;
import com.mr.truck.bean.MissionListBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.EmptyLayout;
import com.mr.truck.view.MyLinearLayoutManager;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/11.
 */

public class MissionCancle extends Fragment {
    private View mRootView;
    private List<MissionListBean.DataBean> list;
    private MissionListAdapter adapter;
    @BindView(R.id.mission_cancel_rcv)
    public RecyclerView rcv;

    @BindView(R.id.mission_cancel_main)
    public LinearLayout main;

    @BindView(R.id.mission_cancel_empty)
    public EmptyLayout empty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.mission_cancle, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        list = new ArrayList<>();
        adapter = new MissionListAdapter(getContext(), list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        rcv.setNestedScrollingEnabled(false);

        adapter.setOnMissionItemClick(new MissionListAdapter.OnMissionItemClick() {
            @Override
            public void onMissionItemClick(View view, MissionDetailBean bean) {
                Intent intent = new Intent(getContext(), MissionDetailActivity.class);
                intent.putExtra("missionDetail", bean);
                startActivity(intent);
                // ToolsUtils.getInstance().toastShowStr(getContext(),bean.getBillsGUID()+"");
            }
        });
        adapter.setOnMissionClick(new MissionListAdapter.OnMissionClick() {
            @Override
            public void onMissionClick(int position, String missionnum, String flag,String status) {
                if (flag.equals("cancel")) {
                    ToolsUtils.getInstance().toastShowStr(getContext(), missionnum);
                } else if (flag.equals("caozuo")) {
                    ToolsUtils.getInstance().toastShowStr(getContext(), missionnum);
                } else if (flag.equals("tel")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + missionnum));
                    startActivity(intent);
                }
            }
        });
        ThreadPoolHelp.Builder
                .cached()
                .builder()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        getList(initJsonData());
                    }
                });
    }

    private void getList(String json) {
        RetrofitUtils.getRetrofitService()
                .getMissionList(Constant.MYINFO_PAGENAME, Config.MISSION_CANCEL, json)
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
                        GetUserInfoUtils.checkKeyValue(getContext(),missionListBean.getErrorCode());
                        list.clear();
                        list.addAll(missionListBean.getData());
                        if (list.size() == 0){
                            main.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            empty.setErrorImag(R.drawable.nomission,"您没有已取消的运单哦");
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private String initJsonData() {
        String guid = GetUserInfoUtils.getGuid(getContext());
        String mobile = GetUserInfoUtils.getMobile(getContext());
        String key = GetUserInfoUtils.getKey(getContext());
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        return JsonUtils.getInstance().getJsonStr(map);
    }
}
