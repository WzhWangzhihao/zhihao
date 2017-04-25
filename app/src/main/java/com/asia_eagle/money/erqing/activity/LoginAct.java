package com.asia_eagle.money.erqing.activity;
/**
 * 登录界面
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asia_eagle.money.erqing.Post.Employee;
import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.entity.AccessCode;
import com.asia_eagle.money.erqing.entity.Token;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.MediaType;


public class

LoginAct extends BaseActivity implements View.OnClickListener {
    private TextView tv_login_reg, tv_login_findpwd, tv_login_login;
    private EditText et_login_instid, et_login_mernum, et_login_mobile, et_login_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_act);
        InitUI();
    }

    public void InitUI() {
        tv_login_reg = findTextViewById(R.id.tv_login_reg);
        tv_login_findpwd = findTextViewById(R.id.tv_login_findpwd);
        tv_login_login = findTextViewById(R.id.tv_login_login);
        et_login_instid = findEditTextById(R.id.et_login_instid);
        et_login_mernum = findEditTextById(R.id.et_login_mernum);
        et_login_mobile = findEditTextById(R.id.et_login_mobile);
        et_login_pwd = findEditTextById(R.id.et_login_pwd);
        tv_login_login.setOnClickListener(this);
        tv_login_findpwd.setOnClickListener(this);
        tv_login_reg.setOnClickListener(this);
        if (!TextUtils.isEmpty(SharedPreferencesHelper.getString("instId"))) {
            et_login_instid.setText(SharedPreferencesHelper.getString("instId"));
        }
        if (!TextUtils.isEmpty(SharedPreferencesHelper.getString("merNum"))) {
            et_login_mernum.setText(SharedPreferencesHelper.getString("merNum"));
        }
        if (!TextUtils.isEmpty(SharedPreferencesHelper.getString("account"))) {
            et_login_mobile.setText(SharedPreferencesHelper.getString("account"));
        }
        if (!TextUtils.isEmpty(SharedPreferencesHelper.getString("pwd"))) {
            et_login_pwd.setText(SharedPreferencesHelper.getString("pwd"));
        }
        et_login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!TextUtils.isEmpty(et_login_mobile.getText().toString().trim())) && (!TextUtils.isEmpty(et_login_pwd.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_instid.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_mernum.getText().toString().trim()))) {
                    tv_login_login.setSelected(true);
                } else {
                    tv_login_login.setSelected(false);
                }
            }
        });
        et_login_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!TextUtils.isEmpty(et_login_mobile.getText().toString().trim())) && (!TextUtils.isEmpty(et_login_pwd.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_instid.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_mernum.getText().toString().trim()))) {
                    tv_login_login.setSelected(true);
                } else {
                    tv_login_login.setSelected(false);
                }
            }
        });
        et_login_instid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!TextUtils.isEmpty(et_login_mobile.getText().toString().trim())) && (!TextUtils.isEmpty(et_login_pwd.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_instid.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_mernum.getText().toString().trim()))) {
                    tv_login_login.setSelected(true);
                } else {
                    tv_login_login.setSelected(false);
                }
            }
        });
        et_login_mernum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((!TextUtils.isEmpty(et_login_mobile.getText().toString().trim())) && (!TextUtils.isEmpty(et_login_pwd.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_instid.getText().toString().trim()))
                        && (!TextUtils.isEmpty(et_login_mernum.getText().toString().trim()))) {
                    tv_login_login.setSelected(true);
                } else {
                    tv_login_login.setSelected(false);
                }
            }
        });
        getPersimmions();
        if ((!TextUtils.isEmpty(SharedPreferencesHelper.getString("account")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("pwd")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("merNum")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("instId")))) {
            tv_login_login.setSelected(true);
        }
        if (("1".equals(SharedPreferencesHelper.getString("key"))) && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("account")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("pwd")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("merNum")))
                && (!TextUtils.isEmpty(SharedPreferencesHelper.getString("instId")))) {
            loginUser(et_login_instid.getText().toString().trim(), et_login_mernum.getText().toString().trim(),
                    et_login_mobile.getText().toString().trim(), et_login_pwd.getText().toString().trim());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_login:
//                if (TextUtils.isEmpty(et_login_instid.getText().toString().trim())) {
//                    App.toast("请输入机构号!");
//                    return;
//                }
//                if (TextUtils.isEmpty(et_login_mernum.getText().toString().trim())) {
//                    App.toast("请输入商户号!");
//                    return;
//                }
//                if (TextUtils.isEmpty(et_login_mobile.getText().toString().trim())) {
//                    App.toast("请输入用户名!");
//                    return;
//                }
//                if (TextUtils.isEmpty(et_login_pwd.getText().toString().trim())) {
//                    App.toast("请输入密码!");
//                    return;
//                }
//                loginUser(et_login_instid.getText().toString().trim(), et_login_mernum.getText().toString().trim(),
//                        et_login_mobile.getText().toString().trim(), et_login_pwd.getText().toString().trim());
//                loginUser("YC00100724","MER160600563","hgyrgwfd","123456");
//                YC00102075，MER160901075，huangjingjue，123456
//                YC00100734,MER160700007,ksliyong,123456
//                YC00020946,MER161200091,alitest001,123456
                loginUser("YC00021499","MER170400106","cst+0","111111");
                break;
            case R.id.tv_login_reg:
                App.toast("功能正在开发中~");
                break;
            case R.id.tv_login_findpwd:
                App.toast("功能正在开发中~");
                break;
            default:
                break;
        }
    }

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            // 相机
            if (addPermission(permissions, Manifest.permission.CAMERA)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            // 闪光灯
            if (addPermission(permissions, Manifest.permission.FLASHLIGHT)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }


        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    /**
     * 登录第一步拿去accessCode
     */
    public void loginUser(final String instId, final String merNum, final String account, final String pwd) {
        progress("正在登录");
        Employee employee = new Employee();
        employee.account = account;
        employee.password = pwd;
        String content = new Gson().toJson(employee);
//        OkHttpUtils.get().url(url).addHeader("instId", "YC00100724")
//                .addHeader("merNum", "MER160600563")
        OkHttpUtils.postString().url(MyService.API_LOGIN1).mediaType(MediaType.parse("application/json")).addHeader("instId", instId).addHeader("merNum", merNum).content(content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                hideProgress();
                App.toast("与服务器通讯失败!");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("response", response);
                try {
                    SharedPreferencesHelper.saveString("instId", instId);
                    SharedPreferencesHelper.saveString("merNum", merNum);
                    SharedPreferencesHelper.saveString("account", account);
                    SharedPreferencesHelper.saveString("pwd", pwd);
                    AccessCode s = new Gson().fromJson(response, AccessCode.class);
                    if (s.code == 200) {
                        Login2(s.accessCode, instId, merNum);
                    } else {
                        hideProgress();
                        App.toast(s.message + "");
                    }
                } catch (Exception e) {
                    hideProgress();
                    App.toast("与服务器连接失败!");
                }
            }
        });
    }

    /**
     * 登录第二步 accessCode换AccessToken
     *
     * @param accessCode
     */
    public void Login2(final String accessCode, final String instId, final String merNum) {
//        String  url="/v1/login/getAccessTokenAndLoginInf";
        Log.e("accessCode", accessCode);
        OkHttpUtils.postString().url(MyService.API_LOGIN2).mediaType(MediaType.parse("application/json")).addHeader("instId", instId).addHeader("merNum", merNum)
                .addHeader("accessCode", accessCode).content("").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideProgress();
                e.printStackTrace();
                App.toast("与服务器通讯失败!");
            }

            @Override
            public void onResponse(String response, int id) {
                hideProgress();
                Log.e("response", response);
                try {
                    Token t = new Gson().fromJson(response, Token.class);
                    if (t.isSuccess()) {
                        App.setToken(t.data);
                        App.setInstId(instId);
                        App.setMerNum(merNum);
                        startActivity(new Intent(LoginAct.this, HomeAct.class));
                        finish();
                    } else {
                        App.toast(t.getMessage() + "");
                    }
                } catch (Exception e) {
                    App.toast("与服务器通许失败!");
                }
            }
        });
    }
}
