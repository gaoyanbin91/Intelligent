package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.RecordDetailAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.RecordDetailBean;
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
 * 描述:运行记录 详细
 */
public class RecordDetailActivity extends BaseActivity {
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private static final int SAVE_CHANGE_STATE = 100;
    private RecordDetailAdapter mAdapter;
    private List<RecordDetailBean.RowsBean> mLists = new ArrayList<>();
    private boolean isShowFooter = true;
    private static int currentPage = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_record_detail;
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                switch (type) {
                    case 10012:
                        hideCustomProgressDialog();
//                        LogUtils.d("运行记录详情", obj);
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }

                        RecordDetailBean bean = JSON.parseObject(obj.toString(), RecordDetailBean.class);

                        if (bean.getPage() == 1) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                            mLists = bean.getRows();
                            mAdapter.mList = mLists;
                            if (mLists.size() >= bean.getTotal()) {
                                mAdapter.setFooterShow(true);
                                isShowFooter = true;
                                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
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
                            List<RecordDetailBean.RowsBean> datas = new ArrayList<>();

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
                mPullToRefreshRecyclerView.onRefreshComplete();
                 hideCustomProgressDialog();
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
        mAdapter = new RecordDetailAdapter(RecordDetailActivity.this, new ArrayList<RecordDetailBean.RowsBean>());
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
                param.put("pageSize", "10");

                param.put("defaultCurrent", String.valueOf(currentPage));
                param.put("scDeviceRunningId", getIntent().getStringExtra("recordID"));
                sendHttpGet(ApiService.QUERY_RECORD_DETAIL_LIST, param, 10012);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                currentPage++;
                // getCookingSkillVideo(mSelectedTag, currentPage, Add_Tag_Cooking_Skills_Video);
                showProgressDialog("加载中...");
                HashMap<String, String> param = new HashMap<>();
                param.put("pageSize", "10");
                param.put("defaultCurrent", String.valueOf(currentPage));
                param.put("scDeviceRunningId", getIntent().getStringExtra("recordID"));
                sendHttpGet(ApiService.QUERY_RECORD_DETAIL_LIST, param, 10012);
            }
        });

        mPullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        showProgressDialog("加载中...");
        HashMap<String, String> param = new HashMap<>();
        param.put("pageSize", "10");
        param.put("defaultCurrent", String.valueOf(currentPage));
        param.put("scDeviceRunningId", getIntent().getStringExtra("recordID"));
        sendHttpGet(ApiService.QUERY_RECORD_DETAIL_LIST, param, 10012);
        //item跳转事件
            mAdapter.setMyItemClick(new RecordDetailAdapter.MyItemClick() {
                @Override
                public void onItemClick(RecordDetailBean.RowsBean model) {
                    Intent supp = new Intent(RecordDetailActivity.this, SupplementRecordActivity.class);
                    supp.putExtra("ID", model.getId());
                    startActivity(supp);
                }
            });
    }
}
