package com.mr.truck.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mr.truck.R;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.GetUserInfoUtils;
import com.mr.truck.utils.JsonUtils;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ThreadManager;
import com.mr.truck.utils.ToolsUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/11/1.
 */

public class FaPiaoSettingActivity extends BaseActivity {
    private String fapiaoType = "";
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fapiao_setting);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.fapiao_radiogroup);
        RadioButton pintai = (RadioButton) findViewById(R.id.fapiao_pintai);
        RadioButton ziji = (RadioButton) findViewById(R.id.fapiao_yourself);
        save = (Button) findViewById(R.id.fapiao_save);
        String type = GetUserInfoUtils.getFapiaoType(FaPiaoSettingActivity.this);
        if (type.equals("0")){
            ziji.setChecked(true);
        }else if (type.equals("1")){
            pintai.setChecked(true);
        }
        save.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
    }



    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            getCheck(i,radioGroup);
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setting();
        }
    };

    private void getCheck(int i,RadioGroup radioGroup) {
        Log.e("fapiao",i+"");
        RadioButton s =(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        String str = s.getText().toString();
        if (str.equals("平台代开增值税发票")) {
            fapiaoType = "1"; //平台开票
        } else if (str.equals("自行向客户开具增值税发票")) {
            fapiaoType = "0";  //自己开票
        }
    }

    private void setting() {
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("GUID", GetUserInfoUtils.getGuid(FaPiaoSettingActivity.this));
                map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(FaPiaoSettingActivity.this));
                map.put(Constant.KEY, GetUserInfoUtils.getKey(FaPiaoSettingActivity.this));
                map.put("MInvoiceType", fapiaoType);//发票类型
                RetrofitUtils.getRetrofitService()
                        .fapiaoSetting(Constant.MYINFO_PAGENAME, Config.FAPIAOSETTING, JsonUtils.getInstance().getJsonStr(map))
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
                                ToolsUtils.getInstance().toastShowStr(FaPiaoSettingActivity.this,getCodeBean.getErrorMsg());
                                if (getCodeBean.getErrorCode().equals("200")){
                                    Log.e("fapiaosetting",fapiaoType);
                                    ToolsUtils.putString(FaPiaoSettingActivity.this,Constant.FAPIAOTYPE,fapiaoType+"");
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
