package com.gao.intelligent.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.model.LanguageDataModel;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.DataCleanManager;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.Utils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述:app设置页面
 */
public class SetActivity extends BaseActivity {
    private static final String TAG = SetActivity.class.getName();
    private static final int update_dialog = 103;
    private static final int clear_cache_refresh_type = 100;
    private static final int REQUEST_CODE = 101;
    @BindView(R.id.cacheSizeTextView)
    TextView cacheSizeText;//缓存
    @BindView(R.id.mainLandTextView)
    TextView mainLandText;//地域
    @BindView(R.id.iv_red)
    ImageView ivRed;
    private boolean showDialog = true;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        try {
            switch (what) {
                case update_dialog:
                    String url = (String) obj;
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    Intent urlIntent = new Intent();
                    urlIntent.setAction("android.intent.action.VIEW");
                    Uri updateUri = Uri.parse(url);
                    urlIntent.setData(updateUri);
                    startActivity(urlIntent);

                    break;
                case clear_cache_refresh_type:
                    //清除应用内存缓存
                    DataCleanManager.clearAllCache(MyApp.getContext());
                    //清除SD卡内存缓存
                    //  FileUtil2.deleteFilesByDirectory(new File(Comm.SDCARD_PROJECT_ROOT));
                    /**
                     * 清空广告信息
                     */
                    String local = Utils.getLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.REGION_CODE);
                    String language = Utils.getLocalSave(this, Comm.SAVEFILE, Comm.LANGUAGE);
                    String advertLocationPath = local + language;
                    Utils.setLocalSaveStr(MyApp.getContext(), Comm.SAVEFILE, advertLocationPath, "");// 存储资源信息
                    cacheSizeText.setText("0 KB");
                    break;
            }
        } catch (Throwable e) {
            LogUtils.e("SetActivity", e);
        }
    }

    @Override
    public void initView() {

        if (getIntent().getStringExtra("flag") != null && getIntent().getStringExtra("flag").equals("1")) {

            ivRed.setVisibility(View.VISIBLE);
            showDialog = true;
        } else {
            ivRed.setVisibility(View.GONE);
            showDialog = false;
        }
    }

    @Override
    public void initData() {
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(MyApp.getContext());
            cacheSizeText.setText(cacheSize);
            List<LanguageDataModel> languageDataModelList = getAreaList();
            if (languageDataModelList != null) {

                LanguageDataModel model = new LanguageDataModel();
                if (Utils.getCheckModel().regionCode.equals("-1")) {
                    model.code = "156";
                } else {
                    model.code = String.valueOf(Utils.getCheckModel().regionCode);
                }

                int index = languageDataModelList.indexOf(model);
                LogUtils.info("index==>" + index + "==leng==>" + languageDataModelList.size() + "==" + model.toString());

                LanguageDataModel model2 = languageDataModelList.get(index);

                mainLandText.setText(model2.getLanguageDisplay());
            }
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.toolbar, R.id.signout_button, R.id.clearLayout, R.id.aboutLayout,
            R.id.languageLayout, R.id.updateLayout, R.id.mainlandLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.languageLayout:
                startActivity(new Intent(this, LanguageActivity.class));
                break;
            case R.id.toolbar:
                finish();
                break;
            case R.id.mainlandLayout://选择地域
                startActivityForResult(new Intent(this, AreaListActivity.class), REQUEST_CODE);
                break;
            case R.id.signout_button://登出
                AppConfig.getInstance().putString("token", "");
                exitApp();//登出
                startActivity(new Intent(SetActivity.this, LoginActivity.class));
                break;
            case R.id.aboutLayout:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.clearLayout://清除缓存
                showAlertDialog(getString(R.string.prompt), getString(R.string.set_clear_cache_dialog), getString(R.string.clear), getString(R.string.cancel), null, clear_cache_refresh_type, CUSTOM_DISMISS, null, null);
                break;
            case R.id.updateLayout://版本更新
                if (showDialog) {
                    LogUtils.d("下载地址：", ApiService.BASE_URL + "downloadFile" + getIntent().getStringExtra("url"));
                    showAlertDialog(getString(R.string.prompt),
                            getString(R.string.hbszxbb),
                            getString(R.string.UMUpdateNow),
                            getString(R.string.UMNotNow),
                            update_dialog,
                            DISMISS_ALERT_DIALOG_TYPE,
                            ApiService.BASE_URL + "downloadFile" + getIntent().getStringExtra("url"),
                            null);
                } else {
                    ToastUtils.showShort("已经是最新版本");
                }
                break;
        }
    }

    /**
     * 获取地域数据
     *
     * @return
     */
    List<LanguageDataModel> getAreaList() {
        try {
            InputStream inputStream = getResources().getAssets().open("regcode.txt");
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                LogUtils.e(TAG, e1);
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer("");
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    // LogUtils.info("line==>" + line);
                    sb.append(line);
                }

            } catch (IOException e) {
                LogUtils.e(TAG, e);
            }

            //    LogUtils.info(sb.toString());
            Gson gson = new Gson();
            List<LanguageDataModel> mList = gson.fromJson(sb.toString(), new TypeReference<List<LanguageDataModel>>() {
            }.getType());
            return mList;
        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            LanguageDataModel languageDataModel = (LanguageDataModel) data.getSerializableExtra("language");
            mainLandText.setText(languageDataModel.getLanguageDisplay());
            LogUtils.info("onActivityResult==>" + languageDataModel.toString());
            Utils.getCheckModel().regionCode = languageDataModel.code;
            Utils.setLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.REGION_CODE, languageDataModel.code);

        }
    }

}
