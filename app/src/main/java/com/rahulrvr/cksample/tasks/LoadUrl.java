package com.rahulrvr.cksample.tasks;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.rahulrvr.cksample.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class LoadUrl extends AsyncTask<Void,Void,Void>{


    WebView mWebView;
    Document document;
    String data;
    String mUrl;
    public LoadUrl(WebView view, String url) {
        mWebView = view;
        mUrl = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            document = Jsoup.connect(mUrl).get();
            document.getElementsByClass("header-container").remove();
            document.getElementsByClass("footer").remove();
            //TODO - to be removed , just for home page.
            if(mUrl.equals(MainActivity.homepage)) {
                document.getElementById("header").remove();
                document.getElementsByClass("hero-cta").remove();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       // mWebView.loadData(document.toString(), "text/html", "utf-8");
        if(mWebView != null) {
            mWebView.loadDataWithBaseURL(mUrl, document.toString(), "text/html", "utf-8", "");
        }
    }



}
