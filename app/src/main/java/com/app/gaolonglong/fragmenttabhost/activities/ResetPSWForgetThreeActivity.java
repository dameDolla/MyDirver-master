package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.app.gaolonglong.fragmenttabhost.view.PasswordInputView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/26.
 */

public class ResetPSWForgetThreeActivity extends BaseActivity {
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
    @BindView(R.id.passwordInputView)
    public PasswordInputView inputView;
    @OnClick(R.id.import_paycode_submit)
    public void submit()
    {
        Map<String,String> map = new HashMap<>();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("MD5Pwd", ToolsUtils.getInstance().getMD5Val(inputView.getText().toString()));
        map.put("PSMSKey",getIntent().getStringExtra("YZM"));
        setPayCode(JsonUtils.getInstance().getJsonStr(map));
    }
    String guid,mobile,key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_import_password);
        ButterKnife.bind(this);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        title.setText("重置密码");
        mobile = ToolsUtils.getString(ResetPSWForgetThreeActivity.this, Constant.MOBILE,"");
        guid = ToolsUtils.getString(ResetPSWForgetThreeActivity.this,Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(ResetPSWForgetThreeActivity.this,Constant.KEY,"");
    }

    /**
     * 添加支付密码
     * @param json
     */
    private void setPayCode(String json)
    {
        RetrofitUtils.getRetrofitService()
                .setPayCode(Constant.MYINFO_PAGENAME, Config.YZM_UPDATE_PAYCODE,json)
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
                        Log.e("info",getCodeBean.getErrorMsg());
                        //ToolsUtils.getInstance().toastShowStr(PayCodeYZMActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }
}
