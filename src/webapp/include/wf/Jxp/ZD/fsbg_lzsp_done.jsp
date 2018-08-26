<%@ page contentType="text/html; charset=utf-8" %>
<%
String strTarget = request.getParameter("Target");
out.println("<script language='javascript'>");
out.println("var target = '"+ strTarget + "';");
out.println("</script>");
String strResult=(String)request.getAttribute("Result");
if (!strResult.equals("1")){
  out.print("失败<br>");
	Exception e=(Exception)request.getAttribute("Exception");
	out.print(e.getMessage());
  e.printStackTrace();
	out.println("<br>");
	out.println("<a href='javascript:history.go(-1)'>返回</a>");
}
else {
%>
<script language="javascript">
window.location = "jxp/ZD/lzsp.jsp";
</script>

<%
}
%>