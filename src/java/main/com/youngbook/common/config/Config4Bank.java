package com.youngbook.common.config;

import com.youngbook.common.KVObject;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 邓超
 * Date 2015-6-3
 */
public class Config4Bank {

    // 电子支付收款 Customer ID
    public static final String BANK_PAYEE_ID = "C2A4841BB76E497A96E5F96BE613B3CB";

    // 招商银行电子支付常量
    public static final String BANK_CMB_ORDER_NUM_PREFIX = "1";                         // 银行定单号前缀
    public static final String BANK_CMB_REQUESTURL = "payment.ebank.cmbchina.com";      // 银行接口请求 URL
    public static final String BANK_CMB_Key = "020666";                                 // 登录密码
    public static final String BANK_CMB_COPWD = "FFFBADd8a5920853";                     // 商户密钥
    public static final String BANK_CMB_BranchID = "0871";                              // 商户所在分行
    public static final String BANK_CMB_Cono = "020665";                                // 商户号
    public static final String BANK_CMB_MerchantURL = "http://115.28.6.59:90/core/w/production/cmbCallback";           // 银行通知 URL

    // 招商银行真实环境不同连接方式的 URL
    public static final String BANK_CMB_CONNECTION_TYPE_1 = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?PrePayC";   // 连接方式1
    public static final String BANK_CMB_CONNECTION_TYPE_2 = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?PrePayC1";  // 连接方式2
    public static final String BANK_CMB_CONNECTION_TYPE_3 = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?PrePayC2";  // 连接方式3

    // 招商银行测试环境不同连接方式的 URL
    public static final String BANK_CMB_TEST_CONNECTION_TYPE_2 = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?TestPrePayC1";  // 测试连接方式2
    public static final String BANK_CMB_TEST_CONNECTION_TYPE_3 = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?TestPrePayC2";  // 测试连接方式3

    // 招商银行商品类型
    public static final String BANK_GOODS_TYPE_TZLC = "54011692";   // 投资理财

    // 定单业务类型
    public static final Integer BANK_BIZ_TYPE_RECHARGE = 0;      // 充值
    public static final Integer BANK_BIZ_TYPE_WITHDRAWAL = 1;    // 提现
    public static final Integer BANK_BIZ_TYPE_SYSTEM_ORDER = 2;  // 系统订单

    // 银行定单状态
    public static final Integer BANK_BILL_STATUS_SETTLE = 0;        // 结账
    public static final Integer BANK_BILL_STATUS_SETTLE_PART = 1;   // 部份结账
    public static final Integer BANK_BILL_STATUS_UNSETTLE = 2;      // 未结账
    public static final Integer BANK_BILL_STATUS_CANCEL = 3;        // 撤销
    public static final Integer BANK_BILL_STATUS_REFUND = 4;        // 退款
    public static final Integer BANK_BILL_STATUS_INVALID = 5;       // 无效
    public static final Integer BANK_BILL_STATUS_UNKNOWN = 6;       // 未知

    /**
     * 创建人：张舜清
     * 时间：8/3/2015
     * 内容：feature/662-张舜清-实现通联支付功能与系统的交互
     */
    // 内部代表代码，1代表通联支付接口，0代表招商接口
    public static final Integer USE_PAY_TYPE = 1;

    // 字符集，默认选择1，1代表UTF-8、2代表GBK、3代表GB2312
    public static final Integer INPUT_CHARSET_TYPE_UTF_8 =1;
    public static final Integer INPUT_CHARSET_TYPE_GBK = 2;
    public static final Integer INPUT_CHARSET_TYPE_GB2312 = 3;

    // 网关页面显示语言种类，默认选择1，1代表简体中文、2代表繁体中文、3代表英语
    public static final Integer LANGUAGE_TYPE_SIMPLIFIED_CHINESE = 1;
    public static final Integer LANGUAGE_TYPE_TRADITIONAL_CHINESE = 2;
    public static final Integer LANGUAGE_TYPE_ENGLISH = 3;

    // 签名类型，默认选择1，1表示商户用使用MD5算法验签上送订单，通联交易结果通知使用证书签名，0表示订单上送和交易结果通知都是用MD5进行签名
    public static final Integer SING_TYPE_0 = 0;
    public static final Integer SING_TYPE_1 = 1;

    // 订单金额币种类型，默认是0或者156，0和156代表人民币，840代表美元，344代表港币
    public static final Integer ORDER_CURRENCY_TYPE_0 = 0;
    public static final Integer ORDER_CURRENCY_TYPE_156 = 156;
    public static final Integer ORDER_CURRENCY_TYPE_840 = 840;
    public static final Integer ORDER_CURRENCY_TYPE_344 = 344;

    // 支付方式，默认为0
    // 0表示为指定支付方式，即显示该商户开通的所有支付方式、1表示个人储蓄卡网银支付、4表示企业网银支付、11个人信用卡网银支付、23表示外卡支付
    public static final Integer PAY_TYPE_0 = 0;
    public static final Integer PAY_TYPE_1 = 1;
    public static final Integer PAY_TYPE_4 = 4;
    public static final Integer PAY_TYPE_11 = 11;
    public static final Integer PAY_TYPE_23 = 23;
    public static final Integer PAY_TYPE_28 = 28;

    // 通联临时测试商户号
    public static final String TEST_MERCHANT_ID = "100020091218001";  // 测试 100020091218001

    // 通联测试环境提供的KEY
    public static final String AllinPay_TEST_KEYs = "1234567890";

    // 通联支付订单提交地址
    public static final String AllinPay_Order_Submit_Urls = "http://ceshi.allinpay.com/gateway/index.do";

    // 商品名称
    public static final String ALLINPAY_PRODUCTNAME = "厚币财富理财专户";

//    public static Map<String, String> getBanks() throws Exception {
//        XmlHelper xml = new XmlHelper(Config.getConfigBankFile());
//        Map<String, String> value = xml.getBanks( "//config/banks/variable");
//        return value;
//    }

    public static Map<String, String> getBanks(Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_kv where 1=1 and GroupName='Bank' order by Orders");

        List<KVPO> listKVPO = MySQLDao.search(dbSQL, KVPO.class, conn);

        Map<String, String> value = new HashMap<String, String>();

        for (int i = 0; listKVPO != null && i < listKVPO.size(); i++) {
            KVPO kv = listKVPO.get(i);
            value.put(kv.getK(), kv.getV());
        }

        return value;
    }

}
