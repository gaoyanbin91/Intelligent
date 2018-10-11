package com.gao.intelligent.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.KeyBean;
import com.gao.intelligent.model.ParamIdsBean;
import com.gao.intelligent.model.ParamsBean;
import com.gao.intelligent.model.ProcessParamBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by gaoyanbin on 2018/6/13.
 * 描述:工艺参数数据页面
 */
public class ProcessParamActivity extends BaseActivity {
    private List<ProcessParamBean.RowsBean> mLists = new ArrayList<>();
    private double outOfNet1, doubleLong, doubleTrawl, doubleRoller1,
            doubleRoller2, doubleRoller3, doubleTrawl2, doublePadder, doublePressNet, doublePreesNetKun;
    @BindView(R.id.out_of_net_1)
    TextView textOutOfNet1;//1#梳理出网
    @BindView(R.id.out_of_net_2)
    TextView textOutOfNet2;//2#梳理出网
    @BindView(R.id.belt_scale_1)
    TextView textBeltScale1;//1#皮带秤
    @BindView(R.id.belt_scale_2)
    TextView textBeltScale2;//2#皮带秤
    @BindView(R.id.text_surface_speed)
    TextView textSurfaceSpeed;//线速度
    @BindView(R.id.text_long_curtain)
    TextView textLongCurtain;//长帘
    @BindView(R.id.text_trawl)
    TextView textTrawl;//拖网
    @BindView(R.id.text_roller1)
    TextView textRoller1;//锟筒1
    @BindView(R.id.text_roller2)
    TextView textRoller2;//锟筒3
    @BindView(R.id.text_roller3)
    TextView textRoller3;//锟筒3
    @BindView(R.id.text_trawl2)
    TextView textTrawl2;//拖网2
    @BindView(R.id.text_padder)
    TextView textPadder;//轧车
    @BindView(R.id.text_press_net)
    TextView textPressNet;//压网
    @BindView(R.id.text_press_net_kun)
    TextView textPressNetKun;//压网锟
    @BindView(R.id.SC_CL_B)
    TextView SC_CL_B;//
    @BindView(R.id.SC_TW1_B)
    TextView SC_TW1_B;
    @BindView(R.id.SC_GT1_B)
    TextView SC_GT1_B;
    @BindView(R.id.SC_GT2_B)
    TextView SC_GT2_B;
    @BindView(R.id.SC_GT3_B)
    TextView SC_GT3_B;
    @BindView(R.id.SC_TW2_B)
    TextView SC_TW2_B;
    @BindView(R.id.SC_YC_B)
    TextView SC_YC_B;
    @BindView(R.id.SC_YW_B)
    TextView SC_YW_B;
    @BindView(R.id.SC_YWG_B)
    TextView SC_YWG_B;
    @BindView(R.id.kaiqing)
    View kaiqing;
    @BindView(R.id.shuliji1)
    View shuliji1;
    @BindView(R.id.shuliji2)
    View shuliji2;
    @BindView(R.id.shuiciji)
    View shuiciji;
    @BindView(R.id.yuanwang)
    View yuanwang;
    @BindView(R.id.juanrao)
    View juanrao;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.line5)
    View line5;
    @BindView(R.id.line6)
    View line6;
    @BindView(R.id.line7)
    View line7;
    @BindView(R.id.line8)
    View line8;
    @BindView(R.id.line9)
    View line9;
    @BindView(R.id.line10)
    View line10;
    @BindView(R.id.line11)
    View line11;

    @BindView(R.id.mTitle)
    TextView mTextView;

    private WebSocket mSocket;
    String changMessage;
    private List<String> mParamsLists = new ArrayList<>();
    private String dataID = "";
    KeyBean keyBean;
    private String lineID;
    private List<ParamsBean.DataBean> trensLists = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_process_param;
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                switch (type) {
                    case 10052:
                        hideCustomProgressDialog();
                        LogUtils.i("工艺参数", obj.toString());
                        ProcessParamBean paramBean = JSON.parseObject(obj.toString(), ProcessParamBean.class);
                        if (paramBean.getRows() != null) {
                            mLists = paramBean.getRows();
                        }
                        if (mLists.size() > 0) {
                            showDatas(mLists);
                        }
                        break;

                    case 10053:
                        hideCustomProgressDialog();
//                        LogUtils.i("工艺参数IDS", obj.toString());

                        ParamIdsBean paramIdsBean = JSON.parseObject(obj.toString(), ParamIdsBean.class);
                        if (paramIdsBean.getRows() != null) {
                            mParamsLists = paramIdsBean.getRows();
                            if (mParamsLists.size() > 0) {
                                for (int i = 0; i < mParamsLists.size(); i++) {
                                    dataID += mParamsLists.get(i) + ",";
                                }
                                LogUtils.i("DataId1", dataID);
                                keyBean = new KeyBean();
                                keyBean.setScode("A1000");
                                keyBean.setStype("0");
                                keyBean.setRemark(lineID);
                                KeyBean.SdataBean m = new KeyBean.SdataBean();
                                m.setIds(dataID);
                                keyBean.setSdata(m);
                                changMessage = new Gson().toJson(keyBean);
                                initSocket();
                            } else {
                                ToastUtils.showShort("数据获取失败！");
                            }
                        }
                        break;
                }

                break;
            case 10003:
                LogUtils.i("工艺参数", obj.toString());
                hideCustomProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    /**
     * 开启长连接
     */
    private void initSocket() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url("ws:/"+ApiService.base+"websocket/androidConfig").build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            //连接成功后，发送登录信息
            LogUtils.i("对象", changMessage);
            mSocket.send(changMessage);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            hideProgressDialog();
            //收到服务器端发送来的信息后，每隔25秒发送一次心跳包
            LogUtils.i("返回shuju", text);
            output(text);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mSocket.send(changMessage);
                }
            }, 10000);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    }

    private void output(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ParamsBean valueBean = JSON.parseObject(content, ParamsBean.class);
                if (valueBean.getScode().equals("0011")) {
                    trensLists = valueBean.getData();
                    if (trensLists.size() > 0) {
                        for (int i = 0; i < trensLists.size(); i++) {
                            try {
                                String id = trensLists.get(i).getCode();
                                if (id.equals("SL1_XCWL_B")) {
                                    outOfNet1 = Double.parseDouble(trensLists.get(i).getValue());
                                    textOutOfNet1.setText(trensLists.get(i).getValue());//1#梳理出网
                                    textSurfaceSpeed.setText(trensLists.get(i).getValue());//线速度
                                } else if (id.equals("SL2_XCWL_B")) {
                                    textOutOfNet2.setText(trensLists.get(i).getValue());//2#梳理出网
                                } else if (id.equals("SL1_PDC_A")) {
                                    textBeltScale1.setText(trensLists.get(i).getValue());//1#皮带秤
                                } else if (id.equals("SL2_PDC_A")) {
                                    textBeltScale2.setText(trensLists.get(i).getValue());//2#皮带秤
                                }
                                int view_id = R.id.class.getField(id).getInt(null);
                                TextView view = findViewById(view_id);
                                if (trensLists.get(i).getValue() != null) {
                                    view.setText(trensLists.get(i).getValue());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (SC_CL_B.getText().toString() != null && SC_CL_B.getText().toString() != "") {
                            DecimalFormat df = new DecimalFormat("######0.0000");
                            doubleLong = outOfNet1 * Double.parseDouble(SC_CL_B.getText().toString());
                            textLongCurtain.setText(df.format(doubleLong) + "");//长帘
                            if (SC_TW1_B.getText().toString() != null && SC_TW1_B.getText().toString() != "") {
                                doubleTrawl = doubleLong * Double.parseDouble(SC_TW1_B.getText().toString());
                                textTrawl.setText(df.format(doubleTrawl) + "");//拖网
                                if (SC_GT1_B.getText().toString() != null && SC_GT1_B.getText().toString() != "") {
                                    doubleRoller1 = doubleTrawl * Double.parseDouble(SC_GT1_B.getText().toString());
                                    textRoller1.setText(df.format(doubleRoller1) + "");//锟筒1
                                    if (SC_GT2_B.getText().toString() != null && SC_GT2_B.getText().toString() != "") {
                                        doubleRoller2 = doubleRoller1 * Double.parseDouble(SC_GT2_B.getText().toString());
                                        textRoller2.setText(df.format(doubleRoller2) + "");//锟筒2
                                        if (SC_GT3_B.getText().toString() != null && SC_GT3_B.getText().toString() != "") {
                                            doubleRoller3 = doubleRoller2 * Double.parseDouble(SC_GT3_B.getText().toString());
                                            textRoller3.setText(df.format(doubleRoller3) + "");//锟筒3
                                            if (SC_TW2_B.getText().toString() != null && SC_TW2_B.getText().toString() != "") {
                                                doubleTrawl2 = doubleRoller3 * Double.parseDouble(SC_TW2_B.getText().toString());
                                                textTrawl2.setText(df.format(doubleTrawl2) + "");//拖网2
                                                if (SC_YC_B.getText().toString() != null && SC_YC_B.getText().toString() != "") {
                                                    doublePadder = doubleTrawl2 * Double.parseDouble(SC_YC_B.getText().toString());
                                                    textPadder.setText(df.format(doublePadder) + "");//轧车
                                                    if (SC_YW_B.getText().toString() != null && SC_YW_B.getText().toString() != "") {
                                                        doublePressNet = doublePadder * Double.parseDouble(SC_YW_B.getText().toString());
                                                        textPressNet.setText(df.format(doublePressNet) + "");//压网
                                                        if (SC_YWG_B.getText().toString() != null && SC_YWG_B.getText().toString() != "") {
                                                            doublePreesNetKun = doublePressNet * Double.parseDouble(SC_YWG_B.getText().toString());
                                                            textPressNetKun.setText(df.format(doublePreesNetKun) + "");//压网锟
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            ToastUtils.showShort("获取参数失败！");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getStringExtra("name")!=null){
            mTextView.setText(getIntent().getStringExtra("name")+"参数");
        }
        if (getIntent().getStringExtra("ID") != null) {
            lineID = getIntent().getStringExtra("ID");
        }
        HashMap<String, String> param = new HashMap<>();
        showProgressDialog("加载中..");
        if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("base")) {
            shuiciji.setVisibility(View.GONE);
            shuliji1.setVisibility(View.GONE);
            shuliji2.setVisibility(View.GONE);
            yuanwang.setVisibility(View.GONE);
            juanrao.setVisibility(View.GONE);

            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line6.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line8.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            line11.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("shuliji1")) {
            shuiciji.setVisibility(View.GONE);
            shuliji2.setVisibility(View.GONE);
            kaiqing.setVisibility(View.GONE);
            yuanwang.setVisibility(View.GONE);
            juanrao.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            //  line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line6.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line8.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            line11.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("shuliji2")) {
            shuiciji.setVisibility(View.GONE);
            shuliji1.setVisibility(View.GONE);
            kaiqing.setVisibility(View.GONE);
            yuanwang.setVisibility(View.GONE);
            juanrao.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            //  line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line6.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line8.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            // line11.setVisibility(View.GONE);
        } else if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("shuiciji")) {
            kaiqing.setVisibility(View.GONE);
            shuliji1.setVisibility(View.GONE);
            shuliji2.setVisibility(View.GONE);
            yuanwang.setVisibility(View.GONE);
            juanrao.setVisibility(View.GONE);

            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line8.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            line11.setVisibility(View.GONE);
        }else if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("yuanwang")) {
            shuiciji.setVisibility(View.GONE);
            shuliji1.setVisibility(View.GONE);
            shuliji2.setVisibility(View.GONE);
            kaiqing.setVisibility(View.GONE);
            juanrao.setVisibility(View.GONE);

            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line6.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            //  line11.setVisibility(View.GONE);
//                juanrao.setVisibility(View.GONE);
        }
        else if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("juanrao")) {
            shuiciji.setVisibility(View.GONE);
            shuliji1.setVisibility(View.GONE);
            shuliji2.setVisibility(View.GONE);
            kaiqing.setVisibility(View.GONE);
            yuanwang.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            line4.setVisibility(View.GONE);
            line5.setVisibility(View.GONE);
            line6.setVisibility(View.GONE);
            line7.setVisibility(View.GONE);
            line9.setVisibility(View.GONE);
            line10.setVisibility(View.GONE);
            // line11.setVisibility(View.GONE);
//                juanrao.setVisibility(View.GONE);
        }
        if (getIntent().getStringExtra("flag") != null &&
                getIntent().getStringExtra("flag").equals("list")) {
            param.put("fjTechModelId", getIntent().getStringExtra("ID"));
            sendHttpGet(ApiService.QUERY_PROCESS_PRAAM_LIST, param, 10052);
        } else if (getIntent().getStringExtra("flag") != null &&
                getIntent().getStringExtra("flag").equals("id")) {
            param.put("fjProductionLineId", getIntent().getStringExtra("ID"));

            sendHttpGet(ApiService.QUERY_PROCESS_PRAAM_IDS, param, 10053);
        }
    }

    /**
     * 显示数据
     *
     * @param lists
     */
    private void showDatas(List<ProcessParamBean.RowsBean> lists) {
        for (int i = 0; i < lists.size(); i++) {
            try {
                String id = lists.get(i).getFjVarCode() + "_" + lists.get(i).getFjBusinessType();
                if (id.equals("SL1_XCWL_B")) {
                    outOfNet1 = Double.parseDouble(lists.get(i).getValue());
                    textOutOfNet1.setText(lists.get(i).getValue());//1#梳理出网
                    textSurfaceSpeed.setText(lists.get(i).getValue());//线速度
                } else if (id.equals("SL2_XCWL_B")) {
                    textOutOfNet2.setText(lists.get(i).getValue());//2#梳理出网
                } else if (id.equals("SL1_PDC_A")) {
                    textBeltScale1.setText(lists.get(i).getValue());//1#皮带秤
                } else if (id.equals("SL2_PDC_A")) {
                    textBeltScale2.setText(lists.get(i).getValue());//2#皮带秤
                }
                int view_id = R.id.class.getField(id).getInt(null);
                TextView view = findViewById(view_id);
                if (lists.get(i).getValue() != null) {
                    view.setText(lists.get(i).getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (SC_CL_B.getText().toString() != null && SC_CL_B.getText().toString() != "") {
            DecimalFormat df = new DecimalFormat("######0.0000");
            doubleLong = outOfNet1 * Double.parseDouble(SC_CL_B.getText().toString());
            textLongCurtain.setText(df.format(doubleLong) + "");//长帘
            if (SC_TW1_B.getText().toString() != null && SC_TW1_B.getText().toString() != "") {
                doubleTrawl = doubleLong * Double.parseDouble(SC_TW1_B.getText().toString());
                textTrawl.setText(df.format(doubleTrawl) + "");//拖网
                if (SC_GT1_B.getText().toString() != null && SC_GT1_B.getText().toString() != "") {
                    doubleRoller1 = doubleTrawl * Double.parseDouble(SC_GT1_B.getText().toString());
                    textRoller1.setText(df.format(doubleRoller1) + "");//锟筒1
                    if (SC_GT2_B.getText().toString() != null && SC_GT2_B.getText().toString() != "") {
                        doubleRoller2 = doubleRoller1 * Double.parseDouble(SC_GT2_B.getText().toString());
                        textRoller2.setText(df.format(doubleRoller2) + "");//锟筒2
                        if (SC_GT3_B.getText().toString() != null && SC_GT3_B.getText().toString() != "") {
                            doubleRoller3 = doubleRoller2 * Double.parseDouble(SC_GT3_B.getText().toString());
                            textRoller3.setText(df.format(doubleRoller3) + "");//锟筒3
                            if (SC_TW2_B.getText().toString() != null && SC_TW2_B.getText().toString() != "") {
                                doubleTrawl2 = doubleRoller3 * Double.parseDouble(SC_TW2_B.getText().toString());
                                textTrawl2.setText(df.format(doubleTrawl2) + "");//拖网2
                                if (SC_YC_B.getText().toString() != null && SC_YC_B.getText().toString() != "") {
                                    doublePadder = doubleTrawl2 * Double.parseDouble(SC_YC_B.getText().toString());
                                    textPadder.setText(df.format(doublePadder) + "");//轧车
                                    if (SC_YW_B.getText().toString() != null && SC_YW_B.getText().toString() != "") {
                                        doublePressNet = doublePadder * Double.parseDouble(SC_YW_B.getText().toString());
                                        textPressNet.setText(df.format(doublePressNet) + "");//压网
                                        if (SC_YWG_B.getText().toString() != null && SC_YWG_B.getText().toString() != "") {
                                            doublePreesNetKun = doublePressNet * Double.parseDouble(SC_YWG_B.getText().toString());
                                            textPressNetKun.setText(df.format(doublePreesNetKun) + "");//压网锟
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            ToastUtils.showShort("获取参数失败！");
        }
    }

    @OnClick(R.id.toolbar)
    public void onFinish() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            mSocket.cancel();
        }
    }
}
