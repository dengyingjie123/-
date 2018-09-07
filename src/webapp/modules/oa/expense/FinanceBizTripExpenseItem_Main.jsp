<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-5-18
  Time: 下午 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
  String token = request.getParameter("token") ;
%>
<html>
<head>
  <title></title>
</head>
<body>
<div style='padding:5px;'>
  <table id='financeBizTripExpenseItemTable<%=token%>'></table>
</div>
</body>
</html>
