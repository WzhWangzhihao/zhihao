package com.asia_eagle.money.erqing.service;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyService {

    /**
     * 销毁流程页面的广播。如注册,找回密码等流程
     */
    public static final String FINISH_PAGE = "action_finish_page";

    /**
     * 刷新
     */
    public static final String FINISH_UPDATE = "action_update_liushi";

    /**
     * 开发环境
     */
    public static final String BASE_URL = "https://tpay.ae-pay.com/yayinclearing/pc";
    /**
     * 正式环境
     */
//    public static final String BASE_URL = "https://pay.ae-pay.com/yayinclearing/pc";
    /**
     * 登录第一步
     */
    public static final String API_LOGIN1 = BASE_URL + "/v1/login/getAccessCode";
    /**
     * 登录第二步
     */
    public static final String API_LOGIN2 = BASE_URL + "/v1/login/getAccessTokenAndLoginInfo";
    /**
     * 微信支付
     */
    public static final String API_WXSCANPAY = BASE_URL + "/v1/wxpay/micropay";
    /**
     * 流水
     */
    public static final String API_GETTRANS = BASE_URL + "/v1/wxpay/trans";

    /**
     * 阿里流水
     */
    public static final String API_ALIGETTRANS = BASE_URL + "/v1/alipay/trans";

    /**
     * 微信生成二维码
     */
    public static final String API_FENERATECODE = BASE_URL + "/v1/wxpay/native";

    /**
     * 微信生成退款
     */
    public static final String API_REFUND = BASE_URL + "/v1/wxpay/refund/";

    /**
     * 微信查看是否支付成功
     */
    public static final String API_SELECTOK = BASE_URL + "/v1/wxpay/selectbyqrcode/";

    /**
     * 微信生成撤单
     */
    public static final String API_REVERSE = BASE_URL + "/v1/wxpay/wxreverse/";

    /**
     * QQ查看是否支付成功
     */
    public static final String API_QQSELECTOK = BASE_URL + "/v1/qqpay/selectbyqrcode/";

    /**
     * QQ生成撤单
     */
    public static final String API_QQREVERSE = BASE_URL + "/v1/qqpay/qqreverse/";
    /**
     * 获取文件地址
     */
    public static final String API_VERSIONURL = "https://pay.ae-pay.com/mobile/version.json";
    /**
     * QQ支付
     */
    public static final String API_QQSCANPAY = BASE_URL + "/v1/qqpay/micropay";
    /**
     * QQ生成二维码
     */
    public static final String API_QQFENERATECODE = BASE_URL + "/v1/qqpay/native";


    /**
     * 支付宝支付
     */
    public static final String API_ALIPAYSCANPAY = BASE_URL + "/v1/alipay/micropay";
    /**
     * 支付宝生成二维码
     */
    public static final String API_ALIPAYFENERATECODE = BASE_URL + "/v1/alipay/native";

    /**
     * 支付宝生成退款
     */
    public static final String API_ALIPAYREFUND = BASE_URL + "/v1/alipay/refund/";

    /**
     * 支付宝生成撤单
     */
    public static final String API_ALIREVERSE = BASE_URL + "/v1/alipay/reverse/";

    /**
     * 支付宝查看是否支付成功
     */
    public static final String API_ALIPAYSELECTOK = BASE_URL + "/v1/alipay/selectbyqrcode/";

    /**
     * 流水
     */
    public static final String API_QQGETTRANS = BASE_URL + "/v1/qqpay/trans";
    /**
     * 查看今日流水
     */
    public static final String API_GETDAYKNO = BASE_URL + "/v1/statistics/all";

    /**
     * 时间戳转化为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time));
        System.out.println(date);
        return date;
    }
}
