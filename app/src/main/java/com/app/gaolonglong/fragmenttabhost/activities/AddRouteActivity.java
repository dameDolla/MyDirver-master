package com.app.gaolonglong.fragmenttabhost.activities;

import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by yanqi on 2017/9/6.
 */

public class AddRouteActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private ListView popListView;
    private SimpleAdapter adapter;
    private WindowManager.LayoutParams params;
    private List<Map<String, String>> list;
    private List<String> strs = null;
    private View contentView;
    private PopupWindow popMenu;

    @BindView(R.id.top_title)
    public TextView title;
    private String key;
    private String mobile;
    private String guid;

    @OnClick(R.id.title_back)
    public void back(){finish();}

    @BindViews({R.id.add_route_start,R.id.add_route_finish,
                R.id.add_route_type})
    public List<TextView> mText;

    @BindViews({R.id.addr_route_rl_start,R.id.add_route_rl_finish,
                R.id.add_route_rl_type})
    public List<RelativeLayout> mRel;

    @BindView(R.id.bt_add_route)
    public Button submit;

    @BindView(R.id.add_route_ll)
    public LinearLayout content_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_route);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
        initPopwindow();
    }
    private void initView()
    {
        title.setText("添加线路");
        mRel.get(0).setOnClickListener(this);
        mRel.get(1).setOnClickListener(this);
        mRel.get(2).setOnClickListener(this);
        submit.setOnClickListener(this);

        guid = ToolsUtils.getString(AddRouteActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(AddRouteActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(AddRouteActivity.this, Constant.KEY,"");
    }
    private  void initPopwindow()
    {
        //initPopData();
        contentView = getLayoutInflater().inflate(R.layout.find_poplist, null);
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
    private void showPop(List<Map<String,String>> l)
    {
        list = l;
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        popListView.setOnItemClickListener(this);
        adapter = new SimpleAdapter(AddRouteActivity.this,list, R.layout.item_listview_popwin,
                new String[]{"name"},new int[]{R.id.listview_popwind_tv});
        popListView.setAdapter(adapter);
        popMenu.showAtLocation(content_ll, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha=0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        popMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private  void submit()
    {
        String start = mText.get(0).getText().toString();
        String finish = mText.get(1).getText().toString();
        String type = mText.get(2).getText().toString();
        JSONObject mJson = new JSONObject();
        try {
            mJson.put("guid",guid);
            mJson.put("mobile",mobile);
            mJson.put(Constant.KEY,key);
            mJson.put("fromSite",start);
            mJson.put("toSite",finish);
            mJson.put("trucktype","大奔");
            mJson.put("trucklength","123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtils.getRetrofitService()
                .addRoute(Constant.MYINFO_PAGENAME, Config.ROUTE_ADD,mJson.toString())
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
                        ToolsUtils.getInstance().toastShowStr(AddRouteActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addr_route_rl_start:
                startActivityForResult(new Intent(AddRouteActivity.this,SearchAddrActivity.class),102);
                break;
            case R.id.add_route_rl_finish:
                startActivityForResult(new Intent(AddRouteActivity.this,SearchAddrActivity.class),103);
                break;
            case R.id.add_route_type:
                strs = new ArrayList<String>();
                strs.add("空仓");
                strs.add("余仓");

                showPop(initPopData(strs));
                break;
            case R.id.bt_add_route:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String addr = data.getStringExtra("address");
        if(requestCode == 102 && resultCode == 4)
        {
            mText.get(0).setText(addr);
        }else if (requestCode == 103 && resultCode == 4)
        {
            mText.get(1).setText(addr);
        }
    }
}
