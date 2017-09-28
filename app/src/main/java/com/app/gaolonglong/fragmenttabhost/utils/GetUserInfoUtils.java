package com.app.gaolonglong.fragmenttabhost.utils;

import android.content.Context;

import com.app.gaolonglong.fragmenttabhost.config.Constant;

/**
 * Created by yanqi on 2017/9/27.
 */

public class GetUserInfoUtils {
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
}
