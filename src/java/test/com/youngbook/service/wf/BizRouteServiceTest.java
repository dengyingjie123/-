package com.youngbook.service.wf;

import com.youngbook.common.config.Config;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.sql.Connection;

public class BizRouteServiceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: insertOrUpdate(String id, String ModelsStringId, String controlString1, String controlsString2, int WorkflowId, boolean flag, UserPO user, Connection conn)
     *
     */
    @Test
    public void testInsertOrUpdate() throws Exception {
//TODO: Test goes here...
    }

    /**
     *
     * Method: getModelsStringId(String controlString1, String ModelsStringId, int WorkflowId, boolean flag, Connection conn)
     *
     */
    @Test
    public void testGetModelsStringId() throws Exception {
// getModelsStringId(String controlString1,String ModelsStringId,int WorkflowId,boolean flag, Connection conn ) throws Exception{

        String  controlString1 = "0A3A222B-99E9-4F67-B253-0A8276A779BE";

        String MOdelsStringid ="";

        int WorkflowId = 11;

        boolean flag = true;
        Connection  conn= Config.getConnection();

        String modelStr =   BizRouteService.getModelsStringId(controlString1,MOdelsStringid,WorkflowId,flag,conn);

        MOdelsStringid += modelStr;
        flag=false;
        String modelStr2= BizRouteService.getModelsStringId(controlString1,MOdelsStringid,WorkflowId,flag,conn);
        System.out.println(modelStr);
        System.out.println(modelStr2);
        System.out.println(modelStr.equals(modelStr2));
    }
}