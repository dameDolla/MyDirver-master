package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/7.
 */

public class AddReleaseActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private View contentView;
    private PopupWindow popMenu;

    private int flag = 0;

    @BindViews({R.id.top_title,R.id.car_num,
                R.id.car_type,R.id.car_status,
                R.id.release_backtime_tv,R.id.release_rl_emptytime_tv,
                R.id.release_start_tv,R.id.release_finish_tv})
    public List<TextView> mText;

    @BindViews({R.id.import_weight,R.id.import_tiji,
                R.id.import_baojia,R.id.et_leave_message})
    public List<EditText> mEdit;

    @BindView(R.id.fabu_now)
    public Button fabu;

    @BindViews({R.id.title_back})
    public List<ImageView> mImage;

    @BindViews({R.id.release_begin_addr,R.id.release_finish_addr,
                R.id.release_rl_emptytime,R.id.release_rl_backtime,
                R.id.release_rl_carstatus})
    public List<RelativeLayout> mRelat;

    @BindView(R.id.release_parent)
    public LinearLayout parent;

    private String car_num;
    private String carType;
    private String carStatus;
    private String weight;
    private String tiji;
    private String baojia;
    private String msg;
    private String guid;
    private String mobile;
    private String key;
    private ListView popListView;
    private SimpleAdapter adapter;
    private WindowManager.LayoutParams params;
    private List<Map<String, String>> list;
    private List<String> strs = null;
    private int GETADDR = 100;
    private String start;
    private String finish;
    private String backtime;
    private String emptyTime;
    private String status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_return);
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
        mText.get(0).setText("发布空程");
        mRelat.get(0).setOnClickListener(this);
        mRelat.get(1).setOnClickListener(this);
        fabu.setOnClickListener(this);
        mRelat.get(2).setOnClickListener(this);
        mRelat.get(3).setOnClickListener(this);
        mRelat.get(4).setOnClickListener(this);

        guid = ToolsUtils.getString(AddReleaseActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(AddReleaseActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(AddReleaseActivity.this, Constant.KEY,"");
    }

    private void submit()
    {
        car_num = mText.get(1).getText().toString();
        carType = mText.get(2).getText().toString();
        carStatus = mText.get(3).getText().toString();

        start = mText.get(6).getText().toString();
        finish = mText.get(7).getText().toString();
        backtime = mText.get(4).getText().toString();
        emptyTime = mText.get(5).getText().toString();
        status = mText.get(2).getText().toString();

        weight = mEdit.get(0).getText().toString();
        tiji = mEdit.get(1).getText().toString();
        baojia = mEdit.get(2).getText().toString();
        msg = mEdit.get(3).getText().toString();

        if (TextUtils.isEmpty(guid) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(tiji) ||
            TextUtils.isEmpty(key) || TextUtils.isEmpty(car_num) || TextUtils.isEmpty(msg)  ||
            TextUtils.isEmpty(weight) || TextUtils.isEmpty(baojia) || TextUtils.isEmpty(start) ||
            TextUtils.isEmpty(finish) || TextUtils.isEmpty(emptyTime) ||
             TextUtils.isEmpty(backtime)) {
            ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this,"请填写完整的信息");
            return;
        }
        JSONObject mJson = new JSONObject();
        try {
            mJson.put("GUID",guid);
            mJson.put(Constant.MOBILE,mobile);
            mJson.put(Constant.KEY,key);
            mJson.put("truckno",car_num);
            mJson.put("SurplusTon",weight);
            mJson.put("TransportOffer",baojia);
            mJson.put("SurplusPower",tiji);
            mJson.put("MyMessage",msg);
            mJson.put("fromSite",start);
            mJson.put("toSite",finish);
            mJson.put("emptytime",emptyTime);//计划返程时间
            mJson.put("backtime",backtime);
            mJson.put("boardingtime",backtime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitUtils.getRetrofitService()
                .addRelease(Constant.MYINFO_PAGENAME,Config.ADD_RELEASE,mJson.toString())
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
                        ToolsUtils.getInstance().toastShowStr(AddReleaseActivity.this,getCodeBean.getErrorMsg());
                        setResult(2);
                        AddReleaseActivity.this.finish();
                    }
                });
    }

    private  void initPopwindow()
    {
        //initPopData();
        contentView=  getLayoutInflater().inflate(R.layout.find_poplist,null);
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
    private void showPop(List<Map<String,String>> l,int i)
    {
        list = l;
        popListView = (ListView) contentView.findViewById(R.id.find_pop_listview);
        popListView.setOnItemClickListener(this);
        adapter = new SimpleAdapter(AddReleaseActivity.this,list, R.layout.item_listview_popwin,
            new String[]{"name"},new int[]{R.id.listview_popwind_tv});
        popListView.setAdapter(adapter);
        popMenu.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
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
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_back:
                finish();
                break;
            case R.id.release_begin_addr:

                startActivityForResult(new Intent(AddReleaseActivity.this,SearchAddrActivity.class),GETADDR);
                break;
            case R.id.release_finish_addr:

                startActivityForResult(new Intent(AddReleaseActivity.this,SearchAddrActivity.class),200);
                break;
            case R.id.fabu_now:
                submit();
                break;
            case R.id.release_rl_backtime:
                flag = 3;
                strs = new ArrayList<String>();
                strs.add(ToolsUtils.StringData(0));
                strs.add(ToolsUtils.StringData(1));
                strs.add(ToolsUtils.StringData(2));
                strs.add(ToolsUtils.StringData(3));
                strs.add(ToolsUtils.StringData(4));
                strs.add(ToolsUtils.StringData(5));
                strs.add(ToolsUtils.StringData(6));
                showPop(initPopData(strs),flag);
                break;
            case R.id.release_rl_emptytime:
                flag =2;
                strs = new ArrayList<String>();
                strs.add(ToolsUtils.StringData(0));
                strs.add(ToolsUtils.StringData(1));
                strs.add(ToolsUtils.StringData(2));
                strs.add(ToolsUtils.StringData(3));
                strs.add(ToolsUtils.StringData(4));
                strs.add(ToolsUtils.StringData(5));
                strs.add(ToolsUtils.StringData(6));
                showPop(initPopData(strs),flag);
                break;
            case R.id.release_rl_carstatus:
                flag = 1;
                strs = new ArrayList<String>();
                strs.add("空仓");
                strs.add("余仓");

                showPop(initPopData(strs),flag);
                break;

        }
       // popMenu.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popMenu.dismiss();
        String str = list.get(i).get("name");
        if(flag == 1)
        {
            mText.get(3).setText(str);

        }else if(flag == 2)
        {
            mText.get(5).setText(str);

        }else if (flag == 3)
        {
            mText.get(4).setText(str);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String start = data.getStringExtra("address");
        //String finish = data.getStringExtra("finish");
        if(requestCode == 100 && resultCode == 4){
            mText.get(6).setText(start);
        }else if (requestCode == 200 && resultCode == 4)
        {
            mText.get(7).setText(start);
        }

    }
}
