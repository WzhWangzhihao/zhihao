package com.asia_eagle.money.erqing.activity;

import android.content.Intent;
import android.os.Bundle;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.util.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;


/**
 * 启动页
 */
public class StartPageAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start_page);
        SharedPreferencesHelper.saveString("key", "1");
        getVersion();
        InitUI();
    }

    public void InitUI() {
        new MyThread().start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            try {
                sleep(3000);
                Intent intent = new Intent(StartPageAct.this, LoginAct.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getVersion() {
        File file = new File(App.getSDPath() + "/money");
        // 判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdir();
        }
        String url = MyService.API_VERSIONURL;
        OkHttpUtils.get().url(url).build().
                execute(new FileCallBack(file.getAbsolutePath(), "version.json")//
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                    }
                });
    }
}