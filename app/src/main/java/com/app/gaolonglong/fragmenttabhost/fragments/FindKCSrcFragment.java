package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddReleaseActivity;
import com.app.gaolonglong.fragmenttabhost.activities.FindDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.FIndRoutePopAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.FindKCPopAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.FindSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ReleaseBean;
import com.app.gaolonglong.fragmenttabhost.bean.RouteListBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;

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
 * Created by yanqi on 2017/9/14.
 */

public class FindKCSrcFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    @BindView(R.id.find_kc_rlv)
    public RecyclerView rlv;



    @BindViews({R.id.find_kc_selectkc,R.id.find_kc_addkc})
    public List<TextView> mText;

    @BindView(R.id.find_kc_refresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.find_kc_parent)
    public LinearLayout parent;

    private boolean isRenzheng;
    private PopupWindow popMenu;
    private ListView popListView;
    private WindowManager.LayoutParams params;

   /* @OnClick(R.id.find_kc_bt)
    public void add() {
        startActivity(new Intent(getContext(), AddReleaseActivity.class));
    }*/

    @BindView(R.id.find_kc_main)
    public LinearLayout main;

    @BindView(R.id.find_kc_empty)
    public EmptyLayout empty;

    private View mRootView;
    private List<ReleaseBean.DataBean> kcList;
    private List<GetSRCBean.DataBean> kcSrcList;
    private String guid;
    private String mobile;
    private String key;
    private FindSrcAdapter adapter;
    //private List<RouteListBean.DataBean> popList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.find_kc_src, container, false);
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

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        initView();
        initPopWindow();
    }

    private void initView() {

        mText.get(0).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        isRenzheng = GetUserInfoUtils.isRenzheng(getContext());
        kcList = new ArrayList<ReleaseBean.DataBean>();
        kcSrcList = new ArrayList<GetSRCBean.DataBean>();
        guid = ToolsUtils.getString(getContext(), Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(getContext(), Constant.MOBILE, "");
        key = ToolsUtils.getString(getContext(), Constant.KEY, "");

        adapter = new FindSrcAdapter(getContext(), kcSrcList);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rlv.setLayoutManager(manager);
        rlv.setAdapter(adapter);
        getKC();
        adapter.setOnItemClickListener(new FindSrcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean) {
                if (isRenzheng) {
                    Intent intent = new Intent(getContext(), FindDetailActivity.class);
                    intent.putExtra("findSrc", bean);
                    startActivity(intent);
                } else {
                    ToolsUtils.getInstance().toastShowStr(getContext(), "请先通过认证");
                }
            }
        });
        adapter.setOnFindClickListener(new FindSrcAdapter.OnFindClickListener() {
            @Override
            public void onFindClick(int position, String tel) {
                if (isRenzheng) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + kcSrcList.get(position).getOwnerphone()));
                    startActivity(intent);
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

    private Map<String, String> map = new HashMap<String, String>();

    private String initJsonData(String kcGuid) {
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        if (!kcGuid.equals("")) {
            map.put("truckplansGUID", kcGuid);
        }
        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 获取空程的货源
     *
     * @param json
     */
    private void getKCData(String json) {
        RetrofitUtils.getRetrofitService()
                .getSRCWithFromside(Constant.MYINFO_PAGENAME, Config.SRC_KONGCHENG, json)
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
                        Log.e("KCSFragment", getSRCBean.getErrorMsg());
                        rlv.removeAllViews();
                        kcSrcList.clear();
                        kcSrcList.addAll(getSRCBean.getData());


                        if (kcSrcList.size() == 0){
                            main.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            empty.setErrorImag(R.drawable.nosrc,"没有与您空程匹配的货源");
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 获取空程列表
     *
     * @param
     */
    private void getKC() {
        Map<String, String> map = new HashMap<>();
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        RetrofitUtils.getRetrofitService()
                .getFabuRelease(Constant.MYINFO_PAGENAME, Config.RELEASE_FABU, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReleaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // ToolsUtils.getInstance().toastShowStr(getContext(), e.getMessage());
                    }

                    @Override
                    public void onNext(ReleaseBean releaseBean) {

                        kcList.addAll(releaseBean.getData());
                        if (kcList.size() == 0){
                            main.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            empty.setErrorImag(R.drawable.nokongcheng,"您还没有添加空程计划哦");
                        }else {
                            main.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                            Log.e("getKCSGUID", initJsonData(releaseBean.getData().get(0).getTruckplansGUID()));
                            getKCData(initJsonData(releaseBean.getData().get(0).getTruckplansGUID()));
                        }
                    }
                });
    }
    /**
     * 初始化popwindow
     */
    private void initPopWindow() {
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.find_poplist, null);
        popMenu = new PopupWindow(contentView,
               ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(dw);
        popMenu.setFocusable(true);
        popMenu.setTouchable(true);
        popMenu.setAnimationStyle(R.style.mypopwindow_anim_style);
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        popListView.setOnItemClickListener(this);
    }

    private void showPop(List<ReleaseBean.DataBean> l) {
        kcList = l;
       /*Popadapter = new SimpleAdapter(getContext(),popList, R.layout.item_listview_popwin,
                new String[]{"name"},new int[]{R.id.listview_popwind_tv});*/
        FindKCPopAdapter popAdapter = new FindKCPopAdapter(getContext(),l);
        popListView.setAdapter(popAdapter);
        popMenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popMenu.showAsDropDown(mText.get(3),0,10);
        params = getActivity().getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.find_kc_selectkc:
                showPop(kcList);
                break;
            case R.id.find_kc_addkc:
                startActivity(new Intent(getContext(),AddReleaseActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
