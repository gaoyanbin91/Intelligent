package com.gao.intelligent.view.pulltorefresh;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public interface IPullToRefresh <T extends View> {
    boolean demo();

    PullToRefreshBase.Mode getCurrentMode();

    boolean getFilterTouchEvents();

    ILoadingLayout getLoadingLayoutProxy();

    ILoadingLayout getLoadingLayoutProxy(boolean var1, boolean var2);

    PullToRefreshBase.Mode getMode();

    T getRefreshableView();

    boolean getShowViewWhileRefreshing();

    PullToRefreshBase.State getState();

    boolean isPullToRefreshEnabled();

    boolean isPullToRefreshOverScrollEnabled();

    boolean isRefreshing();

    boolean isScrollingWhileRefreshingEnabled();

    void onRefreshComplete();

    void setFilterTouchEvents(boolean var1);

    void setMode(PullToRefreshBase.Mode var1);

    void setOnPullEventListener(PullToRefreshBase.OnPullEventListener<T> var1);

    void setOnRefreshListener(PullToRefreshBase.OnRefreshListener<T> var1);

    void setOnRefreshListener(PullToRefreshBase.OnRefreshListener2<T> var1);

    void setPullToRefreshOverScrollEnabled(boolean var1);

    void setRefreshing();

    void setRefreshing(boolean var1);

    void setScrollAnimationInterpolator(Interpolator var1);

    void setScrollingWhileRefreshingEnabled(boolean var1);

    void setShowViewWhileRefreshing(boolean var1);
}

