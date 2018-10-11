package com.gao.intelligent.view.pulltorefresh.internal;

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

public class RotateLoadingLayout extends LoadingLayout {
    static final int ROTATION_ANIMATION_DURATION = 1200;
    private final Animation mRotateAnimation;
    private final Matrix mHeaderImageMatrix;
    private float mRotationPivotX;
    private float mRotationPivotY;
    private final boolean mRotateDrawableWhilePulling;

    public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        this.mRotateDrawableWhilePulling = attrs.getBoolean(R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);
        this.mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
        this.mHeaderImageMatrix = new Matrix();
        this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
        this.mRotateAnimation = new RotateAnimation(0.0F, 720.0F, 1, 0.5F, 1, 0.5F);
        this.mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        this.mRotateAnimation.setDuration(1200L);
        this.mRotateAnimation.setRepeatCount(-1);
        this.mRotateAnimation.setRepeatMode(1);
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            this.mRotationPivotX = (float) Math.round((float)imageDrawable.getIntrinsicWidth() / 2.0F);
            this.mRotationPivotY = (float) Math.round((float)imageDrawable.getIntrinsicHeight() / 2.0F);
        }

    }

    protected void onPullImpl(float scaleOfLayout) {
        float angle;
        if (this.mRotateDrawableWhilePulling) {
            angle = scaleOfLayout * 90.0F;
        } else {
            angle = Math.max(0.0F, Math.min(180.0F, scaleOfLayout * 360.0F - 180.0F));
        }

        this.mHeaderImageMatrix.setRotate(angle, this.mRotationPivotX, this.mRotationPivotY);
        this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
    }

    protected void refreshingImpl() {
        this.mHeaderImage.startAnimation(this.mRotateAnimation);
    }

    protected void resetImpl() {
        this.mHeaderImage.clearAnimation();
        this.resetImageRotation();
    }

    private void resetImageRotation() {
        if (null != this.mHeaderImageMatrix) {
            this.mHeaderImageMatrix.reset();
            this.mHeaderImage.setImageMatrix(this.mHeaderImageMatrix);
        }

    }

    protected void pullToRefreshImpl() {
    }

    protected void releaseToRefreshImpl() {
    }

    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_rotate;
    }
}

