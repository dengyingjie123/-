<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：业务完成页面
 * 时间：2004/12/06 16:10:58
 * 描述：显示业务完成信息
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.admin.RouteList"%>
<%@ page import="com.youngbook.common.wf.common.*"%>
<%@ page import="com.ydtf.dmis.jxp.NewJxp" %>
<%@ page import="com.ydtf.dmis.jxp.JxpSb" %>
<%@ page import="com.youngbook.common.wf.processdefine.*"%>

<form name="form" method="post" action="jxp/SQ/index.jsp">
</form>
<%
String strResult = (String)request.getAttribute("Result");
String strFile = request.getParameter("File");

if (strResult.equals("1")){
  //上传附件
  if (strFile != null && strFile.equals("1")) {
    int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
    String strYWID = request.getParameter("YWID");
    out.println("<script language='JavaScript'>");
    out.println("  window.open('jxp/SQ/uploadFile.jsp?WorkflowID="+intWorkflowID+"&YWID="+strYWID+"&Address=GZNR','','width=550,height=500');");
    out.println("</script>");
  }
	out.println("<script language='JavaScript'>");
	out.println("document.form.submit();");
	out.println("</script>");
}
else {
  out.print("失败<br>");
  out.print("原因如下：(Exception)request.getAttribute(\"Exception \")<br>");
  Exception e=(Exception)request.getAttribute("Exception");
  out.print(e.getMessage());
  e.printStackTrace();
}
%>
