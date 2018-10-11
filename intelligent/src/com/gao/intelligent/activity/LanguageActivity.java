package com.gao.intelligent.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述:选择语言
 */
public class LanguageActivity extends BaseActivity {
    private static final String TAG = LanguageActivity.class.getName();
    private static final int save_language_refresy_type = 100;

    @BindView(R.id.languageRadioGroup)
    RadioGroup languageRadioGroup;
    @BindView(R.id.simplifiedChechbox)
    RadioButton simplifiedChechbox;
    @BindView(R.id.traditionalChechbox)
    RadioButton traditionalChechbox;
    @BindView(R.id.englishChechbox)
    RadioButton englishChechbox;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_language;

    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {
        if (Global.LANGUAGE_TYPE.equals(Comm.SIMPLIFIED_CHINESE)) {
            simplifiedChechbox.setChecked(true);
        } else if (Global.LANGUAGE_TYPE.equals(Comm.TRADITIONAL_CHINESE)) {
            traditionalChechbox.setChecked(true);
        } else if (Global.LANGUAGE_TYPE.equals(Comm.ENGLISH)) {
            englishChechbox.setChecked(true);
        }

    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        try {

            switch (what) {
                case save_language_refresy_type:
                    String language = Comm.SIMPLIFIED_CHINESE;
                    switch (languageRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.simplifiedChechbox:
                            language = Comm.SIMPLIFIED_CHINESE;
                            break;
                        case R.id.traditionalChechbox:
                            language = Comm.TRADITIONAL_CHINESE;
                            break;
                        case R.id.englishChechbox:
                            language = Comm.ENGLISH;
                            break;
                    }

                    Utils.setLocalSave(this, Comm.SAVEFILE, Comm.LANGUAGE, language);
                    finish();

                    break;



            }

        } catch (Throwable e) {
            e.printStackTrace();
            LogUtils.e(TAG, e);
        }
    }

    @OnClick({R.id.toolbar,R.id.saveButton})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.toolbar:
                 finish();
                break;
            case R.id.saveButton:
                String str = Comm.SIMPLIFIED_CHINESE;

                switch (languageRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.simplifiedChechbox:
                        str = Comm.SIMPLIFIED_CHINESE;
                        break;
                    case R.id.traditionalChechbox:
                        str = Comm.TRADITIONAL_CHINESE;
                        break;
                    case R.id.englishChechbox:
                        str = Comm.ENGLISH;
                        break;
                }

                if (Global.LANGUAGE_TYPE.equals(str)) {
                    finish();
                } else {
                    showAlertDialog(getString(R.string.activity_language), getString(R.string.language_dialog_msg), getString(R.string.language_dialog_switch), getString(R.string.language_dialog_cancel), save_language_refresy_type, DISMISS_ALERT_DIALOG_TYPE, null, null);

                }
                break;
        }

    }
}
