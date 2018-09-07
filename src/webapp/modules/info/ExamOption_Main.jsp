<%--
  Created by IntelliJ IDEA.
  User: zsq
  Date: 4/14/2015
  Time: 9:42 AM
  To change this template use File | Settings | File Templates.
--%>
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
                <td>问题</td>
                <td><input class="easyui-validatebox" type="text" id="Search_QuestionId<%=token %>"/></td>
                <td>选项</td>
                <td><input class="easyui-validatebox" type="text" id="Search_Name<%=token %>"/></td>
                <td>分数</td>
                <td><input class="easyui-validatebox" type="text" id="Search_Score<%=token %>"/></td>
                <td>
                    <a id="btnExamOptionSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnExamOptionSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ExamOptionTable<%=token%>"></table>

</div>
</body>
</html>
