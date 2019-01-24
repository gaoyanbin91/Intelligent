package com.gao.intelligent.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/5/4.
 * 描述:关于我们
 */
public class AboutActivity extends BaseActivity {
    private static final String TAG = AboutActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.verson_textview)
    TextView versonText;//版本号
    @BindView(R.id.about_owner)
    TextView aboutOwner;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        versonText.setText(UIUtils.getAppVersionName(this));
        aboutOwner.setText("\u3000\u3000"+UIUtils.getResource().getString(R.string.about_owner));
    }
}
