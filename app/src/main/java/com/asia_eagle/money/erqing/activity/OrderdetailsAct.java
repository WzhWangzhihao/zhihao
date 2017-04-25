package com.asia_eagle.money.erqing.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.entity.AliLiuShui;
import com.asia_eagle.money.erqing.entity.BaseString;
import com.asia_eagle.money.erqing.entity.Liushui;
import com.asia_eagle.money.erqing.service.MyService;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;

import okhttp3.Call;

public class OrderdetailsAct extends BaseActivity {
    /**
     * 退款
     */
    private TextView tv_order_details_tui;
    /**
     * 名称
     */
    private TextView tv_order_details_name;
    /**
     * 支付方式
     */
    private TextView tv_order_details_pay;
    /**
     * 订单时间
     */
    private TextView tv_order_details_time;
    /**
     * 订单编号
     */
    private TextView tv_order_details_id;
    /**
     * 订单金钱
     */
    private TextView tv_order_details_money;
    /**
     * 已经退款
     */
    private TextView tv_order_details_over_tui;
    /**
     * 交易成功
     */
    private TextView tv_order_details_over_suc;
    /**
     * 流水
     */
    private Liushui liushui;
    private AliLiuShui aliLiuShui;
    /**
     * 返回
     */
    private ImageButton btn_left;
    int title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_order_details);
        tv_order_details_tui = findTextViewById(R.id.tv_order_details_tui);
        tv_order_details_name = findTextViewById(R.id.tv_order_details_name);
        tv_order_details_pay = findTextViewById(R.id.tv_order_details_pay);
        tv_order_details_time = findTextViewById(R.id.tv_order_details_time);
        tv_order_details_id = findTextViewById(R.id.tv_order_details_id);
        tv_order_details_money = findTextViewById(R.id.tv_order_details_money);
        tv_order_details_over_tui = findTextViewById(R.id.tv_order_details_over_tui);
        tv_order_details_over_suc = findTextViewById(R.id.tv_order_details_over_suc);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        title = getIntent().getIntExtra("title", 0);
        if (1 == title) {
            initViewAli();
        } else {
            initViewWx();
        }
    }

    /**
     * 初始化组件
     */
    private void initViewWx() {
        tv_order_details_pay.setText("微信支付");
        liushui = (Liushui) getIntent().getSerializableExtra("liushui");
        String str = new Gson().toJson(liushui);
        Log.e("str", str);
        //时间
        if (!TextUtils.isEmpty(liushui.strCreate)) {
            tv_order_details_time.setText(liushui.strCreate);
        }
        //收款单位
        if (!TextUtils.isEmpty(liushui.operationName)) {
            tv_order_details_name.setText(liushui.operationName);
        }
        //订单编号
        if (!TextUtils.isEmpty(liushui.payWeixinNo)) {
            tv_order_details_id.setText(liushui.payWeixinNo);
        }
        //交易成功  已退款
        if ("7".equals(liushui.status)) {
            tv_order_details_over_tui.setVisibility(View.VISIBLE);
        } else {
            tv_order_details_over_suc.setVisibility(View.VISIBLE);
            tv_order_details_tui.setVisibility(View.VISIBLE);
        }
        //钱
        if (liushui.totalFee <= 0) {
            tv_order_details_money.setText("￥" + 0);
        } else {
            BigDecimal b1 = new BigDecimal(liushui.totalFee).multiply(new BigDecimal(0.01));
            String total_fee = String.valueOf(b1.doubleValue());
            tv_order_details_money.setText("￥" + total_fee);
        }
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_order_details_tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDropOut();
            }
        });
    }


    /**
     * 初始化组件
     */
    private void initViewAli() {
        tv_order_details_pay.setText("支付宝支付");
        aliLiuShui = (AliLiuShui) getIntent().getSerializableExtra("liushui");
        String str = new Gson().toJson(liushui);
        Log.e("str", str);
        //时间
        if ((aliLiuShui.dbCreate > 0)) {
            tv_order_details_time.setText(MyService.dateToString(aliLiuShui.dbCreate));
        }
        //收款单位
        if (!TextUtils.isEmpty(aliLiuShui.operationName)) {
            tv_order_details_name.setText(aliLiuShui.operationName);
        }
        //订单编号
        if (!TextUtils.isEmpty(aliLiuShui.outTradeNo)) {
            tv_order_details_id.setText(aliLiuShui.outTradeNo);
        }
        //交易成功  已退款
        if (7==(aliLiuShui.status)) {
            tv_order_details_over_tui.setVisibility(View.VISIBLE);
        } else {
            tv_order_details_over_suc.setVisibility(View.VISIBLE);
            tv_order_details_tui.setVisibility(View.VISIBLE);
        }
        //钱
        if (aliLiuShui.totalAmount <= 0) {
            tv_order_details_money.setText("+0");
        } else {
            BigDecimal b1 = new BigDecimal(aliLiuShui.totalAmount).multiply(new BigDecimal(0.01));
            String total_fee = String.valueOf(b1.doubleValue());
            tv_order_details_money.setText("+" + total_fee);
        }
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_order_details_tui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDropOut();
            }
        });
    }

    /**
     * 退出登录dialog
     */
    public void dialogDropOut() {
        final Dialog dialog = new Dialog(OrderdetailsAct.this, R.style.Dialog);
        dialog.setContentView(R.layout.v_drop_out);
        TextView tv_queding = (TextView) dialog.findViewById(R.id.tv_queding);
        TextView tv_drop_out_content = (TextView) dialog.findViewById(R.id.tv_drop_out_content);
        TextView tv_quxiao = (TextView) dialog.findViewById(R.id.tv_quxiao);
        dialog.show();
        // 确定
        tv_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title == 1) {
                    refundAli(aliLiuShui.tradeNo);
                } else {
                    refundWeixin(liushui.payWeixinNo);
                }
                dialog.dismiss();
            }
        });
        // 确定
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 微信退款
     */
    public void refundWeixin(String payWeixinNo) {
        progress("正在退款");
        String url = MyService.API_REFUND + payWeixinNo;
        OkHttpUtils.get().url(url)
                .addHeader("accessToken", App.getToken().accessToken)
                .addHeader("Content-Type", "application/json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideProgress();
                e.printStackTrace();
                App.toast("与服务器通讯失败！");
            }

            @Override
            public void onResponse(String response, int id) {
                hideProgress();
                Log.e("response", response);
                try {
                    BaseString baseString = new Gson().fromJson(response, BaseString.class);
                    if (baseString.isSuccess()) {
                        dialogDropOut1("退款成功!");
                    } else {
                        dialogDropOut2(baseString.getMessage());
                    }
                } catch (Exception e) {
                    App.toast("与服务器连接失败！");
                }
            }
        });
    }


    /**
     * 阿里退款
     */
    public void refundAli(String payWeixinNo) {
        progress("正在退款");
        String url = MyService.API_ALIPAYREFUND + payWeixinNo;
        OkHttpUtils.get().url(url)
                .addHeader("accessToken", App.getToken().accessToken)
                .addHeader("Content-Type", "application/json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                hideProgress();
                e.printStackTrace();
                App.toast("与服务器通讯失败！");
            }

            @Override
            public void onResponse(String response, int id) {
                hideProgress();
                Log.e("response", response);
                try {
                    BaseString baseString = new Gson().fromJson(response, BaseString.class);
                    if (baseString.isSuccess()) {
                        dialogDropOut1("退款成功!");
                    } else {
                        dialogDropOut2(baseString.getMessage());
                    }
                } catch (Exception e) {
                    App.toast("与服务器连接失败！");
                }
            }
        });
    }


    /**
     * 提示成功
     */
    public void dialogDropOut1(String content) {
        final Dialog dialog = new Dialog(OrderdetailsAct.this, R.style.Dialog);
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
                sendBroadcast(new Intent(MyService.FINISH_UPDATE));
                finish();
                dialog.dismiss();
            }
        });
        tv_quxiao.setVisibility(View.GONE);
        // 取消
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
    public void dialogDropOut2(String content) {
        final Dialog dialog = new Dialog(OrderdetailsAct.this, R.style.Dialog);
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

}
