package com.gao.intelligent.config;

import android.net.Uri;

import com.gao.intelligent.MyApp;
import com.gao.intelligent.model.CheckModel;
import com.gao.intelligent.utils.Utils;


/**
 * Created by siberiawolf on 15/10/23.
 */
public class Global {
    public static String VERSION = "2.3.3";
    public static String IMIE;
    public static int LOGTYPE = 5;
    public static int LOGTOFILE = 0;
    public static Uri uritempFile;
    public static int mScreenWidth;
    public static int mScreenHeight;
    public static CheckModel mCheckModel;
    public static String LANGUAGE_TYPE = Utils.getLocalSave(MyApp.getContext(), Comm.SAVEFILE, Comm.LANGUAGE);

    public static String tokenId;
}
