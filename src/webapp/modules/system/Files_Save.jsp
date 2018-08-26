<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/3
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
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
        <form id="formFilesWindow<%=token %>" name="formFilesWindow" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">中文名</td>
                    <td><input class="easyui-validatebox" type="text" id="name<%=token %>" name="files.name" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">描述</td>
                    <td><input class="easyui-validatebox" type="text" id="description<%=token %>" name="files.description" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">文件名</td>
                    <td><input class="easyui-validatebox" type="text" id="fileName<%=token %>" name="files.fileName" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">扩展名</td>
                    <td><input class="easyui-validatebox" type="text" id="extensionName<%=token %>" name="files.extensionName" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">大小</td>
                    <td><input class="easyui-validatebox" type="text" id="size<%=token %>" name="files.size" validType="number"  required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">保存路径</td>
                    <td><input class="easyui-validatebox" type="text" id="path<%=token %>" name="files.path" required="true" missingmessage="必须填写" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">创建时间</td>
                    <td><input type="text" class="easyui-datebox" id="createTime<%=token %>" name="files.createTime" style="width:200px;" editable="false" required="true" missingmessage="必须填写"/></td>
                </tr>
                <tr>
                    <td align="right">校验码</td>
                    <td><input class="easyui-validatebox" type="text" id="checkCode<%=token %>" name="files.checkCode" readonly="readonly" style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">所属模块描述</td>
                    <td><input class="easyui-validatebox" type="text" id="moduleDescription<%=token %>" name="files.moduleDescription"  style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">操作员</td>
                    <td><input type="text" id="operatorName<%=token %>" name="files.operatorName" readonly="readonly" style="width:200px"/></td>
                </tr>

            </table>
            <input type="hidden" id="operatorId<%=token %>" name="files.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="files.sid"    style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="files.id"    style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="files.operateTime"  style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="files.state" style="width:200px"/>
            <input type="hidden" id="state<%=token %>"  name="files.moduleId"style="width:200px"/>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnFilesSubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >确定</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('FilesWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>

