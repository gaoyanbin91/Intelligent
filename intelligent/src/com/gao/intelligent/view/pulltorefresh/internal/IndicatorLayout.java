package com.gao.intelligent.view.pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

@SuppressLint({"ViewConstructor"})
public class IndicatorLayout extends FrameLayout implements Animation.AnimationListener {
    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;
    private Animation mInAnim;
    private Animation mOutAnim;
    private ImageView mArrowImageView;
    private final Animation mRotateAnimation;
    private final Animation mResetRotateAnimation;

    public IndicatorLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        this.mArrowImageView = new ImageView(context);
        Drawable arrowD = this.getResources().getDrawable(R.drawable.indicator_arrow);
        this.mArrowImageView.setImageDrawable(arrowD);
        int padding = this.getResources().getDimensionPixelSize( R.dimen.indicator_internal_padding);
        this.mArrowImageView.setPadding(padding, padding, padding, padding);
        this.addView(this.mArrowImageView);
        int inAnimResId;
        int outAnimResId;
        switch (mode) {
            case PULL_FROM_END:
                inAnimResId = R.anim.slide_in_from_bottom;
                outAnimResId = R.anim.slide_out_to_bottom;
                this.setBackgroundResource(R.drawable.indicator_bg_bottom);
                this.mArrowImageView.setScaleType(ImageView.ScaleType.MATRIX);
                Matrix matrix = new Matrix();
                matrix.setRotate(180.0F, (float) arrowD.getIntrinsicWidth() / 2.0F, (float) arrowD.getIntrinsicHeight() / 2.0F);
                this.mArrowImageView.setImageMatrix(matrix);
                break;
            case PULL_FROM_START:
            default:
                inAnimResId = R.anim.slide_in_from_top;
                outAnimResId = R.anim.slide_out_to_top;
                this.setBackgroundResource(R.drawable.indicator_bg_top);
        }

        this.mInAnim = AnimationUtils.loadAnimation(context, inAnimResId);
        this.mInAnim.setAnimationListener(this);
        this.mOutAnim = AnimationUtils.loadAnimation(context, outAnimResId);
        this.mOutAnim.setAnimationListener(this);
        Interpolator interpolator = new LinearInterpolator();
        this.mRotateAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
        this.mRotateAnimation.setInterpolator(interpolator);
        this.mRotateAnimation.setDuration(150L);
        this.mRotateAnimation.setFillAfter(true);
        this.mResetRotateAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
        this.mResetRotateAnimation.setInterpolator(interpolator);
        this.mResetRotateAnimation.setDuration(150L);
        this.mResetRotateAnimation.setFillAfter(true);
    }

    public final boolean isVisible() {
        Animation currentAnim = this.getAnimation();
        if (null != currentAnim) {
            return this.mInAnim == currentAnim;
        } else {
            return this.getVisibility() == 0;
        }
    }

    public void hide() {
        this.startAnimation(this.mOutAnim);
    }

    public void show() {
        this.mArrowImageView.clearAnimation();
        this.startAnimation(this.mInAnim);
    }

    public void onAnimationEnd(Animation animation) {
        if (animation == this.mOutAnim) {
            this.mArrowImageView.clearAnimation();
            this.setVisibility(8);
        } else if (animation == this.mInAnim) {
            this.setVisibility(0);
        }

        this.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
        this.setVisibility(0);
    }

    public void releaseToRefresh() {
        this.mArrowImageView.startAnimation(this.mRotateAnimation);
    }

    public void pullToRefresh() {
        this.mArrowImageView.startAnimation(this.mResetRotateAnimation);
    }
}

