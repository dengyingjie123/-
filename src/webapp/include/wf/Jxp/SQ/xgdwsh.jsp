<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<html>
<head>
<title>
相关单位审核
</title>
</head>
<body bgcolor="#ffffff">
<%
String strHGDWYJ=request.getParameter("XGDWYJ");
int intRouteListID=Integer.parseInt(request.getParameter("RouteListID"));
String strYWID=request.getParameter("YWID");
String strParticipant=request.getParameter("Participant");
int intID=Integer.parseInt(strYWID);
NewJxp newjxp=new NewJxp ();
newjxp.setXGDWYJ(Tools.toUTF8(strHGDWYJ));
newjxp.setSQDW((String)session.getAttribute("dangwei"));
newjxp.setID(intID);
newjxp.setSQR(strParticipant);
if (newjxp.xgdwsh(intRouteListID)==1)
{
  %>

  相关单位审核完成！
  <script language="javascript">
  window.location="index.jsp";
  </script>
  <%
}
  %>
</body>
</html>
