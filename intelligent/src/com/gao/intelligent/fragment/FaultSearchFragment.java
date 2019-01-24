package com.gao.intelligent.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.LoginActivity;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.ErrorBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Yanbin on 2019/1/4.
 * 描述:故障查询
 */
public class FaultSearchFragment extends BaseFragment {
    @BindView(R.id.edt_fault_code)
    EditText edtFaultCode;
    @BindView(R.id.texSearch)
    TextView texSearch;//搜索
    @BindView(R.id.no_internet)
    View mView;
    @BindView(R.id.phone_delte_imageview)
    ImageView phone_delte_imageview;
    @BindView(R.id.texErrorName)
    TextView texErrorName;//故障名称
    @BindView(R.id.texErrorCode)
    TextView texErrorCode;//故障类型
    @BindView(R.id.textErrorAnser)
    TextView textErrorAnser;//故障结果
    @BindView(R.id.texErrorResponse)
    TextView texErrorResponse;//故障反馈
    @BindView(R.id.texErrorReson)
    TextView texErrorReson;//故障原因
    @BindView(R.id.texErrorDispose)
    TextView texErrorDispose;//故障描述
    @BindView(R.id.errorView)
     View errorView;
    private List<ErrorBean.RowsBean> mRowsBeanList = new ArrayList<>();
    @Override
    public int getFragmentViewLayout() {
        return R.layout.fragment_fault_search;
    }

    @Override
    protected void initView() {
        edtFaultCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim().replace(" ", "");
                if (!TextUtils.isEmpty(str) && edtFaultCode.hasFocus()) {
                    phone_delte_imageview.setVisibility(View.VISIBLE);
                } else {
                    phone_delte_imageview.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void aidHandleMessage(int what,int type, Object obj) {
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
                LogUtils.i("故障信息", obj.toString());

                ErrorBean errorBean = JSON.parseObject(obj.toString(), ErrorBean.class);
                    if (errorBean.getRows()!=null){
                        mRowsBeanList = errorBean.getRows();
                        if (mRowsBeanList.size()>0){
                            errorView.setVisibility(View.VISIBLE);
                            texErrorName.setText(mRowsBeanList.get(0).getErrorName());
                            texErrorCode.setText(mRowsBeanList.get(0).getErrorType());
                            textErrorAnser.setText(mRowsBeanList.get(0).getErrorAnswer());
                            texErrorResponse.setText(mRowsBeanList.get(0).getErrorResponse());
                            texErrorReson.setText(mRowsBeanList.get(0).getErrorReason());
                            texErrorDispose.setText(mRowsBeanList.get(0).getErrorDispose());
                        }else {
                            ToastUtils.showShort("故障查询失败");
                            errorView.setVisibility(View.GONE);
                            mView.setVisibility(View.VISIBLE);
                        }
                    }
                break;
            case 10003:
                hideProgressDialog();
                break;
        }
    }
    @OnClick({R.id.texSearch,R.id.phone_delte_imageview})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.texSearch:
                if (edtFaultCode.getText().length()==6) {
                    showProgressDialog("加载中...");
                    mView.setVisibility(View.GONE);
                    HashMap<String, String> param = new HashMap<>();
                    param.put("errorCode", edtFaultCode.getText().toString());
                    sendHttpPost(ApiService.QUERY_ERROR_DETAIL, param, 11001);
                }else {
                    mView.setVisibility(View.VISIBLE);
                    ToastUtils.showShort("请输入6位故障代码");
                }
                break;

            case R.id.phone_delte_imageview:
                mView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                edtFaultCode.setText("");
                break;
        }
    }
}
