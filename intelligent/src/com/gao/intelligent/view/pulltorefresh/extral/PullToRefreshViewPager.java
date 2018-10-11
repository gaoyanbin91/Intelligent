package com.gao.intelligent.view.pulltorefresh.extral;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

public class PullToRefreshViewPager extends PullToRefreshBase<ViewPager> {
    public PullToRefreshViewPager(Context context) {
        super(context);
    }

    public PullToRefreshViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.HORIZONTAL;
    }

    protected ViewPager createRefreshableView(Context context, AttributeSet attrs) {
        ViewPager viewPager = new ViewPager(context, attrs);
        //viewPager.setId(id.viewpager);
        return viewPager;
    }

    protected boolean isReadyForPullStart() {
        ViewPager refreshableView = (ViewPager)this.getRefreshableView();
        PagerAdapter adapter = refreshableView.getAdapter();
        if (null != adapter) {
            return refreshableView.getCurrentItem() == 0;
        } else {
            return false;
        }
    }

    protected boolean isReadyForPullEnd() {
        ViewPager refreshableView = (ViewPager)this.getRefreshableView();
        PagerAdapter adapter = refreshableView.getAdapter();
        if (null != adapter) {
            return refreshableView.getCurrentItem() == adapter.getCount() - 1;
        } else {
            return false;
        }
    }
}

