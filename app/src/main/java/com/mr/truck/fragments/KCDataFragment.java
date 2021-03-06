package com.mr.truck.fragments;

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

import com.mr.truck.R;
import com.mr.truck.activities.AddReleaseActivity;
import com.mr.truck.adapter.ReleaseAdapter;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.ReleaseBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.EmptyLayout;
import com.mr.truck.view.MyLinearLayoutManager;
import com.mr.truck.view.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/11/14.
 */

public class KCDataFragment extends Fragment implements View.OnClickListener{
    private View mRootView;
    private String guid;
    private String mobile;
    private String key;
    private LoadingDialog dialog;
    private List<ReleaseBean.DataBean> list = new ArrayList<ReleaseBean.DataBean>();
    private  List<ReleaseBean.DataBean> canclelist = new ArrayList<ReleaseBean.DataBean>();

    private ReleaseAdapter adapter;
    private JSONObject mJson;
    //private int positon = 0;
    private ReleaseAdapter cancleAdapter;

    @BindView(R.id.rcv_have_fabu)
    public RecyclerView rcv;
    @BindView(R.id.release_empty)
    public EmptyLayout empty;
    @BindView(R.id.release_refresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.release_kc_main)
    public LinearLayout main;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.kc_fragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this,mRootView);
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
        initView();
        getData();
        //getCancleData();

    }
    private void initView()
    {
        // rl.setOnClickListener(this);
        //ReleaseAdapter adapter = new ReleaseAdapter()
        dialog = LoadingDialog.showDialog(getContext());
        dialog.show();
        guid = ToolsUtils.getString(getActivity(), Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(getActivity(), Constant.MOBILE,"");
        key = ToolsUtils.getString(getActivity(), Constant.KEY,"");
        mJson = new JSONObject();
        try {
            mJson.put("GUID",guid);
            mJson.put(Constant.KEY,key);
            mJson.put(Constant.MOBILE,mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ReleaseAdapter(getContext(),list);
        cancleAdapter = new ReleaseAdapter(getContext(),canclelist);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        //manager.setScrollEnabled(false);
        rcv.setNestedScrollingEnabled(false);
        rcv.setLayoutManager(manager);
        rcv.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));
        rcv.setAdapter(adapter);
        empty.setOnClickListener(this);

        cancleAdapter.TextSetOnclick(new ReleaseAdapter.TextInterface() {
            @Override
            public void onclick(int position, String flag, String id, String status) {
                if(flag.equals(Constant.RELEASE_DEL))
                {
                    try {
                        mJson.put("TruckplansGUID",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    del(mJson.toString(),"cancle",position);
                }
            }
        });
        adapter.TextSetOnclick(new ReleaseAdapter.TextInterface() {
            @Override
            public void onclick(int  position, String flag,String id,String status) {
                //positon = position;

                if(flag.equals(Constant.RELEASE_CANCLE))
                {
                    try {
                        mJson.put("TruckplanStatus","0");
                        mJson.put("TruckplansGUID",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cancle(mJson.toString(),position);
                    //ToolsUtils.getInstance().toastShowStr(getContext(),status);

                }else if (flag.equals(Constant.RELEASE_EDIT))
                {

                    ToolsUtils.getInstance().toastShowStr(getContext(),Constant.RELEASE_EDIT);
                }else if (flag.equals(Constant.RELEASE_DEL))
                {
                    try {
                        mJson.put("TruckplansGUID",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    del(mJson.toString(),"fabu",position);
                }

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
    private void getData()
    {

        RetrofitUtils.getRetrofitService()
                .getFabuRelease(Constant.MYINFO_PAGENAME, Config.RELEASE_FABU,mJson.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReleaseBean>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(ReleaseBean releaseBean) {
                        GetUserInfoUtils.checkKeyValue(getContext(),releaseBean.getErrorCode());
                        //ToolsUtils.getInstance().toastShowStr(getContext(),releaseBean.getErrorMsg());
                        dialog.dismiss();
                        list.clear();
                        list.addAll(releaseBean.getData());
                        Log.i("backfragment",releaseBean.getErrorCode()+"-"+releaseBean.getErrorMsg()+list.size()+"");
                        if (list.size() == 0)
                        {
                            empty.setVisibility(View.VISIBLE);
                            main.setVisibility(View.GONE);
                            empty.setErrorImag(R.drawable.nokongcheng,"您还没有发布空程");
                            //empty.setErrorType(EmptyLayout.NODATA);
                        }
                        else{
                            empty.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    /**
     * 删除空程
     * @param json
     */
    private void del(String json, final String flag, final int po)
    {
        // ToolsUtils.getInstance().toastShowStr(getContext(),po+"");
        RetrofitUtils.getRetrofitService()
                .delRelease(Constant.MYINFO_PAGENAME,Config.RELEASE_DEL,json)
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
                        if(getCodeBean.getErrorCode().equals("200"))
                        {
                            if(flag.equals("fabu"))
                            {
                                list.remove(po);
                                adapter.notifyItemRemoved(po);
                            }else if (flag.equals("cancle"))
                            {
                                canclelist.remove(po);
                                cancleAdapter.notifyDataSetChanged();
                            }


                        }
                    }
                });
    }

    /**
     * 取消空程计划
     * @param json
     */
    private void cancle(String json, final int positon)
    {
        RetrofitUtils.getRetrofitService()
                .cancleRelease(Constant.MYINFO_PAGENAME,Config.RELEASE_UPDATE,json)
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
                        if(getCodeBean.getErrorCode().equals("200"))
                        {
                            list.remove(positon);
                            adapter.notifyItemRemoved(positon);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.release_empty:
                if (GetUserInfoUtils.getUserType(getContext()).equals("3")){
                    ToolsUtils.getInstance().toastShowStr(getContext(),"对不起，您的权限不够");
                }else {
                    if (GetUserInfoUtils.isRenzheng(getContext())){
                        startActivityForResult(new Intent(getActivity(),AddReleaseActivity.class),1);
                    }else {
                        ToolsUtils.toRenzhengMain(getActivity());
                    }
                }
                break;
        }
    }
}
