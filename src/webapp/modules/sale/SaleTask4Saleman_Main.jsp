<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/22
  Time: 9:48
  To change this template use File | Settings | File Templates.
  销售小组产品分配
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
    <table id='saleTask4SalemanTable<%=token%>'></table>
</div>
</body>
</html>
