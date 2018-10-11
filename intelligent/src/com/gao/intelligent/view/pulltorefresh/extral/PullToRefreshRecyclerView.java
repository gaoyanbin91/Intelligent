package com.gao.intelligent.view.pulltorefresh.extral;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        return recyclerView;
    }

    protected boolean isReadyForPullStart() {
        return this.isFirstItemVisible();
    }

    protected boolean isReadyForPullEnd() {
        return this.isLastItemVisible();
    }

    private boolean isFirstItemVisible() {
        RecyclerView.Adapter<?> adapter = ((RecyclerView)this.getRefreshableView()).getAdapter();
        if (null != adapter && adapter.getItemCount() != 0) {
            if (this.getFirstVisiblePosition() == 0) {
                return ((RecyclerView)this.mRefreshableView).getChildAt(0).getTop() >= ((RecyclerView)this.mRefreshableView).getTop();
            } else {
                return false;
            }
        } else {
         //   LibraryLogUtils.info("isFirstItemVisible. Empty View.");
            return true;
        }
    }

    private int getFirstVisiblePosition() {
        View firstVisibleChild = ((RecyclerView)this.mRefreshableView).getChildAt(0);
        return firstVisibleChild != null ? ((RecyclerView)this.mRefreshableView).getChildAdapterPosition(firstVisibleChild) : -1;
    }

    private boolean isLastItemVisible() {
        RecyclerView.Adapter<?> adapter = ((RecyclerView)this.getRefreshableView()).getAdapter();
        if (null != adapter && adapter.getItemCount() != 0) {
            int lastVisiblePosition = this.getLastVisiblePosition();
            if (lastVisiblePosition >= ((RecyclerView)this.mRefreshableView).getAdapter().getItemCount() - 1) {
                return ((RecyclerView)this.mRefreshableView).getChildAt(((RecyclerView)this.mRefreshableView).getChildCount() - 1).getBottom() <= ((RecyclerView)this.mRefreshableView).getBottom();
            } else {
                return false;
            }
        } else {
          //  LibraryLogUtils.info("isFirstItemVisible. Empty View.");
            return true;
        }
    }

    private int getLastVisiblePosition() {
        View lastVisibleChild = ((RecyclerView)this.mRefreshableView).getChildAt(((RecyclerView)this.mRefreshableView).getChildCount() - 1);
        return lastVisibleChild != null ? ((RecyclerView)this.mRefreshableView).getChildAdapterPosition(lastVisibleChild) : -1;
    }
}

