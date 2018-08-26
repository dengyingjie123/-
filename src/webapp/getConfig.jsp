<%@ page contentType="text/html; charset=utf-8" language="java"  import="com.youngbook.common.config.Config" %><%
    String k = request.getParameter("k");
    out.print(Config.getSystemConfig(k));
%>