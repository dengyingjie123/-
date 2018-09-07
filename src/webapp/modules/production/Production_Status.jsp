<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.production.ProductionStatus" %>
<%=ProductionStatus.getStrutsJSONArray().toString()%>