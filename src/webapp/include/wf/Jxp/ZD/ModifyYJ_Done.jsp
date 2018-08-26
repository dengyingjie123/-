<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 程序：李升华
 * 时间：2004-12-27
 * 说明：常用批示语修改成功页面
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
	String strMENU = Tools.toUTF8(request.getParameter("MENU"));
	String strITEM = Tools.toUTF8(request.getParameter("ITEM"));
	String strLIST = Tools.toUTF8(request.getParameter("LIST"));
	
	JxpYJ jxpYJ = new JxpYJ();
	jxpYJ.setID(intID);
	jxpYJ.setMENU(strMENU);
	jxpYJ.setITEM(strITEM);
	jxpYJ.setLIST(strLIST);
	
	int intResult = jxpYJ.update();
	if(intResult == 1){
%>
<jsp:forward page="queryYJ.jsp?ID=<%=intID%>"></jsp:forward>
 <%
	}
}catch(Exception e){

 out.print("增加失败,信息如下:"+e.getMessage());
 out.print("<a href='javascript:history.back();'>返回</a>");
}
%> 