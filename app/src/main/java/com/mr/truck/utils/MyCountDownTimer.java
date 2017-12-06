package com.mr.truck.utils;

import android.os.CountDownTimer;

/**
 * Created by yanqi on 2017/8/16.
 */

public class MyCountDownTimer extends CountDownTimer {
    long time;
    long jiange;
    int id;

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }


    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {

    }
}
