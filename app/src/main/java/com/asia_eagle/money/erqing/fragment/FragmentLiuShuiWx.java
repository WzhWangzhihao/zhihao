package com.asia_eagle.money.erqing.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.App;
import com.asia_eagle.money.erqing.activity.HomeAct;
import com.asia_eagle.money.erqing.activity.OrderdetailsAct;
import com.asia_eagle.money.erqing.adapter.CommonAdapter;
import com.asia_eagle.money.erqing.adapter.ViewHolder;
import com.asia_eagle.money.erqing.entity.ArrayListLiushui;
import com.asia_eagle.money.erqing.entity.Liushui;
import com.asia_eagle.money.erqing.refresh.RefreshLayout;
import com.asia_eagle.money.erqing.service.MyService;
import com.asia_eagle.money.erqing.util.Pager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 微信交易记录
 *
 * @author wzh
 */
public class FragmentLiuShuiWx extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    private Pager pager_wx = new Pager(10);
    List<Liushui> list_wx = new ArrayList<>();
    private CommonAdapter<Liushui> adapter_wx;
    private ListView lv_home_wx;
    private RefreshLayout swipeLayout_wx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fragment_viewpager_wx);
        Init_wx();
        return contentView;
    }

    private void Init_wx() {
        swipeLayout_wx = (RefreshLayout) findViewById(R.id.swipe_container_wx);
        swipeLayout_wx.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);
        setListener();
        lv_home_wx = findListViewById(R.id.lv_home_wx);
        initAdapter_wx();
        lv_home_wx.setAdapter(adapter_wx);
        getData(true);
        getActivity().registerReceiver(receiver, new IntentFilter(MyService.FINISH_UPDATE));

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            onRefresh();
        }
    };

    @Override
    public void onClick(View v) {
    }

    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout_wx.setOnRefreshListener(this);
        swipeLayout_wx.setOnLoadListener(this);
    }

    private void initAdapter_wx() {
        adapter_wx = new CommonAdapter<Liushui>(getActivity(), list_wx, R.layout.item_home_liushui) {
            @Override
            public void convert(ViewHolder helper, final Liushui item, int position) {
                TextView tv_item_money = helper.getView(R.id.tv_item_money);
                TextView tv_item_time = helper.getView(R.id.tv_item_time);
                TextView tv_item_order = helper.getView(R.id.tv_item_order);
                LinearLayout ll_item_main = helper.getView(R.id.ll_item_main);
//                if (TextUtils.isEmpty(item.payWeixinNo)) {
//                    tv_item_order.setText("消费单号：");
//                } else {
//                    tv_item_order.setText("消费单号：" + item.payWeixinNo);
//                }
                if (item.totalFee <= 0) {
                    tv_item_money.setText("+0");
                } else {
                    BigDecimal b1 = new BigDecimal(item.totalFee).multiply(new BigDecimal(0.01));
                    String total_fee = String.valueOf(b1.doubleValue());
                    tv_item_money.setText("+" + total_fee);
                }
//                if (TextUtils.isEmpty(item.strCreate)) {
//                    tv_item_time.setText("00:00");
//                } else {
//                    tv_item_time.setText(item.strCreate.substring(11, 16));
//                }
                if (!TextUtils.isEmpty(item.strCreate)) {
                    tv_item_time.setText(item.strCreate.substring(5, 16));
                }
                ll_item_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderdetailsAct.class);
                        Liushui liushui = item;
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("liushui", liushui);
                        intent.putExtras(bundle2);
                        mContext.startActivity(intent);
                    }
                });
            }
        };
    }

    /**
     * 上拉
     */
    @Override
    public void onLoad() {
        swipeLayout_wx.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据 更新完后调用该方法结束刷新
                getData(false);
                swipeLayout_wx.setLoading(false);
            }
        }, 1000);
    }

    /**
     * 下拉
     */
    @Override
    public void onRefresh() {
        swipeLayout_wx.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据 更新完后调用该方法结束刷新
                pager_wx.setFirstPage();
                swipeLayout_wx.setRefreshing(false);
                swipeLayout_wx.UptdateisAddFooter();
                getData(true);
            }
        }, 1000);
    }

    public void getData(boolean home) {
        Log.e("fff", "zzzz");
        if (home) {
            swipeLayout_wx.UptdateisAddFooter();
            pager_wx.setFirstPage();
        }
        if (pager_wx.isLastPage()) {
            swipeLayout_wx.removeFooterView();
            return;
        }
        ((HomeAct) getActivity()).progress("正在加载");
        final String url = MyService.API_GETTRANS + "/" + (pager_wx.nextPage() - 1) + "/10";
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
                    ArrayListLiushui al = new Gson().fromJson(response, ArrayListLiushui.class);
                    if (al.isSuccess()) {
                        // 如果是第一页,清空数据(必须在setCurrentPage之前)
                        if (pager_wx.nextPage() - 1 == 0) {
                            list_wx.clear();
                        }
                        // 更新分页器的页码
                        pager_wx.setCurrentPage(pager_wx.nextPage(), al.data.size());
                        list_wx.addAll(al.data);
                        if (al.data.size() < 10) {
                            swipeLayout_wx.removeFooterView();
                        }
                        adapter_wx.notifyDataSetChanged();
                    } else {
                        App.toast(al.getMessage() + "");
                    }
                } catch (Exception e) {
                    App.toast("与服务器连接失败!");
                }
            }
        });
    }
}
