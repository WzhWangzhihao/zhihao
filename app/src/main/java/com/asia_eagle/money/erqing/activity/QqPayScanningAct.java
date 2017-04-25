package com.asia_eagle.money.erqing.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.Post.Generate;
import com.asia_eagle.money.erqing.Post.ScanPay;
import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.entity.AccessCode;
import com.asia_eagle.money.erqing.entity.BaseString;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.widget.ZBarView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
//import com.uuzuche.lib_zxing.activity.CaptureFragment;
//import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 定制化显示扫描界面
 */
public class QqPayScanningAct extends BaseActivity implements View.OnClickListener, QRCodeView.Delegate {

    //    private CaptureFragment captureFragment;
    //付款码
    private FrameLayout fl_receivables_code;
    private LinearLayout ll_receivables_code;
    private TextView tv_receivables_code;
    private ImageView iv_wxpay_receivables_code_quan;
    private ImageView iv_wxpay_receivables_code_code;
    //扫码收
//    private FrameLayout fl_scan_code;
    private TextView tv_scan_code;
    private LinearLayout ll_scan_code;
    private ImageView iv_wxpay_scan_code_quan;
    private ImageView iv_wxpay_scan_code_code;
    private TextView tv_scan_code_top;
    //顶部标题
    private TextView tv_wxpay_content;
    //返回
    private ImageButton btn_left;

    private float fmoney;
    /**
     * 生成二维码
     */
    private ImageView iv_qqpay_code_erweima;
    /**
     * 生成条形码
     */
    private ImageView iv_wxpay_code_tiaoxingma;

    /**
     *
     */
    private String mauthCode;

    private boolean isBack = true;

    private boolean isCode = true;

    private QRCodeView mQRCodeView;

    private LinearLayout ll_pay;

    private MediaPlayer mediaPlayer = new MediaPlayer();       //媒体播放器对象
    private String path;                        //音乐文件路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_qqpay_scanning);
        initView();
        getPersimmions();
    }


    private void initView() {
        fmoney = getIntent().getFloatExtra("fmoney", 0);
        ll_receivables_code = (LinearLayout) findViewById(R.id.ll_receivables_code);
        ll_scan_code = (LinearLayout) findViewById(R.id.ll_scan_code);
        tv_receivables_code = findTextViewById(R.id.tv_receivables_code);
        ll_pay = findLinearLayoutById(R.id.ll_pay);
        iv_qqpay_code_erweima = findImageViewById(R.id.iv_qqpay_code_erweima);
//        captureFragment = new CaptureFragment();
//        // 为二维码扫描界面设置定制化界面
//        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
//        captureFragment.setAnalyzeCallback(analyzeCallback);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_scan_code, captureFragment).commit();
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
        tv_scan_code = findTextViewById(R.id.tv_scan_code);
        fl_receivables_code = (FrameLayout) findViewById(R.id.fl_receivables_code);
//        fl_scan_code = (FrameLayout) findViewById(R.id.fl_scan_code);
        iv_wxpay_scan_code_quan = findImageViewById(R.id.iv_wxpay_scan_code_quan);
        iv_wxpay_scan_code_code = findImageViewById(R.id.iv_wxpay_scan_code_code);
        iv_wxpay_receivables_code_code = findImageViewById(R.id.iv_wxpay_receivables_code_code);
        iv_wxpay_receivables_code_quan = findImageViewById(R.id.iv_wxpay_receivables_code_quan);
        iv_wxpay_code_tiaoxingma = findImageViewById(R.id.iv_wxpay_code_tiaoxingma);
        tv_wxpay_content = findTextViewById(R.id.tv_wxpay_content);
        tv_scan_code_top = findTextViewById(R.id.tv_scan_code_top);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        ll_receivables_code.setOnClickListener(this);
        ll_scan_code.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        String type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            tv_wxpay_content.setText("付款码");
            tv_wxpay_content.setTextColor(getResources().getColor(R.color.topziti));
            iv_wxpay_receivables_code_code.setSelected(true);
            iv_wxpay_receivables_code_quan.setSelected(true);
            iv_wxpay_scan_code_code.setSelected(false);
            iv_wxpay_scan_code_quan.setSelected(false);
            tv_scan_code.setSelected(false);
            tv_receivables_code.setSelected(true);
//            fl_scan_code.setVisibility(View.GONE);
            fl_receivables_code.setVisibility(View.VISIBLE);
            tv_scan_code_top.setVisibility(View.GONE);
            mQRCodeView.hiddenScanRect();
            mQRCodeView.stopCamera();
            ll_pay.setVisibility(View.GONE);
            GenerateCode();
        } else if ("2".equals(type)) {
            tv_wxpay_content.setText("二维码/条形码");
            tv_wxpay_content.setTextColor(Color.WHITE);
            iv_wxpay_receivables_code_code.setSelected(false);
            iv_wxpay_receivables_code_quan.setSelected(false);
            iv_wxpay_scan_code_code.setSelected(true);
            iv_wxpay_scan_code_quan.setSelected(true);
            tv_scan_code.setSelected(true);
            tv_receivables_code.setSelected(false);
//            fl_scan_code.setVisibility(View.VISIBLE);
            fl_receivables_code.setVisibility(View.GONE);
            tv_scan_code_top.setVisibility(View.VISIBLE);
            mQRCodeView.showScanRect();
            ll_pay.setVisibility(View.VISIBLE);
        }
        registerReceiver(receiver, new IntentFilter(MyService.FINISH_PAGE));
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_receivables_code:
                tv_wxpay_content.setText("付款码");
                tv_wxpay_content.setTextColor(getResources().getColor(R.color.topziti));
                iv_wxpay_receivables_code_code.setSelected(true);
                iv_wxpay_receivables_code_quan.setSelected(true);
                iv_wxpay_scan_code_code.setSelected(false);
                iv_wxpay_scan_code_quan.setSelected(false);
                tv_scan_code.setSelected(false);
                tv_receivables_code.setSelected(true);
//                fl_scan_code.setVisibility(View.GONE);
                fl_receivables_code.setVisibility(View.VISIBLE);
                tv_scan_code_top.setVisibility(View.GONE);
                isCode = true;
                mQRCodeView.hiddenScanRect();
                mQRCodeView.stopCamera();
                ll_pay.setVisibility(View.GONE);
                GenerateCode();
                break;
            case R.id.ll_scan_code:
                tv_wxpay_content.setText("二维码/条形码");
                tv_wxpay_content.setTextColor(Color.WHITE);
                iv_wxpay_receivables_code_code.setSelected(false);
                iv_wxpay_receivables_code_quan.setSelected(false);
                iv_wxpay_scan_code_code.setSelected(true);
                iv_wxpay_scan_code_quan.setSelected(true);
                tv_scan_code.setSelected(true);
                tv_receivables_code.setSelected(false);
//                fl_scan_code.setVisibility(View.VISIBLE);
                fl_receivables_code.setVisibility(View.GONE);
                tv_scan_code_top.setVisibility(View.VISIBLE);
                isCode = false;
                mQRCodeView.showScanRect();
                mQRCodeView.startSpotDelay(1);
                ll_pay.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_left:
                isCode = false;
                finish();
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
     * 二维码扫描后的回调事件
     *
     * @param authCode 二维码
     */
    public void WxScanPay(final String authCode) {
        int totalFee = (int) (fmoney * 100);
        progress("正在收款");
        ScanPay scanPay = new ScanPay();
        scanPay.authCode = authCode;
        scanPay.body = App.getToken().instName + "(" + App.getToken().merName + ")";
        scanPay.totalFee = totalFee + "";
        Log.e("totalFee", scanPay.totalFee);
        scanPay.source = "安卓移动端";
        mauthCode = authCode;
        final String content = new Gson().toJson(scanPay);
        OkHttpUtils.postString().url(MyService.API_QQSCANPAY).mediaType(MediaType.parse("application/json"))
                .addHeader("accessToken", App.getToken().accessToken)
                .addHeader("instId", App.getInstId())
                .addHeader("merNum", App.getMerNum())
                .tag("wxpay")
                .content(content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isBack) {
                    new myThread().start();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("fffff", response);
                hideProgress();
                try {
                    AccessCode s = new Gson().fromJson(response, AccessCode.class);
                    if (s.code == 200) {
                        App.toast("收款成功!");
                        Intent intent = new Intent(QqPayScanningAct.this, OverPayAct.class);
                        intent.putExtra("fmoney", fmoney+"");
                        startActivity(intent);
                    } else {
                        dialogDropOut2(s.message);
                    }
                } catch (Exception e) {
                    hideProgress();
                    App.toast("与服务器连接失败!");
                }
            }
        });
    }

    /**
     * 生成二维码
     */
    public void GenerateCode() {
        int totalFee = (int) (fmoney * 100);
        progress("正在生成二维码");
        Generate generate = new Generate();
        generate.body = App.getToken().instName + "(" + App.getToken().merName + ")";
        generate.totalFee = totalFee + "";
        generate.source = "安卓移动端";
        final String content = new Gson().toJson(generate);
        OkHttpUtils.postString().url(MyService.API_QQFENERATECODE).mediaType(MediaType.parse("application/json"))
                .addHeader("accessToken", App.getToken().accessToken)
                .content(content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideProgress();
                e.printStackTrace();
                dialogDropOut2("请检查网络后重试!");
            }

            @Override
            public void onResponse(String response, int id) {
                hideProgress();
                Log.e("response", response);
                try {
                    BaseString baseString = new Gson().fromJson(response, BaseString.class);
                    if (baseString.isSuccess()) {
                        count = 60;
                        new myThread1().start();
                        Log.e("ffff", baseString.data + "---");
                        createEnglishQRCodeWithLogo(baseString.data);
                    } else {
                        dialogDropOut2(baseString.getMessage());
                    }
                } catch (Exception e) {
                    App.toast("与服务器连接失败！");
                }
            }
        });
    }


    boolean f = false;
    /**
     * 倒计时60秒
     */
    private int recLen = 0;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /**
             * 判断
             */
            if (!f) {
                if (msg.what < 16) {
                    Log.e("ffff", "what---" + msg.what);
                    if (msg.what % 5 == 0) {
                        selectOk(mauthCode);
                    } else {
                        new myThread().start();
                    }
                } else {
                    serverOK(mauthCode);
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onScanQRCodeSuccess(String result) {
        try {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.wx);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(result)) {
            WxScanPay(result);
        } else {
            App.toast("识别二维码失败！");
        }

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class myThread extends Thread {
        @Override
        public void run() {
            try {
                sleep(1000);
                recLen++;
                Log.e("ffff", "recLen---" + recLen);
                handler.sendEmptyMessage(recLen);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public Bitmap mBitmap = null;

    private void createEnglishQRCodeWithLogo(final String content) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                mBitmap = BitmapFactory.decodeResource(QqPayScanningAct.this.getResources(), R.mipmap.icon);
                return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(QqPayScanningAct.this, 300), Color.BLACK, Color.WHITE, mBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    iv_qqpay_code_erweima.setImageBitmap(bitmap);
//                    iv_qqpay_code_erweima.setImageResource(R.mipmap.icon);
                } else {
                    dialogDropOut3("生成二维码失败,重新生成!");
                }
            }
        }.execute();
    }

    /**
     * 查询是否支付成功!
     */
    public void selectOk(final String authCode) {
//        App.toast("authCode");
        Log.e("authCode", authCode);
        String url = MyService.API_QQSELECTOK + authCode;
        OkHttpUtils.get().url(url).addHeader("accessToken", App.getToken().accessToken)
                .addHeader("Content-Type", "application/json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("ffff", "response---" + "无返回");
                e.printStackTrace();
                new myThread().start();
            }

            @Override
            public void onResponse(String response, int id) {
                /**
                 * 解析服务器返回的数据
                 * baseString.code==200 就说明收款成功
                 * baseString.code==1280 就说明继续查询
                 * baseString.code 出现其他的就直接提示错误
                 * baseString.code屋任何返回就继续下一次查询
                 *   new myThread().start(); 休眠5秒 开启下一次查询的线程
                 */
                try {
                    BaseString baseString = new Gson().fromJson(response, BaseString.class);
                    Log.e("response111", response);
                    if (baseString.code == 200) {
                        App.toast("收款成功!");
                        f = true;
                        hideProgress();
                        sendBroadcast(new Intent(MyService.FINISH_PAGE));
                        finish();
                    } else if (baseString.code == 1280) {
                        new myThread().start();
                    } else {
                        hideProgress();
                        dialogDropOut2(baseString.getMessage());
                    }
                } catch (Exception e) {
                    dialogDropOut2("与服务器连接失败!");
                }
            }
        });
    }

    /**
     * 撤单
     */
    public void serverOK(String authCode) {
        String url = MyService.API_QQREVERSE + authCode;
        OkHttpUtils.get().url(url)
                .addHeader("accessToken", App.getToken().accessToken)
                .addHeader("Content-Type", "application/json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideProgress();
                e.printStackTrace();
                dialogDropOut2("请检查网络后重试!");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("response", response);
                hideProgress();
                try {
                    BaseString baseString = new Gson().fromJson(response, BaseString.class);
                    if (baseString.isSuccess()) {
                        //终止掉发起支付的这个线程
                        OkHttpUtils.getInstance().cancelTag("wxpay");
                        dialogDropOut2("订单已撤销，请重新支付");
                    } else {
                        dialogDropOut2(baseString.getMessage());
                    }
                } catch (Exception e) {
                    dialogDropOut2("与服务器连接失败!");
                }
            }
        });
    }

    /**
     * 提示失败
     */
    public void dialogDropOut2(String content) {
        final Dialog dialog = new Dialog(QqPayScanningAct.this, R.style.Dialog);
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
                finish();
                dialog.dismiss();
            }
        });
        // 取消
        tv_quxiao.setVisibility(View.GONE);
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 提示失败
     */
    public void dialogDropOut1(String content) {
        final Dialog dialog = new Dialog(QqPayScanningAct.this, R.style.Dialog);
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
                finish();
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

    /**
     * 提示生成二维码失败
     */
    public void dialogDropOut3(String content) {
        final Dialog dialog = new Dialog(QqPayScanningAct.this, R.style.Dialog);
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
                GenerateCode();
                isCode=false;
                dialog.dismiss();
            }
        });
        // 取消
        tv_quxiao.setVisibility(View.GONE);
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 监听返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(mauthCode)) {
                isBack = false;
                dialogDropOut1("正在收款，确定退出吗？");
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //终止掉发起支付的这个线程
        OkHttpUtils.getInstance().cancelTag("wxpay");
        super.onDestroy();
    }

    /**
     * 计时handel
     */
    final Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isCode) {
                if (msg.what <= 0) {
                    count = 60;
                    GenerateCode();
                } else {
                    new myThread1().start();
                }
            }
            super.handleMessage(msg);
        }
    };
    private int count = 0;

    /**
     * 计时线程
     */
    class myThread1 extends Thread {
        @Override
        public void run() {
            try {
                sleep(1000);
                count--;
                handler1.sendEmptyMessage(count);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
