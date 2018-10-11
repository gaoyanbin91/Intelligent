package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.RecordAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.RecordBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.mydivider.MyDecoration0_25dp;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;
import com.gao.intelligent.view.pulltorefresh.extral.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/6/12.
 * 描述:设备运行记录路列表页面
 */
public class RecordListActivity extends BaseActivity {
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.no_internet)
    View noInternet;//
    private static final int SAVE_CHANGE_STATE = 100;
    private RecordAdapter mAdapter;
    private boolean isShowFooter = true;
    private static int currentPage = 1;
    private List<RecordBean.RowsBean> mLists = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_record_list;
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                switch (type) {
                    case 10011:
                        hideCustomProgressDialog();
//                        LogUtils.d("运行记录", obj);
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        RecordBean bean = JSON.parseObject(obj.toString(), RecordBean.class);
                        if (bean.getPage() == 1) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                            mLists = bean.getRows();
                            mAdapter.mList = mLists;
                            if (mLists.size()==0){
                                noInternet.setVisibility(View.VISIBLE);
                                mPullToRefreshRecyclerView.setVisibility(View.GONE);
                            } else if (mLists.size() >= bean.getTotal()) {
                                noInternet.setVisibility(View.GONE);
                                mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                                mAdapter.setFooterShow(true);
                                isShowFooter = true;
                                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                noInternet.setVisibility(View.GONE);
                                mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                                mAdapter.setFooterShow(false);
                                isShowFooter = false;
                                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                            mAdapter.notifyDataSetChanged();
                            mPullToRefreshRecyclerView.getRefreshableView().scrollToPosition(0);
                            return;
                        }
                        if (currentPage > 1) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                            List<RecordBean.RowsBean> datas = new ArrayList<>();
                            datas = bean.getRows();
                            mLists.addAll(datas);
                            if (datas.size() < 10 && mLists.size() >= bean.getTotal()) {
                                isShowFooter = false;
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
                }

                break;
            case 10003:
                mPullToRefreshRecyclerView.setVisibility(View.GONE);
                noInternet.setVisibility(View.VISIBLE);
                hideCustomProgressDialog();
                mPullToRefreshRecyclerView.onRefreshComplete();
                // hideCustomProgressDialog();
                ToastUtils.showShort("请检查网络");
                break;

        }
    }

    @Override
    public void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new RecordAdapter(RecordListActivity.this, new ArrayList<RecordBean.RowsBean>());
        MyDecoration0_25dp divider = new MyDecoration0_25dp(this, MyDecoration0_25dp.VERTICAL_LIST);
        divider.setDivider(R.drawable.divider_transparent_0_25dp);
        mPullToRefreshRecyclerView.getRefreshableView().addItemDecoration(divider);
        mPullToRefreshRecyclerView.getRefreshableView().setAdapter(mAdapter);

        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                currentPage = 1;
                showProgressDialog("加载中...");
                HashMap<String, String> param = new HashMap<>();
                param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
                param.put("pageSize", "10");
                //    param.put("sort", "createTime");
                param.put("fjCustomerld", AppConfig.getInstance().getString("fjCustomerId", ""));
                param.put("defaultCurrent", String.valueOf(currentPage));
                sendHttpGet(ApiService.QUERY_RECORD_LIST, param, 10011);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                currentPage++;
                // getCookingSkillVideo(mSelectedTag, currentPage, Add_Tag_Cooking_Skills_Video);
                showProgressDialog("加载中...");
                HashMap<String, String> param = new HashMap<>();
                param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
                param.put("pageSize", "10");
                //    param.put("sort", "createTime");
                param.put("fjCustomerld", AppConfig.getInstance().getString("fjCustomerId", ""));
                param.put("defaultCurrent", String.valueOf(currentPage));
                sendHttpGet(ApiService.QUERY_RECORD_LIST, param, 10011);
            }
        });
        currentPage = 1;
        mPullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        showProgressDialog("加载中...");
        HashMap<String, String> param = new HashMap<>();
        param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
        param.put("pageSize", "10");
        param.put("fjCustomerld", AppConfig.getInstance().getString("fjCustomerId", ""));
        param.put("defaultCurrent", String.valueOf(currentPage));
        sendHttpGet(ApiService.QUERY_RECORD_LIST, param, 10011);
        //item跳转事件
        mAdapter.setMyItemClick(new RecordAdapter.MyItemClick() {
            @Override
            public void onItemClick(RecordBean.RowsBean model) {
                Intent iDetail = new Intent(RecordListActivity.this, RecordDetailActivity.class);
                iDetail.putExtra("recordID", model.getId());
                startActivity(iDetail);

            }
        });
    }
}
