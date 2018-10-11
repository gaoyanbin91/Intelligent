package com.gao.intelligent.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.DemandDetailAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.DemandDetailBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/5/29.
 * 描述:服务请求详情
 */
public class DemandDetailActivity extends BaseActivity {
    @BindView(R.id.text_name)
    TextView textName;//问题名称
    @BindView(R.id.text_level)
    TextView textLeve;//优先级
    @BindView(R.id.text_classfiy)
    TextView textClassfiy;//问题分类
    @BindView(R.id.text_create_name)
    TextView textCreateName;//创建人
    @BindView(R.id.text_create_time)
    TextView textCreateTime;//创建时间
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.add_button)
    TextView addButton;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.re_call_phone)
    View reCallPhone;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    private DemandDetailAdapter mDetailAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private List<DemandDetailBean.ObjectBean.DetailListBean> mlists = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    DemandDetailBean demandDetailBean;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_demand_detail;
    }

    HashMap<String, String> detailParam = new HashMap<>();

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {

            case 10004:
                hideProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                LogUtils.i("详情数据", obj.toString());

                demandDetailBean = JSON.parseObject(obj.toString(), DemandDetailBean.class);
                textName.setText(TextUtils.isEmpty(demandDetailBean.getObject().getName()) ? "" : "名称：" + demandDetailBean.getObject().getName());
                tvPhone.setText(TextUtils.isEmpty(demandDetailBean.getObject().getPhone()) ? "联系电话：" : "联系电话：" + demandDetailBean.getObject().getPhone());
                if (demandDetailBean.getObject().getPriority().equals("0")) {
                    textLeve.setText("重要");
                } else if (demandDetailBean.getObject().getPriority().equals("1")) {
                    textLeve.setText("一般");
                } else {
                    textLeve.setText("标准");
                }

                //  textLeve.setText(TextUtils.isEmpty(demandDetailBean.getObject().getPriority()) ? "" : demandDetailBean.getObject().getPriority());
                if (demandDetailBean.getObject().getPhone() != null) {
                    reCallPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + demandDetailBean.getObject()
                                    .getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }else {
                    ivCall.setVisibility(View.GONE);
                }
                textClassfiy.setText(TextUtils.isEmpty(demandDetailBean.getObject().getTypeName()) ? "" : demandDetailBean.getObject().getTypeName());
                textCreateName.setText(TextUtils.isEmpty(demandDetailBean.getObject().getCreater()) ? "" : "发布人：" + demandDetailBean.getObject().getCreater());
                textCreateTime.setText(TextUtils.isEmpty(demandDetailBean.getObject().getCreateTime()) ? "" : demandDetailBean.getObject().getCreateTime());
                mlists = demandDetailBean.getObject().getDetailList();
                //  ToastUtils.showShort(mlists.s);
                mDetailAdapter = new DemandDetailAdapter(DemandDetailActivity.this, mlists);
                mListView.setAdapter(mDetailAdapter);
                mDetailAdapter.setMyItemImagesClick(new DemandDetailAdapter.MyItemImagesClick() {
                    @Override
                    public void onItemImagesClick(DemandDetailBean.ObjectBean.DetailListBean model, int postion) {
                        List<DemandDetailBean.ObjectBean.DetailListBean.PicListBean> pic = model.getPicList();
                        imageUrls.clear();
                        if (pic.size() > 0) {
                            for (final DemandDetailBean.ObjectBean.DetailListBean.PicListBean img : pic) {
                                if (img.getType().equals("1")) {
                                    imageUrls.add(ApiService.BASE_URL + "downloadFile" + img.getAddress());
                                }
                            }
                        }
                        // ToastUtils.showShort(postion+"");
                        ShowBigImageActivity.startShowBigImageActivity(DemandDetailActivity.this, imageUrls, postion,
                                null);

                    }
                });
                break;
            case 10003:
                hideProgressDialog();
                //        ToastUtils.showShort(obj.toString());
                ToastUtils.showShort("请检查网络");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailParam = new HashMap<>();
        detailParam.put("id", getIntent().getStringExtra("ID"));
        if (getIntent().getStringExtra("state") != null && getIntent().getStringExtra("state").equals("0")) {
            addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.GONE);
        }
        showProgressDialog("加载中...");
        sendHttpGet(ApiService.QUERY_BYID_DEMAND, detailParam, 10007);
    }

    @Override
    public void initData() {
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }


    @OnClick(R.id.add_button)
    public void showDialog() {
        Intent reply = new Intent(DemandDetailActivity.this, ServiceReplyActivity.class);
        reply.putExtra("ID", getIntent().getStringExtra("ID"));
        startActivity(reply);
    }


}
