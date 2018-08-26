package com.youngbook.common.wf.clientapp;

import java.util.List;

/**
 * Created by Lee on 2015/6/3.
 */
public class WFDemo {
    public static void test() throws Exception {
        int intWorkflowID = 4;
        int currentNodeId = 1;
        String fieldName = "CHECKREPLAY";
        int status = ClientApplications.getFieldStatus(intWorkflowID, currentNodeId, fieldName);

        String userId = "5d8c18c7e9b246d89fddebd48ecc2d2a";
        List list = ClientApplications.getOperableNodebyUserID(intWorkflowID, userId, 3);

        System.out.println("WFDeom.test(): " + status);
    }
}
