<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
我的销售合同列表页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String token = request.getParameter("token");
%>
<html>
<head>
    <title>我的销售合同</title>
</head>
<body>
<div style="padding:5px;">

  <div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td>合同号</td>
        <td><input type="text" id="search_ContractNo<%=token %>" style="width:180px;"/></td>
        <td>产品名称</td>
        <td><input type="text" id="search_ProductionName<%=token %>" style="width:180px;"/></td>
        <td>签约用户</td>
        <td><input type="text" id="search_CustomerName<%=token %>" style="width:180px;"/></td>
        <td>合同状态</td>
        <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" editable="false"
                   style="width:180px;"/></td>
      </tr>
    </table>
    <table border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td>签约开始时间</td>
        <td><input type="text" class="easyui-datebox" id="search_SigendTime_Start<%=token %>"
                   editable="false"
                   style="width:180px;"/></td>
        <td>签约结束时间</td>
        <td><input type="text" class="easyui-datebox" id="search_SigendTime_End<%=token %>"
                   editable="false"
                   style="width:180px;"/></td>
        <td>流转状态</td>
        <td><input type="text" class="easyui-combotree" id="search_RouteActionType<%=token %>" editable="false"
                   style="width:180px;"/></td>
        <td>
          <a id="btnSearchContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-search">查询</a>
        </td>
        <td>
          <a id="btnResetContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-cut">重置</a>
        </td>
      </tr>
    </table>
  </div>
  <br>
  <table id="ContractTable<%=token%>" data-options="toolbar:toolbar"></table>

</div>
</body>
</html>
