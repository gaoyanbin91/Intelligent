package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.internal.EmptyViewMethodAccessor;
import com.gao.intelligent.view.pulltorefresh.internal.LoadingLayout;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
    private LoadingLayout mHeaderLoadingView;
    private LoadingLayout mFooterLoadingView;
    private FrameLayout mLvFooterLoadingFrame;
    private boolean mListViewExtrasEnabled;

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected void onRefreshing(boolean doScroll) {
        ListAdapter adapter = ((ListView)this.mRefreshableView).getAdapter();
        if (this.mListViewExtrasEnabled && this.getShowViewWhileRefreshing() && null != adapter && !adapter.isEmpty()) {
            super.onRefreshing(false);
            LoadingLayout origLoadingView;
            LoadingLayout listViewLoadingView;
            LoadingLayout oppositeListViewLoadingView;
            int selection;
            int scrollToY;
            switch(this.getCurrentMode()) {
                case MANUAL_REFRESH_ONLY:
                case PULL_FROM_END:
                    origLoadingView = this.getFooterLayout();
                    listViewLoadingView = this.mFooterLoadingView;
                    oppositeListViewLoadingView = this.mHeaderLoadingView;
                    selection = ((ListView)this.mRefreshableView).getCount() - 1;
                    scrollToY = this.getScrollY() - this.getFooterSize();
                    break;
                case PULL_FROM_START:
                default:
                    origLoadingView = this.getHeaderLayout();
                    listViewLoadingView = this.mHeaderLoadingView;
                    oppositeListViewLoadingView = this.mFooterLoadingView;
                    selection = 0;
                    scrollToY = this.getScrollY() + this.getHeaderSize();
            }

            origLoadingView.reset();
            origLoadingView.hideAllViews();
            oppositeListViewLoadingView.setVisibility(GONE);
            listViewLoadingView.setVisibility(VISIBLE);
            listViewLoadingView.refreshing();
            if (doScroll) {
                this.disableLoadingLayoutVisibilityChanges();
                this.setHeaderScroll(scrollToY);
                ((ListView)this.mRefreshableView).setSelection(selection);
                this.smoothScrollTo(0);
            }

        } else {
            super.onRefreshing(doScroll);
        }
    }

    protected void onReset() {
        if (!this.mListViewExtrasEnabled) {
            super.onReset();
        } else {
            LoadingLayout originalLoadingLayout;
            LoadingLayout listViewLoadingLayout;
            int scrollToHeight;
            int selection;
            boolean scrollLvToEdge;
            switch(this.getCurrentMode()) {
                case MANUAL_REFRESH_ONLY:
                case PULL_FROM_END:
                    originalLoadingLayout = this.getFooterLayout();
                    listViewLoadingLayout = this.mFooterLoadingView;
                    selection = ((ListView)this.mRefreshableView).getCount() - 1;
                    scrollToHeight = this.getFooterSize();
                    scrollLvToEdge = Math.abs(((ListView)this.mRefreshableView).getLastVisiblePosition() - selection) <= 1;
                    break;
                case PULL_FROM_START:
                default:
                    originalLoadingLayout = this.getHeaderLayout();
                    listViewLoadingLayout = this.mHeaderLoadingView;
                    scrollToHeight = -this.getHeaderSize();
                    selection = 0;
                    scrollLvToEdge = Math.abs(((ListView)this.mRefreshableView).getFirstVisiblePosition() - selection) <= 1;
            }

            if (listViewLoadingLayout.getVisibility() == VISIBLE) {
                originalLoadingLayout.showInvisibleViews();
                listViewLoadingLayout.setVisibility(GONE);
                if (scrollLvToEdge && this.getState() != State.MANUAL_REFRESHING) {
                    ((ListView)this.mRefreshableView).setSelection(selection);
                    this.setHeaderScroll(scrollToHeight);
                }
            }

            super.onReset();
        }
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        LoadingLayoutProxy proxy = super.createLoadingLayoutProxy(includeStart, includeEnd);
        if (this.mListViewExtrasEnabled) {
            Mode mode = this.getMode();
            if (includeStart && mode.showHeaderLoadingLayout()) {
                proxy.addLayout(this.mHeaderLoadingView);
            }

            if (includeEnd && mode.showFooterLoadingLayout()) {
                proxy.addLayout(this.mFooterLoadingView);
            }
        }

        return proxy;
    }

    protected ListView createListView(Context context, AttributeSet attrs) {
        Object lv;
        if (VERSION.SDK_INT >= 9) {
            lv = new PullToRefreshListView.InternalListViewSDK9(context, attrs);
        } else {
            lv = new PullToRefreshListView.InternalListView(context, attrs);
        }

        return (ListView)lv;
    }

    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView lv = this.createListView(context, attrs);
       int s  = 16908298;
        lv.setId(s);
        return lv;
    }

    protected void handleStyledAttributes(TypedArray a) {
        super.handleStyledAttributes(a);
        this.mListViewExtrasEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrListViewExtrasEnabled, true);
        if (this.mListViewExtrasEnabled) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2, 1);
            FrameLayout frame = new FrameLayout(this.getContext());
            this.mHeaderLoadingView = this.createLoadingLayout(this.getContext(), Mode.PULL_FROM_START, a);
            this.mHeaderLoadingView.setVisibility(GONE);
            frame.addView(this.mHeaderLoadingView, lp);
            ((ListView)this.mRefreshableView).addHeaderView(frame, (Object)null, false);
            this.mLvFooterLoadingFrame = new FrameLayout(this.getContext());
            this.mFooterLoadingView = this.createLoadingLayout(this.getContext(), Mode.PULL_FROM_END, a);
            this.mFooterLoadingView.setVisibility(GONE);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, lp);
            if (!a.hasValue(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
                this.setScrollingWhileRefreshingEnabled(true);
            }
        }

    }

    protected class InternalListView extends ListView implements EmptyViewMethodAccessor {
        private boolean mAddedLvFooter = false;

        public InternalListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
            } catch (IndexOutOfBoundsException var3) {
                var3.printStackTrace();
            }

        }

        public boolean dispatchTouchEvent(MotionEvent ev) {
            try {
                return super.dispatchTouchEvent(ev);
            } catch (IndexOutOfBoundsException var3) {
                var3.printStackTrace();
                return false;
            }
        }

        public void setAdapter(ListAdapter adapter) {
            if (null != PullToRefreshListView.this.mLvFooterLoadingFrame && !this.mAddedLvFooter) {
                this.addFooterView(PullToRefreshListView.this.mLvFooterLoadingFrame, (Object)null, false);
                this.mAddedLvFooter = true;
            }

            super.setAdapter(adapter);
        }

        public void setEmptyView(View emptyView) {
            PullToRefreshListView.this.setEmptyView(emptyView);
        }

        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalListViewSDK9 extends PullToRefreshListView.InternalListView {
        public InternalListViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshListView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);
            return returnValue;
        }
    }
}

