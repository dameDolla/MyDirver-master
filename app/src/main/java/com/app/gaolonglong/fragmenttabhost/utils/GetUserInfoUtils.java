package com.app.gaolonglong.fragmenttabhost.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.activities.DiaoduRenzheng3Activity;
import com.app.gaolonglong.fragmenttabhost.activities.LoginActivity;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
import com.app.gaolonglong.fragmenttabhost.view.CommomDialog;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

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
        if (vtruename.equals("9") || vcompanyname.equals("9"))
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

}
