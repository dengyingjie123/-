<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<style>
#calendar {
		max-width: 900px;
		margin: 0 auto;
	}
</style>
</head>
<body>
<table  id="raiseInfoTable<%=token%>">
</table>
<br />
<br />
<div style="max-width:800px; margin: 0 auto;" id='calendar'></div>
<br/>

</body>
</html>