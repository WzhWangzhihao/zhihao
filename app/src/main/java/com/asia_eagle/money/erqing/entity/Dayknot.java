package com.asia_eagle.money.erqing.entity;

/**
 * Created by 志浩 on 2016/12/17.
 */
public class Dayknot {
    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 200;

    /**
     * 返回码 200:成功 300或其他: 失败
     */
    public int code;
    /**
     * 信息
     */
    private String message;
    /**
     * 总数
     */
    public int total;
    /**
     *
     */
    public String accessCode;

    /**
     * 返回码是否为200
     *
     * @return
     */
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    public String getMessage() {
        if (null == message) return "";
        else
            return message;
    }
    public WxPayStatistic wxPayStatistic;
    public AliPayStatistic aliPayStatistic;
    public AllPayStatistic allPayStatistic;
}
