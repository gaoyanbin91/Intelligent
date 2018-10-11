package com.gao.intelligent.view.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.gao.intelligent.view.pulltorefresh.internal.EmptyViewMethodAccessor;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshExpandableListView extends PullToRefreshAdapterViewBase<ExpandableListView> {
    public PullToRefreshExpandableListView(Context context) {
        super(context);
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshExpandableListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshExpandableListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected ExpandableListView createRefreshableView(Context context, AttributeSet attrs) {
        Object lv;
        if (VERSION.SDK_INT >= 9) {
            lv = new PullToRefreshExpandableListView.InternalExpandableListViewSDK9(context, attrs);
        } else {
            lv = new PullToRefreshExpandableListView.InternalExpandableListView(context, attrs);
        }
                int s =16908298 ;
        ((ExpandableListView)lv).setId(s);
        return (ExpandableListView)lv;
    }

    @TargetApi(9)
    final class InternalExpandableListViewSDK9 extends PullToRefreshExpandableListView.InternalExpandableListView {
        public InternalExpandableListViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshExpandableListView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);
            return returnValue;
        }
    }

    class InternalExpandableListView extends ExpandableListView implements EmptyViewMethodAccessor {
        public InternalExpandableListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setEmptyView(View emptyView) {
            PullToRefreshExpandableListView.this.setEmptyView(emptyView);
        }

        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }
}
