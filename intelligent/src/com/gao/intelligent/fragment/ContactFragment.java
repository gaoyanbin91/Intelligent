package com.gao.intelligent.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gao.intelligent.R;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;
import com.gao.intelligent.view.pulltorefresh.extral.PullToRefreshRecyclerView;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/4/12.
 * 描述:通讯录页面
 */
public class ContactFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2{
    @BindView(R.id.re_title)
    View reTitle;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView pullToRefreshRecyclerView;
    @Override
    protected void initView() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) reTitle.getLayoutParams();
        linearParams.width = width;
        linearParams.height = width * 25 / 72;
        reTitle.setLayoutParams(linearParams);
        pullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
         pullToRefreshRecyclerView.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase var1) {
//        ToastUtils.showShort("下拉刷新");
//        try {
//            Thread.sleep(2000);
//
//        }catch (Exception e){
//
//        }
       pullToRefreshRecyclerView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase var1) {
        ToastUtils.showShort("上拉加载");
//        try {
//            Thread.sleep(2000);
//
//        }catch (Exception e){
//
//        }
        pullToRefreshRecyclerView.onRefreshComplete();


    }
}
