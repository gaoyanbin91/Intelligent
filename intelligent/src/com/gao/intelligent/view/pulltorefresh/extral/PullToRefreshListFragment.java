package com.gao.intelligent.view.pulltorefresh.extral;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshListView;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class PullToRefreshListFragment extends PullToRefreshBaseListFragment<PullToRefreshListView> {
    public PullToRefreshListFragment() {
    }

    protected PullToRefreshListView onCreatePullToRefreshListView(LayoutInflater inflater, Bundle savedInstanceState) {
        return new PullToRefreshListView(this.getActivity());
    }
}
