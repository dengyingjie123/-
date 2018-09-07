package com.youngbook.entity.po.wf;

import com.youngbook.annotation.*;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.common.WorkflowDao;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.common.wf.services.IBizService;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.BasePO;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by haihong on 2015/6/12.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */

@Table(name = "OA_BizRoute", jsonPrefix = "bizRoute")
public class BizRoutePO extends BasePO implements IBizDao {
// 业务编号 : 支持查询
    @Id
    private String id_ywid = new String();

    // 工作流编号
    private int workflowId = Integer.MAX_VALUE;

    // 申请人编号
    private String applicantId = new String();

    // 申请人名称 : 支持查询
    private String applicantName = new String();

    // 申请备注 : 支持查询
    private String applicantComment = new String();

    // 申请日期 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicantTime = new String();

    // 经手人编号
    private String submitterId = new String();

    // 经手人名称 : 支持查询
    private String submitterName = new String();

    // 经手人时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String submitterTime = new String();

    // 部门负责人编号
    private String departmentLeaderId = new String();

    // 部门负责人名称 : 支持查询
    private String departmentLeaderName = new String();

    // 部门负责人审核内容 : 支持查询
    private String departmentLeaderContent = new String();

    // 部门负责人审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 部门负责人审核状态
    private int departmentLeaderStatus = Integer.MAX_VALUE;

    // 所属总经理编号
    private String generalManagerId = new String();

    // 所属总经理名称 : 支持查询
    private String generalManagerName = new String();

    // 所属总经理意见 : 支持查询
    private String generalManagerContent = new String();

    // 所属总经理审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String generalManagerTime = new String();

    // 所属总经理状态
    private int generalManagerStatus = Integer.MAX_VALUE;

    // 会计编号
    private String accountingId = new String();

    // 会计名称 : 支持查询
    private String accountingName = new String();

    // 会计内容 : 支持查询
    private String accountingContent = new String();

    // 会计时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String accountingTime = new String();

    // 会计状态
    private int accountingStatus = Integer.MAX_VALUE;

    // 财务总监编号
    private String financeDirectorId = new String();

    // 财务总监名称 : 支持查询
    private String financeDirectorName = new String();

    // 财务总监审核内容 : 支持查询
    private String financeDirectorContent = new String();

    // 财务总监审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDirectorTime = new String();

    // 财务总监审核状态
    private int financeDirectorStatus = Integer.MAX_VALUE;

    // 分管领导编号
    private String chargeLeaderId = new String();

    // 分管领导名称 : 支持查询
    private String chargeLeaderName = new String();

    // 分管领导审核内容 : 支持查询
    private String chargeLeaderContent = new String();

    // 分管领导审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String chargeLeaderTime = new String();

    // 分管领导审核状态
    private int chargeLeaderStatus = Integer.MAX_VALUE;

    // 执行懂事编号
    private String executiveDirectorId = new String();

    // 执行董事名称 : 支持查询
    private String executiveDirectorName = new String();

    // 执行董事审核内容 : 支持查询
    private String executiveDirectorContent = new String();

    // 执行董事审核时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String executiveDirectorTime = new String();

    // 执行董事审核状态
    private int executiveDirectorStatus = Integer.MAX_VALUE;

    // 出纳编号
    private String cashierId = new String();

    // 出纳名称 : 支持查询
    private String cashierName = new String();

    // 出纳意见 : 支持查询
    private String cashierContent = new String();

    // 出纳时间 : 时间段查询,时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String cashierTime = new String();

    // 出纳状态
    private int cashierStatus = Integer.MAX_VALUE;

    // 控制整型1
    private int controlInt1 = Integer.MAX_VALUE;

    // 控制整型2
    private int controlInt2 = Integer.MAX_VALUE;

    // 控制整型3
    private int controlInt3 = Integer.MAX_VALUE;

    // 控制浮点1
    private double controlDouble1 = Double.MAX_VALUE;

    // 控制浮点2
    private double controlDouble2 = Double.MAX_VALUE;

    // 控制浮点3
    private double controlDouble3 = Double.MAX_VALUE;

    // 公司名称
    private String controlString1 = new String();

    // 部门名称
    private String controlString2 = new String();

    // 唯一标识
    private String controlString3 = new String();

    // 控制资金1
    private double controlMoney1 = Double.MAX_VALUE;

    // 控制资金2
    private double controlMoney2 = Double.MAX_VALUE;

    // 控制资金3
    private double controlMoney3 = Double.MAX_VALUE;

    // 控制时间1
    @DataAdapter(fieldType = FieldType.DATE)
    private String controlTime1 = new String();

    // 控制时间2
    @DataAdapter(fieldType = FieldType.DATE)
    private String controlTime2 = new String();

    // 控制时间3
    @DataAdapter(fieldType = FieldType.DATE)
    private String controlTime3 = new String();


    /**
     * 修改人周海鸿
     * 修改时间2015-6-30
     * 修改事件 添加业务特许属性
     * */


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

    @IgnoreDB
    @IgnoreJson
    @EnumType(id = "serviceClassName", display = "serviceClassName", value = "")
    private String  serviceClassName  = new String();

    //interface 接口

    @Override
    public int insert(Connection conn) throws Exception {
            if(StringUtils.isEmpty(this.getYWID())){
               return MySQLDao.insert(this, conn);
            }
        return 1;
    }

    @Override
    public int update(Connection conn) throws Exception {
        return MySQLDao.update(this, conn);
    }

    @Override
    public int delete(Connection conn) throws Exception {
        return MySQLDao.deletePhysically(this, conn);
    }

    @Override
    public String getYWID() {
        return this.id_ywid;
    }

    @Override
    public void setYWID(String YWID) throws Exception {

    }

    /**
     *
     * 判断是否满足进入条件
     * @param Condition 条件字串，如：abc='a' and che>100
     * @param conn
     * @return
     * @throws Exception
     */
    @Override
    public boolean satisfyForwardCondition(String Condition, Connection conn) throws Exception {
       /* StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT * FROM oa_bizroute where 1=1 ");
        if (!StringUtils.isEmpty(this.getYWID())) {
            sbSQL.append("and id_ywid='").append(this.getYWID()).append("'");
        }

        if (!StringUtils.isEmpty(Condition)) {
            sbSQL.append(" and ").append(Condition);
        }*/
        if(StringUtils.isEmpty(Condition.trim())){
            return true;
        }
        Condition = Condition.replaceAll("\\{YWID\\}", "'" + this.getYWID() + "'");
        List list = MySQLDao.query(Condition, conn);
        return list.size() > 0;
    }

    @Override
    public HashMap dataSnapShot(Connection conn) throws Exception {

        return WorkflowDao.dataSnapShot(this, conn);
    }

    /**
     * 说明：根据传入的RouteList 来操作当业务结束的时候
     * 需要处理的一些业务上的操作。
     *
     * @param routeList 流转记录对象
     * @param wodlkflow   工作流数据
     * @param conn      数据库连接
     * @return
     * @throws Exception
     */
    @Override
    public int afterOver(RouteList routeList,Action wodlkflow, Connection conn) throws Exception {
        //获取className
        String ServiceClassName= this.getServiceClassName();

        //判断是否有值
        if(!StringUtils.isEmpty(ServiceClassName)){
            //根据className 获取指定的类实例
            Class servlesClass = Class.forName(getServiceClassName());
            IBizService service = (IBizService) servlesClass.newInstance();
            service.afterOver(this,routeList, wodlkflow, conn);
        }
        return 0;
    }

    /**
     * 说明：设置再afterOver中调用的service 的CLass 的全名称
     *
     * @param className
     * @return
     */
    @Override
    public void setServiceClassName(String className) {
            this.serviceClassName = className;
    }

    /**
     * 获取设置的service 的class 的全名称
     *
     * @return
     */
    @Override
    public String getServiceClassName() {
        return this.serviceClassName;
    }


    //getset




    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantComment() {
        return applicantComment;
    }

    public void setApplicantComment(String applicantComment) {
        this.applicantComment = applicantComment;
    }

    public String getApplicantTime() {
        return applicantTime;
    }

    public void setApplicantTime(String applicantTime) {
        this.applicantTime = applicantTime;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterTime() {
        return submitterTime;
    }

    public void setSubmitterTime(String submitterTime) {
        this.submitterTime = submitterTime;
    }

    public String getExecutiveDirectorContent() {
        return executiveDirectorContent;
    }

    public void setExecutiveDirectorContent(String executiveDirectorContent) {
        this.executiveDirectorContent = executiveDirectorContent;
    }

    public String getExecutiveDirectorId() {
        return executiveDirectorId;
    }

    public void setExecutiveDirectorId(String executiveDirectorId) {
        this.executiveDirectorId = executiveDirectorId;
    }

    public String getExecutiveDirectorTime() {
        return executiveDirectorTime;
    }

    public void setExecutiveDirectorTime(String executiveDirectorTime) {
        this.executiveDirectorTime = executiveDirectorTime;
    }

    public int getExecutiveDirectorStatus() {
        return executiveDirectorStatus;
    }

    public void setExecutiveDirectorStatus(int executiveDirectorStatus) {
        this.executiveDirectorStatus = executiveDirectorStatus;
    }

    public String getChargeLeaderContent() {
        return chargeLeaderContent;
    }

    public void setChargeLeaderContent(String chargeLeaderContent) {
        this.chargeLeaderContent = chargeLeaderContent;
    }

    public String getChargeLeaderId() {
        return chargeLeaderId;
    }

    public void setChargeLeaderId(String chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId;
    }

    public String getChargeLeaderTime() {
        return chargeLeaderTime;
    }

    public void setChargeLeaderTime(String chargeLeaderTime) {
        this.chargeLeaderTime = chargeLeaderTime;
    }

    public int getChargeLeaderStatus() {
        return chargeLeaderStatus;
    }

    public void setChargeLeaderStatus(int chargeLeaderStatus) {
        this.chargeLeaderStatus = chargeLeaderStatus;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public int getDepartmentLeaderStatus() {
        return departmentLeaderStatus;
    }

    public void setDepartmentLeaderStatus(int departmentLeaderStatus) {
        this.departmentLeaderStatus = departmentLeaderStatus;
    }

    public String getFinanceDirectorContent() {
        return financeDirectorContent;
    }

    public void setFinanceDirectorContent(String financeDirectorContent) {
        this.financeDirectorContent = financeDirectorContent;
    }

    public String getFinanceDirectorId() {
        return financeDirectorId;
    }

    public void setFinanceDirectorId(String financeDirectorId) {
        this.financeDirectorId = financeDirectorId;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public int getFinanceDirectorStatus() {
        return financeDirectorStatus;
    }

    public void setFinanceDirectorStatus(int financeDirectorStatus) {
        this.financeDirectorStatus = financeDirectorStatus;
    }

    public String getAccountingContent() {
        return accountingContent;
    }

    public void setAccountingContent(String accountingContent) {
        this.accountingContent = accountingContent;
    }

    public String getAccountingId() {
        return accountingId;
    }

    public void setAccountingId(String accountingId) {
        this.accountingId = accountingId;
    }

    public String getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(String accountingTime) {
        this.accountingTime = accountingTime;
    }

    public int getAccountingStatus() {
        return accountingStatus;
    }

    public void setAccountingStatus(int accountingStatus) {
        this.accountingStatus = accountingStatus;
    }

    public String getGeneralManagerId() {
        return generalManagerId;
    }

    public void setGeneralManagerId(String generalManagerId) {
        this.generalManagerId = generalManagerId;
    }

    public String getGeneralManagerTime() {
        return generalManagerTime;
    }

    public void setGeneralManagerTime(String generalManagerTime) {
        this.generalManagerTime = generalManagerTime;
    }

    public String getGeneralManagerContent() {
        return generalManagerContent;
    }

    public void setGeneralManagerContent(String generalManagerContent) {
        this.generalManagerContent = generalManagerContent;
    }

    public int getGeneralManagerStatus() {
        return generalManagerStatus;
    }

    public void setGeneralManagerStatus(int generalManagerStatus) {
        this.generalManagerStatus = generalManagerStatus;
    }

    public String getId_ywid() {
        return id_ywid;
    }

    public void setId_ywid(String id_ywid) {
        this.id_ywid = id_ywid;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
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

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierContent() {
        return cashierContent;
    }

    public void setCashierContent(String cashierContent) {
        this.cashierContent = cashierContent;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public int getCashierStatus() {
        return cashierStatus;
    }

    public void setCashierStatus(int cashierStatus) {
        this.cashierStatus = cashierStatus;
    }

    public int getControlInt1() {
        return controlInt1;
    }

    public void setControlInt1(int controlInt1) {
        this.controlInt1 = controlInt1;
    }

    public int getControlInt2() {
        return controlInt2;
    }

    public void setControlInt2(int controlInt2) {
        this.controlInt2 = controlInt2;
    }

    public int getControlInt3() {
        return controlInt3;
    }

    public void setControlInt3(int controlInt3) {
        this.controlInt3 = controlInt3;
    }

    public double getControlDouble1() {
        return controlDouble1;
    }

    public void setControlDouble1(double controlDouble1) {
        this.controlDouble1 = controlDouble1;
    }

    public double getControlDouble2() {
        return controlDouble2;
    }

    public void setControlDouble2(double controlDouble2) {
        this.controlDouble2 = controlDouble2;
    }

    public double getControlDouble3() {
        return controlDouble3;
    }

    public void setControlDouble3(double controlDouble3) {
        this.controlDouble3 = controlDouble3;
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

    public String getControlString3() {
        return controlString3;
    }

    public void setControlString3(String controlString3) {
        this.controlString3 = controlString3;
    }

    public double getControlMoney1() {
        return controlMoney1;
    }

    public void setControlMoney1(double controlMoney1) {
        this.controlMoney1 = controlMoney1;
    }

    public double getControlMoney2() {
        return controlMoney2;
    }

    public void setControlMoney2(double controlMoney2) {
        this.controlMoney2 = controlMoney2;
    }

    public double getControlMoney3() {
        return controlMoney3;
    }

    public void setControlMoney3(double controlMoney3) {
        this.controlMoney3 = controlMoney3;
    }

    public String getControlTime1() {
        return controlTime1;
    }

    public void setControlTime1(String controlTime1) {
        this.controlTime1 = controlTime1;
    }

    public String getControlTime2() {
        return controlTime2;
    }

    public void setControlTime2(String controlTime2) {
        this.controlTime2 = controlTime2;
    }

    public String getControlTime3() {
        return controlTime3;
    }

    public void setControlTime3(String controlTime3) {
        this.controlTime3 = controlTime3;
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


}
