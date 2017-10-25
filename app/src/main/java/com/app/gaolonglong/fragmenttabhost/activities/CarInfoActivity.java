package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CarinfoBean;
import com.app.gaolonglong.fragmenttabhost.bean.TestBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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
 * Created by yanqi on 2017/9/29.
 */

public class CarInfoActivity extends BaseActivity  implements View.OnClickListener{

    private List<CarinfoBean.DataBean> data;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back_txt)
    public void backs()
    {
        finish();
    }
    @BindView(R.id.top_title)
    public TextView title;

    @BindViews({R.id.carinfo_cartype,R.id.carinfo_carlength,R.id.carinfo_num,R.id.carinfo_xsznum,R.id.carinfo_tiji})
    public List<TextView> mText;

    @BindViews({R.id.carinfo_pic1,R.id.carinfo_pic2,R.id.carinfo_pic3,R.id.carinfo_pic4})
    public List<SimpleDraweeView> img;

    @BindViews({R.id.carinfo_update,R.id.carinfo_del})
    public List<Button> mButton;

    @BindView(R.id.carinfo_refresh)
    public SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_info);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("车辆信息");
        getCarInfo(initJsonData());
        mButton.get(0).setOnClickListener(this);
        refresh.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(null);
                refresh.setRefreshing(false);
            }
        });
    }
    private String initJsonData()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID", GetUserInfoUtils.getGuid(CarInfoActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(CarInfoActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(CarInfoActivity.this));

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void getCarInfo(final String json)
    {
        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .getCarInfo(Constant.TRUCK_PAGENAME, Config.GETCARINFO,json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CarinfoBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                //Log.e("carinfoerror",e.getMessage());
                            }

                            @Override
                            public void onNext(CarinfoBean carinfoBean) {
                                Log.e("carinfo",carinfoBean.getErrorCode());
                                //Log.e("carinfoimg",GetUserInfoUtils.getImg(,"4"));
                                //Log.e("carinfoimgssss",GetUserInfoUtils.getImg(carinfoBean.getData().get(0).getTrucksGUID(),"4"));
                                setView(carinfoBean);
                                Log.e("carinfo",carinfoBean.getErrorMsg());
                            }
                        });
            }
        });
    }
    private void setView(CarinfoBean bean)
    {
        String guid = GetUserInfoUtils.getGuid(CarInfoActivity.this);
        data = bean.getData();
        mText.get(0).setText(data.get(0).getTrucktype()+"");
        mText.get(1).setText(data.get(0).getTrucklength()+"");
        mText.get(2).setText(data.get(0).getTruckno()+"");
        mText.get(3).setText(data.get(0).getTrucklicence()+"");

        img.get(0).setImageURI(Uri.parse(GetUserInfoUtils.getImg(data.get(0).getTrucksGUID(),"10")));
        img.get(1).setImageURI(Uri.parse(GetUserInfoUtils.getImg(data.get(0).getTrucksGUID(),"16")));
        img.get(2).setImageURI(Uri.parse(GetUserInfoUtils.getImg(data.get(0).getTrucksGUID(),"6")));
        img.get(3).setImageURI(Uri.parse(GetUserInfoUtils.getImg(data.get(0).getTrucksGUID(),"7")));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.carinfo_update:
                Intent intent = new Intent(CarInfoActivity.this,AddCarActivity.class);
                intent.putExtra("truckguid",data.get(0).getTrucksGUID());
                intent.putExtra("cartype",data.get(0).getTrucktype());
                intent.putExtra("carlength",data.get(0).getTrucklength());
                intent.putExtra("carno",data.get(0).getTruckno());
                intent.putExtra("carxsz",data.get(0).getTrucklicence());
                intent.putExtra("flag","carinfo");
                startActivity(intent);
                break;
        }
    }
}
