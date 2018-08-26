<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true" style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>A1</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="mainTable.a1" id="Search_A1<%=token%>" style="width:100px;"/>
                </td>
                <td>A2</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="mainTable.a2" id="Search_A2<%=token%>" style="width:100px;"/>
                </td>
                <td>A3</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="Search_A3_Start" id="Search_A3_Start<%=token%>" style="width:20px;"/>
                    至
                    <input type="text" class="easyui-validatebox" name="Search_A3_End" id="Search_A3_End<%=token%>" style="width:20px;"/>
                </td>
                <td>
                    开始A5
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A5_Start" id="Search_A5_Start<%=token%>" editable="true" style="width:100px;"/>
                           
                </td>
                <td>
                    结束A5
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A5_End" id="Search_A5_End<%=token%>" editable="true" style="width:100px;"/>
                </td>
                <td>
                    开始A6
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A6_Start" id="Search_A6_Start<%=token%>" editable="true" style="width:100px;"/>
                           
                </td>
                <td>
                    结束A6
                </td>
                <td>
                    <input type="text" class="easyui-datebox" name="Search_A6_End" id="Search_A6_End<%=token%>" editable="true" style="width:100px;"/>
                </td>
                <td>A7</td>
                <td><input type="text" class="easyui-combotree" id="Search_A7<%=token %>" style="width:80px;" editable="false" /></td>
                <td>A8</td>
                <td><input type="text" class="easyui-combotree" id="Search_A8<%=token %>" style="width:80px;" editable="false" /></td>
                <td>
                    <a id="btnMainTableSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnMainTableSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="MainTableTable<%=token%>"></table>
    <div id="SelectArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelect<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>