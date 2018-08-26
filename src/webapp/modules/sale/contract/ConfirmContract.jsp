<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
销售合同状态确认页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String token = request.getParameter("token");
%>
<html>
<head>
    <title>销售合同状态确认页</title>
</head>
<body>

<div class="easyui-layout" fit="true">
  <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
    <table id="ConfirmContractTable<%=token%>" data-options="toolbar:toolbar"></table>
  </div>
  <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
    <a id="btnConfirmContractSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
       href="javascript:void(0)">确定</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
       onClick="fwCloseWindow('ConfirmContractWindow<%=token%>')">取消</a>
  </div>
</div>
</body>
</html>
