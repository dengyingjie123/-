<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%@ page contentType="application/*;charset=utf-8" %>
<% response.setHeader("Content-Disposition","attachment; filename=Login.jsp");%>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>


<%
String strWorkflowID = request.getParameter("WorkflowID");  //获得工作流编号
String strPrimaryKey = request.getParameter("PrimaryKey");  //获得主键，保存的是主键字段名
String strClassName = request.getParameter("ClassName");  //获得类名
String strPackageName = request.getParameter("PackageName");  //获得包结构
String strDir = request.getParameter("Dir");
PageMaker pm = new PageMaker();
pm.setWorkflowID(Integer.parseInt(strWorkflowID));
pm.setPrimaryKey(strPrimaryKey);
pm.setClassName(strClassName);
pm.setPackage(strPackageName);
pm.setDir(strDir);
pm.initField();
pm.makeLoginPage(out);
%>
