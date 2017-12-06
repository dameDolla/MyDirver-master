package com.mr.truck.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mr.truck.R;
import com.mr.truck.config.Constant;

/**
 * Created by yanqi on 2017/10/19.
 */

public class IntroduceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);
        init();
    }



    private void init()
    {
        initView();
    }
    private void initView()
    {
        WebView webView = (WebView) findViewById(R.id.intro_webview);
        String url = Constant.ABOUTUSURL;
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webView.loadUrl(url);


    }
}
