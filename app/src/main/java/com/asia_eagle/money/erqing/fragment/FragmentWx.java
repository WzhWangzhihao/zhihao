package com.asia_eagle.money.erqing.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.ReceivablesAct;


/**
 * 微信
 *
 * @author wzh
 */
public class FragmentWx extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_wx_shoukuanma;
    private LinearLayout ll_wx_saomafu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_home_wxpay);
        Init();
        return contentView;
    }

    private void Init() {
        ll_wx_shoukuanma = findLinearLayoutById(R.id.ll_wx_shoukuanma);
        ll_wx_saomafu = findLinearLayoutById(R.id.ll_wx_saomafu);
        ll_wx_saomafu.setOnClickListener(this);
        ll_wx_shoukuanma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wx_shoukuanma:
                Intent wxintent1 = new Intent(getActivity(), ReceivablesAct.class);
                wxintent1.putExtra("type", 4);
                startActivity(wxintent1);
                break;
            case R.id.ll_wx_saomafu:
                Intent wxintent2 = new Intent(getActivity(), ReceivablesAct.class);
                wxintent2.putExtra("type", 5);
                startActivity(wxintent2);
                break;
        }
    }
}
