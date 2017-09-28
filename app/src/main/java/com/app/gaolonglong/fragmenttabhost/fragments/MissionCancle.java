package com.app.gaolonglong.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.MissionListAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.MissionListBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.mission_cancle,container,false);
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
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        list = new ArrayList<>();
        adapter = new MissionListAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        getList(initJsonData());
    }

    private void getList(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getMissionList(Constant.MYINFO_PAGENAME, Config.MISSION_CANCEL,json)
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

                    }
                });
    }
    private String initJsonData()
    {
        String guid = ToolsUtils.getString(getContext(),Constant.LOGIN_GUID,"");
        String mobile = ToolsUtils.getString(getContext(),Constant.MOBILE,"");
        String key = ToolsUtils.getString(getContext(),Constant.KEY,"");
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        return JsonUtils.getInstance().getJsonStr(map);
    }
}
