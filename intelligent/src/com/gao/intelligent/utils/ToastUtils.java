package com.gao.intelligent.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.view.HandyTextView;


@SuppressLint("InflateParams")
public class ToastUtils {
	
	private static Toast toast;
	private static Context context = MyApp.getContext();
	private static View toastRoot = LayoutInflater.from(context).inflate(
			R.layout.common_toast, null);
	
	// 可以使ToastUtils在非UI线程中运行
	private static Handler UIHandler = MyApp.getInstance().handler;

	public enum ToastDisplayTime {
		TOAST_DISPLAY_LONG, TOAST_DISPLAY_SHORT
	}
	private ToastUtils() {
	}

	@SuppressLint("ShowToast")
	private static void checkToast() {
		if (toast == null) {
			toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
		}
	}

	private static void setToastDisplayTime(ToastDisplayTime time) {
		if (time == ToastDisplayTime.TOAST_DISPLAY_LONG)
			toast.setDuration(Toast.LENGTH_LONG);
		else {
			toast.setDuration(Toast.LENGTH_SHORT);
		}
	}

	private static void show(String msg, ToastDisplayTime time) {
		checkToast();
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(toastRoot);
		toast.show();
	}

	private static void show(int msg, ToastDisplayTime time) {
		checkToast();
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(toastRoot);
		toast.show();
	}
	
	private static void show(String msg, ToastDisplayTime time, int position){
		checkToast();
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(position, 0, 0);
		toast.setView(toastRoot);
		toast.show();
	}
	
	private static void show(int msg, ToastDisplayTime time, int position){
		checkToast();
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(position, 0, 0);
		toast.setView(toastRoot);
		toast.show();
	}

	public static void showLong(String msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_LONG);
	}

	public static void showLong(int msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_LONG);
	}

	public static void showShort(String msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}

	public static void showShort(int msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}
	
	public static void showShortWithPositon(String msg, int position){
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT,position);
	}
	public static void showShortWithPositon(int msg,int position){
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT,position);
	}

	public static void showInUiThread(final String msg) {
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				showShort(msg);
			}
		});
	}

	public static void exit() {
		if (null != toast) {
			toast.cancel();
			toast = null;
		}
	}
}
