package com.gao.intelligent.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.AppTools;
import com.gao.intelligent.utils.UIUtils;
import com.gao.intelligent.view.photo.PhotoPickerActivity;
import com.gao.intelligent.view.photo.PhotoPickerIntent;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:用户信息页面
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.rl_icon)
    View rlIcon;
    @BindView(R.id.imgv_icon)
    ImageView imgvIcon;//头像
    @BindView(R.id.edt_email)
    EditText mTextEmail;//邮箱
    @BindView(R.id.edt_nickname)
    EditText mTextName;//用户名
    @BindView(R.id.edt_phone)
    EditText mTextPhone;//电话

    public final static int dialog_sure = 108;
    public final static int dialog_cancel = 109;

    private static final int PHOTO_REQUEST_CODE = 1001;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData() {
        if (AppConfig.getInstance().getString("mobile", "") != null) {
            mTextPhone.setText(AppConfig.getInstance().getString("mobile", "") );
        }
        if (AppConfig.getInstance().getString("usercname", "") != null) {
            mTextName.setText(AppConfig.getInstance().getString("usercname", ""));
        }
        if (AppConfig.getInstance().getString("email", "") != null) {
            mTextEmail.setText(AppConfig.getInstance().getString("email", ""));
        }

    }

    @OnClick({R.id.rl_icon, R.id.toolbar, R.id.txv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_icon:
                PhotoPickerIntent intent = new PhotoPickerIntent(this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, PHOTO_REQUEST_CODE);
                break;
            case R.id.toolbar:
                finish();
                break;
            case R.id.txv_save:
                UIUtils.showToast("保存");
                break;
        }
    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        switch (what) {
            case dialog_sure:
                alertDialog.dismiss();
                break;
            case dialog_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Uri uri = Uri.fromFile(new File(photos.get(0)));
                AppTools.startPhotoZoom(this, uri);
            }
        } else if (requestCode == Comm.RESULT_ZOOM && resultCode == Activity.RESULT_OK) {
            try {
                hideProgressDialog();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                options.inJustDecodeBounds = false;
                options.outWidth = 200;
                options.outHeight = 200;
                //将Uri图片转换为Bitmap
                final Bitmap photo = BitmapFactory.decodeFile(Global.uritempFile.getPath(), options);
                imgvIcon.setImageBitmap(photo);
                //  FileUtil.saveSDcardImage(photo, Comm.SDCARD_IMG_ROOT, String.valueOf(Global.currentAccountModel.getId()));
                // ImageLoader.getInstance().clearDiskCache();
                /// showProgressDialog(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
