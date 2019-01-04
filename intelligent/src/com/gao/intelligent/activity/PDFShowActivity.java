package com.gao.intelligent.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

/**
 * Created by Yanbin on 2018/12/10.
 * 描述:联系函展示页面
 */
public class PDFShowActivity extends BaseActivity implements DownloadFile.Listener{
    @BindView(R.id.remote_pdf_root)
    RelativeLayout remotePDF;
    RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.no_internet)
    View noData;

    @BindView(R.id.mTitle)
    TextView mTextView;
    @BindView(R.id.txvSignName)
    TextView txvSignName;
    private Button btndelete,btnSave;
    ImageView mSignName;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pdf_show;
    }

    @Override
    public void initView() {
        mTextView.setText("回函表");
//        showProgressDialog("加载中...");
//        setDownloadListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回



        if (getIntent().getStringExtra("back")!=null&&
                getIntent().getStringExtra("back").equals("3")&&
                AppConfig.getInstance().getString("authority","").equals("[LEADER]")){
                txvSignName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressDialog("加载中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("questionID"));
        sendHttpPost(ApiService.PREVIE_CONTACT_FORM, map, 10098);
        if (MyApp.getInstance().isType()){
            txvSignName.setVisibility(View.GONE);
        }
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {

            case 10004:


                hideCustomProgressDialog();
                switch (type) {

                    case 10098:
                        LogUtils.i("联系函", obj);

                        ResultBean resultBean = JSON.parseObject(obj.toString(), ResultBean.class);
                        if (resultBean.getResultCode().equals("1")){
                            String mUrl = ApiService.BASE_URL  +  resultBean.getResultData();
                            showProgressDialog("加载中...");
                            setDownloadListener(mUrl);
                        }
                        break;
                    case 66667:
                        ResultBean result  = JSON.parseObject(obj.toString(), ResultBean.class);
                        if (result .getResultCode().equals("1")){
                            ToastUtils.showShort("提交成功");
                            showProgressDialog("加载中...");
                            txvSignName.setVisibility(View.GONE);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", getIntent().getStringExtra("questionID"));
                            sendHttpPost(ApiService.PREVIE_CONTACT_FORM, map, 10098);

                        }else {
                            ToastUtils.showShort(result .getResultDesc()+"");
                        }

                        break;
                }
                break;
            case 10003:
                hideCustomProgressDialog();
                // ToastUtils.showShort(obj.toString());
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    private void setDownloadListener(String url) {
        final DownloadFile.Listener listener = this;
//        String mUrl = ApiService.BASE_URL + "downloadFile" + getIntent().getStringExtra("url");
        remotePDFViewPager = new RemotePDFViewPager(this, url, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }
    @OnClick({R.id.txvSignName})
    public void onClick(){

        if (AppConfig.getInstance().getString("signImg","")!=null
                &&AppConfig.getInstance().getString("signImg","")!="") {
            showDialog();
        }else {
        Intent l = new Intent(baseContext, SignActivity.class);
        l.putExtra("questionID",  getIntent().getStringExtra("questionID"));
        l.putExtra("version",getIntent().getStringExtra("version"));
        l.putExtra("flag", "hui");
        LogUtils.d("heheh1",getIntent().getStringExtra("version")+"2");
        startActivity(l);}

    }
    public void showDialog( ) {
        final Dialog alertDialog = new Dialog( baseContext);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setContentView(R.layout.verl_code_dialog);
        mSignName = window.findViewById(R.id.mSignName);
        btndelete = (Button) window.findViewById(R.id.btn_delete);
        btnSave = (Button) window.findViewById(R.id.btn_submit);
        btnSave.setEnabled(true);
        if (AppConfig.getInstance().getString("signImg","")!=null
                &&AppConfig.getInstance().getString("signImg","")!=""){

            Glide.with(baseContext)
                    .load(ApiService.BASE_URL+"downloadFile"+AppConfig.getInstance().getString("signImg",""))
                    .placeholder(R.mipmap.icon_loading)
                    .error(R.mipmap.icon_load_fail)
                    .into(mSignName);

        }
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> p = new HashMap< >();
                p.put("version", getIntent().getStringExtra("version"));
                p.put("id",  getIntent().getStringExtra("questionID"));//
                p.put("address", AppConfig.getInstance().getString("signImg",""));//
                sendHttpPost(ApiService.SING_HUI_NAME,  p,66667);
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(baseContext, SignActivity.class);
                l.putExtra("questionID",  getIntent().getStringExtra("questionID"));
                l.putExtra("version",getIntent().getStringExtra("version"));
                l.putExtra("flag", "hui");
                LogUtils.d("heheh1",getIntent().getStringExtra("version")+"2");
                startActivity(l);
                alertDialog.dismiss();
            }
        });
    }
    @Override
    public void onSuccess(String url, String destinationPath) {
        hideCustomProgressDialog();
        remotePDF.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
//        ToastUtils.showShort(adapter.getCount()+"页");
//        textPageSizes.setText("共"+adapter.getCount()+"页");
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
