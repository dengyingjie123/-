<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%
/**
 * 标题：登陆页面
 * 描述：用于用户登陆
 * 时间：2005-10-17 18:09:04
 * 版权：
 */
%>
<html>
<head>
<title></title>
<style type="text/css">
<!--
.style1 {	font-size: 36px;
	font-weight: bold;
	font-family: Georgia, Times New Roman, Times, serif;
}
-->
</style>
<link href="style.css" rel="stylesheet" type="text/css">
</head>

<body>
<p align="center"><span class="style1">地调Dmis检修票系统</span></p>
<form action="List.jsp" method="post" name="form" id="form">
  <table width="40%" border="0" align="center" cellpadding="2" cellspacing="1" class="table_bg_black" >
    <tr class="table_bg_white">
      <td class="td_blue"><div align="right">用户名：</div></td>
      <td><input name="UserID" type="text" id="UserID"></td>
    </tr>
    <tr class="table_bg_white">
      <td class="td_blue"><div align="right">密码：</div></td>
      <td><input type="password" name="textfield">
      <input name="WorkflowID" type="hidden" value="7"></td>
    </tr>
  </table>
  <p align="center">
    <input type="submit" name="Submit" value="登录">
    <input type="reset" name="Reset" value="重写">
  </p>
</form>
</body>
</html>

