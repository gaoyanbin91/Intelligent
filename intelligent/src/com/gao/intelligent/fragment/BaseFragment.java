package com.gao.intelligent.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gao.intelligent.dialog.CustomProgressDialog;
import com.gao.intelligent.dialog.materialdialogs.MaterialDialog;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.okhttp.callback.ResultCallback;
import com.gao.intelligent.utils.okhttp.request.OkHttpRequest;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by gaoyanbin on 2018/6/27.
 * 描述:
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getName();
    public static final int NETWORK_SUCCESSFUL_TYPE = 10004;

    private MaterialDialog alertDialog;
    MaterialDialog progressDialog;

    CustomProgressDialog customProgressDialog;

    private View rootView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                switch(msg.what) {


                    case 10003:
                        BaseFragment.this.hideProgressDialog();
                        BaseFragment.this.hideCustomProgressDialog();
                        if (msg.obj == null) {
                            return;
                        }
                        BaseFragment.this.aidHandleMessage(msg.what, msg.obj);
                        // LibraryNotifyMgr.showShortToast(String.valueOf(msg.obj));
                        break;
                    case NETWORK_SUCCESSFUL_TYPE:
                        BaseFragment.this.aidHandleMessage(msg.what, msg.obj);
                        break;
                    case 10005:
                        BaseFragment.this.aidHandleMessage(msg.what, msg.obj);
                        break;

                    default:
                        BaseFragment.this.aidHandleMessage(msg.what, msg.obj);
                }
            } catch (Throwable var3) {
                Log.e(BaseFragment.TAG, "handleMessage", var3);
            }

        }
    };
    public BaseFragment() {
    }

    public void aidHandleMessage(int what, Object obj) {
    }

    public void aidsendMessage(int what, Object obj) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = what;
        msg.obj = obj;
        this.mHandler.sendMessage(msg);
    }

    public void aidsendMessage(int what, int arg1, Object obj) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = what;
        msg.arg1 = arg1;
        msg.obj = obj;
        this.mHandler.sendMessage(msg);
    }

    public void aidsendMessage(int what, Object obj, long delayMillis) {
        Message msg = this.mHandler.obtainMessage();
        msg.what = what;
        msg.obj = obj;
        this.mHandler.sendMessageDelayed(msg, delayMillis);
    }


    public void showProgressDialog(boolean cancancelable) {
        customProgressDialog = new CustomProgressDialog(getActivity(),"");
        customProgressDialog.setCancelable(true);
        customProgressDialog.show();


    }
    public void showProgressDialog(String text) {
        if (customProgressDialog == null) {

            customProgressDialog = new CustomProgressDialog(getActivity(),text);
            customProgressDialog.setCanceledOnTouchOutside(false);
            //customProgressDialog.setCancelable(true);
        }
        customProgressDialog.show();


    }


    public void showProgressDialog() {
        showProgressDialog(false);
    }

    public void hideCustomProgressDialog() {

        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }

    }

    public void hideProgressDialog() {
        hideCustomProgressDialog();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getFragmentViewLayout() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        try {
            if (rootView == null) {
                rootView = inflater.inflate(getFragmentViewLayout(), null);
            }
            ButterKnife.bind(this, rootView);
            initView();
            initData();
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        } catch (Throwable e) {
            //    LogUtils.e(TAG, e);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        //移除当前视图，防止重复加载相同视图使得程序闪退
        if (rootView != null && rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    public abstract int getFragmentViewLayout();
    protected abstract void initView();
    protected abstract void initData();

    public void sendHttpGet(String url, HashMap<String, String> map, final int refreshType) {
        try {
            HashMap<String, String> heard = new HashMap<>();
            heard.put("token", AppConfig.getInstance().getString("token", ""));
            LogUtils.info("GET请求url==>" + url);
            LogUtils.info("GET请求token==>" + AppConfig.getInstance().getString("token", ""));

            (new  OkHttpRequest.Builder()).url(url).params(map).headers(heard).get(new ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {

                    LogUtils.e(BaseFragment.TAG, e.getMessage() + "===" + e.getLocalizedMessage());
                    if (e instanceof UnknownHostException) {
                        aidsendMessage(10003, "当前网络不可用，请检查网络再试");
                    } else if (e instanceof SocketTimeoutException) {
                        aidsendMessage(10003, "请求超时，请稍后再试");
                    } else {
                        aidsendMessage(10003, "网络异常，请稍后再试");
                    }
                }
                @Override
                public void onResponse(String response) {

                    aidsendMessage(NETWORK_SUCCESSFUL_TYPE, response);
                    //    LogUtils.d("shuj",response.toString());
                }
            });
        } catch (Throwable var6) {
            Log.e(TAG, "sendHttp", var6);
        }

    }


    public void sendHttpPost(String url, HashMap<String, String> map,final int refreshType) {
        try {
            HashMap<String, String> heard = new HashMap<>();
            heard.put("token", AppConfig.getInstance().getString("token", ""));
            LogUtils.info("POST请求url==>" + url);
            LogUtils.info("POST请求token==>" + AppConfig.getInstance().getString("token", ""));
            (new  OkHttpRequest.Builder()).url(url).params(map).headers(heard).post(new ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {

                    LogUtils.e(BaseFragment.TAG, e.getMessage() + "===" + e.getLocalizedMessage());
                    if (e instanceof UnknownHostException) {
                        aidsendMessage(10003, "当前网络不可用，请检查网络再试");
                    } else if (e instanceof SocketTimeoutException) {
                        aidsendMessage(10003, "请求超时，请稍后再试");
                    } else {
                        aidsendMessage(10003, "网络异常，请稍后再试");
                    }
                    // LibraryActivity.this.aidsendMessage(10005, responseBean);
                }
                @Override
                public void onResponse(String response) {
                    aidsendMessage(NETWORK_SUCCESSFUL_TYPE, response);
                }
            });
        } catch (Throwable var6) {
            Log.e(TAG, "sendHttp", var6);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
