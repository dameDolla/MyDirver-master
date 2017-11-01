package com.app.gaolonglong.fragmenttabhost.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

/**
 * Created by yanqi on 2017/10/31.
 */

public class CancelMissionDialog extends Dialog {
    Activity context;
    private View.OnClickListener mClickListener;
    public  TextView status;
    public  EditText reason;
    public  Button cancel;

    public CancelMissionDialog(Activity context) {
        super(context);
        this.context = context;
    }
    public CancelMissionDialog(Activity context, int theme, View.OnClickListener clickListener){
        super(context,theme);
        this.mClickListener = clickListener;
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_mission_dialog);
        init();
    }
    private void init()
    {
        status = (TextView) findViewById(R.id.cancel_mission_dialog_status);
        reason = (EditText) findViewById(R.id.cancel_mission_dialog_reason);
        cancel = (Button)  findViewById(R.id.cancel_mission_dialog_cancel);

        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        cancel.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
    public void showDialog(Activity context){
        CancelMissionDialog dialog = new CancelMissionDialog(context,R.style.dialog,onClickListener);
        dialog.show();
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
