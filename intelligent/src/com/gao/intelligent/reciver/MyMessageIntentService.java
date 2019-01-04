/*
package com.gao.intelligent.reciver;

import android.content.Context;

import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

*/
/**
 * Created by Yanbin on 2018/11/20.
 * 描述:
 *//*

public class MyMessageIntentService   extends AliyunMessageIntentService {
    private static final String TAG = "MyMessageIntentService";

    */
/**
     * 推送通知的回调方法
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     *//*

    @Override
    protected void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        Log.i(REC_TAG,"收到一条推送通知 ： " + title + ", summary:" + summary);
//        MainApplication.setConsoleText("收到一条推送通知 ： " + title + ", summary:" + summary);
    }

    */
/**
     * 推送消息的回调方法
     * @param context
     * @param cPushMessage
     *//*

    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        Log.i(REC_TAG,"收到一条推送消息 ： " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
//        MainApplication.setConsoleText(cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    */
/**
     * 从通知栏打开通知的扩展处理
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     *//*

    @Override
    protected void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.i(REC_TAG,"onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
//        MainApplication.setConsoleText("onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    */
/**
     * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     *//*

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.i(REC_TAG,"onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
        MainApplication.setConsoleText("onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    */
/**
     * 通知删除回调
     * @param context
     * @param messageId
     *//*

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.i(REC_TAG, "onNotificationRemoved ： " + messageId);
        MainApplication.setConsoleText("onNotificationRemoved ： " + messageId);
    }

    */
/**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,相关详情请参考https://help.aliyun.com/document_detail/30066.html#h3-3-4-basiccustompushnotification-api
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     * @param openType
     * @param openActivity
     * @param openUrl
     *//*

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.i(REC_TAG,"onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
        MainApplication.setConsoleText("onNotificationReceivedInApp ： " + " : " + title + " : " + summary);
    }
}*/
