<jsp:include page="../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
String strYWID=request.getParameter("YWID");
int intActionID=Integer.parseInt(request.getParameter("ActionID"));
List listhd=ClientApplications.getHistoryData(intWorkflowID,strYWID,intActionID);
Iterator ithd=listhd.iterator();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工作流业务数据历史</title>
</head>
<link href="../style/common.css" rel="stylesheet" type="text/css">
<body>
  <br>
  <center>
  <strong style="font-size:12pt">工作流业务数据历史</strong><br ><br >
  <table class="TabledStyle" width=531 border=0 align="center" cellpadding=0 cellspacing=1>
             <tr class="DateListTableHeadStyle">
			   <td width="193"  class="TableBgStyle"><div align="center">字段名</div></td>
              <td width="165"  class="TableBgStyle"><div align="center">
               原值
              </div></td>
              <td width="169"  class="TableBgStyle"><div align="center">
               新值
              </div></td>
              </tr>
			<%
			while (ithd.hasNext()){
			  HistoryData hd=(HistoryData)ithd.next();
			  %>
			   <tr >
			   <td  class="TableBgStyle" height="25"><div align="center"><%=hd.getName()%>(<%=hd.getTitle()%>)</div></td>
              <td  class="TableBgStyle"><div align="center"><%=hd.getOldVal()%>

              </div></td>
              <td  class="TableBgStyle"><div align="center"><%=hd.getNewVal()%>

              </div></td>
              </tr>
			<%}
			%>
			</table><br /><br />
<center>


</body>
</html>
