<%--系统统计--%>

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

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td>
                    <input type="text" name="statistics.name" id="search_name<%=token%>" style="width:150px;"/>
                </td>
                <td>标识</td>
                <td>
                    <input  type="text" naem="statistics.tag" id="search_tag<%=token%>" style="width:150px"/>
                </td>
                <td>数值</td>
                <td>
                    <input  type="text" naem="statistics.v"  id="search_v<%=token%>" style="width:150px"/>
                </td>
                <td>
                    <a id="btnStatisticsSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnStatisticsSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="statisticsTable<%=token%>"></table>

</div>
</body>
</html>