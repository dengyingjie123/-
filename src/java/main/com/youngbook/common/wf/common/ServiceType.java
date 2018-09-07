package com.youngbook.common.wf.common;

/**
 * Created by Lee on 2016/10/21.
 */
public class ServiceType {
    public static final int SAVE_ONLY = 1;
    public static final int SAVE_FORWARD = 2;
    public static final int AUTO_FORWARD = 3;
    public static final int CANCEL = 4;
    public static final int OVER = 5;
    public static final int REJECT = 6;


    public static final String SAVE_ONLY_STRING = "SAVEONLY";
    public static final String REJECT_STRING = "REJECT";
    public static final String AUTO_FORWARD_STRING = "AUTOFORWARD";
    public static final String OVER_STRING = "OVER";
    public static final String CANCEL_STRING = "CANCEL";
    public static final String SAVE_FORWARD_STRING = "SAVEFORWARD";
}
