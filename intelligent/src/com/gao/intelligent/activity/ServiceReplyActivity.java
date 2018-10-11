package com.gao.intelligent.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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

import com.gao.intelligent.R;
import com.gao.intelligent.adapter.ImageViewVpAdapter;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.dialog.CustomProgressDialog;
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
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gaoyanbin on 2018/5/31.
 * 描述:服务请求回复页面
 */
public class ServiceReplyActivity extends Activity implements ImageViewVpAdapter.OnImageClick{
    @BindView(R.id.edt_part_content)
    EditText etContent;
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
    @BindView(R.id.view_back)
    View viewBack;
    private int vpNum = 0;
    private TextView mTittle,txv_page;
    private final static int IMAGE_CHOSE_REQUEST = 1000;
    private final static int IMAGE_CUT_REQUEST = 1001;
    private PopupWindow imagePopupWindow;
    private ViewPager vp_photos;
    private ImageViewVpAdapter imageViewVpAdapter;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_VIDEO_TYPE_PNG = MediaType.parse("video/mp4");
    String videoPath;
    private List<String> imgvUrlList = new ArrayList<>();
    private List<File> imgFiles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_reply);
        ButterKnife.bind(this);
        initData();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    public void initData() {
        mTittle = findViewById(R.id.textView);
        imageViewVpAdapter = new ImageViewVpAdapter(this, new ArrayList<String>());
        imageViewVpAdapter.setOnImageClick(ServiceReplyActivity.this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(etContent.getText())) {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_enable);
                } else {
                    txvSubmit.setBackgroundResource(R.drawable.bg_btn_face_disable);
                }
            }
        };
        etContent.addTextChangedListener(textWatcher);
        showSelectImgv();
        ivVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceReplyActivity.this, RecordedActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        txvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    ToastUtils.showShort("请输入问题详情");
                    return;
                }
                params.put("content", etContent.getText().toString());
                params.put("fjQuestionId",  getIntent().getStringExtra("ID"));
                params.put("queFlag", "1");
                /**
                 * 提交数据
                 */
                uploadDatas(ApiService.SAVE_REPLY_DATAS, params, imgFiles);
            }
        });
        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    /**
     * 提交数据
     * @param reqUrl
     * @param params
     * @param files
     */
    private void uploadDatas(String reqUrl, Map<String, String> params, List<File> files) {
          showProgressDialog("正在提交...");
        txvSubmit.setEnabled(false);
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
        // 添加URL地址
        RequestBuilder.url(reqUrl);
        RequestBuilder.post(requestBody);
        RequestBuilder.addHeader("token", AppConfig.getInstance().getString("token", ""));
        Request request = RequestBuilder.build();
        OkHttpClient mOkHttpClient = new OkHttpClient();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideCustomProgressDialog();
                ToastUtils.showShort("提交失败");

            }

            @Override
            public void onResponse(Call call, Response response)  {
                LogUtils.i("回复", response);
                hideCustomProgressDialog();
                if (response.code()==200){
                    new Thread() {
                        public void run() {
                            Looper.prepare();
                            ToastUtils.showShort("提交成功");
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }

                    }.start();
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
            ImageView imgv_face =  view.findViewById(R.id.imgv_face);
            View rl_pic = view.findViewById(R.id.rl_pic);

            ImageView imgv_del =  view.findViewById(R.id.imgv_del);
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
            ImageView imgv_default =  defaultView.findViewById(R.id.imgv_face);
            imgv_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(ServiceReplyActivity.this);
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
            vp_photos =  view.findViewById(R.id.vp_photos);
            vp_photos.setAdapter(imageViewVpAdapter);
            txv_page =  view.findViewById(R.id.txv_page);
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
            imagePopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.black)));
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


    CustomProgressDialog customProgressDialog;
    public void showProgressDialog(String text) {
        if (customProgressDialog == null) {

            customProgressDialog = new CustomProgressDialog(this,text);
            customProgressDialog.setCanceledOnTouchOutside(false);
            //customProgressDialog.setCancelable(true);
        }
        customProgressDialog.show();
    }

    public void hideCustomProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }

    @Override
    public void click() {
        if (imagePopupWindow != null && imagePopupWindow.isShowing()) {
            imagePopupWindow.dismiss();
        }
    }
}
