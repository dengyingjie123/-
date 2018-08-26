package com.youngbook.entity.po.oa.administration;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/14
 * 描述：
 * SealusageItemStatus:
 */
public class SealusageItem2Status {
    //未接收
    public final static String RECEIVEISCONFIRM_NO = "0";
    //已经接收
    public final static String RECEIVEISCONFIRM_OK = "1";

    //未归还
    public final static String OUTBACKISCONFIRM_NO = "0";
    //已经归还
    public final static String OUTBACKISCONFIRM_OK = "1";

    //不需要外带
    public final static int ISOUT_NO = 0;
    //需要外带
    public final static int ISOUT_OK = 1;
    //未全部接收
    public final static String ISALLRECEIVE_NO = "1";
    //已经全部接收
    public final static String ISALLRECEIVE_OK = "0";

    //未全部归还
    public final static String ISALLOUTBACK_NO = "1";
    //全部归还
    public final static String ISALLOUTBACK_OK = "0";
    //不需要外带
    public final static int STATUS_NO = 0;
    //需要外带
    public final static int STATUS_OK = 1;
}
