<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李扬
 * 时间：2004-11-24
 */
%>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%
try {
  //获得工作流编号
  int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
  //获得业务编号
  String strYWID = request.getParameter("YWID");

  ClientApplications.deleteYW(intWorkflowID, strYWID);
}
catch (Exception e) {
  throw e;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>删除业务成功</title>
<link href="../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="350"  border="0" align="center" cellpadding="0" cellspacing="1" class="TableINBgStyle">
  <tr>
    <td height="23" class="TableTdStyle">系统提示:</td>
  </tr>
  <tr>
    <td class="TableBgStyle"><div align="center">
      <p><br>
        删除业务成功</p>
      <p>[ <a href="ProcessDef.jsp">继续</a> ]<br>
        <br>
          </p>
    </div></td>
  </tr>
</table>
</body>
</html>
