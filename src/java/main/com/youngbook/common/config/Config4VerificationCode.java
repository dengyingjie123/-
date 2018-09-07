package com.youngbook.common.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个验证码用途的常量类，标识了验证码在何处使用，验证码能将这些记录存入数据库
 */
public class Config4VerificationCode {

    private static Map<Integer, String> useTo = new HashMap<Integer, String>();

    private static void setUses() {
        useTo.put(0, "CODE_USE_UNKNOWN");       // 未知用途
        useTo.put(1, "CODE_USE_LOGIN");         // 登录用途
        useTo.put(2, "CODE_USE_REGISTER");      // 注册用途
        useTo.put(3, "CODE_USE_RECHARGE");      // 充值用途
        useTo.put(4, "CODE_USE_WITHDRAWAL");    // 提现用途
        useTo.put(5, "CODE_USE_PRODUCTION_PAY");    // 产品支付用途
        useTo.put(6, "CODE_USE_INVESTMENT_PLAN");   // 投资计划用途
    }

    /**
     * 获取验证码的用途 Map
     * 用法：Config4VerificationCode.getUseTo()
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-30
     *
     * @author 邓超
     * @return 适用于交易平台的 json，例如 {hasQuestions : 1}
     * @throws Exception
     */
    public static Map<Integer, String> getUseTo() {
        setUses();
        return useTo;
    }

}
