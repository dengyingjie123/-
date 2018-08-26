package com.youngbook.common;

/**
 * 返回值定义类
 * 知识库地址： http://c.youngbook.net:94/pages/viewpage.action?pageId=1441905
 */
public class ReturnObjectCode {

    // -------------------- 1000 - 1999 公共开始 ------------------- //
    // 参数不完整
    public static final Integer PUBLIC_PARAMETER_NOT_COMPLETE = 1000;
    // 参数未加密
    public static final Integer PUBLIC_PARAMETER_NOT_ENCRYPT = 1001;
    // 验证码不正确
    public static final Integer PUBLIC_WEBCODE_NOT_CORRECT = 1002;
    // Token不正确
    public static final Integer PUBLIC_TOKEN_NOT_CORRECT = 1004;
    // Token失效
    public static final Integer PUBLIC_TOKEN_IS_INVALID = 1005;
    // 参数不正确
    public static final Integer PUBLIC_PARAMETER_NOT_CORRECT = 1002;
    // 两次密码不相同
    public static final Integer PUBLIC_PASSWORDS_NOT_SAME = 1003;
    // 发送短信失败
    public static final Integer PUBLIC_SMS_SEND_ERROR = 1006;
    // 动态码不正确
    public static final Integer PUBLIC_MOBILECODE_NOT_CORRECT = 1007;
    // 没有银行卡列表
    public static final Integer PUBLIC_BANKS_NOT_FOUND = 1008;
    // 不支持该银行卡
    public static final Integer PUBLIC_BANK_CODE_NOT_FOUND = 1009;
    // 签名不正确
    public static final Integer PUBLIC_SIGN_NOT_CURRECT = 1010;
    // -------------------- 1000 - 1999 公共结束 ------------------- //


    // -------------------- 2000 - 2199 客户开始 ------------------- //
    // 登录名不存在
    public static final Integer CUSTOMER_LOGIN_NAME_NOT_EXISTENT = 2000;
    // 登录名已被注册
    public static final Integer CUSTOMER_LOGIN_NAME_IS_REGISTERED = 2001;
    // 交易密码未设置
    public static final Integer CUSTOMER_TRANDING_PASSWORD_NOT_SET = 2002;
    // 没有实名认证
    public static final Integer CUSTOMER_NOT_REAL_AUTHENTICATION = 2003;
    // 没有绑定银行卡
    public static final Integer CUSTOMER_BANKCARD_NOT_BIND = 2004;
    // 修改或设置交易密码失败
    public static final Integer CUSTOMER_TRANDING_PASSWORD_OPERATION_ERROR = 2005;
    // 未设置交易密码
    public static final Integer CUSTOMER_TRANDING_PASSWORD_NOT_FOUND = 2007;
    // 没有资金记录
    public static final Integer CUSTOMER_MONEY_NOT_FOUND = 2006;
    // 修改或设置手机号码失败
    public static final Integer CUSTOMER_MOBILE_NUMBER_OPERATION_ERROR = 2008;
    // 修改或设置密码失败
    public static final Integer CUSTOMER_MOBILE_PASSWORD_OPERATION_ERROR = 2009;
    // 没有兑付信息
    public static final Integer CUSTOMER_PAYMENTPLAN_NOT_FOUND = 2010;
    // 重新绑定银行卡失败
    public static final Integer CUSTOMER_REBIND_BANK_CARD_FAILED = 2011;
   // 存在未兑付计划信息
    public static final Integer CUSTOMER_PAYMENTPLAN_EXIST = 2012;
    // 用户名或密码错误，登录失败
    public static final Integer CUSTOMER_LOGIN_FAIL = 2013;
    // 客户不存在
    public static final Integer CUSTOMER_NOT_EXISTENT = 2014;
    // 客户没有通过实名验证
    public static final Integer CUSTOMER_NOT_CERTIFICATED = 2015;
    // 身份证已存在
    public static final Integer CUSTOMER_ID_CARD_EXISTENT = 2016;

    // -------------------- 2000 - 2199 客户结束 ------------------- //


    // -------------------- 2200 - 2399 订单开始 ------------------- //
    // 订单获取详情错误
    public static final Integer ORDER_GET_DETIAL_ERROR = 2202;
    // 该订单的产品已经不能再购买
    public static final Integer ORDER_CAN_NOT_PAY_AGAIN = 2203;
    // 订单支付失败
    public static final Integer ORDER_PAY_FAILED = 2204;
    // 订单支付验签失败
    public static final Integer ORDER_PAY_ENCRYPE_FAILED = 2205;
    // 订单回调金额不相符
    public static final Integer ORDER_CALLBACK_MONEY_NOT_CORRECT = 2206;
    // 订单回调银行卡不相符
    public static final Integer ORDER_CALLBACL_BANK_CARD_NOT_CURRENT = 2207;
    // 推荐人不存在 referralCode
    public static final Integer ORDER_REFERRALCODE_NOT_EXISTENT = 2208;
    // 返佣记录不存在
    public static final Integer ORDER_COMMISSION_NOT_EXISTENT = 2209;
    // 投资金额不正确
    public static final Integer ORDER_MONEY_NOT_CURRECT = 2210;
    // 投资金额超过可购买金额
    public static final Integer ORDER_MONEY_MORE_THAN_PRODUCTION_MONEY = 2211;
    // -------------------- 2200 - 2399 订单结束 ------------------- //

    // -------------------- 2400 - 2599 产品开始 ------------------- //
    // 产品不存在
    public static final Integer PRODUCTION_NOT_EXISTENT = 2401;
    // 产品已售罄
    public static final Integer PRODUCTION_NOT_SALING = 2501;
    // 产品已过期
    public static final Integer PRODUCTION_EXPIRED = 2502;
    // -------------------- 2400 - 2599 产品结束 ------------------- //

    // -------------------- 2600 - 2799 外部销售人员开始 ------------------- //
    // 对外销售人员不存在
    public static final Integer SALEMAN_OUTER_NOT_EXISTENT = 2601;
    // 对外销售人员状态异常
    public static final Integer SALEMAN_OUTER_STATUS_EXCEPTION = 2602;
    // 对外销售人员密码修改失败
    public static final Integer SALEMAN_OUTER_UPDATE_PWD_FAILED = 2603;
    // 对外销售人员的银行卡已存在
    public static final Integer SALEMAN_OUTER_BANKCARD_EXISTENT = 2604;
    // -------------------- 2600 - 2799 外部销售人员结束 ------------------- //

    // -------------------- 2800 - 2999 返佣开始 ------------------- //
    // 返佣记录不存在
    public static final Integer COMMISSION_NOT_EXISTENT = 2800;
    // -------------------- 2800 - 2999 返佣结束 ------------------- //

    // -------------------- 3000 - 3199 对外销售人员的客户开始 ------------------- //
    // 对外销售人员的客户不存在
    public static final Integer CUSTOMER_OUTER_NOT_EXISTENT = 3000;
    // -------------------- 3000 - 3199 对外销售人员的客户结束 ------------------- //

    // -------------------- 3200 - 3399 产品构成开始 ------------------- //
    // 产品构成不存在
    public static final Integer PRODUCTION_COMPOSITION_NOT_EXISTENT = 3200;
    // -------------------- 3200 - 3399 产品构成结束 ------------------- //

    // -------------------- 3400 - 3599 内部销售人员开始 ------------------- //
    // 所在销售组多于 1 个
    public static final Integer SALEMAN_GROUP_MORE_THAN_ONE = 3400;
    // -------------------- 3400 - 3599 内部销售人员结束 ------------------- //

}
