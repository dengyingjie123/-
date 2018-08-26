<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/9/10
  Time: 14:41
  To change this template use File | Settings | File Templates.
  兑付审批
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
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
          <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        <td><a id="btnResetPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
        </td>
      </tr>
    </table>
  </div>
    <br/>
    <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;' >
        <div title='等待审批' style='padding:5px; height:auto;'>
            <table id="PaymentPlanWaitTable<%=token%>"></table>
        </div>
        <div title='审批完成' style='padding:5px;height:auto;'>
            <table id="PaymentPlanOverTable<%=token%>"></table>
        </div>
    </div>

</div>
</body>
</html>
