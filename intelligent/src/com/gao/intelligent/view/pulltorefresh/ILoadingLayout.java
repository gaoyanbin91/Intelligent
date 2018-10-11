package com.gao.intelligent.view.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */
public interface ILoadingLayout {
    void setLastUpdatedLabel(CharSequence var1);

    void setLoadingDrawable(Drawable var1);

    void setPullLabel(CharSequence var1);

    void setRefreshingLabel(CharSequence var1);

    void setReleaseLabel(CharSequence var1);

    void setTextTypeface(Typeface var1);
}
