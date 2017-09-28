package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class ResetPSWRemTwoActivity extends BaseActivity {
    @BindView(R.id.top_title)
    public TextView title;
    private String password;
    private String guid;
    private String mobile;
    private String key;

    @OnClick(R.id.import_paycode_submit)
    public void submit()
    {
        submits(initJsonData());
    }
    @BindView(R.id.passwordInputView)
    public PasswordInputView inputView;
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
        password = getIntent().getStringExtra("password");
        Log.e("md5",ToolsUtils.getInstance().getMD5Val(password));
        //ToolsUtils.getInstance().toastShowStr(ResetPSWRemTwoActivity.this,password);
    }
    private void initView()
    {
        title.setText("设置支付密码");
    }
    private String initJsonData()
    {
        Map<String,String> map  = new HashMap<String,String>();
        guid = ToolsUtils.getString(ResetPSWRemTwoActivity.this, Constant.LOGIN_GUID,"");
        mobile = ToolsUtils.getString(ResetPSWRemTwoActivity.this, Constant.MOBILE,"");
        key = ToolsUtils.getString(ResetPSWRemTwoActivity.this, Constant.KEY,"");
        String psw = inputView.getText().toString();
        map.put("GUID",guid);
        map.put(Constant.MOBILE,mobile);
        map.put(Constant.KEY,key);
        map.put("MD5Pwd",ToolsUtils.getInstance().getMD5Val(password));
        map.put("NewMD5Pwd",ToolsUtils.getInstance().getMD5Val(psw));
        return JsonUtils.getInstance().getJsonStr(map);
    }
    private void submits(String json)
    {
        RetrofitUtils.getRetrofitService()
                .setPayCode(Constant.MYINFO_PAGENAME, Config.OLD_UPDATE_PAYCODE,json)
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
                        ToolsUtils.getInstance().toastShowStr(ResetPSWRemTwoActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }
}

