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
 * qq
 *
 * @author wzh
 */
public class FragmentQq extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_qq_shoukuanma;
    private LinearLayout ll_qq_saomafu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_home_qqpay);
        Init();
        return contentView;
    }

    private void Init() {
        ll_qq_shoukuanma = findLinearLayoutById(R.id.ll_qq_shoukuanma);
        ll_qq_saomafu = findLinearLayoutById(R.id.ll_qq_saomafu);
        ll_qq_shoukuanma.setOnClickListener(this);
        ll_qq_saomafu.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_qq_shoukuanma:
                App.toast("QQ暂未开通");
//                Intent qqintent1 = new Intent(getActivity(), ReceivablesAct.class);
//                qqintent1.putExtra("type", 6);
//                startActivity(qqintent1);
                break;
            case R.id.ll_qq_saomafu:
                App.toast("QQ暂未开通");
//                Intent qqintent2 = new Intent(getActivity(), ReceivablesAct.class);
//                qqintent2.putExtra("type", 7);
//                startActivity(qqintent2);
                break;
        }
    }
}
