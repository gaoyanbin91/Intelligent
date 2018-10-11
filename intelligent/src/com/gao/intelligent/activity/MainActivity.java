package com.gao.intelligent.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.gao.intelligent.R;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.fragment.ContactFragment;
import com.gao.intelligent.fragment.HomeFragment;
import com.gao.intelligent.fragment.MessageFragment;
import com.gao.intelligent.fragment.MineFragment;
import com.gao.intelligent.view.BottomBarItem;
import com.gao.intelligent.view.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ViewPager mVpContent;
    private BottomBarLayout mBottomBarLayout;
    private List<Fragment> mFragmentList = new ArrayList<>();

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
        setSystemBar(false);
        mVpContent =   findViewById(R.id.vp_content);
        mBottomBarLayout =   findViewById(R.id.bbl);
    }
    public void initData() {
        MessageFragment messageFragment = new MessageFragment();
        Bundle bundle1 = new Bundle();
        messageFragment.setArguments(bundle1);
        mFragmentList.add(messageFragment);
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle2 = new Bundle();
        homeFragment.setArguments(bundle2);
        mFragmentList.add(homeFragment);
        ContactFragment contactFragment = new ContactFragment();
        Bundle bundle3 = new Bundle();
        contactFragment.setArguments(bundle3);
        mFragmentList.add(contactFragment);
        MineFragment meFragment = new MineFragment();
        Bundle bundle4 = new Bundle();
        meFragment.setArguments(bundle4);
        mFragmentList.add(meFragment);
        initListener();
    }
    private void initListener() {
        mVpContent.setAdapter(new MyAdapter(getSupportFragmentManager()));

        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setCurrentItem(1);
        mVpContent.setCurrentItem(1);
       mBottomBarLayout.getBottomItem(0).setStatus(false);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int position) {
                Log.i("TabActivity", "position: " + position);
            }
        });

        //   mBottomBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
        //  mBottomBarLayout.setUnread(1,1001);//设置第二个页签的未读数
        //  mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        //  mBottomBarLayout.setMsg(3,"NEW");//设置第四个页签显示NEW提示文字
    }
    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
