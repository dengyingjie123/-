package com.youngbook.common.wf.common;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 15-3-28
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class ActionType {
    public static final String ACTIVE = "ACTIVE";
    public static final int ActiveNO = 1;

    public static final String WAIT = "WAIT";
    public static final int WaitNO = 2;

    public static final String CANCEL = "CANCEL";
    public static final int CancelNO = 3;

    public static final String FINISH = "FINISH";
    public static final int FinishNO = 4;

    public static final String OVER = "OVER";
    public static final int OverNO = 5;

    public static final String UNKNOWN = "UNKNOWN";
    public static final int UnknownNO = 0;

    public static String getFromNO(int typeNO) {

        String type = ActionType.UNKNOWN;
        switch (typeNO) {
            case 1: type = ActionType.ACTIVE;
                break;
            case 2:type = ActionType.WAIT;
                break;
            case 3:type = ActionType.CANCEL;
                break;
            case 4:type = ActionType.FINISH;
                break;
            case 5:type = ActionType.OVER;
                break;
            default: type = ActionType.UNKNOWN;
                break;
        }

        return type;
    }
}
