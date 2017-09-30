package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yanqi on 2017/8/31.
 */

public class CommitSuccessActivity extends BaseActivity {

    @BindView(R.id.top_title)
    public TextView title;

    @OnClick(R.id.title_back)
    public void back()
    {
        finish();
    }

    @BindView(R.id.success_tiaozhuan)
    public TextView tiaozhuan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renzheng_commit_success);
        ButterKnife.bind(this);
        init();
    }
    private void init()
    {
        MyCounDownTimer timer = new MyCounDownTimer(3000, 1000);
        timer.start();
    }
    private class MyCounDownTimer extends CountDownTimer {
        public MyCounDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tiaozhuan.setText(l / 1000 + "s后跳转到货源");
        }

        @Override
        public void onFinish() {
            //重新给Button设置文字

        }
    }
}
