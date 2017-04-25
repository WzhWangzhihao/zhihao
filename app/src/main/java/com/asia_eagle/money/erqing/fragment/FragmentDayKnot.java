package com.asia_eagle.money.erqing.fragment;


import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.App;
import com.asia_eagle.money.erqing.activity.HomeAct;
import com.asia_eagle.money.erqing.entity.AliPayStatistic;
import com.asia_eagle.money.erqing.entity.AllPayStatistic;
import com.asia_eagle.money.erqing.entity.Dayknot;
import com.asia_eagle.money.erqing.entity.WxPayStatistic;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.util.Printer;
import com.asia_eagle.money.erqing.util.Tools;
import com.google.gson.Gson;
import com.lkl.cloudpos.aidl.printer.AidlPrinterListener;
import com.lkl.cloudpos.aidl.printer.PrintItemObj;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 日结
 *
 * @author wzh
 */
public class FragmentDayKnot extends BaseFragment implements View.OnClickListener {
    private ImageButton btn_right;
    private TextView tv_wx_day, tv_qq_day, tv_alipay_day;
    private RelativeLayout rl_top;
    private LinearLayout ll_main;
    private AliPayStatistic aliPayStatistic;
    private AllPayStatistic allPayStatistic;
    private WxPayStatistic wxPayStatistic;
    private TextView tv_zong_money, tv_zong_count, tv_tui_money, tv_tui_count, tv_zong_fangshi, tv_zong_day, tv_order_details_submit;
    boolean f = true;
    int title = 1;
    List<PrintItemObj> data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_day_knot);
        Init();
        return contentView;
    }

    private void Init() {
        f = false;
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        tv_wx_day = (TextView) findViewById(R.id.tv_wx_day);
        tv_qq_day = (TextView) findViewById(R.id.tv_qq_day);
        tv_zong_money = (TextView) findViewById(R.id.tv_zong_money);
        tv_zong_count = (TextView) findViewById(R.id.tv_zong_count);
        tv_tui_money = (TextView) findViewById(R.id.tv_tui_money);
        tv_tui_count = (TextView) findViewById(R.id.tv_tui_count);
        tv_zong_fangshi = (TextView) findViewById(R.id.tv_zong_fangshi);
        tv_zong_day = findTextViewById(R.id.tv_zong_day);
        tv_alipay_day = (TextView) findViewById(R.id.tv_alipay_day);
        tv_order_details_submit = findTextViewById(R.id.tv_order_details_submit);
        rl_top = findRelativeLayoutById(R.id.rl_top);
        ll_main = findLinearLayoutById(R.id.ll_main);
        btn_right.setOnClickListener(this);
        tv_wx_day.setOnClickListener(this);
        tv_qq_day.setOnClickListener(this);
        tv_alipay_day.setOnClickListener(this);
        ll_main.setOnClickListener(this);
        tv_zong_day.setOnClickListener(this);
        tv_order_details_submit.setOnClickListener(this);
        title = 1;
        data = new ArrayList<PrintItemObj>();
        getData();
    }

    public void getData() {
        ((HomeAct) getActivity()).progress("正在加载");
        final String url = MyService.API_GETDAYKNO + "/" + Tools.getDataYMD() + "/" + Tools.getDataYMDHMS();
        OkHttpUtils.get().url(url).addHeader("instId", App.getInstId())
                .addHeader("merNum", App.getMerNum())
                .addHeader("accessToken", App.getToken().accessToken)
                .addHeader("Content-Type", "application/json")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ((HomeAct) getActivity()).hideProgress();
                e.printStackTrace();
                App.toast("与服务器通讯失败!");
            }

            @Override
            public void onResponse(String response, int id) {
                ((HomeAct) getActivity()).hideProgress();
                Log.e("response", response);
                try {
                    Dayknot al = new Gson().fromJson(response, Dayknot.class);
                    if (al.isSuccess()) {
                        aliPayStatistic = al.aliPayStatistic;
                        allPayStatistic = al.allPayStatistic;
                        wxPayStatistic = al.wxPayStatistic;
                        if (!(allPayStatistic == null || "".equals(allPayStatistic))) {
                            setData("总帐单", allPayStatistic.totalPay, allPayStatistic.paySuccessNum, allPayStatistic.payRefundMoney, allPayStatistic.payRefundNum);
                        }
                    } else {
                        App.toast(al.getMessage() + "");
                    }
                } catch (Exception e) {
                    App.toast("与服务器连接失败!");
                }
            }
        });
    }

    public void setData(String type, String totalPay, String paySuccessNum, String payRefundMoney, String payRefundNum) {
        if (!TextUtils.isEmpty(totalPay)) {
            tv_zong_money.setText(totalPay + "元");
        } else {
            tv_zong_money.setText("0.00元");
        }
        if (!TextUtils.isEmpty(paySuccessNum)) {
            tv_zong_count.setText(paySuccessNum + "次");
        } else {
            tv_zong_count.setText("0次");
        }
        if (!TextUtils.isEmpty(payRefundMoney)) {
            tv_tui_money.setText(payRefundMoney + "元");
        } else {
            tv_tui_money.setText("0.00元");
        }
        if (!TextUtils.isEmpty(payRefundNum)) {
            tv_tui_count.setText(payRefundNum + "次");
        } else {
            tv_tui_count.setText("0次");
        }
        if (!TextUtils.isEmpty(type)) {
            tv_zong_fangshi.setText(type);
        }

    }

//    public void printText(List<PrintItemObj> data, AidlPrinterListener listener)
//            throws RemoteException {
//        if(null == listener){
//            return ;
//        }
//        if(null == data){
//            listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
//        }
//        LakalaDebug.LogD("Printer","printText");
//        View view = LayoutInflater.from(context).inflate(R.layout.print_templete, null);
//        LinearLayout llTemplete = (LinearLayout) view.findViewById(R.id.container);
//
//        for (PrintItemObj item : data) {
//            if (item.getFontSize()<=0) {
//                listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
//                return;
//            }
//            int fontSize = getFontSize(item.getFontSize());
//            TextView tView = new TextView(context);
//            tView.setLayoutParams(new LayoutParams(768,LayoutParams.WRAP_CONTENT));
//            LakalaDebug.LogD("printText  item.getText()=", item.getText()+item.getFontSize());
//            LakalaDebug.LogD("printText  item.getText()=", item.getText()+fontSize);
//            SpannableString spannableString = new SpannableString(item.getText());
//            spannableString.setSpan(new AbsoluteSizeSpan(fontSize*4), 0, spannableString.toString()
//                    .length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            tView.setText(spannableString);
//            tView.setTextColor(Color.BLACK);
//            //tView.setTextSize(fontSize);
//
////			Typeface fontFace = Typeface.createFromAsset(context.getAssets(), "fonts/Redocn001.ttf");
////			tView.setTypeface(fontFace);
//
//
//            tView.setPadding(item.getMarginLeft(), 0, 0, 0);
//            ALIGN align = item.getAlign();
//            if (align == ALIGN.LEFT) {
//                tView.setGravity(Gravity.LEFT);
//            } else if (align == ALIGN.RIGHT) {
//                tView.setGravity(Gravity.RIGHT);
//            } else if (align == ALIGN.CENTER) {
//                tView.setGravity(Gravity.CENTER_HORIZONTAL);
//            }
//            if (item.isUnderline()) {
//                tView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//                tView.getPaint().setAntiAlias(true);
//            }
//            if (item.isBold()) {
//                tView.getPaint().setFakeBoldText(true);
//            }
//            llTemplete.addView(tView);
//        }
//
//        if (printThread != null) {
//            printThread.interrupt();
//        }
//        printThread = new PrintThread(listener, convertViewToBitmap(llTemplete));
//        printThread.start();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                rl_top.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_wx_day:
                rl_top.setVisibility(View.GONE);
                title = 2;
                if (!(wxPayStatistic == null || "".equals(wxPayStatistic))) {
                    setData("微信", wxPayStatistic.totalPay, wxPayStatistic.paySuccessNum, wxPayStatistic.payRefundMoney, wxPayStatistic.payRefundNum);
                } else {
                    App.toast("今天微信账单为空!");
                }
                break;
            case R.id.tv_qq_day:
                rl_top.setVisibility(View.GONE);
                break;
            case R.id.tv_alipay_day:
                rl_top.setVisibility(View.GONE);
                title = 3;
                if (!(aliPayStatistic == null || "".equals(aliPayStatistic))) {
                    setData("支付宝", aliPayStatistic.totalPay, aliPayStatistic.paySuccessNum, aliPayStatistic.payRefundMoney, aliPayStatistic.payRefundNum);
                } else {
                    App.toast("今天支付宝账单为空!");
                }
                break;
            case R.id.ll_main:
                rl_top.setVisibility(View.GONE);
                break;
            case R.id.tv_zong_day:
                title = 1;
                rl_top.setVisibility(View.GONE);
                if (!(allPayStatistic == null || "".equals(allPayStatistic))) {
                    setData("总帐单", allPayStatistic.totalPay, allPayStatistic.paySuccessNum, allPayStatistic.payRefundMoney, allPayStatistic.payRefundNum);
                } else {
                    App.toast("今天总帐单为空！");
                }
                break;
            case R.id.tv_order_details_submit:
//                if (open()) {
//                    if (queryStatus() > 0) {
                switch (title) {
                    case 1:
                        write("总帐单", allPayStatistic.totalPay, allPayStatistic.paySuccessNum, allPayStatistic.payRefundMoney, allPayStatistic.payRefundNum);

                        break;
                    case 2:
                        write("微信", wxPayStatistic.totalPay, wxPayStatistic.paySuccessNum, wxPayStatistic.payRefundMoney, wxPayStatistic.payRefundNum);

                        break;
                    case 3:
                        write("支付宝", aliPayStatistic.totalPay, aliPayStatistic.paySuccessNum, aliPayStatistic.payRefundMoney, aliPayStatistic.payRefundNum);

                        break;
                }
//                    } else {
//                        App.toast("打印机缺纸");
//                    }
//                } else {
////                    App.toast("打印机故障");
//                }
                if (Printer.show != null) {
                    if (!Printer.show.isRecycled()) {
                        Printer.show.recycle();
                        Printer.show = null;
                    }
                }
                Print();
                break;
            default:
                break;

        }
    }

    private void write(String sttats, String zongMoney, String count, String tuiMoney, String tuiCount) {
        data.clear();
        PrintItemObj obj1 = new PrintItemObj(App.getToken().merName, 8, true, PrintItemObj.ALIGN.CENTER);
        PrintItemObj obj2 = new PrintItemObj("       " + sttats, 8, false);
        PrintItemObj obj3 = new PrintItemObj("       收银总额:" + zongMoney + "元", 8, false);
        PrintItemObj obj4 = new PrintItemObj("       收款次数:" + count + "次", 8, false);
        PrintItemObj obj5 = new PrintItemObj("       退款金额：" + tuiMoney + "元", 8, false);
        PrintItemObj obj6 = new PrintItemObj("       退款次数:" + tuiCount + "次", 8, false);
        PrintItemObj obj7 = new PrintItemObj("       收款日期:" + Tools.getDataYMDS(), 8, false);
        data.add(obj1);
        data.add(obj2);
        data.add(obj3);
        data.add(obj4);
        data.add(obj5);
        data.add(obj6);
        data.add(obj7);
    }

    public void Print() {
        try {
            Printer.getInstance(getActivity()).printText(data, new AidlPrinterListener() {

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

    public void showDate() {
        if (!f) {
            getData();
        }
    }
}
