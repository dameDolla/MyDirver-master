package com.mr.truck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.mr.truck.R;
import com.mr.truck.adapter.CompanyInfoAdapters;
import com.mr.truck.bean.CompanyInfoBean;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.utils.LoadingDialog;
import com.mr.truck.utils.RetrofitUtils;
import com.mr.truck.utils.ToolsUtils;
import com.mr.truck.view.MyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.OkHttpClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/24.
 */

public class CarGroupRenzheng2Activity extends BaseActivity {

    private OkHttpClient client;

    private String s2, s3;

    private LoadingDialog dialog;

    private List<CompanyInfoBean.DataBean> list = new ArrayList<CompanyInfoBean.DataBean>();

    private String mobile;
    private String guid;
    private String key;

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindView(R.id.renzheng_company_name)
    public EditText name;



    @BindView(R.id.rcv_cominfo)
    public RecyclerView recyclerView;
    private CompanyInfoAdapters adapter;


    @OnClick(R.id.title_back)
    public void back() {
        finish();
    }
    @OnClick(R.id.title_back_txt)
    public void backs()
    {
        finish();
    }

    @OnClick(R.id.submit_info)
    public void submit()
    {
        submitInfo();
    }

    @OnTextChanged(R.id.renzheng_company_name)
    public void change() {
        dialog.show();
        String str = name.getText().toString();
        JSONObject json = new JSONObject();
        try {
            //s3 = URLEncoder.encode(str,"utf-8");
            //s3 = DESUtil.encryptDES(str,"PYLF");
            json.put("GUID", guid);
            json.put("mobile", mobile);
            json.put(Constant.KEY, key);
            json.put("companyName", str);
            json.put("companyID", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            s2 = new String((json.toString()).getBytes("utf-8"), "utf-8");
            String s = ToolsUtils.string2Unicode(json.toString());


        } catch (UnsupportedEncodingException e) {

        }
        MyLinearLayoutManager manager = new MyLinearLayoutManager(CarGroupRenzheng2Activity.this);

        recyclerView.setLayoutManager(manager);
        adapter = new CompanyInfoAdapters(list,CarGroupRenzheng2Activity.this);
        recyclerView.setAdapter(adapter);

        getCompanyInfo(json.toString());


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargroup_renzheng_two);
        ButterKnife.bind(this);
        init();
    }



    private void init() {
        initView();
    }

    private void initView() {
        mText.get(0).setText("车队司机认证");
        dialog = LoadingDialog.showDialog(CarGroupRenzheng2Activity.this);
        mobile = ToolsUtils.getString(CarGroupRenzheng2Activity.this, Constant.MOBILE, "");
        guid = ToolsUtils.getString(CarGroupRenzheng2Activity.this, Constant.LOGIN_GUID, "");
        key = ToolsUtils.getString(CarGroupRenzheng2Activity.this, Constant.KEY, "");

    }


    /**
     * 查询公司信息
     */
    private void getCompanyInfo(String jsonVal) {

        RetrofitUtils.getRetrofitService()
                .getConpanyInfo("YZ", Config.GET_COMPANYINFO, jsonVal.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyInfoBean>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        //ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this, "cargrouperror:"+e.getMessage());

                    }

                    @Override
                    public void onNext(final CompanyInfoBean getCodeBean) {

                        dialog.dismiss();
                        list.clear();
                        list.addAll(getCodeBean.getData());
                        adapter.notifyDataSetChanged();
                        if (list.size()==0){
                            ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,"无公司信息");
                        }
                        //ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,"公司名车："+getCodeBean.getData().get(0).getCompanyName());
                    }
                });
    }
    /**
     * 提交绑定公司
     */
    private void submitInfo()
    {
       Map<String,String> map = adapter.getMap();
        String companyCode = map.get("cguid");
       // ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,companyCode);
        //name.setText(companyCode);
        JSONObject json = new JSONObject();
        try {
            json.put("GUID",guid);
            json.put("mobile",mobile);
            json.put(Constant.KEY,key);
            json.put("CompanyGUID",companyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitUtils.getRetrofitService().bindCompany("YZ",Config.BIND_COMPANY,json.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("cargrouperror",e.getMessage());
                    }

                    @Override
                    public void onNext(GetCodeBean response) {
                       //ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,response.getErrorMsg());

                        startActivity(new Intent(CarGroupRenzheng2Activity.this,CommitSuccessActivity.class));
                    }
                });
    }

}
