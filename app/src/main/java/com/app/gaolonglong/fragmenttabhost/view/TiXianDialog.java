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

import com.app.gaolonglong.fragmenttabhost.R;

/**
 * Created by yanqi on 2017/11/15.
 */

public class TiXianDialog extends Dialog {
    private TiXianDialog tiXianDialog;
    private Activity context;
    public TiXianDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    public TiXianDialog(@NonNull Activity context, @StyleRes int themeResId/*, View.OnClickListener onClickListener*/) {
        super(context, themeResId);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian_dialog);
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
    }
}
