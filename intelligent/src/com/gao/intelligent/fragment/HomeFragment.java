package com.gao.intelligent.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.DemandServiceActivity;
import com.gao.intelligent.activity.DeviceListActivity;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.activity.ServiceLIstAty;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.UserLineBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.GlideUtils;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.view.NoticeView;
import com.gao.intelligent.view.banner.BGABanner;
import com.gao.intelligent.view.popupwindow.top.PopUpWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/12.
 * 描述:主页
 */
public class HomeFragment extends BaseFragment implements BGABanner.Adapter {

    @BindView(R.id.gv_home_model)
    GridView mGridView;//功能模块
    @BindView(R.id.ll_selection_lines)
    LinearLayout llSelectionLines;
    @BindView(R.id.tw_line_name)
    TextView textViewLineName;//产线名称
    @BindView(R.id.home_banner)
    BGABanner mBGABanner;//轮播图
    @BindView(R.id.notice_view)
    NoticeView mNoticeView;
    private SimpleAdapter mAdapter;
    public ArrayList<Integer> urls = new ArrayList<>();
    ArrayList<UserLineBean.ObjectBean.ListBean> mList = new ArrayList<>();//产线数据
    String[] linesArray;
    private String lineID = "";

    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams bannerlp = mBGABanner.getLayoutParams();
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        bannerlp.height = width * 122 / 375;
        mBGABanner.setLayoutParams(bannerlp);
        urls.clear();
        urls.add(R.drawable.banner1);
        urls.add(R.drawable.banner2);
        mBGABanner.setAdapter(this);
        mBGABanner.setData(urls, null);
        List<String> notices = new ArrayList<>();
        notices.add("金春一线正式");
        notices.add("金春五线正式");
        notices.add("金春三线正式");
        mNoticeView.addNotice(notices);
        mNoticeView.startFlipping();
    }

    @Override
    public void aidHandleMessage(int what, Object obj) {
        super.aidHandleMessage(what, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                LogUtils.i("个人信息", obj.toString());
                UserLineBean mBean = JSON.parseObject(obj.toString(), UserLineBean.class);
                mList = (ArrayList<UserLineBean.ObjectBean.ListBean>) mBean.getObject().getList();
                AppConfig.getInstance().putString("fjCustomerId", mList.get(0).getFjCustomerId());
                textViewLineName.setText(mList.get(0).getName());
                lineID = mList.get(0).getId();

                if (mList.size() > 0) {
                    linesArray = new String[mList.size()];
                    for (int i = 0; i < mList.size(); i++) {
                        MyApp.map.put(mList.get(i).getId(),mList.get(i).getName());
                        linesArray[i] = mList.get(i).getName();
                    }
                }
                if (mBean.getObject().getEmail() != null) {//存储用户邮箱
                    AppConfig.getInstance().putString("email", mBean.getObject().getEmail());
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


                //tvF.setText( obj.toString());
                break;
            case 10003:
                hideProgressDialog();
                //        ToastUtils.showShort(obj.toString());
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    protected void initData() {
        //设置主页功能模块
        mAdapter = new SimpleAdapter(getActivity(), getDatas(), R.layout.main_item, new String[]{"picture", "name"}, new int[]{R.id.iv_model_image, R.id.tv_model_name});
        mGridView.setAdapter(mAdapter);
        //主页功能模块监听
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0://设备档案
                        if (TextUtils.isEmpty(lineID)) {
                            ToastUtils.showShort("暂无产线信息");
                            return;
                        }
                        Intent intentOne = new Intent(getActivity(), DeviceListActivity.class);
                        intentOne.putExtra("lineID", lineID);
                        intentOne.putExtra("lineName", textViewLineName.getText().toString());
                        startActivity(intentOne);
                        break;
//                    case 1://跳转到实时监测
//                        if (TextUtils.isEmpty(lineID)) {
//                            ToastUtils.showShort("暂无产线信息");
//                            return;
//                        }
//                        Intent i = new Intent(getActivity(), RealtimeMonitorActivity.class);
//                        i.putExtra("lineID", lineID);
//                        i.putExtra("lineName", textViewLineName.getText().toString());
//                        startActivity(i);
//                        break;
                    case 1://服务请求
                        if (TextUtils.isEmpty(lineID)) {
                            ToastUtils.showShort("暂无产线信息");
                            return;
                        }
                        Intent service = new Intent(getActivity(), ServiceLIstAty.class);
                        service.putExtra("flag", "add");
                        service.putExtra("lineID", lineID);
                        startActivity(service);
                        break;
//                    case 3://运行记录
////                        if(TextUtils.isEmpty(lineID)){
////                            ToastUtils.showShort("暂无产线信息");
////                            return;
////                        }
//                        Intent record = new Intent(getActivity(), RecordListActivity.class);
//                        record.putExtra("lineID", lineID);
//                        startActivity(record);
//                        break;
//                    case 4://工艺模板列表
//                        if (TextUtils.isEmpty(lineID)) {
//                            ToastUtils.showShort("暂无产线信息");
//                            return;
//                        }
//                        Intent param = new Intent(getActivity(), ProcessTemplateActivity.class);
//                        param.putExtra("lineID", lineID);
//                        startActivity(param);
//                        break;
//                    case 5://实时工艺
//                        if (TextUtils.isEmpty(lineID)) {
//                            ToastUtils.showShort("暂无产线信息");
//                            return;
//                        }
//                        Intent paramTem = new Intent(getActivity(), ProcessTypeActivity.class);
//                        paramTem.putExtra("ID", lineID);
//                        paramTem.putExtra("flag", "id");
//                        startActivity(paramTem);
//                        break;
                    case 2://
//                      、、  ToastUtils.showShort("开发中，请耐心等待");
                        if (TextUtils.isEmpty(lineID)) {

                            ToastUtils.showShort("暂无产线信息");
                            return;
                        }
                        Intent i1 = new Intent(getActivity(), DemandServiceActivity.class);
                        i1.putExtra("lineID", lineID);
                        startActivity(i1);
                        break;
                    default:
                        break;

                }
            }
        });
        showProgressDialog("加载中...");
        /**
         * 获取产线和用户信息
         */
        sendHttpGet(ApiService.QUERY_USER_MEAASGE, new HashMap<String, String>(), 10001);
    }

    /**
     * 选择产线
     */
    @OnClick(R.id.ll_selection_lines)
    public void onCLickLine() {
        if (mList.size() > 0) {
            showFilterPop(1, linesArray, textViewLineName, llSelectionLines);//选择产线
        }
    }


    private void showFilterPop(final int flag, final String[] arr, final TextView lineName, final View view) {
        PopUpWindow popupWindow = new PopUpWindow(getActivity(), arr, null, view);
        popupWindow.setShowLocationYourself(true);
        popupWindow.showPopUpWindow(new PopUpWindow.OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
                lineName.setText(arr[position]);
                lineID = mList.get(position).getId();
                AppConfig.getInstance().putString("fjCustomerId", mList.get(position).getFjCustomerId());
            }

            @Override
            public void onshowLocation(PopupWindow popupWindow) {
                popupWindow.showAsDropDown(view, 0, 0);
            }
        });
    }

    /**
     * 得到菜单数据
     *
     * @return
     */
    private List<Map<String, Object>> getDatas() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map = new HashMap<>();
        map.put("picture", R.mipmap.icon_home_dossier);
        map.put("name", getString(R.string.home_1));
        list.add(map);
//        map = new HashMap<>();
//        map.put("picture", R.mipmap.icon_home_realtime);
//        map.put("name", getString(R.string.home_2));
//        list.add(map);
        map = new HashMap<>();
        map.put("picture", R.mipmap.icon_home_service);
        map.put("name", getString(R.string.home_3));
        list.add(map);
//        map = new HashMap<>();
//        map.put("picture", R.mipmap.icon_home_record);
//        map.put("name", getString(R.string.home_4));
//        list.add(map);
//        map = new HashMap<>();
//        map.put("picture", R.mipmap.icon_home_param);
//        map.put("name", getString(R.string.home_5));
//        list.add(map);
//        map = new HashMap<>();
//        map.put("picture", R.mipmap.icon_home_reltime_data);
//        map.put("name", getString(R.string.home_6));
//        list.add(map);
        map = new HashMap<>();
        map.put("picture", R.mipmap.icon_home_7);
        map.put("name", getString(R.string.home_7));
        list.add(map);
        int size = list.size();
        if (size % 4 != 0) {
            for (int i = 0; i < 4 - size % 4; i++) {
                map = new HashMap<>();
                map.put("picture", "");
                map.put("name", "");
                list.add(map);
            }
        }

        return list;
    }


    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Integer adModel = (Integer) model;
        ImageView webImageView = (ImageView) view;
        GlideUtils.load(adModel, webImageView, R.drawable.ic_launcher_background);
    }
}
