package com.gao.intelligent.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.view.WDSeekBar;


public class DownloadDialog extends Dialog {

	Context context;
	public  static TextView tv_content,tv_content2;
	public String content;
	private String title;
	public  static WDSeekBar wdSeekBar;

	public DownloadDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		this.tv_content2 = tv_content2;
		this.tv_content = tv_content;
		this.title = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_download);
		initViews();
	}

	private void initViews() {
		tv_content = (TextView) findViewById(R.id.dialog_content);
		tv_content.setText(this.content);
		tv_content2 = (TextView) findViewById(R.id.dialog_content2);
		tv_content2.setText(this.content);

		wdSeekBar = (WDSeekBar) findViewById(R.id.pb_product_progress);

	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (mListener != null) {
			mListener.dismiss();
		}
	}

	public UpdateOnclickListener mListener = null;

	public void setUpdateOnClickListener(UpdateOnclickListener mListener) {
		this.mListener = mListener;
	}

	public interface UpdateOnclickListener {
		public void dismiss();

		public void BtnYesOnClickListener(View v);

		public void BtnCancleOnClickListener(View v, WDSeekBar bar);
	}
	
}
