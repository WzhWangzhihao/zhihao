package com.asia_eagle.money.erqing.fragment;


import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.App;
import com.asia_eagle.money.erqing.entity.Versions;
import com.asia_eagle.money.erqing.util.SharedPreferencesHelper;
import com.asia_eagle.money.erqing.util.Tools;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 *
 * @author wzh
 */
public class FragmentHome extends BaseFragment implements View.OnClickListener {
    private ViewPager fg_home_view_pager;
    private List<BaseFragment> list = new ArrayList<BaseFragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_home);
        Init();
        return contentView;
    }

    private void Init() {
        FragmentWx f1 = new FragmentWx();
        FragmentQq f2 = new FragmentQq();
        FragmentAli f3 = new FragmentAli();
        list.add(f1);
        list.add(f2);
        list.add(f3);
        fg_home_view_pager = (ViewPager) findViewById(R.id.fg_home_view_pager);
        fg_home_view_pager.setAdapter(new viewPagerAdapter(getActivity().getSupportFragmentManager()));
        try {
            Versions versions = new Gson().fromJson(getFileFromSD(App.getSDPath() + "/money/version.json"), Versions.class);
//            versions.version = "1.0.1";
            Log.e("fffff",new Gson().toJson(versions));
            int s1 = Integer.valueOf(versions.version.replaceAll("\\.", ""));
            int s2 = Integer.valueOf(getVersion2().replaceAll("\\.", ""));
            Log.e("sss",s1+"---"+s2);
            if (s1 - s2 > 0) {
                dialogDropOut("发现新版本~请更新", versions.url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class viewPagerAdapter extends FragmentPagerAdapter {
        public viewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private String getFileFromSD(String path) {
        String result = "";

        try {
            FileInputStream f = new FileInputStream(path);
            BufferedReader bis = new BufferedReader(new InputStreamReader(f));
            String line = "";
            while ((line = bis.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取本机程序的版本号
     */
    public String getVersion2() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            SharedPreferencesHelper.saveString("LocalhostsoftwareVersion", version);
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 提示更新
     */
    public void dialogDropOut(String content, final String url) {
        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        dialog.setContentView(R.layout.v_drop_out);
        TextView tv_queding = (TextView) dialog.findViewById(R.id.tv_queding);
        TextView tv_drop_out_content = (TextView) dialog.findViewById(R.id.tv_drop_out_content);
        TextView tv_quxiao = (TextView) dialog.findViewById(R.id.tv_quxiao);
        tv_drop_out_content.setText(content);
        dialog.show();
        // 确定
        tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.openInBrowser(getActivity(), url);
                dialog.dismiss();
            }
        });
        // 取消
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
