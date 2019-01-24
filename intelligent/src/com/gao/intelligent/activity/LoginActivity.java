package com.gao.intelligent.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.LoginBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/27.
 * 描述:登录页面
 */
public class LoginActivity extends BaseActivity implements TextWatcher {

    private final static String TAG = LoginActivity.class.getName();

    @BindView(R.id.mobile_edittext)
    EditText mobileEditText;//登录手机号
    @BindView(R.id.pwd_edittext)
    EditText pwdEditText;//登录密码
    @BindView(R.id.clear_pwd_imageview)
    ImageView clearPwdImageView;//清除输入密码
    @BindView(R.id.phone_delte_imageview)
    ImageView phoneDeleteImageView;//清除输入密码
    @BindView(R.id.showpwd_imageview)
    ImageView showPwdImageView;//显示密码
    @BindView(R.id.login_button)
    Button loginButton;//登录按钮
    LoginBean loginBean;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Button btndelete,btnSave;
    private EditText edtCodeNum;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_login;
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {

            case 10004:
                hideCustomProgressDialog();
                loginButton.setEnabled(true);
                LogUtils.d("Success", obj);
                Gson gson = new Gson();
                loginBean = gson.fromJson(obj.toString(), LoginBean.class);
                //LoginBean loginBean = JSON.parseObject(obj.toString(), LoginBean.class);
//                ToastUtils.showShort(loginBean.getSuccess());
//                LogUtils.i("Success", loginBean.getDesc());
                if (!TextUtils.isEmpty(loginBean.getCurrentAuthority())) {
                    AppConfig.getInstance().putString("token", loginBean.getToken());
                    AppConfig.getInstance().putString("authority", loginBean.getCurrentAuthority());
                    if (!TextUtils.isEmpty(loginBean.getType())){
                        AppConfig.getInstance().putString("type", loginBean.getType());
                    }
                    ToastUtils.showShort(loginBean.getStatus());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
               else {
                    if(loginBean.getType()!=null&&loginBean.getType().equals("2")){
                        ToastUtils.showShort( loginBean.getStatus());
                        showDialog();

                    }else {
                        ToastUtils.showShort(loginBean.getStatus());
                    }
                }
                //tvF.setText( obj.toString());
                break;
            case 10003:
                hideCustomProgressDialog();
                loginButton.setEnabled(true);
                //        ToastUtils.showShort(obj.toString());
                ToastUtils.showShort(obj.toString());
                break;
        }
    }
    public void showDialog() {
        final Dialog alertDialog = new Dialog( baseContext);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setContentView(R.layout.login_code_dialog);
        alertDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        btndelete = (Button) window.findViewById(R.id.btn_delete);
        btnSave = (Button) window.findViewById(R.id.btn_submit);
        btnSave.setEnabled(true);
        edtCodeNum = (EditText) window.findViewById(R.id.edtCodeNum);

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtCodeNum.getText().toString())){
                    showProgressDialog("重新登陆...");
                    HashMap<String, String> loginMap = new HashMap<>();
                    loginMap.put("username", mobileEditText.getText().toString());
                    loginMap.put("password", pwdEditText.getText().toString());
                    loginMap.put("deviceId", AppConfig.getInstance().getString("deviceId",""));
                    loginMap.put("verification",  edtCodeNum.getText().toString());
                    sendHttpPost(ApiService.LOGIN_UP, loginMap, 10000);
                    alertDialog.dismiss();
                }else {
                    ToastUtils.showShort("验证码不能为空");
                }
            }
        });
    }
    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        if (AppConfig.getInstance().getString("username", "") != null) {
            mobileEditText.setText(AppConfig.getInstance().getString("username", ""));
        }
        pwdEditText.addTextChangedListener(this);
        pwdEditText.setHint(getResources().getString(R.string.login_input_password));
        pwdEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        pwdEditText.setFilters(filters);

        showPwdImageView.setVisibility(View.VISIBLE);

        mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim().replace(" ", "");
                if (!TextUtils.isEmpty(str) && mobileEditText.hasFocus()) {
                    phoneDeleteImageView.setVisibility(View.VISIBLE);
                } else {
                    phoneDeleteImageView.setVisibility(View.GONE);
                }
            }
        });
        mobileEditText.addTextChangedListener(this);


    }

    @OnClick({R.id.pwd_forget_textview, R.id.login_button, R.id.phone_delte_imageview , R.id.clear_pwd_imageview, R.id.showpwd_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pwd_forget_textview:
                //    showProgressDialog();
                //   showProgressDialog("正在登录..");
                startActivity(new Intent(baseContext, ForgetPsdActivity.class));
//                ToastUtils.showShort("忘记密码");
                break;

            case R.id.phone_delte_imageview://清空输入账号
                mobileEditText.setText("");

                break;
            case R.id.clear_pwd_imageview://清空密码
                pwdEditText.setText(String.valueOf(""));
                break;
            case R.id.login_button://登录按钮
//                showDialog();
                loginOn();

                break;
            case R.id.showpwd_imageview://显示或隐藏密码
                if (pwdEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    showPwdImageView.setImageResource(R.mipmap.btn_password_display);
                    pwdEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwdEditText.setSelection(pwdEditText.getText().length());
                } else {
                    showPwdImageView.setImageResource(R.mipmap.btn_password_nodisplay);
                    pwdEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pwdEditText.setSelection(pwdEditText.getText().length());
                }

                break;
        }
    }

    /**
     * 登录
     */
    private void loginOn() {
        loginButton.setEnabled(false);
        showProgressDialog("正在登陆...");
        HashMap<String, String> loginMap = new HashMap<>();
        loginMap.put("username", mobileEditText.getText().toString());
        loginMap.put("password", pwdEditText.getText().toString());
        loginMap.put("deviceId", AppConfig.getInstance().getString("deviceId",""));
        sendHttpPost(ApiService.LOGIN_UP, loginMap, 10000);
        AppConfig.getInstance().putString("username", mobileEditText.getText().toString());
        AppConfig.getInstance().putString("password", pwdEditText.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mobileEditText.length() > 0 && pwdEditText.length() > 0) {
            loginButton.setClickable(true);
            loginButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_orange);
        } else {
            loginButton.setClickable(false);
            loginButton.setBackgroundResource(R.drawable.background_newlogin_gobutton_gray);
        }

        if (pwdEditText.length() > 0) {
            clearPwdImageView.setVisibility(View.VISIBLE);
        } else {
            clearPwdImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mobileEditText, pwdEditText};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.mobile_edittext, R.id.pwd_edittext};
        return ids;
    }
}
