<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/14
  Time: 16:24
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
                <td align="right">出差人</td>
                <td><input type="text" id="Search_userId<%=token %>" style="width:200px"/></td>

                <td align="right">部门</td>
                <td><input type="text" class="easyui-combotree" id="Search_department<%=token %>" editable="false"
                           style="width:200px"/></td>

                <td align="right">费用预算</td>
                <td><input type="text" id="Search_expenseBudge<%=token %>" style="width:200px"/></td>
            </tr>

            <tr>
                <td align="right">实际费用</td>
                <td><input type="text" id="Search_expenseActual<%=token %>" style="width:200px"/></td>

                <td align="right">经办人签字</td>
                <td><input type="text" id="Search_operatorSign<%=token %>" style="width:200px"/></td>

                <td>
                    <a id="btnBusinessTripApplicationSearchSubmit<%=token %>" href="javascript:void(0)"
                       class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnBusinessTripApplicationSearchReset<%=token %>" href="javascript:void(0)"
                       class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div style="height:600px;">
        <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
            <div title='我的申请' style='padding:5px'>
                <table id="BusinessTripApplicationTable<%=token%>"></table>
            </div>
            <div title='等待我审批' style='padding:5px'>
                <table id="WaitBusinessTripApplicationTable<%=token%>"></table>
            </div>
            <div title='已完成' style='padding:5px'>
                <table id="ParticipantBusinessTripApplicationTable<%=token%>"></table>
            </div>
        </div>
    </div>

</div>
</body>
</html>
