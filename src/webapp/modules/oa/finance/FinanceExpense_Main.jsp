<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 15-4-1
  Time: 下午3:10
  To change this template use File | Settings | File Templates.
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
                <td>部门</td>
                <td><input type="text" class="easyui-combotree" id="search_Department<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>报销人</td>
                <td><input type="text" id="search_ExpenseName<%=token %>" style="width:150px;"/></td>
                <td>金额</td>
                <td><input type="text" class="easyui-validatebox" id="search_Money<%=token %>" validType="currency"
                           style="width:150px;"/></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
            <tr>
                <td>状态</td>
                <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SubmitterTime_Start<%=token %>"
                           style="width:150px;" editable="false"/></td>
                <td>结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SubmitterTime_End<%=token %>"
                           style="width:150px;" editable="false"/></td>

            </tr>
        </table>
    </div>
    <br>

    <div style='height: 500px;width:auto'>
        <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
            <div title='我的申请' style='padding:5px'>
                <table id="FinanceExpenseTable<%=token%>"></table>
            </div>
            <div title='等待我审批' style='padding:5px'>
                <table id="WaitFinanceExpenseTable<%=token%>"></table>
            </div>
            <div title='已完成' style='padding:5px'>
                <table id="ParticipantFinanceExpenseTable<%=token%>"></table>
            </div>
        </div>

    </div>
</div>
</body>
</html>