package com.asia_eagle.money.erqing.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.App;
import com.asia_eagle.money.erqing.activity.ReceivablesAct;


/**
 * 支付宝
 *
 * @author wzh
 */
public class FragmentAli extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_alipay_shoukuanma;
//    private LinearLayout ll_alipay_shenbofu;
    private LinearLayout ll_alipay_saomafu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_home_alipay);
        Init();
        return contentView;
    }
    private void Init(){
        ll_alipay_shoukuanma=findLinearLayoutById(R.id.ll_alipay_shoukuanma);
//        ll_alipay_shenbofu=findLinearLayoutById(R.id.ll_alipay_shenbofu);
        ll_alipay_saomafu=findLinearLayoutById(R.id.ll_alipay_saomafu);
        ll_alipay_shoukuanma.setOnClickListener(this);
//        ll_alipay_shenbofu.setOnClickListener(this);
        ll_alipay_saomafu.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
    switch(v.getId()){
        case R.id.ll_alipay_shoukuanma:
            Intent aliintent1 = new Intent(getActivity(), ReceivablesAct.class);
            aliintent1.putExtra("type", 1);
            startActivity(aliintent1);
            break;
//        case R.id.ll_alipay_shenbofu:
//            App.toast("支付宝暂未开通");
//            Intent aliintent2 = new Intent(getActivity(), ReceivablesAct.class);
//            aliintent2.putExtra("type", 2);
//            startActivity(aliintent2);
//            break;
        case R.id.ll_alipay_saomafu:
            Intent aliintent3 = new Intent(getActivity(), ReceivablesAct.class);
            aliintent3.putExtra("type", 3);
            startActivity(aliintent3);
            break;
    }
    }
}
