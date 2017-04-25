package com.asia_eagle.money.erqing.util;

import android.app.Application;
import android.widget.Toast;


public class BaseApplication extends Application{
	public static BaseApplication instance;

	public void onCreate() {
		super.onCreate();
		instance = this;
		//============== 工具类初始化, 必须调用! ========
		//======================================
	};
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public static BaseApplication getInstance() {
		return instance;
	}
	
	private static Toast toast;

	public static void toast(int resId){
		toast(getInstance().getString(resId), Toast.LENGTH_SHORT);
	}
	public static void toastShort(int resId){
		toast(getInstance().getString(resId), Toast.LENGTH_SHORT);
	}

	public static void toastLong(int resId){
		toast(getInstance().getString(resId), Toast.LENGTH_LONG);
	}

	public static void toast(String s){
		toast(s, Toast.LENGTH_SHORT);
	}
	public static void toastShort(String s){
		toast(s, Toast.LENGTH_SHORT);
	}

	public static void toastLong(String s){
		toast(s, Toast.LENGTH_LONG);
	}

	private static void toast(String s, int length){
		try{
			if(toast != null){
				toast.setText(s);
			}else{
				toast = Toast.makeText(getInstance(), s, length);
			}
			toast.show();
		}catch(Exception e){}
	}
}