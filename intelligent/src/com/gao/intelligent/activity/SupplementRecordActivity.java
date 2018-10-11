package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.ResultBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/6/12.
 * 描述:补充运行记录页面
 */
public class SupplementRecordActivity extends BaseActivity {
    @BindView(R.id.CheckBox_1)
    CheckBox mCheckBox1;//
    @BindView(R.id.CheckBox_2)
    CheckBox mCheckBox2;//
    @BindView(R.id.CheckBox_3)
    CheckBox mCheckBox3;//
    @BindView(R.id.CheckBox_4)
    CheckBox mCheckBox4;//
    @BindView(R.id.CheckBox_5)
    CheckBox mCheckBox5;//
    @BindView(R.id.CheckBox_6)
    CheckBox mCheckBox6;//
    @BindView(R.id.CheckBox_7)
    CheckBox mCheckBox7;//
    @BindView(R.id.CheckBox_8)
    CheckBox mCheckBox8;//
    @BindView(R.id.ll_see_no)
    LinearLayout llSeeNo;//显示输入内容
    @BindView(R.id.edt_person_name)
    EditText edtPersonName;//记录人
    @BindView(R.id.edt_product_nme)
    EditText edtProductName;//设备名称
    @BindView(R.id.edt_maintenance_part)
    EditText edtMaintenancePart;//保养部位
    @BindView(R.id.edt_replace_part)
    EditText edtReplacePart;//更换部件
    @BindView(R.id.edt_accendant_name)
    EditText edtAccendantName;//维护人员
    @BindView(R.id.edt_process_redort)
    EditText edtProcessRedoct;//过程记录
    @BindView(R.id.edt_remark)
    EditText edtRemark;//备注
    @BindView(R.id.rg_ok_no)
    RadioGroup rgOkNo;//是否带故障运行
    private RadioButton selectButton;//是否带故障运行
    private String okText = "";//优先级
    private String selectId = "";//
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_supplement_record;
    }

    @Override
    public void initView() {
        rgOkNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectButton = (RadioButton) findViewById(rgOkNo.getCheckedRadioButtonId());
                okText = selectButton.getText().toString();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llSeeNo.setVisibility(View.VISIBLE);

                } else {
                    llSeeNo.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.txv_submit})
    public void submitData() {
        selectId = "";
        if (mCheckBox1.isChecked()) {
            selectId += "0,";
        }
        if (mCheckBox2.isChecked()) {
            selectId += "1,";
        }
        if (mCheckBox3.isChecked()) {
            selectId += "2,";
        }
        if (mCheckBox4.isChecked()) {
            selectId += "3,";
        }
        if (mCheckBox5.isChecked()) {
            selectId += "4,";
        }
        if (mCheckBox6.isChecked()) {
            selectId += "5,";
        }
        if (mCheckBox7.isChecked()) {
            selectId += "6,";
        }
        if (mCheckBox8.isChecked()) {
            selectId += "7,";
        }

        HashMap<String, String> param = new HashMap<>();
        if (TextUtils.isEmpty(selectId)|selectId.equals("")){
            ToastUtils.showShort("请选择至少一个停车原因");
            return;
        }
        ToastUtils.showShort(selectId.substring(0,selectId.length()-1));

        if (mCheckBox3.isChecked()){
            HashMap<String, String> paramFrist = new HashMap<>();
            paramFrist.put("runningIll", okText);//
            paramFrist.put("deviceName", edtPersonName.getText().toString());//设备名称
            paramFrist.put("devicePosition", edtMaintenancePart.getText().toString());//保养部位
            paramFrist.put("replacePart", edtReplacePart.getText().toString());//更换部位
            paramFrist.put("maintainPerson", edtAccendantName.getText().toString());//维护人员
            paramFrist.put("processRecord", edtProcessRedoct.getText().toString());//过程记录
            paramFrist.put("stopRerson", selectId);//停车原因
            paramFrist.put("id", getIntent().getStringExtra("ID"));//ID
            sendHttpPost(ApiService.SAVE_RECORD_DATA_FRIST,paramFrist,10015);
        }
        param.put("stopRerson", selectId.substring(0,selectId.length()-1));//停车原因
        param.put("id", getIntent().getStringExtra("ID"));//ID
        param.put("remark", edtRemark.getText().toString());//备注
        param.put("recordPerson", edtPersonName.getText().toString());//记录人
        showProgressDialog("正在提交..");
        sendHttpPost(ApiService.SAVE_RECORD_DATA,param,10013);
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                switch (type) {
                    case 10013:
                        hideCustomProgressDialog();
//                        LogUtils.d("补充提交", obj);
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        try {
                            ResultBean bean = JSON.parseObject(obj.toString(), ResultBean.class);

                            if (bean.getResultCode()!=null&&bean.getResultCode().equals("1")){
                                ToastUtils.showShort("补充成功！");
                                finish();
                            }else {
                                ToastUtils.showShort("补充失败！");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;
                    case 10015:
                        hideCustomProgressDialog();
                        //   mPullToRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        LogUtils.d("补充提交11", obj);
                        if (obj.equals("401")) {
                            ToastUtils.showShort("登录超时，请重新登录");
                            startActivity(new Intent(this, LoginActivity.class));
                            return;
                        }
                        break;
                }
                break;
            case 10003:
                 hideCustomProgressDialog();
                ToastUtils.showShort("请检查网络");
                break;
        }
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {edtAccendantName, edtRemark, edtMaintenancePart, edtPersonName, edtProcessRedoct, edtProcessRedoct, edtReplacePart};
        return views;
    }
    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.edt_person_name, R.id.edt_product_nme, R.id.edt_accendant_name, R.id.edt_maintenance_part,
                R.id.edt_replace_part, R.id.edt_process_redort, R.id.edt_remark};
        return ids;
    }
}
