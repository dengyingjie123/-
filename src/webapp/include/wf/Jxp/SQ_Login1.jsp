<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<html>
<head>
<title>
</title>
</head>
<body bgcolor="#ffffff">
<%
String strDwmc=Tools.toUTF8(request.getParameter("dwmc"));
String strUserName=request.getParameter("UserName");
String strpassword=request.getParameter("Password");
String strXM=Tools.toUTF8(request.getParameter("XM"));
if (strDwmc==null||strDwmc.equals("")||
    strUserName==null||strUserName.equals("")||
	strpassword==null||strpassword.equals(""))
	{%>
	<script language="javascript" type="text/javascript">
	alert("请输入单位名称、用户名、密码！");
	history.back();
	</script>
	<%}
	else
	{
	sys_squser user=new sys_squser();
	user.setDWMC(strDwmc);
	user.setPASSWORD(strpassword);
	user.setUNID(strUserName);
	List ls=user.queryExact();
	if (ls.size()!=1){
	%>
	<script language="javascript" type="text/javascript">
	alert("无效的用户名和密码!");
	history.back();
	</script>
	<%
	}
	else
	{
	  session.setAttribute("dwlb", "远程用户");
	  session.setAttribute("dangwei", strDwmc.trim());
	  session.setAttribute("xingming", strXM.trim());
	  session.setAttribute("username", strUserName.trim());
	  %>
	<script language="javascript" type="text/javascript">
	window.location="SQ/index.jsp"
	</script>
	<%}
	}
%>
</body>
</html>
