package com.gao.intelligent.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.activity.NewFeedBackActivity;
import com.gao.intelligent.activity.SetActivity;
import com.gao.intelligent.activity.UserInfoActivity;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.AppVersionBean;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/12.
 * 描述:我的页面
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.rl_user)
    View rlUser;
    @BindView(R.id.tw_username)
    TextView twUserName;//用户名
    @BindView(R.id.iv_fra_red)
    ImageView ivFraRed;
    String flag = "0";
    List<AppVersionBean.RowsBean> mRowsBeanList = new ArrayList<>();


    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_mine;
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
                LogUtils.i("版本信息", obj.toString());
                AppVersionBean mBean = JSON.parseObject(obj.toString(), AppVersionBean.class);
                if (mBean.getRows() != null) {
                    mRowsBeanList = mBean.getRows();
//                    ToastUtils.showShort(mRowsBeanList.get(0).getVersion());
                    try {
                        if ((UIUtils.compareVersion(mRowsBeanList.get(0).getVersion(), UIUtils.getAppVersionCode(getActivity())) > 0)) {
                            ivFraRed.setVisibility(View.VISIBLE);
                            flag = "1";
                        } else {
                            ivFraRed.setVisibility(View.GONE);
                            flag = "0";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //tvF.setText( obj.toString());
                break;
            case 10003:
                hideProgressDialog();
                //  ToastUtils.showShort(obj.toString());
//                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    protected void initView() {
        HashMap<String, String> param = new HashMap<>();
        param.put("name", "weijieyun.apk");
        sendHttpGet(ApiService.QUERY_APP_VERSON, param, 11001);
    }

    @Override
    protected void initData() {
        if (AppConfig.getInstance().getString("usercname", "") != null) {
            twUserName.setText(AppConfig.getInstance().getString("usercname", ""));
        }

    }

    @OnClick({R.id.rl_user, R.id.settingbutton, R.id.re_new_face_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.settingbutton:
                Intent intent = new Intent(new Intent(getActivity(), SetActivity.class));
                intent.putExtra("flag", flag);
                if (mRowsBeanList.size() > 0) {
                    intent.putExtra("url", mRowsBeanList.get(0).getAddress());
                }
                startActivity(intent);
                break;

            case R.id.re_new_face_back:
                startActivity(new Intent(getActivity(), NewFeedBackActivity.class));
                break;
        }
    }
}
