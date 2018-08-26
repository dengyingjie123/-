package com.youngbook.common.config;

public class Config4W {

    // 后台银行kv组
    public static final String W_BANK_KV_GROUP = "Bank";


    //身份认证成功
    public static final int ACCOUNTSTATUS = 1;

    // 网站的厚币财富收款 Customer
    public static final String W_CUSTOMER_PAYEEID = "C2A4841BB76E497A96E5F96BE613B3CB";

    public static final String W_SET_TransPassword = "/core/w/modules/customer/transPassword.jsp";
    //
    public static final int TRX_CODE = 100002;   //测试批量代付代码
    public static final int TRX_AUTH = 220001;   //测试国名身份认证

    public static final int TRX_TRADE = 200004;   //测试交易结果查询



    public static final String VERSION = "03"; //版本  默认2
    public static final int DATA_TYPE = 2; //数据类型 2是xml
    public static final int LEVEL = 5; //优先级  默认5

    public static final String BUSINESS_CODE ="09900";//测试交易代码
    public static final String MERCHANT_IDs ="200604000000445";//测试交易代码 200604000000445


    public static final String TEMP_CUSTOMER_ID = "CustomerID";
    public static final String TEMP_CUSTOMER_LoginName = "LoginName";


    //招商的测试的银行代码 4314730
    public static final String BANK_CODEs="308";

    public static final String W_LOGIN_ID = "loginId";
    public static final String W_LOGIN_NAME = "loginName";


    // 忘记密码使用的临时session
    public static final String TEMP_SESSION_LOGINUSER_STRING = "tempLoginUser";

}