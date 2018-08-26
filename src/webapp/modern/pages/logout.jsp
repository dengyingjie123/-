<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%
    request.getSession().setAttribute(Config.SESSION_ACTION_LOGINUSER_STRING, null);
%>
<script>
    window.location = "<%=Config.getWebRoot()%>/modern/pages/login.jsp";
</script>