package com.roc.hcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roc.hcs.R;
import com.roc.hcs.utils.MyWebViewClient;
import com.roc.hcs.utils.NetworkDetector;

/**
 * Created by s1112001 on 2015/11/19.
 */
public class DetailActivity extends HorizontalActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_title;
    private WebView wv_detail;
    private SwipeRefreshLayout mSwipeLayout= null;
    private String title,url,back;
    private LinearLayout linearLayout1;
    private Button button;
    private ProgressBar bar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = getIntent();
        title  = intent.getStringExtra("title");
        url  = intent.getStringExtra("url");
        back  = intent.getStringExtra("back");

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setColorFilter(Color.RED);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(title);
        iv_back.setOnClickListener(this);

        wv_detail = (WebView)findViewById(R.id.wv_detail);
        wv_detail.getSettings().setJavaScriptEnabled(true);//enable js
//progressbar
        bar = (ProgressBar)findViewById(R.id.myProgressBar);
        wv_detail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                    mSwipeLayout.setRefreshing(false);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                    if (!mSwipeLayout.isRefreshing())
                        mSwipeLayout.setRefreshing(true);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        wv_detail.loadUrl(url);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新刷新页面
                linearLayout1.setVisibility(View.GONE);
                wv_detail.loadUrl(url);
            }
        });
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        wv_detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
// TODO Auto-generated method stub
                Log.i("webview", "url = " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                linearLayout1.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        button = (Button)findViewById(R.id.online_error_btn_retry);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean networkState = NetworkDetector.detect(DetailActivity.this);
                if (networkState) {
                    linearLayout1.setVisibility(View.GONE);
                    wv_detail.loadUrl(url);
                }else{
                    Toast.makeText(getApplicationContext(), "请检查网络连接！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            default:
                break;
        }
    }
    // 重写onKeyDown
    public  boolean  onKeyDown ( int  keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            back();
            return true;
        }
        return  super .onKeyDown(keyCode, event);
    }
    private void back()
    {
        switch(back){
            case "PropertyRepair":
                Intent intent = new Intent(DetailActivity.this, PropertyRepairActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            default:
                Intent intent2 = new Intent(DetailActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                break;
        }
        finish();
    }
}
