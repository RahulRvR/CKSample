package com.rahulrvr.cksample;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class WebClient extends WebViewClient {

    MainActivity mainActivity;
    public WebClient(MainActivity activity) {
        mainActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return shouldOverrideUrlLoading(view, url);
}

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
       // mainActivity.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
       // mainActivity.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        view.loadUrl("javascript: $('.main-container').css('padding-Top','40px')");
        view.loadUrl("javascript: $('.fixed-bar').css('top','0px')");
    }
}
