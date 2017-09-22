package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.app.gaolonglong.fragmenttabhost.activities.AddReleaseActivity;
import com.app.gaolonglong.fragmenttabhost.activities.FindDetailActivity;
import com.app.gaolonglong.fragmenttabhost.activities.SearchAddrActivity;
import com.app.gaolonglong.fragmenttabhost.adapter.FindSrcAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.GetSRCBean;
import com.app.gaolonglong.fragmenttabhost.bean.TestBean;
import com.app.gaolonglong.fragmenttabhost.bean.ToSrcDetailBean;
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
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/14.
 */

public class FindAllSrcFragment extends ForResultNestedCompatFragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    private View mRootView;
    private List<GetSRCBean.DataBean> list = new ArrayList<GetSRCBean.DataBean>();
    private FindSrcAdapter adapter;
    private int SHIFADI = 99;
    private int MUDIDI = 98;
    private int flag = 0;
    private String addrs = "";
    private View contentView;
    private PopupWindow popMenu;
    private SimpleAdapter Popadapter;
    private WindowManager.LayoutParams params;
    private List<Map<String, String>> popList;


    @BindView(R.id.find_src_rlv)
    public RecyclerView rlv;

    @BindView(R.id.find_all_parent)
    public LinearLayout parent;

    @BindViews({R.id.find_tv_origin,R.id.find_tv_destination,R.id.find_tv_cartype,R.id.find_tv_time})
    public List<TextView> mText;
    private String guid;
    private String mobile;
    private String key;
    private String location;
    private String time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null)
        {
            mRootView = inflater.inflate(R.layout.find_all_src,container,false);
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

    @Override
    public void onStart() {
        super.onStart();
       getSrcFromside(initJsonData(flag,addrs));

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init()
    {
        initView();
        initPopwindow();
    }
    private void initView()
    {
        guid = ToolsUtils.getString(getContext(), Constant.LOGIN_GUID, "");
        mobile = ToolsUtils.getString(getContext(), Constant.MOBILE, "");
        key = ToolsUtils.getString(getContext(), Constant.KEY, "");
        location = ToolsUtils.getString(getContext(), Constant.CITY, "");

        mText.get(0).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        mText.get(2).setOnClickListener(this);
        mText.get(3).setOnClickListener(this);


        adapter = new FindSrcAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rlv.setLayoutManager(manager);
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new FindSrcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean) {
                //ToolsUtils.getInstance().toastShowStr(getContext(),bean.getTo());
                Intent intent = new Intent(getContext(), FindDetailActivity.class);
                intent.putExtra("findSrc",bean);
                //startActivity(intent);
                ToolsUtils.getInstance().toastShowStr(getContext(),bean.getBillsGUID());
            }
        });

    }
    private  void initPopwindow()
    {
        //initPopData();
        contentView=  getActivity().getLayoutInflater().inflate(R.layout.find_poplist,null);
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
     *
     * @param flag 0(默认定位) 1(选择了终点) 2（选择了车辆类型） 3（选择了装车时间）
     * @return
     */
    Map<String,String> map = new HashMap<String,String>();
    private String initJsonData(int flag,String addr)
    {

        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);


        if (flag == 0)
        {
            if(addr.equals(""))
            {
                map.put("fromSite","深圳市");
            }else
            {
                map.put("fromSite",addr);
            }

        }else if (flag == 1){
            map.put("toSite",addr);
        }else if (flag == 2)
        {
            map.put("trucktype","");
        }
        else if (flag == 3)
        {
            map.put("preloadtime",time);
        }

        return JsonUtils.getInstance().getJsonStr(map);
    }

    /**
     * 根据始发地查找货源
     */
    private void getSrcFromside(String json)
    {

        RetrofitUtils.getRetrofitService()
                .getSRCWithFromside(Constant.MYINFO_PAGENAME, Config.SRC_FROMSIDE,json)
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

                        list.clear();
                        list.addAll(getSRCBean.getData());
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.find_tv_origin:
                Intent intent = new Intent(getContext(), SearchAddrActivity.class);
                startActivityForResult(intent,SHIFADI);
                break;
            case R.id.find_tv_destination:
                startActivityForResult(new Intent(getContext(), SearchAddrActivity.class),MUDIDI);
                break;
            case R.id.find_tv_cartype:

                break;
            case R.id.find_tv_time:
                List<String> strs = new ArrayList<String>();
                strs.add(ToolsUtils.StringData(0));
                strs.add(ToolsUtils.StringData(1));
                strs.add(ToolsUtils.StringData(2));
                strs.add(ToolsUtils.StringData(3));
                strs.add(ToolsUtils.StringData(4));
                strs.add(ToolsUtils.StringData(5));
                strs.add(ToolsUtils.StringData(6));
                showPop(initPopData(strs));
                break;
        }
    }
    private List<Map<String,String>> initPopData(List<String> str)
    {
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
    private ListView popListView;
    private void showPop(List<Map<String,String>> l)
    {
        popList= l;
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        popListView.setOnItemClickListener(this);
        Popadapter = new SimpleAdapter(getContext(),popList, R.layout.item_listview_popwin,
                new String[]{"name"},new int[]{R.id.listview_popwind_tv});
        popListView.setAdapter(Popadapter);
        popMenu.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
        //popMenu.showAsDropDown(mText.get(3),0,10);
        params = getActivity().getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha=0.7f;
        getActivity().getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getActivity().getWindow().getAttributes();
                params.alpha=1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }
    @Override
    public void onActivityResultNestedCompat(int requestCode, int resultCode, Intent data) {
        super.onActivityResultNestedCompat(requestCode, resultCode, data);
        String address = data.getStringExtra("address")+"市";
        if(requestCode == SHIFADI && resultCode == 4)
        {
            flag =0;
            addrs = address;
            ToolsUtils.getInstance().toastShowStr(getActivity(),addrs);
            mText.get(0).setText(addrs);
        }else if (requestCode == MUDIDI && resultCode == 4)
        {
            flag = 1;
            addrs = address;
            mText.get(1).setText(addrs);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        flag = 3;
        popMenu.dismiss();
        time = popList.get(i).get("name");
        mText.get(3).setText(time);
       ToolsUtils.getInstance().toastShowStr(getContext(),initJsonData(flag,addrs));
       // getSrcFromside(initJsonData(flag,addrs));
    }
}
