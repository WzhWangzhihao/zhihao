package com.asia_eagle.money.erqing.util;

import android.os.Bundle;

/**
 * 带有旋转对话框的CaptureActivity
 * @author JaYe
 * 
 */
public class ProgressCaptureActivity extends BaseCaptureActivity {
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		progressDialog = new ProgressDialog(this);
	}
	
	/**
	 * 显示对话框
	 * @param message
	 */
	public void progress(String message){
		progressDialog.show(message);
	}
	/**
	 * UI现成显示对话框
	 * @param message
	 */
	public void postProgress(final String message){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progress(message);
			}
		});
	}
	
	public void hideProgress(){
		progressDialog.hide();
	}
	
	@Override
	public void finish() {
		progressDialog.dismiss();
		super.finish();
	}

	@Override
	protected void onPhotoTaked(String photoPath) {
		// TODO Auto-generated method stub
		
	}
}
