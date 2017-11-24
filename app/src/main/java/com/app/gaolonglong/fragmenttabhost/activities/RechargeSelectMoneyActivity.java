package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.pay.WXPayConfig;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.WXPayBean;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.PayUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/11/3.
 */

public class RechargeSelectMoneyActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.top_title)
    public TextView title;
    @BindViews({R.id.selectmoney_one_rg,R.id.selectmoney_two_rg})
    public List<RadioGroup> rg;
    @BindViews({R.id.select_money_one_1,R.id.select_money_one_2,R.id.select_money_one_3,
                R.id.select_money_two_1,R.id.select_money_two_2,R.id.select_money_two_3})
    public List<RadioButton> rb;
    @BindView(R.id.select_money_et)
    public EditText et;
    @BindView(R.id.select_money_bt)
    public Button bt;
    private String selectMoney;
    private String guid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_selectmoney);
        ButterKnife.bind(this);
        title.setText("充值");
        bt.setOnClickListener(this);
        guid = GetUserInfoUtils.getGuid(RechargeSelectMoneyActivity.this);
        rg.get(0).setOnCheckedChangeListener(new OnMySelectOneChangeListener());
        rg.get(1).setOnCheckedChangeListener(new OnMySelectTwoChangeListener());
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bt.setText("充值金额"+et.getText()+"元");
                bt.setBackgroundResource(R.drawable.login_bt_);
                bt.setEnabled(true);
            }
        });

    }


    private class OnMySelectOneChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i)
            {
                case R.id.select_money_one_1:
                    if (rb.get(0).isChecked()){
                        rg.get(1).clearCheck();
                        selectMoney = rb.get(0).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+rb.get(0).getText()+"元");
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_one_2:
                    if (rb.get(1).isChecked()){
                        selectMoney = rb.get(1).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+rb.get(1).getText()+"元");
                        rg.get(1).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_one_3:
                    if (rb.get(2).isChecked()){
                        rg.get(1).clearCheck();
                        selectMoney = rb.get(2).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+rb.get(2).getText()+"元");
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
            }
        }
    }
    private class OnMySelectTwoChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i)
            {
                case R.id.select_money_two_1:
                    if (rb.get(3).isChecked()){
                        selectMoney = rb.get(3).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+rb.get(3).getText()+"元");
                        rg.get(0).clearCheck();
                        //et.setText("");
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_two_2:
                    if (rb.get(4).isChecked()){
                        selectMoney = rb.get(4).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+selectMoney+"元");
                        rg.get(0).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_two_3:
                    if (rb.get(5).isChecked()){
                        selectMoney = rb.get(5).getText().toString();
                        et.setText(selectMoney);
                        et.setSelection(selectMoney.length());
                        bt.setText("充值金额"+ selectMoney +"元");
                        rg.get(0).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
            }
        }
    }
    private void toPay(String money)
    {
        String md5str  = ToolsUtils.getInstance().getMD5Val(money);
        Log.i("md5-str",md5str);
        RetrofitUtils.getRetrofitService()
                .chongZhi(money,md5str, guid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WXPayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("recharge",e.getMessage());
                    }

                    @Override
                    public void onNext(WXPayBean wxPayBean) {
                    ToolsUtils.getInstance().toastShowStr(RechargeSelectMoneyActivity.this,wxPayBean.getErrorMsg()+"-"+wxPayBean.getErrorCode());
                        if (wxPayBean.getErrorCode().equals("200")){
                            WXPayBean.DataBean wxdata = wxPayBean.getData().get(0);
                            IWXAPI api = WXAPIFactory.createWXAPI(RechargeSelectMoneyActivity.this, WXPayConfig.APP_ID);
                            api.registerApp(WXPayConfig.APP_ID);
                            PayReq req = new PayReq();
                            req.appId = WXPayConfig.APP_ID;
                            req.partnerId = wxdata.getPartnerid();
                            req.prepayId = wxdata.getPrepayid();
                            req.packageValue = wxdata.getPackageX();
                            req.nonceStr = wxdata.getNoncestr();
                            req.timeStamp = wxdata.getTimestamp();
                            req.sign = wxdata.getSign();
                            boolean a = api.sendReq(req);
                            ToolsUtils.getInstance().toastShowStr(RechargeSelectMoneyActivity.this,a+"");
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_money_bt:
                String money = et.getText().toString();

                toPay(money);
                break;
        }
    }
}
