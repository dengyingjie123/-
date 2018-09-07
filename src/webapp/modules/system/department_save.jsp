<%--
  Created by IntelliJ IDEA.
  User: Jayden
  Date: 14-4-29
  Time: 下午4:42
  To change this template use File | Settings | File Templates.
--%>
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
        <form id="formDepartment<%=token %>" name="formKV" action="" method="post">
            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">名称</td>
                    <td><input class="easyui-validatebox" data-options="required:true" type="text" id="K<%=token %>" name="department.name"  required="true" missingmessage="必须填写" editable="true" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">部门图标</td>
                    <td><input class="easyui-combotree" id="departmentIcon<%=token %>" name="department.icon" editable="true" style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">上级部门</td>
                    <td><input class="easyui-combotree" id="parentId<%=token %>" name="department.parentId" data-options="required:true" style="width:200px" /></td>
                </tr>
                <tr>
                    <td align="right">部门类型</td>
                    <td><input id="Search_Type<%=token%>" class="easyui-combotree" name="department.typeID" data-options="required:true" style="width:200px;"/></td>
                </tr>
                <tr>
                 <td align="right">部门全称</td>
                 <td><input id="Search_Type<%=token%>" class="easyui-validatebox" name="department.fromName"  style="width:200px;"/></td>
                 </tr>
                <tr>
                    <td align="right">排序</td>
                    <td><input  type="text" id="orders<%=token %>" name="department.orders"  required="true" style="width:200px"/></td>
                </tr>
            </table>
            <input  type="hidden" id="ID<%=token %>" name="department.id" readonly="true"   style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px 0;">
        <a id="btnSubmitDepartment<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('DeparmentWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>