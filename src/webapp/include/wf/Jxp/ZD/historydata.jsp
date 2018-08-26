<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：历史数据查询页面
 * 时间：2004/12/06 16:11:11
 * 描述：根据工作流编号、业务编号和行为编号，查找出历史数据的变化
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.NewJxp" %>
<%@ page import = "java.util.*" %>
<%
int intWorkflowID = 1;
String strYWID = request.getParameter("YWID");
String strActionID = request.getParameter("ActionID");
if (strYWID == null || strActionID == null) {
  throw new Exception("无法获得所要查询的工作流号、业务号或操作号！");
}

int intActionID = Integer.parseInt(strActionID);
List listHD = new ArrayList();  //历史数据记录
listHD = ClientApplications.getHistoryData(intWorkflowID, strYWID, intActionID);
Iterator itHD = listHD.iterator();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看数据变化</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="400"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><form action="" method="post" name="form" id="form">
      <div align="center">数据变化<br>
      </div>
      <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
        <tr class="tdbg">
          <td>字段</td>
          <td>原值</td>
          <td>新值</td>
        </tr>
        <%
        while (itHD.hasNext()) {
          String strFieldTitle = new String();
          HistoryData hdTemp = (HistoryData)itHD.next();
          strFieldTitle = BizData.getFieldTitle(intWorkflowID,hdTemp.getName());
        %>
        <tr class="tdbg">
          <td><%=hdTemp.getName()+"("+hdTemp.getTitle()+")"%></td>
          <td><%=Tools.HTMLEncode(hdTemp.getOldVal())%> &nbsp;</td>
          <td><%=Tools.HTMLEncode(hdTemp.getNewVal())%> &nbsp;</td>
        </tr>
        <%
          }
        %>
      </table>
      <div align="center"><br>
          <a href="javascript:window.close()">关闭窗口</a> </div>
    </form></td>
  </tr>
</table>
</body>
</html>
