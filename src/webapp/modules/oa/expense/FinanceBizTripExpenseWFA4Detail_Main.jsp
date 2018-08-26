<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-5-18
  Time: 下午 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style='padding:5px;'>
    <div class='easyui-panel' title='查询' iconCls='icon-search'>
        <table border='0' cellpadding='3' cellspacing='0'>
            <tr>
                <td align="right">部门组织</td>
                <td><input type='text' class='easyui-combotree' name='search_orgName' id='search_orgName<%=token%>'
                           style='width:150px;'/></td>
                <td align="right">出差人</td>
                <td><input type='text' class='easyui-validatebox' name='search_userName' id='search_userName<%=token%>'
                           style='width:150px;'/></td>
                <td align="right">报销人</td>
                <td><input type='text' class='easyui-validatebox' name='search_reimburseName'
                           id='search_reimburseName<%=token%>' style='width:150px;'/></td>
                <td align="right">金额</td>
                <td><input type='text' class='easyui-validatebox' name='search_money' id='search_money<%=token%>'
                           style='width:150px;'/></td>
            </tr>
            <tr>
                <td align="right">报销人开始时间</td>
                <td><input type='text' class='easyui-datebox' name='search_reimburseTime_start'
                           id='search_reimburseTime_start<%=token%>' style='width:150px;' editable='false'/></td>
                <td align="right">报销人结束时间</td>
                <td><input type='text' class='easyui-datebox' name='search_reimburseTime_end'
                           id='search_reimburseTime_end<%=token%>' style='width:150px;' editable='false'/></td>
                <td>
                    <a id='btnFinanceBizTripExpenseWFASearchSubmit<%=token %>' href='javascript:void(0)'
                       class='easyui-linkbutton' iconCls='icon-search'>查询</a>
                </td>
                <td>
                    <a id='btnFinanceBizTripExpenseWFASearchReset<%=token %>' href='javascript:void(0)'
                       class='easyui-linkbutton' iconCls='icon-cut'>重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div style="height:600px;">
        <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
            <div title='我的申请' style='padding:5px'>
                <table id="financeBizTripExpenseWFATable<%=token%>"></table>
            </div>
            <div title='等待我审批' style='padding:5px'>
                <table id="WaiitfinanceBizTripExpenseWFATable<%=token%>"></table>
            </div>
            <div title='已完成' style='padding:5px'>
                <table id="ParticipantfinanceBizTripExpenseWFATable<%=token%>"></table>
            </div>

        </div>
    </div>
</div>
</body>
</html>
