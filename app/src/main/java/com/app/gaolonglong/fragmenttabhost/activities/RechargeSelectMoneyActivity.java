package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by yanqi on 2017/11/3.
 */

public class RechargeSelectMoneyActivity extends BaseActivity {
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_selectmoney);
        ButterKnife.bind(this);
        title.setText("充值");
        rg.get(0).setOnCheckedChangeListener(new OnMySelectOneChangeListener());
        rg.get(1).setOnCheckedChangeListener(new OnMySelectTwoChangeListener());
    }
    private class OnMySelectOneChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i)
            {
                case R.id.select_money_one_1:
                    if (rb.get(0).isChecked()){
                        rg.get(1).clearCheck();
                        bt.setText("充值金额"+rb.get(0).getText()+"元");
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_one_2:
                    if (rb.get(1).isChecked()){
                        bt.setText("充值金额"+rb.get(1).getText()+"元");
                        rg.get(1).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_one_3:
                    if (rb.get(2).isChecked()){
                        rg.get(1).clearCheck();
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
                        bt.setText("充值金额"+rb.get(3).getText()+"元");
                        rg.get(0).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_two_2:
                    if (rb.get(4).isChecked()){
                        bt.setText("充值金额"+rb.get(4).getText()+"元");
                        rg.get(0).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
                case R.id.select_money_two_3:
                    if (rb.get(5).isChecked()){
                        bt.setText("充值金额"+rb.get(5).getText()+"元");
                        rg.get(0).clearCheck();
                        bt.setBackgroundResource(R.drawable.login_bt_);
                        bt.setEnabled(true);
                    }
                    break;
            }
        }
    }
}
