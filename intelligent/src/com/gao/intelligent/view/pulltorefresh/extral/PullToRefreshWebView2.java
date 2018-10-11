package com.gao.intelligent.view.pulltorefresh.extral;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshWebView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshWebView2 extends PullToRefreshWebView {
    static final String JS_INTERFACE_PKG = "ptr";
    static final String DEF_JS_READY_PULL_DOWN_CALL = "javascript:isReadyForPullDown();";
    static final String DEF_JS_READY_PULL_UP_CALL = "javascript:isReadyForPullUp();";
    private PullToRefreshWebView2.JsValueCallback mJsCallback;
    private final AtomicBoolean mIsReadyForPullDown = new AtomicBoolean(false);
    private final AtomicBoolean mIsReadyForPullUp = new AtomicBoolean(false);

    public PullToRefreshWebView2(Context context) {
        super(context);
    }

    public PullToRefreshWebView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshWebView2(Context context, Mode mode) {
        super(context, mode);
    }

    @SuppressLint("JavascriptInterface")
    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        WebView webView = super.createRefreshableView(context, attrs);
        this.mJsCallback = new PullToRefreshWebView2.JsValueCallback();
        webView.addJavascriptInterface(this.mJsCallback, "ptr");
        return webView;
    }

    protected boolean isReadyForPullStart() {
        ((WebView)this.getRefreshableView()).loadUrl("javascript:isReadyForPullDown();");
        return this.mIsReadyForPullDown.get();
    }

    protected boolean isReadyForPullEnd() {
        ((WebView)this.getRefreshableView()).loadUrl("javascript:isReadyForPullUp();");
        return this.mIsReadyForPullUp.get();
    }

    final class JsValueCallback {
        JsValueCallback() {
        }

        public void isReadyForPullUpResponse(boolean response) {
            PullToRefreshWebView2.this.mIsReadyForPullUp.set(response);
        }

        public void isReadyForPullDownResponse(boolean response) {
            PullToRefreshWebView2.this.mIsReadyForPullDown.set(response);
        }
    }
}
