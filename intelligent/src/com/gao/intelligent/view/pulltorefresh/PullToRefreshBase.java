package com.gao.intelligent.view.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.internal.FlipLoadingLayout;
import com.gao.intelligent.view.pulltorefresh.internal.LoadingLayout;
import com.gao.intelligent.view.pulltorefresh.internal.RotateLoadingLayout;
import com.gao.intelligent.view.pulltorefresh.internal.Utils;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    static final boolean DEBUG = true;
    static final boolean USE_HW_LAYERS = false;
    static final String LOG_TAG = "PullToRefresh";
    static final float FRICTION = 2.0F;
    public static final int SMOOTH_SCROLL_DURATION_MS = 200;
    public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;
    static final int DEMO_SCROLL_INTERVAL = 225;
    static final String STATE_STATE = "ptr_state";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_SCROLLING_REFRESHING_ENABLED = "ptr_disable_scrolling";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_SUPER = "ptr_super";
    private int mTouchSlop;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged = false;
    private PullToRefreshBase.State mState;
    private PullToRefreshBase.Mode mMode;
    private PullToRefreshBase.Mode mCurrentMode;
    protected T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;
    private boolean mShowViewWhileRefreshing;
    private boolean mScrollingWhileRefreshingEnabled;
    private boolean mFilterTouchEvents;
    private boolean mOverScrollEnabled;
    private boolean mLayoutVisibilityChangesEnabled;
    private Interpolator mScrollAnimationInterpolator;
    private PullToRefreshBase.AnimationStyle mLoadingAnimationStyle;
    private LoadingLayout mHeaderLayout;
    private LoadingLayout mFooterLayout;
    private PullToRefreshBase.OnRefreshListener<T> mOnRefreshListener;
    private PullToRefreshBase.OnRefreshListener2<T> mOnRefreshListener2;
    private PullToRefreshBase.OnPullEventListener<T> mOnPullEventListener;
    private PullToRefreshBase<T>.SmoothScrollRunnable mCurrentSmoothScrollRunnable;

    public PullToRefreshBase(Context context) {
        super(context);
        this.mState = PullToRefreshBase.State.RESET;
        this.mMode = PullToRefreshBase.Mode.PULL_FROM_START;
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = PullToRefreshBase.AnimationStyle.ROTATE;
        this.init(context, (AttributeSet)null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = PullToRefreshBase.State.RESET;
        this.mMode = PullToRefreshBase.Mode.PULL_FROM_START;
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = PullToRefreshBase.AnimationStyle.ROTATE;
        this.init(context, attrs);
    }

    public PullToRefreshBase(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        this.mState = PullToRefreshBase.State.RESET;
        this.mMode = PullToRefreshBase.Mode.PULL_FROM_START;
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = PullToRefreshBase.AnimationStyle.ROTATE;
        this.mMode = mode;
        this.init(context, (AttributeSet)null);
    }

    public PullToRefreshBase(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.AnimationStyle animStyle) {
        super(context);
        this.mState = PullToRefreshBase.State.RESET;
        this.mMode = PullToRefreshBase.Mode.PULL_FROM_START;
        this.mShowViewWhileRefreshing = true;
        this.mScrollingWhileRefreshingEnabled = false;
        this.mFilterTouchEvents = true;
        this.mOverScrollEnabled = true;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mLoadingAnimationStyle = PullToRefreshBase.AnimationStyle.ROTATE;
        this.mMode = mode;
        this.mLoadingAnimationStyle = animStyle;
        this.init(context, (AttributeSet)null);
    }

    public void addView(View child, int index, LinearLayout.LayoutParams params) {
        Log.d("PullToRefresh", "addView: " + child.getClass().getSimpleName());
        T refreshableView = this.getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup)refreshableView).addView(child, index, params);
        } else {
            throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
        }
    }

    public final boolean demo() {
        if (this.mMode.showHeaderLoadingLayout() && this.isReadyForPullStart()) {
            this.smoothScrollToAndBack(-this.getHeaderSize() * 2);
            return true;
        } else if (this.mMode.showFooterLoadingLayout() && this.isReadyForPullEnd()) {
            this.smoothScrollToAndBack(this.getFooterSize() * 2);
            return true;
        } else {
            return false;
        }
    }

    public final PullToRefreshBase.Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    public final ILoadingLayout getLoadingLayoutProxy() {
        return this.getLoadingLayoutProxy(true, true);
    }

    public final ILoadingLayout getLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        return this.createLoadingLayoutProxy(includeStart, includeEnd);
    }

    public final PullToRefreshBase.Mode getMode() {
        return this.mMode;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    public final PullToRefreshBase.State getState() {
        return this.mState;
    }

    /** @deprecated */
    public final boolean isDisableScrollingWhileRefreshing() {
        return !this.isScrollingWhileRefreshingEnabled();
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    public final boolean isPullToRefreshOverScrollEnabled() {
        return VERSION.SDK_INT >= 9 && this.mOverScrollEnabled && OverscrollHelper.isAndroidOverScrollEnabled(this.mRefreshableView);
    }

    public final boolean isRefreshing() {
        return this.mState == PullToRefreshBase.State.REFRESHING || this.mState == PullToRefreshBase.State.MANUAL_REFRESHING;
    }

    public final boolean isScrollingWhileRefreshingEnabled() {
        return this.mScrollingWhileRefreshingEnabled;
    }

    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.isPullToRefreshEnabled()) {
            return false;
        } else {
            int action = event.getAction();
            if (action != 3 && action != 1) {
                if (action != 0 && this.mIsBeingDragged) {
                    return true;
                } else {
                    switch(action) {
                        case 0:
                            if (this.isReadyForPull()) {
                                this.mLastMotionY = this.mInitialMotionY = event.getY();
                                this.mLastMotionX = this.mInitialMotionX = event.getX();
                                this.mIsBeingDragged = false;
                            }
                            break;
                        case 2:
                            if (!this.mScrollingWhileRefreshingEnabled && this.isRefreshing()) {
                                return true;
                            }

                            if (this.isReadyForPull()) {
                                float y = event.getY();
                                float x = event.getX();
                                float diff;
                                float oppositeDiff;
                                switch(this.getPullToRefreshScrollDirection()) {
                                    case HORIZONTAL:
                                        diff = x - this.mLastMotionX;
                                        oppositeDiff = y - this.mLastMotionY;
                                        break;
                                    case VERTICAL:
                                    default:
                                        diff = y - this.mLastMotionY;
                                        oppositeDiff = x - this.mLastMotionX;
                                }

                                float absDiff = Math.abs(diff);
                                if (absDiff > (float)this.mTouchSlop && (!this.mFilterTouchEvents || absDiff > Math.abs(oppositeDiff))) {
                                    if (this.mMode.showHeaderLoadingLayout() && diff >= 1.0F && this.isReadyForPullStart()) {
                                        this.mLastMotionY = y;
                                        this.mLastMotionX = x;
                                        this.mIsBeingDragged = true;
                                        if (this.mMode == PullToRefreshBase.Mode.BOTH) {
                                            this.mCurrentMode = PullToRefreshBase.Mode.PULL_FROM_START;
                                        }
                                    } else if (this.mMode.showFooterLoadingLayout() && diff <= -1.0F && this.isReadyForPullEnd()) {
                                        this.mLastMotionY = y;
                                        this.mLastMotionX = x;
                                        this.mIsBeingDragged = true;
                                        if (this.mMode == PullToRefreshBase.Mode.BOTH) {
                                            this.mCurrentMode = PullToRefreshBase.Mode.PULL_FROM_END;
                                        }
                                    }
                                }
                            }
                    }

                    return this.mIsBeingDragged;
                }
            } else {
                this.mIsBeingDragged = false;
                return false;
            }
        }
    }

    public final void onRefreshComplete() {
        if (this.isRefreshing()) {
            this.setState(PullToRefreshBase.State.RESET);
        }

    }

    public final boolean onTouchEvent(MotionEvent event) {
        if (!this.isPullToRefreshEnabled()) {
            return false;
        } else if (!this.mScrollingWhileRefreshingEnabled && this.isRefreshing()) {
            return true;
        } else if (event.getAction() == 0 && event.getEdgeFlags() != 0) {
            return false;
        } else {
            switch(event.getAction()) {
                case 0:
                    if (this.isReadyForPull()) {
                        this.mLastMotionY = this.mInitialMotionY = event.getY();
                        this.mLastMotionX = this.mInitialMotionX = event.getX();
                        return true;
                    }
                    break;
                case 1:
                case 3:
                    if (this.mIsBeingDragged) {
                        this.mIsBeingDragged = false;
                        if (this.mState != PullToRefreshBase.State.RELEASE_TO_REFRESH || null == this.mOnRefreshListener && null == this.mOnRefreshListener2) {
                            if (this.isRefreshing()) {
                                this.smoothScrollTo(0);
                                return true;
                            }

                            this.setState(PullToRefreshBase.State.RESET);
                            return true;
                        }

                        this.setState(PullToRefreshBase.State.REFRESHING, true);
                        return true;
                    }
                    break;
                case 2:
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = event.getY();
                        this.mLastMotionX = event.getX();
                        this.pullEvent();
                        return true;
                    }
            }

            return false;
        }
    }

    public final void setScrollingWhileRefreshingEnabled(boolean allowScrollingWhileRefreshing) {
        this.mScrollingWhileRefreshingEnabled = allowScrollingWhileRefreshing;
    }

    /** @deprecated */
    public void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        this.setScrollingWhileRefreshingEnabled(!disableScrollingWhileRefreshing);
    }

    public final void setFilterTouchEvents(boolean filterEvents) {
        this.mFilterTouchEvents = filterEvents;
    }

    /** @deprecated */
    public void setLastUpdatedLabel(CharSequence label) {
        this.getLoadingLayoutProxy().setLastUpdatedLabel(label);
    }

    /** @deprecated */
    public void setLoadingDrawable(Drawable drawable) {
        this.getLoadingLayoutProxy().setLoadingDrawable(drawable);
    }

    /** @deprecated */
    public void setLoadingDrawable(Drawable drawable, PullToRefreshBase.Mode mode) {
        this.getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setLoadingDrawable(drawable);
    }

    public void setLongClickable(boolean longClickable) {
        this.getRefreshableView().setLongClickable(longClickable);
    }

    public final void setMode(PullToRefreshBase.Mode mode) {
        if (mode != this.mMode) {
            Log.d("PullToRefresh", "Setting mode to: " + mode);
            this.mMode = mode;
            this.updateUIForMode();
        }

    }

    public void setOnPullEventListener(PullToRefreshBase.OnPullEventListener<T> listener) {
        this.mOnPullEventListener = listener;
    }

    public final void setOnRefreshListener(PullToRefreshBase.OnRefreshListener<T> listener) {
        this.mOnRefreshListener = listener;
        this.mOnRefreshListener2 = null;
    }

    public final void setOnRefreshListener(PullToRefreshBase.OnRefreshListener2<T> listener) {
        this.mOnRefreshListener2 = listener;
        this.mOnRefreshListener = null;
    }

    public void setHeaderLayout(LoadingLayout headerLayout) {
        this.mHeaderLayout = headerLayout;
        this.updateUIForMode();
    }

    public void setFooterLayout(LoadingLayout footerLayout) {
        this.mFooterLayout = footerLayout;
        this.updateUIForMode();
    }

    /** @deprecated */
    public void setPullLabel(CharSequence pullLabel) {
        this.getLoadingLayoutProxy().setPullLabel(pullLabel);
    }

    /** @deprecated */
    public void setPullLabel(CharSequence pullLabel, PullToRefreshBase.Mode mode) {
        this.getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setPullLabel(pullLabel);
    }

    /** @deprecated */
    public final void setPullToRefreshEnabled(boolean enable) {
        this.setMode(enable ? PullToRefreshBase.Mode.PULL_FROM_START : PullToRefreshBase.Mode.DISABLED);
    }

    public final void setPullToRefreshOverScrollEnabled(boolean enabled) {
        this.mOverScrollEnabled = enabled;
    }

    public final void setRefreshing() {
        this.setRefreshing(true);
    }

    public final void setRefreshing(boolean doScroll) {
        if (!this.isRefreshing()) {
            this.setState(PullToRefreshBase.State.MANUAL_REFRESHING, doScroll);
        }

    }

    /** @deprecated */
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        this.getLoadingLayoutProxy().setRefreshingLabel(refreshingLabel);
    }

    /** @deprecated */
    public void setRefreshingLabel(CharSequence refreshingLabel, PullToRefreshBase.Mode mode) {
        this.getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setRefreshingLabel(refreshingLabel);
    }

    /** @deprecated */
    public void setReleaseLabel(CharSequence releaseLabel) {
        this.setReleaseLabel(releaseLabel, PullToRefreshBase.Mode.BOTH);
    }

    /** @deprecated */
    public void setReleaseLabel(CharSequence releaseLabel, PullToRefreshBase.Mode mode) {
        this.getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setReleaseLabel(releaseLabel);
    }

    public void setScrollAnimationInterpolator(Interpolator interpolator) {
        this.mScrollAnimationInterpolator = interpolator;
    }

    public final void setShowViewWhileRefreshing(boolean showView) {
        this.mShowViewWhileRefreshing = showView;
    }

    public abstract PullToRefreshBase.Orientation getPullToRefreshScrollDirection();

    final void setState(PullToRefreshBase.State state, boolean... params) {
        this.mState = state;
        Log.d("PullToRefresh", "State: " + this.mState.name());
        switch(this.mState) {
            case RESET:
                this.onReset();
                break;
            case PULL_TO_REFRESH:
                this.onPullToRefresh();
                break;
            case RELEASE_TO_REFRESH:
                this.onReleaseToRefresh();
                break;
            case REFRESHING:
            case MANUAL_REFRESHING:
                this.onRefreshing(params[0]);
            case OVERSCROLLING:
        }

        if (null != this.mOnPullEventListener) {
            this.mOnPullEventListener.onPullEvent(this, this.mState, this.mCurrentMode);
        }

    }

    protected final void addViewInternal(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
    }

    protected final void addViewInternal(View child, LayoutParams params) {
        super.addView(child, -1, params);
    }

    protected LoadingLayout createLoadingLayout(Context context, PullToRefreshBase.Mode mode, TypedArray attrs) {
        LoadingLayout layout = this.mLoadingAnimationStyle.createLoadingLayout(context, mode, this.getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(INVISIBLE);
        return layout;
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        LoadingLayoutProxy proxy = new LoadingLayoutProxy();
        if (includeStart && this.mMode.showHeaderLoadingLayout()) {
            proxy.addLayout(this.mHeaderLayout);
        }

        if (includeEnd && this.mMode.showFooterLoadingLayout()) {
            proxy.addLayout(this.mFooterLayout);
        }

        return proxy;
    }

    protected abstract T createRefreshableView(Context var1, AttributeSet var2);

    protected final void disableLoadingLayoutVisibilityChanges() {
        this.mLayoutVisibilityChangesEnabled = false;
    }

    protected final LoadingLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final int getFooterSize() {
        return this.mFooterLayout.getContentSize();
    }

    protected final LoadingLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderSize() {
        return this.mHeaderLayout.getContentSize();
    }

    protected int getPullToRefreshScrollDuration() {
        return 200;
    }

    protected int getPullToRefreshScrollDurationLonger() {
        return 325;
    }

    protected FrameLayout getRefreshableViewWrapper() {
        return this.mRefreshableViewWrapper;
    }

    protected void handleStyledAttributes(TypedArray a) {
    }

    protected abstract boolean isReadyForPullEnd();

    protected abstract boolean isReadyForPullStart();

    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
    }

    protected void onPtrSaveInstanceState(Bundle saveState) {
    }

    protected void onPullToRefresh() {
        switch(this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.pullToRefresh();
                break;
            case PULL_FROM_START:
                this.mHeaderLayout.pullToRefresh();
        }

    }

    protected void onRefreshing(boolean doScroll) {
        if (this.mMode.showHeaderLoadingLayout()) {
            this.mHeaderLayout.refreshing();
        }

        if (this.mMode.showFooterLoadingLayout()) {
            this.mFooterLayout.refreshing();
        }

        if (doScroll) {
            if (this.mShowViewWhileRefreshing) {
                PullToRefreshBase.OnSmoothScrollFinishedListener listener = new PullToRefreshBase.OnSmoothScrollFinishedListener() {
                    public void onSmoothScrollFinished() {
                        PullToRefreshBase.this.callRefreshListener();
                    }
                };
                switch(this.mCurrentMode) {
                    case PULL_FROM_END:
                    case MANUAL_REFRESH_ONLY:
                        this.smoothScrollTo(this.getFooterSize(), listener);
                        break;
                    case PULL_FROM_START:
                    default:
                        this.smoothScrollTo(-this.getHeaderSize(), listener);
                }
            } else {
                this.smoothScrollTo(0);
            }
        } else {
            this.callRefreshListener();
        }

    }

    protected void onReleaseToRefresh() {
        switch(this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.releaseToRefresh();
                break;
            case PULL_FROM_START:
                this.mHeaderLayout.releaseToRefresh();
        }

    }

    protected void onReset() {
        this.mIsBeingDragged = false;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mHeaderLayout.reset();
        this.mFooterLayout.reset();
        this.smoothScrollTo(0);
    }

    protected final void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof Bundle)) {
          try {
              super.onRestoreInstanceState(state);

          }catch (Exception e){

          }

        } else {
            Bundle bundle = (Bundle)state;
            this.setMode(PullToRefreshBase.Mode.mapIntToValue(bundle.getInt("ptr_mode", 0)));
            this.mCurrentMode = PullToRefreshBase.Mode.mapIntToValue(bundle.getInt("ptr_current_mode", 0));
            this.mScrollingWhileRefreshingEnabled = bundle.getBoolean("ptr_disable_scrolling", false);
            this.mShowViewWhileRefreshing = bundle.getBoolean("ptr_show_refreshing_view", true);
            super.onRestoreInstanceState(bundle.getParcelable("ptr_super"));
            PullToRefreshBase.State viewState = PullToRefreshBase.State.mapIntToValue(bundle.getInt("ptr_state", 0));
            if (viewState == PullToRefreshBase.State.REFRESHING || viewState == PullToRefreshBase.State.MANUAL_REFRESHING) {
                this.setState(viewState, true);
            }

            this.onPtrRestoreInstanceState(bundle);
        }
    }

    protected final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        this.onPtrSaveInstanceState(bundle);
        bundle.putInt("ptr_state", this.mState.getIntValue());
        bundle.putInt("ptr_mode", this.mMode.getIntValue());
        bundle.putInt("ptr_current_mode", this.mCurrentMode.getIntValue());
        bundle.putBoolean("ptr_disable_scrolling", this.mScrollingWhileRefreshingEnabled);
        bundle.putBoolean("ptr_show_refreshing_view", this.mShowViewWhileRefreshing);
        bundle.putParcelable("ptr_super", super.onSaveInstanceState());
        return bundle;
    }

    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("PullToRefresh", String.format("onSizeChanged. W: %d, H: %d", w, h));
        super.onSizeChanged(w, h, oldw, oldh);
        this.refreshLoadingViewsSize();
        this.refreshRefreshableViewSize(w, h);
        this.post(new Runnable() {
            public void run() {
                PullToRefreshBase.this.requestLayout();
            }
        });
    }

    protected final void refreshLoadingViewsSize() {
        int maximumPullScroll = (int)((float)this.getMaximumPullScroll() * 1.2F);
        int pLeft = this.getPaddingLeft();
        int pTop = this.getPaddingTop();
        int pRight = this.getPaddingRight();
        int pBottom = this.getPaddingBottom();
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setWidth(maximumPullScroll);
                    pLeft = -maximumPullScroll;
                } else {
                    pLeft = 0;
                }

                if (this.mMode.showFooterLoadingLayout()) {
                    this.mFooterLayout.setWidth(maximumPullScroll);
                    pRight = -maximumPullScroll;
                } else {
                    pRight = 0;
                }
                break;
            case VERTICAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setHeight(maximumPullScroll);
                    pTop = -maximumPullScroll;
                } else {
                    pTop = 0;
                }

                if (this.mMode.showFooterLoadingLayout()) {
                    this.mFooterLayout.setHeight(maximumPullScroll);
                    pBottom = -maximumPullScroll;
                } else {
                    pBottom = 0;
                }
        }

        Log.d("PullToRefresh", String.format("Setting Padding. L: %d, T: %d, R: %d, B: %d", pLeft, pTop, pRight, pBottom));
        this.setPadding(pLeft, pTop, pRight, pBottom);
    }

    protected final void refreshRefreshableViewSize(int width, int height) {
        android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)this.mRefreshableViewWrapper.getLayoutParams();
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (lp.width != width) {
                    lp.width = width;
                    this.mRefreshableViewWrapper.requestLayout();
                }
                break;
            case VERTICAL:
                if (lp.height != height) {
                    lp.height = height;
                    this.mRefreshableViewWrapper.requestLayout();
                }
        }

    }

    protected final void setHeaderScroll(int value) {
        Log.d("PullToRefresh", "setHeaderScroll: " + value);
        int maximumPullScroll = this.getMaximumPullScroll();
        value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (value < 0) {
                this.mHeaderLayout.setVisibility(VISIBLE);
            } else if (value > 0) {
                this.mFooterLayout.setVisibility(VISIBLE);
            } else {
                this.mHeaderLayout.setVisibility(INVISIBLE);
                this.mFooterLayout.setVisibility(INVISIBLE);
            }
        }

        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                this.scrollTo(value, 0);
                break;
            case VERTICAL:
                this.scrollTo(0, value);
        }

    }

    protected final void smoothScrollTo(int scrollValue) {
        this.smoothScrollTo(scrollValue, (long)this.getPullToRefreshScrollDuration());
    }

    protected final void smoothScrollTo(int scrollValue, PullToRefreshBase.OnSmoothScrollFinishedListener listener) {
        this.smoothScrollTo(scrollValue, (long)this.getPullToRefreshScrollDuration(), 0L, listener);
    }

    protected final void smoothScrollToLonger(int scrollValue) {
        this.smoothScrollTo(scrollValue, (long)this.getPullToRefreshScrollDurationLonger());
    }

    protected void updateUIForMode() {
        android.widget.LinearLayout.LayoutParams lp = this.getLoadingLayoutLayoutParams();
        if (this == this.mHeaderLayout.getParent()) {
            this.removeView(this.mHeaderLayout);
        }

        if (this.mMode.showHeaderLoadingLayout()) {
            this.addViewInternal(this.mHeaderLayout, 0, lp);
        }

        if (this == this.mFooterLayout.getParent()) {
            this.removeView(this.mFooterLayout);
        }

        if (this.mMode.showFooterLoadingLayout()) {
            this.addViewInternal(this.mFooterLayout, lp);
        }

        this.refreshLoadingViewsSize();
        this.mCurrentMode = this.mMode != PullToRefreshBase.Mode.BOTH ? this.mMode : PullToRefreshBase.Mode.PULL_FROM_START;
    }

    private void addRefreshableView(Context context, T refreshableView) {
        this.mRefreshableViewWrapper = new FrameLayout(context);
        this.mRefreshableViewWrapper.addView(refreshableView, -1, -1);
        this.addViewInternal(this.mRefreshableViewWrapper, new android.widget.LinearLayout.LayoutParams(-1, -1));
    }

    private void callRefreshListener() {
        if (null != this.mOnRefreshListener) {
            this.mOnRefreshListener.onRefresh(this);
        } else if (null != this.mOnRefreshListener2) {
            if (this.mCurrentMode == PullToRefreshBase.Mode.PULL_FROM_START) {
                this.mOnRefreshListener2.onPullDownToRefresh(this);
            } else if (this.mCurrentMode == PullToRefreshBase.Mode.PULL_FROM_END) {
                this.mOnRefreshListener2.onPullUpToRefresh(this);
            }
        }

    }

    private void init(Context context, AttributeSet attrs) {
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                this.setOrientation(HORIZONTAL);
                break;
            case VERTICAL:
            default:
                this.setOrientation(VERTICAL);
        }

        this.setGravity(17);
        ViewConfiguration config = ViewConfiguration.get(context);
        this.mTouchSlop = config.getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        if (a.hasValue(R.styleable.PullToRefresh_ptrMode)) {
            this.mMode = PullToRefreshBase.Mode.mapIntToValue(a.getInteger(R.styleable.PullToRefresh_ptrMode, 0));
        }

        if (a.hasValue(R.styleable.PullToRefresh_ptrAnimationStyle)) {
            this.mLoadingAnimationStyle = PullToRefreshBase.AnimationStyle.mapIntToValue(a.getInteger(R.styleable.PullToRefresh_ptrAnimationStyle, 0));
        }

        this.mRefreshableView = this.createRefreshableView(context, attrs);
        this.addRefreshableView(context, this.mRefreshableView);
        this.mHeaderLayout = this.createLoadingLayout(context, PullToRefreshBase.Mode.PULL_FROM_START, a);
        this.mFooterLayout = this.createLoadingLayout(context, PullToRefreshBase.Mode.PULL_FROM_END, a);
        Drawable background;
        if (a.hasValue(R.styleable.PullToRefresh_ptrRefreshableViewBackground)) {
            background = a.getDrawable(R.styleable.PullToRefresh_ptrRefreshableViewBackground);
            if (null != background) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        } else if (a.hasValue(R.styleable.PullToRefresh_ptrAdapterViewBackground)) {
            Utils.warnDeprecation("ptrAdapterViewBackground", "ptrRefreshableViewBackground");
            background = a.getDrawable(R.styleable.PullToRefresh_ptrAdapterViewBackground);
            if (null != background) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        }

        if (a.hasValue(R.styleable.PullToRefresh_ptrOverScroll)) {
            this.mOverScrollEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrOverScroll, true);
        }

        if (a.hasValue(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
            this.mScrollingWhileRefreshingEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled, false);
        }

        this.handleStyledAttributes(a);
        a.recycle();
        this.updateUIForMode();
    }

    private boolean isReadyForPull() {
        switch(this.mMode) {
            case PULL_FROM_END:
                return this.isReadyForPullEnd();
            case PULL_FROM_START:
                return this.isReadyForPullStart();
            case MANUAL_REFRESH_ONLY:
            default:
                return false;
            case BOTH:
                return this.isReadyForPullEnd() || this.isReadyForPullStart();
        }
    }

    private void pullEvent() {
        float initialMotionValue;
        float lastMotionValue;
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                initialMotionValue = this.mInitialMotionX;
                lastMotionValue = this.mLastMotionX;
                break;
            case VERTICAL:
            default:
                initialMotionValue = this.mInitialMotionY;
                lastMotionValue = this.mLastMotionY;
        }

        int newScrollValue;
        int itemDimension;
        switch(this.mCurrentMode) {
            case PULL_FROM_END:
                newScrollValue = Math.round(Math.max(initialMotionValue - lastMotionValue, 0.0F) / 2.0F);
                itemDimension = this.getFooterSize();
                break;
            case PULL_FROM_START:
            default:
                newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0.0F) / 2.0F);
                itemDimension = this.getHeaderSize();
        }

        this.setHeaderScroll(newScrollValue);
        if (newScrollValue != 0 && !this.isRefreshing()) {
            float scale = (float) Math.abs(newScrollValue) / (float)itemDimension;
            switch(this.mCurrentMode) {
                case PULL_FROM_END:
                    this.mFooterLayout.onPull(scale);
                    break;
                case PULL_FROM_START:
                default:
                    this.mHeaderLayout.onPull(scale);
            }

            if (this.mState != PullToRefreshBase.State.PULL_TO_REFRESH && itemDimension >= Math.abs(newScrollValue)) {
                this.setState(PullToRefreshBase.State.PULL_TO_REFRESH);
            } else if (this.mState == PullToRefreshBase.State.PULL_TO_REFRESH && itemDimension < Math.abs(newScrollValue)) {
                this.setState(PullToRefreshBase.State.RELEASE_TO_REFRESH);
            }
        }

    }

    private android.widget.LinearLayout.LayoutParams getLoadingLayoutLayoutParams() {
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                return new android.widget.LinearLayout.LayoutParams(-2, -1);
            case VERTICAL:
            default:
                return new android.widget.LinearLayout.LayoutParams(-1, -2);
        }
    }

    private int getMaximumPullScroll() {
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                return Math.round((float)this.getWidth() / 2.0F);
            case VERTICAL:
            default:
                return Math.round((float)this.getHeight() / 2.0F);
        }
    }

    private final void smoothScrollTo(int scrollValue, long duration) {
        this.smoothScrollTo(scrollValue, duration, 0L, (PullToRefreshBase.OnSmoothScrollFinishedListener)null);
    }

    private final void smoothScrollTo(int newScrollValue, long duration, long delayMillis, PullToRefreshBase.OnSmoothScrollFinishedListener listener) {
        if (null != this.mCurrentSmoothScrollRunnable) {
            this.mCurrentSmoothScrollRunnable.stop();
        }

        int oldScrollValue;
        switch(this.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                oldScrollValue = this.getScrollX();
                break;
            case VERTICAL:
            default:
                oldScrollValue = this.getScrollY();
        }

        if (oldScrollValue != newScrollValue) {
            if (null == this.mScrollAnimationInterpolator) {
                this.mScrollAnimationInterpolator = new DecelerateInterpolator();
            }

            this.mCurrentSmoothScrollRunnable = new PullToRefreshBase.SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);
            if (delayMillis > 0L) {
                this.postDelayed(this.mCurrentSmoothScrollRunnable, delayMillis);
            } else {
                this.post(this.mCurrentSmoothScrollRunnable);
            }
        }

    }

    private final void smoothScrollToAndBack(int y) {
        this.smoothScrollTo(y, 200L, 0L, new PullToRefreshBase.OnSmoothScrollFinishedListener() {
            public void onSmoothScrollFinished() {
                PullToRefreshBase.this.smoothScrollTo(0, 200L, 225L, (PullToRefreshBase.OnSmoothScrollFinishedListener)null);
            }
        });
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    final class SmoothScrollRunnable implements Runnable {
        private final Interpolator mInterpolator;
        private final int mScrollToY;
        private final int mScrollFromY;
        private final long mDuration;
        private PullToRefreshBase.OnSmoothScrollFinishedListener mListener;
        private boolean mContinueRunning = true;
        private long mStartTime = -1L;
        private int mCurrentY = -1;

        public SmoothScrollRunnable(int fromY, int toY, long duration, PullToRefreshBase.OnSmoothScrollFinishedListener listener) {
            this.mScrollFromY = fromY;
            this.mScrollToY = toY;
            this.mInterpolator = PullToRefreshBase.this.mScrollAnimationInterpolator;
            this.mDuration = duration;
            this.mListener = listener;
        }

        public void run() {
            if (this.mStartTime == -1L) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                long normalizedTime = 1000L * (System.currentTimeMillis() - this.mStartTime) / this.mDuration;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000L), 0L);
                int deltaY = Math.round((float)(this.mScrollFromY - this.mScrollToY) * this.mInterpolator.getInterpolation((float)normalizedTime / 1000.0F));
                this.mCurrentY = this.mScrollFromY - deltaY;
                PullToRefreshBase.this.setHeaderScroll(this.mCurrentY);
            }

            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                ViewCompat.postOnAnimation(PullToRefreshBase.this, this);
            } else if (null != this.mListener) {
                this.mListener.onSmoothScrollFinished();
            }

        }

        public void stop() {
            this.mContinueRunning = false;
            PullToRefreshBase.this.removeCallbacks(this);
        }
    }

    public static enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);

        private int mIntValue;

        static PullToRefreshBase.State mapIntToValue(int stateInt) {
            PullToRefreshBase.State[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PullToRefreshBase.State value = var1[var3];
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }

            return RESET;
        }

        private State(int intValue) {
            this.mIntValue = intValue;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public static enum Orientation {
        VERTICAL,
        HORIZONTAL;

        private Orientation() {
        }
    }

    public interface OnRefreshListener2<V extends View> {
        void onPullDownToRefresh(PullToRefreshBase<V> var1);

        void onPullUpToRefresh(PullToRefreshBase<V> var1);
    }

    public interface OnRefreshListener<V extends View> {
        void onRefresh(PullToRefreshBase<V> var1);
    }

    public interface OnPullEventListener<V extends View> {
        void onPullEvent(PullToRefreshBase<V> var1, PullToRefreshBase.State var2, PullToRefreshBase.Mode var3);
    }

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    public static enum Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);

        /** @deprecated */
        public static PullToRefreshBase.Mode PULL_DOWN_TO_REFRESH = PULL_FROM_START;
        /** @deprecated */
        public static PullToRefreshBase.Mode PULL_UP_TO_REFRESH = PULL_FROM_END;
        private int mIntValue;

        static PullToRefreshBase.Mode mapIntToValue(int modeInt) {
            PullToRefreshBase.Mode[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PullToRefreshBase.Mode value = var1[var3];
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }

            return getDefault();
        }

        static PullToRefreshBase.Mode getDefault() {
            return PULL_FROM_START;
        }

        private Mode(int modeInt) {
            this.mIntValue = modeInt;
        }

        boolean permitsPullToRefresh() {
            return this != DISABLED && this != MANUAL_REFRESH_ONLY;
        }

        public boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public static enum AnimationStyle {
        ROTATE,
        FLIP;

        private AnimationStyle() {
        }

        static PullToRefreshBase.AnimationStyle getDefault() {
            return ROTATE;
        }

        static PullToRefreshBase.AnimationStyle mapIntToValue(int modeInt) {
            switch(modeInt) {
                case 0:
                default:
                    return ROTATE;
                case 1:
                    return FLIP;
            }
        }

        LoadingLayout createLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
            switch(this) {
                case ROTATE:
                default:
                    return new RotateLoadingLayout(context, mode, scrollDirection, attrs);
                case FLIP:
                    return new FlipLoadingLayout(context, mode, scrollDirection, attrs);
            }
        }
    }
}
