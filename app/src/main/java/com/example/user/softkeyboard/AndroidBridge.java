package com.example.user.softkeyboard;

/**
 * Created by user on 17-6-29.
 */

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;


//这个类中提供各种js可调用的方法。
public class AndroidBridge{
    private final Handler handler=new Handler();//只有用handel才能修改android控件的值
    private Activity mActivity;
    private WebView mWebView;

    public AndroidBridge(Activity activity,WebView webView) {
        mActivity = activity;
        mWebView = webView;
    }

    @JavascriptInterface
    public void callJava(final String id){
        handler.post(new Runnable(){
            public void run(){
//                Toast.makeText(mActivity,"callJava",Toast.LENGTH_SHORT).show();
                PopwinSoftkeyboard.getInstance(mActivity).initWebModeKeyboard(id,mWebView).show(mWebView);
            }
        });
    }
    public void addInfoToJs(String id,String info) {

        //调用js中的函数：showInfoFromJava(msg)
//        mWebView.loadUrl("javascript:addInfoToJs('"+id+"' , '" + info + "')");
        mWebView.loadUrl("javascript:addInfoToJs('"+id+"','"+info+"')");

    }
    public void delInfoToJs(String id) {

        //调用js中的函数：showInfoFromJava(msg)
        mWebView.loadUrl("javascript:delInfoToJs('" + id + "')");

    }


}