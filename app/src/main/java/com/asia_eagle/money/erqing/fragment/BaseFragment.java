package com.asia_eagle.money.erqing.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * fragment基类
 * 
 * @author wzh
 * 
 */
public class BaseFragment extends Fragment {
	public static String TAG = "";
	public Context context;
	/**
	 * Fragment视图
	 */
	public View contentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getSimpleName();
		
	}


	/**
	 * 设置视图
	 * 
	 * @param inflater
	 * @param layout
	 */
	public void setContentView(LayoutInflater inflater, int layout) {
		contentView = inflater.inflate(layout, null);
		context=getActivity().getApplicationContext();
	}

	/**
	 * 找到view
	 * 
	 * @param resId
	 * @return
	 */
	public View findViewById(int resId) {
		return contentView.findViewById(resId);
	}

	/**
	 * 找到TextView
	 * 
	 * @param resId
	 * @return
	 */
	public TextView findTextViewById(int resId) {
		return (TextView) findViewById(resId);
	}

	/**
	 * 找到EditView
	 * 
	 * @param resId
	 * @return
	 */
	public EditText findEditTextViewById(int resId) {
		return (EditText) findViewById(resId);
	}

	/**
	 * 找到Button
	 * 
	 * @param resId
	 * @return
	 */
	public Button findButtonById(int resId) {
		return (Button) findViewById(resId);
	}

	/**
	 * 找到Button
	 * 
	 * @param view
	 * @param resId
	 * @return
	 */
	public Button findButtonById(View view, int resId) {
		return (Button) view.findViewById(resId);
	}

	/**
	 * 找到LinearLayout
	 * 
	 * @param resId
	 * @return
	 */
	public LinearLayout findLinearLayoutById(int resId) {
		return (LinearLayout) findViewById(resId);
	}

	/**
	 * 找到RelativeLayout
	 * 
	 * @param resId
	 * @return
	 */
	public RelativeLayout findRelativeLayoutById(int resId) {
		return (RelativeLayout) findViewById(resId);
	}

	/**
	 * 找到ListView
	 * 
	 * @param resId
	 * @return
	 */
	public ListView findListViewById(int resId) {
		return (ListView) findViewById(resId);
	}

	/**
	 * 找到ImageView
	 * 
	 * @param resId
	 * @return
	 */
	public ImageView findImageViewById(int resId) {
		return (ImageView) findViewById(resId);
	}

	/**
	 * 阻拦onBackPressed事件
	 * 
	 * @return
	 */
	public boolean onBackPressed() {
		return false;
	}

	/**
	 * 右边第一个按钮点击
	 */
	public void onIconRight1Click(View v) {

	}

	/**
	 * 右边第二个按钮点击
	 */
	public void onIconRight2Click(View v) {

	}
}
