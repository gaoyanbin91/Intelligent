package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.util.Log;
import android.view.View;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

@TargetApi(9)
public final class OverscrollHelper {
    static final String LOG_TAG = "OverscrollHelper";
    static final float DEFAULT_OVERSCROLL_SCALE = 1.0F;

    public OverscrollHelper() {
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, 0, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, scrollRange, 0, 1.0F, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, int fuzzyThreshold, float scaleFactor, boolean isTouchEvent) {
        int deltaValue;
        int currentScrollValue;
        int scrollValue;
        switch(view.getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                deltaValue = deltaX;
                scrollValue = scrollX;
                currentScrollValue = view.getScrollX();
                break;
            case VERTICAL:
            default:
                deltaValue = deltaY;
                scrollValue = scrollY;
                currentScrollValue = view.getScrollY();
        }

        if (view.isPullToRefreshOverScrollEnabled() && !view.isRefreshing()) {
            PullToRefreshBase.Mode mode = view.getMode();
            if (mode.permitsPullToRefresh() && !isTouchEvent && deltaValue != 0) {
                int newScrollValue = deltaValue + scrollValue;
                Log.d("OverscrollHelper", "OverScroll. DeltaX: " + deltaX + ", ScrollX: " + scrollX + ", DeltaY: " + deltaY + ", ScrollY: " + scrollY + ", NewY: " + newScrollValue + ", ScrollRange: " + scrollRange + ", CurrentScroll: " + currentScrollValue);
                if (newScrollValue < 0 - fuzzyThreshold) {
                    if (mode.showHeaderLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(PullToRefreshBase.State.OVERSCROLLING, new boolean[0]);
                        }

                        view.setHeaderScroll((int)(scaleFactor * (float)(currentScrollValue + newScrollValue)));
                    }
                } else if (newScrollValue > scrollRange + fuzzyThreshold) {
                    if (mode.showFooterLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(PullToRefreshBase.State.OVERSCROLLING, new boolean[0]);
                        }

                        view.setHeaderScroll((int)(scaleFactor * (float)(currentScrollValue + newScrollValue - scrollRange)));
                    }
                } else if (Math.abs(newScrollValue) <= fuzzyThreshold || Math.abs(newScrollValue - scrollRange) <= fuzzyThreshold) {
                    view.setState(PullToRefreshBase.State.RESET, new boolean[0]);
                }
            } else if (isTouchEvent && PullToRefreshBase.State.OVERSCROLLING == view.getState()) {
                view.setState(PullToRefreshBase.State.RESET, new boolean[0]);
            }
        }

    }

    static boolean isAndroidOverScrollEnabled(View view) {
        return view.getOverScrollMode() != 2;
    }
}
