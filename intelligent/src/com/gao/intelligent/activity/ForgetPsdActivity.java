package com.gao.intelligent.activity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.utils.CountDownTimerUtils;
import com.gao.intelligent.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Yanbin on 2018/11/1.
 * 描述:忘记密码
 */
public class ForgetPsdActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.register_phone)
    TextView registerPhone;
    @BindView(R.id.register_msg)
    EditText registerMsg;
    @BindView(R.id.register_msg_bt)
    TextView registerMsgBt;

    @BindView(R.id.pwd_edittext)
    EditText registerPwd;
    @BindView(R.id.clear_pwd_imageview)
    ImageView clearPwdImageView;//清除输入密码
    @BindView(R.id.showpwd_imageview)
    ImageView showPwdImageView;//显示密码

    @BindView(R.id.pwd_edittext_2)
    EditText registerPwd2;
    @BindView(R.id.clear_pwd_imageview_2)
    ImageView clearPwdImageView2;//清除输入密码
    @BindView(R.id.showpwd_imageview_2)
    ImageView showPwdImageView2;//显示密码


    @BindView(R.id.register_bt)
    TextView registerBt;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    CountDownTimerUtils mCountDownTimerUtils;//倒计时工具

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_forget_pwd;
    }


    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //输入类型
        registerPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //输入类型
        registerPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //最大输入长度
        registerPwd2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //最大输入长度

        mCountDownTimerUtils = new CountDownTimerUtils(registerMsgBt, 60000, 1000);
        showPwdImageView.setVisibility(View.VISIBLE);
        showPwdImageView2.setVisibility(View.VISIBLE);
        registerPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str1 = s.toString().trim().replace(" ", "");
                if (!TextUtils.isEmpty(str1) && registerPwd.hasFocus()) {
                    clearPwdImageView.setVisibility(View.VISIBLE);
                } else {
                    clearPwdImageView.setVisibility(View.GONE);
                }
            }
        });
        registerPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim().replace(" ", "");
                if (!TextUtils.isEmpty(str) && registerPwd2.hasFocus()) {
                    clearPwdImageView2.setVisibility(View.VISIBLE);
                } else {
                    clearPwdImageView2.setVisibility(View.GONE);
                }
            }
        });
        registerMsg.addTextChangedListener(this);
        registerPwd.addTextChangedListener(this);
        registerPwd2.addTextChangedListener(this);
    }

    @OnClick({R.id.register_msg_bt, R.id.register_bt, R.id.clear_pwd_imageview, R.id.showpwd_imageview,
            R.id.clear_pwd_imageview_2, R.id.showpwd_imageview_2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_pwd_imageview://清空密码
                registerPwd.setText(String.valueOf(""));
                break;
            case R.id.showpwd_imageview://显示或隐藏密码
                if (registerPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    showPwdImageView.setImageResource(R.mipmap.btn_password_display);
                    registerPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    registerPwd.setSelection(registerPwd.getText().length());
                } else {
                    showPwdImageView.setImageResource(R.mipmap.btn_password_nodisplay);
                    registerPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    registerPwd.setSelection(registerPwd.getText().length());
                }

                break;
            case R.id.clear_pwd_imageview_2://清空密码
                registerPwd.setText(String.valueOf(""));
                break;
            case R.id.showpwd_imageview_2://显示或隐藏密码
                if (registerPwd2.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    showPwdImageView2.setImageResource(R.mipmap.btn_password_display);
                    registerPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    registerPwd2.setSelection(registerPwd2.getText().length());
                } else {
                    showPwdImageView2.setImageResource(R.mipmap.btn_password_nodisplay);
                    registerPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    registerPwd2.setSelection(registerPwd2.getText().length());
                }

                break;
            case R.id.register_msg_bt:
                // setSmsObserver();
                String phone = registerPhone.getText().toString().trim();

                if (!UIUtils.isMobileNO(phone)) {
                    UIUtils.showToast("手机号格式不正确");
                    return;
                }
                if (UIUtils.isMobileNO(registerPhone.getText().toString())) {
                    mCountDownTimerUtils.start();
                }
//                phoneCode();

                break;

            case R.id.register_bt:
                if (UIUtils.isEmpty(registerPhone.getText().toString().trim()) ||
                        UIUtils.isEmpty(registerMsg.getText().toString().trim()) ||
                        UIUtils.isEmpty(registerPwd.getText().toString().trim()) ||
                        UIUtils.isEmpty(registerPwd2.getText().toString().trim())) {
                    UIUtils.showToast("手机号或密码不能为空");
                    return;
                }

                if (!UIUtils.isMobileNO(registerPhone.getText().toString().trim())) {
                    UIUtils.showToast("手机号格式不正确");
                    return;
                }
                if (!UIUtils.isPwdFormat(registerPwd.getText().toString().trim())) {
                    UIUtils.showToast("密码必须在6位及以上");
                    return;
                }
                if (!registerPwd.getText().toString().trim().equals(registerPwd2.getText().toString().trim())) {
                    UIUtils.showToast("两次输入的密码不一致");
                    return;
                }

                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (registerMsg.length() > 0 && registerPwd.length() > 0&&registerPwd2.length()>0) {
            registerBt.setClickable(true);
            registerBt.setBackgroundResource(R.drawable.background_newlogin_gobutton_orange);
        } else {
            registerBt.setClickable(false);
            registerBt.setBackgroundResource(R.drawable.background_newlogin_gobutton_gray);
        }
    }
}
