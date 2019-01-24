package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Yanbin on 2018/11/1.
 * 描述:修改密码
 */
public class PasswordActivity extends BaseActivity {
    @BindView(R.id.btn_next)
    Button btnNext;//修改
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_old_pwd)
    EditText oldPwd;
    @BindView(R.id.et_repeat)
    EditText etRepeat;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pass_word;
    }

    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {


    }
    @OnClick(R.id.btn_next)
    public void onClick(){
        if (TextUtils.isEmpty(oldPwd.getText().toString())){
            ToastUtils.showShort("请输入原密码");
            return;
        }else if (!oldPwd.getText().toString().equals(AppConfig.getInstance().getString("password",""))){
            ToastUtils.showShort("原密码错误");
            return; 
        }else if (TextUtils.isEmpty(etPwd.getText().toString())||etRepeat.getText().toString().equals("")){
            ToastUtils.showShort("请输入新密码");
            return;
        }else if (!etRepeat.getText().toString().equals(etPwd.getText().toString())){
            ToastUtils.showShort("新密码输入不一致");
            return;
        }
        revisePsd();
        
        
        
    }
    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {

            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    exitApp();
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                ResultBean resultBean = JSON.parseObject(obj.toString(), ResultBean.class);
                LogUtils.d("修改密码", obj);
                if (resultBean.getResultCode()!=null&&resultBean.getResultCode().equals("1")){
                    ToastUtils.showShort("修改成功");
                    exitApp();
                    startActivity(new Intent(baseContext,LoginActivity.class));
                }else {
                    ToastUtils.showShort("修改失败");
                }

                break;
            case 10003:
                hideCustomProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }


    private void revisePsd() {
        showProgressDialog("修改中");
        HashMap<String, String> param = new HashMap<>();
        param.put("newPwd", etPwd.getText().toString());
        sendHttpPost(ApiService.REVISE_PASSWORD,param,12345);

    }
    @Override
    public View[] filterViewByIds() {
        View[] views = {etPwd, etRepeat,oldPwd};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_pwd, R.id.et_repeat,R.id.et_old_pwd};
        return ids;
    }

}
