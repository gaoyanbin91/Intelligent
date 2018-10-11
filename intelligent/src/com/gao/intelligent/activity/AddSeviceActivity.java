package com.gao.intelligent.activity;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ImageViewVpAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
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
 * 描述:
 */
public class AddSeviceActivity extends BaseActivity implements   ImageViewVpAdapter.OnImageClick {
    @BindView(R.id.btn_sound)
    Button btn_sound;
    @BindView(R.id.edt_part_content)
    EditText mEditPartContents;
    @BindView(R.id.edt_problem_name)
    EditText edtProblemName;//问题名称
    @BindView(R.id.tex_classify)
    TextView texClassify;//问题分类
    @BindView(R.id.edt_code_num)
    EditText edtCodeNum;//出厂编码
    @BindView(R.id.edt_phone_num)
    EditText edtPhoneNum;//手机号码
    @BindView(R.id.rg_level)
    RadioGroup rgLevel;//优先级

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
    String[] classfiyArray;
    String videoPath, classfitID, levelID;//视频文件路劲
    private RadioButton selectButton;//选中的优先级
    private String levelText = "";//优先级
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
    private List<QueClassfiyBean> mLists = new ArrayList<>();
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sound;
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
    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HashMap<String, String> param = new HashMap<>();
        param.put("code", "wtfl");
        sendHttpGet(ApiService.QUERY_QUS_CLASSFIY, param, 10003);
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
                        && !TextUtils.isEmpty(texClassify.getText())&&!TextUtils.isEmpty(edtPhoneNum.getText().toString())) {
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
                        && !TextUtils.isEmpty(texClassify.getText())&&!TextUtils.isEmpty(edtPhoneNum.getText().toString())) {
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
                Intent intent = new Intent( AddSeviceActivity.this, RecordedActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void showFilterPop(final int flag, final String[] arr, final TextView lineName, final View view) {
        PopUpWindow popupWindow = new PopUpWindow(AddSeviceActivity.this, arr, null, view);
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
        /**
         * 提交数据
         */
        uploadDatas(ApiService.SAVE_GUARANTEE, params, "", imgFiles);

    }

    /**
     * 提交数据
     *
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
                    finish();
                }
            }
        });
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

    /**
     * 销毁时需要释放识别资源。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
