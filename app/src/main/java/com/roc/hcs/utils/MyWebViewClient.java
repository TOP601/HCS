package com.roc.hcs.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Nacy on 2015/11/22.
 */
public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        view.setVisibility(View.GONE);
        super.onPageStarted(view, url, favicon);
    }
    @Override
    public void onPageFinished(WebView view, String url) {
//        if(view!=null){
//        view.setVisibility(View.VISIBLE);
//        }
        super.onPageFinished(view, url);
    }
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
        view.loadData("Network Err", "text/html", "UTF-8");
//        view.setVisibility(View.VISIBLE);

    }
}
