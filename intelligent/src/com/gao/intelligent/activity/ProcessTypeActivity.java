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
import com.gao.intelligent.model.ParamIdsBean;
import com.gao.intelligent.model.ParamsBean;
import com.gao.intelligent.model.ProcessParamBean;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.google.gson.Gson;

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
 * Created by Yanbin on 2018/9/12.
 * 描述:工艺分类
 */
public class ProcessTypeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String lineID;
    private List<ProcessParamBean.RowsBean> mLists = new ArrayList<>();
    private List<ParamsBean.DataBean> trensLists = new ArrayList<>();

    private WebSocket mSocket;
    String changMessage;
    private List<String> mParamsLists = new ArrayList<>();
    private String dataID = "";
    KeyBean keyBean;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_process_type;
    }

    @Override
    public void initView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
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
     * 显示数据
     *
     * @param lists
     */
    private void showDatas(List<ProcessParamBean.RowsBean> lists) {
        for (int i = 0; i < lists.size(); i++) {
            try {
                String id = lists.get(i).getFjVarCode() + "_" + lists.get(i).getFjBusinessType();

                int view_id = R.id.class.getField(id).getInt(null);
                TextView view = findViewById(view_id);
                if (lists.get(i).getValue() != null) {
                    view.setText(lists.get(i).getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        Request request = new Request.Builder().url("ws:/106.15.205.62:80/websocket/androidConfig").build();
        ProcessTypeActivity.EchoWebSocketListener socketListener = new ProcessTypeActivity.EchoWebSocketListener();
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

                                int view_id = R.id.class.getField(id).getInt(null);
                                TextView view = findViewById(view_id);
                                if (trensLists.get(i).getValue() != null) {
                                    view.setText(trensLists.get(i).getValue());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            }
        });
    }
    @Override
    public void initData() {
        if (getIntent().getStringExtra("ID") != null) {
            lineID = getIntent().getStringExtra("ID");
        }
        HashMap<String, String> param = new HashMap<>();
        showProgressDialog("加载中..");
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

    @OnClick({R.id.ll_base, R.id.ll_shuliji1, R.id.ll_shuliji2, R.id.ll_shuiciji, R.id.ll_yuanwang, R.id.ll_juanrao})
    public void onClick(View view) {
        Intent paramTem = new Intent(baseContext, ProcessParamActivity.class);

        switch (view.getId()) {
            case R.id.ll_base:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "base");
                paramTem.putExtra("name", "开清");
                startActivity(paramTem);
                break;
            case R.id.ll_shuliji1:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "shuliji1");
                paramTem.putExtra("name", "1#梳理机");
                startActivity(paramTem);
                break;
            case R.id.ll_shuliji2:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "shuliji2");
                paramTem.putExtra("name", "2#梳理机");

                startActivity(paramTem);
                break;
            case R.id.ll_shuiciji:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "shuiciji");
                paramTem.putExtra("name", "水刺机");
                startActivity(paramTem);
                break;
            case R.id.ll_yuanwang:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "yuanwang");
                paramTem.putExtra("name", "圆网烘干");
                startActivity(paramTem);
                break;
            case R.id.ll_juanrao:
                paramTem.putExtra("ID", getIntent().getStringExtra("ID"));
                paramTem.putExtra("flag", getIntent().getStringExtra("flag"));
                paramTem.putExtra("type", "juanrao");
                paramTem.putExtra("name", "卷绕");
                startActivity(paramTem);
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
