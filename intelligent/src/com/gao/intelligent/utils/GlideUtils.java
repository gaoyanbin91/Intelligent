package com.gao.intelligent.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by siberiawolf on 16/6/7.
 */

public class GlideUtils {

    private static final String TAG = GlideUtils.class.getName();

    private static Drawable placeholderDrawable = MyApp.mInstance.getResources().getDrawable(R.mipmap.ic_launcher);

    private static Drawable errorDrawable = MyApp.mInstance.getResources().getDrawable(R.mipmap.ic_launcher);

    // bitmapTransform(new CropCircleTransformation(mContext)) // 圆形

    //bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0,RoundedCornersTransformation.CornerType.ALL))// 四角变圆角

    public static void load(Object url, int placeholderRes, int errorRes, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                    .placeholder(placeholderRes)
                    .error(errorRes)
                    .listener(requestListener)
                    .into(imageView);
        } catch (Throwable e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void load(Object url, final ImageView imageView) {
        try {

            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                    .error(errorDrawable)
                    .listener(requestListener)
                    .into(imageView);
        } catch (Throwable e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void load(Object url, ImageView imageView, int defImg) {
        try {
            int h = imageView.getMeasuredHeight();
            int w = imageView.getMeasuredWidth();
            if (h > 0 && w > 0) {
                Glide.with(imageView.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                        .placeholder(defImg)
                        .centerCrop()
                        .error(defImg)
                        .listener(requestListener)
                        .override(w, h)
                        .into(imageView);
            } else {
                Glide.with(imageView.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                        .placeholder(defImg)
                        .centerCrop()
                        .error(defImg)
                        .listener(requestListener)
                        .into(imageView);
            }


        } catch (Throwable e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void load(Object url, int rounded, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                    .placeholder(placeholderDrawable)
                    .bitmapTransform(new RoundedCornersTransformation(MyApp.mInstance, rounded, 0, RoundedCornersTransformation.CornerType.ALL))// 四角变圆角
                    .error(errorDrawable)
                    .listener(requestListener)
                    .into(imageView);
        } catch (Throwable e) {
          Log.e(TAG, e.toString());
        }
    }

    public static void loadCropCircle(Object url, int placeholderRes, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                    .placeholder(placeholderRes)
                    .bitmapTransform(new CropCircleTransformation(MyApp.mInstance)) // 圆形
                    .error(placeholderRes)
                    .listener(requestListener)
                    .into(imageView);
        } catch (Throwable e) {
        //    LibraryLogUtils.e(TAG, e);
        }
    }

    public static void loadRoundedCorners(Object url, int radius, RoundedCornersTransformation.CornerType cornerType, ImageView imageView) {
        try {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                    .placeholder(placeholderDrawable)
                    .error(errorDrawable)
                    .listener(requestListener)
                    .bitmapTransform(new RoundedCornersTransformation(MyApp.mInstance, radius, 0, cornerType))
                    .into(imageView);
        } catch (Throwable e) {
            Log.e(TAG, e.toString());
        }
    }

//    public static void loadMyCustomRoundedCorners(Object url, int radius, ImageView imageView) {
//        try {
//            Glide.with(Global.mContext)
//                    .load(url)
//                    .centerCrop()
//                    .placeholder(placeholderDrawable)
//                    .error(errorDrawable)
//                    .listener(requestListener)
//                    .override(imageView.getWidth(), imageView.getHeight())
//                    .bitmapTransform(new GlideRoundTransform(Global.mContext, radius))
//                    .into(imageView);
//        } catch (Throwable e) {
//            LibraryLogUtils.e(TAG, e);
//        }
//    }


    // 监听图片的加载 回调
    private static RequestListener<Object, GlideDrawable> requestListener = new RequestListener<Object, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
            if (e != null)
                Log.e(TAG, e.toString());
         //   LogUtils.info(TAG + "==e==>" + e + "==model==>" + model + "isFirstResource==>" + isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        //    LogUtils.info(model.toString() + isFromMemoryCache + isFirstResource);

            return false;
        }
    };


}
