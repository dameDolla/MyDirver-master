package com.app.gaolonglong.fragmenttabhost.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.bean.CompanyInfoBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;
import com.app.gaolonglong.fragmenttabhost.utils.JsonUtils;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ThreadManager;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/10/19.
 */

public class CompanyInfoActivity extends BaseActivity implements View.OnClickListener{

    private TextView name;
    private TextView status;
    private TextView tel,qyxydm,username;
    private SimpleDraweeView yyzz;
    private SimpleDraweeView dlysxk;
    private SimpleDraweeView sqwt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_info);
        init();
    }
    private void init()
    {
        initView();
    }
    private void initView()
    {
        TextView title = (TextView) findViewById(R.id.top_title);
        ImageView back = (ImageView) findViewById(R.id.title_back);
        TextView backs = (TextView) findViewById(R.id.title_back_txt);
        name = (TextView) findViewById(R.id.company_info_name);
        username = (TextView) findViewById(R.id.company_info_username);
        status = (TextView) findViewById(R.id.company_info_status);
        tel = (TextView) findViewById(R.id.company_info_tel);
        qyxydm = (TextView) findViewById(R.id.company_info_qyxydm);
        SimpleDraweeView logo = (SimpleDraweeView) findViewById(R.id.company_info_logo);
        yyzz = (SimpleDraweeView) findViewById(R.id.company_info_yyzz);
        dlysxk = (SimpleDraweeView) findViewById(R.id.company_info_ysxkz);
        sqwt = (SimpleDraweeView) findViewById(R.id.company_info_sqwt);
        String url = ToolsUtils.getString(CompanyInfoActivity.this,Constant.HEADLOGO,"");
        logo.setImageURI(url);
        String yyzzurl = GetUserInfoUtils.getImg(GetUserInfoUtils.getGuid(CompanyInfoActivity.this),"9");
        String dlysxkurl = GetUserInfoUtils.getImg(GetUserInfoUtils.getGuid(CompanyInfoActivity.this),"19");
        String sqwturl = GetUserInfoUtils.getImg(GetUserInfoUtils.getGuid(CompanyInfoActivity.this),"20");

        getHttpBitmap(yyzzurl,yyzz);
        getHttpBitmap(dlysxkurl,dlysxk);
        getHttpBitmap(sqwturl,sqwt);

        title.setText("公司信息");
        back.setOnClickListener(this);
        backs.setOnClickListener(this);


        String companyguid = GetUserInfoUtils.getCompanyGuid(CompanyInfoActivity.this);
        if (!TextUtils.isEmpty(companyguid)){
            getCompanyInfo();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_back_txt:
                finish();
                break;
        }
    }
    private void getCompanyInfo(){
        String companyGuid = TextUtils.isEmpty(GetUserInfoUtils.getCompanyGuid(CompanyInfoActivity.this))?"":GetUserInfoUtils.getCompanyGuid(CompanyInfoActivity.this);
        Map<String,String> map = new HashMap<>();
        map.put("GUID",GetUserInfoUtils.getGuid(CompanyInfoActivity.this));
        map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(CompanyInfoActivity.this));
        map.put(Constant.KEY,GetUserInfoUtils.getKey(CompanyInfoActivity.this));
        map.put("companyName", "");
        map.put("companyID", "-1");
        map.put("companysGUID", companyGuid);
        RetrofitUtils.getRetrofitService()
                .getConpanyInfo("YZ", Config.GET_COMPANYINFO, JsonUtils.getInstance().getJsonStr(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CompanyInfoBean companyInfoBean) {
                        Log.e("companyinfo",companyInfoBean.getErrorCode()+"--"+companyInfoBean.getErrorMsg());
                        if (companyInfoBean.getErrorCode().equals("200")){
                            setView(companyInfoBean);
                        }
                    }
                });
    }
    private void setView(CompanyInfoBean bean){
        CompanyInfoBean.DataBean data = bean.getData().get(0);
        name.setText(data.getCompanyName());
        tel.setText(data.getPhone());
        qyxydm.setText(data.getCompanycode());
        username.setText(data.getPerson());
    }
    /**
     * 从服务器取图片
     *http://bbs.3gstdy.com
     * @param url
     * @return
     */
    public void getHttpBitmap(final String url, final SimpleDraweeView s) {

        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                URL myFileUrl = null;
                Bitmap bitmap = null;
                try {
                    //Log.d(TAG, url);
                    myFileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setConnectTimeout(0);
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    final Bitmap finalBitmap = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int count = finalBitmap.getByteCount();
                            if (count != Constant.NOBYTECOUNT){
                                s.setImageURI(Uri.parse(url));
                            }else {
                                s.setImageResource(R.drawable.pic_loaderror);
                            }


                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        //return bitmap.getByteCount();
    }
}
