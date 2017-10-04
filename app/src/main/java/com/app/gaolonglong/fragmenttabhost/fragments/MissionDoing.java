package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.MissionDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.MissionListAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.MissionDetailBean;
import com.app.gaolonglong.fragmenttabhost.bean.MissionListBean;
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

    @BindView(R.id.mission_doing_empty)
    public EmptyLayout empty;
    private MissionListAdapter adapter;

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
        String guid = GetUserInfoUtils.getGuid(getContext());
        String mobile = GetUserInfoUtils.getMobile(getContext());
        String key = GetUserInfoUtils.getKey(getContext());
        list = new ArrayList<>();
        adapter = new MissionListAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        adapter.setOnMissionItemClick(new MissionListAdapter.OnMissionItemClick() {
            @Override
            public void onMissionItemClick(View view, MissionDetailBean bean) {
                Intent intent = new Intent(getContext(), MissionDetailActivity.class);
                intent.putExtra("missionDetail",bean);
                startActivity(intent);
               // ToolsUtils.getInstance().toastShowStr(getContext(),bean.getBillsGUID()+"");
            }
        });
        adapter.setOnMissionClick(new MissionListAdapter.OnMissionClick() {
            @Override
            public void onMissionClick(int position, String missionnum, String flag) {
                if (flag.equals("cancel"))
                {
                    ToolsUtils.getInstance().toastShowStr(getContext(),missionnum);
                }else if (flag.equals("caozuo")){
                    ToolsUtils.getInstance().toastShowStr(getContext(),missionnum);
                }else if (flag.equals("tel")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+missionnum));
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
                                if (missionListBean.getData().size() == 0){
                                    empty.setErrorType(EmptyLayout.NODATA);
                                }else {
                                    empty.setVisibility(View.GONE);
                                    list.clear();
                                    list.addAll(missionListBean.getData());
                                    adapter.notifyDataSetChanged();
                                }

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

    @Override
    public void onResume() {
        super.onResume();
        onActivityCreated(null);
    }
}
