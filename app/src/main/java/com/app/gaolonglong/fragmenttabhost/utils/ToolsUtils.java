package com.app.gaolonglong.fragmenttabhost.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by yanqi on 2017/8/1.
 */

public class ToolsUtils {

    private ToolsUtils(){}

    public static Toast mToast;

    private static final ToolsUtils tools = new ToolsUtils();

    public  static ToolsUtils getInstance()
    {
        return tools;
    }

    /**
     * 控制Toast显示
     * 文字提示
     */
    public void toastShowStr(Context context, String str)
    {
        if(mToast == null)
        {
            mToast = Toast.makeText(context,str, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(str);
        }
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }
    /**
     * Toast 图片文字提示
     */
    public void ToastShowImgStr(Context context, String str, int resImg)
    {
        if(mToast == null)
        {
            mToast = Toast.makeText(context,str, Toast.LENGTH_SHORT);
        }
        else
        {
            mToast.setText(str);
        }
        LinearLayout linear = (LinearLayout)mToast.getView();
        ImageView img = new ImageView(context);
        img.setImageResource(resImg);
        linear.addView(img);
        mToast.show();
    }
}
