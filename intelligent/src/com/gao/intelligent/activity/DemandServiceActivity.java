package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.DemandAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.DemandBean;
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
 * Created by gaoyanbin on 2018/5/28.
 * 描述:服务请求列表
 */
public class DemandServiceActivity extends BaseActivity {
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private static final int SAVE_CHANGE_STATE = 100;
    private DemandAdapter mAdapter;
    private List<DemandBean.RowsBean> mLists = new ArrayList<>();
    private boolean isShowFooter = true;
    private static int currentPage = 1;
    @BindView(R.id.add_button)
    TextView addButton;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_demand_service;
    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        switch (what) {
            case SAVE_CHANGE_STATE:
                HashMap<String, String> change = new HashMap<>();
                change.put("id", obj.toString());
                change.put("state", "1");
                showProgressDialog("正在解决..");
                sendHttpPost(ApiService.SAVE_CHANGE_DEMAND, change, 10009);
                break;
        }
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                switch (type) {
                    case 10005:
                        hideCustomProgressDialog();
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        LogUtils.i("服务请求数据", obj.toString());
                        DemandBean bean = JSON.parseObject(obj.toString(), DemandBean.class);
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
                            List<DemandBean.RowsBean> datas = new ArrayList<>();
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

    @OnClick(R.id.toolbar)
    public void onBack() {
        finish();
    }

    @Override
    public void initData() {
        if (getIntent().getStringExtra("flag") != null) {
            addButton.setVisibility(View.VISIBLE);

        } else {
            addButton.setVisibility(View.GONE);
        }
        mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
        mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new DemandAdapter(DemandServiceActivity.this, new ArrayList<DemandBean.RowsBean>());
        MyDecoration0_25dp divider = new MyDecoration0_25dp(this, MyDecoration0_25dp.VERTICAL_LIST);
        divider.setDivider(R.drawable.divider_transparent_0_25dp);
        mPullToRefreshRecyclerView.getRefreshableView().addItemDecoration(divider);
        mPullToRefreshRecyclerView.getRefreshableView().setAdapter(mAdapter);

        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                showProgressDialog("加载中...");
                currentPage = 1;
                HashMap<String, String> param = new HashMap<>();
                param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
                param.put("pageSize", "10");
                param.put("sort", "createTime");
                param.put("order", "DESC");
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
                param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
                param.put("pageSize", "10");
                param.put("sort", "createTime");
                param.put("order", "DESC");
                param.put("defaultCurrent", "2");
                sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
            }
        });

        mPullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        showProgressDialog("加载中...");
        HashMap<String, String> param = new HashMap<>();
        param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
        param.put("pageSize", "10");
        param.put("sort", "createTime");
        param.put("order", "DESC");
        param.put("defaultCurrent", String.valueOf(currentPage));
        sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
        mAdapter.setMyItemClick(new DemandAdapter.MyItemClick() {
            @Override
            public void onItemClick(DemandBean.RowsBean model) {
                Intent detail = new Intent(DemandServiceActivity.this, DemandDetailActivity.class);
                detail.putExtra("ID", model.getId());
                detail.putExtra("state", model.getState());
                detail.putExtra("phone", model.getPhone());
                startActivity(detail);
            }
        });
//        mAdapter.setMyItemButtonClick(new DemandAdapter.MyItemButtonClick() {
//            @Override
//            public void onItemButtonClick(DemandBean.RowsBean model) {
//               if (model.getState().equals("0")) {
//                   showAlertDialog(getString(R.string.prompt), "是否确认解决？", getString(R.string.picker_yes), getString(R.string.cancel), null, SAVE_CHANGE_STATE, CUSTOM_DISMISS, model.getId(), null);
//               }
//
//            }
//        });
    }

}
