package com.asia_eagle.money.erqing.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.App;
import com.asia_eagle.money.erqing.activity.LoginAct;
import com.asia_eagle.money.erqing.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 流水
 *
 * @author wzh
 */
public class FragmentLiuShui extends BaseFragment implements View.OnClickListener {
//    private Pager pager = new Pager(10);
//    List<Liushui> list = new ArrayList<>();
//    private LiuShuiAdapter adapter;
//    private ListView lv_home;
//    private Pager pager1 = new Pager(10);
//    List<QQLiuShui> list1 = new ArrayList<QQLiuShui>();
//    private CommonAdapter<QQLiuShui> adapter1;
//    private ListView lv_home_qq;

    //    private RefreshLayout swipeLayout;
    private RelativeLayout rl_top;
    private TextView tv_exit, tv_wx_liushui, tv_qq_liushui,tv_ali_liushui;
    private ImageButton btn_right;
    private ViewPager fg_home_view_pager;
    private List<BaseFragment> list = new ArrayList<>();
//    private int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(inflater, R.layout.fg_liushui);
        Init();
        return contentView;
    }

    private void Init() {
//        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
//        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);
//        setListener();
//        lv_home = findListViewById(R.id.lv_home);
        rl_top = findRelativeLayoutById(R.id.rl_top);
        tv_exit = findTextViewById(R.id.tv_exit);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        tv_wx_liushui = findTextViewById(R.id.tv_wx_liushui);
        tv_qq_liushui = findTextViewById(R.id.tv_qq_liushui);
        tv_ali_liushui=findTextViewById(R.id.tv_ali_liushui);
        tv_ali_liushui.setOnClickListener(this);
        tv_qq_liushui.setOnClickListener(this);
        tv_wx_liushui.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        rl_top.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        fg_home_view_pager = (ViewPager) findViewById(R.id.fg_home_view_pager);
        FragmentLiuShuiWx wx = new FragmentLiuShuiWx();
        FragmentLiuShuiAli ali = new FragmentLiuShuiAli();
        FragmentLiuShuiQQ qq = new FragmentLiuShuiQQ();
        list.add(wx);
//        list.add(qq);
        list.add(ali);
        fg_home_view_pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        fg_home_view_pager.setCurrentItem(0);
    }


//        getActivity().registerReceiver(receiver, new IntentFilter(MyService.FINISH_UPDATE));
//    }
//
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            onRefresh();
//        }
//    };

//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setOnLoadListener(this);
//    }

//    /**
//     * 上拉
//     */
//    @Override
//    public void onLoad() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // 更新数据 更新完后调用该方法结束刷新
//                if (type == 1) {
//                    getData(true);
//                } else {
//                    getDataqq(true);
//                }
//                swipeLayout.setLoading(false);
//            }
//        }, 1000);
//    }

//    /**
//     * 下拉
//     */
//    @Override
//    public void onRefresh() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // 更新数据 更新完后调用该方法结束刷新
//                pager.setFirstPage();
//                swipeLayout.setRefreshing(false);
//                swipeLayout.UptdateisAddFooter();
//                if (type == 1) {
//                    getData(true);
//                } else {
//                    getDataqq(true);
//                }
//
//            }
//        }, 1000);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top:
                rl_top.setVisibility(View.GONE);
                break;
            case R.id.tv_exit:
//                getActivity().finish();
//                System.exit(0);
                App.setInstId("");
                App.setToken(null);
                App.setMerNum("");
                SharedPreferencesHelper.saveString("key", "2");
                startActivity(new Intent(getActivity(), LoginAct.class));
                getActivity().finish();
                break;
            case R.id.btn_right:
                rl_top.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_wx_liushui:
//                type = 1;
//                getData(true);
                fg_home_view_pager.setCurrentItem(0);
                rl_top.setVisibility(View.GONE);
                break;
            case R.id.tv_qq_liushui:
//                type = 2;
//                getDataqq(true);
                fg_home_view_pager.setCurrentItem(1);
                rl_top.setVisibility(View.GONE);
                break;
            case R.id.tv_ali_liushui:
//                type = 2;
//                getDataqq(true);
                fg_home_view_pager.setCurrentItem(1);
                rl_top.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


//
//    private void initAdapterQQ() {
//        adapter1 = new CommonAdapter<QQLiuShui>(getActivity(), list1, R.layout.item_home_liushui) {
//            @Override
//            public void convert(ViewHolder helper, final QQLiuShui item, int position) {
//                TextView tv_item_money = helper.getView(R.id.tv_item_money);
//                TextView tv_item_time = helper.getView(R.id.tv_item_time);
//                TextView tv_item_order = helper.getView(R.id.tv_item_order);
//                LinearLayout ll_item_main = helper.getView(R.id.ll_item_main);
//
//            }
//        };
//    }

//    public void getData(boolean home) {
////        lv_home_qq.setVisibility(View.GONE);
////        lv_home.setVisibility(View.VISIBLE);
//        if (home) {
//            swipeLayout.UptdateisAddFooter();
//            pager.setFirstPage();
//        }
//        if (pager.isLastPage()) {
//            swipeLayout.removeFooterView();
////			App.toast("没有更多了!");
//            return;
//        }
//        ((HomeAct) getActivity()).progress("正在加载");
//        final String url = MyService.API_GETTRANS + "/" + (pager.nextPage() - 1) + "/10";
//        OkHttpUtils.get().url(url).addHeader("instId", App.getInstId())
//                .addHeader("merNum", App.getMerNum())
//                .addHeader("accessToken", App.getToken().accessToken)
//                .addHeader("Content-Type", "application/json")
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ((HomeAct) getActivity()).hideProgress();
//                e.printStackTrace();
//                App.toast("与服务器通讯失败!");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                ((HomeAct) getActivity()).hideProgress();
//                Log.e("response", response);
//                try {
//                    ArrayListLiushui al = new Gson().fromJson(response, ArrayListLiushui.class);
//                    if (al.isSuccess()) {
//                        // 如果是第一页,清空数据(必须在setCurrentPage之前)
//                        if (pager.nextPage() - 1 == 0) {
//                            list.clear();
//                        }
//                        // 更新分页器的页码
//                        pager.setCurrentPage(pager.nextPage(), al.data.size());
//                        list.addAll(al.data);
//                        if (list.size() < 10) {
//                            swipeLayout.removeFooterView();
//                        }
//                        for (int i = 0; i < list.size(); i++) {
//                            list.get(i).type = 0;
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        App.toast(al.getMessage() + "");
//                    }
//                } catch (Exception e) {
//                    App.toast("与服务器连接失败!");
//                }
//            }
//        });
//    }

//    public void getDataqq(boolean home) {
////        lv_home_qq.setVisibility(View.VISIBLE);
////        lv_home.setVisibility(View.GONE);
//        if (home) {
//            swipeLayout.UptdateisAddFooter();
//            pager.setFirstPage();
//        }
//        if (pager.isLastPage()) {
//            swipeLayout.removeFooterView();
////			App.toast("没有更多了!");
//            return;
//        }
//        ((HomeAct) getActivity()).progress("正在加载");
//        final String url = MyService.API_QQGETTRANS + "/" + (pager.nextPage() - 1) + "/10";
//        OkHttpUtils.get().url(url).addHeader("instId", App.getInstId())
//                .addHeader("merNum", App.getMerNum())
//                .addHeader("accessToken", App.getToken().accessToken)
//                .addHeader("Content-Type", "application/json")
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ((HomeAct) getActivity()).hideProgress();
//                e.printStackTrace();
//                App.toast("与服务器通讯失败!");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                ((HomeAct) getActivity()).hideProgress();
//                Log.e("response", response);
//                try {
//                    ArrayListLiushui al = new Gson().fromJson(response, ArrayListLiushui.class);
//                    if (al.isSuccess()) {
//                        // 如果是第一页,清空数据(必须在setCurrentPage之前)
//                        if (pager.nextPage() - 1 == 0) {
//                            list.clear();
//                        }
//                        // 更新分页器的页码
//                        pager.setCurrentPage(pager.nextPage(), al.data.size());
//                        list.addAll(al.data);
//                        if (list.size() < 10) {
//                            swipeLayout.removeFooterView();
//                        }
//                        for (int i = 0; i < list.size(); i++) {
//                            list.get(i).type = 1;
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        App.toast(al.getMessage() + "");
//                    }
//                } catch (Exception e) {
//                    App.toast("与服务器连接失败!");
//                }
//            }
//        });
//    }
}
