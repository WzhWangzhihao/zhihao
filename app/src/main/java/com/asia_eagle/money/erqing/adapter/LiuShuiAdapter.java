package com.asia_eagle.money.erqing.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.activity.OrderdetailsAct;
import com.asia_eagle.money.erqing.entity.Liushui;
import com.asia_eagle.money.erqing.entity.QQLiuShui;
import com.asia_eagle.money.erqing.service.MyService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 志浩 on 2016/10/10.
 */
public class LiuShuiAdapter extends BaseAdapter {
    private Context mContext;
    private List<Liushui> list;

    public LiuShuiAdapter(Context mContext, List<Liushui> list) {
        super();
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Liushui getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder1 = null;
        ViewHolder holder2 = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case 0:	holder1 = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_liushui, null);
                    holder1.tv_item_money = (TextView) convertView.findViewById(R.id.tv_item_money);
                    holder1.tv_item_time = (TextView) convertView.findViewById(R.id.tv_item_time);
                    holder1.tv_item_order = (TextView) convertView.findViewById(R.id.tv_item_order);
                    holder1.ll_item_main = (LinearLayout) convertView.findViewById(R.id.ll_item_main);
                    break;
                case 1:
                    holder2 = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_liushui, null);
                    holder2.tv_item_money = (TextView) convertView.findViewById(R.id.tv_item_money);
                    holder2.tv_item_time = (TextView) convertView.findViewById(R.id.tv_item_time);
                    holder2.tv_item_order = (TextView) convertView.findViewById(R.id.tv_item_order);
                    holder2.ll_item_main = (LinearLayout) convertView.findViewById(R.id.ll_item_main);
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 0:
                    holder1 = (ViewHolder) convertView.getTag();
                    break;
                case 1:
                    holder2 = (ViewHolder) convertView.getTag();
                    break;
            }
        }
        switch (getItemViewType(position)) {
            case 0:
                getView1(position, holder1);
                break;
            case 1:
                getView2(position, holder2);
                break;
        }
        return convertView;
    }

    public void getView1(int position, ViewHolder holder) {
        final Liushui item = list.get(position);
        if (TextUtils.isEmpty(item.payWeixinNo)) {
            holder.tv_item_order.setText("消费单号：");
        } else {
            holder.tv_item_order.setText("消费单号：" + item.payWeixinNo);
        }
        if (item.totalFee <= 0) {
            holder.tv_item_money.setText("+0");
        } else {
            BigDecimal b1 = new BigDecimal(item.totalFee).multiply(new BigDecimal(0.01));
            String total_fee = String.valueOf(b1.doubleValue());
            holder.tv_item_money.setText("+" + total_fee);
        }
        if (TextUtils.isEmpty(item.strCreate)) {
            holder.tv_item_time.setText("00:00");
        } else {
            holder.tv_item_time.setText(item.strCreate.substring(11, 16));
        }
        holder.ll_item_main.setOnClickListener(new View.OnClickListener() {
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

    public void getView2(int position, ViewHolder holder) {
        final Liushui item = list.get(position);
        if (TextUtils.isEmpty(item.payQQNo)) {
            holder.tv_item_order.setText("消费单号：");
        } else {
            holder.tv_item_order.setText("消费单号：" + item.payQQNo);
        }
        if (item.totalFee <= 0) {
            holder.tv_item_money.setText("+0");
        } else {
            BigDecimal b1 = new BigDecimal(item.totalFee).multiply(new BigDecimal(0.01));
            String total_fee = String.valueOf(b1.doubleValue());
            holder.tv_item_money.setText("+" + total_fee);
        }
        if (item.dbCreate <= 0) {
            holder.tv_item_time.setText("00:00");
        } else {
            holder.tv_item_time.setText(MyService.dateToString(item.dbCreate).substring(11, 16));
        }
        holder.ll_item_main.setOnClickListener(new View.OnClickListener() {
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

    static class ViewHolder {
        TextView tv_item_money;
        TextView tv_item_time;
        TextView tv_item_order;
        LinearLayout ll_item_main;
    }

}
