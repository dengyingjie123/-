<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：常用批示语删除成功页面
 */
%>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
	try{
	int intID = Integer.parseInt(request.getParameter("ID"));
	String strTYPE = Tools.toUTF8(request.getParameter("TYPE"));
	
	JxpYJ jxpYJ = new JxpYJ();
	jxpYJ.setID(intID);
	
	int intResult = jxpYJ.delete();
	if(intResult == 1){
%>
<jsp:forward page="ListYJ.jsp?TYPE=<%=strType%>"></jsp:forward>
 <%
	}
}catch(Exception e){

 out.print("删除失败,信息如下:"+e.getMessage());
 out.print("<a href='javascript:history.back();'>返回</a>");
}
%> 