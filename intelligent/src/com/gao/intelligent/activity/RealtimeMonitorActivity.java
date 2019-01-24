package com.gao.intelligent.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gao.intelligent.R;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.KeyBean;
import com.gao.intelligent.model.LineParamBean;
import com.gao.intelligent.model.LineValueBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by gaoyanbin on 2018/4/18.
 * 描述:实时监测 页面
 */
public class RealtimeMonitorActivity extends BaseActivity {

    //    @BindView(R.id.rl_no_data)
//    View rlNoData;//暂无数据
    //  @BindView(R.id.listview_data)
    //   GridView listviewData;// 动态数据
    @BindView(R.id.mTitle)
    TextView mTitleName;

    @BindView(R.id.text_1)
    TextView text1;//产品规格
    @BindView(R.id.text_2)
    TextView text2; //产品克重
    @BindView(R.id.text_3)
    TextView text3;//产品幅宽
    @BindView(R.id.text_4_1)
    TextView text4_1;//原料配比1
    @BindView(R.id.text_4_2)
    TextView text4_2;//原料配比1
    @BindView(R.id.text_4_3)
    TextView text4_3;//原料配比3

    @BindView(R.id.text_5)
    TextView text5;//生产速度
    @BindView(R.id.text_6)
    TextView text6;//卷绕长度
    @BindView(R.id.text_7)
    TextView text7;//运转时长
    @BindView(R.id.text_8)
    TextView text8;//运转率
    @BindView(R.id.text_9)
    TextView text9;//月产量
    @BindView(R.id.text_10)
    TextView text10;//班产量
    @BindView(R.id.text_11)
    TextView text11;//月水能耗
    @BindView(R.id.text_12)
    TextView text12;//班水能耗
    @BindView(R.id.text_13)
    TextView text13;//月电能耗
    @BindView(R.id.text_14)
    TextView text14;//班电能耗
    @BindView(R.id.text_15)
    TextView text15;//月气能耗
    @BindView(R.id.text_16)
    TextView text16;//班气能耗
    @BindView(R.id.text_1_1)
    TextView text1_1;//产品卷号
    // private String stringData = "";
    private String lineID;
    private List<LineParamBean.RowsBean> mParamsLists = new ArrayList<>();
    private String dataID = "";
    String changMessage, changMessage1, scodeId;
    private WebSocket mSocket;
    //private LineParamAdapter mAdapter;
    KeyBean keyBean;
    KeyBean keyBean1;
    private int count = 10;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_realtime_monitor;
    }

    @Override
    public void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        if (getIntent().getStringExtra("lineID") != null) {
//            lineID = getIntent().getStringExtra("lineID");
//        }
//        if (getIntent().getStringExtra("lineName") != null) {
//            mTitleName.setText(getIntent().getStringExtra("lineName"));
//
//        }
        // sendHttpGet(ApiService.QUERY_VENDER_LIST,new HashMap<String, String>(),);
//        showProgressDialog();
        HashMap<String, String> apiParam = new HashMap<>();
//        LogUtils.d("lineid", lineID);

        apiParam.put("fjProductionLineId", "402881e85ad53f8a015ad5ad3a000002");
        /**
         * 获取参数ids
         */
        sendHttpGet(ApiService.QUERY_LINE_VALUES, apiParam, 1111);
    }

    /**
     * 开启长连接
     */
    private void initSocket() {
        showProgressDialog("加载中...");
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Request request = new Request.Builder().url("ws:/" + ApiService.base + "websocket/androidConfig").build();
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
//            LogUtils.i("对象", changMessage);
            mSocket.send(changMessage);
            // output("连接成功！");
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
            LogUtils.i("实时刷新数据" + count, text);
            output(text);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //  ToastUtils.showShort(scodeId+"code");
                    count++;
                    if (scodeId != null && scodeId.equals("0011") && count > 9) {
                        count = 0;
                        LogUtils.i("ssss",changMessage+count);
                        mSocket.send(changMessage1);
//                        mSocket.send(new File());
                    } else {
                        LogUtils.i("ssss1",changMessage+count);
                        mSocket.send(changMessage);

                    }
                }
            }, 1500);
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
                LineValueBean lineValueBean = JSON.parseObject(content, LineValueBean.class);
                scodeId = lineValueBean.getScode();
                if (lineValueBean.getScode().equals("0011")) {
                    ArrayList<LineValueBean.DataBean> mLists = (ArrayList<LineValueBean.DataBean>) lineValueBean.getData();
                    if (mLists != null) {
                        for (int i = 0; i < mLists.size(); i++) {
                            if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("产品订单")) {
                                text1.setText(mLists.get(i).getValue());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("产品克重")) {
                                text2.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("当前卷号")) {
                                text1_1.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("产品幅宽")) {
                                text3.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("原料T")) {
                                text4_1.setText(" T " + mLists.get(i).getValue());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("原料R")) {
                                text4_3.setText(" R " + mLists.get(i).getValue());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("原料C")) {
                                text4_2.setText(" C " + mLists.get(i).getValue());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("卷绕速度")) {
                                text5.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("卷绕长度")) {
                                text6.setText(mLists.get(i).getValue() + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("运转时长（小时）")) {
                                text7.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("运转率")) {
                                text8.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            }
                        }
                    }
                } else {
                    ArrayList<LineValueBean.DataBean> mLists = (ArrayList<LineValueBean.DataBean>) lineValueBean.getData();
                    if (mLists != null) {
                        for (int i = 0; i < mLists.size(); i++) {
                            if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("月产量")) {
                                text9.setText(Utils.formateRate(mLists.get(i).getValue()) + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("月水消耗")) {
                                text11.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("月电消耗")) {
                                text13.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("月气消耗")) {
                                text15.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("班气消耗")) {
                                text16.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("班产量")) {
                                text10.setText(Utils.formateRate(mLists.get(i).getValue()) + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("班水消耗")) {
                                text12.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            } else if (mLists.get(i).getName() != null && mLists.get(i).getName().equals("班电消耗")) {
                                text14.setText(mLists.get(i).getValue() + " " + mLists.get(i).getVarunit());
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void aidHandleMessage(int what, int type, Object obj) {
        super.aidHandleMessage(what, type, obj);
        switch (what) {
            case 10004:
                hideCustomProgressDialog();
                LogUtils.d("参数数据IDS", obj);
                if (obj.equals("401")) {
                    ToastUtils.showShort("登录超时，请重新登录");
                    exitApp();
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                LineParamBean paramBean = JSON.parseObject(obj.toString(), LineParamBean.class);
                if (paramBean.getRows() != null) {
                    mParamsLists = paramBean.getRows();
                    if (mParamsLists.size() > 0) {
                        for (int i = 0; i < mParamsLists.size(); i++) {
                            dataID += mParamsLists.get(i).getId() + ",";
                        }
                        keyBean = new KeyBean();
                        keyBean.setScode("A1000");
                        keyBean.setStype("0");
                        keyBean.setRemark(lineID);
                        KeyBean.SdataBean m = new KeyBean.SdataBean();
                        m.setIds(dataID + lineID + "ZX_YLPB1," + lineID + "ZX_YLPB2," + lineID + "ZX_YLPB3,"
                                + lineID + "ZX_CPJH," +
                                lineID + "ZX_CPDDH," + lineID + "ZX_CPKZ," + lineID + "ZX_CPFK," +
                                lineID + "ZX_YZSC," + lineID + "ZX_YZL," +
                                lineID + "ZX_YCL," + lineID + "ZX_YSNH,"
                                + lineID + "ZX_YDNH," + lineID + "ZX_YQNH," +
                                lineID + "ZX_BCL," + lineID + "ZX_BSNH," + lineID + "ZX_BDNH," +
                                lineID + "ZX_BQNH,");
                        keyBean.setSdata(m);
                        changMessage = new Gson().toJson(keyBean);
                        initSocket();
                        keyBean1 = new KeyBean();
                        keyBean1.setScode("A1004");
                        keyBean1.setStype("0");
                        KeyBean.SdataBean n = new KeyBean.SdataBean();
                        n.setIds("402881e85ad53f8a015ad5ad3a000002");
                        keyBean1.setSdata(n);
                        changMessage1 = new Gson().toJson(keyBean1);
                    } else {
                        ToastUtils.showShort("数据获取失败！");
                    }
                }
                break;
            case 10003:
                hideProgressDialog();
                hideCustomProgressDialog();
                ToastUtils.showShort(obj.toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            mSocket.cancel();
        }
    }
}
