package com.app.gaolonglong.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

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
 * Created by yanqi on 2017/9/14.
 */

public class FindKCSrcFragment extends Fragment {

    @BindView(R.id.find_kc_rlv)
    public RecyclerView rlv;

    private View mRootView;
    private List<ReleaseBean.DataBean>  kcList;
    private List<GetSRCBean.DataBean> kcSrcList;
    private String guid;
    private String mobile;
    private String key;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.find_kc_src,container,false);
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

    @Override
    public void onStart() {
        super.onStart();
       // init();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init()
    {
        initView();
        //getKC(initJsonData(""));
        ToolsUtils.getInstance().toastShowStr(getContext(),"12222");
    }
    private void initView()
    {
        kcList = new ArrayList<ReleaseBean.DataBean>();
        kcSrcList = new ArrayList<GetSRCBean.DataBean>();
        guid = ToolsUtils.getString(getContext(), Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(getContext(), Constant.MOBILE,"");
        key = ToolsUtils.getString(getContext(), Constant.KEY,"");
    }
    private Map<String,String> map = new HashMap<String,String>();
    private String initJsonData(String kcGuid)
    {
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
       if (kcGuid.equals("")){
            map.put("truckplansGUID",kcGuid);
        }
        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 获取空程的货源
     * @param json
     */
    private void getKCData(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getSRCWithFromside(Constant.MYINFO_PAGENAME,Config.SRC_KONGCHENG,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetSRCBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetSRCBean getSRCBean) {
                        if (getSRCBean.getData().size() != 9)
                        {
                            kcSrcList.addAll(getSRCBean.getData());
                        }
                    }
                });
    }

    /**
     * 获取空程列表
     * @param json
     */
    private void getKC(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getFabuRelease(Constant.MYINFO_PAGENAME, Config.RELEASE_FABU,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReleaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToolsUtils.getInstance().toastShowStr(getContext(),e.getMessage());
                    }

                    @Override
                    public void onNext(ReleaseBean releaseBean) {
                        if(releaseBean.getData().size() != 0)
                        {
                            kcList.addAll(releaseBean.getData());
                            getKCData(initJsonData(releaseBean.getData().get(0).getTruckplansGUID()));
                        }
                        ToolsUtils.getInstance().toastShowStr(getContext(),releaseBean.getData().size()+"");
                    }
                });
    }
}