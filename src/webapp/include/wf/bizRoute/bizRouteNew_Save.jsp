<%--
  Created by IntelliJ IDEA.
  User: 小周
  Date: 2015/6/12
  Time: 15:41
  实现通用业务控制界面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.oa.administration.SealUsageWFAPO" %>
<%@ page import="com.youngbook.dao.MySQLDao" %>
<%@ page import="com.youngbook.common.wf.clientapp.ClientApplications" %>
<%@ page import="com.youngbook.common.wf.processdefine.Node" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.wf.processdefine.BizData" %>
<%
    String token = request.getParameter("token");
    int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));  //获得工作流编号
    int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
    int intRouteListID = Integer.parseInt(request.getParameter("RouteListID"));  //获得RouteList编号
    String strYWID = request.getParameter("YWID") == null ? "" : request.getParameter("YWID");  //获得业务编号
    SealUsageWFAPO biz = new SealUsageWFAPO();

    if (strYWID != null && !strYWID.equals("")) {
        biz.setId(strYWID);
        biz = MySQLDao.load(biz, SealUsageWFAPO.class);
    }

    String actioinUrl = "/core/wf/Workflow_service.action";
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:2px;background:#E0E0E0;border:1px solid #E0E0E0;">
    <div style=" height: 30px;font-size: 15px" align="center">审批意见</div>
    <form id="formBizRoute<%=token %>" name="formBizRoute" action="" method="post">
        <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">

            <table width="500" border="0   " cellspacing="5" cellpadding="0">
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "departmentLeaderContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "departmentLeaderContent")%>"
                        width="100px;">
                        部门负责人意见
                    </td>
                    <td colspan="3"><textarea type="text" id="departmentLeaderContent<%=token %>"
                                              name="bizRoute.departmentLeaderContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "departmentLeaderContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "departmentLeaderContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "departmentLeaderContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="departmentLeaderName<%=token %>" name="bizRoute.departmentLeaderName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "departmentLeaderContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="departmentLeaderTime<%=token %>"
                               name="bizRoute.departmentLeaderTime" style="width:140px"/></td>
                </tr>

                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  == 2 ? "none" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        会计意见
                    </td>
                    <td colspan="3"><textarea type="text" id="accountingContent<%=token %>"
                                              name="bizRoute.accountingContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  ==  2 ? "none" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="accountingName<%=token %>" name="bizRoute.accountingName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="accountingTime<%=token %>" name="bizRoute.accountingTime"
                               style="width:140px"/></td>
                </tr>






                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  == 2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        会计审核
                    </td>
                    <td colspan="3"><textarea type="text" id="accountingContent<%=token %>"
                                              name="bizRoute.accountingContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "accountingContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="accountingName<%=token %>" name="bizRoute.accountingName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "accountingContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="accountingTime<%=token %>" name="bizRoute.accountingTime"
                               style="width:140px"/></td>

                </tr>




                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "financeDirectorContent")  == 2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "financeDirectorContent")%>">
                        财务总监意见
                    </td>
                    <td colspan="3"><textarea type="text" id="financeDirectorContent<%=token %>"
                                              name="bizRoute.financeDirectorContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "financeDirectorContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "financeDirectorContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "financeDirectorContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="financeDirectorName<%=token %>" name="bizRoute.financeDirectorName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "financeDirectorContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="financeDirectorTime<%=token %>" name="bizRoute.financeDirectorTime"
                               style="width:140px"/></td>

                </tr>

                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "generalManagerContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "generalManagerContent")%>">
                        副总裁意见
                    </td>
                    <td colspan="3"><textarea type="text" id="generalManagerContent<%=token %>"
                                              name="bizRoute.generalManagerContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "generalManagerContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "generalManagerContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "generalManagerContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="generalManagerName<%=token %>" name="bizRoute.generalManagerName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "generalManagerContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="generalManagerTime<%=token %>" name="bizRoute.generalManagerTime"
                               style="width:140px"/></td>

                </tr>

                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "executiveDirectorContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "executiveDirectorContent")%>">
                        执行董事意见
                    </td>
                    <td colspan="3"><textarea type="text" id="executiveDirectorContent<%=token %>"
                                              name="bizRoute.executiveDirectorContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "executiveDirectorContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "executiveDirectorContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "executiveDirectorContent")%>">
                        姓名
                    </td>
                    <td><input type="text" id="executiveDirectorName<%=token %>" name="bizRoute.executiveDirectorName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "executiveDirectorContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="executiveDirectorTime<%=token %>"
                               name="bizRoute.executiveDirectorTime" style="width:140px"/></td>

                </tr>

                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "cashierContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "cashierContent")%>">
                        出纳意见
                    </td>
                    <td colspan="3"><textarea type="text" id="cashierContent<%=token %>"
                                              name="bizRoute.cashierContent"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "cashierContent")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "cashierContent")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "cashierContent")%>">姓名
                    </td>
                    <td><input type="text" id="cashierName<%=token %>" name="bizRoute.cashierName"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "cashierContent")%>">
                        时间
                    </td>
                    <td><input type="text" id="cashierTime<%=token %>"
                               name="bizRoute.cashierTime" style="width:140px"/></td>

                </tr>
                <%
                    /**
                     * 修改人周海鸿
                     * 修改时间：2015-6-30
                     * 修改事情 添加特殊审批 实现根据配置实现显示名称
                     *
                     */
                %>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content1")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content1")%>">
                        <%=BizData.getFieldTitle(intWorkflowID, "NAME1")%>
                    </td>
                    <td colspan="3"><textarea type="text" id="content1<%=token %>"
                                              name="bizRoute.content1"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content1")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content1")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content1")%>">姓名
                    </td>
                    <td><input type="text" id="name1<%=token %>" name="bizRoute.name1"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content1")%>">
                        时间
                    </td>
                    <td><input type="text" id="time1<%=token %>"
                               name="bizRoute.time1" style="width:140px"/></td>

                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content2")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content2")%>">
                        <%=BizData.getFieldTitle(intWorkflowID, "NAME2")%>
                    </td>
                    <td colspan="3"><textarea type="text" id="content2<%=token %>"
                                              name="bizRoute.content2"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content2")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content2")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content2")%>">姓名
                    </td>
                    <td><input type="text" id="name2<%=token %>" name="bizRoute.name2"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content2")%>">
                        时间
                    </td>
                    <td><input type="text" id="time2<%=token %>"
                               name="bizRoute.time2" style="width:140px"/></td>

                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content3")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content3")%>">
                        <%=BizData.getFieldTitle(intWorkflowID, "NAME3")%>
                    </td>
                    <td colspan="3"><textarea type="text" id="content3<%=token %>"
                                              name="bizRoute.content3"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content3")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content3")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content3")%>">姓名
                    </td>
                    <td><input type="text" id="name3<%=token %>" name="bizRoute.name3"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content3")%>">
                        时间
                    </td>
                    <td><input type="text" id="time3<%=token %>"
                               name="bizRoute.time3" style="width:140px"/></td>

                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content4")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content4")%>">
                        <%=BizData.getFieldTitle(intWorkflowID, "NAME4")%>
                    </td>
                    <td colspan="3"><textarea type="text" id="content4<%=token %>"
                                              name="bizRoute.content4"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content4")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content4")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content4")%>">姓名
                    </td>
                    <td><input type="text" id="name4<%=token %>" name="bizRoute.name4"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content4")%>">
                        时间
                    </td>
                    <td><input type="text" id="time4<%=token %>"
                               name="bizRoute.time4" style="width:140px"/></td>

                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content5")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content5")%>">
                        <%=BizData.getFieldTitle(intWorkflowID, "NAME5")%>
                    </td>
                    <td colspan="3"><textarea type="text" id="content5<%=token %>"
                                              name="bizRoute.content5"
                                              style="resize:none;width:350px;height: 40px"
                                              wf="<%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content5")  == 0 ? "0" : "1"%>"/>
                    </td>
                </tr>
                <tr style="display: <%=ClientApplications.getFieldStatus(intWorkflowID, intCurrNodeID, "content5")  ==  2 ? "none;" : ""%>">
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content5")%>">姓名
                    </td>
                    <td><input type="text" id="name5<%=token %>" name="bizRoute.name5"
                               style="width:140px"/></td>
                    <td align="right"
                        style="<%=ClientApplications.getFieldCSS(intWorkflowID, intCurrNodeID, "content5")%>">
                        时间
                    </td>
                    <td><input type="text" id="time5<%=token %>"
                               name="bizRoute.time5" style="width:140px"/></td>

                </tr>

            </table>
            <%--  <table width="100%" border="0" cellspacing="5" cellpadding="0">

                  <tr>
                      <td align="right" valign="top">发送至</td>
                      <td colspan="4">
                          <select name="NextNode" size="3" multiple id="NextNode<%=token %>"
                                  style="width:350px;height:100px;">
                              <%
                                  List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID);
                                  Iterator itNode = listNode.iterator();
                                  while (itNode.hasNext()) {
                                      Node node = (Node) itNode.next();
                                      String strNodeName = node.getName();
                                      int intNextNodeID = node.getID();
                              %>
                              <option value="<%=intNextNodeID%>"><%=strNodeName %>
                              </option>
                              <%
                                  }
                              %>
                          </select>
                      </td>
                  </tr>
              </table>--%>
            <%---业务流编号--%>
            <input name="bizRoute.workflowId" type="hidden">
            <%--经手人--%>
            <input type="hidden" id="submitterId<%=token %>" name="bizRoute.submitterId" style="width:140px"/>
            <input type="hidden" id="submitterTime<%=token %>" name="bizRoute.submitterTime" style="width:140px"/>
            <input type="hidden" id="submitterName<%=token %>" name="bizRoute.submitterName" style="width:140px"/>

            <%--申请人--%>
            <input type="hidden" id="applicationId<%=token %>" name="bizRoute.applicationId" style="width:140px"/>
            <%--出纳--%>
            <input type="hidden" id="cashierId<%=token %>" name="bizRoute.cashierId" style="width:140px"/>
            <%--分管领导意见--%>
            <input type="hidden" id="chargeLeaderId<%=token %>" name="bizRoute.chargeLeaderId"
                   style="width:140px"/>
            <%--部门负责人--%>
            <input type="hidden" id="departmentLeaderId<%=token %>" name="bizRoute.departmentLeaderId"
                   style="width:140px"/>
            <%--董事--%>
            <input type="hidden" id="executiveDirectorId<%=token %>" name="bizRoute.executiveDirectorId"
                   style="width:140px"/>
            <%--总监--%>
            <input type="hidden" id="financeDirectorId<%=token %>" name="bizRoute.financeDirectorId"
                   style="width:140px"/>
            <%--会计--%>
            <input type="hidden" id="accountingId<%=token %>" name="bizRoute.accountingId" style="width:140px"/>
            <%--总经理--%>
            <input type="hidden" id="generalManagerId<%=token %>" name="bizRoute.generalManagerId"
                   style="width:140px"/>

            <%--保留字段--%>
            <%--<input type="hidden" id="controlInt1<%=token %>" name="bizRoute.controlInt1" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlInt2<%=token %>" name="bizRoute.controlInt2" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlInt3<%=token %>" name="bizRoute.controlInt3" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlDouble1<%=token %>" name="bizRoute.controlDouble1" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlDouble2<%=token %>" name="bizRoute.controlDouble2" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlDouble3<%=token %>" name="bizRoute.controlDouble3" style="width:140px"/>--%>
            <input type="hidden" id="controlString1<%=token %>" name="bizRoute.controlString1" style="width:140px"/>
            <input type="hidden" id="controlString2<%=token %>" name="bizRoute.controlString2" style="width:140px"/>
            <input type="hidden" id="controlString3<%=token %>" name="bizRoute.controlString3" style="width:140px"/>
            <%--<input type="hidden" id="controlMoney1<%=token %>" name="bizRoute.controlMoney1" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlMoney2<%=token %>" name="bizRoute.controlMoney2" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlMoney3<%=token %>" name="bizRoute.controlMoney3" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlTime1<%=token %>" name="bizRoute.controlTime1" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlTime2<%=token %>" name="bizRoute.controlTime2" style="width:140px"/>--%>
            <%--<input type="hidden" id="controlTime3<%=token %>" name="bizRoute.controlTime3" style="width:140px"/>--%>


            <input id="yWID<%=token%>" name="bizRoute.id_ywid" type="hidden" value="<%=strYWID%>"/>
            <!--申请备注-->
            <input id="applicationComment<%=token%>" name="bizRoute.applicationComment" type="hidden"/>
            <input id="applicantId<%=token%>" name="bizRoute.applicantId" type="hidden"/>
            <input id="applicantName<%=token%>" name="bizRoute.applicantName" type="hidden"/>
            <input id="applicantTime<%=token%>" name="bizRoute.applicantTime" type="hidden"/>

            <input type="hidden" id="TargetURL<%=token %>" name="TargetURL"/>
            <input type="hidden" id="JsonPrefix<%=token %>" name="JsonPrefix"/>
            <input type="hidden" id="ServiceType<%=token %>" name="ServiceType"/>
            <input type="hidden" id="BizDaoName<%=token %>" name="BizDaoName"/>
            <input name="CurrentNode" type="hidden" id="CurrentNode<%=token %>" value="<%=intCurrNodeID%>">
            <input name="WorkflowID" type="hidden" id="WorkflowID<%=token %>" value="<%=intWorkflowID%>">

            <%--<input name="YWID" type="hidden" value="<%=strYWID%>"/>--%>
            <input name="RouteListID" type="hidden" id="RouteListID<%=token %>" value="<%=intRouteListID%>">
            <input name="Participant" type="hidden" id="Participant<%=token %>">
            <%--用来存储需要实现iBizService 接口的service 类的名称--%>
            <input name="bizRoute.serviceClassName" type="hidden" id="serviceClassName<%=token %>">

            <%--状态--%>
            <input type="hidden" id="departmentLeaderStatus<%=token %>" name="bizRoute.departmentLeaderStatus"
                   style="width:140px"/>
            <input type="hidden" id="accountingStatus<%=token %>" name="bizRoute.accountingStatus" style="width:140px"/>
            <input type="hidden" id="chargeLeaderStatus<%=token %>" name="bizRoute.chargeLeaderStatus"
                   style="width:140px"/>
            <input type="hidden" id="financeDirectorStatus<%=token %>" name="bizRoute.financeDirectorStatus"
                   style="width:140px"/>
            <input type="hidden" id="generalManagerStatus<%=token %>" name="bizRoute.generalManagerStatus"
                   style="width:140px"/>
            <input type="hidden" id="executiveDirectorStatus<%=token %>" name="bizRoute.executiveDirectorStatus"
                   style="width:140px"/>
            <input type="hidden" id="cashierStatus<%=token %>" name="bizRoute.cashierStatus" style="width:140px"/>

            <input type="hidden" id="status1<%=token %>" name="bizRoute.status1" style="width:140px"/>
            <input type="hidden" id="status2<%=token %>" name="bizRoute.status2" style="width:140px"/>
            <input type="hidden" id="status3<%=token %>" name="bizRoute.status3" style="width:140px"/>
            <input type="hidden" id="status4<%=token %>" name="bizRoute.status4" style="width:140px"/>
            <input type="hidden" id="status5<%=token %>" name="bizRoute.status5" style="width:140px"/>

        </div>
    </form>
</div>
</body>
</html>
