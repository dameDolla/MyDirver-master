package com.app.gaolonglong.fragmenttabhost.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.gaolonglong.fragmenttabhost.R;

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
        String url = "http://mp.weixin.qq.com/s/3y9jffreSoTgcZv86f2hXg";
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowContentAccess(true);

    }
}
