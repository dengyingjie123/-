<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
Connection conn = Tools.getDBConn();
int intWorkflowID = 1;
try {
  conn.setAutoCommit(false);

  NewJxp jxp = new NewJxp();
  JxpSb jxpsb = new JxpSb();
  FileLoad fl = new FileLoad();

  jxp.BuildObject(request);
  
  //1、更新检修票
  jxp.update(conn);
  //2、更新设备
	//删除原先的设备
	jxpsb.setUNID(String.valueOf(jxp.getID()));
  jxpsb.delete(conn);
	//加入新的设备
  List listSB = new ArrayList();
  listSB = jxp.getJxpSb();
  for (int i = 0; listSB != null && i < listSB.size(); i++) {
    jxpsb = (JxpSb)listSB.get(i);
    jxpsb.insert(conn);
  }

  conn.commit();
%>
<jsp:forward page="index.jsp"></jsp:forward>
<%
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
