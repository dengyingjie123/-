<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%
String strActions = request.getParameter("Actions");
if (strActions != null && strActions.equals("Login")) {
  Config workflowConfig = new Config();
  workflowConfig.Read();
  String strAdminPwd = request.getParameter("AdminPwd");
  String strUser=request.getParameter("Admin");
  if (strAdminPwd != null && strAdminPwd.equals(workflowConfig.getAdminPwd())&&strUser!=null && strUser.equals("admin")) {
	session.setAttribute("WorkflowLogin","1");
  %>
  <jsp:forward page="ManagerFrame.jsp"></jsp:forward>
  <%
  }
  else {
    out.println("<script  language='javascript'>alert('登录失败，用户名或是密码错误!')</script>");
  }
}
%>
<html>
<head>
<SCRIPT language="javascript">
if ( top.frames.length > 1 )
top.location="Login.jsp";
</SCRIPT>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理员登录</title>
<link href="style/wo" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.TextStyle{background-image: url(images/TextStyleimg.gif);font-size:9pt;font-family:宋体;border-width: 1;border:1px solid #999999;HEIGHT: 22px;}
.ButtonStyle{background-image: url(images/Button.gif);BORDER-BOTTOM:#5A9ADF 1px solid;BORDER-LEFT: #5A9ADF 1px solid;BORDER-RIGHT:#5A9ADF 1px solid; BORDER-TOP: #5A9ADF 1px solid;FONT-FAMILY:宋体;FONT-SIZE:9pt;HEIGHT: 22px;}

-->
</style>
<script language="javascript">
	if(self != top)
		top.location = self.location;
</script>
</head>

<body topMargin=0 leftMargin=0 rightMargin=0 bottomMargin=0 background="images/Login1.png"><form name="form1" method="post" action="Login.jsp">
<div style="position:absolute; left: 683px; top: 329px; width: 201px; height: 152px;">
   <table cellSpacing=0 cellPadding=0 border=0>
   <tr>
    <td height="25"><font style="color:#006699;font-size:12pt">用户名：</font></td>
    <td><input name="Admin" type="text" class="TextStyle" id="Admin" style="WIDTH:90pt" ></td>
     </tr>
	 <tr>
     <td height="25"><font style="color:#006699;font-size:12pt"> 密 &nbsp;码：</font></td>
      <td><input name="AdminPwd" type="password" class="TextStyle" id="AdminPwd" style="WIDTH:90pt"></td>
     </tr>
 <tr>

	<td align="center" colspan="2">
      <br>
      <br>
      <input name="Actions" type="hidden" value="Login">
      <input name="Submit" type="submit"  value="确定" class="ButtonStyle" style="width:70px">
      <input type="reset" name="reset"  value="取消" class="ButtonStyle" style="width:70px">
    </td> 
 </tr>  </table>
</div></form>
</body>
</html>
