package com.app.gaolonglong.fragmenttabhost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
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
 * Created by yanqi on 2017/9/26.
 */

public class ResetPSWForgetOneActivity extends BaseActivity {
    @BindView(R.id.top_title)
    public TextView title;
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
    String guid,key,mobile;
    @BindViews({R.id.yzm_one,R.id.yzm_two,R.id.yzm_three,R.id.yzm_four,R.id.yzm_five,R.id.yzm_six})
    public List<EditText> mEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_paycode_yanzhengma);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mobile = ToolsUtils.getString(ResetPSWForgetOneActivity.this, Constant.MOBILE,"");
        guid = ToolsUtils.getString(ResetPSWForgetOneActivity.this,Constant.LOGIN_GUID,"");
        key = ToolsUtils.getString(ResetPSWForgetOneActivity.this,Constant.KEY,"");
        title.setText("重置支付密码");
        mEdit.get(0).addTextChangedListener(tw);
        mEdit.get(1).addTextChangedListener(tw);
        mEdit.get(2).addTextChangedListener(tw);
        mEdit.get(3).addTextChangedListener(tw);
        mEdit.get(4).addTextChangedListener(tw);
        mEdit.get(5).addTextChangedListener(tw);
        getCode();
    }
    TextWatcher tw = new TextWatcher(){
        //@Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after){
        }
        //@Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
        }
        //@Override
        public void afterTextChanged(Editable s){
            if(s.toString().length() == 1)
            {
                if(mEdit.get(0).isFocused())
                {
                    mEdit.get(0).clearFocus();
                    mEdit.get(1).requestFocus();
                }
                else if(mEdit.get(1).isFocused())
                {
                    mEdit.get(1).clearFocus();
                    mEdit.get(2).requestFocus();
                }
                else if(mEdit.get(2).isFocused())
                {
                    mEdit.get(2).clearFocus();
                    mEdit.get(3).requestFocus();
                }
                else if (mEdit.get(3).isFocused())
                {
                    mEdit.get(3).clearFocus();
                    mEdit.get(4).requestFocus();
                }
                else if (mEdit.get(4).isFocused())
                {
                    mEdit.get(4).clearFocus();
                    mEdit.get(5).requestFocus();
                }
                else if (mEdit.get(5).isFocused())
                {
                    mEdit.get(5).clearFocus();
                    String code = mEdit.get(0).getText().toString()+mEdit.get(1).getText().toString()+mEdit.get(2).getText().toString()+mEdit.get(3).getText().toString()+mEdit.get(4).getText().toString()+mEdit.get(5).getText().toString();
                   /* Map<String,String> map = new HashMap<>();
                    map.put("GUID",guid);
                    map.put(Constant.MOBILE,mobile);
                    map.put(Constant.KEY,key);
                    map.put("MD5Pwd", ToolsUtils.getInstance().getMD5Val(getIntent().getStringExtra("psw")));
                    map.put("PSMSKey",code);*/

                    Intent intent = new Intent(ResetPSWForgetOneActivity.this,ResetPSWForgetThreeActivity.class);
                    intent.putExtra("YZM",code);
                    startActivity(intent);
                }
            }
        }
    };
    /**
     *获取验证码
     */
    private void getCode()
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put(Constant.MOBILE,mobile);
        map.put("GUID",guid);
        map.put(Constant.KEY,key);
        map.put("idcard",getIntent().getStringExtra("SFZ"));
        RetrofitUtils.getRetrofitService()
                .getMSG("MyInfo", Config.GET_MSG, JsonUtils.getInstance().getJsonStr(map))
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
                        Log.e("YZM",getCodeBean.getErrorMsg());
                        //ToolsUtils.getInstance().toastShowStr(PayCodeYZMActivity.this,getCodeBean.getErrorMsg());
                    }
                });

    }
}
