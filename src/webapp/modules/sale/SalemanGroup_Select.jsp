<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
%>
<html>
<head>
  <title></title>
</head>
<body>
<div class="easyui-layout" fit="true" style="padding:5px;">

  <div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
      <tr>
          <td>销售组名</td>
        <td>
          <input type="text" class="easyui-validatebox" name="groupName" id="Search_GroupName<%=token%>" style="width:100px;"/>
        </td>
        <td>销售姓名</td>
        <td>
          <input type="text" class="easyui-validatebox" name="userName" id="Search_UserName<%=token%>" style="width:100px;"/>
        </td>
        <td>
          <a id="btnSalemanGroupSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-search">查询</a>
        </td>
        <td>
          <a id="btnSalemanGroupSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
             iconCls="icon-cut">重置</a>
        </td>
      </tr>
    </table>
  </div>
  <br>
    <table id="SalesmanGroupTable<%=token%>"></table>
    <div id="SalemanGroupSelectArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelect<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SalesmanGroupSelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>