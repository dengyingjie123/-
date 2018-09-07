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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结果</title>
<script src="../script/common.js"></script>
<script language="javascript">
  var url = "jxp/ZD/";
  switch (target) {
	  case "3": url += "slsq.jsp?op=dslsq"; break;
		case "4": url += "slsq.jsp?op=yykap"; break;
		case "12": url += "ddzx.jsp"; break;
		default: url += "lzsp.jsp"; break;
	}
window.location = url;
</script>
</head>

<body>

</body>
</html>
<%
}
%>