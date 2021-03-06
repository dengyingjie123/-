package com.youngbook.entity.vo.oa.expense;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created by 邓超
 * Date 2015-5-19
 */


@Table(name = "OA_FinanceBizTripExpenseWFA", jsonPrefix = "financeBizTripExpenseWFA")
public class FinanceBizTripExpenseWFAVO extends BaseVO {

    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    // 组织编号
    private String orgId = new String();
    //部门组织名称
    private String orgName = new String();
    // 出差人编号    使用，隔开
    private String userId = new String();
    //出差人名称使用，隔开
    private String userNames = new String();

    // 事由
    private String comment = new String();


    // 日期
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // 金额
    private double money = Double.MAX_VALUE;


    // 报销人编号
    private String reimburseId = new String();
    //报销人名称
    private String reimburseName = new String();
    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //状态
    private int state = Integer.MAX_VALUE;

    // 报销人时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String reimburseTime = new String();
    /**
     * 2015-6-17 周海鸿 添加了查询需要的元素，业务查询需要的元素
     */
    // 执行董事审核意见
    private String executiveDirectorContent = new String();

    // 分管领导审核意见
    private String chargeLeaderContent = new String();

    // 部门负责人审核意见
    private String departmentLeaderContent = new String();

    // 财务总监审核意见
    private String financeDirectorContent = new String();

    // 会计审核内容
    private String accountingContent = new String();
    //出纳意见
    private String cashierContent = new String();

    // 所属公司总经理意见
    private String generalManagerContent = new String();

    //用于控制业务流程
    private String currentNodeId = new String();

    //用户获取节点标题
    private String currentNodeTitle = new String();

    private String routeListId = new String();
    private String currentStatus = new String();
    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();


    // 会计名称 : 支持查询
    private String accountingName = new String();

    // 财务总监名称 : 支持查询
    private String financeDirectorName = new String();

    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();

    // 出纳名称 : 支持查询
    private String cashierName = new String();

    //附件张数
    private int accessoryNumber  = Integer.MAX_VALUE;

   /* *
            * 修改：周海鸿
    * 时间：2015-7-17
            * 内容：添加数据字段*/

    // 部门负责人审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 所属总经理审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();


    // 会计时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String accountingTime = new String();

    // 财务总监审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDirectorTime = new String();

    // 分管领导审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String chargeLeaderTime = new String();

    // 执行董事审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String executiveDirectorTime = new String();


    // 出纳时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String cashierTime = new String();
    // 编号
    private String id1 = new String();

    // 名称 : 支持查询
    private String name1 = new String();

    // 意见 : 支持查询
    private String content1  = new String();

    // 时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time1  = new String();

    // 状态
    private int status1 = Integer.MAX_VALUE;


    // 编号
    private String id2 = new String();

    // 名称 : 支持查询
    private String name2 = new String();

    // 意见 : 支持查询
    private String content2  = new String();

    // 时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time2  = new String();

    // 状态
    private int status2 = Integer.MAX_VALUE;

    // 编号3
    private String id3 = new String();

    // 名称 : 支持查询
    private String name3 = new String();

    // 意见 : 支持查询
    private String content3  = new String();

    // 时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time3  = new String();

    // 状态
    private int status3 = Integer.MAX_VALUE;


    // 编号4
    private String id4 = new String();

    // 名称 : 支持查询
    private String name4 = new String();

    // 意见 : 支持查询
    private String content4  = new String();

    // 时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time4  = new String();

    // 状态
    private int status4 = Integer.MAX_VALUE;

    // 编号5
    private String id5 = new String();

    // 名称 : 支持查询
    private String name5 = new String();

    // 意见 : 支持查询
    private String content5  = new String();

    // 时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time5  = new String();

    // 状态
    private int status5 = Integer.MAX_VALUE;

       /*
    * 作者：周海鸿
    * 时间：2015-7-31
    * 内容：添加公司部门的字段属性*/


    //公司
    private String controlString1 = new String();
    //部门
    private String controlString2 = new String();
    //公司编号
    private String controlString1Id = new String();
    //部门编号
    private String controlString2Id = new String();
    //getset



    //唯一标识
    private String controlString3 = new String();

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
    }

    //getset
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }



    public String getReimburseId() {
        return reimburseId;
    }

    public void setReimburseId(String reimburseId) {
        this.reimburseId = reimburseId;
    }

    public String getReimburseTime() {
        return reimburseTime;
    }

    public void setReimburseTime(String reimburseTime) {
        this.reimburseTime = reimburseTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getReimburseName() {
        return reimburseName;
    }

    public void setReimburseName(String reimburseName) {
        this.reimburseName = reimburseName;
    }

    public String getExecutiveDirectorContent() {
        return executiveDirectorContent;
    }

    public void setExecutiveDirectorContent(String executiveDirectorContent) {
        this.executiveDirectorContent = executiveDirectorContent;
    }

    public String getChargeLeaderContent() {
        return chargeLeaderContent;
    }

    public void setChargeLeaderContent(String chargeLeaderContent) {
        this.chargeLeaderContent = chargeLeaderContent;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getFinanceDirectorContent() {
        return financeDirectorContent;
    }

    public void setFinanceDirectorContent(String financeDirectorContent) {
        this.financeDirectorContent = financeDirectorContent;
    }

    public String getAccountingContent() {
        return accountingContent;
    }

    public void setAccountingContent(String accountingContent) {
        this.accountingContent = accountingContent;
    }

    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public String getRouteListId() {
        return routeListId;
    }

    public void setRouteListId(String routeListId) {
        this.routeListId = routeListId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    public String getCashierContent() {
        return cashierContent;
    }

    public void setCashierContent(String cashierContent) {
        this.cashierContent = cashierContent;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
    }

    public String getGeneralManagerName() {
        return generalManagerName;
    }

    public void setGeneralManagerName(String generalManagerName) {
        this.generalManagerName = generalManagerName;
    }

    public String getAccountingName() {
        return accountingName;
    }

    public void setAccountingName(String accountingName) {
        this.accountingName = accountingName;
    }

    public String getFinanceDirectorName() {
        return financeDirectorName;
    }

    public void setFinanceDirectorName(String financeDirectorName) {
        this.financeDirectorName = financeDirectorName;
    }

    public String getChargeLeaderName() {
        return chargeLeaderName;
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName;
    }

    public String getExecutiveDirectorName() {
        return executiveDirectorName;
    }

    public void setExecutiveDirectorName(String executiveDirectorName) {
        this.executiveDirectorName = executiveDirectorName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public String getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(String accountingTime) {
        this.accountingTime = accountingTime;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public String getExecutiveDirectorTime() {
        return executiveDirectorTime;
    }

    public void setExecutiveDirectorTime(String executiveDirectorTime) {
        this.executiveDirectorTime = executiveDirectorTime;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public int getStatus3() {
        return status3;
    }

    public void setStatus3(int status3) {
        this.status3 = status3;
    }

    public String getId4() {
        return id4;
    }

    public void setId4(String id4) {
        this.id4 = id4;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public int getStatus4() {
        return status4;
    }

    public void setStatus4(int status4) {
        this.status4 = status4;
    }

    public String getId5() {
        return id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    public String getName5() {
        return name5;
    }

    public void setName5(String name5) {
        this.name5 = name5;
    }

    public String getContent5() {
        return content5;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public String getTime5() {
        return time5;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public int getStatus5() {
        return status5;
    }

    public void setStatus5(int status5) {
        this.status5 = status5;
    }

    public String getControlString1() {
        return controlString1;
    }

    public void setControlString1(String controlString1) {
        this.controlString1 = controlString1;
    }

    public String getControlString2() {
        return controlString2;
    }

    public void setControlString2(String controlString2) {
        this.controlString2 = controlString2;
    }

    public String getControlString1Id() {
        return controlString1Id;
    }

    public void setControlString1Id(String controlString1Id) {
        this.controlString1Id = controlString1Id;
    }

    public String getControlString2Id() {
        return controlString2Id;
    }

    public void setControlString2Id(String controlString2Id) {
        this.controlString2Id = controlString2Id;
    }

    public int getAccessoryNumber() {
        return accessoryNumber;
    }

    public void setAccessoryNumber(int accessoryNumber) {
        this.accessoryNumber = accessoryNumber;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
}
