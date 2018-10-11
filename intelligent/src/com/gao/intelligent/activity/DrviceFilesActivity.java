package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.DrviceFilesAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.DrviceFileBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Yanbin on 2018/7/16.
 * 描述: 设备档案 列表 页面
 */
public class DrviceFilesActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.no_internet)
    View noData;
    private DrviceFilesAdapter mAdapter;
    private List<DrviceFileBean.RowsBean> mLists = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_drvice_files;
    }

    @Override
    public void aidHandleMessage(int what,int type, Object obj) {
        super.aidHandleMessage(what, type,obj);
        switch (what) {

            case 10004:
                hideCustomProgressDialog();
                LogUtils.d("Files列表", obj);
                DrviceFileBean drviceFileBean = JSON.parseObject(obj.toString(), DrviceFileBean.class);
                if (drviceFileBean.getRows()!=null){
                    mLists = drviceFileBean.getRows();
                        if (mLists.size()==0){
                            noData.setVisibility(View.VISIBLE);
                        }else {
                            noData.setVisibility(View.GONE);
                        }
                    mAdapter = new DrviceFilesAdapter(baseContext, mLists);
                    mListView.setAdapter(mAdapter);
                }
                //tvF.setText( obj.toString());
                break;
            case 10003:
                hideCustomProgressDialog();
                noData.setVisibility(View.VISIBLE);
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
        param.put("id", getIntent().getStringExtra("id"));
       // param.put("model", getIntent().getStringExtra("model"));
        showProgressDialog("加载中..");
        sendHttpGet(ApiService.QUERY_DRVICE_FILES_LISTS,param,13011);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(baseContext, ShowPdfActivity.class);
                i.putExtra("url", mLists.get(position).getAddress());
                i.putExtra("name", mLists.get(position).getName());
                startActivity(i);
            }
        });
    }
}
