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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddRouteActivity;
import com.app.gaolonglong.fragmenttabhost.activities.FindDetailActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.FIndRoutePopAdapter;
import com.app.gaolonglong.fragmenttabhost.adapter.FindSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
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
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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

public class FindRouteSrcFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View mRootView;
    private List<GetSRCBean.DataBean> list;
    private FindSrcAdapter srcAdapter;
    private View contentView;
    private PopupWindow popMenu;
    private SimpleAdapter Popadapter;
    private WindowManager.LayoutParams params;
    private List<RouteListBean.DataBean> popList;
    private String guid;
    private String mobile;
    private String key;

    @BindView(R.id.find_route_rlv)
    public RecyclerView rlv;

    @BindView(R.id.find_route_parent)
    public LinearLayout parent;

    @BindViews({R.id.find_route_origin, R.id.find_route_destination, R.id.find_route_cartype, R.id.find_route_changeroute})
    public List<TextView> mText;

    @BindView(R.id.find_route_main)
    public LinearLayout main;

    @BindView(R.id.find_route_empty)
    public EmptyLayout empty;


    @BindView(R.id.find_route_refresh)
    public SwipeRefreshLayout refresh;
   /* @OnClick(R.id.find_route_bt)
    public void addRoute()
    {
        startActivity(new Intent(getContext(), AddRouteActivity.class));
    }*/

    private ListView popListView;
    private FIndRoutePopAdapter popAdapter;
    private boolean isRenzheng;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.find_route_src, container, false);
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
        // init();
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
        isRenzheng = GetUserInfoUtils.isRenzheng(getContext());
        rlv.setVisibility(View.VISIBLE);
        list = new ArrayList<GetSRCBean.DataBean>();
        routeList = new ArrayList<RouteListBean.DataBean>();
        guid = GetUserInfoUtils.getGuid(getContext());
        mobile = GetUserInfoUtils.getMobile(getContext());
        key = GetUserInfoUtils.getKey(getContext());

        mText.get(3).setOnClickListener(this);


        srcAdapter = new FindSrcAdapter(getContext(), list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rlv.setLayoutManager(manager);
        rlv.setAdapter(srcAdapter);
        rlv.setNestedScrollingEnabled(false);

        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                getRouteData(initJsonData("", "", ""));
            }
        });
        srcAdapter.setOnItemClickListener(new FindSrcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean) {
                if (isRenzheng){
                    Intent intent = new Intent(getContext(), FindDetailActivity.class);
                    intent.putExtra("findSrc",bean);
                    startActivity(intent);
                }else {
                    ToolsUtils.getInstance().toastShowStr(getContext(),"请先通过认证");
                }
            }
        });
        srcAdapter.setOnFindClickListener(new FindSrcAdapter.OnFindClickListener() {
            @Override
            public void onFindClick(int position, String tel) {
                if (isRenzheng){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list.get(position).getOwnerphone()));
                    startActivity(intent);
                }else {
                    ToolsUtils.getInstance().toastShowStr(getContext(),"请先通过认证");
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

    private String initJsonData(String fromSite, String toSite, String carType) {
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        if (!fromSite.equals("")) {
            map.put("fromSite", fromSite);
        } else {
            map.put("fromSite", "深圳市");
        }
        if (!toSite.equals("")) {
            map.put("toSite", toSite);
        }
        if (!carType.equals("")) {
            map.put("TruckType", carType);
        }

        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 获取货源数据
     *
     * @param json
     */
    private void getSrcData(String json) {
        RetrofitUtils.getRetrofitService()
                .getSRCWithFromside(Constant.MYINFO_PAGENAME, Config.SRC_ROUTE, json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetSRCBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        //ToolsUtils.getInstance().toastShowStr(getContext(),e.getMessage());
                       // Log.e("888888888888888888888", e.getMessage());
                    }

                    @Override
                    public void onNext(GetSRCBean getSRCBean) {
                        // rlv.removeAllViews();
                       //Log.e("route",getSRCBean.getErrorCode());
                        rlv.removeAllViews();
                        list.clear();
                        list.addAll(getSRCBean.getData());

                        if (list.size() == 0){
                            main.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            empty.setErrorImag(R.drawable.nosrc,"暂无与您订阅线路匹配的货源信息");
                        }else {
                            main.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                            srcAdapter.notifyDataSetChanged();
                        }


                    }
                });
    }

    /**
     * 获取订阅线路
     *
     * @param json
     */
    private List<RouteListBean.DataBean> routeList;

    private void getRouteData(String json) {
        RetrofitUtils.getRetrofitService()
                .getMyRouteList(Constant.MYINFO_PAGENAME, Config.MYROUTE_LIST, json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RouteListBean>() {


                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RouteListBean routeListBean) {

                        routeList.addAll(routeListBean.getData());
                        int listsize = routeList.size();
                        // showPop(list);
                        if (listsize == 0){
                            main.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            empty.setErrorImag(R.drawable.noroute,"您还没有添加线路哦");
                        }else {
                            for (int i = 0; i < routeList.size(); i++) {
                                if (routeList.get(i).getMainLin().equals(("1"))) {
                                    mText.get(0).setText(routeList.get(i).getFromSite());
                                    mText.get(1).setText(routeList.get(i).getToSite());
                                    String json = initJsonData(routeList.get(i).getFromSite(), routeList.get(i).getToSite(), "");
                                    Log.e("routeJson", json);
                                    getSrcData(json);
                                }
                            }
                        }

                    }
                });
    }

    /**
     * 初始化popwindow
     */
    private void initPopWindow() {
        contentView = getActivity().getLayoutInflater().inflate(R.layout.find_poplist, null);
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

    private void showPop(List<RouteListBean.DataBean> l) {
        popList = l;
       /*Popadapter = new SimpleAdapter(getContext(),popList, R.layout.item_listview_popwin,
                new String[]{"name"},new int[]{R.id.listview_popwind_tv});*/
        popAdapter = new FIndRoutePopAdapter(getContext(), l);
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
        switch (view.getId()) {
            case R.id.find_route_origin:

                break;
            case R.id.find_route_destination:

                break;
            case R.id.find_route_cartype:

                break;
            case R.id.find_route_changeroute:

                showPop(routeList);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popMenu.dismiss();
        getSrcData(initJsonData(routeList.get(i).getFromSite(), routeList.get(i).getToSite(), ""));
        mText.get(0).setText(routeList.get(i).getFromSite());
        mText.get(1).setText(routeList.get(i).getToSite());

        //ToolsUtils.getInstance().toastShowStr(getContext(),initJsonData(routeList.get(i).getFromSite(),routeList.get(i).getToSite(),""));
    }
}
