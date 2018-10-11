package com.gao.intelligent.view.pulltorefresh.extral;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.gao.intelligent.view.pulltorefresh.PullToRefreshExpandableListView;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class PullToRefreshExpandableListFragment extends PullToRefreshBaseListFragment<PullToRefreshExpandableListView> {
    public PullToRefreshExpandableListFragment() {
    }

    protected PullToRefreshExpandableListView onCreatePullToRefreshListView(LayoutInflater inflater, Bundle savedInstanceState) {
        return new PullToRefreshExpandableListView(this.getActivity());
    }
}