package com.gao.intelligent.view.pulltorefresh.internal;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.View;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class ViewCompat {

    public ViewCompat() {
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16L);
        }

    }

    public static void setBackground(View view, Drawable background) {
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }

    }

    public static void setLayerType(View view, int layerType) {
        if (VERSION.SDK_INT >= 11) {
            ViewCompat.SDK11.setLayerType(view, layerType);
        }

    }

    @TargetApi(16)
    static class SDK16 {
        SDK16() {
        }

        public static void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        public static void setBackground(View view, Drawable background) {
            view.setBackground(background);
        }
    }

    @TargetApi(11)
    static class SDK11 {
        SDK11() {
        }

        public static void setLayerType(View view, int layerType) {
            view.setLayerType(layerType, (Paint)null);
        }
    }
}

