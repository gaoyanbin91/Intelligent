package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ServiceLisAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.DemandBean;
import com.gao.intelligent.model.ResultBean;
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
 * Created by Yanbin on 2018/9/14.
 * 描述:
 */
public class ServiceLIstAty extends BaseActivity {
    @BindView(R.id.pullToRefreshRecyclerView)
    PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    @BindView(R.id.no_internet)
    View noInternet;
    private static final int SAVE_CHANGE_STATE = 100;
    private ServiceLisAdapter mAdapter;
    private List<DemandBean.RowsBean> mLists = new ArrayList<>();
    private boolean isShowFooter = true;
    private static int currentPage = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_services_lis;
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
                        //   mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//                        LogUtils.d("LineDatas", obj);
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
//                        LogUtils.i("服务请求" + type, obj.toString());
                        DemandBean bean = JSON.parseObject(obj.toString(), DemandBean.class);

                        if (bean.getPage() == 1) {
                            mPullToRefreshRecyclerView.onRefreshComplete();
                            mLists = bean.getRows();
                            mAdapter.mList = mLists;
                            if (mLists.size() ==0) {
                                mPullToRefreshRecyclerView.setVisibility(View.GONE);
                                noInternet.setVisibility(View.VISIBLE);
                            } else if (mLists.size() >= bean.getTotal()) {
                                mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                                noInternet.setVisibility(View.GONE);
                                mAdapter.setFooterShow(true);
                                isShowFooter = true;
                                mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                mPullToRefreshRecyclerView.setVisibility(View.VISIBLE);
                                noInternet.setVisibility(View.GONE);
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
                    case 10009:
                        hideProgressDialog();
//                        LogUtils.i("解决", obj.toString());
                        ResultBean resultBean = JSON.parseObject(obj.toString(), ResultBean.class);
                        if (resultBean.getResultCode().equals("1")) {
                            ToastUtils.showShort("服务请求已解决");
                            showProgressDialog("刷新中...");
                            HashMap<String, String> param = new HashMap<>();
                            param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
                            param.put("pageSize", "10");
                            param.put("sort", "createTime");
                            param.put("order", "DESC");
                            param.put("defaultCurrent", String.valueOf(currentPage));
                            sendHttpGet(ApiService.QUERY_DEMAND_SERVICE_LIST, param, 10005);
                        }
                        break;
                }

                break;
            case 10003:
                mPullToRefreshRecyclerView.setVisibility(View.GONE);
                noInternet.setVisibility(View.VISIBLE);
                mPullToRefreshRecyclerView.onRefreshComplete();
                hideCustomProgressDialog();
                ToastUtils.showShort("请检查网络");
                break;

        }
    }


    @OnClick(R.id.add_button)
    public void addData() {
        Intent i1 = new Intent(this, AddSeviceActivity.class);
        i1.putExtra("lineID", getIntent().getStringExtra("lineID"));
        startActivity(i1);
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
        mAdapter = new ServiceLisAdapter( ServiceLIstAty.this, new ArrayList<DemandBean.RowsBean>());
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
        mAdapter.setMyItemClick(new ServiceLisAdapter.MyItemClick() {
            @Override
            public void onItemClick(DemandBean.RowsBean model) {
                Intent detail = new Intent(ServiceLIstAty.this, DemandDetailActivity.class);
                detail.putExtra("ID", model.getId());
                detail.putExtra("state", model.getState());
                startActivity(detail);
            }
        });
        mAdapter.setMyItemButtonClick(new ServiceLisAdapter.MyItemButtonClick() {
            @Override
            public void onItemButtonClick(DemandBean.RowsBean model) {
                if (model.getState().equals("0")) {
                    showAlertDialog(getString(R.string.prompt), "是否确认解决？", getString(R.string.picker_yes), getString(R.string.cancel), null, SAVE_CHANGE_STATE, CUSTOM_DISMISS, model.getId(), null);
                }
            }
        });
    }
}
