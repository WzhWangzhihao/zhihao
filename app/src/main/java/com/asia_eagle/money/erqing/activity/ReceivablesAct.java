package com.asia_eagle.money.erqing.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.service.MyService;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ReceivablesAct extends BaseActivity implements View.OnClickListener {
    /**
     * 0-9  00 . 删除
     */
    private TextView tv_receivables_zerozero, tv_receivables_zero,
            tv_receivables_one, tv_receivables_two, tv_receivables_three,
            tv_receivables_four, tv_receivables_five, tv_receivables_six,
            tv_receivables_seven, tv_receivables_eight, tv_receivables_nine,
            tv_receivables_dian, tv_receivables_submit;
    private ImageView iv_receivables_del;
    //订单号
    private TextView tv_receivables_ordernumber;
    //时间
    private TextView tv_receivables_time;
    //收款金额
//    private TextView tv_receivables_money;
    private EditText tv_receivables_money;

    private String money = "";

    private int type;

    private float fmoney;

    private TextView tv_receivables_type;

    private ImageView iv_receivables_xiangxia;

    private LinearLayout ll_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.ac_receivables);
        type = getIntent().getIntExtra("type", 0);
        /**
         * 初始化组件
         */
        initView();
    }

    @Override
    public void clickLeft() {
        super.clickLeft();
        finish();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        tv_receivables_zerozero = findTextViewById(R.id.tv_receivables_zerozero);
        tv_receivables_zero = findTextViewById(R.id.tv_receivables_zero);
        tv_receivables_one = findTextViewById(R.id.tv_receivables_one);
        tv_receivables_two = findTextViewById(R.id.tv_receivables_two);
        tv_receivables_three = findTextViewById(R.id.tv_receivables_three);
        tv_receivables_four = findTextViewById(R.id.tv_receivables_four);
        tv_receivables_five = findTextViewById(R.id.tv_receivables_five);
        tv_receivables_six = findTextViewById(R.id.tv_receivables_six);
        tv_receivables_seven = findTextViewById(R.id.tv_receivables_seven);
        tv_receivables_eight = findTextViewById(R.id.tv_receivables_eight);
        tv_receivables_nine = findTextViewById(R.id.tv_receivables_nine);
        iv_receivables_del = findImageViewById(R.id.iv_receivables_del);
        tv_receivables_dian = findTextViewById(R.id.tv_receivables_dian);
        tv_receivables_submit = findTextViewById(R.id.tv_receivables_submit);
        tv_receivables_ordernumber = findTextViewById(R.id.tv_receivables_ordernumber);
        tv_receivables_time = findTextViewById(R.id.tv_receivables_time);
        tv_receivables_money = findEditTextById(R.id.tv_receivables_money);
        iv_receivables_xiangxia = findImageViewById(R.id.iv_receivables_xiangxia);
        tv_receivables_type = findTextViewById(R.id.tv_receivables_type);
        ll_all = findLinearLayoutById(R.id.ll_all);


        hideSoftKeyboard(tv_receivables_money, ReceivablesAct.this);

        tv_receivables_zerozero.setOnClickListener(this);
        tv_receivables_zero.setOnClickListener(this);
        tv_receivables_one.setOnClickListener(this);
        tv_receivables_two.setOnClickListener(this);
        tv_receivables_three.setOnClickListener(this);
        tv_receivables_four.setOnClickListener(this);
        tv_receivables_five.setOnClickListener(this);
        tv_receivables_six.setOnClickListener(this);
        tv_receivables_seven.setOnClickListener(this);
        tv_receivables_eight.setOnClickListener(this);
        tv_receivables_nine.setOnClickListener(this);
        tv_receivables_submit.setOnClickListener(this);
        iv_receivables_del.setOnClickListener(this);
        tv_receivables_dian.setOnClickListener(this);
        iv_receivables_xiangxia.setOnClickListener(this);
        tv_receivables_money.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        //得到long类型当前时间
        long l = System.currentTimeMillis();
        //new日期对象
        Date date = new Date(l);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tv_receivables_time.setText(dateFormat.format(date) + "");

        switch (type) {
            case 1:
                tv_receivables_type.setText("用户扫描支付宝二维码");
                break;
            case 2:
                tv_receivables_type.setText("支付宝声波支付");
                break;
            case 3:
                tv_receivables_type.setText("扫描用户支付宝二维码");
                break;
            case 4:
                tv_receivables_type.setText("用户扫描微信二维码");
                break;
            case 5:
                tv_receivables_type.setText("扫描用户微信二维码");
                break;
            case 6:
                tv_receivables_type.setText("用户扫描QQ二维码");
                break;
            case 7:
                tv_receivables_type.setText("扫描用户QQ二维码");
                break;
            default:
                break;
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
            case R.id.iv_receivables_xiangxia:
                ll_all.setVisibility(View.GONE);
                break;
            case R.id.tv_receivables_money:
                hideSoftKeyboard(tv_receivables_money, ReceivablesAct.this);
                ll_all.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_receivables_zerozero:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "00";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_zero:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "0";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_one:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "1";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_two:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "2";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_three:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "3";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_four:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "4";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_five:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "5";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_six:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "6";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_seven:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "7";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_eight:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "8";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.tv_receivables_nine:
                if (tv_receivables_money.getText().toString().trim().length() >= 7) {
                    return;
                }
                money += "9";
                tv_receivables_money.setText(money + "");
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                break;
            case R.id.iv_receivables_del:
                if (money.length() - 1 > 0) {
                    money = money.substring(0, money.length() - 1);
                    tv_receivables_money.setText(money + "");
                    tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
                } else {
                    money = "";
                    tv_receivables_money.setText("");
                }
                break;
            case R.id.tv_receivables_dian:
//                App.toast(".".contains(money) + "");
                if (!money.contains(".")) {
                    if (TextUtils.isEmpty(money)) {
                        money = "0";
                    }
                    money += ".";
                    tv_receivables_money.setText(money + "");
                }
                tv_receivables_money.setSelection(money.length());//将光标移至文字末尾
//                String a="sabcd";
//                String b="a";
////                App.toast(a.contains(b)+"");
//                Log.e("zz",a.contains(b)+"");
//                Log.e("zz",b.contains(a)+"");
                break;
            case R.id.tv_receivables_submit:
                money = tv_receivables_money.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    App.toast("请输入收款金额");
                    return;
                }
                String nextMoney = "";
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                double d = Double.valueOf(money);
                nextMoney = df.format(d) + "";
                fmoney = Float.valueOf(nextMoney);
                fmoney = (float) (Math.round(fmoney * 100)) / 100;
                if (fmoney <= 0) {
                    App.toast("请输入收款金额");
                    return;
                }
                switch (type) {
                    case 1:
                        Intent aliintent1 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
                        aliintent1.putExtra("type", "1");
                        aliintent1.putExtra("fmoney", fmoney);
                        startActivity(aliintent1);
                        break;
                    case 2:
//                        Intent aliintent2 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
//                        aliintent2.putExtra("type", "2");
//                        aliintent2.putExtra("fmoney", fmoney);
//                        startActivity(aliintent2);
                        break;
                    case 3:
                        Intent aliintent3 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
                        aliintent3.putExtra("type", "2");
                        aliintent3.putExtra("fmoney", fmoney);
                        startActivity(aliintent3);
                        break;
                    case 4:
                        Intent wxintent1 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
                        wxintent1.putExtra("type", "1");
                        wxintent1.putExtra("fmoney", fmoney);
                        startActivity(wxintent1);
                        break;
                    case 5:
                        if (checkMoney()) {
                            Intent wxintent2 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
                            wxintent2.putExtra("type", "2");
                            wxintent2.putExtra("fmoney", fmoney);
                            startActivity(wxintent2);
                        } else {
                            App.toast("请输入正确的金额!");
                        }
                        break;
                    case 6:
                        Intent qqintent1 = new Intent(ReceivablesAct.this, QqPayScanningAct.class);
                        qqintent1.putExtra("type", "1");
                        qqintent1.putExtra("fmoney", fmoney);
                        startActivity(qqintent1);
                        break;
                    case 7:
                        Intent qqintent2 = new Intent(ReceivablesAct.this, QqPayScanningAct.class);
                        qqintent2.putExtra("type", "2");
                        qqintent2.putExtra("fmoney", fmoney);
                        startActivity(qqintent2);
                        break;
//                    case 6:
//                        Intent qqintent1 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
//                        qqintent1.putExtra("type", "3");
//                        qqintent1.putExtra("fmoney", fmoney);
//                        startActivity(qqintent1);
//                        break;
//                    case 7:
//                        Intent qqintent2 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
//                        qqintent2.putExtra("type", "4");
//                        qqintent2.putExtra("fmoney", fmoney);
//                        startActivity(qqintent2);
//                        break;
                    default:
                        break;
                }
                break;


        }
//        switch (v.getId()) {
//            case R.id.tv_alipay_saomashou:
//                Intent aliintent1 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
//                aliintent1.putExtra("type", "3");
//                startActivity(aliintent1);
//                break;
//            case R.id.tv_alipay_shenbofu:
//                Intent aliintent2 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
//                aliintent2.putExtra("type", "3");
//                startActivity(aliintent2);
//                break;
//            case R.id.tv_alipay_shoukuanma:
//                Intent aliintent3 = new Intent(ReceivablesAct.this, AliPayScanningAct.class);
//                aliintent3.putExtra("type", "3");
//                startActivity(aliintent3);
//                break;
//            case R.id.tv_wx_shoukuanma:
//                Intent wxintent1 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
//                wxintent1.putExtra("type", "1");
//                startActivity(wxintent1);
//                break;
//            case R.id.tv_wx_saomashou:
//                Intent wxintent2 = new Intent(ReceivablesAct.this, WxPayScanningAct.class);
//                wxintent2.putExtra("type", "2");
//                startActivity(wxintent2);
//                break;
//            case R.id.tv_qq_shoukuanma:
//                Intent qqintent1 = new Intent(ReceivablesAct.this, QqPayScanningAct.class);
//                qqintent1.putExtra("type", "2");
//                startActivity(qqintent1);
//                break;
//            case R.id.tv_qq_saomashou:
//                Intent qqintent2 = new Intent(ReceivablesAct.this, QqPayScanningAct.class);
//                qqintent2.putExtra("type", "2");
//                startActivity(qqintent2);
//                break;
//            default:
//                break;
    }

    // 检查金额是否有误
    boolean checkMoney() {
        if (tv_receivables_money.getText().toString().trim().equals("0.0")
                || tv_receivables_money.getText().toString().trim().equals("0.00")
                || tv_receivables_money.getText().toString().trim().equals("0.")) {
            return false;
        } else {
            String str = tv_receivables_money.getText().toString().trim();
            String regex = "^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$";//
            return str.matches(regex);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public static void hideSoftKeyboard(EditText editText, Context context) {
        if (editText != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 10);
        }
    }

}
