<%@ page contentType="application/*;charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-16
 * 说明：下载附件页面
 */
%>
<% response.setHeader("Content-Disposition","attachment; filename="+ request.getParameter("FileName"));%>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.File" %>
<%
	int intID = Integer.parseInt(request.getParameter("ID"));
	String strName = Tools.toUTF8(request.getParameter("FileName"));
	
	FileLoad fileLoad = new FileLoad();
	fileLoad.setID(intID);
	fileLoad.OutputContent(response);
%>
