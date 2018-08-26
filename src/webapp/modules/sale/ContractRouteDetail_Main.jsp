<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2015/9/29
  Time: 9:32
  To change this template use File | Settings | File Templates.
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
<div class="easyui-layout" fit="true">
    <div style="padding:5px;">
        <table id="ContractRouteDetailTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('contractRouteDetailWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
