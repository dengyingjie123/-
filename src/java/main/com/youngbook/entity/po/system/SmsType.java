package com.youngbook.entity.po.system;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmsType {
    // 参考 http://youngbook.net:96/pages/viewpage.action?pageId=22052869
    public static final int TYPE_IDENTIFY_CODE = 1; // 验证码

    public static final int TYPE_OA_MESSAGE = 2;  // OA消息

    public static final int TYPE_PRODUCT_MESSAGE = 3;  // 产品消息

    public static final int TYPE_CONTRACT_MESSAGE = 4; // 合同消息

    public static final int TYPE_PAYMENTPLAN_MESSAGE = 5; // 兑付消息

    public static final int TYPE_NEW_USER_PASSWORD = 6; // 新用户密码

    public static final int TYPE_OTHER = 100;  // 其他类型
}
