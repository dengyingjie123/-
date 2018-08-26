<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.lang.*" %>
<%
	String strLogin = (String)session.getAttribute("WorkflowLogin");
	if(strLogin == null||!strLogin.equals("1")){
		%>
		<script language="javascript">
		window.location="/workflow/Login.jsp";
		</script>
	<%}
%>
