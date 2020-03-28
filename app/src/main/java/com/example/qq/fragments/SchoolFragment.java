package com.example.qq.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.qq.R;


public class SchoolFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_school, container, false);
        WebView webView = view.findViewById(R.id.webView_school);
        initWebView(webView);
        return view;
    }

    private void initWebView(WebView webView) {
        webView.loadUrl("http://www.jvtc.jx.cn/index.htm");//加载url

//        webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
//        webView.setWebChromeClient(new InnerWebView());
        webView.setWebViewClient(new InnerWebView());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
    }

    private class InnerWebView extends WebViewClient {

    }
}


