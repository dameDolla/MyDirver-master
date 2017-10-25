package com.app.gaolonglong.fragmenttabhost.utils;

import android.content.Context;

import com.app.gaolonglong.fragmenttabhost.activities.DiaoduRenzheng3Activity;
import com.app.gaolonglong.fragmenttabhost.bean.GetCodeBean;
import com.app.gaolonglong.fragmenttabhost.config.Config;
import com.app.gaolonglong.fragmenttabhost.config.Constant;
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



}
