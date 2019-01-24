package com.gao.intelligent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.dialog.DownloadDialog;
import com.gao.intelligent.fragment.DataShowFragment;
import com.gao.intelligent.fragment.FaultSearchFragment;
import com.gao.intelligent.fragment.MyFragment;
import com.gao.intelligent.fragment.RealtimeFragment;
import com.gao.intelligent.model.AppVersionBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.FileUtil;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.UIUtils;
import com.gao.intelligent.utils.okhttp.DownloadUtil;
import com.gao.intelligent.view.FragmentTabHost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private String txvMenu[];
    private int intImageViewArray2[];
    /**
     * 定义数组来存放Fragment界面
     */
    private Class fragmentArray[];

    private List<View> badgeTargetList;

    List<AppVersionBean.RowsBean> mRowsBeanList = new ArrayList<>();

    private static final int update_dialog = 103;
    private static final int update_dialog_cancel = 123;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void aidHandleMessage(int what,int type, Object obj) {
        super.aidHandleMessage(what, type,obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    finish();
                    exitApp();//登出
                    startActivity(new Intent(baseContext, LoginActivity.class));
                    return;
                }
                LogUtils.i("版本信息", obj.toString());
                AppVersionBean mBean = JSON.parseObject(obj.toString(), AppVersionBean.class);
                if (mBean.getRows() != null) {
                    mRowsBeanList = mBean.getRows();
                    try {
                        LogUtils.d("版本",mRowsBeanList.get(0).getCustomizeVersion()+":"+ UIUtils.getAppVersionName( baseContext));
                        if ((UIUtils.compareVersion(mRowsBeanList.get(0).getCustomizeVersion(), UIUtils.getAppVersionName( baseContext)) > 0)) {
                            showAlertDialog((getString(R.string.prompt)),  mRowsBeanList.get(0).getUpdateContent(),
                                    getString(R.string.UMUpdateNow),
                                    getString(R.string.UMNotNow), null, update_dialog, update_dialog_cancel, ApiService.BASE_URL + "downloadFile" +mRowsBeanList.get(0).getAddress(), null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 10003:
                hideProgressDialog();
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
                break;
            case update_dialog_cancel:
                cancelProgressDialog();
                finish();
//                AppConfig.getInstance().putString("token", "");
                AppConfig.getInstance().putString("type", "");
                AppConfig.getInstance().putString("signImg", "");
                exitApp();//登出
                break;
        }
    }
    public void initView() {
        HashMap<String, String> param = new HashMap<>();
        param.put("name", "易生产.apk");
        sendHttpGet(ApiService.QUERY_APP_VERSON, param, 11001);
        //    requestPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setSystemBar(true);
        if (TextUtils.isEmpty(AppConfig.getInstance().getString("type",""))){
            intImageViewArray2 = new int[]{R.drawable.tab_home_bg, R.drawable.tab_fault_bg,   R.drawable.tab_my_bg};
            fragmentArray = new Class[]{DataShowFragment.class,FaultSearchFragment.class,  MyFragment.class};
            txvMenu = new String[]{getString(R.string.tab_home) ,getString(R.string.tab_fault),
                    getString(R.string.tab_mine)};

        }else {
            intImageViewArray2 = new int[]{R.drawable.tab_home_bg,R.drawable.tab_fault_bg,R.drawable.tab_search_bg,    R.drawable.tab_my_bg};
            fragmentArray = new Class[]{DataShowFragment.class,FaultSearchFragment.class, RealtimeFragment.class, MyFragment.class};
            txvMenu = new String[]{getString(R.string.tab_home) ,getString(R.string.tab_fault),getString(R.string.tab_contact) ,
                    getString(R.string.tab_mine)};
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = intImageViewArray2.length;

        tabhost.setup(this, fragmentManager, R.id.realtabcontent);
        badgeTargetList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 将Tab按钮添加进Tab选项卡中
            View view = getTabItemView(i);
            badgeTargetList.add(view);
            tabhost.addTab(tabhost.newTabSpec(String.valueOf(i)).setIndicator(view), fragmentArray[i], null);
        }

        tabhost.getTabWidget().setDividerDrawable(getResources().getDrawable(R.color.white));
        tabhost.setOnTabChangedListener(this);
    }
    private View getTabItemView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
        ImageView mImageView = (ImageView) view.findViewById(R.id.icon);
        TextView txv_menu = (TextView) view.findViewById(R.id.txv_menu);
        txv_menu.setText(txvMenu[i]);
        mImageView.setImageResource(intImageViewArray2[i]);
        return view;
    }
    @Override
    public void onTabChanged(String tabId) {
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
                    downloadDialog = new DownloadDialog( baseContext, R.style.transparentFrameWindowStyle  );
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
            Uri apkUri = FileProvider.getUriForFile( baseContext, "com.pmcc.wisdomelectro.fileprovider", new File(filePath));//在AndroidManifest中的android:authorities值
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
