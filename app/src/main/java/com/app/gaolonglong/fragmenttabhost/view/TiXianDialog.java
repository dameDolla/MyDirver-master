package com.app.gaolonglong.fragmenttabhost.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;
import com.app.gaolonglong.fragmenttabhost.utils.GetUserInfoUtils;

/**
 * Created by yanqi on 2017/11/15.
 */

public class TiXianDialog extends Dialog {
    private TiXianDialog tiXianDialog;
    private Activity context;
    private View.OnClickListener clickListener;
    public EditText money;
    public  TextView name;
    public TextView num;
    public TextView type;

    public TiXianDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    public TiXianDialog(@NonNull Activity context, @StyleRes int themeResId, View.OnClickListener onClickListener) {
        super(context, themeResId);
        this.context = context;
        this.clickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian_dialog);
        Window dialogWindow = this.getWindow();
        money = (EditText) findViewById(R.id.tixian_money);
        TextView submit = (TextView) findViewById(R.id.tixian_submit);
        name = (TextView) findViewById(R.id.tixian_cardname);
        num = (TextView) findViewById(R.id.tixian_cardnum);
        type = (TextView) findViewById(R.id.tixian_cardtype);
        WindowManager m = context.getWindowManager();
        name.setText(GetUserInfoUtils.getBankCardUsername(context));
        num.setText(GetUserInfoUtils.getBankCardNum(context));
        type.setText(GetUserInfoUtils.getBankCardType(context));
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        submit.setOnClickListener(clickListener);
    }

}
