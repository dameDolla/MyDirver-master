package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.AddressActivity;
import com.app.gaolonglong.fragmenttabhost.activities.FindDetailActivity;
import com.app.gaolonglong.fragmenttabhost.activities.SelectTruckTypeActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.FindSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.EmptyLayout;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/14.
 */

public class FindAllSrcFragment extends ForResultNestedCompatFragment implements View.OnClickListener, AdapterView.OnItemClickListener,OnAddressSelectedListener {

    private View mRootView;
    private List<GetSRCBean.DataBean> list = new ArrayList<GetSRCBean.DataBean>();
    private FindSrcAdapter adapter;
    private int SHIFADI = 99;
    private int MUDIDI = 98;
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
    private PopupWindow typePopmenu;
    private WindowManager.LayoutParams param;
    private boolean isRenzheng;
    private String lenStr = null;
    private String typeStr = null;
    private BottomDialog addrDialog;
    private TextView timecancel,timetxt,timesure;
    private List<Map<String, String>> time1map;
    private List<Map<String, String>> time2map;

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
                    ToolsUtils.getInstance().toastShowStr(getContext(), "请先通过认证");

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
        adapter.setOnFindClickListener(new FindSrcAdapter.OnFindClickListener() {
            @Override
            public void onFindClick(int position, String tel) {
                if (isRenzheng) {
                    //ToolsUtils.getInstance().toastShowStr(getContext(),tel);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(position).getOwnerphone()));
                    startActivity(intent);
                } else {
                    ToolsUtils.getInstance().toastShowStr(getContext(), "请先通过认证");
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

    private void initCartypePopwindow() {
        popView = getActivity().getLayoutInflater().inflate(R.layout.find_cartype_gridview, null);
        typePopmenu = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        typePopmenu.setOutsideTouchable(true);
        typePopmenu.setBackgroundDrawable(dw);
        typePopmenu.setFocusable(true);
        typePopmenu.setTouchable(true);
        typePopmenu.setAnimationStyle(R.style.mypopwindow_anim_style);
    }



    private void showCarPop() {
        initCartypePopwindow();
        List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> lengthList = new ArrayList<Map<String, String>>();
        String[] length = {"不限", "4.2米", "4.5米", "5米", "5.2米", "6.2米", "6.8米",
                "7.2米", "11.7米", "12.5米", "13米", "13.5米", "14米", "15米", "16米", "17米"};

        final String[] type = {"不限", "冷藏车", "平板", "高栏", "箱式", "保温", "危险品", "高低板"};

        for (int j = 0; j < type.length; j++) {
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("type", type[j]);
            typeList.add(maps);
        }

        for (int i = 0; i < length.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("length", length[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) popView.findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(getContext(), lengthList, R.layout.find_cartype_pop_item, new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(getContext(), typeList, R.layout.find_cartype_pop_item, new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typePopmenu.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        param = getActivity().getWindow().getAttributes();
        param.alpha = 0.7f;
        getActivity().getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getActivity().getWindow().getAttributes();
                param.alpha = 1f;
                getActivity().getWindow().setAttributes(param);
            }
        });
        lenthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence len = ((TextView) lenthGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                lenStr = len.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) lenthGrid.getChildAt(m).findViewById(R.id.gv_item_text);

                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        typeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence type = ((TextView) typeGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                typeStr = type.toString();
                for (int m = 0; m < adapterView.getCount(); m++) {
                    TextView item = (TextView) typeGrid.getChildAt(m).findViewById(R.id.gv_item_text);
                    //typeStr = (String) item.getText();
                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        TextView sure = (TextView) popView.findViewById(R.id.cartype_grid_sure);
        TextView noLimit = (TextView) popView.findViewById(R.id.cartype_grid_nocartype);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                mText.get(2).setText(typeStr + "/" + lenStr);
                getSrcFromside(initJsonData(flag, addrs, lenStr, typeStr));
                typePopmenu.dismiss();
            }
        });
        noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                mText.get(2).setText("车型" + "/" + "车长");
                Log.e("findlog", initJsonData(flag, addrs, "", ""));
                getSrcFromside(initJsonData(flag, addrs, "", ""));
                typePopmenu.dismiss();
            }
        });
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

                            }

                            @Override
                            public void onError(Throwable e) {
                                //ToolsUtils.getInstance().toastShowStr(getContext(),e.getMessage());
                            }

                            @Override
                            public void onNext(GetSRCBean getSRCBean) {

                                //Log.e("allSrc", getSRCBean.getErrorMsg() + "--" + getSRCBean.getErrorCode());

                                if (getSRCBean.getErrorCode().equals("203")) {
                                    main.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    empty.setErrorImag(R.drawable.nosrc, "暂无货源信息");
                                } else {
                                    rlv.removeAllViews();
                                    list.clear();
                                    list.addAll(getSRCBean.getData());
                                    Log.e("srcsize", list.size() + "");
                                    main.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
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
               // startActivity(new Intent(getContext(), SelectTruckTypeActivity.class));
                showCarPop();
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

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        String s =
                (province == null ? "" : province.name) +
                        (city == null ? "" : "\n" + city.name) +
                        (county == null ? "" : "\n" + county.name) +
                        (street == null ? "" : "\n" + street.name);
            addrDialog.dismiss();
            addrs = city.name;
            //ToolsUtils.getInstance().toastShowStr(getContext(),addrs);
            if (flag ==0){
                mText.get(0).setText(addrs);
            }else if (flag == 1){
                mText.get(1).setText(addrs);
            }
        onResume();
    }
}
