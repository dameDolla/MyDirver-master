package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/19.
 */

public class PayCodeYZMActivity extends BaseActivity {

    @BindView(R.id.top_title)
    public TextView title;

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
    @BindView(R.id.setpaycode_yzm_tel)
    public TextView tel;
    @BindViews({R.id.yzm_one,R.id.yzm_two,R.id.yzm_three,R.id.yzm_four})
    public List<EditText> mEdit;
    @BindViews({R.id.yzm_time,R.id.yzm_resend})
    public List<TextView> mText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_paycode_yanzhengma);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        title.setText("设置支付密码");
        String mobile = ToolsUtils.getString(PayCodeYZMActivity.this, Constant.MOBILE,"");
        tel.setText(mobile);
        mEdit.get(0).addTextChangedListener(tw);
        mEdit.get(1).addTextChangedListener(tw);
        mEdit.get(2).addTextChangedListener(tw);
        mEdit.get(3).addTextChangedListener(tw);
        MyCounDownTimer timer = new MyCounDownTimer(60000, 1000);
        timer.start();
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
                else if (mEdit.get(3).isFocusable())
                {
                    mEdit.get(3).clearFocus();
                    ToolsUtils.getInstance().toastShowStr(PayCodeYZMActivity.this,"done");
                }
            }
        }
    };
    /**
     *获取验证码
     */
    private void getCode(String json)
    {
        RetrofitUtils.getRetrofitService()
                .getCode("YZ", Config.APPLOGIN_SMS,json)
                .enqueue(new Callback<GetCodeBean>() {
                    @Override
                    public void onResponse(Call<GetCodeBean> call, Response<GetCodeBean> response) {
                        GetCodeBean s = response.body();
                        ToolsUtils.getInstance().toastShowStr(PayCodeYZMActivity.this,s.getErrorMsg());
                    }

                    @Override
                    public void onFailure(Call<GetCodeBean> call, Throwable t) {

                    }
                });

    }
    /**
     * 添加支付密码
     * @param json
     */
    private void setPayCode(String json)
    {
        RetrofitUtils.getRetrofitService()
                .setPayCode(Constant.MYINFO_PAGENAME,"",json)
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
                        ToolsUtils.getInstance().toastShowStr(PayCodeYZMActivity.this,getCodeBean.getErrorMsg());
                    }
                });
    }

    private class MyCounDownTimer extends CountDownTimer {
        public MyCounDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            mText.get(0).setClickable(false);
            mText.get(0).setText(l / 1000 + " S后重新获取");
            mText.get(0).setTextSize(18);
        }

        @Override
        public void onFinish() {
            //重新给Button设置文字

        }
    }

}
