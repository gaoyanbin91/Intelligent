package com.gao.intelligent.view.pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.ILoadingLayout;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

@SuppressLint({"ViewConstructor"})
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {
    static final String LOG_TAG = "PullToRefresh-LoadingLayout";
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    private FrameLayout mInnerLayout;
    protected final ImageView mHeaderImage;
    protected final ProgressBar mHeaderProgress;
    private boolean mUseIntrinsicAnimation;
    private final TextView mHeaderText;
    private final TextView mSubHeaderText;
    protected final PullToRefreshBase.Mode mMode;
    protected final PullToRefreshBase.Orientation mScrollDirection;
    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    public LoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context);
        this.mMode = mode;
        this.mScrollDirection = scrollDirection;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderLayout)) {
            int layoutres = attrs.getResourceId(R.styleable.PullToRefresh_ptrHeaderLayout, R.layout.pull_to_refresh_header_vertical);
            LayoutInflater.from(context).inflate(layoutres, this);
        } else {
            switch(scrollDirection) {
                case HORIZONTAL:
                    LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_horizontal, this);
                    break;
                case VERTICAL:
                default:
                    LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical, this);
            }
        }

        this.mInnerLayout = (FrameLayout)this.findViewById(R.id.fl_inner);
        this.mHeaderText = (TextView)this.mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        this.mHeaderProgress = (ProgressBar)this.mInnerLayout.findViewById(R.id.pull_to_refresh_progress);
        this.mSubHeaderText = (TextView)this.mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        this.mHeaderImage = (ImageView)this.mInnerLayout.findViewById(R.id.pull_to_refresh_image);
        FrameLayout.LayoutParams lp = (LayoutParams)this.mInnerLayout.getLayoutParams();
        switch(mode) {
            case PULL_FROM_END:
                lp.gravity = scrollDirection == PullToRefreshBase.Orientation.VERTICAL ? 48 : 3;
                this.mPullLabel = context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_from_bottom_release_label);
                break;
            case PULL_FROM_START:
            default:
                lp.gravity = scrollDirection == PullToRefreshBase.Orientation.VERTICAL ? 80 : 5;
                this.mPullLabel = context.getString(R.string.pull_to_refresh_pull_label);
                this.mRefreshingLabel = context.getString(R.string.pull_to_refresh_refreshing_label);
                this.mReleaseLabel = context.getString(R.string.pull_to_refresh_release_label);
        }

        Drawable imageDrawable;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderBackground)) {
            imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrHeaderBackground);
            if (null != imageDrawable) {
                ViewCompat.setBackground(this, imageDrawable);
            }
        }

        TypedValue styleID;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance)) {
            styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrHeaderTextAppearance, styleID);
            this.setTextAppearance(styleID.data);
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance)) {
            styleID = new TypedValue();
            attrs.getValue(R.styleable.PullToRefresh_ptrSubHeaderTextAppearance, styleID);
            this.setSubTextAppearance(styleID.data);
        }

        ColorStateList colors;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
            colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderTextColor);
            if (null != colors) {
                this.setTextColor(colors);
            }
        }

        if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderSubTextColor)) {
            colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderSubTextColor);
            if (null != colors) {
                this.setSubTextColor(colors);
            }
        }

        imageDrawable = null;
        if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawable)) {
            imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawable);
        }

        switch(mode) {
            case PULL_FROM_END:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableEnd)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableEnd);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableBottom)) {
                    Utils.warnDeprecation("ptrDrawableBottom", "ptrDrawableEnd");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableBottom);
                }
                break;
            case PULL_FROM_START:
            default:
                if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableStart)) {
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableStart);
                } else if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawableTop)) {
                    Utils.warnDeprecation("ptrDrawableTop", "ptrDrawableStart");
                    imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawableTop);
                }
        }

        if (null == imageDrawable) {
            imageDrawable = context.getResources().getDrawable(this.getDefaultDrawableResId());
        }

        this.setLoadingDrawable(imageDrawable);
        this.reset();
    }

    public final void setHeight(int height) {
        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.height = height;
        this.requestLayout();
    }

    public final void setWidth(int width) {
        android.view.ViewGroup.LayoutParams lp = this.getLayoutParams();
        lp.width = width;
        this.requestLayout();
    }

    public final int getContentSize() {
        switch(this.mScrollDirection) {
            case HORIZONTAL:
                return this.mInnerLayout.getWidth();
            case VERTICAL:
            default:
                return this.mInnerLayout.getHeight();
        }
    }

    public final void hideAllViews() {
        if (0 == this.mHeaderText.getVisibility()) {
            this.mHeaderText.setVisibility(INVISIBLE);
        }

        if (0 == this.mHeaderProgress.getVisibility()) {
            this.mHeaderProgress.setVisibility(INVISIBLE);
        }

        if (0 == this.mHeaderImage.getVisibility()) {
            this.mHeaderImage.setVisibility(INVISIBLE);
        }

        if (0 == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(INVISIBLE);
        }

    }

    public final void onPull(float scaleOfLayout) {
        if (!this.mUseIntrinsicAnimation) {
            this.onPullImpl(scaleOfLayout);
        }

    }

    public final void pullToRefresh() {
        if (null != this.mHeaderText) {
            this.mHeaderText.setText(this.mPullLabel);
        }

        this.pullToRefreshImpl();
    }

    public final void refreshing() {
        if (null != this.mHeaderText) {
            this.mHeaderText.setText(this.mRefreshingLabel);
        }

        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable)this.mHeaderImage.getDrawable()).start();
        } else {
            this.refreshingImpl();
        }

        if (null != this.mSubHeaderText) {
            this.mSubHeaderText.setVisibility(8);
        }

    }

    public final void releaseToRefresh() {
        if (null != this.mHeaderText) {
            this.mHeaderText.setText(this.mReleaseLabel);
        }

        this.releaseToRefreshImpl();
    }

    public final void reset() {
        if (null != this.mHeaderText) {
            this.mHeaderText.setText(this.mPullLabel);
        }

        this.mHeaderImage.setVisibility(VISIBLE);
        if (this.mUseIntrinsicAnimation) {
            ((AnimationDrawable)this.mHeaderImage.getDrawable()).stop();
        } else {
            this.resetImpl();
        }

        if (null != this.mSubHeaderText) {
            if (TextUtils.isEmpty(this.mSubHeaderText.getText())) {
                this.mSubHeaderText.setVisibility(GONE);
            } else {
                this.mSubHeaderText.setVisibility(VISIBLE);
            }
        }

    }

    public void setLastUpdatedLabel(CharSequence label) {
        this.setSubHeaderText(label);
    }

    public final void setLoadingDrawable(Drawable imageDrawable) {
        this.mHeaderImage.setImageDrawable(imageDrawable);
        this.mUseIntrinsicAnimation = imageDrawable instanceof AnimationDrawable;
        this.onLoadingDrawableSet(imageDrawable);
    }

    public void setPullLabel(CharSequence pullLabel) {
        this.mPullLabel = pullLabel;
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        this.mRefreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(CharSequence releaseLabel) {
        this.mReleaseLabel = releaseLabel;
    }

    public void setTextTypeface(Typeface tf) {
        this.mHeaderText.setTypeface(tf);
    }

    public final void showInvisibleViews() {
        if (4 == this.mHeaderText.getVisibility()) {
            this.mHeaderText.setVisibility(VISIBLE);
        }

        if (4 == this.mHeaderProgress.getVisibility()) {
            this.mHeaderProgress.setVisibility(VISIBLE);
        }

        if (4 == this.mHeaderImage.getVisibility()) {
            this.mHeaderImage.setVisibility(VISIBLE);
        }

        if (4 == this.mSubHeaderText.getVisibility()) {
            this.mSubHeaderText.setVisibility(VISIBLE);
        }

    }

    protected abstract int getDefaultDrawableResId();

    protected abstract void onLoadingDrawableSet(Drawable var1);

    protected abstract void onPullImpl(float var1);

    protected abstract void pullToRefreshImpl();

    protected abstract void refreshingImpl();

    protected abstract void releaseToRefreshImpl();

    protected abstract void resetImpl();

    private void setSubHeaderText(CharSequence label) {
        if (null != this.mSubHeaderText) {
            if (TextUtils.isEmpty(label)) {
                this.mSubHeaderText.setVisibility(GONE);
            } else {
                this.mSubHeaderText.setText(label);
                if (8 == this.mSubHeaderText.getVisibility()) {
                    this.mSubHeaderText.setVisibility(VISIBLE);
                }
            }
        }

    }

    private void setSubTextAppearance(int value) {
        if (null != this.mSubHeaderText) {
            this.mSubHeaderText.setTextAppearance(this.getContext(), value);
        }

    }

    private void setSubTextColor(ColorStateList color) {
        if (null != this.mSubHeaderText) {
            this.mSubHeaderText.setTextColor(color);
        }

    }

    private void setTextAppearance(int value) {
        if (null != this.mHeaderText) {
            this.mHeaderText.setTextAppearance(this.getContext(), value);
        }

        if (null != this.mSubHeaderText) {
            this.mSubHeaderText.setTextAppearance(this.getContext(), value);
        }

    }

    private void setTextColor(ColorStateList color) {
        if (null != this.mHeaderText) {
            this.mHeaderText.setTextColor(color);
        }

        if (null != this.mSubHeaderText) {
            this.mSubHeaderText.setTextColor(color);
        }

    }
}
