package com.youngbook.entity.po.system;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmsStatus {

    // 参考：http://youngbook.net:96/pages/viewpage.action?pageId=22052869
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_SENT = 1;
    public static final int STATUS_SENT_CONFIRM = 2;
    public static final int STATUS_SENT_FAILED = 3;
}
