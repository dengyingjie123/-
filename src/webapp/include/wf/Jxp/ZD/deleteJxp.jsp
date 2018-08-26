<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
int intWorkflowID = 1;
String strYWID = request.getParameter("YWID");
NewJxp jxp = new NewJxp();
JxpSb jxpsb = new JxpSb();
FileLoad fl = new FileLoad();
jxp.setYWID(strYWID);
Connection conn = Tools.getDBConn();
conn.setAutoCommit(false);
try {
  if (!jxp.isEmptyID()) {
    jxp = jxp.BuildObject();
    //1、删除检修票
    jxp.delete(conn);
    //2、删除设备
    jxpsb.setUNID(String.valueOf(jxp.getID()));
    jxpsb.delete(conn);
    //3、删除附件
    fl.setWorkflowID(intWorkflowID);
    fl.setYWID(strYWID);
    fl.deleteByWorkflowIDAndYWID(conn);

    conn.commit();
  }
  else {
    conn.rollback();
    throw new Exception("无法获得业务编号");
  }
  if (ClientApplications.deleteYW(intWorkflowID, strYWID) == 1) {
    conn.commit();
    out.println("<script language='JavaScript'>");
    out.println("window.location = 'index.jsp'");
    out.println("</script>");
  }
}
catch (Exception e) {
  conn.rollback();
  throw e;
}
finally {
  if (conn != null) {
    conn.close();
  }
}
%>
