package com.gao.intelligent.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ImageViewVpAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.model.EquipmentBean;
import com.gao.intelligent.model.LeaderNameBean;
import com.gao.intelligent.model.LineBean;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.AppTools;
import com.gao.intelligent.utils.FileUtil;
import com.gao.intelligent.utils.GlideUtils;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.photo.PhotoPickerActivity;
import com.gao.intelligent.view.photo.PhotoPickerIntent;
import com.yixia.camera.MediaRecorderBase;
import com.zhaoshuang.weixinrecorded.MyVideoView;
import com.zhaoshuang.weixinrecorded.RecordedActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gaoyanbin on 2018/6/7.
 * 描述:新增问题页面
 */
public class AddSeviceActivity extends BaseActivity implements ImageViewVpAdapter.OnImageClick {

    @BindView(R.id.edt_part_content)
    EditText mEditPartContents;
    @BindView(R.id.tex_classify)
    TextView texClassify;//问题设备

    @BindView(R.id.ll_imgv)
    LinearLayout mLinearLayout;
    @BindView(R.id.txv_submit)
    TextView txvSubmit;//提交
    @BindView(R.id.iv_vedio)
    ImageView ivVedio;
    @BindView(R.id.vv_play)
    MyVideoView vv_play;
    @BindView(R.id.rl_show)
    RelativeLayout rl_show;
    @BindView(R.id.ll_classfiy)
    LinearLayout llClassFiy;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_all)
    TextView textAll;
    @BindView(R.id.ll_line)
    LinearLayout llLine;//选择产线
    @BindView(R.id.tex_line_name)
    TextView textLineName;//问题产线
    @BindView(R.id.llPersonName)
    LinearLayout llPersonName;
    @BindView(R.id.texName)
    TextView texName;
    @BindView(R.id.edtTypeNum)
            EditText edtTypeNum;
    @BindView(R.id.edtErrorCode)
            EditText edtErrorCode;//故障代码
    String[] classfiyArray;
    String[] lineArray;
    String videoPath, classfitID,lineId,lineName,personSignID;//视频文件路劲
    private final static int IMAGE_CHOSE_REQUEST = 1000;
    private final static int IMAGE_CUT_REQUEST = 1001;
    private PopupWindow imagePopupWindow;
    private ViewPager vp_photos;
    private TextView txv_page;
    private int vpNum = 0;
    private TextView mTittle;
    private ImageViewVpAdapter imageViewVpAdapter;
    private List<String> imgvUrlList = new ArrayList<>();
    private List<File> imgFiles = new ArrayList<>();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_VIDEO_TYPE_PNG = MediaType.parse("video/mp4");
    private List<EquipmentBean> mLists = new ArrayList<>();
    private List<LineBean> mListLines = new ArrayList<>();
    private List<LeaderNameBean.RowsBean> mListName = new ArrayList<>();
    String[] mameArrays;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_service;
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
                    case 10013:
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        LogUtils.i("问题设备", obj.toString());
                        mLists = JSON.parseArray(obj.toString(), EquipmentBean.class);
                        if (mLists.size() > 0) {
                            // textViewLineName.setText(mList.get(0).getName());
                            // lineID = mList.get(0).getId();
                            classfiyArray = new String[mLists.size()];
                            for (int i = 0; i < mLists.size(); i++) {
                                classfiyArray[i] = mLists.get(i).getName();
                            }
                        }
                        break;
                    case 10014:
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        LogUtils.i("产线列表", obj.toString());
                        mListLines = JSON.parseArray(obj.toString(), LineBean.class);
                        if (mListLines.size() > 0) {
                            // textViewLineName.setText(mList.get(0).getName());
                            // lineID = mList.get(0).getId();
                            lineArray = new String[mListLines.size()];
                            for (int i = 0; i < mListLines.size(); i++) {
                                lineArray[i] = mListLines.get(i).getName();
                            }
                        }
                        break;
                    case 10019:
                        LogUtils.d("审批人列表", obj.toString());

                        LeaderNameBean leaderNameBean = JSON.parseObject(obj.toString(), LeaderNameBean.class);
                        if (leaderNameBean.getRows()!=null) {
                            mListName = leaderNameBean.getRows();
                            if (mListName.size()>0){
                                mameArrays = new String[mListName.size()];
                                for (int i=0;i<mListName.size();i++){
                                    mameArrays[i] = mListName.get(i).getUserCname();
                                }
                            }
                        }
                        break;
                }
                break;
            case 10003:
                // hideCustomProgressDialog();
                ToastUtils.showShort("请检查网络");
                break;
        }
    }

    @Override
    public void initView() {
        if (AppConfig.getInstance().getInt("lineSize",0)>0&&AppConfig.getInstance().getInt("lineSize",0)==1){
            llLine.setVisibility(View.GONE);
            lineId = AppConfig.getInstance().getString("lineId", "");
            lineName = AppConfig.getInstance().getString("lineName", "");
//            ToastUtils.showShort(lineId+"\n"+lineName);
        }else {
            llLine.setVisibility(View.VISIBLE);
            HashMap<String, String> param1 = new HashMap<>();
            param1.put("fjCustomerId",AppConfig.getInstance().getString("customerId",""));
            sendHttpGet(ApiService.QUERY_LINE_DATAS, param1, 10014);
        }
        textAll.setText("0/300");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HashMap<String, String> param = new HashMap<>();
        param.put("code", "cpfl");
        sendHttpGet(ApiService.QUERY_QUS_CLASSFIY, param, 10013);

        HashMap<String, String> code = new HashMap<>();
//        code.put("code", AppConfig.getInstance().getString("authority", ""));
        sendHttpGet(ApiService.QUERY_LADER_NAMES,code ,10019);

        mTittle = findViewById(R.id.mTitle);
        imageViewVpAdapter = new ImageViewVpAdapter(this, new ArrayList<String>());
        imageViewVpAdapter.setOnImageClick(this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textAll.setText(s.length() + "/300");
                if (!TextUtils.isEmpty(mEditPartContents.getText())) {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_enable);
                } else {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_disable);
                }
            }
        };
        mEditPartContents.addTextChangedListener(textWatcher);
        showSelectImgv();
        texClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLists.size() > 0) {
                    ShowChoise("0",classfiyArray, texClassify, "请选择问题设备");
//                    showFilterPop(1, classfiyArray, texClassify, llClassFiy);//选择产线
                }else {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("code", "cpfl");
                    sendHttpGet(ApiService.QUERY_QUS_CLASSFIY, param, 10013);
                    ToastUtils.showShort("请检查网络");
                }
            }
        });
        llLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChoise("1",lineArray, textLineName, "请选择问题产线");

            }
        });

        llPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ShowChoise("2",mameArrays,texName,"请选择审批人员");
            }
        });
        ivVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSeviceActivity.this, RecordedActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick(R.id.txv_submit)
    public void submitDatas() {
        Map<String, String> params = new HashMap<>();
//        params.put("lineId", AppConfig.getInstance().getString("fjCustomerId", ""));//xian
//        params.put("fjProductionLineId", getIntent().getStringExtra("lineID"));//产线
        if (TextUtils.isEmpty(texClassify.getText().toString())) {
            ToastUtils.showShort("请选择问题分类");
            return;
        }
        if (TextUtils.isEmpty(mEditPartContents.getText().toString())) {
            ToastUtils.showShort("请输入问题详情");
            return;
        }
        if (TextUtils.isEmpty(personSignID)){
            ToastUtils.showShort("请选择审批人");
            return;
        }
        if (!TextUtils.isEmpty(edtTypeNum.getText().toString())){
            params.put("compoentNum",  edtTypeNum.getText().toString());//

            LogUtils.d("compoentNum", edtTypeNum.getText().toString());
        }
        if (!TextUtils.isEmpty(edtErrorCode.getText().toString())){
            params.put("errorCode",  edtErrorCode.getText().toString());//
        }
        params.put("processState",  "0");//当前位置
        params.put("lineName", lineName);//产线name
        params.put("questionDescribe", mEditPartContents.getText().toString());//问题描述
        params.put("equipType", texClassify.getText().toString());// 机器设备分类
        params.put("factoryId",AppConfig.getInstance().getString("sysOrgId","") );// 厂家id
        params.put("factoryName", AppConfig.getInstance().getString("sysOrgName",""));//厂家名称
        params.put("customerLeaderId",  personSignID);// 审批人ID
        LogUtils.e("提交数据3", AppConfig.getInstance().getString("fjCustomerId", ""));
        LogUtils.e("提交数据5", mEditPartContents.getText().toString());
        /**
         * 提交数据
         */
        uploadDatas(ApiService.SAVE_GUARANTEE, params, "", imgFiles);

    }

    /**
     * 提交数据
     * @param reqUrl
     * @param params
     * @param pic_key
     * @param files
     */
    private void uploadDatas(String reqUrl, Map<String, String> params, String pic_key, List<File> files) {
        showProgressDialog("正在提交...");
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (files != null) {
            for (File file : files) {
                multipartBodyBuilder.addFormDataPart("files", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        if (videoPath != null) {
            multipartBodyBuilder.addFormDataPart("files", new File(videoPath).getName(), RequestBody.create(MEDIA_VIDEO_TYPE_PNG, new File(videoPath)
            ));

        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl);// 添加URL地址
        RequestBuilder.post(requestBody);
        RequestBuilder.addHeader("token", AppConfig.getInstance().getString("token", ""));
        Request request = RequestBuilder.build();
        OkHttpClient mOkHttpClient = new OkHttpClient();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("上传", call.request().body().toString());
                hideCustomProgressDialog();
                ToastUtils.showShort("提交失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtils.i("上传", response);
                hideCustomProgressDialog();
                if (response.code() == 200) {
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            try {
                                String responseData=response.body().string();
                                LogUtils.i("fengqianz",responseData);
                                ResultBean resultBean = JSON.parseObject(responseData, ResultBean.class);
                                if (resultBean.getResultCode().equals("1")){
                                    ToastUtils.showShort("提交成功");
                                    MyApp.getInstance().setType(true);

                                    finish();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }

                    }.start();
                    finish();
                }
            }
        });
    }

    private void ShowChoise(final String flag, final String[] items, final TextView textView, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseContext, R.style.CustomAlertDialogBackground);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(title);
        //    指定下拉列表的显示数据
        //  final String[] majors = {"电气", "动力", "给排水", "电气低压"};
        //    设置一个下拉的列表选择项
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(items[which]);
//                lineId = mListLines.get(which).getId();
                if (flag.equals("1")){
                    lineId = mListLines.get(which).getId();
                    lineName = mListLines.get(which).getName();
                }
                if (flag.equals("2")){
                    personSignID = mListName.get(which).getId();
                }
            }
        });
        builder.show();
    }

    /**
     * 显示图片
     */
    private void showSelectImgv() {
        mLinearLayout.removeAllViews();
        for (final String url : imgvUrlList) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_imgv_faceback, null);
            ImageView imgv_face = (ImageView) view.findViewById(R.id.imgv_face);
            View rl_pic = view.findViewById(R.id.rl_pic);

            ImageView imgv_del = (ImageView) view.findViewById(R.id.imgv_del);
            imgv_del.setVisibility(View.VISIBLE);
            imgv_del.setTag(url);
            rl_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagePopupWindow == null) {
                        initImagePop();
                    }
                    vpNum = imgvUrlList.size();
                    imageViewVpAdapter.setData(imgvUrlList);
                    //   vp_photos.setCurrentItem(clickPositon);
//                    txv_page.setText((1) + "/" + vpNum);
                    imagePopupWindow.showAtLocation(mTittle, Gravity.BOTTOM, 0, 0);

                }
            });

            imgv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = (String) v.getTag();
                    //   imgvNetUrlList.remove(imgvUrlList.indexOf(url));
                    imgvUrlList.remove(url);
                    imgFiles.remove(new File(url));
                    /// ToastUtils.showShort(imgFiles.size()+"");
                    showSelectImgv();
                }
            });

            GlideUtils.load(url, imgv_face);
            mLinearLayout.addView(view);
        }

        if (imgvUrlList.size() < 4) {
            View defaultView = LayoutInflater.from(this).inflate(R.layout.layout_imgv_faceback, null);
            ImageView imgv_default = (ImageView) defaultView.findViewById(R.id.imgv_face);
            imgv_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(AddSeviceActivity.this);
                    intent.setPhotoCount(1);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, IMAGE_CHOSE_REQUEST);
                }
            });
            mLinearLayout.addView(defaultView);
        }
    }

    /**
     * 点击查看大图片
     */
    private void initImagePop() {
        try {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_showpic_pop, null);
            vp_photos = (ViewPager) view.findViewById(R.id.vp_photos);
            vp_photos.setAdapter(imageViewVpAdapter);
            txv_page = (TextView) view.findViewById(R.id.txv_page);
            vp_photos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    txv_page.setText((vp_photos.getCurrentItem() + 1) + "/" + vpNum);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            imagePopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imagePopupWindow.setAnimationStyle(R.style.Animation_AppCompat_Dialog);
            //获取popwindow焦点
            imagePopupWindow.setFocusable(true);
            imagePopupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
            //设置popwindow如果点击外面区域，便关闭。

            imagePopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        imagePopupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        } catch (Throwable e) {
            LogUtils.e("", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CHOSE_REQUEST:
                if (data != null) {
                    List<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    if (photos != null && photos.size() > 0) {
                        // imgvUrlList.addAll(photos );
                        // showSelectImgv();
                        Uri uri = Uri.fromFile(new File(photos.get(0)));
                        AppTools.startPhotoZoom(this, uri, IMAGE_CUT_REQUEST);
                    }
                }
                break;
            case IMAGE_CUT_REQUEST:
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    options.inJustDecodeBounds = false;
                    final Bitmap photo = BitmapFactory.decodeFile(Global.uritempFile.getPath(), options);

                    final String imgName = String.valueOf(System.currentTimeMillis());
                    FileUtil.saveSDcardImage(photo, Comm.SDCARD_IMG_ROOT, imgName);
                    String filepath = Comm.SDCARD_IMG_ROOT + "/" + imgName + ".png";
                    imgvUrlList.add(filepath);
                    imgFiles.add(new File(filepath));
                    showSelectImgv();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (resultCode == RESULT_OK) {
            videoPath = data.getStringExtra("videoPath");
            if (!TextUtils.isEmpty(videoPath)) {
                //  ivVedio.setVisibility(View.GONE);
                vv_play.setVisibility(View.VISIBLE);
                vv_play.setVideoPath(videoPath);
                vv_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        vv_play.setLooping(true);
                        vv_play.start();
                        float widthF = vv_play.getVideoWidth() * 1f / MediaRecorderBase.VIDEO_HEIGHT;
                        float heightF = vv_play.getVideoHeight() * 1f / MediaRecorderBase.VIDEO_WIDTH;
                        ViewGroup.LayoutParams layoutParams = vv_play.getLayoutParams();
                        layoutParams.width = (int) (rl_show.getWidth() * widthF);
                        layoutParams.height = (int) (rl_show.getHeight() * heightF);
                        vv_play.setLayoutParams(layoutParams);
                    }
                });
            }
        }
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mEditPartContents};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.edt_part_content};
        return ids;
    }

    @Override
    public void click() {
        if (imagePopupWindow != null && imagePopupWindow.isShowing()) {
            imagePopupWindow.dismiss();
        }
    }

    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
