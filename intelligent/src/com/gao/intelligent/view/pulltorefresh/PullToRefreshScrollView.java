package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {
    public PullToRefreshScrollView(Context context) {
        super(context);
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        Object scrollView;
        if (VERSION.SDK_INT >= 9) {
            scrollView = new PullToRefreshScrollView.InternalScrollViewSDK9(context, attrs);
        } else {
            scrollView = new ScrollView(context, attrs);
        }

      //  ((ScrollView)scrollView).setId(id.scrollview);
        return (ScrollView)scrollView;
    }

    protected boolean isReadyForPullStart() {
        return ((ScrollView)this.mRefreshableView).getScrollY() == 0;
    }

    protected boolean isReadyForPullEnd() {
        View scrollViewChild = ((ScrollView)this.mRefreshableView).getChildAt(0);
        if (null != scrollViewChild) {
            return ((ScrollView)this.mRefreshableView).getScrollY() >= scrollViewChild.getHeight() - this.getHeight();
        } else {
            return false;
        }
    }

    @TargetApi(9)
    final class InternalScrollViewSDK9 extends ScrollView {
        public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshScrollView.this, deltaX, scrollX, deltaY, scrollY, this.getScrollRange(), isTouchEvent);
            return returnValue;
        }

        private int getScrollRange() {
            int scrollRange = 0;
            if (this.getChildCount() > 0) {
                View child = this.getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
            }

            return scrollRange;
        }
    }
}

