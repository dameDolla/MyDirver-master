package com.mr.truck.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.activities.AddressActivity;
import com.mr.truck.activities.FindDetailActivity;
import com.mr.truck.activities.SelectTruckTypeActivity;
import com.mr.truck.adapter.FindSrcAdapter;
import com.mr.truck.bean.GetSRCBean;
import com.mr.truck.bean.ToSrcDetailBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.EmptyLayout;
import com.mr.truck.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/14.
 */

public class FindAllSrcFragment extends ForResultNestedCompatFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View mRootView;
    private List<GetSRCBean.DataBean> list = new ArrayList<GetSRCBean.DataBean>();
    private FindSrcAdapter adapter;
    private int SHIFADI = 99;
    private int MUDIDI = 98;
    private int TRUCKTYPE = 102;
    private int flag = 0;
    private String addrs = "";
    private View contentView;
    private PopupWindow popMenu;
    private SimpleAdapter Popadapter,time1adapter,time2adapter;
    private WindowManager.LayoutParams params;
    private List<Map<String, String>> popList;


    @BindView(R.id.find_src_rlv)
    public RecyclerView rlv;

    @BindView(R.id.find_all_parent)
    public LinearLayout parent;

    @BindView(R.id.find_all_refresh)
    public SwipeRefreshLayout refresh;

    @BindView(R.id.find_all_main)
    public LinearLayout main;

    @BindView(R.id.find_all_empty)
    public EmptyLayout empty;

    @BindViews({R.id.find_tv_origin, R.id.find_tv_destination, R.id.find_tv_cartype, R.id.find_tv_time})
    public List<TextView> mText;
    private String guid;
    private String mobile;
    private String key;
    private String location;
    private String time;
    private View popView;
    private WindowManager.LayoutParams param;
    private boolean isRenzheng;
    private String lenStr = null;
    private String typeStr = null;
    private TextView timecancel,timetxt,timesure;
    private List<Map<String, String>> time1map;
    private List<Map<String, String>> time2map;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.find_all_src, container, false);
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
        loadingDialog = LoadingDialog.showDialog(getActivity());
        loadingDialog.show();
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
    }

    private void initView() {
        isRenzheng = GetUserInfoUtils.isRenzheng(getContext());
       /* LoadingDialog loadingDialog = new LoadingDialog(getContext(),R.style.dialog_style);
        loadingDialog.show();*/

        location = ToolsUtils.getString(getContext(), Constant.CITY, "");
        mText.get(0).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        mText.get(2).setOnClickListener(this);
        mText.get(3).setOnClickListener(this);
        if (addrs.equals("")) {
            mText.get(0).setText(location);
        } else {
            // mText.get(0).setText(addrs);
        }

        adapter = new FindSrcAdapter(getContext(), list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rlv.setLayoutManager(manager);
        rlv.setAdapter(adapter);
        rlv.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new FindSrcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean) {

                if (isRenzheng) {
                    Intent intent = new Intent(getContext(), FindDetailActivity.class);
                    intent.putExtra("findSrc", bean);
                    startActivity(intent);
                } else {
                    ToolsUtils.toRenzhengMain(getActivity());

                   /*CommomDialog dialog =  new CommomDialog(getContext(), R.style.dialog, "您暂时还未通过认证，现在需要去认证吗?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm){
                                dialog.dismiss();
                                startActivity(new Intent(getContext(), RenzhengMainActivity.class));
                            }
                        }
                    });
                    dialog.setNegativeButton("取消");
                    dialog.setPositiveButton("确定");
                    dialog.show();*/
                }


            }
        });

        Log.e("json",initJsonData(flag, addrs, lenStr, typeStr));
        getSrcFromside(initJsonData(flag, addrs, lenStr, typeStr));

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

    private void initPopwindow() {
        //initPopData();
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

    }

    /**
     * @param flag 0(默认定位) 1(选择了终点) 2（选择了车辆类型） 3（选择了装车时间）
     * @return
     */

    private String initJsonData(int flag, String addr, String trucklenth, String trucktype) {
        guid = GetUserInfoUtils.getGuid(getContext());
        mobile = GetUserInfoUtils.getMobile(getContext());
        key = GetUserInfoUtils.getKey(getContext());
        Map<String, String> map = new HashMap<String, String>();

        // ToolsUtils.getInstance().toastShowStr(getContext(),addr);
        map.put("GUID", guid);
        map.put(Constant.MOBILE, mobile);
        map.put(Constant.KEY, key);
        map.put("companyGUID", ToolsUtils.getString(getContext(), Constant.COMPANYGUID, ""));

        if (flag == 0) {
            if (addr.equals("")) {
                map.put("fromSite", location);
            } else {
                if (addr.equals("全国")){
                    //map.put("fromSite", "");
                }else {
                    map.put("fromSite", addr);
                }
            }
        } else if (flag == 1) {
            if (addr.equals("全国")){
               // map.put("toSite", "");
            }else {
                map.put("toSite", addr);
            }
            map.put("fromSite",mText.get(0).getText().toString());

        } else if (flag == 2) {
            if (addr.equals("")) {
                map.put("fromSite", location);
            } else {
                map.put("fromSite", addr);
        }
            map.put("trucklength", trucklenth);
            map.put("trucktype", trucktype);
        } else if (flag == 3) {
            map.put("preloadtime", time);
        }

        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 根据始发地查找货源
     */
    private void getSrcFromside(final String json) {
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getSRCWithFromside(Constant.MYINFO_PAGENAME, Config.SRC_FROMSIDE, json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GetSRCBean>() {
                            @Override
                            public void onCompleted() {
                                loadingDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loadingDialog.dismiss();
                                //ToolsUtils.getInstance().toastShowStr(getContext(),e.getMessage());
                            }

                            @Override
                            public void onNext(GetSRCBean getSRCBean) {

                                String code = getSRCBean.getErrorCode();

                                if (code.equals("203")) {
                                    loadingDialog.dismiss();
                                    main.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    empty.setErrorImag(R.drawable.nosrc, "暂无货源信息");
                                } else {
                                    loadingDialog.dismiss();
                                    rlv.removeAllViews();
                                    list.clear();
                                    list.addAll(getSRCBean.getData());
                                    Log.e("srcsize", list.size() + "");
                                    main.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
                                Log.e("allSrc-size", list.size()+"");
                            }
                        });
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_tv_origin:
                flag = 0;
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivityForResult(intent, SHIFADI);
                /*
                addrDialog = new BottomDialog(getContext());
                addrDialog.setOnAddressSelectedListener(this);
                addrDialog.show();*/

                break;
            case R.id.find_tv_destination:
                flag = 1;
                Intent intents = new Intent(getContext(), AddressActivity.class);
                startActivityForResult(intents, MUDIDI);
                /*addrDialog = new BottomDialog(getContext());
                addrDialog.setOnAddressSelectedListener(this);
                addrDialog.show();*/
                break;
            case R.id.find_tv_cartype:
                //startActivity();
                Intent intentFortrucktype = new Intent(getContext(), SelectTruckTypeActivity.class);
                startActivityForResult(intentFortrucktype,TRUCKTYPE);
               // showCarPop();
                break;
            case R.id.find_tv_time:

                //Log.e("date-detail",ToolsUtils.get7date().toString());
                showPop(initPopData(ToolsUtils.get7date()));
                break;
        }
    }

    private List<Map<String, String>> initPopData(List<String> str) {
        List<Map<String, String>> menuData1 = new ArrayList<Map<String, String>>();
        List<String> menuStr1 = str;
        Map<String, String> map1;
        for (int i = 0, len = menuStr1.size(); i < len; ++i) {
            map1 = new HashMap<String, String>();
            map1.put("name", menuStr1.get(i));
            menuData1.add(map1);
        }
        return menuData1;
    }

    /**
     * 显示popwindow
     */
    private ListView popListView,timeListview1,timeListview2;

    private void showPop(List<Map<String, String>> l) {
        initPopwindow();
        popList = l;

        List<String> time1 = new ArrayList<>();
        List<String> time2 = new ArrayList<>();


        String[] s = {"上午","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00",};
        String[] s2 = {"下午","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00","24:00",};
        for (String x:s){
            time1.add(x);
        }
        for (String y:s2){
            time2.add(y);
        }
        time1map = initPopData(time1);
        time2map = initPopData(time2);
        timecancel = (TextView) contentView.findViewById(R.id.find_time_cancel);
        timetxt = (TextView) contentView.findViewById(R.id.find_time_timetxt);
        timesure = (TextView) contentView.findViewById(R.id.find_time_sure);
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        timeListview1 = (ListView) contentView.findViewById(R.id.find_time_listview1);
        timeListview2 = (ListView) contentView.findViewById(R.id.find_time_listview2);
        popListView.setOnItemClickListener(this);
        timeListview1.setOnItemClickListener(this);
        timeListview2.setOnItemClickListener(this);
        Popadapter = new SimpleAdapter(getContext(), popList, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        time1adapter = new SimpleAdapter(getContext(), time1map, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        time2adapter = new SimpleAdapter(getContext(), time2map, R.layout.item_listview_popwin,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        popListView.setAdapter(Popadapter);
        timeListview1.setAdapter(time1adapter);
        timeListview2.setAdapter(time2adapter);
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
        timesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selecttime = timetxt.getText().toString();
                popMenu.dismiss();
                mText.get(3).setText(selecttime);
                //getSrcFromside(initJsonData(flag, addrs, "", ""));
                //ToolsUtils.getInstance().toastShowStr(getContext(),timetxt.getText().toString());
            }
        });
    }

    @Override
    public void onActivityResultNestedCompat(int requestCode, int resultCode, Intent data) {
        super.onActivityResultNestedCompat(requestCode, resultCode, data);
        String address = data.getStringExtra("address");

        if (requestCode == SHIFADI && resultCode == 4) {
            flag = 0;
            addrs = address;
            mText.get(0).setText(addrs);
        } else if (requestCode == MUDIDI && resultCode == 4) {
            flag = 1;
            addrs = address;
            mText.get(1).setText(addrs);
        } else if (requestCode == TRUCKTYPE && resultCode == 101){
            flag = 2;
            String trucktype = data.getStringExtra("trucktype");
            String trucklength = data.getStringExtra("trucklength");
            if (TextUtils.isEmpty(trucklength)&&TextUtils.isEmpty(trucktype)){
                mText.get(2).setText("车型" + "/" + "车长");
            }else {
                mText.get(2).setText(trucktype + "/" + trucklength);
            }
            Log.e("allSrc-cartype",initJsonData(flag, addrs, trucklength, trucktype));

            getSrcFromside(initJsonData(flag, addrs, trucklength, trucktype));
        }
        onResume();
    }
    String time1 = "";
    String time2 = "";
    String time3 = "";

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        flag = 3;
        //popMenu.dismiss();

        switch (adapterView.getId()){
            case R.id.find_pop_listview:
                if (!TextUtils.isEmpty(time1)){
                    time2 = "";
                    time3 = "";
                    time1 = popList.get(i).get("name");
                }else {
                    time1 = popList.get(i).get("name");
                }
                break;
            case R.id.find_time_listview1:
                if (!TextUtils.isEmpty(time2)||!TextUtils.isEmpty(time3)){
                    time2 = "";
                    time3 = "";
                    time2 = time1map.get(i).get("name");
                }else{
                    time2 = time1map.get(i).get("name");
                }
                break;
            case R.id.find_time_listview2:
                if (!TextUtils.isEmpty(time2)||!TextUtils.isEmpty(time3)){
                    time2 = "";
                    time3 = "";
                    time3= time2map.get(i).get("name");
                }else {
                    time3= time2map.get(i).get("name");
                }
                break;
        }
        timetxt.setText(time1+" "+time2+" "+time3);
        //time = popList.get(i).get("name");

        // ToolsUtils.getInstance().toastShowStr(getContext(),initJsonData(flag,addrs));
       // getSrcFromside(initJsonData(flag, addrs, "", ""));
    }


}
