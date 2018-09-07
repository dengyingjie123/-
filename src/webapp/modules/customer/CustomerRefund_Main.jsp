<%--客户退款--%>
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

                <td>客户名称</td>
                <td><input type="text" class="easyui-textbox" id="search_customerName<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>操作员名称</td>
                <td><input type="text" class="easyui-textbox" id="search_operatorName<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>操作时间</td>
                <td><input type="text" class="easyui-datebox" id="search_operateTime<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>
                    <a id="btnSearchCustomerMoney<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchCustomerMoneyReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="customerRefundTable<%=token%>"></table>

</div>
</body>
</html>