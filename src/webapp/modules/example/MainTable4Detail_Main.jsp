<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
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
                <td>
                    开始A5
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A5_Start" id="Search_A5_Start<%=token%>"
                           editablt="true" style="width:100px;"/>
                </td>
                <td>
                    结束A5
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A5_End" id="Search_A5_End<%=token%>"
                           editablt="true" style="width:100px;"/>
                </td>
                <td>
                    开始A6
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A6_Start" id="Search_A6_Start<%=token%>"
                           editablt="true" style="width:100px;"/>
                </td>
                <td>
                    结束A6
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A6_End" id="Search_A6_End<%=token%>"
                           editablt="true" style="width:100px;"/>
                </td>

            </tr>
            <tr>
                <td>
                    A1
                </td>
                <td>
                    <input type="text" class="easyui-validatebox"
                           name="search_A1" id="search_A1<%=token%>" style="width:100px;"/>
                </td>
                <td>
                    A2
                </td>
                <td>
                    <input type="text" class="easyui-validatebox"
                           name="search_A2" id="search_A2<%=token%>" style="width:100px;"/>
                </td>
                <td>
                    A3
                </td>
                <td>
                    <input type="text" class="easyui-validatebox" name="mainTableVO_a3_Start" id="mainTableVO_a3_Start<%=token%>" style="width:20px;"/>
                    至
                    <input type="text" class="easyui-validatebox" name="mainTableVO_a3_End" id="mainTableVO_a3_End<%=token%>" style="width:20px;"/>
                </td>
                <td>
                    A4
                </td>
                <td>
                    <input type="text" class="easyui-validatebox" data-optioins="validType:'intOrFloat'"
                           name="search_A4" id="search_A4<%=token%>" style="width:100px;"/>
                </td>
                <td>
                    <a id="btnMainTableSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnMainTableSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
            <tr>
            <td>A7</td>
            <td><input type="text" class="easyui-combotree" id="search_A7<%=token %>" style="width:80px;" editable="false" /></td>
            <td>A8</td>
            <td><input type="text" class="easyui-combotree" id="search_A8<%=token %>" style="width:80px;" editable="false" /></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        </table>
    </div>
    <br>
    <table id="MainTableTable<%=token%>"></table>

</div>
</body>
</html>