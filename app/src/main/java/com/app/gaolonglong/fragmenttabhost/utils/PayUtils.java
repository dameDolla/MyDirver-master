package com.app.gaolonglong.fragmenttabhost.utils;

import android.content.Context;

import com.app.gaolonglong.fragmenttabhost.activities.pay.WXPayConfig;
import com.app.gaolonglong.fragmenttabhost.bean.WXPayBean;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanqi on 2017/11/1.
 */

public class PayUtils {
    private PayUtils mPayUtils = null;

    public PayUtils getPayUtils()
    {
        if (mPayUtils == null){
            mPayUtils = new PayUtils();
        }
        return mPayUtils;
    }

    public static IWXAPI getAPI(Context context)
    {
        return WXAPIFactory.createWXAPI(context, WXPayConfig.APP_ID);
    }
    public static void getInfoFramServer(final Context context)
    {



    }
    public static void WxPay(Context context, WXPayBean wxPayBean){
        WXPayBean.DataBean data = wxPayBean.getData().get(0);
        IWXAPI api = getAPI(context);
        api.registerApp(WXPayConfig.APP_ID);
        PayReq req = new PayReq();
        /*req.appId = WXPayConfig.APP_ID;
        req.partnerId = "";
        req.prepayId = data.getPrepay_id();
        req.packageValue = "";
        req.nonceStr = data.getNonce_str();
        req.timeStamp = "";
        req.sign = data.getSign();*/
        api.sendReq(req);
    }


}
