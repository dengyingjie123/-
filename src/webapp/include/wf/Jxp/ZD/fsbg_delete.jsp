<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%
Connection conn = Tools.getDBConn();
int intWorkflowID = 3;
String strYWID = request.getParameter("YWID");
try {
  conn.setAutoCommit(false);
  Fsbg fsbg = new Fsbg();
  fsbg.setYWID(strYWID);
  FileLoad fl = new FileLoad();
  fl.setWorkflowID(intWorkflowID);
  fl.setYWID(strYWID);
  //删除方式变更单
  fsbg.delete(conn);
  //删除附件
  fl.deleteByWorkflowIDAndYWID(conn);
  if (ClientApplications.deleteYW(intWorkflowID, strYWID) == 1) {
    conn.commit();
    //lzsp.jsp
    %>
    <script language="javascript" type="text/javascript">
    window.location = "lzsp.jsp";
    </script>
    <%
  }
}
catch (Exception e) {
  conn.rollback();
  %>
  <script language="javascript" type="text/javascript">
  window.alert("删除业务出错！" + "\n" +
               "<%=e.getMessage()%>");
  window.close();
  </script>
  <%
  throw e;
}
finally {
  if (conn != null) {
    conn.close();
  }
}
%>
