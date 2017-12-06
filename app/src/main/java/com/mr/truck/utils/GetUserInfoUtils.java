package com.mr.truck.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.mr.truck.R;
import com.mr.truck.activities.LoginActivity;
import com.mr.truck.activities.SplashActivity;
import com.mr.truck.bean.LoginBean;
import com.mr.truck.config.Config;
import com.mr.truck.config.Constant;
import com.mr.truck.view.CommomDialog;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yanqi on 2017/9/27.
 */

public class GetUserInfoUtils {

    public static String getUserName(Context context)
    {
        return ToolsUtils.getString(context,Constant.USERNAME,"");
    }
    /**
     * 获取银行卡开户人
     * @param context
     * @return
     */
    public static String getBankCardUsername(Context context){
        return ToolsUtils.getString(context,Constant.BANKCARDUSERNAME,"");
    }
    /**
     * 获取银行卡号
     * @param context
     * @return
     */
    public static String getBankCardNum(Context context){
        return ToolsUtils.getString(context,Constant.BANKCARDNUM,"");
    }
    /**
     * 获取银行卡类型
     * @param context
     * @return
     */
    public static String getBankCardType(Context context){
        return ToolsUtils.getString(context,Constant.BANKCARDTYPE,"");
    }
    /**
     * 获取身份证号
     * @param context
     * @return
     */
    public static String getIdCard(Context context){
        return ToolsUtils.getString(context,"idcard","");
    }
    /**
     * 获取钱包余额
     * @param context
     * @return
     */
    public static String getMoney(Context context){
        return ToolsUtils.getString(context,"money","");
    }

    /**
     * 获取设置的发票类型
     * @param context
     * @return
     */
    public static String getFapiaoType(Context context){
        return ToolsUtils.getString(context,Constant.FAPIAOTYPE,"");
    }

    /**
     * 获取接单次数
     * @param context
     * @return
     */
    public static String getDriverbill(Context context){
        return ToolsUtils.getString(context,Constant.DRIVERBILL,"");
    }
    /**
     * 获取公司guid
     * @param context
     * @return
     */
    public static String getCompanyGuid(Context context)
    {
        return ToolsUtils.getString(context,Constant.COMPANYGUID,"");
    }
    /**
     * 获取用户类型
     * @param context
     * @return
     */
    public static String getUserType(Context context)
    {
        return ToolsUtils.getString(context, Constant.USRE_TYPE,"");
    }
    /**
     * 判断个人或者公司是否认证
     */
    public static boolean isRenzheng(Context context)
    {
        String vtruename = GetUserInfoUtils.getVtrueName(context);
        String vcompanyname = GetUserInfoUtils.getVcompany(context);
        String usertype = GetUserInfoUtils.getUserType(context);
        /*if (usertype.equals("2")){

        }*/
        if (vtruename.equals("9") /*|| vcompanyname.equals("9")*/)
        {
            return true;
        }else {
            return false;
        }
    }
    /**
     * 是否认证
     * @param context
     * @return
     */
    public static String getVtrueName(Context context)
    {
        return ToolsUtils.getString(context,Constant.VTRUENAME,"");
    }

    /**
     * 公司是否认证
     * @param context
     * @return
     */
    public static String getVcompany(Context context)
    {
        return ToolsUtils.getString(context,Constant.VCOMPANY,"");
    }
    /**
     * 获取guid
     * @param context
     * @return
     */
    public static String getGuid(Context context)
    {
        return ToolsUtils.getString(context,Constant.LOGIN_GUID,"");
    }
    /**
     * 获取mobile
     * @param context
     * @return
     */
    public static String getMobile(Context context)
    {
        return ToolsUtils.getString(context,Constant.MOBILE,"");
    }
    /**
     * 获取key
     * @param context
     * @return
     */
    public static String getKey(Context context)
    {
        return ToolsUtils.getString(context,Constant.KEY,"");
    }
    /**
     * 获取用户头像
     * @param context
     * @return
     */
    public static String getIcon(Context context)
    {
        return ToolsUtils.getString(context,Constant.HEADLOGO,"");
    }

    /**
     * 获取图片的url
     * @param guid
     * @param imgType
     * @return
     */
    public static String getImg(String guid,String imgType)
    {
        String url = Config.baseURL+Config.GET_IMG+"?MemberGUID="+ guid+"&ImgType="+imgType;
        return url;
    }

    public static void checkKeyValue(final Context context, String errorcode)
    {
        if (!TextUtils.isEmpty(errorcode)){
            if (errorcode.equals(Constant.KEYISWRONG)){//key值不符
                String s = "您的账号已在别处登录,是否本人操作。如非不是本人操作，请尽快处理！";
                CommomDialog dialog = new CommomDialog(context, R.style.dialog, s, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                        if (confirm)
                        {
                            ToolsUtils.getInstance().loginOut(context);
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    }
                });
                dialog.setTitle("警告");
                dialog.show();
            }
        }
    }
    public static  void checkLogin(final Context context) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("GUID", GetUserInfoUtils.getGuid(context));
        map.put(Constant.MOBILE, GetUserInfoUtils.getMobile(context));
        map.put(Constant.KEY, GetUserInfoUtils.getKey(context));
        final String jsonVal = JsonUtils.getInstance().getJsonStr(map);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RetrofitUtils.getRetrofitService()
                        .checkLogin("Login", Config.CHECK_LOGIN, jsonVal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginBean>() {
                            @Override
                            public void onCompleted() {

                            }


                            @Override
                            public void onError(Throwable e) {
                                //Log.e("splasherror",e.getMessage());
                                //ToolsUtils.getInstance().loginOut(context);
                            }

                            @Override
                            public void onNext(LoginBean loginBean) {
                                // Log.e("splashinfo",loginBean.getErrorMsg()+"---"+loginBean.getErrorCode());
                                // ToolsUtils.getInstance().toastShowStr(context,loginBean.getErrorCode());
                                if (loginBean.getErrorCode().equals("202")||loginBean.getErrorCode().equals("220")||loginBean.getErrorCode().equals("203")) {
                                    ToolsUtils.getInstance().loginOut(context);
                                    context.startActivity(new Intent(context,LoginActivity.class));

                                } else if (loginBean.getErrorCode().equals("200")) {
                                    ToolsUtils.putString(context,Constant.LOGIN_GUID,loginBean.getData().get(0).getGUID()+"");
                                    ToolsUtils.putString(context, Constant.KEY, loginBean.getData().get(0).getSecreKey()+"");
                                    ToolsUtils.putString(context, Constant.USRE_TYPE, loginBean.getData().get(0).getUsertype()+"");
                                    ToolsUtils.putString(context, Constant.VTRUENAME, loginBean.getData().get(0).getVtruename()+"");
                                    ToolsUtils.putString(context, Constant.HEADLOGO, loginBean.getData().get(0).getAvatarAddress()+"");
                                    ToolsUtils.putString(context,Constant.COUNT,loginBean.getData().get(0).getMoney()+"");
                                    ToolsUtils.putString(context,Constant.VCOMPANY,loginBean.getData().get(0).getVcompany()+"");
                                    ToolsUtils.putString(context,Constant.USERNAME,loginBean.getData().get(0).getTruename()+"");
                                    ToolsUtils.putString(context,Constant.COMPANYGUID,loginBean.getData().get(0).getCompanyGUID()+"");
                                    ToolsUtils.putString(context,Constant.DRIVERBILL,loginBean.getData().get(0).getDriverbill()+"");
                                    ToolsUtils.putString(context,Constant.FAPIAOTYPE,loginBean.getData().get(0).getMInvoiceType()+"");
                                    ToolsUtils.putString(context,Constant.BANKCARDNUM,loginBean.getData().get(0).getAccount()+"");
                                    ToolsUtils.putString(context,Constant.BANKCARDUSERNAME,loginBean.getData().get(0).getBankUserName()+"");
                                    ToolsUtils.putString(context,Constant.BANKCARDTYPE,loginBean.getData().get(0).getBanktype()+"");
                                    ToolsUtils.putString(context,"money",loginBean.getData().get(0).getMoney()+"");
                                    ToolsUtils.putString(context,"idcard",loginBean.getData().get(0).getIdcard()+"");

                                    //ToolsUtils.getInstance().toastShowStr(context,loginBean.getData().get(0).getAvatarAddress());
                                }
                                //  toMain();
                            }
                        });
            }
        }).start();

    }
}
