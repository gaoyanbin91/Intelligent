package com.gao.intelligent.config;

import android.os.Environment;

/**
 * Created by siberiawolf on 15/10/23.
 */
public class Comm {

    //全局文件保存名称
    public static final String SAVEFILE = "interligent_save_file";
    public static final int reloadData = 7259;
    public static final int choicefragment_refresh_type = 15;
    public static final int tabactivity_refresh_type = 20;
    public static final String REGION_CODE = "regioncode";
    public static final String MAINLAND = "mainland";
    public static final String LANGUAGE = "language_type";
    public static final String SIMPLIFIED_CHINESE = "simplified_chinese";
    public static final String TRADITIONAL_CHINESE = "traditional_chinese";
    public static final String ENGLISH = "english";
    public static final int recipefragment_refresh_type = 17;
    public static final String CODE_201 = "201";
    public static final String CODE_401 = "401";//内容包含敏感信息
    public static final String CODE_402 = "402";//版本过低
    public static final String CODE_406 = "406";//图片不合格，有问题
    public static final String CODE_411 = "411";
    public static final String CODE_200 = "200";
    public static final String CODE_500 = "500";

    public static final String BASE_URL = "http://106.15.205.62:8087/appJkOrg/";//外网
    public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final int RESULT_ZOOM = 10003;
    public static final String SDCARD_IMG_ROOT = SDCARD_ROOT + "/interligent/img";
    public static final String SDCARD_APK_ROOT = SDCARD_ROOT + "/interligent/apk";
    public static final String SDCARD_VIDEO_ROOT = SDCARD_ROOT + "/interligent/video";
    public static String getEncodeKeys = BASE_URL + "auth/key.do";
    public static String getToken = BASE_URL + "auth/authKey.do";


    public static String upgreage_version =   "http://api.daydaycook.com.cn/version/updversion.do";
}
