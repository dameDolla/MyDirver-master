package com.app.gaolonglong.fragmenttabhost.model;


import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.bean.RequestPostBody;
import com.app.gaolonglong.fragmenttabhost.utils.RetrofitUtils;
import com.app.gaolonglong.fragmenttabhost.utils.ToolsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
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
