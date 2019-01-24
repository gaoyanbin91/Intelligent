package com.gao.intelligent.activity;

import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.okhttp.DownloadUtil;
import com.gao.intelligent.view.ZoomImageView;
import com.yixia.camera.MediaRecorderBase;
import com.zhaoshuang.weixinrecorded.MyVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/5/29.
 * 描述:查看大图
 */


public class ImageActivity extends BaseActivity {
    @BindView(R.id.iv_big_image)
    ZoomImageView mImageView;
    @BindView(R.id.vv_play)
    MyVideoView vv_play;
    @BindView(R.id.rl_show)
    RelativeLayout rl_show;
    @BindView(R.id.mTitle)
    TextView mTitle;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    public void initData() {
        LogUtils.i("图片url", getIntent().getStringExtra("imageUrl"));
        if (getIntent().getStringExtra("flag").equals("image")) {
            mTitle.setText("图片");
            mImageView.setVisibility(View.VISIBLE);
            vv_play.setVisibility(View.GONE);
            Glide.with(this)
                    .load(getIntent().getStringExtra("imageUrl"))
                    // .placeholder(R.mipmap.icon_loading) //占位符 也就是加载中的图片，可放个gif
                    .into(mImageView);
        }
        if (getIntent().getStringExtra("flag").equals("video")) {
            mImageView.setVisibility(View.GONE);
            mTitle.setText("视频");
            //  vv_play.setVideoPath(videoPath);
            String videoUrl = getIntent().getStringExtra("videoUrl");
            DownloadUtil.getInstance().download(videoUrl, Comm.SDCARD_VIDEO_ROOT, new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(String path) {
                    hideProgressDialog();
                    LogUtils.i("视频", path);
                    vv_play.setVideoPath(path);
                    vv_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            vv_play.setLooping(true);
                            vv_play.start();
                            float widthF = vv_play.getVideoWidth() * 1f / MediaRecorderBase.VIDEO_HEIGHT;
                            float heightF = vv_play.getVideoHeight() * 1f / MediaRecorderBase.VIDEO_WIDTH;
                            ViewGroup.LayoutParams layoutParams = vv_play.getLayoutParams();
                            layoutParams.width = (int) (rl_show.getWidth() * widthF);
                            layoutParams.height = (int) (rl_show.getHeight() * heightF);
                            vv_play.setLayoutParams(layoutParams);
                        }
                    });
                }

                @Override
                public void onDownloading(int progress) {
                    showProgressDialog("视频缓存中...");
                    LogUtils.i("视频下载", progress + "");
                }

                @Override
                public void onDownloadFailed() {
                    hideCustomProgressDialog();
                    ToastUtils.showShort("下载失败");

                }
            });
        }
    }

    @OnClick({R.id.toolbar, R.id.iv_big_image})
    public void onFinish() {
        finish();
    }
}
