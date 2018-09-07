package com.youngbook.common.config;

/**
 * Created by haihong on 2015/7/6.
 * 日期：2015年8月3日 19:02:19
 * @author 周海鸿
 */

import com.youngbook.common.Database;

/**
 * 该类主要用来配置记录各个oa的业务流编号
 */
public class BizRouteConfig {
    //资金支付业务编号
    public static final int WORKFLOWID_Finance_FinancePayWFA = 6;
    //用章管理
    public static final int WORKFLOWID_Administation_SealUsageWFA = 4;
    //费用管理
    public static final int WORKFLOWID_Finance_FinanceExpende = 7;
    //差旅费报销
    public static final int WORKDLOWID_Expense_FinanceBizTripExpenseWFA = 8;

    //固定资产申请
    public static final int WORKFLOWID_AssetFixation_AsserApplication = 9;
    //对外资料报送
    public static final int WORKFLOWID_Information_Information = 10;
    //请假休假业务编号
    public static final int WORKFLOWID_HR_Leave = 11;
    //出差审批
    public static final int WORKFLOWID_Business_Business=12;
    //产品
    public static final int WORKFLOWID_Product_Check=13;
    //兑付计划
    public static final int WORKFLOWID_PaymentPlam_Check=14;


    /*
     * 作者：周海鸿
     * 时间:2015-7-30
     *内容:
     * 根据用户编获取用户所有部门的公司编号
     * */
    public static final String getDepartmentParentId(String id) {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  select id from system_department where  id in ");
        sbSQL.append(" (");
        sbSQL.append(" SELECT");
        sbSQL.append("	department.parentId");
        sbSQL.append(" FROM");
        sbSQL.append("	system_positionuser Puser,");
        sbSQL.append("	system_department department,");
        sbSQL.append("	system_position positon");
        sbSQL.append(" WHERE");
        sbSQL.append("	1=1");
        sbSQL.append(" and Puser.positionId = positon.Id");
        sbSQL.append(" and positon.DepartmentId = department.ID");
        sbSQL.append(" and department.id!='"+Config.DefaultDepartment+"'");
        sbSQL.append(" and Puser.userId='"+ Database.encodeSQL(id)+"') ");

        return sbSQL.toString();
    }

    /*
    * 作者：周海鸿
    * 时间：2015-7-30
    * 内容：
    * 获取并凑字符串时BizRot中的固定的SQL语句
    * */
    public static final  String getBizRouteStr() {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("ob.DepartmentLeaderId,");
        sbSQL.append("ob.DepartmentLeaderName,");
        sbSQL.append("ob.DepartmentLeaderContent,");
        sbSQL.append("ob.DepartmentLeaderTime,");
        sbSQL.append("ob.DepartmentLeaderStatus,");
        sbSQL.append("ob.GeneralManagerId,");
        sbSQL.append("ob.GeneralManagerName,");
        sbSQL.append("ob.GeneralManagerContent,");
        sbSQL.append("ob.GeneralManagerTime,");
        sbSQL.append("ob.GeneralManagerStatus,");
        sbSQL.append("ob.AccountingId,");
        sbSQL.append("ob.AccountingName,");
        sbSQL.append("ob.AccountingContent,");
        sbSQL.append("ob.AccountingTime,");
        sbSQL.append("ob.AccountingStatus,");
        sbSQL.append("ob.FinanceDirectorId,");
        sbSQL.append("ob.FinanceDirectorName,");
        sbSQL.append("ob.FinanceDirectorContent,");
        sbSQL.append("ob.FinanceDirectorTime,");
        sbSQL.append("ob.FinanceDirectorStatus,");
        sbSQL.append("ob.ChargeLeaderId,");
        sbSQL.append("ob.ChargeLeaderName,");
        sbSQL.append("ob.ChargeLeaderContent,");
        sbSQL.append("ob.ChargeLeaderTime,");
        sbSQL.append("ob.ChargeLeaderStatus,");
        sbSQL.append("ob.ExecutiveDirectorId,");
        sbSQL.append("ob.ExecutiveDirectorName,");
        sbSQL.append("ob.ExecutiveDirectorContent,");
        sbSQL.append("ob.ExecutiveDirectorTime,");
        sbSQL.append("ob.ExecutiveDirectorStatus,");
        sbSQL.append("ob.CashierId,");
        sbSQL.append("ob.CashierName,");
        sbSQL.append("ob.CashierContent,");
        sbSQL.append("ob.CashierTime,");
        sbSQL.append("ob.CashierStatus,");
        sbSQL.append("ob.Id5,");
        sbSQL.append("ob.Name5,");
        sbSQL.append("ob.Content5,");
        sbSQL.append("ob.Time5,");
        sbSQL.append("ob.Status5,");
        sbSQL.append("ob.Id4,");
        sbSQL.append("ob.Name4,");
        sbSQL.append("ob.Content4,");
        sbSQL.append("ob.Time4,");
        sbSQL.append("ob.Status4,");
        sbSQL.append("ob.Id3,");
        sbSQL.append("ob.Name3,");
        sbSQL.append("ob.Content3,");
        sbSQL.append("ob.Time3,");
        sbSQL.append("ob.Status3,");
        sbSQL.append("ob.Id2,");
        sbSQL.append("ob.Name2,");
        sbSQL.append("ob.Content2,");
        sbSQL.append("ob.Time2,");
        sbSQL.append("ob.Status2,");
        sbSQL.append("ob.Id1,");
        sbSQL.append("ob.Name1,");
        sbSQL.append("ob.Content1,");
        sbSQL.append("ob.Time1,");
        sbSQL.append("ob.Status1,");
        sbSQL.append("ob.controlString3,");
        return sbSQL.toString();
    }

    /**
     * 获取并凑字符串时BizRot中的固定的SQL语句 用 '' 标识的列数据
     * @return
     */
    public static final String getBizRouteStrTonull(){
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" '' DepartmentLeaderId,");
        sbSQL.append("'' DepartmentLeaderName,");
        sbSQL.append("'' DepartmentLeaderContent,");
        sbSQL.append("'' DepartmentLeaderTime,");
        sbSQL.append("'' DepartmentLeaderStatus,");
        sbSQL.append("'' GeneralManagerId,");
        sbSQL.append("'' GeneralManagerName,");
        sbSQL.append("'' GeneralManagerContent,");
        sbSQL.append("'' GeneralManagerTime,");
        sbSQL.append("'' GeneralManagerStatus,");
        sbSQL.append("'' AccountingId,");
        sbSQL.append("'' AccountingName,");
        sbSQL.append("'' AccountingContent,");
        sbSQL.append("'' AccountingTime,");
        sbSQL.append("'' AccountingStatus,");
        sbSQL.append("'' FinanceDirectorId,");
        sbSQL.append("'' FinanceDirectorName,");
        sbSQL.append("'' FinanceDirectorContent,");
        sbSQL.append("'' FinanceDirectorTime,");
        sbSQL.append("'' FinanceDirectorStatus,");
        sbSQL.append("'' ChargeLeaderId,");
        sbSQL.append("'' ChargeLeaderName,");
        sbSQL.append("'' ChargeLeaderContent,");
        sbSQL.append("'' ChargeLeaderTime,");
        sbSQL.append("'' ChargeLeaderStatus,");
        sbSQL.append("'' ExecutiveDirectorId,");
        sbSQL.append("'' ExecutiveDirectorName,");
        sbSQL.append("'' ExecutiveDirectorContent,");
        sbSQL.append("'' ExecutiveDirectorTime,");
        sbSQL.append("'' ExecutiveDirectorStatus,");
        sbSQL.append("'' CashierId,");
        sbSQL.append("'' CashierName,");
        sbSQL.append("'' CashierContent,");
        sbSQL.append("'' CashierTime,");
        sbSQL.append("'' CashierStatus,");
        sbSQL.append("'' Id5,");
        sbSQL.append("'' Name5,");
        sbSQL.append("'' Content5,");
        sbSQL.append("'' Time5,");
        sbSQL.append("'' Status5,");
        sbSQL.append("'' Id4,");
        sbSQL.append("'' Name4,");
        sbSQL.append("'' Content4,");
        sbSQL.append("'' Time4,");
        sbSQL.append("'' Status4,");
        sbSQL.append("'' Id3,");
        sbSQL.append("'' Name3,");
        sbSQL.append("'' Content3,");
        sbSQL.append("'' Time3,");
        sbSQL.append("'' Status3,");
        sbSQL.append("'' Id2,");
        sbSQL.append("'' Name2,");
        sbSQL.append("'' Content2,");
        sbSQL.append("'' Time2,");
        sbSQL.append("'' Status2,");
        sbSQL.append("'' Id1,");
        sbSQL.append("'' Name1,");
        sbSQL.append("'' Content1,");
        sbSQL.append("'' Time1,");
        sbSQL.append("'' Status1,");
        sbSQL.append("ob.controlString3,");
        return sbSQL.toString();
    }
}
