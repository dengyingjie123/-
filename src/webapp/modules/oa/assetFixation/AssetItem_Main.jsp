<%--
  Created by IntelliJ IDEA.
  User: Jepson
  Date: 2015/6/8
  Time: 14:04
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
    <table id='assetItem<%=token%>'></table>
</div>
</body>
</html>
