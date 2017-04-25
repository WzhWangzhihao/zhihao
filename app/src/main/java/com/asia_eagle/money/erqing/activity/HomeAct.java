package com.asia_eagle.money.erqing.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.fragment.BaseFragment;
import com.asia_eagle.money.erqing.fragment.FragmentDayKnot;
import com.asia_eagle.money.erqing.fragment.FragmentHome;
import com.asia_eagle.money.erqing.fragment.FragmentLiuShui;

import java.util.ArrayList;


public class HomeAct extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_1, ll_2,ll_3;
    private View v_1, v_2,v_3;
    private ImageButton ib_1, ib_2,ib_3;
    private FragmentManager fragmentManager;// 定义fragment管理
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);

        /**
         * 初始化组件
         */
        initView();
    }


    /**
     * 初始化组件
     */
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        ll_1 = findLinearLayoutById(R.id.ll_1);
        ll_2 = findLinearLayoutById(R.id.ll_2);
        ll_3 = findLinearLayoutById(R.id.ll_3);
        v_1 = findViewById(R.id.v_1);
        v_2 = findViewById(R.id.v_2);
        v_3 = findViewById(R.id.v_3);
        ib_1 = (ImageButton) findViewById(R.id.ib_1);
        ib_2 = (ImageButton) findViewById(R.id.ib_2);
        ib_3 = (ImageButton) findViewById(R.id.ib_3);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        v_3.setOnClickListener(this);
        v_1.setOnClickListener(this);
        v_2.setOnClickListener(this);
        ib_1.setOnClickListener(this);
        ib_2.setOnClickListener(this);
        ib_3.setOnClickListener(this);
        showFragment(FragmentHome.class);
        ib_1.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                ib_1.setSelected(true);
                ib_2.setSelected(false);
                ib_3.setSelected(false);
                v_1.setVisibility(View.VISIBLE);
                v_2.setVisibility(View.INVISIBLE);
                v_3.setVisibility(View.INVISIBLE);
                showFragment(FragmentHome.class);
                break;
            case R.id.ib_1:
                ib_1.setSelected(true);
                ib_2.setSelected(false);
                ib_3.setSelected(false);
                v_1.setVisibility(View.VISIBLE);
                v_2.setVisibility(View.INVISIBLE);
                v_3.setVisibility(View.INVISIBLE);
                showFragment(FragmentHome.class);
                break;
            case R.id.ib_2:
                ib_1.setSelected(false);
                ib_2.setSelected(true);
                ib_3.setSelected(false);
                v_1.setVisibility(View.INVISIBLE);
                v_3.setVisibility(View.INVISIBLE);
                v_2.setVisibility(View.VISIBLE);
                showFragment(FragmentLiuShui.class);
                break;
            case R.id.ll_2:
                ib_1.setSelected(false);
                ib_2.setSelected(true);
                ib_3.setSelected(false);
                v_1.setVisibility(View.INVISIBLE);
                v_2.setVisibility(View.VISIBLE);
                v_3.setVisibility(View.INVISIBLE);
                showFragment(FragmentLiuShui.class);
                break;
            case R.id.ll_3:
                ib_1.setSelected(false);
                ib_2.setSelected(false);
                ib_3.setSelected(true);
                v_1.setVisibility(View.INVISIBLE);
                v_2.setVisibility(View.INVISIBLE);
                v_3.setVisibility(View.VISIBLE);
                showFragment(FragmentDayKnot.class);
                ((FragmentDayKnot)currentFragment).showDate();
                break;
            case R.id.ib_3:
                ib_1.setSelected(false);
                ib_2.setSelected(false);
                ib_3.setSelected(true);
                v_1.setVisibility(View.INVISIBLE);
                v_2.setVisibility(View.INVISIBLE);
                v_3.setVisibility(View.VISIBLE);
                showFragment(FragmentDayKnot.class);
                ((FragmentDayKnot)currentFragment).showDate();
                break;
            default:
                break;
        }
    }

    /**
     * 显示一个Fragment
     *
     * @param clazz
     * @return 是否已经是当前fragment
     */
    public boolean showFragment(Class<? extends BaseFragment> clazz) {
        try {
            FragmentTransaction t = fragmentManager.beginTransaction();
            if (currentFragment != null && !(currentFragment.getClass() == clazz)) {
                t.hide(currentFragment);
            }
            BaseFragment f = (BaseFragment) fragmentManager.findFragmentByTag(clazz.getName());
            if (f == null) f = clazz.newInstance();
            if (!f.isAdded()) {
                t.add(R.id.fl_content, f, f.getClass().getName());
                t.show(f);
            } else {
                t.show(f);
            }
            boolean b = currentFragment == f;
            currentFragment = f;
            t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            t.commit();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 连续按2次退出
     */
    private long exitTime = 0;

    /**
     * 按 2次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            App.toast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
