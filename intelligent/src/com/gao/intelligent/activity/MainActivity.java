package com.gao.intelligent.activity;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.fragment.DataShowFragment;
import com.gao.intelligent.fragment.FaultSearchFragment;
import com.gao.intelligent.fragment.MyFragment;
import com.gao.intelligent.fragment.RealtimeFragment;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.view.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private ViewPager mVpContent;
    private String txvMenu[];
    private int intImageViewArray2[];
    /**
     * 定义数组来存放Fragment界面
     */
    private Class fragmentArray[];

    private List<View> badgeTargetList;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    public void initView() {
        //    requestPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setSystemBar(true);
        if (TextUtils.isEmpty(AppConfig.getInstance().getString("type",""))){
            intImageViewArray2 = new int[]{R.drawable.tab_home_bg, R.drawable.tab_fault_bg,   R.drawable.tab_my_bg};
            fragmentArray = new Class[]{DataShowFragment.class,FaultSearchFragment.class,  MyFragment.class};
            txvMenu = new String[]{getString(R.string.tab_home) ,getString(R.string.tab_fault),
                    getString(R.string.tab_mine)};

        }else {
            intImageViewArray2 = new int[]{R.drawable.tab_home_bg,R.drawable.tab_fault_bg,R.drawable.tab_search_bg,    R.drawable.tab_my_bg};
            fragmentArray = new Class[]{DataShowFragment.class,FaultSearchFragment.class, RealtimeFragment.class, MyFragment.class};
            txvMenu = new String[]{getString(R.string.tab_home) ,getString(R.string.tab_fault),getString(R.string.tab_contact) ,
                    getString(R.string.tab_mine)};
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = intImageViewArray2.length;

        tabhost.setup(this, fragmentManager, R.id.realtabcontent);
        badgeTargetList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 将Tab按钮添加进Tab选项卡中
            View view = getTabItemView(i);
            badgeTargetList.add(view);
            tabhost.addTab(tabhost.newTabSpec(String.valueOf(i)).setIndicator(view), fragmentArray[i], null);
        }

        tabhost.getTabWidget().setDividerDrawable(getResources().getDrawable(R.color.white));
        tabhost.setOnTabChangedListener(this);

    }



    private View getTabItemView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
        ImageView mImageView = (ImageView) view.findViewById(R.id.icon);
        TextView txv_menu = (TextView) view.findViewById(R.id.txv_menu);
        txv_menu.setText(txvMenu[i]);
        mImageView.setImageResource(intImageViewArray2[i]);
        return view;
    }
    @Override
    public void onTabChanged(String tabId) {

    }
}
