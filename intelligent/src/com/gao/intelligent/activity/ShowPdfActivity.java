package com.gao.intelligent.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.utils.ToastUtils;

import butterknife.BindView;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

/**
 * Created by Yanbin on 2018/7/16.
 * 描述: 设备档案  pdf文档查看
 */
public class ShowPdfActivity extends BaseActivity implements DownloadFile.Listener {
    @BindView(R.id.remote_pdf_root)
    RelativeLayout remotePDF;
    RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.no_internet)
    View noData;
    @BindView(R.id.tv_page_size)
    TextView textPageSizes;
    @BindView(R.id.mTitle)
    TextView mTextView;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pdf_show;
    }

    @Override
    public void initView() {
        mTextView.setText(getIntent().getStringExtra("name"));
        showProgressDialog("加载中...");
        setDownloadListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回
    }

    private void setDownloadListener() {
        final DownloadFile.Listener listener = this;
        String mUrl = ApiService.BASE_URL + "downloadFile" + getIntent().getStringExtra("url");
        remotePDFViewPager = new RemotePDFViewPager(this, mUrl, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        hideCustomProgressDialog();
        remotePDF.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        ToastUtils.showShort("共"+adapter.getCount()+"页");
        textPageSizes.setText("共"+adapter.getCount()+"页");
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    /*更新视图*/
    private void updateLayout() {
        remotePDF.removeAllViewsInLayout();
        remotePDF.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onFailure(Exception e) {
        hideCustomProgressDialog();
        ToastUtils.showShort("数据加载错误");
        noData.setVisibility(View.VISIBLE);
        remotePDF.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}
