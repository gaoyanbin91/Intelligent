package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.DeviceListAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.DrviceBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Yanbin on 2018/7/16.
 * 描述:设备列表页面
 */
public class DeviceListActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.no_internet)
    View noInternet;//
    private List<DrviceBean.RowsBean> mBeans = new ArrayList<>();
    private DeviceListAdapter mAdapter;
    @BindView(R.id.rootLayout)
    View rootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_drvice_list;
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();

                LogUtils.d("设备列表", obj);
                DrviceBean drviceBean = JSON.parseObject(obj.toString(), DrviceBean.class);
                if (drviceBean.getRows() != null) {
                    mBeans = drviceBean.getRows();
                    if (mBeans.size() == 0) {
                        noInternet.setVisibility(View.VISIBLE);
                        rootView.setVisibility(View.GONE);
                    } else {
                        rootView.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                    }
                    mAdapter = new DeviceListAdapter(baseContext, mBeans);
                    mListView.setAdapter(mAdapter);
                }
                break;
            case 10003:
                hideCustomProgressDialog();
                noInternet.setVisibility(View.VISIBLE);
                // ToastUtils.showShort(obj.toString());
                ToastUtils.showShort(obj.toString());
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
        HashMap<String, String> param = new HashMap<>();
        param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
        showProgressDialog("加载中..");
        sendHttpGet(ApiService.QUERY_DRVICE_LISTS, param, 11011);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(baseContext, DrviceFilesActivity.class);
                intent.putExtra("id", mBeans.get(position).getId());
                intent.putExtra("lineID", getIntent().getStringExtra("lineID"));
                //    intent.putExtra("model", mBeans.get(position).getModel());
                startActivity(intent);
            }
        });
    }
}
