package com.gao.intelligent.view.pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.gao.intelligent.R;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase.Mode;
import com.gao.intelligent.view.pulltorefresh.PullToRefreshBase.Orientation;


/**
 * Created by gaoyanbin on 2018/4/16.
 * 描述:
 */

@SuppressLint({"ViewConstructor"})
public class FlipLoadingLayout extends LoadingLayout {
    static final int FLIP_ANIMATION_DURATION = 150;
    private final Animation mRotateAnimation;
    private final Animation mResetRotateAnimation;

    public FlipLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        int rotateAngle = mode == Mode.PULL_FROM_START ? -180 : 180;
        this.mRotateAnimation = new RotateAnimation(0.0F, (float)rotateAngle, 1, 0.5F, 1, 0.5F);
        this.mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mRotateAnimation.setDuration(150L);
        this.mRotateAnimation.setFillAfter(true);
        this.mResetRotateAnimation = new RotateAnimation((float)rotateAngle, 0.0F, 1, 0.5F, 1, 0.5F);
        this.mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mResetRotateAnimation.setDuration(150L);
        this.mResetRotateAnimation.setFillAfter(true);
    }

    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            int dHeight = imageDrawable.getIntrinsicHeight();
            int dWidth = imageDrawable.getIntrinsicWidth();
            LayoutParams lp = (LayoutParams) this.mHeaderImage.getLayoutParams();
            lp.width = lp.height = Math.max(dHeight, dWidth);
            this.mHeaderImage.requestLayout();
            this.mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
            Matrix matrix = new Matrix();
            matrix.postTranslate((float)(lp.width - dWidth) / 2.0F, (float)(lp.height - dHeight) / 2.0F);
            matrix.postRotate(this.getDrawableRotationAngle(), (float)lp.width / 2.0F, (float)lp.height / 2.0F);
            this.mHeaderImage.setImageMatrix(matrix);
        }

    }

    protected void onPullImpl(float scaleOfLayout) {
    }

    protected void pullToRefreshImpl() {
        if (this.mRotateAnimation == this.mHeaderImage.getAnimation()) {
            this.mHeaderImage.startAnimation(this.mResetRotateAnimation);
        }

    }

    protected void refreshingImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderImage.setVisibility(INVISIBLE);
        this.mHeaderProgress.setVisibility(VISIBLE);
    }

    protected void releaseToRefreshImpl() {
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    protected void resetImpl() {
        this.mHeaderImage.clearAnimation();
        this.mHeaderProgress.setVisibility(GONE);
        this.mHeaderImage.setVisibility(VISIBLE);
    }

    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_flip;
    }

    private float getDrawableRotationAngle() {
        float angle = 0.0F;
        switch(this.mMode) {
            case PULL_FROM_END:
                if (this.mScrollDirection == Orientation.HORIZONTAL) {
                    angle = 90.0F;
                } else {
                    angle = 180.0F;
                }
                break;
            case PULL_FROM_START:
                if (this.mScrollDirection == Orientation.HORIZONTAL) {
                    angle = 270.0F;
                }
        }

        return angle;
    }
}

