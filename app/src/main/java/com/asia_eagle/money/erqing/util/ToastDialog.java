package com.asia_eagle.money.erqing.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;


/**
 * toast
 * @author JiaYe 2014-8-13
 *
 */
public class ToastDialog extends Dialog{
	final static String TAG = ToastDialog.class.getSimpleName();
	public ToastDialog(Context context) {
		super(context, R.style.Dialog);
		setCanceledOnTouchOutside(true);
	}

	private String info = null;
	private TextView tv;
	private LinearLayout root;
	private int bgResid = -1;
	private int textColor = Color.BLACK;
	private int textSize = 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_toast);
		tv = (TextView) findViewById(R.id.tv);
		root = (LinearLayout)findViewById(R.id.root);

		if(bgResid != -1){
			root.setBackgroundResource(bgResid);
		}
		root.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				hide();
			}
		});
		tv.setTextColor(textColor);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
		tv.setText(info);
	}

	/**
	 * 设置背景
	 * @param resid
	 * @return
	 */
	public ToastDialog setBackgroundResource(int resid){
		bgResid = resid;
		if(root != null){
			root.setBackgroundResource(bgResid);
		}
		return this;
	}

	/**
	 *  设置字体颜色
	 * @param color
	 * @return
	 */
	public ToastDialog setTextColor(int color){
		textColor = color;
		if(tv != null){
			tv.setTextColor(color);
		}
		return this;
	}

	public ToastDialog setTextSizeSp(int sp){
		textSize = sp;
		if(tv != null){
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
		}
		return this;
	}

	/**
	 * 指定毫秒数以后自动销毁
	 * @param msg
	 * @param delayMs
	 * @return
	 */
	public ToastDialog show(String msg, int delayMs){
		info = msg;
		if(tv != null){
			tv.setText(msg);
		}
		super.show();
		handler.removeCallbacks(close);
		handler.postDelayed(close, delayMs);
		return this;
	}

	public ToastDialog show(String msg){
		info = msg;
		if(tv != null){
			tv.setText(msg);
		}
		super.show();
		return this;
	}

	private Handler handler = new Handler();
	private Runnable close = new Runnable() {
		@Override
		public void run() {
			dismiss();
		}
	};

	/**
	 * 指定毫秒数以后自动销毁
	 * @param msg
	 * @param delayMs
	 * @return
	 */
	public ToastDialog showHandler(String msg, int delayMs,final Activity ac){
		info = msg;
		if(tv != null){
			tv.setText(msg);
		}
		super.show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
				ac.finish();
			}
		}, delayMs);
		return this;
	}
}