<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2015/9/16
  Time: 11:22
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
<div style="padding:5px;" class="easyui-layout" fit="true">
    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="4" cellspacing="0">
            <tr>
                <td>兑付类型</td>
                <td><input type="text" class="easyui-combotree" id="search_Type<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>兑付状态</td>
                <td><input type="text" class="easyui-combotree" id="search_status<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>开始兑付日期</td>
                <td><input type="text" class="easyui-datebox" id="search_PaymentTime_Start<%=token %>"
                           style="width:150px;"
                           editable="false"/></td>
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
                    <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div>
        <table id="PaymentPlanQueryTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('queryWindow<%=token%>')">取消</a>
    </div>

</div>
</body>
</html>
