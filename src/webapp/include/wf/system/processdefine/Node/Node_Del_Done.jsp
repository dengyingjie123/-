<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序:李扬
 * 时间:2004-11-16
 * 说明:删除节点页面，根据NodeList.jsp传过来的参数（工作流编号，节点编号），删除节点
 *     并显示删除成功页面
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.jdom.*" %>
<%
try {
int intResult = 0;
//从从request中获得工作流编号
String strWorkflowID = request.getParameter("WorkflowID");
//从request中获得节点编号
String strID = request.getParameter("ID");
//判断工作流编号和节点编号的合法性
if (strWorkflowID != null && !strWorkflowID.equals("") &&
  strID != null && !strID.equals("")) {
  int intWorkflowID = Integer.parseInt(strWorkflowID);
  int intID = Integer.parseInt(strID);
  //根据工作流编号和节点编号，查出相应的节点
  Node node = Node.searchNodeObject(intWorkflowID, intID);
  //删除该节点
  intResult = node.delete();
  node.destroy();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>删除成功</title>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
  删除节点成功!<br>
              <br>
              [ <a href="Node_SelectWorkflow.jsp">继续</a> ] <br>
              <br>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<%
}
catch (Exception e) {
  //异常处理
  out.println(e.getMessage());
  out.println("<a href = 'Node_SelectWorkflow.jsp'>返回</a>");
}
%>
