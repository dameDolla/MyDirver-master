package com.app.gaolonglong.fragmenttabhost.fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.GridView;
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
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;
import com.app.gaolonglong.fragmenttabhost.view.MyLinearLayoutManager;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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

    @BindView(R.id.find_all_refresh)
    public SwipeRefreshLayout refresh;

    @BindViews({R.id.find_tv_origin,R.id.find_tv_destination,R.id.find_tv_cartype,R.id.find_tv_time})
    public List<TextView> mText;
    private String guid;
    private String mobile;
    private String key;
    private String location;
    private String time;
    private View popView;
    private PopupWindow typePopmenu;
    private WindowManager.LayoutParams param;

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

        ThreadPoolHelp.Builder
                .cached()
                .builder()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //线程执行体
                       getSrcFromside(initJsonData(flag,addrs));
                    }
                    });

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init()
    {
        initView();
        initPopwindow();
        initCartypePopwindow();
    }
    private void initView()
    {
        guid = GetUserInfoUtils.getGuid(getContext());
        mobile = GetUserInfoUtils.getMobile(getContext());
        key = GetUserInfoUtils.getKey(getContext());
        location = ToolsUtils.getString(getContext(), Constant.CITY, "");
        mText.get(0).setOnClickListener(this);
        mText.get(1).setOnClickListener(this);
        mText.get(2).setOnClickListener(this);
        mText.get(3).setOnClickListener(this);
        if (addrs.equals("")){
            mText.get(0).setText(location);
        }else {
           // mText.get(0).setText(addrs);
        }

        adapter = new FindSrcAdapter(getContext(),list);
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getContext());
        rlv.setLayoutManager(manager);
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new FindSrcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ToSrcDetailBean bean) {
                //ToolsUtils.getInstance().toastShowStr(getContext(),bean.getOwnerguid());
                Intent intent = new Intent(getContext(), FindDetailActivity.class);
                intent.putExtra("findSrc",bean);
                startActivity(intent);
            }
        });
        adapter.setOnFindClickListener(new FindSrcAdapter.OnFindClickListener() {
            @Override
            public void onFindClick(int position, String tel) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+list.get(position).getOwnerphone()));
                startActivity(intent);
            }
        });

        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onActivityCreated(null);
                refresh.setRefreshing(false);
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
    private void initCartypePopwindow()
    {
        popView = getActivity().getLayoutInflater().inflate(R.layout.find_cartype_gridview,null);
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

    String lenStr = null;
    String typeStr = null;
    private void showCarPop()
    {

        List<Map<String,String>> typeList = new ArrayList<Map<String,String>>();
        List<Map<String,String>> lengthList = new ArrayList<Map<String,String>>();
        String[] length = { "不限", "4.2米", "4.5米", "5米", "5.2米", "6.2米", "6.8米",
                "7.2米", "11.7米", "12.5米", "13米", "13.5米","14米","15米","16米","17米" };

        final String[] type = {"不限","冷藏车","平板","高栏","箱式","保温","危险品","高低板"};

        for (int j=0;j<type.length;j++)
        {
            Map<String,String> maps = new HashMap<String, String>();
            maps.put("type",type[j]);
            typeList.add(maps);
        }

        for (int i= 0;i<length.length;i++)
        {
            Map<String,String> map = new HashMap<String,String>();
            map.put("length",length[i]);
            lengthList.add(map);
        }
        final MyGridView lenthGrid = (MyGridView) popView.findViewById(R.id.gridview);
        SimpleAdapter lenthAdapter = new SimpleAdapter(getContext(),lengthList,R.layout.find_cartype_pop_item,new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(getContext(),typeList,R.layout.find_cartype_pop_item,new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typePopmenu.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);

        param = getActivity().getWindow().getAttributes();
        param.alpha=0.7f;
        getActivity().getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getActivity().getWindow().getAttributes();
                param.alpha=1f;
                getActivity().getWindow().setAttributes(param);
            }
        });
        lenthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence len = ((TextView) lenthGrid.getChildAt(i).findViewById(R.id.gv_item_text)).getText();
                lenStr = len.toString();
                for(int m=0;m<adapterView.getCount();m++){
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
                for(int m=0;m<adapterView.getCount();m++){
                    TextView item = (TextView) typeGrid.getChildAt(m).findViewById(R.id.gv_item_text);
                    typeStr = (String)item.getText();
                    if (i == m) {//当前选中的Item改变背景颜色
                        item.setBackgroundResource(R.drawable.cartype_unselect);
                    } else {
                        item.setBackgroundResource(R.drawable.cartype_select);
                    }
                }
            }
        });
        TextView sure = (TextView)popView.findViewById(R.id.cartype_grid_sure);
        TextView noLimit = (TextView)popView.findViewById(R.id.cartype_grid_nocartype);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.get(2).setText(lenStr+"/"+typeStr);
                typePopmenu.dismiss();
            }
        });
        noLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    /**
     *
     * @param flag 0(默认定位) 1(选择了终点) 2（选择了车辆类型） 3（选择了装车时间）
     * @return
     */
    Map<String,String> map = new HashMap<String,String>();
    private String initJsonData(int flag,String addr)
    {
       // ToolsUtils.getInstance().toastShowStr(getContext(),addr);
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);


        if (flag == 0)
        {
            if(addr.equals(""))
            {
                map.put("fromSite",location);
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
    private void getSrcFromside(final String json)
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

                                //Log.e("allSrc",getSRCBean.getErrorMsg()+"");
                                if (getSRCBean.getErrorCode().equals("200"))
                                {
                                    rlv.removeAllViews();
                                    list.clear();
                                    list.addAll(getSRCBean.getData());
                                    adapter.notifyDataSetChanged();
                                }



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
                showCarPop();
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
            mText.get(0).setText(addrs);
            Log.e("address",addrs);
        }else if (requestCode == MUDIDI && resultCode == 4)
        {
            flag = 1;
            addrs = address;
            mText.get(1).setText(addrs);
        }
        onActivityCreated(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        flag = 3;
        popMenu.dismiss();
        time = popList.get(i).get("name");
        mText.get(3).setText(time);
      // ToolsUtils.getInstance().toastShowStr(getContext(),initJsonData(flag,addrs));
        getSrcFromside(initJsonData(flag,addrs));
    }
}
