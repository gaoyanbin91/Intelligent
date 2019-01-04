package com.gao.intelligent.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.sign.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Yanbin on 2018/12/6.
 * 描述:签名页面
 */
public class SignActivity extends BaseActivity {
    @BindView(R.id.signature_pad)
    SignatureView mSignatureView;
    @BindView(R.id.button)
    Button mSaveButton;
    @BindView(R.id.clear)
    Button mClearButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.texHint)
    TextView texHint;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sign;

    }

    @Override
    public void initView() {
        LogUtils.d("heheh1",getIntent().getStringExtra("version")+"3");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //手写布局监听
        mSignatureView.setOnSignedListener(new SignatureView.OnSignedListener() {
            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
                mSaveButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_orange);
                mClearButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_green);
                texHint.setVisibility(View.GONE);
            }

            @Override
            public void onClear() {
                texHint.setVisibility(View.VISIBLE);
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
                mSaveButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_gray);
                mClearButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_gray);
            }
        });
        //清除画板
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.clear();
            }
        });

        //提交签名
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = mSignatureView.getSignatureBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                File photo = new File(getAlbumStorageDir("yishengchan"), String.format("yishengchan_%d.png", System.currentTimeMillis()));
                try {
                    saveBitmapToPNG(signatureBitmap, photo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<String, String> params = new HashMap<>();
                if (getIntent().getStringExtra("flag").equals("lian")){//联系函签字
                    showProgressDialog("正在提交...");
                    params.put("version", getIntent().getStringExtra("version"));
                    params.put("id",  getIntent().getStringExtra("questionID"));//
                    uploadSign(ApiService.SAVE_SIGN_PNG,params,photo);


                }else {//回函表签字
                    params.put("processState", getIntent().getStringExtra("version"));
                    params.put("id",  getIntent().getStringExtra("questionID"));//
                    showProgressDialog("正在提交...");
                    uploadSign(ApiService.SING_HUI_NAME, params,photo);

                }
            }
        });
    }

    /**
     * 上传签名
     */
    private void uploadSign(String url,Map<String, String> params,File photo) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        multipartBodyBuilder.addFormDataPart("file", photo.getName(), RequestBody.create(MEDIA_TYPE_PNG, photo));
        LogUtils.d("name", photo.getName());

        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(url);// 添加URL地址
        RequestBuilder.post(requestBody);
        RequestBuilder.addHeader("token", AppConfig.getInstance().getString("token", ""));
        Request request = RequestBuilder.build();
        OkHttpClient mOkHttpClient = new OkHttpClient();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("上传", call.request().body().toString());
                hideCustomProgressDialog();
                new Thread() {
                    public void run() {
                        Looper.prepare();

                        ToastUtils.showShort("提交失败");
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }.start();


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
                                LogUtils.i("上传", responseData);
                                ResultBean resultBean = JSON.parseObject(responseData, ResultBean.class);
                                if (resultBean.getResultCode().equals("1")){
                                    ToastUtils.showShort("提交成功");
                                    MyApp.getInstance().setType(true);
                                    finish();
                                }else {
                                    ToastUtils.showShort("上传失败了");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }

                    }.start();

                    //  Toast.makeText(baseContext, "提交成功", Toast.LENGTH_SHORT).show();

                }else {
                    ToastUtils.showShort("上传失败了");
                }
            }
        });
    }


    public void saveBitmapToPNG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        stream.close();
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }
}
