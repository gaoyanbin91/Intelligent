package com.gao.intelligent.view.pulltorefresh.extral;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

abstract class PullToRefreshBaseListFragment<T extends PullToRefreshBase<? extends AbsListView>> extends ListFragment {
    private T mPullToRefreshListView;

    PullToRefreshBaseListFragment() {
    }

    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        ListView lv = (ListView)layout.findViewById(16908298);
        ViewGroup parent = (ViewGroup)lv.getParent();
        int lvIndex = parent.indexOfChild(lv);
        parent.removeViewAt(lvIndex);
        this.mPullToRefreshListView = this.onCreatePullToRefreshListView(inflater, savedInstanceState);
        parent.addView(this.mPullToRefreshListView, lvIndex, lv.getLayoutParams());
        return layout;
    }

    public final T getPullToRefreshListView() {
        return this.mPullToRefreshListView;
    }

    protected abstract T onCreatePullToRefreshListView(LayoutInflater var1, Bundle var2);
}
