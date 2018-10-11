package com.gao.intelligent;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.Utils;
import com.yixia.camera.VCamera;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by gaoyanbin on 2018/1/24.
 * 描述:全局application
 */

public class MyApp extends BaseApp {
    private static final String TAG = MyApp.class.getSimpleName();

    private static MyApp _context;
    public static MyApp mInstance = null;
    public static HashMap<String, Activity> activityHashMap;
    public Handler handler;
    public static String VIDEO_PATH = "/sdcard/zhengfangji/";
    @Override
    public void onCreate() {
        LogUtils.d( "","[ExampleApplication] onCreate");
        super.onCreate();
        _context = this;
        mInstance = this;

        handler = new Handler();

        VIDEO_PATH += String.valueOf(System.currentTimeMillis());
        File file = new File(VIDEO_PATH);
        if (!file.exists()) { file.mkdirs();}

        VCamera.setVideoCachePath(VIDEO_PATH);
        VCamera.setDebugMode(true);
        VCamera.initialize(this);

        initScreent();

        initLanguage();
        //**************************************相关第三方SDK的初始化等操作*************************************************

    }

    private void initScreent() {
        try {
            DisplayMetrics localDisplayMetrics = MyApp.getContext().getResources().getDisplayMetrics();
            Global.mScreenHeight = localDisplayMetrics.heightPixels;
            Global.mScreenWidth = localDisplayMetrics.widthPixels;

            LogUtils.info(localDisplayMetrics.density + "==" + localDisplayMetrics.densityDpi + "==" + Global.mScreenWidth + "==" + Global.mScreenHeight);

            TelephonyManager tm = (TelephonyManager) MyApp.getContext().getSystemService(TELEPHONY_SERVICE);

            Global.IMIE = tm.getDeviceId();
            LogUtils.info("IMIE==>" + Global.IMIE);

            PackageManager manager =MyApp.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApp.getContext().getPackageName(), 0);
            String version = info.versionName;
            Global.VERSION = version;

        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }
    }

    private void initLanguage() {
        try {
            Global.LANGUAGE_TYPE = Utils.getLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.LANGUAGE);
            if (TextUtils.isEmpty(Global.LANGUAGE_TYPE)) {
                String mobileLanguage = Utils.getLanguageStr(MyApp.getContext());
                if (!TextUtils.isEmpty(mobileLanguage)) {
                    if (mobileLanguage.equals("zh")) {
                        Global.LANGUAGE_TYPE = Comm.SIMPLIFIED_CHINESE;
                    } else if (mobileLanguage.equals("en")) {
                        Global.LANGUAGE_TYPE = Comm.ENGLISH;
                    }
                } else {
                    Global.LANGUAGE_TYPE = Comm.SIMPLIFIED_CHINESE;
                }
            }

            LogUtils.info(TAG + "===>" + Utils.getLanguageStr(MyApp.getContext()));

            if (Global.LANGUAGE_TYPE.equals(Comm.SIMPLIFIED_CHINESE)) {
                Utils.setLang(MyApp.getContext(), Locale.SIMPLIFIED_CHINESE);
            } else if (Global.LANGUAGE_TYPE.equals(Comm.TRADITIONAL_CHINESE)) {
                Utils.setLang(MyApp.getContext(), Locale.TRADITIONAL_CHINESE);
            } else if (Global.LANGUAGE_TYPE.equals(Comm.ENGLISH)) {
                Utils.setLang(MyApp.getContext(), Locale.ENGLISH);
            }
            LogUtils.info(TAG + "===>" + Utils.getLanguageStr(MyApp.getContext()));
            Utils.setLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.LANGUAGE,Global.LANGUAGE_TYPE);
        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }
    }

    public static MyApp getInstance() {
        return _context;
    }
    public HashMap<String, Activity> getActivityHashMap() {
        if (activityHashMap == null) {
            activityHashMap = new HashMap<>();
        }
        return activityHashMap;
    }

    public  static Map <String,String> map=new HashMap<>();

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        MyApp.map = map;
    }
}
