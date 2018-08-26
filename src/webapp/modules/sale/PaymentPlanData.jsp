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
                <td>兑付类型</td>
                <td><input type="text" class="easyui-combotree" id="search_Type<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>开始兑付日期</td>
                <td><input type="text" class="easyui-datebox" id="search_PaymentTime_Start<%=token %>"
                           style="width:150px;"
                           editable="false"/></td>
                <td>
                <td>结束兑付日期</td>
                <td><input type="text" class="easyui-datebox" id="search_PaymentTime_End<%=token %>"
                           style="width:150px;"
                           editable="false"/></td>

            </tr>
            <tr>
                <td>订单编号</td>
                <td><input type="text" class="easyui-textbox" id="search_orderId<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>产品名称</td>
                <td><input type="text" class="easyui-textbox" id="search_productName<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>客户名称</td>
                <td><input type="text" class="easyui-textbox" id="search_customerName<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>
                    <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                <td><a id="btnResetPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td></td>
            </tr>
        </table>
    </div>
    <br>
    <table id="PaymentPlanDataTable<%=token%>"></table>

</div>
</body>
</html>