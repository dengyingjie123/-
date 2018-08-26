<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <%--<div class="easyui-panel" title="查询" iconCls="icon-search">--%>
        <%--<table border="0" cellpadding="3" cellspacing="0">--%>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--<a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</div>--%>
    <%--<br>--%>
    <table id="DetailTable<%=token%>"></table>

</div>
</body>
</html>
