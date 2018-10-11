package com.gao.intelligent.view.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.internal.EmptyViewMethodAccessor;
import com.gao.intelligent.view.pulltorefresh.internal.IndicatorLayout;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements AbsListView.OnScrollListener {
    private boolean mLastItemVisible;
    private AbsListView.OnScrollListener mOnScrollListener;
    private OnLastItemVisibleListener mOnLastItemVisibleListener;
    private View mEmptyView;
    private IndicatorLayout mIndicatorIvTop;
    private IndicatorLayout mIndicatorIvBottom;
    private boolean mShowIndicator;
    private boolean mScrollEmptyView = true;

    private static LinearLayout.LayoutParams convertEmptyViewLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        LinearLayout.LayoutParams newLp = null;
        if (null != lp) {
            newLp = new LinearLayout.LayoutParams(lp);
            if (lp instanceof android.widget.LinearLayout.LayoutParams) {
                newLp.gravity = ((android.widget.LinearLayout.LayoutParams)lp).gravity;
            } else {
                newLp.gravity = 17;
            }
        }

        return newLp;
    }

    public PullToRefreshAdapterViewBase(Context context) {
        super(context);
        ((AbsListView)this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((AbsListView)this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode) {
        super(context, mode);
        ((AbsListView)this.mRefreshableView).setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
        ((AbsListView)this.mRefreshableView).setOnScrollListener(this);
    }

    public boolean getShowIndicator() {
        return this.mShowIndicator;
    }

    public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("PullToRefresh", "First Visible: " + firstVisibleItem + ". Visible Count: " + visibleItemCount + ". Total Items:" + totalItemCount);
        if (null != this.mOnLastItemVisibleListener) {
            this.mLastItemVisible = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 1;
        }

        if (this.getShowIndicatorInternal()) {
            this.updateIndicatorViewsVisibility();
        }

        if (null != this.mOnScrollListener) {
            this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

    }

    public final void onScrollStateChanged(AbsListView view, int state) {
        if (state == 0 && null != this.mOnLastItemVisibleListener && this.mLastItemVisible) {
            this.mOnLastItemVisibleListener.onLastItemVisible();
        }

        if (null != this.mOnScrollListener) {
            this.mOnScrollListener.onScrollStateChanged(view, state);
        }

    }

    public void setAdapter(ListAdapter adapter) {
        ((AdapterView)this.mRefreshableView).setAdapter(adapter);
    }

    public final void setEmptyView(View newEmptyView) {
        FrameLayout refreshableViewWrapper = this.getRefreshableViewWrapper();
        if (null != newEmptyView) {
            newEmptyView.setClickable(true);
            ViewParent newEmptyViewParent = newEmptyView.getParent();
            if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
                ((ViewGroup)newEmptyViewParent).removeView(newEmptyView);
            }

            LinearLayout.LayoutParams lp = convertEmptyViewLayoutParams(newEmptyView.getLayoutParams());
            if (null != lp) {
                refreshableViewWrapper.addView(newEmptyView, lp);
            } else {
                refreshableViewWrapper.addView(newEmptyView);
            }
        }

        if (this.mRefreshableView instanceof EmptyViewMethodAccessor) {
            ((EmptyViewMethodAccessor)this.mRefreshableView).setEmptyViewInternal(newEmptyView);
        } else {
            ((AbsListView)this.mRefreshableView).setEmptyView(newEmptyView);
        }

        this.mEmptyView = newEmptyView;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        ((AbsListView)this.mRefreshableView).setOnItemClickListener(listener);
    }

    public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        this.mOnLastItemVisibleListener = listener;
    }

    public final void setOnScrollListener(AbsListView.OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    public final void setScrollEmptyView(boolean doScroll) {
        this.mScrollEmptyView = doScroll;
    }

    public void setShowIndicator(boolean showIndicator) {
        this.mShowIndicator = showIndicator;
        if (this.getShowIndicatorInternal()) {
            this.addIndicatorViews();
        } else {
            this.removeIndicatorViews();
        }

    }

    protected void onPullToRefresh() {
        super.onPullToRefresh();
        if (this.getShowIndicatorInternal()) {
            switch(this.getCurrentMode()) {
                case PULL_FROM_END:
                    this.mIndicatorIvBottom.pullToRefresh();
                    break;
                case PULL_FROM_START:
                    this.mIndicatorIvTop.pullToRefresh();
            }
        }

    }

    protected void onRefreshing(boolean doScroll) {
        super.onRefreshing(doScroll);
        if (this.getShowIndicatorInternal()) {
            this.updateIndicatorViewsVisibility();
        }

    }

    protected void onReleaseToRefresh() {
        super.onReleaseToRefresh();
        if (this.getShowIndicatorInternal()) {
            switch(this.getCurrentMode()) {
                case PULL_FROM_END:
                    this.mIndicatorIvBottom.releaseToRefresh();
                    break;
                case PULL_FROM_START:
                    this.mIndicatorIvTop.releaseToRefresh();
            }
        }

    }

    protected void onReset() {
        super.onReset();
        if (this.getShowIndicatorInternal()) {
            this.updateIndicatorViewsVisibility();
        }

    }

    protected void handleStyledAttributes(TypedArray a) {
        this.mShowIndicator = a.getBoolean(R.styleable.PullToRefresh_ptrShowIndicator, !this.isPullToRefreshOverScrollEnabled());
    }

    protected boolean isReadyForPullStart() {
        return this.isFirstItemVisible();
    }

    protected boolean isReadyForPullEnd() {
        return this.isLastItemVisible();
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != this.mEmptyView && !this.mScrollEmptyView) {
            this.mEmptyView.scrollTo(-l, -t);
        }

    }

    protected void updateUIForMode() {
        super.updateUIForMode();
        if (this.getShowIndicatorInternal()) {
            this.addIndicatorViews();
        } else {
            this.removeIndicatorViews();
        }

    }

    private void addIndicatorViews() {
        Mode mode = this.getMode();
        FrameLayout refreshableViewWrapper = this.getRefreshableViewWrapper();
        LinearLayout.LayoutParams params;
        if (mode.showHeaderLoadingLayout() && null == this.mIndicatorIvTop) {
            this.mIndicatorIvTop = new IndicatorLayout(this.getContext(), Mode.PULL_FROM_START);
            params = new LinearLayout.LayoutParams(-2, -2);
            params.rightMargin = this.getResources().getDimensionPixelSize(R.dimen.indicator_right_padding);
            params.gravity = 53;
            refreshableViewWrapper.addView(this.mIndicatorIvTop, params);
        } else if (!mode.showHeaderLoadingLayout() && null != this.mIndicatorIvTop) {
            refreshableViewWrapper.removeView(this.mIndicatorIvTop);
            this.mIndicatorIvTop = null;
        }

        if (mode.showFooterLoadingLayout() && null == this.mIndicatorIvBottom) {
            this.mIndicatorIvBottom = new IndicatorLayout(this.getContext(), Mode.PULL_FROM_END);
            params = new LinearLayout.LayoutParams(-2, -2);
            params.rightMargin = this.getResources().getDimensionPixelSize(R.dimen.indicator_right_padding);
            params.gravity = 85;
            refreshableViewWrapper.addView(this.mIndicatorIvBottom, params);
        } else if (!mode.showFooterLoadingLayout() && null != this.mIndicatorIvBottom) {
            refreshableViewWrapper.removeView(this.mIndicatorIvBottom);
            this.mIndicatorIvBottom = null;
        }

    }

    private boolean getShowIndicatorInternal() {
        return this.mShowIndicator && this.isPullToRefreshEnabled();
    }

    private boolean isFirstItemVisible() {
        Adapter adapter = ((AbsListView)this.mRefreshableView).getAdapter();
        if (null != adapter && !adapter.isEmpty()) {
            if (((AbsListView)this.mRefreshableView).getFirstVisiblePosition() <= 1) {
                View firstVisibleChild = ((AbsListView)this.mRefreshableView).getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= ((AbsListView)this.mRefreshableView).getTop();
                }
            }

            return false;
        } else {
            Log.d("PullToRefresh", "isFirstItemVisible. Empty View.");
            return true;
        }
    }

    private boolean isLastItemVisible() {
        Adapter adapter = ((AbsListView)this.mRefreshableView).getAdapter();
        if (null != adapter && !adapter.isEmpty()) {
            int lastItemPosition = ((AbsListView)this.mRefreshableView).getCount() - 1;
            int lastVisiblePosition = ((AbsListView)this.mRefreshableView).getLastVisiblePosition();
            Log.d("PullToRefresh", "isLastItemVisible. Last Item Position: " + lastItemPosition + " Last Visible Pos: " + lastVisiblePosition);
            if (lastVisiblePosition >= lastItemPosition - 1) {
                int childIndex = lastVisiblePosition - ((AbsListView)this.mRefreshableView).getFirstVisiblePosition();
                View lastVisibleChild = ((AbsListView)this.mRefreshableView).getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= ((AbsListView)this.mRefreshableView).getBottom();
                }
            }

            return false;
        } else {
            Log.d("PullToRefresh", "isLastItemVisible. Empty View.");
            return true;
        }
    }

    private void removeIndicatorViews() {
        if (null != this.mIndicatorIvTop) {
            this.getRefreshableViewWrapper().removeView(this.mIndicatorIvTop);
            this.mIndicatorIvTop = null;
        }

        if (null != this.mIndicatorIvBottom) {
            this.getRefreshableViewWrapper().removeView(this.mIndicatorIvBottom);
            this.mIndicatorIvBottom = null;
        }

    }

    private void updateIndicatorViewsVisibility() {
        if (null != this.mIndicatorIvTop) {
            if (!this.isRefreshing() && this.isReadyForPullStart()) {
                if (!this.mIndicatorIvTop.isVisible()) {
                    this.mIndicatorIvTop.show();
                }
            } else if (this.mIndicatorIvTop.isVisible()) {
                this.mIndicatorIvTop.hide();
            }
        }

        if (null != this.mIndicatorIvBottom) {
            if (!this.isRefreshing() && this.isReadyForPullEnd()) {
                if (!this.mIndicatorIvBottom.isVisible()) {
                    this.mIndicatorIvBottom.show();
                }
            } else if (this.mIndicatorIvBottom.isVisible()) {
                this.mIndicatorIvBottom.hide();
            }
        }

    }
}

