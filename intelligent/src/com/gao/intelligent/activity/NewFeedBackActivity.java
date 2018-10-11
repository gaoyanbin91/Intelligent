package com.gao.intelligent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.utils.AppTools;
import com.gao.intelligent.utils.FileUtil;
import com.gao.intelligent.utils.GlideUtils;
import com.gao.intelligent.view.photo.PhotoPickerActivity;
import com.gao.intelligent.view.photo.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/23.
 * 描述:意见反馈页面
 */
public class NewFeedBackActivity extends BaseActivity {
    private final static int IMAGE_CHOSE_REQUEST = 1000;
    private final static int IMAGE_CUT_REQUEST = 1001;

    @BindView(R.id.edt_tel)
    EditText mEditTextTel;//电话
    @BindView(R.id.edt_content)
    EditText mEditTextContent;//意见
    @BindView(R.id.txv_submit)
    TextView mTextViewSubmit;//提交
    @BindView(R.id.ll_imgv)
    LinearLayout mLinearLayout;
    private List<String> imgvUrlList =new ArrayList<>();
    private List<String> imgvNetUrlList=new ArrayList<>();
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_face_back;
    }

    @Override
    public void initView() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(mEditTextTel.getText()) && !TextUtils.isEmpty(mEditTextContent.getText())) {
                    mTextViewSubmit.setBackgroundResource(R.drawable.bg_btn_face_enable);
                } else {
                    mTextViewSubmit.setBackgroundResource(R.drawable.bg_btn_face_disable);
                }
            }
        };
        mEditTextContent.addTextChangedListener(textWatcher);
        mEditTextTel.addTextChangedListener(textWatcher);
        showSelectImgv();
    }

    @Override
    public void initData() {


    }

    /**
     * 显示图片
     */
    private void showSelectImgv() {
        mLinearLayout.removeAllViews();

        for (String url : imgvUrlList) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_imgv_faceback, null);
            ImageView imgv_face = (ImageView) view.findViewById(R.id.imgv_face);

            ImageView imgv_del = (ImageView) view.findViewById(R.id.imgv_del);
            imgv_del.setVisibility(View.VISIBLE);
            imgv_del.setTag(url);
            imgv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = (String) v.getTag();
                 //   imgvNetUrlList.remove(imgvUrlList.indexOf(url));
                    imgvUrlList.remove(url);
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
                    PhotoPickerIntent intent = new PhotoPickerIntent(NewFeedBackActivity.this);
                    intent.setPhotoCount(1);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, IMAGE_CHOSE_REQUEST);
                }
            });
            mLinearLayout.addView(defaultView);
        }
    }
    @OnClick({R.id.toolbar})
    public void onClick() {
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CHOSE_REQUEST:

                if (data != null) {
                    List<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                    if (photos != null && photos.size() > 0) {
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
                //    String imageUrl = ImageDownloader.Scheme.FILE.wrap(filepath);
                 //   updateImage(filepath, imageUrl);
                    imgvUrlList.add(filepath );
                    showSelectImgv();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
