package com.app.gaolonglong.fragmenttabhost.utils;

import android.content.Context;

import com.app.gaolonglong.fragmenttabhost.activities.pay.WXPayConfig;
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

    public IWXAPI getAPI(Context context)
    {
        return WXAPIFactory.createWXAPI(context, WXPayConfig.APP_ID);
    }
    public static void getInfoFramServer(final Context context)
    {
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                Map<String,String> map = new HashMap<String, String>();
                map.put("GUID",GetUserInfoUtils.getGuid(context));
                map.put(Constant.MOBILE,GetUserInfoUtils.getMobile(context));
                map.put(Constant.KEY,GetUserInfoUtils.getKey(context));
            }
        });
    }
    public void WxPay(Context context){
        IWXAPI api = getAPI(context);
        api.registerApp(WXPayConfig.APP_ID);
        PayReq req = new PayReq();
        req.appId = WXPayConfig.APP_ID;
        req.partnerId = "";
        req.prepayId = "";
        req.packageValue = "";
        req.nonceStr = "";
        req.timeStamp = "";
        req.sign = "";
        api.sendReq(req);
    }
}
