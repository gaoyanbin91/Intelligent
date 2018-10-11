/*
package com.gao.intelligent.activity.sound;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.adapter.ImageViewVpAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.model.QueClassfiyBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.AppTools;
import com.gao.intelligent.utils.FileUtil;
import com.gao.intelligent.utils.GlideUtils;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.UIUtils;
import com.gao.intelligent.view.photo.PhotoPickerActivity;
import com.gao.intelligent.view.photo.PhotoPickerIntent;
import com.gao.intelligent.view.popupwindow.top.PopUpWindow;
import com.yixia.camera.MediaRecorderBase;
import com.zhaoshuang.weixinrecorded.RecordedActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

*/
/**
 * 识别的基类Activity。 ActivityCommon定义了通用的UI部分
 * 封装了识别的大部分逻辑，包括MyRecognizer的初始化，资源释放
 * <p>
 * <p>
 * 集成流程代码，只需要一句： myRecognizer.start(params);具体示例代码参见startRough()
 * =》.实例化 myRecognizer   new MyRecognizer(this, listener);
 * =》 实例化 listener  new MessageStatusRecogListener(null);
 * </p>
 * 集成文档： http://ai.baidu.com/docs#/ASR-Android-SDK/top 集成指南一节
 * demo目录下doc_integration_DOCUMENT
 * ASR-INTEGRATION-helloworld  ASR集成指南-集成到helloworld中 对应 ActivityMiniRecog
 * ASR-INTEGRATION-TTS-DEMO ASR集成指南-集成到合成DEMO中 对应 ActivityRecog
 * <p>
 * 大致流程为
 * 1. 实例化 MyRecognizer ,调用 release 方法前不可以实例化第二个。参数中需要开发者自行填写语音识别事件的回调类，实现开发者自身的业务逻辑
 * 2. 如果使用离线命令词功能，需要调用 loadOfflineEngine。在线功能不需要。
 * 3. 根据识别的参数文档，或者demo中测试出的参数，组成 json 格式的字符串。调用 start 方法
 * 4. 在合适的时候，调用release释放资源。
 * <p>
 *//*


public abstract class ActivityRecog extends ActivityCommon implements   ImageViewVpAdapter.OnImageClick {

    private final static int IMAGE_CHOSE_REQUEST = 1000;
    private final static int IMAGE_CUT_REQUEST = 1001;
    */
/**
     * `
     * 识别控制器，使用MyRecognizer控制识别的流程
     *//*

   // protected MyRecognizer myRecognizer;
    private TextView mTittle;
    */
/*
     * Api的参数类，仅仅用于生成调用START的json字符串，本身与SDK的调用无关
     *//*

  //  protected CommonRecogParams apiParams;

    */
/*
     * 本Activity中是否需要调用离线命令词功能。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     *//*

    protected boolean enableOffline = false;
    //private AudioRecoderDialog recoderDialog;
    //private AudioRecoderUtils recoderUtils;
    private long downT;
    //private List<QueClassfiyBean> mLists = new ArrayList<>();
    String[] classfiyArray;
    String videoPath, classfitID, levelID;//视频文件路劲
    private RadioButton selectButton;//选中的优先级
    private String levelText = "";//优先级

    private PopupWindow imagePopupWindow;
    private ViewPager vp_photos;
    private TextView txv_page;
    private int vpNum = 0;
    private ImageViewVpAdapter imageViewVpAdapter;
    private List<String> imgvUrlList = new ArrayList<>();
    private List<File> imgFiles = new ArrayList<>();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_VIDEO_TYPE_PNG = MediaType.parse("video/mp4");
    */
/**
     * 控制UI按钮的状态
     *//*

    protected int status;

    */
/**
     * 日志使用
     *//*

    private static final String TAG = "ActivityRecog";

    */
/**
     * 在onCreate中调用。初始化识别控制类MyRecognizer
     *//*

//    protected void initRecog() {
//        StatusRecogListener listener = new MessageStatusRecogListener(handler);
//        myRecognizer = new MyRecognizer(this, listener);
//        apiParams = getApiParams();
//        status = STATUS_NONE;
//        if (enableOffline) {
//            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
//        }
//    }

    */
/**
     * 开始录音，点击“开始”按钮后调用。
     *//*

//    protected void start() {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ActivityRecog.this);
//        //  上面的获取是为了生成下面的Map， 自己集成时可以忽略
//        // / 集成时不需要上面的代码，只需要params参数。
//        final Map<String, Object> params = apiParams.fetch(sp);
//        // 复制此段可以自动检测错误
//        (new AutoCheck(getApplicationContext(), new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 100) {
//                    AutoCheck autoCheck = (AutoCheck) msg.obj;
//                    synchronized (autoCheck) {
//                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
//                        // String s = autoCheck.obtainErrorMessage();
//                        // String s1 = autoCheck.obtainAllMessage();
//                        //  txtLog.append(message + "\n");
//                        // 可以用下面一行替代，在logcat中查看代码
//                        Log.w("AutoCheckMessage", message);
//                    }
//                }
//            }
//        }, enableOffline)).checkAsr(params);
//
//        // 这里打印出params， 填写至您自己的app中，直接调用下面这行代码即可。
//        myRecognizer.start(params);
//    }


    // 以上为 语音SDK调用，以下为UI部分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, String> param = new HashMap<>();
        param.put("code", "wtfl");
        sendHttpGet(ApiService.QUERY_QUS_CLASSFIY, param, 10003);
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                LogUtils.d("LineDatas", obj);
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                LogUtils.i("分类数据", obj.toString());
                mLists = JSON.parseArray(obj.toString(), QueClassfiyBean.class);
                if (mLists.size() > 0) {
                    // textViewLineName.setText(mList.get(0).getName());
                    // lineID = mList.get(0).getId();
                    classfiyArray = new String[mLists.size()];
                    for (int i = 0; i < mLists.size(); i++) {
                        classfiyArray[i] = mLists.get(i).getName();
                    }
                }

                break;
            case 10003:
                // hideCustomProgressDialog();
                ToastUtils.showShort("请检查网络");
                break;
        }
    }


    private void showFilterPop(final int flag, final String[] arr, final TextView lineName, final View view) {
        PopUpWindow popupWindow = new PopUpWindow(ActivityRecog.this, arr, null, view);
        popupWindow.setShowLocationYourself(true);
        popupWindow.showPopUpWindow(new PopUpWindow.OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
                texClassify.setText(arr[position]);
                classfitID = mLists.get(position).getCode();
               // ToastUtils.showShort(classfitID);
            }

            @Override
            public void onshowLocation(PopupWindow popupWindow) {
                popupWindow.showAsDropDown(view, 0, 0);
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        rgLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectButton = (RadioButton) findViewById(rgLevel.getCheckedRadioButtonId());
                levelText = selectButton.getText().toString();
                if (levelText.equals("重要")) {
                    levelID = "0";
                } else if (levelText.equals("一般")) {
                    levelID = "1";

                } else if (levelText.equals("标准")) {
                    levelID = "2";

                }
                if (!TextUtils.isEmpty(levelText) && !TextUtils.isEmpty(edtProblemName.getText()) && !TextUtils.isEmpty(mEditPartContents.getText())
                        && !TextUtils.isEmpty(texClassify.getText()) && !TextUtils.isEmpty(edtPhoneNum.getText().toString())) {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_enable);
                } else {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_disable);
                }
            }
        });
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
                if (!TextUtils.isEmpty(levelText) && !TextUtils.isEmpty(edtProblemName.getText()) && !TextUtils.isEmpty(mEditPartContents.getText())
                        && !TextUtils.isEmpty(texClassify.getText()) && !TextUtils.isEmpty(edtPhoneNum.getText().toString())) {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_enable);
                } else {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_disable);
                }
            }
        };
        edtPhoneNum.addTextChangedListener(textWatcher);
        edtProblemName.addTextChangedListener(textWatcher);
        mEditPartContents.addTextChangedListener(textWatcher);
        texClassify.addTextChangedListener(textWatcher);
        showSelectImgv();
        texClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLists.size() > 0) {
                    showFilterPop(1, classfiyArray, texClassify, llClassFiy);//选择产线
                }
            }
        });
        ivVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRecog.this, RecordedActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        recoderDialog = new AudioRecoderDialog(this);
        recoderDialog.setShowAlpha(0.98f);

        recoderUtils = new AudioRecoderUtils(new File(Environment.getExternalStorageDirectory() + "/recoder.amr"));
        recoderUtils.setOnAudioStatusUpdateListener(this);
//
//        btn_sound.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        btn_sound.setBackgroundResource(R.mipmap.icon_btn_sound_noselect);
//
//                        btn_sound.setEnabled(false);
//                        status = STATUS_NONE;
//                        recoderUtils.startRecord();
//                        downT = System.currentTimeMillis();
//                        recoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
//                        switch (status) {
//                            case STATUS_NONE: // 初始状态
//                                start();
//                                // startRough();
//                                status = STATUS_WAITING_READY;
//                                updateBtnTextByStatus();
//                                mEditPartContents.setText("");
//                                break;
//                            case STATUS_WAITING_READY: // 调用本类的start方法后，即输入START事件后，等待引擎准备完毕。
//                            case STATUS_READY: // 引擎准备完毕。
//                            case STATUS_SPEAKING:
//                            case STATUS_FINISHED: // 长语音情况
//                            case STATUS_RECOGNITION:
//                                stop();
//
//                                // recoderUtils.stopRecord();
//                                status = STATUS_STOPPED; // 引擎识别中
//                                updateBtnTextByStatus();
//                                break;
//                            case STATUS_STOPPED: // 引擎识别中
//
//                                cancel();
//                                status = STATUS_NONE; // 识别结束，回到初始状态
//                                updateBtnTextByStatus();
//                                break;
//                            default:
//                                break;
//                        }
//
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                          btn_sound.setBackgroundResource(R.mipmap.icon_btn_sound);
//                        btn_sound.setEnabled(true);
//                        status = STATUS_RECOGNITION;
//                        switch (status) {
//                            case STATUS_RECOGNITION:
//                                stop();
//                                // recoderUtils.stopRecord();
//                                status = STATUS_STOPPED; // 引擎识别中
//                                recoderDialog.dismiss();
//
//                                updateBtnTextByStatus();
//                                break;
//                            case STATUS_STOPPED: // 引擎识别中
//                                cancel();
//                                if (recoderDialog != null) {
//                                    recoderDialog.dismiss();
//                                }
//                                status = STATUS_NONE; // 识别结束，回到初始状态
//                                updateBtnTextByStatus();
//
//                                break;
//                            default:
//                                break;
//                        }
//
//                        return true;
//                }
//                return false;
//            }
//        });
//        }

//    protected void handleMsg(Message msg) {
//        super.handleMsg(msg);
//
//        switch (msg.what) { // 处理MessageStatusRecogListener中的状态回调
//            case STATUS_FINISHED:
//                if (msg.arg2 == 1) {
//                    LogUtils.e("结果", msg.obj.toString());
//                    if (msg.obj.toString().startsWith("识别结束")) {
//                        String[] tempArr = msg.obj.toString().split("”");
//                        String value = tempArr[1].trim();
//                        mEditPartContents.setText(value);
//
//                    } else if (msg.obj.toString().startsWith("识别错误")) {
//                        ToastUtils.showShort("请长按录音");
//                    }
//                }
//                status = msg.what;
//                updateBtnTextByStatus();
//                break;
//            case STATUS_NONE:
//            case STATUS_READY:
//            case STATUS_SPEAKING:
//            case STATUS_RECOGNITION:
//                status = msg.what;
//                updateBtnTextByStatus();
//                break;
//            default:
//                break;
//
//        }
//    }

//    private void updateBtnTextByStatus() {
//        switch (status) {
//            case STATUS_NONE:
//
//              //  btn_sound.setText("开始录音");
//                btn_sound.setEnabled(true);
//                break;
//            case STATUS_WAITING_READY:
//            case STATUS_READY:
//            case STATUS_SPEAKING:
//            case STATUS_RECOGNITION:
//                btn_sound.setBackgroundResource(R.mipmap.icon_btn_sound);
//               // btn_sound.setText("停止录音");
//                btn_sound.setEnabled(true);
//
//                break;
//
//            case STATUS_STOPPED:
//                btn_sound.setBackgroundResource(R.mipmap.icon_btn_sound);
//              //  btn_sound.setText("识别中...");
//                btn_sound.setEnabled(true);
//
//                break;
//            default:
//                break;
//        }
//    }

//    @Override
//    public void onUpdate(double db) {
//        if (null != recoderDialog) {
//            int level = (int) db;
//            recoderDialog.setLevel((int) db);
//            recoderDialog.setTime(System.currentTimeMillis() - downT);
//        }
//    }
    }
    @OnClick(R.id.txv_submit)
    public void submitDatas() {

        Map<String, String> params = new HashMap<>();
        if (TextUtils.isEmpty(levelID)) {
            ToastUtils.showShort("请选择优先级");
            return;
        }
        params.put("priority", levelID);//优先级

        if (TextUtils.isEmpty(edtProblemName.getText().toString())) {
            ToastUtils.showShort("请输入问题名称");
            return;
        }
        if (TextUtils.isEmpty(edtPhoneNum.getText().toString())){
            ToastUtils.showShort("请输入手机号码");
            return;
        }
        params.put("name", edtProblemName.getText().toString());//问题名称
        params.put("fjCustomerId", AppConfig.getInstance().getString("fjCustomerId", ""));//xian

        params.put("fjProductionLineId", getIntent().getStringExtra("lineID"));//产线
        if (TextUtils.isEmpty(classfitID)) {
            ToastUtils.showShort("请选择问题分类");
            return;
        }
        if (!UIUtils.isMobileNO(edtPhoneNum.getText().toString())){
            ToastUtils.showShort("手机号码格式错误");
            return;
        }
        params.put("type", classfitID);//问题分类
        if (!TextUtils.isEmpty(edtCodeNum.getText().toString())) {
            params.put("code", edtCodeNum.getText().toString());//出厂编码
        }
        if (!TextUtils.isEmpty(edtPhoneNum.getText().toString())) {
            if (!UIUtils.isMobileNO(edtPhoneNum.getText().toString())) {
                ToastUtils.showShort("手机号码格式错误");
                return;
            }
            params.put("phone", edtPhoneNum.getText().toString());//手机号码
        }
        if (TextUtils.isEmpty(mEditPartContents.getText().toString())) {
            ToastUtils.showShort("请输入问题详情");
            return;
        }
        params.put("content", mEditPartContents.getText().toString());//问题详情
        LogUtils.e("提交数据1", levelID);
        LogUtils.e("提交数据2", edtProblemName.getText().toString());
        LogUtils.e("提交数据3", AppConfig.getInstance().getString("fjCustomerId", ""));
        LogUtils.e("提交数据4", edtCodeNum.getText().toString());
        LogUtils.e("提交数据5", mEditPartContents.getText().toString());
        */
/**
         * 提交数据
         *//*

        uploadDatas(ApiService.SAVE_GUARANTEE, params, "", imgFiles);

    }

    */
/**
     * 提交数据
     *
     * @param reqUrl
     * @param params
     * @param pic_key
     * @param files
     *//*

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
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i("上传", response);
                hideCustomProgressDialog();
                if (response.code() == 200) {
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            ToastUtils.showShort("提交成功");
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }

                    }.start();

                    //  Toast.makeText(baseContext, "提交成功", Toast.LENGTH_SHORT).show();
                    //
                    finish();
                }
            }
        });
    }

    */
/**
     * 显示图片
     *//*

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
                    //   txv_page.setText((clickPositon + 1) + "/" + vpNum);
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
                    PhotoPickerIntent intent = new PhotoPickerIntent(ActivityRecog.this);
                    intent.setPhotoCount(1);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, IMAGE_CHOSE_REQUEST);
                }
            });
            mLinearLayout.addView(defaultView);
        }
    }

    */
/**
     * 点击查看大图片
     *//*

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
        View[] views = {edtProblemName, mEditPartContents, edtPhoneNum, edtCodeNum};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.edt_part, R.id.edt_part_content, R.id.edt_phone_num, R.id.edt_code_num};
        return ids;
    }

    @Override
    public void click() {
        if (imagePopupWindow != null && imagePopupWindow.isShowing()) {
            imagePopupWindow.dismiss();
        }
    }

    */
/**
     * 销毁时需要释放识别资源。
     *//*

    @Override
    protected void onDestroy() {
      //  myRecognizer.release();
        Log.i(TAG, "onDestory");
        super.onDestroy();
    }

    */
/**
     * 开始录音后，手动停止录音。SDK会识别在此过程中的录音。点击“停止”按钮后调用。
     *//*

//    private void stop() {
//        myRecognizer.stop();
//    }

    */
/**
     * 开始录音后，取消这次录音。SDK会取消本次识别，回到原始状态。点击“取消”按钮后调用。
     *//*

//    private void cancel() {
//        myRecognizer.cancel();
//    }

    */
/**
     * @return
     *//*

    //protected abstract CommonRecogParams getApiParams();
}
*/
