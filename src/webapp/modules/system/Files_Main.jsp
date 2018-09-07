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
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:3px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>文件名</td>
                <td><input type="text" id="search_FileName<%=token %>" style="width:150px;" editable="false"/></td>

                <td>扩展名</td>
                <td><input type="text" id="search_ExtensionName<%=token %>" style="width:150px;" editable="false"/></td>

                <td>创建开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_CreateTime_Start<%=token %>"
                           style="width:90px;" editable="false"/></td>
                <td>创建结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_CreateTime_End<%=token %>" style="width:90px;"
                           editable="false"/></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>

    </div>
    <br/>

    <div class="easyui-panel" title="文件上传" iconCls="icon-add">
        <form id="formContractUpload<%=token%>" enctype="multipart/form-data" method="post">
            <table>
                <tr>
                    <td><input type="file" name="upload" id="upload<%=token%>"/></td>
                    <td><a id="btnFilesUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">提交</a></td>
                </tr>
            </table>
        </form>
    </div>
    <br>
    <table id="FilesTable<%=token%>"></table>

</div>

</body>
</html>