<%@ page contentType="text/html; charset=utf-8" %>
<%
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
//CurrNodeID=12&YWID
  var YWID = '<%=request.getParameter("YWID")%>';
  var CurrNodeID = 12;
  var url = "jxp/ZD/dd.jsp?CurrNodeID=" + CurrNodeID + "&YWID=" + YWID;
window.location = url;
</script>
</head>

<body>

</body>
</html>
<%
}
%>
