package com.gao.intelligent.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.utils.AppConfig;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/4/25.
 * 描述: 闪屏页 显示logo和广告图片
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.rl_splash_content)
    View mView;//
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash_layout;
    }


    public void initView() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);//设置动画播放时长500毫秒
        mView.startAnimation(alphaAnimation);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setFillAfter(true);
        //设置动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                if (TextUtils.isEmpty(AppConfig.getInstance().getString("token",""))||
                        AppConfig.getInstance().getString("token","")==""){
                    //页面的跳转
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    //页面的跳转
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        hideNav();
    }
    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        |View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        |View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
