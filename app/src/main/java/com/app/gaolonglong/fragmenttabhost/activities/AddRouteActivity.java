package com.app.gaolonglong.fragmenttabhost.activities;

import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.MyGridView;

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


    private List<String> strs = null;
    private View popView;
    private PopupWindow typePopmenu;
    private WindowManager.LayoutParams param;

    @BindView(R.id.top_title)
    public TextView title;
    private String key;
    private String mobile;
    private String guid;

    @OnClick(R.id.title_back)
    public void back(){finish();}

    @OnClick(R.id.title_back_txt)
    public void backs()
    {
        finish();
    }

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
        initCartypePopwindow();
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
    private void initCartypePopwindow()
    {
        popView = getLayoutInflater().inflate(R.layout.find_cartype_gridview, null);
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
        SimpleAdapter lenthAdapter = new SimpleAdapter(AddRouteActivity.this,lengthList,R.layout.find_cartype_pop_item,new String[]{"length"},
                new int[]{R.id.gv_item_text});
        lenthGrid.setAdapter(lenthAdapter);

        final MyGridView typeGrid = (MyGridView) popView.findViewById(R.id.gridview_2);
        SimpleAdapter typeAdapter = new SimpleAdapter(AddRouteActivity.this,typeList,R.layout.find_cartype_pop_item,new String[]{"type"},
                new int[]{R.id.gv_item_text});
        typeGrid.setAdapter(typeAdapter);

        typePopmenu.showAtLocation(content_ll, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);

        param = getWindow().getAttributes();
        param.alpha=0.7f;
        getWindow().setAttributes(param);
        typePopmenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                param = getWindow().getAttributes();
                param.alpha=1f;
                getWindow().setAttributes(param);
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.get(2).setText(lenStr+"/"+typeStr);
                typePopmenu.dismiss();
            }
        });

    }

    private  void submit()
    {
        String start = mText.get(0).getText().toString();
        String finish = mText.get(1).getText().toString();
        String type = mText.get(2).getText().toString();
        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(finish)){
            ToolsUtils.getInstance().toastShowStr(AddRouteActivity.this,"请填入始发地和目的地");
        }else {
            Map<String,String> map = new HashMap<>();
            map.put("guid",guid);
            map.put("mobile",mobile);
            map.put(Constant.KEY,key);
            map.put("fromSite",start+"市");
            map.put("toSite",finish+"市");
            if (!type.equals(""))
            {
                String[] carinfo = type.split("/");
                map.put("trucktype",carinfo[0]);
                map.put("trucklength",carinfo[1]);
            }else {
                map.put("trucktype","");
                map.put("trucklength","");
            }


            RetrofitUtils.getRetrofitService()
                    .addRoute(Constant.MYINFO_PAGENAME, Config.ROUTE_ADD, JsonUtils.getInstance().getJsonStr(map))
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
                            // ToolsUtils.getInstance().toastShowStr(AddRouteActivity.this,getCodeBean.getErrorMsg());
                            startActivity(new Intent(AddRouteActivity.this,MyRouteListActivity.class));
                        }
                    });
        }

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

                break;
            case R.id.bt_add_route:
                submit();
                break;
            case R.id.add_route_rl_type:
                showCarPop();
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
