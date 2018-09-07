<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/9/10
  Time: 14:40
  To change this template use File | Settings | File Templates.
  兑付申请页面
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
                <td>所属项目</td>
                <td><input type="text" class="easyui-combotree" id="Search_ProjectName<%=token %>"
                           style="width:200px;"/></td>
                <td>产品名称</td>
                <td><input type="text" id="Search_ProductionName<%=token %>" style="width:200px;"/></td>

                <td>
                    <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                <td><a id="btnResetPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ProductionPaymentPlanApplyTable<%=token%>"></table>

</div>
</body>
</html>
