package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;

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
    private static final int TRUCKTYPE = 107;

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
    }
    private void initView()
    {
        title.setText("添加线路");
        mRel.get(0).setOnClickListener(this);
        mRel.get(1).setOnClickListener(this);
        mRel.get(2).setOnClickListener(this);
        submit.setOnClickListener(this);
        //ToolsUtils.getInstance().addStatusViewWithColor(this,getResources().getColor(R.color.shen_blue));
        guid = ToolsUtils.getString(AddRouteActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(AddRouteActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(AddRouteActivity.this, Constant.KEY,"");
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
            map.put("fromSite",start);
            map.put("toSite",finish);
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
                startActivityForResult(new Intent(AddRouteActivity.this,AddressActivity.class),102);
                break;
            case R.id.add_route_rl_finish:
                startActivityForResult(new Intent(AddRouteActivity.this,AddressActivity.class),103);
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
                startActivityForResult(new Intent(AddRouteActivity.this,SelectTruckTypeActivity.class),TRUCKTYPE);
                //showCarPop();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 102:
                String addr = data.getStringExtra("address");
                mText.get(0).setText(addr);
                break;
            case 103:
                String addrs = data.getStringExtra("address");
                mText.get(1).setText(addrs);
                break;
            case TRUCKTYPE:
                String type = data.getStringExtra("trucklength")+"/"+data.getStringExtra("trucktype");
                mText.get(2).setText(type);
                break;
        }

    }
}
