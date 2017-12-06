package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;

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

    @BindView(R.id.setpaycode_yzm_tel)
    public TextView tel;
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
        tel.setText(mobile);
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
                    checkSms();
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
                        //Log.e("YZM",getCodeBean.getErrorMsg());
                        ToolsUtils.getInstance().toastShowStr(ResetPSWForgetOneActivity.this,getCodeBean.getErrorMsg());
                    }
                });

    }
    /**
     * 检验验证码是否正确
     */
    private void checkSms()
    {
        Map<String,String> map = new HashMap<>();
        RetrofitUtils.getRetrofitService()
                .checkSms("YZNewPwdKey",Config.CHECKSMS,JsonUtils.getInstance().getJsonStr(map))
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
                        if (getCodeBean.getErrorCode().equals("200")){
                            String code = mEdit.get(0).getText().toString()+mEdit.get(1).getText().toString()+mEdit.get(2).getText().toString()+mEdit.get(3).getText().toString()+mEdit.get(4).getText().toString()+mEdit.get(5).getText().toString();
                            Intent intent = new Intent(ResetPSWForgetOneActivity.this,ResetPSWForgetThreeActivity.class);
                            intent.putExtra("YZM",code);
                            startActivity(intent);
                        }else {
                            ToolsUtils.getInstance().toastShowStr(ResetPSWForgetOneActivity.this,getCodeBean.getErrorMsg());
                        }
                    }
                });
    }
}
