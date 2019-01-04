package com.gao.intelligent.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.MainActivity;
import com.gao.intelligent.dialog.CustomProgressDialog;
import com.gao.intelligent.dialog.materialdialogs.DialogAction;
import com.gao.intelligent.dialog.materialdialogs.MaterialDialog;
import com.gao.intelligent.utils.AppConfig;
import com.gao.intelligent.utils.KeyBoardUtils;
import com.gao.intelligent.utils.LogUtils;
import com.gao.intelligent.utils.ToastUtils;
import com.gao.intelligent.utils.okhttp.callback.ResultCallback;
import com.gao.intelligent.utils.okhttp.request.OkHttpRequest;
import com.gao.intelligent.view.SystemBarTintManager;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by gaoyanbin on 2018/6/26.
 * 描述:activity封装基类
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context mContext ;
    public Context baseContext;
    public static final int DISMISS_ALERT_DIALOG_TYPE = 100000;
    private final static String KEY_HAS_INTERSTITIAL_AD = "KEY_HAS_INTERSTITIAL_AD";
    protected boolean mHasInterstitialAd = false;

    private static final String TAG = BaseActivity.class.getName();
    CustomProgressDialog customProgressDialog;
    public static final int CUSTOM_DISMISS = 6;
    private static long mPreTime;
    public static List<Activity> mActivities = new LinkedList<>();
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public MaterialDialog alertDialog;
    MaterialDialog progressDialog;
    private AlertDialog mAlertDialog;
    private boolean systemBar = true;
    int statusBarHeight = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch(msg.what) {

                    case 100000:
                        if ( mAlertDialog != null &&  mAlertDialog.isShowing()) {
                            mAlertDialog.dismiss();
                        }
                        break;
                    case 10003:

                     //   dismissAlertDialog();
                      //  dismissProgressDialog();
                        if (msg.obj == null) {
                            return;
                        }

                        aidHandleMessage(msg.what,msg.arg1, msg.obj);
                        break;
                    case 10004:
                        aidHandleMessage(msg.what,msg.arg1, msg.obj);
                        break;
                    case 10005:
                        aidHandleMessage(msg.what, msg.obj);
                        break;

                    default:
                        aidHandleMessage(msg.what, msg.obj);
                }
            } catch (Throwable var3) {
                Log.e(BaseActivity.TAG, "handleMessage", var3);
            }

        }
    };

    public BaseActivity() {
    }
    public void aidHandleMessage(int what, Object obj) {
    }
    public void aidHandleMessage(int what,int type, Object obj) {
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
    public void setSystemBar(boolean systemBar) {
        this.systemBar = systemBar;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getApplication()).getActivityHashMap().put(this.getClass().getName(), this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_HAS_INTERSTITIAL_AD)) {
                mHasInterstitialAd = savedInstanceState.getBoolean(KEY_HAS_INTERSTITIAL_AD);
                savedInstanceState.remove(KEY_HAS_INTERSTITIAL_AD);
            }
        }
        mContext = this;

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {

            mActivities.add(this);
        }
        //BaseActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
        initView();
        initData();
        baseContext = this;
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && systemBar) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            statusBarHeight = tintManager.getConfig().getStatusBarHeight();

            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            //tintManager.setTintColor(getResources().getColor(R.color.activity_login_orange));
            tintManager.setTintDrawable(getResources().getDrawable(R.drawable.status_background));

        }
    }

    public void sendHttpGet(String url, HashMap<String, String> map, final int refreshType) {

        try {
            HashMap<String, String> heard = new HashMap<>();
            heard.put("token", AppConfig.getInstance().getString("token", ""));
             LogUtils.info("GET请求url==>" + url+map.get("id"));
             LogUtils.info("GET请求token==>" + AppConfig.getInstance().getString("token", ""));

            (new  OkHttpRequest.Builder()).url(url).params(map).headers(heard).get(new ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {

                    LogUtils.e(BaseActivity.TAG, e.getMessage() + "===" + e.getLocalizedMessage());

                    if (e instanceof UnknownHostException) {
                        aidsendMessage(10003, refreshType,"当前网络不可用，请检查网络再试");
                    } else if (e instanceof SocketTimeoutException) {
                        aidsendMessage(10003, refreshType,"请求超时，请稍后再试");
                    } else {
                        aidsendMessage(10003, refreshType,"网络异常，请稍后再试");
                    }
                }
                @Override
                public void onResponse(String response) {

                    aidsendMessage(10004, refreshType,response);
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

                    LogUtils.e( BaseActivity.TAG, e.getMessage() + "===" + e.getLocalizedMessage());
                    if (e instanceof UnknownHostException) {
                        aidsendMessage(10003, refreshType,"当前网络不可用，请检查网络再试");
                    } else if (e instanceof SocketTimeoutException) {
                        aidsendMessage(10003, refreshType,"请求超时，请稍后再试");
                    } else {
                        aidsendMessage(10003, refreshType,"网络异常，请稍后再试");
                    }
                    // LibraryActivity.this.aidsendMessage(10005, responseBean);
                }
                @Override
                public void onResponse(String response) {
                    aidsendMessage(10004,refreshType, response);
                }
            });
        } catch (Throwable var6) {
            Log.e(TAG, "sendHttp", var6);
        }

    }



    public void showProgressDialog(String text) {
        if (customProgressDialog == null) {

            customProgressDialog = new CustomProgressDialog(this,text);
            customProgressDialog.setCanceledOnTouchOutside(false);
            //customProgressDialog.setCancelable(true);
        }
        customProgressDialog.show();


    }

    public void hideCustomProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }

    public void hideProgressDialog() {
        hideCustomProgressDialog();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    public void initData()   {
    }
    public void initView() {
    }

    protected abstract int provideContentViewId();

    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }
    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                ToastUtils.showShort("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    public void showAlertDialog(String message) {
        this.showAlertDialog("提示", message, "确定", "取消", 100000, 100000, (Object)null, (Object)null);
    }

    public void showAlertDialog(String title, String message, String sureString, String cancelString, final int sureType, final int cancelType, final Object sureObject, final Object cancelObject) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }

            builder.setMessage(message).setNegativeButton(sureString, new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aidsendMessage(sureType, sureObject);
                }
            }).setPositiveButton(cancelString, new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aidsendMessage(cancelType, cancelObject);
                }
            });
            this.mAlertDialog = builder.create();
            this.mAlertDialog.show();
        } catch (Throwable var10) {
            Log.e(TAG, "showAlertDialog", var10);
        }
    }
    public void showAlertDialog(String title, String msg, String okString, String cancelString, View view, final int ok_handler, final int cancel_handler, final Object ok_obj, final Object cancel_obj) {
        try {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title(title)
                    .content(msg)
                    .contentColor(getResources().getColor(R.color.main_color))
                    .titleColor(getResources().getColor(R.color.main_color))
                    .positiveText(okString)
                    .positiveColor(getResources().getColor(R.color.main_color))
                    .negativeText(cancelString)
                    .negativeColor(getResources().getColor(R.color.main_color))
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            switch (which) {
                                case POSITIVE:
                                    aidsendMessage(ok_handler, ok_obj);
                                    break;
                                case NEGATIVE:
                                    aidsendMessage(cancel_handler, cancel_obj);
                                    break;
                            }

                        }
                    }).cancelable(false);

            alertDialog = builder.build();
            alertDialog.show();


        } catch (Throwable e) {
            Log.e(TAG + "==showAlertDialog", e.toString());
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) {
                return super.dispatchTouchEvent(ev);
            }
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0) {
                return super.dispatchTouchEvent(ev);
            }
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }


    /**
     * 隐藏键盘
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }
    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }
}
