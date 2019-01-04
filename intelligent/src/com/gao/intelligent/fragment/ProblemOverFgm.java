package com.gao.intelligent.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.AddSeviceActivity;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.adapter.ProblemAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.ProblemBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.mydivider.MyDecoration0_25dp;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;
import com.gao.intelligent.view.pulltorefresh.extral.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/23.
 * 描述:s数据展示
 */
public class ProblemOverFgm extends BaseFragment {
    @BindView(R.id.iv_add)
    ImageView mImageView;//新增
    @BindView(R.id.text)
    TextView mTextView;
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.no_internet)
    View noInternet;
    private ProblemAdapter mAdapter;
    private static int currentPage = 1;
    private String processState;
    private List<ProblemBean.RowsBean> mLists = new ArrayList<>();

    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_data_list;
    }


    @Override
    public void aidHandleMessage(int what,int type, Object obj) {
        super.aidHandleMessage(what,type, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                LogUtils.i("问题列表", obj.toString());

                ProblemBean bean = JSON.parseObject(obj.toString(), ProblemBean.class);

                if (bean.getPage() == 1) {
                    mPullToRefreshRecyclerView.onRefreshComplete();
                    mLists = bean.getRows();
                    mAdapter.mList = mLists;
                    if (mLists.size() == 0) {
                        mPullToRefreshRecyclerView.setVisibility(View.GONE);
                        noInternet.setVisibility(View.VISIBLE);
                    } else if (mLists.size() >= bean.getTotal()) {
                        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                        mAdapter.setFooterShow(true);
                        //isShowFooter = true;
                        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                        mAdapter.setFooterShow(false);
                        //isShowFooter = false;
                        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                    mAdapter.notifyDataSetChanged();
                    mPullToRefreshRecyclerView.getRefreshableView().scrollToPosition(0);
                    return;
                }
                if (currentPage > 1) {
                    mPullToRefreshRecyclerView.onRefreshComplete();
                    List<ProblemBean.RowsBean> datas = new ArrayList<>();

                    datas = bean.getRows();
                    mLists.addAll(datas);
                    if (datas.size() < 10 && mLists.size() >= bean.getTotal()) {
//                        isShowFooter = false;
                    }
                    mAdapter.mList = mLists;
                    if (mLists.size() >= bean.getTotal()) {
                        mAdapter.setFooterShow(true);
                        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        mAdapter.setFooterShow(false);
                        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                    }

                    mAdapter.notifyDataSetChanged();
                }

                break;
            case 10003:
                hideProgressDialog();
                //  ToastUtils.showShort(obj.toString());
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    protected void initView() {
        mImageView.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        showProgressDialog("加载中...");
        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new ProblemAdapter(getActivity(), new ArrayList<ProblemBean.RowsBean>());
        MyDecoration0_25dp divider = new MyDecoration0_25dp(getActivity(), MyDecoration0_25dp.VERTICAL_LIST);
        divider.setDivider(R.drawable.divider_transparent_0_25dp);
        mPullToRefreshRecyclerView.getRefreshableView().addItemDecoration(divider);
        mPullToRefreshRecyclerView.getRefreshableView().setAdapter(mAdapter);

        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                showProgressDialog("加载中...");
                currentPage = 1;
                HashMap<String, String> param = new HashMap<>();
                param.put("pageSize", "10");
                param.put("processState", "2");
                param.put("introducerId", AppConfig.getInstance().getString("customerId", ""));
                param.put("defaultCurrent", String.valueOf(currentPage));
                sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                currentPage++;
                // getCookingSkillVideo(mSelectedTag, currentPage, Add_Tag_Cooking_Skills_Video);
                showProgressDialog("加载中..." + currentPage);
//                ToastUtils.showShort(currentPage+"");
                HashMap<String, String> param = new HashMap<>();
                param.put("pageSize", "10");
                param.put("processState", "2");
                param.put("introducerId", AppConfig.getInstance().getString("customerId", ""));
                param.put("defaultCurrent", String.valueOf(currentPage));
                sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
            }
        });

        mPullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        HashMap<String, String> param = new HashMap<>();
        param.put("pageSize", "10");
        param.put("processState", "2");
        mTextView.setText("2");
        param.put("introducerId", AppConfig.getInstance().getString("customerId", ""));
        param.put("defaultCurrent", String.valueOf(currentPage));
        LogUtils.d("页码", getArguments().getString("flag"));
        sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
    }


    @OnClick({R.id.iv_add})
    public void onClick() {
        startActivity(new Intent(getActivity(), AddSeviceActivity.class));
    }

}
