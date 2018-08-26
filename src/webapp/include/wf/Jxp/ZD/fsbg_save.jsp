<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.Fsbg" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="java.util.*" %>
<%
try {
  Fsbg fsbg = new Fsbg();
  fsbg.BuildObject(request);
  fsbg.update();
  %>
  <jsp:forward page="lzsp.jsp"></jsp:forward>
  <%
  }
  catch (Exception e) {
    throw e;
  }
  %>
