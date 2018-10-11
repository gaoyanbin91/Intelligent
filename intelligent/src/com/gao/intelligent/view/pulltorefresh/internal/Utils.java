package com.gao.intelligent.view.pulltorefresh.internal;

import android.util.Log;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class Utils {
    static final String LOG_TAG = "PullToRefresh";

    public Utils() {
    }

    public static void warnDeprecation(String depreacted, String replacement) {
        Log.w("PullToRefresh", "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }
}

