








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：历史数据查询页面
 * 时间：2005-10-17 18:09:50
 * 描述：根据工作流编号、业务编号和行为编号，查找出历史数据的变化
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import = "java.util.*" %>
<%
String strWorkflowID = request.getParameter("WorkflowID");
String strYWID = request.getParameter("YWID");
String strActionID = request.getParameter("ActionID");
if (strWorkflowID  == null || strYWID == null || strActionID == null) {
  throw new Exception("无法获得所要查询的工作流号、业务号或操作号！");
}
int intWorkflowID = Integer.parseInt(strWorkflowID);
int intActionID = Integer.parseInt(strActionID);
List listHD = new ArrayList();  //历史数据记录
listHD = ClientApplications.getHistoryData(intWorkflowID, strYWID, intActionID);
Iterator itHD = listHD.iterator();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看数据</title>
</head>
<body>
<table width="400" align='center'  border="1">
  <tr>
    <td align='center'>字段名</td>
    <td align='center'>原值</td>
    <td align='center'>新值</td>
  </tr>
  <%
  while (itHD.hasNext()) {
    String strFieldTitle = new String();
    HistoryData hdTemp = (HistoryData)itHD.next();
    strFieldTitle = BizData.getFieldTitle(Integer.parseInt(strWorkflowID),hdTemp.getName());
  %>
  <tr>
    <td align='center'><%=hdTemp.getName()+"("+hdTemp.getTitle()+")"%></td>
    <td align='center'><%=hdTemp.getOldVal()%> &nbsp;</td>
    <td align='center'><%=hdTemp.getNewVal()%></td>
  </tr>
  <%
  }
  %>
</table>
</body>
</html>

