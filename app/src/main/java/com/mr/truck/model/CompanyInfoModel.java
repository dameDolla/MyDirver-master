package com.mr.truck.model;


import com.mr.truck.bean.CompanyInfoBean;
import com.mr.truck.bean.GetCodeBean;
import com.mr.truck.bean.RequestPostBody;
import com.mr.truck.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/8/25.
 */

public class CompanyInfoModel {

    public static CompanyInfoModel companyInfoModel = null;

    private RequestPostBody body = null;

    private GetCodeBean code = new GetCodeBean();

    private final List<String> list = new ArrayList<String>();

    public static CompanyInfoModel getInstance()
    {
        if (companyInfoModel == null)
        {
            companyInfoModel = new CompanyInfoModel();
        }
        return companyInfoModel;
    }

    public List<String> getCompanyInfo(String md5, String jsonVal, String pageName, String methos)
    {
        body = new RequestPostBody(pageName,methos,jsonVal,md5);
        /*body.setPageName(pageName);
        body.setMethodName(methos);
        body.setMDSValue(md5);
        body.setJsonValue(jsonVal);*/
        RetrofitUtils.getRetrofitService()
                .getConpanyInfo("","","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //code.setErrorCode("fail");

                    }

                    @Override
                    public void onNext(final CompanyInfoBean getCodeBean) {
                        list.add(getCodeBean.getErrorCode());
                        list.add(getCodeBean.getErrorMsg());
                        if (getCodeBean.getErrorCode() == "200")
                        {


                        }
                    }
                });
        return list;
    }
}
