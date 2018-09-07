<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");

%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true">
     <table id="CustomerProductionDataTable<%=token%>" ></table>

</div>
</body>
</html>