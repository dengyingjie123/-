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
                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td>结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_StopTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_StopTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td>创建时间</td>
                <td><input type="text" class="easyui-datebox" id="search_CreateTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_CreateTime_End<%=token %>" style="width:100px;" editable="false" /></td>
            </tr><tr>
                <td>执行时间 </td>
                <td><input type="text" class="easyui-datebox" id="search_ExecuteTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_ExecuteTime_End<%=token %>" style="width:100px;" editable="false" /></td>

                <td>检查时间 </td>
                <td><input type="text" class="easyui-datebox" id="search_CheckTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_CheckTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td align="right">名称</td>
            <td><input type="text" id="search_name<%=token%>" name="name" style="width:100px;"/></td>
            <td align="right">状态</td>
            <td>
                <input  id="search_status<%=token%>" class="easyui-combotree" style="width:100px;"/>
            </td>
        </tr>
            <tr>
                <td align="right">执行者</td>
                <td><input type="text" name="executorName" id="search_executorName<%=token%>" style="width:100px;"></td>
                <td align="right">创建者</td>
                <td><input type="text" name="creatorName" id="search_creatorName<%=token%>" style="width:100px;"></td>
                <td align="right">
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td align="right">
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="TaskTable<%=token%>"></table>

</div>
</body>
</html>
