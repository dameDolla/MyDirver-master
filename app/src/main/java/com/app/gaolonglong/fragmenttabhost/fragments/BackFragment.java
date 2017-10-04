package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddReleaseActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.ReleaseAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.LoadingDialog;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.app.gaolonglong.fragmenttabhost.view.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/2.
 */

public class BackFragment extends Fragment implements View.OnClickListener{
    private View mRootView;

    @BindView(R.id.rl_toaddrelease)
    public RelativeLayout rl;

    @BindViews({R.id.rcv_have_fabu,R.id.rcv_have_cancle})
    public List<RecyclerView> rcv;

    @BindView(R.id.back_empty_view)
    public EmptyLayout emptyLayout;

    private String guid;
    private String mobile;
    private String key;
    private LoadingDialog dialog;
    private  List<ReleaseBean.DataBean> list = new ArrayList<ReleaseBean.DataBean>();
    private  List<ReleaseBean.DataBean> canclelist = new ArrayList<ReleaseBean.DataBean>();
    private ReleaseAdapter adapter;
    private JSONObject mJson;
    private int positon = 0;
    private ReleaseAdapter cancleAdapter;


    @OnClick(R.id.rl_toaddrelease)
    public void release()
    {
        String usertype = GetUserInfoUtils.getUserType(getContext());
        if (ToolsUtils.getInstance().isLogin(getContext()))
        {
            if(usertype.equals("3"))
            {
                new CommomDialog(getContext(),"对不起,您的权限不够").setTitle("友情提示").show();
            }
            else
            {
                startActivityForResult(new Intent(getActivity(),AddReleaseActivity.class),1);
            }
        }
        else {
            ToolsUtils.getInstance().toastShowStr(getContext(),"请先登录");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.release_list,container,false);
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
       // init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //getData();
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init()
    {
        initView();
        getData();
        getCancleData();

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
        rcv.get(0).setNestedScrollingEnabled(false);
        rcv.get(0).setLayoutManager(manager);
        rcv.get(0).addItemDecoration(new RecycleViewDivider(getContext(),LinearLayoutManager.VERTICAL));
        rcv.get(0).setAdapter(adapter);

        rcv.get(1).setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.get(1).setAdapter(cancleAdapter);
        rcv.get(1).addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));

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
                    cancle(mJson.toString());
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
                       // Log.e("backfragment",releaseBean.getErrorCode());
                        //ToolsUtils.getInstance().toastShowStr(getContext(),releaseBean.getErrorMsg());
                        dialog.dismiss();
                        list.clear();
                        list.addAll(releaseBean.getData());
                        adapter.notifyDataSetChanged();
                        if (list.size() == 0 )
                        {
                            emptyLayout.setErrorType(EmptyLayout.NODATA);
                        }
                        else{
                            emptyLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }
    private void getCancleData()
    {
        RetrofitUtils.getRetrofitService()
                .getFabuRelease(Constant.MYINFO_PAGENAME, Config.RELEASE_CANCEL,mJson.toString())
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
                        //ToolsUtils.getInstance().toastShowStr(getContext(),releaseBean.getErrorMsg());
                        if(releaseBean.getData().size() == 0)
                        {

                        }
                        else {
                            canclelist.clear();
                            canclelist.addAll(releaseBean.getData());
                            cancleAdapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();


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
                                adapter.notifyItemRemoved(positon);
                                getCancleData();
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
    private void cancle(String json)
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
                           getCancleData();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }
}
