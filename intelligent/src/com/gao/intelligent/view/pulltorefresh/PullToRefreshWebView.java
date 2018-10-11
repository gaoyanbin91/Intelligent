package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {
    private static final OnRefreshListener<WebView> defaultOnRefreshListener = new OnRefreshListener<WebView>() {
        public void onRefresh(PullToRefreshBase<WebView> refreshView) {
            ((WebView)refreshView.getRefreshableView()).reload();
        }
    };
    private final WebChromeClient defaultWebChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                PullToRefreshWebView.this.onRefreshComplete();
            }

        }
    };

    public PullToRefreshWebView(Context context) {
        super(context);
        this.setOnRefreshListener(defaultOnRefreshListener);
        ((WebView)this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnRefreshListener(defaultOnRefreshListener);
        ((WebView)this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode) {
        super(context, mode);
        this.setOnRefreshListener(defaultOnRefreshListener);
        ((WebView)this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public PullToRefreshWebView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
        this.setOnRefreshListener(defaultOnRefreshListener);
        ((WebView)this.mRefreshableView).setWebChromeClient(this.defaultWebChromeClient);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        Object webView;
        if (VERSION.SDK_INT >= 9) {
            webView = new PullToRefreshWebView.InternalWebViewSDK9(context, attrs);
        } else {
            webView = new WebView(context, attrs);
        }

       // ((WebView)webView).setId(id.webview);
        return (WebView)webView;
    }

    protected boolean isReadyForPullStart() {
        return ((WebView)this.mRefreshableView).getScrollY() == 0;
    }

    protected boolean isReadyForPullEnd() {
        double exactContentHeight = Math.floor((double)((float)((WebView)this.mRefreshableView).getContentHeight() * ((WebView)this.mRefreshableView).getScale()));
        return (double)((WebView)this.mRefreshableView).getScrollY() >= exactContentHeight - (double)((WebView)this.mRefreshableView).getHeight();
    }

    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
        super.onPtrRestoreInstanceState(savedInstanceState);
        ((WebView)this.mRefreshableView).restoreState(savedInstanceState);
    }

    protected void onPtrSaveInstanceState(Bundle saveState) {
        super.onPtrSaveInstanceState(saveState);
        ((WebView)this.mRefreshableView).saveState(saveState);
    }

    @TargetApi(9)
    final class InternalWebViewSDK9 extends WebView {
        static final int OVERSCROLL_FUZZY_THRESHOLD = 2;
        static final float OVERSCROLL_SCALE_FACTOR = 1.5F;

        public InternalWebViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshWebView.this, deltaX, scrollX, deltaY, scrollY, this.getScrollRange(), 2, 1.5F, isTouchEvent);
            return returnValue;
        }

        private int getScrollRange() {
            return (int) Math.max(0.0D, Math.floor((double)((float)((WebView)PullToRefreshWebView.this.mRefreshableView).getContentHeight() * ((WebView)PullToRefreshWebView.this.mRefreshableView).getScale())) - (double)(this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
        }
    }
}
