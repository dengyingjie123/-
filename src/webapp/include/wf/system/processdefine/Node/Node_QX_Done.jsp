<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%
/**
 * 程序：李扬
 * 时间：2004-10-27
 * 说明：保存节点权限配置，如果数据库中有该节点的配置信息，则完成更新操作，如果没有，则完成
 *      插入操作
 *      保存通过Participant类实现
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%
String strInfo = new String();
String strActions = new String();
Connection conn = null;
try {
  StringBuffer sbSQL = new StringBuffer();
  //从request中获得工作流编号
  String strWorkflowID = request.getParameter("WorkflowID");
  //从request中获得节点编号
  String strNodeID = request.getParameter("NodeID");
  //从request中获得角色
  String[] strRole = request.getParameterValues("RoleTo");
  //从request中获得用户
  String[] strUser = request.getParameterValues("UserTo");
  //角色缓冲字符串，用于将角色从数组的形式变为|XXX|XXX|
  StringBuffer sbRole = new StringBuffer();
  //用户缓冲字符串，用于将用户从数组的形式变为|XXX|XXX|
  StringBuffer sbUser = new StringBuffer();

  //转变角色存储形式
  for (int i = 0; strRole != null && i < strRole.length; i++) {
    sbRole.append("|");
    sbRole.append(strRole[i]);
  }
  //转变用户存储形式
  for (int i = 0; strUser != null && i < strUser.length; i++) {
    sbUser.append("|");
    sbUser.append(Tools.toUTF8(strUser[i]));
  }
  //添加最后一个“|”
  if (sbRole.length() > 0) {
    sbRole.append("|");
  }
  //添加最后一个“|”
  if (sbUser.length() > 0) {
    sbUser.append("|");
  }
  //实例化参与者类，用于保存节点权限信息
  Participant pt = new Participant();
  pt.setWorkflowID(Integer.parseInt(strWorkflowID));
  pt.setNodeID(Integer.parseInt(strNodeID));
  List listResult = pt.query();
  if (listResult != null && listResult.size() > 0) {
    //update
    sbSQL.append("UPDATE WORKFLOW_PARTICIPANT SET ");
    sbSQL.append("ROLEID='");
    sbSQL.append(sbRole.toString());
    sbSQL.append("',");
    sbSQL.append("USERLIST='");
    sbSQL.append(sbUser.toString());
    sbSQL.append("' WHERE ");
    sbSQL.append("WORKFLOWID=");
    sbSQL.append(Integer.parseInt(strWorkflowID));
    sbSQL.append(" AND ");
    sbSQL.append("NODEID=");
    sbSQL.append(Integer.parseInt(strNodeID));
  }
  else {
    //insert
    sbSQL.append("INSERT INTO WORKFLOW_PARTICIPANT (WORKFLOWID,NODEID,ROLEID,USERLIST) VALUES (");
    sbSQL.append(Integer.parseInt(strWorkflowID));
    sbSQL.append(",");
    sbSQL.append(Integer.parseInt(strNodeID));
    sbSQL.append(",'");
    sbSQL.append(sbRole.toString());
    sbSQL.append("','");
    sbSQL.append(sbUser.toString());
    sbSQL.append("')");
  }
  System.out.println("工作流节点权限配置： " +sbSQL.toString());
  conn = Tools.getDBConn();
  Statement statement = conn.createStatement();
  int intResult = 0;
  intResult = statement.executeUpdate(sbSQL.toString());
  if (statement != null) {
    statement.close();
  }
  if (intResult == 1) {
    strInfo = "配置成功!";
    strActions = "<a href = 'Node_SelectWorkflow.jsp'>继续</a>";
  }
  else {
    strInfo = "配置失败!";
    strActions = "<a href = 'Node_SelectWorkflow.jsp'>返回</a>";
  }
}
catch (Exception e) {
  strInfo = "配置失败!失败信息如下：" + e.getMessage();
  strActions = "<a href = 'Node_SelectWorkflow.jsp'>返回</a>";
}
finally {
  if (conn != null) {
    conn.close();
  }
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>配置成功！</title>
<link href="../../style/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="300"  border="0" align="center" cellpadding="0" cellspacing="0" id="bg">
  <tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="TableBorderStyle">
        <tr>
          <td height="23" class="TableTdStyle">系统提示:</td>
        </tr>
        <tr>
          <td class="TableBgStyle"><div align="center"><br>
              <%=strInfo%><br>
              <br>
              [ <%=strActions%> ] <br>
              <br>
          </div></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
