<jsp:include page="../../checkLogin.jsp"></jsp:include>
<%
/**
 * 程序：李扬
 * 时间：2004-11-16
 * 说明：生成代码页面，根据工作流编号、主键、类名和包结构生成业务逻辑类
 */
%>
<%@ page contentType="application/*;charset=utf-8" %>
<% response.setHeader("Content-Disposition","attachment; filename="+request.getParameter("ClassName")+".java");%>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>


<%
String strWorkflowID = request.getParameter("WorkflowID");  //获得工作流编号
String strPrimaryKey = request.getParameter("PrimaryKey");  //获得主键，保存的是主键字段名
String strClassName = request.getParameter("ClassName");  //获得类名
String strPackageName = request.getParameter("PackageName");  //获得包结构
//实例化Code类
CodeMaker codeMaker = new CodeMaker(Integer.parseInt(strWorkflowID), strPrimaryKey);
//设置类名
codeMaker.setClassName(strClassName);
//设置包结构
codeMaker.setPackageName(strPackageName);
//生成代码
codeMaker.makeCode(out);
%>
