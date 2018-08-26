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
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
        <form id="formColumn<%=token %>" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" data-options="required:true" type="text" id="column.name<%=token %>" name="column.name"  required="true" missingmessage="必须填写" editable="true" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">父级</td>
                    <td><input class="easyui-combotree" id="parentId<%=token %>" name="column.parentId" editable="true" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">描述</td>
                    <td><input class="easyui-validatebox" data-options="required:true" type="text" id="description<%=token %>" name="column.description"  editable="true" style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="ID<%=token %>" name="column.id" readonly="true"   style="width:200px"/>
            <input  type="hidden" id="ID<%=token %>" name="column.sid" readonly="true"   style="width:200px"/>
        </form>
        <form id="formColumn<%=token %>" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">权限</td>
                    <td><input class="easyui-combotree" id="permissionId<%=token %>" data-options="required:true" style="width:200px" /></td>
                </tr>
            </table>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px 0;">
        <a id="btnSubmitColumn<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('ColumnWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>