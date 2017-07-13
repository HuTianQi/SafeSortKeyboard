package com.example.user.softkeyboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText et;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebview();
        et = (EditText) findViewById(R.id.et);
        pwd = (EditText) findViewById(R.id.pwd);
        PopwinSoftkeyboard.getInstance(this).initEditText(et,false);
        PopwinSoftkeyboard.getInstance(this).initEditText(pwd,true);

    }

    private void initWebview() {

            webView = (WebView) findViewById(R.id.webview);

            WebSettings webSettings =   webView .getSettings();

            webSettings.setJavaScriptEnabled(true);
            AndroidBridge androidBridge = new AndroidBridge(this, webView);
            webView.setTag(androidBridge);
            webView.addJavascriptInterface(androidBridge, "client");


            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
            webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
            webSettings.setLoadWithOverviewMode(true);
            if (Build.VERSION.SDK_INT <= 18) {// user_logoff to  showing save password diallog
                webSettings.setSavePassword(false);
            }

            webView.clearHistory();
            webView.requestFocusFromTouch();
            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        return false ;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view,url,favicon);
                    addLoacalJS();
                }
                @Override
                public void onPageFinished(WebView view, String url) {

                    super.onPageFinished(view,url);

                    webView.loadUrl("javascript:load()");
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                    return super.shouldInterceptRequest(view, request);
                }


                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    //SecureLog.e("onReceivedError","fail URL=" + request.toString());
                    super.onReceivedError(view, request,error);

                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view,handler,error);
                }

            });

            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {


                }

                @Override
                public void onProgressChanged(WebView view, int newProgress) {

                    super.onProgressChanged(view, newProgress);
                }

            };
            // 设置setWebChromeClient对象
            webView.setWebChromeClient(wvcc);



//            localHtml();
        localHtmlWithoutJS();
        }

    /**
     * 显示本地网页文件,html中包含js
     */
    private void localHtml() {
        try {
            // 本地文件处理(如果文件名中有空格需要用+来替代)
            webView.loadUrl("file:///android_asset/login.html");
//            webView.loadUrl("file:///android_asset/test.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void localHtmlWithoutJS() {
        try {
            // 本地文件处理(如果文件名中有空格需要用+来替代)
            webView.loadUrl("file:///android_asset/login2.html");
//            webView.loadUrl("file:///android_asset/test.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void addLoacalJS(){
        //获取js文本
        InputStream mIs = null;
        String wholeJS="";
        try {
            mIs = getResources().getAssets().open("login.js");
            if(mIs != null){
                byte buff[] = new byte[1024];
                ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
                FileOutputStream out = null;
                do {
                    int numread = 0;
                    numread = mIs.read(buff);
                    if (numread <= 0) {
                        break;
                    }
                    fromFile.write(buff, 0, numread);
                } while (true);
                wholeJS = fromFile.toString();
            }else{
                Toast.makeText(MainActivity.this, "js加载失败", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //webview添加读取的js
        webView.loadUrl("javascript:" + wholeJS);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           if (webView.canGoBack()){
               webView.goBack();
           }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
