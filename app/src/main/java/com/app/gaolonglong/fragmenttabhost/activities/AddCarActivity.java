package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

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
 * Created by yanqi on 2017/10/3.
 */

public class AddCarActivity extends BaseActivity {

    @BindViews({R.id.add_car_carnum,R.id.add_car_xsznum,R.id.add_car_time,R.id.add_car_cartype})
    public List<EditText> mEdit;
    private Map<String, String> map;

    @OnClick(R.id.title_back_txt)
    public void back()
    {
        finish();
    }
    @OnClick(R.id.title_back)
    public void backs()
    {
        finish();
    }
    @BindView(R.id.top_title)
    public TextView title;

    @OnClick(R.id.addcar_queren)
    public void querens()
    {
        add(initJsonData());
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("添加车辆");
    }

    private String  initJsonData()
    {
        String guid = GetUserInfoUtils.getGuid(AddCarActivity.this);
        String mobile = GetUserInfoUtils.getMobile(AddCarActivity.this);
        String key = GetUserInfoUtils.getKey(AddCarActivity.this);
        String carnum = mEdit.get(0).getText()+"";
        String xsznum = mEdit.get(1).getText()+"";
        String time = mEdit.get(2).getText()+"";
        String cartype = mEdit.get(3).getText()+"";
        if (TextUtils.isEmpty(carnum) || TextUtils.isEmpty(xsznum) || TextUtils.isEmpty(time) || TextUtils.isEmpty(cartype))
        {
            ToolsUtils.getInstance().toastShowStr(AddCarActivity.this,"请填写完整的信息");
        }else {
            map = new HashMap<>();
            map.put("GUID",guid);
            map.put(Constant.MOBILE,mobile);
            map.put(Constant.KEY,key);
            map.put("truckno",carnum);
            map.put("trucktype",cartype);
            map.put("boardingtime",time);
            map.put("companyGUID","");
            map.put("company","");
        }
        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void add(String json)
    {
        RetrofitUtils.getRetrofitService()
                .addCars(Constant.TRUCK_PAGENAME, Config.ADDTRUCK,json)
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

                    }
                });
    }

}
