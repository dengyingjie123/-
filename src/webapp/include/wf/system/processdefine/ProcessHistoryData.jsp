<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import = "java.util.*" %>
<%
String strWorkflowID = request.getParameter("WorkflowID");
String strYWID = request.getParameter("YWID");
String strActionID = request.getParameter("ActionID");
if (strWorkflowID  == null || strYWID == null || strActionID == null) {
  throw new Exception("无法获得所要查询的工作流号、业务号或操作号！");
}
HistoryData hd = new HistoryData();
hd.setYWID(strYWID);
hd.setWorkflowID(Integer.parseInt(strWorkflowID));
hd.setActionID(Integer.parseInt(strActionID));
List listHD = new ArrayList();
listHD = hd.query();
Iterator itHD = listHD.iterator();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看数据</title>
</head>
<body>
<table width="373"  border="1">
  <tr>
    <td>字段名</td>
    <td>原值</td>
    <td>新值</td>
  </tr>
  <%
  while (itHD.hasNext()) {
    String strFieldTitle = new String();
    HistoryData hdTemp = (HistoryData)itHD.next();
    strFieldTitle = BizData.getFieldTitle(Integer.parseInt(strWorkflowID),hdTemp.getName());
  %>
  <tr>
    <td><%=strFieldTitle%></td>
    <td><%=hdTemp.getOldVal()%> &nbsp;</td>
    <td><%=hdTemp.getNewVal()%></td>
  </tr>
  <%
  }
  %>
</table>
</body>
</html>

