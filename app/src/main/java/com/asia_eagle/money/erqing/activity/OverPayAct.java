package com.asia_eagle.money.erqing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.util.Printer;
import com.lkl.cloudpos.aidl.printer.AidlPrinterListener;
import com.lkl.cloudpos.aidl.printer.PrintItemObj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.cloudpos.apidemo.function.printer.PrinterCommand;
//import com.cloudpos.jniinterface.PrinterInterface;

/**
 * 启动页
 */
public class OverPayAct extends BaseActivity {
    private ImageButton btn_left;
    private TextView tv_over_pay_play, tv_over_pay_back, tv_over_money;
    private Context mContext;
    private boolean isOpened = false;
    private String times;
    private String money;
    List<PrintItemObj> data = new ArrayList<PrintItemObj>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_over_pay);
        mContext = OverPayAct.this;
        InitUI();
    }

    public void InitUI() {
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        tv_over_pay_play = findTextViewById(R.id.tv_over_pay_play);
        tv_over_pay_back = findTextViewById(R.id.tv_over_pay_back);
        tv_over_money = findTextViewById(R.id.tv_over_money);
        money = getIntent().getStringExtra("fmoney");
        tv_over_money.setText("￥" + money);
        //返回
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                close();
                sendBroadcast(new Intent(MyService.FINISH_PAGE));
                finish();
            }
        });
        //返回
        tv_over_pay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                close();
                sendBroadcast(new Intent(MyService.FINISH_PAGE));
                finish();
            }
        });
        //打印小票
        tv_over_pay_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                times = format.format(date);
                write();
                if (Printer.show != null) {
                    if (!Printer.show.isRecycled()) {
                        Printer.show.recycle();
                        Printer.show = null;
                    }
                }
                Print();
            }
        });
    }

    /**
     * 按 2次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendBroadcast(new Intent(MyService.FINISH_PAGE));
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void write() {
        data.clear();
        PrintItemObj obj1 = new PrintItemObj(App.getToken().merName, 8, true, PrintItemObj.ALIGN.CENTER);
        PrintItemObj obj2 = new PrintItemObj("       收银金额" + money + "元", 8, false);
        PrintItemObj obj3 = new PrintItemObj("       商户编号:" + App.getMerNum(), 8, false);
        PrintItemObj obj4 = null;
        if (2 == getIntent().getIntExtra("title", 0)) {
            obj4 = new PrintItemObj("       收款方式:" + "支付宝支付", 8, false);
        } else {
            obj4 = new PrintItemObj("       收款方式:" + "微信支付", 8, false);
        }
        PrintItemObj obj5 = new PrintItemObj("       收款形式:" + "POS收款", 8, false);
        PrintItemObj obj6 = new PrintItemObj("       收银时间:" + times, 8, false);
        data.add(obj1);
        data.add(obj2);
        data.add(obj3);
        data.add(obj4);
        data.add(obj5);
        data.add(obj6);
    }

    public void Print() {
        try {
            Printer.getInstance(OverPayAct.this).printText(data, new AidlPrinterListener() {

                @Override
                public IBinder asBinder() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onPrintFinish() throws RemoteException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onError(int i) throws RemoteException {
                    // TODO Auto-generated method stub

                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}