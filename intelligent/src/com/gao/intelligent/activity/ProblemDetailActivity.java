package com.gao.intelligent.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ImageItemAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ProblemDetailBean;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.MyGridView;
import com.gao.intelligent.view.TimelineLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Yanbin on 2018/11/8.
 * 描述:问题详情页面
 */
public class ProblemDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_line_name)
    TextView textLineName;//线名
    @BindView(R.id.text_create_name)
    TextView creatName;//创建人
    @BindView(R.id.tv_phone)
    TextView tvPhone;//联系电话
    @BindView(R.id.tex_pro_contact)
    TextView problemContact;//问题描述

    @BindView(R.id.my_grid)
    MyGridView my;
    @BindView(R.id.jc_video)
    JCVideoPlayerStandard mMyVideoView;
    ImageItemAdapter mImaeItemAdapter;

    @BindView(R.id.rl_show)
    RelativeLayout rl_show;

    @BindView(R.id.llReply)
    View llReply;//

    @BindView(R.id.jc_video1)
    JCVideoPlayerStandard mMyVideoView1;

    @BindView(R.id.rl_show1)
    RelativeLayout rl_show1;
    @BindView(R.id.tex_reply)
    TextView textReply;//回复

    @BindView(R.id.llSHName)
    View llSHName;//售后
    @BindView(R.id.textSHname)
    TextView textSHname;
    @BindView(R.id.llJSName)
    View llJSName;
    @BindView(R.id.textJSname)
    TextView textJSname;

    @BindView(R.id.timeline_layout)
    TimelineLayout mTimelineLayout;
    ProblemDetailBean mProblemDetailBean;
    HashMap<String, String> param = new HashMap<>();
    List<ProblemDetailBean.RowsBean> mList = new ArrayList<>();
    private List<String> pics = new ArrayList<>();
    @BindView(R.id.txv_preview)
    TextView txv_preview;
    @BindView(R.id.text_nums_name)
    TextView text_nums_name;
    @BindView(R.id.btnSignName)
    Button btnSignName;
    @BindView(R.id.btnHui)
    Button btnHui;

    private Button btndelete,btnSave;
    ImageView mSignName;
    @BindView(R.id.text_error_code)
    TextView text_error_code;
    @BindView(R.id.ll_error)
    View llError;
    @BindView(R.id.ll_num_size)
    View ll_num_size;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_problem_detail;
    }

    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        if (AppConfig.getInstance().getString("authority","").equals("[LEADER]")){
            btnSignName.setVisibility(View.VISIBLE);
        }else {
            btnSignName.setVisibility(View.GONE);
        }
        if (getIntent().getStringExtra("flag").equals("1")){
            btnSignName.setVisibility(View.GONE);
        }
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    exitApp();
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                switch (type){

                    case 10021:
                        hideCustomProgressDialog();
                        LogUtils.d("问题详情", obj);
                        mProblemDetailBean = JSON.parseObject(obj.toString(), ProblemDetailBean.class);
                        if (mProblemDetailBean.getObject() != null) {
                            textLineName.setText( mProblemDetailBean.getObject().getLineName()+
                                    "  --  "+mProblemDetailBean.getObject().getEquipType());
//                    textProPostion.setText(mProblemDetailBean.getObject().getEquipType());
                            creatName.setText(mProblemDetailBean.getObject().getIntroducerName() + ":");
                            tvPhone.setText(mProblemDetailBean.getObject().getIntroducerTel());
                            problemContact.setText(mProblemDetailBean.getObject().getQuestionDescribe());
                            if (mProblemDetailBean.getObject().getAnswer()!=null){
                                llReply.setVisibility(View.VISIBLE);
                                textReply.setText(mProblemDetailBean.getObject().getAnswer());
                            }
                            if (mProblemDetailBean.getObject().getSalePerson()!=null){
                                llSHName.setVisibility(View.VISIBLE);
                                textSHname.setText(mProblemDetailBean.getObject().getSalePerson());
                            }
                            if (mProblemDetailBean.getObject().getCompoentNum()!=null){
                                ll_num_size.setVisibility(View.VISIBLE);
                                text_nums_name.setText(mProblemDetailBean.getObject().getCompoentNum());
                            }
                            if (mProblemDetailBean.getObject().getProductPerson()!=null){
                                llJSName.setVisibility(View.VISIBLE);
                                textJSname.setText(mProblemDetailBean.getObject().getProductPerson());
                            }
                            if (mProblemDetailBean.getObject().getErrorCode()!=null){
                                llError.setVisibility(View.VISIBLE);
                                text_error_code.setText(mProblemDetailBean.getObject().getErrorCode());
                            }
                                if (mProblemDetailBean.getObject().getBackContactFlag()!=null){
                                btnHui.setVisibility(View.VISIBLE);
                                }else {
                                btnHui.setVisibility(View.GONE);
                                }
                            if (mProblemDetailBean.getObject().getAppQuestionPic() != null) {
                                List<ProblemDetailBean.ObjectBean.AppQuestionPicBean> lists = mProblemDetailBean.getObject().getAppQuestionPic();
                                pics.clear();
                                for (int i = 0;i<lists.size();i++){
                                    if (lists.get(i).getType().equals("1")){
                                        pics.add(ApiService.BASE_URL + "downloadFile" + lists.get(i).getAddress());
                                    }
                                    if (lists.get(i).getType().equals("2")&&lists.get(i).getPersonType(). equals("1")){
                                        rl_show.setVisibility(View.VISIBLE);
                                        showVideo(ApiService.BASE_URL + "downloadFile" + lists.get(i).getAddress());
                                    }
                                    if (lists.get(i).getType().equals("2")&&lists.get(i).getPersonType().equals("2")){
                                        rl_show1.setVisibility(View.VISIBLE);
                                        showVideo1(ApiService.BASE_URL + "downloadFile" + lists.get(i).getAddress());
                                    }
                                }

                                if (pics.size() > 0) {
                                    mImaeItemAdapter = new ImageItemAdapter(baseContext, pics);
                                    my.setAdapter(mImaeItemAdapter);
                                }
                            }
                        }
                        if (mProblemDetailBean.getRows()!=null){
                            mList.clear();
                            mTimelineLayout.removeAllViews();
                            mList = mProblemDetailBean.getRows();

                            if (mList.size()>1){
                                btnSignName.setVisibility(View.GONE);
                                txv_preview.setVisibility(View.GONE);
                            }else {
                                txv_preview.setVisibility(View.GONE);
                            }
                            for (int i= 0;i<mList.size();i++){
                                View view = LayoutInflater.from(this).inflate(R.layout.item_timeline, mTimelineLayout, false);
                                ((TextView) view.findViewById(R.id.tv_action)).setText(mList.get(i).getRemark());
                                ((TextView) view.findViewById(R.id.tv_action_time)).setText(mList.get(i).getLeaderTime());
                                mTimelineLayout.addView(view);
                            }
                        }
                        break;
                    case 66666:

                        ResultBean resultBean = JSON.parseObject(obj.toString(), ResultBean.class);
                        if (resultBean.getResultCode().equals("1")){
                            ToastUtils.showShort("提交成功");
                            MyApp.getInstance().setType(true);
                            param.put("userId", AppConfig.getInstance().getString("userId", ""));
                            param.put("id", getIntent().getStringExtra("id"));
                            showProgressDialog("加载中...");
                            sendHttpGet(ApiService.QUERY_PROBLEM_DETAIL, param, 10021);
                        }else {
                            ToastUtils.showShort(resultBean.getResultDesc()+"");
                        }

                        break;
                }

                break;
            case 10003:
                hideCustomProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    private void showVideo(String s) {

        mMyVideoView.setUp(s, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "问题视频");

        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());

    }
    private void showVideo1(String s) {
        mMyVideoView1.setUp(s, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "解决视频");

        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
    }
    @OnClick({R.id.re_call_phone,R.id.btnSignName,R.id.txv_preview,R.id.btnHui})
    public  void onClick(View view){
        switch (view.getId()){

            case R.id.btnHui:
                if (android.os.Build.VERSION.SDK_INT<21){
                    ToastUtils.showShort("手机版本太低，不支持预览功能。");
                }else {
                    Intent l = new Intent(baseContext, PDFShowActivity.class);
                    l.putExtra("questionID", mProblemDetailBean.getObject().getId());
                    l.putExtra("version", mProblemDetailBean.getObject().getProcessState() + "");
                    l.putExtra("flag", "hui");
                    if (mProblemDetailBean.getObject().getBackContactFlag() != null) {
                        l.putExtra("back", mProblemDetailBean.getObject().getBackContactFlag());
                    }
                    startActivity(l);
                }
                break;
            case R.id.re_call_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  mProblemDetailBean.getObject().getIntroducerTel()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.btnSignName:
                if (AppConfig.getInstance().getString("signImg","")!=null
                        &&AppConfig.getInstance().getString("signImg","")!="") {
                    showDialog();
                }else {
                    Intent detailI = new Intent(baseContext, SignActivity.class);
                    detailI.putExtra("questionID", mProblemDetailBean.getObject().getId());
                    detailI.putExtra("version", mProblemDetailBean.getObject().getVersion());
                    detailI.putExtra("flag", "lian");
                    startActivity(detailI);
                }

                break;
            case R.id.txv_preview:
                LogUtils.d("heheh1",mProblemDetailBean.getObject().getVersion()+"1");

                break;
        }

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
                HashMap<String, String> params = new HashMap<String, String>();
                LogUtils.d("shuju",mProblemDetailBean.getObject().getVersion());
                LogUtils.d("shuju",AppConfig.getInstance().getString("signImg",""));
                params.put("version", mProblemDetailBean.getObject().getVersion());
                params.put("id",   mProblemDetailBean.getObject().getId());//
                params.put("address", AppConfig.getInstance().getString("signImg",""));//
                sendHttpPost(ApiService.SAVE_SIGN_PNG,params,66666);
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailI = new Intent(baseContext, SignActivity.class);
                detailI.putExtra("questionID", mProblemDetailBean.getObject().getId());
                detailI.putExtra("version", mProblemDetailBean.getObject().getVersion());
                detailI.putExtra("flag", "lian");
                startActivity(detailI);
                alertDialog.dismiss();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        param.put("userId", AppConfig.getInstance().getString("userId", ""));
        param.put("id", getIntent().getStringExtra("id"));
        showProgressDialog("加载中...");
        sendHttpGet(ApiService.QUERY_PROBLEM_DETAIL, param, 10021);

    }

    @Override
    public void initData() {
        my.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowBigImageActivity.startShowBigImageActivity( baseContext, pics, position,
                        null);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }
}
