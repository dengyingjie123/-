<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/26
  Time: 17:43
  To change this template use File | Settings | File Templates.
  休假申请审批页面
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>申请人</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="hRLeave.applicantName"
                           id="Search_ApplicantId<%=token%>" style="width:100px;"/>
                </td>
                <td>请假类别</td>
                <td>
                    <input type="text" class="easyui-combotree" name="hRLeave.leaveTypeId"
                           id="Search_LeaveTypeId<%=token%>" style="width:100px;"/>
                </td>
                <td>天数</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="Search_Days_Start"
                           id="Search_Days_Start<%=token%>" style="width:100px;"/>
                    至
                    <input type="text" class="easyui-validatebox" name="Search_Days_End" id="Search_Days_End<%=token%>"
                           style="width:100px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    开始时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_StartTime_Start"
                           id="Search_StartTime_Start<%=token%>" editable="false" style="width:100px;"/>

                </td>
                <td>
                    开始时间
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_StartTime_End"
                           id="Search_StartTime_End<%=token%>" editable="false" style="width:100px;"/>
                </td>

                <td>
                    <a id="btnHRLeaveSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnHRLeaveSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
        <div title='我的申请' style='padding:5px'>
            <table id="HRLeaveTable<%=token%>"></table>
        </div>
        <div title='等待我审批' style='padding:5px'>
            <table id="HRLeavewaitTable<%=token%>"></table>
        </div>
        <div title='已完成' style='padding:5px'>
            <table id="HRLeaveTableParticipant<%=token%>"></table>
        </div>

    </div>

</div>
</body>
</html>