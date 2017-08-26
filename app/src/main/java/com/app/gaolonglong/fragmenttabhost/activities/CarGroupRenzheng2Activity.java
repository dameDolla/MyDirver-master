package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.RequestPostBody;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.model.CompanyInfoModel;
import com.app.gaolonglong.fragmenttabhost.utils.DESUtil;
import com.app.gaolonglong.fragmenttabhost.utils.HttpRequestUtil;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/24.
 */

public class CarGroupRenzheng2Activity extends BaseActivity {

    private OkHttpClient client;

    @BindViews({R.id.top_title})
    public List<TextView> mText;

    @BindView(R.id.renzheng_company_name)
    public EditText name;
    private String s2,s3;

    @OnTextChanged(R.id.renzheng_company_name)
    public void change()
    {
        String str = name.getText().toString();
        JSONObject json = new JSONObject();
        try {
            //s3 = URLEncoder.encode(str,"utf-8");
            s3 = DESUtil.encryptDES(str,"PYLF");
            json.put("GUID", Constant.GUID);
            json.put("mobile","15908690321");
            json.put("companyName",s3);
            json.put("companyID",-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            s2 = new String((json.toString()).getBytes("utf-8"),"utf-8");
            String s = ToolsUtils.string2Unicode(json.toString());


        }
        catch (UnsupportedEncodingException e)
        {

        }

        getCompanyInfo(ToolsUtils.getInstance().getMD5Val(json.toString()),json.toString());
        //getCompany(ToolsUtils.getInstance().getMD5Val(json.toString()),Config.GET_COMPANYINFO,"YZ",json.toString());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargroup_renzheng_two);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        mText.get(0).setText("车队司机认证");

    }

    /**
     * 查询公司信息
     */
    private void getCompanyInfo(String md5,String jsonVal)
    {
        RequestPostBody body = new RequestPostBody("YZ",Config.GET_COMPANYINFO,jsonVal,md5);

        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        RequestBody requestBodyPost  = new FormBody.Builder()
                .add("MethodName",Config.GET_COMPANYINFO)
                .add("PageName","YZ")
                .add("JsonValue",jsonVal)
                .add("MDSValue",md5)
                .build();
        Request request = new Request.Builder()
            .url(Config.baseURL+Config.host)
            .post(requestBodyPost)
            .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
               final String s = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("签名",response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
       // ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this, body.getMethodName());
       /* RetrofitUtils.getRetrofitService()
                .getConpanyInfos(Config.GET_COMPANYINFO,"YZ",jsonVal,md5)
                .enqueue(new Callback<GetCodeBean>() {
                    @Override
                    public void onResponse(Call<GetCodeBean> call, Response<GetCodeBean> response) {
                       // ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,response.message());
                    }

                    @Override
                    public void onFailure(Call<GetCodeBean> call, Throwable t) {

                    }
                });*/

        /*RetrofitUtils.getRetrofitService()
                .getConpanyInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //code.setErrorCode("fail");

                    }

                    @Override
                    public void onNext(final GetCodeBean getCodeBean) {
                       
                       ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,getCodeBean.getErrorMsg());
                    }
                });*/
    }
    private void getCompany(String md5,String method,String page,String json)
    {
        RetrofitUtils.getRetrofitService().getCompany(method,md5,json,page).enqueue(new Callback<GetCodeBean>() {
            @Override
            public void onResponse(Call<GetCodeBean> call, final Response<GetCodeBean> response) {

               // ToolsUtils.getInstance().toastShowStr(CarGroupRenzheng2Activity.this,response.message()+"");


            }

            @Override
            public void onFailure(Call<GetCodeBean> call, Throwable t) {

            }
        });
    }



}
