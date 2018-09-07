<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
        /**
         * 系统管理
         */
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Untitled Document</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formCode<%=token %>" name="formCode" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">code</td>
                    <td><input class="easyui-validatebox" type="text" id="code<%=token %>" name="code.code"    style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">创建时间</td>
                    <td><input  type="text" id="createTime<%=token %>" editable="false" class="easyui-datetimebox" name="code.createTime"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">类型</td>
                    <td>
                        <select name="code.type" id="typeName<%=token%>" style="width:200px">
                            <option value="0">类型一</option>
                            <option value="1">类型二</option>
                        </select>
                </tr>
                <tr>
                    <td align="right">有效时间</td>
                    <td><input class="easyui-datetimebox" editable="false"   type="text" id="availableTime<%=token %>"   name="code.availableTime"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">失效时间</td>
                    <td><input class="easyui-datetimebox" editable="false" type="text" id="expiredTime<%=token %>" name="code.expiredTime"    style="width:200px"/></td>
                </tr>


                <tr>
                    <td align="right">使用时间</td>
                    <td><input  class="easyui-datetimebox" editable="false" type="text" id="usedTime<%=token %>" name="code.usedTime"    style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">使用用户名称</td>
                    <td><input  type="text" id="userName<%=token %>" name="userName"    style="width:200px"/></td>
                </tr>

                <tr>
                    <td align="right">使用者IP</td>
                    <td><input type="text" id="iP<%=token %>" name="code.iP"    style="width:200px"/></td>
                </tr>

            </table>
            <input  type="hidden" id="id<%=token %>" name="code.id"    style="width:200px"/>
            <input  type="hidden" id="userId<%=token %>" name="code.userId"    style="width:200px"/>
        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnCodeSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CodeWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
