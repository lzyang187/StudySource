package com.lzy.studysource.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.studysource.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        FrameLayout container = findViewById(R.id.container);
        findViewById(R.id.btn_back).setOnClickListener(mClickListener);
        findViewById(R.id.btn_forward).setOnClickListener(mClickListener);
        findViewById(R.id.btn_clear_history).setOnClickListener(mClickListener);
        findViewById(R.id.btn_clear_cache).setOnClickListener(mClickListener);
        mWebView = new WebView(this);
        mWebView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_BACK) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }
            return false;
        });
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG, "shouldOverrideUrlLoading: " + super.shouldOverrideUrlLoading(view, request));

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished: ");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
//                Log.e(TAG, "onLoadResource: " + url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "onJsAlert: url：" + url + " message：" + message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "onJsConfirm: url：" + url + " message：" + message);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.e(TAG, "onJsPrompt: url：" + url + " message：" + message);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.e(TAG, "onConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e(TAG, "onProgressChanged: " + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle: " + title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                Log.e(TAG, "onReceivedIcon: " + icon);
            }
        });

        WebSettings settings = mWebView.getSettings();
//        settings.setTextZoom(100);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);

//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setLoadWithOverviewMode(true);

//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setAppCacheEnabled(true);
        String path = getDir("appcachedir", MODE_PRIVATE).getPath();
        settings.setAppCachePath(path);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(this);
//        }
//        CookieManager.getInstance().setCookie(url, cookieStr);
//        CookieManager.getInstance().getCookie(url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(mWebView, lp);
        mWebView.loadUrl("file:///android_asset/h5.html");
//        mWebView.loadUrl("http://www.iflytek.com/");
    }

    private View.OnClickListener mClickListener = (v) -> {
        switch (v.getId()) {
            case R.id.btn_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    Toast.makeText(this, "不能后退", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_forward:
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                } else {
                    Toast.makeText(this, "不能前进", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear_history:
                mWebView.clearHistory();
                Toast.makeText(this, "已清除访问的历史记录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_clear_cache:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.createInstance(this);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CookieManager.getInstance().removeAllCookies(value -> Toast.makeText(WebViewActivity.this, "cookie清除：" + value, Toast.LENGTH_SHORT).show());
                } else {
                    CookieManager.getInstance().removeAllCookie();
                }
                break;
        }
    };

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
