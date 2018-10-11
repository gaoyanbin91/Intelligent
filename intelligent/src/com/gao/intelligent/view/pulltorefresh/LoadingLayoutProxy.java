package com.gao.intelligent.view.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.gao.intelligent.view.pulltorefresh.internal.LoadingLayout;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public class LoadingLayoutProxy implements ILoadingLayout {
    private final HashSet<LoadingLayout> mLoadingLayouts = new HashSet();

    LoadingLayoutProxy() {
    }

    public void addLayout(LoadingLayout layout) {
        if (null != layout) {
            this.mLoadingLayouts.add(layout);
        }

    }

    public void setLastUpdatedLabel(CharSequence label) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setLastUpdatedLabel(label);
        }

    }

    public void setLoadingDrawable(Drawable drawable) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setLoadingDrawable(drawable);
        }

    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setRefreshingLabel(refreshingLabel);
        }

    }

    public void setPullLabel(CharSequence label) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setPullLabel(label);
        }

    }

    public void setReleaseLabel(CharSequence label) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setReleaseLabel(label);
        }

    }

    public void setTextTypeface(Typeface tf) {
        Iterator var2 = this.mLoadingLayouts.iterator();

        while(var2.hasNext()) {
            LoadingLayout layout = (LoadingLayout)var2.next();
            layout.setTextTypeface(tf);
        }

    }
}

