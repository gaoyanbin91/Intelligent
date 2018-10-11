package com.gao.intelligent.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gao.intelligent.MyApp;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.model.CheckModel;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by siberiawolf on 15/11/9.
 */
public class Utils {


    private static final String TAG = "UTILS";

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
    public static String formateRate(String rateStr){
        if(rateStr.indexOf(".") != -1){
            //获取小数点的位置
            int num = 0;
            //找到小数点在字符串中的位置,找到返回一个int类型的数字,不存在的话返回 -1
            num = rateStr.indexOf(".");

            String dianAfter = rateStr.substring(0,num+1);//输入100.30,dianAfter = 100.
            String afterData = rateStr.replace(dianAfter,"");//把原字符(rateStr)串包括小数点和小数点前的字符替换成"",最后得到小数点后的字符(不包括小数点)

            //判断小数点后字符的长度并做不同的操作,得到小数点后两位的字符串
            if(afterData.length() < 2){
                afterData = afterData + "0" ;
            }else{
                afterData = afterData;
            }
            //返回元字符串开始到小数点的位置 + "." + 小数点后两位字符
            return rateStr.substring(0,num) + "." + afterData.substring(0,2);
        }else{
            if(rateStr == "1"){
                return "100";
            }else{
                return rateStr;
            }
        }
    }

    public static CheckModel getCheckModel() {
        try {
            if (Global.mCheckModel == null) {
                Global.mCheckModel = new CheckModel();
                Global.mCheckModel.mainland = 1;
                Global.mCheckModel.regionCode = "156";
                String region = Utils.getLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.REGION_CODE);
                if (!TextUtils.isEmpty(region)) {
                    Global.mCheckModel.regionCode = region;
                }
                String mainland = Utils.getLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.MAINLAND);
                if (!TextUtils.isEmpty(mainland)) {
                    Global.mCheckModel.mainland = Integer.valueOf(mainland);
                }
            }
        } catch (Throwable e) {
            LogUtils.e("getCheckModel", e);
        }

        return Global.mCheckModel;
    }

    public static String getLanguageStr(Context context) {
        Locale locale = MyApp.getContext().getResources().getConfiguration().locale;

        String language = locale.getCountry() + "<==>" + locale.getLanguage();

        return locale.getLanguage();
    }

    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT);
    }

    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeId() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }


    public static void setLang(Context context, Locale l) {
        // 获得res资源对象
        Resources resources = MyApp.getContext().getResources();
        // 获得设置对象
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 语言
        config.locale = l;
        resources.updateConfiguration(config, dm);

        // 刷新activity才能马上奏效
    }

    /**
     * 判断手机号是否合法
     *
     * @param mobiles
     * @return
     */

    public static boolean isMobileNO(String mobiles) {

        if (TextUtils.isEmpty(mobiles)) return false;

        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 判断密码是否合法 6-20位 数字或字母
     *
     * @param password
     * @return
     */

    public static boolean isPassWord(String password) {

        if (TextUtils.isEmpty(password)) return false;

        Pattern p = Pattern.compile("^[0-9A-Za-z]{6,20}$");

        Matcher m = p.matcher(password);

        return m.matches();
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
//        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static void setLocalSaveMap(Context context, String filename, Map<String, String> parms) {

        try {
            if (null != parms) {
                SharedPreferences saves = context.getSharedPreferences(filename, 0);
                SharedPreferences.Editor edit = saves.edit();
                Iterator<String> it = parms.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = parms.get(key);
                    edit.putString(key, value);
                }
                edit.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map getLocalSaveMap(Context context, String filename) {
        Map save = null;
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            save = saves.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;

    }

    @SuppressLint("CommitPrefEdits")
    public static void setLocalSave(Context context, String filename, String savekey, String saveinfo) {
        try {
            if (null != saveinfo) {
                SharedPreferences saves = context.getSharedPreferences(filename, 0);
                SharedPreferences.Editor edit = saves.edit();
                edit.putString(savekey, saveinfo);
                edit.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLocalSaveStr(Context context, String filename, String savekey, String saveinfo) {
        try {
            if (null != saveinfo) {
                SharedPreferences saves = context.getSharedPreferences(filename, 0);
                SharedPreferences.Editor edit = saves.edit();
                edit.putString(savekey, saveinfo);
                edit.apply();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLocalSaveInt(Context context, String filename, String savekey, int saveinfo) {
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            SharedPreferences.Editor edit = saves.edit();
            edit.putInt(savekey, saveinfo);
            edit.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("CommitPrefEdits")
    public static void setLocalSaveBoolean(Context context, String filename, String savekey, boolean saveinfo) {
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            SharedPreferences.Editor edit = saves.edit();
            edit.putBoolean(savekey, saveinfo);
            edit.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getLocalSaveBoolean(Context context, String filename, String key) {
        boolean save = false;
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            save = saves.getBoolean(key, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;

    }


    public static String getLocalSave(Context context, String filename, String key) {
        String save = null;
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            save = saves.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;

    }

    public static String getLocalSave(Context context, String filename, String key, String defaultStr) {
        String save = null;
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            save = saves.getString(key, defaultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;

    }

    public static int getLocalSaveInt(Context context, String filename, String key) {
        int save = 0;
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            save = saves.getInt(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;

    }


    public static void removeLocalSave(Context context, String filename, String savekey) {
        try {
            if (null != savekey) {
                SharedPreferences saves = context.getSharedPreferences(filename, 0);
                SharedPreferences.Editor edit = saves.edit();
                edit.remove(savekey);
                edit.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void clearLocalSave(Context context, String filename) {
        try {
            SharedPreferences saves = context.getSharedPreferences(filename, 0);
            SharedPreferences.Editor edit = saves.edit();
            edit.clear();
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据pkg包路径查看项目是否存在 查看pkg是否存在
     **/
    public static boolean checkPkgByComPath(Context context, String pkgname) {
        try {
            boolean flag = false;
            PackageManager manager = context.getPackageManager();
            List<PackageInfo> pkgList = manager.getInstalledPackages(0);
            for (int i = 0; i < pkgList.size(); i++) {
                PackageInfo pI = pkgList.get(i);

                if (pI.packageName.equalsIgnoreCase(pkgname)) {
                    return true;
                }
            }
        } catch (Throwable e) {
            LogUtils.e("Utils==checkPkgByComPath", e);
        }

        return false;
    }




    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache() {

        try {
            //清理Webview缓存数据库
            try {

                String[] arraystr = MyApp.getContext().databaseList();
                if (arraystr != null && arraystr.length > 0)
                    for (String str : arraystr) {
                        if (str.startsWith("webview")) {
                            LogUtils.info("删除的webview===>" + str);
                            MyApp.getContext().deleteDatabase(str);
                        }

                    }

            } catch (Throwable e) {
                LogUtils.e(TAG, e);
            }

            //WebView 缓存文件
            File appCacheDir = new File(MyApp.getContext().getFilesDir().getAbsolutePath() + "/webcache");
            LogUtils.info(TAG + "appCacheDir path=" + appCacheDir.getAbsolutePath());

            File webviewCacheDir = new File(MyApp.getContext().getCacheDir().getAbsolutePath() + "/webviewCache");
            LogUtils.info(TAG + "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

            File webviewCacheChromiumDir = new File(MyApp.getContext().getCacheDir().getAbsolutePath() + "/webviewCacheChromium");
            LogUtils.info(TAG + "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

            File webviewCacheChromiumStagingDir = new File(MyApp.getContext().getCacheDir().getAbsolutePath() + "/webviewCacheChromiumStaging");
            LogUtils.info(TAG + "webviewCacheChromiumStagingDir path=" + webviewCacheDir.getAbsolutePath());


            //删除webview 缓存目录
            if (webviewCacheDir.exists()) {
                deleteFile(webviewCacheDir);
            }
            //删除webview 缓存 缓存目录
            if (appCacheDir.exists()) {
                deleteFile(appCacheDir);
            }
            //删除webview 缓存 缓存目录
            if (webviewCacheChromiumDir.exists()) {
                deleteFile(appCacheDir);
            }
            //删除webview 缓存 缓存目录
            if (webviewCacheChromiumStagingDir.exists()) {
                deleteFile(appCacheDir);
            }
        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }

    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {

        try {
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
                file.delete();
            } else {
                LogUtils.e(TAG, "delete file no exists " + file.getAbsolutePath());
            }
        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }


    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //拨打电话
    public static void toIntentPhone(Context mContext, String tel) {
        try {
            Intent myIntentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
            mContext.startActivity(myIntentDial);
        } catch (Throwable e) {
            LogUtils.e("IntentUtil==toIntentPhone", e);
        }
    }


}
