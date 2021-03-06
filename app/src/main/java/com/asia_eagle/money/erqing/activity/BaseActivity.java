package com.asia_eagle.money.erqing.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.asia_eagle.money.erqing.R;

import com.asia_eagle.money.erqing.util.ProgressFragmentActivity;


/**
 * Activity基类
 *
 * @author wzh
 */
public class BaseActivity extends ProgressFragmentActivity {

    private LinearLayout ll_content;
    protected View contentView;
    public static Context context;
    /**
     * 左边按钮
     */
    public ImageButton btn_left;
    /**
     * 左边文字
     */
    public TextView tv_left;
    /**
     * 中间标题
     */
    public TextView tv_center;
    /**
     * 右边按钮
     */
    public TextView btn_right;
    /**
     * 返回按钮
     */
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base);
        context = this;
        initView();
    }

    BroadcastReceiver logout = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            finish();
        }
    };

    public ImageView findImageViewById(int resId) {
        return (ImageView) findViewById(resId);
    }

    public TextView findTextViewById(int resId) {
        return (TextView) findViewById(resId);
    }

    public ListView findListViewById(int resId) {
        return (ListView) findViewById(resId);
    }

    public RelativeLayout findRelativeLayoutById(int resId) {
        return (RelativeLayout) findViewById(resId);
    }

    public LinearLayout findLinearLayoutById(int resId) {
        return (LinearLayout) findViewById(resId);
    }

    public ScrollView findScrollViewById(int resId) {
        return (ScrollView) findViewById(resId);
    }

    public EditText findEditTextById(int resId) {
        return (EditText) findViewById(resId);
    }

    public Button findButtonById(int resId) {
        return (Button) findViewById(resId);
    }

    private void initView() {
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        btn_right = findTextViewById(R.id.tv_right_send);
        tv_center = findTextViewById(R.id.tv_center);
        tv_back=findTextViewById(R.id.tv_back);
        btn_left= (ImageButton) findViewById(R.id.btn_left);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeft();
            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLeft();
            }
        });
    }

    /**
     * 左按钮点击事�??
     */
    public void clickLeft() {

    }

    /**
     * 右边按钮点击事件
     */
    public void clickRight() {

    }

    /**
     * 设置左边按钮的图�??
     */
    public void setImageLeft(int imgId) {
        btn_left.setImageResource(imgId);
        btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置中心标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_center.setText(title);
    }

    /**
     * 设置中心标题
     *
     * @param title
     */
    public void setRight(String title) {
        btn_right.setText(title);
    }

    /**
     * 设置左方标题
     *
     * @param title
     */
    public void setLeftText(String title) {
        tv_left.setText(title);
    }

    /**
     * 加入页面内容布局
     *
     * @param layoutId
     */
    protected void contentView(int layoutId) {
        contentView = getLayoutInflater().inflate(layoutId, null);
        if (ll_content.getChildCount() > 0) {
            ll_content.removeAllViews();
        }
        if (contentView != null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            ll_content.addView(contentView, params);
        }
    }

}
