/*
package com.gao.intelligent.activity.sound;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.zhaoshuang.weixinrecorded.MyVideoView;

import java.util.ArrayList;

import butterknife.BindView;

*/
/**
 * Created by gaoyanbin on 2018/6/8.
 *//*


public abstract class ActivityCommon extends BaseActivity {
    @BindView(R.id.btn_sound)
     Button btn_sound;
    @BindView(R.id.edt_part_content)
    EditText mEditPartContents;
    @BindView(R.id.edt_problem_name)
    EditText edtProblemName;//问题名称
    @BindView(R.id.tex_classify)
    TextView texClassify;//问题分类
    @BindView(R.id.edt_code_num)
    EditText edtCodeNum;//出厂编码
    @BindView(R.id.edt_phone_num)
    EditText edtPhoneNum;//手机号码
    @BindView(R.id.rg_level)
    RadioGroup rgLevel;//优先级

    @BindView(R.id.ll_imgv)
    LinearLayout mLinearLayout;
    @BindView(R.id.txv_submit)
    TextView txvSubmit;//提交
    @BindView(R.id.iv_vedio)
    ImageView ivVedio;
    @BindView(R.id.vv_play)
    MyVideoView vv_play;
    @BindView(R.id.rl_show)
    RelativeLayout rl_show;
    @BindView(R.id.ll_classfiy)
    LinearLayout llClassFiy;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    protected Handler handler;


    protected int layout = R.layout.activity_sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setStrictMode();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      //  setContentView(layout);
        initView();
        handler = new Handler() {

            */
/*
             * @param msg
             *//*

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        Logger.setHandler(handler);
        initPermission();
        initRecog();
    }

    @Override
    protected int provideContentViewId() {
        InFileStream.setContext(this);
        return layout;
    }

    protected abstract void initRecog();

    protected void handleMsg(Message msg) {

    }


//    @OnClick(R.id.toolbar)
//    public void onFinish(){
//        finish();
//    }
    */
/**
     * android 6.0 以上需要动态申请权限
     *//*

    private void initPermission() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }
}
*/
