








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：业务位置查询页面
 * 时间：2005-10-17 18:09:57
 * 描述：查询业务所处位置
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*;" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
int intStatu = 1;
String strYWID = request.getParameter("YWID");
List listRL = ClientApplications.getActiveYW(strYWID, intWorkflowID);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>业务位置查询页面</title>
</head>
<body>
<table width="100%"  border="1">
  <tr>
    <td align='center'>业务号</td>
    <td align='center'>当前节点</td>
    <td align='center'>编号</td>
  </tr>
  <%
  for (int i = 0; listRL != null && i < listRL.size(); i++) {
    RouteList rl = (RouteList)listRL.get(i);
    Node node = Node.searchNodeObject(intWorkflowID, rl.getCurrentNode());
  %>
  <tr>
    <td align='center'><%=strYWID%></td>
    <td align='center'><%=rl.getCurrentNode()+"("+node.getName()+")"%></td>
    <td align='center'><%=rl.getID()%></td>
  </tr>
  <%
  }
  %>
</table>
</body>
</html>

