package com.gao.intelligent.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.UserLineBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.UIUtils;
import com.gao.intelligent.view.WDTabPageIndicator;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by gaoyanbin on 2018/4/12.
 * 描述:
 */
public class DataShowFragment extends BaseFragment {
    @BindView(R.id.indicator)
    WDTabPageIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    ArrayList<UserLineBean.ObjectBean.ListBean> mList = new ArrayList<>();//产线数据
    @Override
    public void aidHandleMessage(int what, int type,Object obj) {
        super.aidHandleMessage(what, type,obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                   LogUtils.i("个人信息", obj.toString());
                UserLineBean mBean = JSON.parseObject(obj.toString(), UserLineBean.class);
                mList = (ArrayList<UserLineBean.ObjectBean.ListBean>) mBean.getObject().getList();
                if (mBean.getObject().getEmail() != null) {//存储用户邮箱
                    AppConfig.getInstance().putString("email", mBean.getObject().getEmail());
                }
                if (mBean.getObject().getSignatureAddress()!=null){
                    AppConfig.getInstance().putString("signImg", mBean.getObject().getSignatureAddress());
                }
                if (mBean.getObject().getUserName() != null) {//存储用户名
                    AppConfig.getInstance().putString("username", mBean.getObject().getUserName());
                }
                if (mBean.getObject().getUserCname() != null) {//存储中文用户名
                    AppConfig.getInstance().putString("usercname", mBean.getObject().getUserCname());
                }
                if (mBean.getObject().getMobile() != null) {//存储电话
                    AppConfig.getInstance().putString("mobile", mBean.getObject().getMobile());
                }
                if (mBean.getObject().getCustomerId() != null) {//存储用户id
                    AppConfig.getInstance().putString("customerId", mBean.getObject().getCustomerId());
                }
                if (mBean.getObject().getSysOrgName() != null) {//存储厂家名称
                    AppConfig.getInstance().putString("sysOrgName", mBean.getObject().getSysOrgName());
                }
                if (mBean.getObject().getSysOrgId() != null) {//存储厂家id
                    AppConfig.getInstance().putString("sysOrgId", mBean.getObject().getSysOrgId());
                }if (mBean.getObject().getId() != null) {//存储厂家id
                AppConfig.getInstance().putString("userId", mBean.getObject().getId());
            }
                if (mList.size()>0){//存储产线个数 产线id
                    AppConfig.getInstance().putInt("lineSize",mList.size());
//                    ToastUtils.showShort(mList.size()+"");
                    AppConfig.getInstance().putString("lineId",mList.get(0).getId());//产线id
                    AppConfig.getInstance().putString("lineName",mList.get(0).getName());//产线name
                }

                break;
            case 10003:
                hideProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }
    @Override
    protected void initView() {
        /**
         * 获取 用户信息
         */
        HashMap<String, String> p = new HashMap<>();
        p.put("deviceId", AppConfig.getInstance().getString("deviceId",""));
//        ToastUtils.showShort(AppConfig.getInstance().getString("deviceId",""));
        sendHttpGet(ApiService.QUERY_USER_MEAASGE, p, 10001);

    }


    @Override
    protected void initData() {
        BasePagerAdapter adapter = new BasePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        setTabPagerIndicator();
    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(WDTabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#00ffffff"));// 设置分割线的颜色
        indicator.setDividerPadding(UIUtils.dip2px(getActivity(), 10));
        indicator.setIndicatorColor(ContextCompat.getColor(getActivity(), R.color.theme_color));// 设置底部导航线的颜色
        indicator.setTextColorSelected(ContextCompat.getColor(getActivity(), R.color.theme_color));// 设置tab标题选中的颜色
        indicator.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));// 设置tab标题未被选中的颜色
        indicator.setTextSize(UIUtils.sp2px(18));// 设置字体大小
        indicator.setTextSizeSelected(UIUtils.sp2px(18));//设置tab标题被选中的大小
        indicator.setIndicatorHeight(UIUtils.dip2px(getActivity(), 4));
    }

    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_data_tab;
    }


    class BasePagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
         if (AppConfig.getInstance().getString("authority","").equals("[LEADER]")){
             this.titles = UIUtils.getStringArray(R.array.data_titles_leader);
         }else {
             this.titles = UIUtils.getStringArray(R.array.data_titles);
         }
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
//            switch (position) {
//                case 0:
//                    fragment = new ProblemListFgm();
//                    Bundle bundle1 = new Bundle();
//                    fragment.setArguments(bundle1);
//                    break;
//                case 1:
//                    fragment = new ProblemTndoFgm();
//                    Bundle bundle2 = new Bundle();
//                    fragment.setArguments(bundle2);
//                    break;
//                case 2:
//                    fragment = new ProblemOverFgm();
//                    Bundle bundle3= new Bundle();
//                    fragment.setArguments(bundle3);
//                    break;
//            }
            fragment = new ProblemListFgm();
            Bundle bundle1 = new Bundle();
            bundle1.putString("flag",position+"");
            fragment.setArguments(bundle1);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
