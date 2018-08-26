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
String strDept=Tools.toUTF8(request.getParameter("Dept"));
String strUserName=request.getParameter("UserName");
String strpassword=request.getParameter("Password");
String strXM=Tools.toUTF8(request.getParameter("XM"));

if (strDept==null||strDept.equals("")||
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
	sys_user user=new sys_user();
	user.setDEPT(strDept);
	user.setPASSWORD(strpassword);
	user.setUSERID(strUserName);
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
	  user = (sys_user)ls.get(0);
          strXM = user.getXM();
	  session.setAttribute("dwlb", "中调科室");
	  session.setAttribute("dangwei", strDept.trim());
	  session.setAttribute("xingming", strXM.trim());
	  session.setAttribute("username", strUserName.trim());
	  %>
	<script language="javascript" type="text/javascript">
	window.location="ZD/index.jsp"
	</script>
	<%}
	}
%>
</body>
</html>
