package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.gao.intelligent.view.pulltorefresh.internal.EmptyViewMethodAccessor;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {
    public PullToRefreshGridView(Context context) {
        super(context);
    }

    public PullToRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshGridView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshGridView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected final GridView createRefreshableView(Context context, AttributeSet attrs) {
        Object gv;
        if (VERSION.SDK_INT >= 9) {
            gv = new PullToRefreshGridView.InternalGridViewSDK9(context, attrs);
        } else {
            gv = new PullToRefreshGridView.InternalGridView(context, attrs);
        }

       // ((GridView)gv).setId(id.gridview);
        return (GridView)gv;
    }

    @TargetApi(9)
    final class InternalGridViewSDK9 extends PullToRefreshGridView.InternalGridView {
        public InternalGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshGridView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);
            return returnValue;
        }
    }

    class InternalGridView extends GridView implements EmptyViewMethodAccessor {
        public InternalGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setEmptyView(View emptyView) {
            PullToRefreshGridView.this.setEmptyView(emptyView);
        }

        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }
}

