package com.asia_eagle.money.erqing.activity;

import android.os.Bundle;

import com.asia_eagle.money.erqing.R;


public class OkReceivablesAct extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.ac_okreceivables);
        Init();
    }

    @Override
    public void clickLeft() {
        super.clickLeft();
        finish();
    }

    private void Init() {
        setTitle("返回首页");
    }

}
