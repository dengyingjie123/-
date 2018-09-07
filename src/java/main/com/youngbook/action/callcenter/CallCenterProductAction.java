package com.youngbook.action.callcenter;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class CallCenterProductAction extends BaseAction {

    public String getListProject() throws Exception{
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        Pager pager = null;
        String str_sql = "SELECT * FROM crm_project";
        String projectname = "";
        projectname = request.getParameter("projectname");
//        if(projectname.length() > 0){
//            str_sql += " WHERE name like '"+projectname+"' ";
//            pager = Pager.query(str_sql, ProjectVO.class, request);
//        }
//        pager = Pager.query(str_sql,ProjectVO.class,request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String getListProduct() throws Exception{
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        Pager pager = null;
        String str_sql = "SELECT * FROM crm_production";
        String ProjectId = "";
        ProjectId = request.getParameter("ProjectId");
        if(ProjectId.length() > 0){
            str_sql += " WHERE ProjectId = '"+ProjectId+"' ";
        }
       // pager = Pager.query(str_sql, ProductionVO.class, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String getListProductComposition() throws Exception{
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        Pager pager = null;
        String str_sql = "SELECT * FROM crm_productioncomposition";
        String productionid = "";
        productionid = request.getParameter("productionid");
        if(productionid.length() > 0){
            str_sql += " WHERE ProductionId = '"+productionid+"' ";
        }
       // pager = Pager.query(str_sql, ProductioncompositionPO.class, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

}
