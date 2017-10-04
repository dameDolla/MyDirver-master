package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.adapter.CarTeamAdapter;
import com.app.gaolonglong.fragmenttabhost.bean.CarTeamBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.view.WrapHeightGridView;
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
 * Created by yanqi on 2017/8/29.
 */

public class MyCarTeamActivity extends BaseActivity {

    @BindViews({R.id.top_title})
    public List<TextView> mText;

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
    @BindView(R.id.carteam_gv)
    public WrapHeightGridView gv;

    private List<CarTeamBean.DataBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cargroup);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("车队信息");
        CarTeamAdapter adapter = new CarTeamAdapter(MyCarTeamActivity.this,list);
        gv.setAdapter(adapter);

        ThreadPoolHelp.Builder.cached().builder().execute(new Runnable() {
            @Override
            public void run() {
               // getCarTeam(initJsonData());
            }
        });
    }
    private String initJsonData()
    {
        String guid = GetUserInfoUtils.getGuid(MyCarTeamActivity.this);
        String mobile = GetUserInfoUtils.getMobile(MyCarTeamActivity.this);
        String key = GetUserInfoUtils.getKey(MyCarTeamActivity.this);

        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);

        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void getCarTeam(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getCarTeamList(Constant.TRUCK_PAGENAME, Config.GETTRUCKS,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarTeamBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CarTeamBean carTeamBean) {
                        if (carTeamBean.getErrorCode().equals("200"))
                        {
                            list.clear();
                            list.addAll(carTeamBean.getData());
                        }
                    }
                });
    }
}
