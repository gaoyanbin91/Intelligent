package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ProcessTemplateAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ProcessTemplateBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/6/22.
 * 描述:工艺模板列表
 */
public class ProcessTemplateActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.no_internet)
    View noInternet;
    private ProcessTemplateAdapter mAdapter;
    private List<ProcessTemplateBean.RowsBean> mLists = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_process_template;
    }



    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
//                LogUtils.i("工艺模板", obj.toString());
                if (!TextUtils.isEmpty(obj.toString())) {
                    ProcessTemplateBean processTemplateBean = JSON.parseObject(obj.toString(), ProcessTemplateBean.class);
                        if (processTemplateBean.getRows()!=null){
                            mLists = processTemplateBean.getRows();
                            if (mLists.size()>0){
                               noInternet.setVisibility(View.GONE);

                            mAdapter = new ProcessTemplateAdapter(this, mLists);
                            mListView.setAdapter(mAdapter);
                           }else {
                               noInternet.setVisibility(View.VISIBLE);
                           }
                        }
                }
                break;

            case 10003:
                noInternet.setVisibility(View.VISIBLE);
                LogUtils.i("工艺模板", obj.toString());
                hideCustomProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    public void initData() {
        HashMap<String, String> param = new HashMap<>();
        param.put("fjProductionLineId", getIntent().getStringExtra("lineID"));
        showProgressDialog("加载中..");
        sendHttpGet(ApiService.QUERY_PROCESS_TEMPLATE_LIST, param, 10051);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent template = new Intent(ProcessTemplateActivity.this, ProcessTypeActivity.class);
                template.putExtra("ID", mLists.get(position).getId());
                template.putExtra("flag", "list");
                startActivity(template);

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
