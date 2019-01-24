package com.gao.intelligent.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.activity.PasswordActivity;
import com.gao.intelligent.activity.RealtimeMonitorActivity;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.dialog.DownloadDialog;
import com.gao.intelligent.model.AppVersionBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.FileUtil;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.UIUtils;
import com.gao.intelligent.utils.okhttp.DownloadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.gao.intelligent.base.BaseActivity.exitApp;

/**
 * Created by gaoyanbin on 2018/4/12.
 * 描述:我的页面
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.text_name)
    TextView textName;//用户名
    @BindView(R.id.text_version_name)
    TextView textVersionName;//版本号
    @BindView(R.id.iv_red)
    ImageView ivRed;
    String flag = "0";
    List<AppVersionBean.RowsBean> mRowsBeanList = new ArrayList<>();
    public final static int dialog_sure = 108;
    public final static int dialog_cancel = 109;
    private static final int update_dialog = 103;
    private static final int update_dialog_cancel = 123;
    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public void aidHandleMessage(int what,int type, Object obj) {
        super.aidHandleMessage(what, type,obj);
        switch (what) {

            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                LogUtils.i("版本信息", obj.toString());
                AppVersionBean mBean = JSON.parseObject(obj.toString(), AppVersionBean.class);
                if (mBean.getRows() != null) {
                    mRowsBeanList = mBean.getRows();
//                    ToastUtils.showShort(mRowsBeanList.get(0).getVersion());
                    try {
                        LogUtils.d("版本",mRowsBeanList.get(0).getVersion()+":"+UIUtils.getAppVersionCode(getActivity()));
                        if ((UIUtils.compareVersion(mRowsBeanList.get(0).getCustomizeVersion(), UIUtils.getAppVersionName(getActivity()) ) > 0)) {
                            ivRed.setVisibility(View.VISIBLE);
                            showAlertDialog((getString(R.string.prompt)),  mRowsBeanList.get(0).getUpdateContent(),
                                    getString(R.string.UMUpdateNow),
                                    getString(R.string.UMNotNow), null, update_dialog, update_dialog_cancel, ApiService.BASE_URL + "downloadFile" +mRowsBeanList.get(0).getAddress(), null);
                            flag = "1";
                        } else {
                            ivRed.setVisibility(View.GONE);
                            flag = "0";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //tvF.setText( obj.toString());
                break;
            case 10003:
                hideProgressDialog();
                //  ToastUtils.showShort(obj.toString());
//                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        switch (what) {
            case update_dialog:
                String url = (String) obj;
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                readyDown(url);
//                Intent urlIntent = new Intent();
//                urlIntent.setAction("android.intent.action.VIEW");
//                Uri updateUri = Uri.parse(url);
//                urlIntent.setData(updateUri);
//                startActivity(urlIntent);
                break;
            case update_dialog_cancel:
                getActivity().finish();
                AppConfig.getInstance().putString("token", "");
                AppConfig.getInstance().putString("type", "");
                AppConfig.getInstance().putString("signImg", "");
                exitApp();//登出
                break;
            case dialog_sure:
                getActivity().finish();

                AppConfig.getInstance().putString("token", "");
                AppConfig.getInstance().putString("type", "");
                AppConfig.getInstance().putString("signImg", "");
                exitApp();//登出
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case dialog_cancel:

                break;
        }
    }

    @Override
    protected void initView() {
//        HashMap<String, String> param = new HashMap<>();
//        param.put("name", "易生产.apk");
//        sendHttpGet(ApiService.QUERY_APP_VERSON, param, 11001);
    }

    @Override
    protected void initData() {
        if (AppConfig.getInstance().getString("usercname", "") != null) {
            textName.setText("您好！" + AppConfig.getInstance().getString("usercname", ""));
        }
        textVersionName.setText("版本号 v " + UIUtils.getAppVersionName(getActivity()));
    }

    @OnClick({R.id.passwordLayout, R.id.versionLayout, R.id.signout_button,R.id.lineLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lineLayout:
                startActivity(new Intent(getActivity(),RealtimeMonitorActivity.class));
                break;
            case R.id.versionLayout:
                HashMap<String, String> param = new HashMap<>();
                param.put("name", "易生产.apk");
                sendHttpGet(ApiService.QUERY_APP_VERSON, param, 11001);
                if (flag.equals("1")) {
                    showAlertDialog((getString(R.string.prompt)),  mRowsBeanList.get(0).getUpdateContent(),
                            getString(R.string.UMUpdateNow),
                            getString(R.string.UMNotNow), null, update_dialog, update_dialog_cancel, ApiService.BASE_URL + "downloadFile" +mRowsBeanList.get(0).getAddress(), null);
                } else {
                    ToastUtils.showShort("已经是最新版本");
                }

                break;

            case R.id.signout_button:
                showAlertDialog(getString(R.string.user_update_toast_edit), "",
                        getString(R.string.user_update_toast_edit_con),
                        getString(R.string.md_cancel_label), null, dialog_sure, dialog_cancel, null, null);

                break;

            case R.id.passwordLayout:
                startActivity(new Intent(getContext(),PasswordActivity.class));
                break;

        }
    }

    protected void readyDown(String apkDownloadUrl) {
        // 创建保存路径
        String apkFilePath = FileUtil.getDown().getAbsolutePath();
        File file = new File(String.valueOf(apkFilePath));
        if (!file.exists()) {
            file.mkdirs();
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("chmod 777 " + apkFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        download(apkDownloadUrl, apkFilePath);
    }
    /**
     * 下载监听
     */
    private DownloadDialog downloadDialog;

    private void download(String apkDownloadUrl, String apkFilePath) {

        DownloadUtil.getInstance().download(apkDownloadUrl, Comm.SDCARD_APK_ROOT, new DownloadUtil.OnDownloadListener() {



            @Override
            public void onDownloadSuccess(String path) {
                cancelProgressDialog();
                startInstall(path);

            }

            @Override
            public void onDownloading(int progress) {
//            LogUtils.i("视频下载", progress + "");
                if (downloadDialog == null) {
                    downloadDialog = new DownloadDialog(getActivity(), R.style.transparentFrameWindowStyle);
                    downloadDialog.setCanceledOnTouchOutside(false);
                    downloadDialog.setCancelable(false);
                    Window window = downloadDialog.getWindow();
                    window.setGravity(Gravity.CENTER);
                    downloadDialog.show();
                } else {
                    downloadDialog.show();
                }
                if (downloadDialog != null) {
                    downloadDialog.tv_content2.setText(progress+"%");
                    downloadDialog.tv_content.setText(progress + "/" + "100");
                    downloadDialog.wdSeekBar.setMax(100);
                    downloadDialog.wdSeekBar.setProgress((int) progress);
                }
            }

            @Override
            public void onDownloadFailed() {
                downloadDialog.dismiss();
                ToastUtils.showShort("下载失败");
            }
        });
    }

    private void startInstall(String filePath) {
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.gao.intelligent.fileprovider", new File(filePath));//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else {
            //System.out.println("下载完成至：" + apkFile.getPath());
            Intent intent = new Intent();// 提示用户进行安装 开启一个系统的界面 用到隐式意图
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.fromFile(new File(filePath)); // 翻译一个file 成Uri
                   /* intent.setType("application/vnd.android.package-archive");
                    intent.setData(uri);*/
            intent.setDataAndType(uri, "application/vnd.android.package-archive"); // 如果需要同时设置type和data需要 同时设置
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//如果不加，最后不会提示完成、打开。
            startActivity(intent);
        }
    }

    private void cancelProgressDialog() {
        if (downloadDialog != null) {
            downloadDialog.dismiss();
            downloadDialog = null;
        }
    }
}
