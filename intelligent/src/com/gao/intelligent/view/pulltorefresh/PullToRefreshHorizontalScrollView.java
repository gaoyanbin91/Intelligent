package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshHorizontalScrollView extends PullToRefreshBase<HorizontalScrollView> {
    public PullToRefreshHorizontalScrollView(Context context) {
        super(context);
    }

    public PullToRefreshHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshHorizontalScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshHorizontalScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.HORIZONTAL;
    }

    protected HorizontalScrollView createRefreshableView(Context context, AttributeSet attrs) {
        Object scrollView;
        if (VERSION.SDK_INT >= 9) {
            scrollView = new PullToRefreshHorizontalScrollView.InternalHorizontalScrollViewSDK9(context, attrs);
        } else {
            scrollView = new HorizontalScrollView(context, attrs);
        }

      //  ((HorizontalScrollView)scrollView).setId(id.scrollview);
        return (HorizontalScrollView)scrollView;
    }

    protected boolean isReadyForPullStart() {
        return ((HorizontalScrollView)this.mRefreshableView).getScrollX() == 0;
    }

    protected boolean isReadyForPullEnd() {
        View scrollViewChild = ((HorizontalScrollView)this.mRefreshableView).getChildAt(0);
        if (null != scrollViewChild) {
            return ((HorizontalScrollView)this.mRefreshableView).getScrollX() >= scrollViewChild.getWidth() - this.getWidth();
        } else {
            return false;
        }
    }

    @TargetApi(9)
    final class InternalHorizontalScrollViewSDK9 extends HorizontalScrollView {
        public InternalHorizontalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshHorizontalScrollView.this, deltaX, scrollX, deltaY, scrollY, this.getScrollRange(), isTouchEvent);
            return returnValue;
        }

        private int getScrollRange() {
            int scrollRange = 0;
            if (this.getChildCount() > 0) {
                View child = this.getChildAt(0);
                scrollRange = Math.max(0, child.getWidth() - (this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()));
            }

            return scrollRange;
        }
    }
}

