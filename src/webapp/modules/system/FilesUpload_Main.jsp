<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
    String token = request.getParameter("token");

    String isSingleFileString = request.getParameter("single");

    boolean isSingleFile = false;

    if (!StringUtils.isEmpty(isSingleFileString) && isSingleFileString.equals("1")) {
        isSingleFile = true;
    }
%>
<html>
<head>
    <title></title>
</head>
<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc; display:<%=isSingleFile?"none":""%>" >
        <div class="easyui-panel" title="查询" iconCls="icon-search">
            <table border="0" cellpadding="3" cellspacing="0">
                <tr>
                    <td>文件名</td>
                    <td><input type="text" id="search_FileName<%=token %>" style="width:100px;" editable="false"/></td>

                    <td>扩展名</td>
                    <td><input type="text" id="search_ExtensionName<%=token %>" style="width:50px;" editable="false"/>
                    </td>

                    <td>创建开始时间</td>
                    <td><input type="text" class="easyui-datebox" id="search_CreateTime_Start<%=token %>"
                               style="width:90px;" editable="false"/></td>
                    <td>创建结束时间</td>
                    <td><input type="text" class="easyui-datebox" id="search_CreateTime_End<%=token %>"
                               style="width:90px;" editable="false"/></td>
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
                        <td>文件名： <input type="text" name="name" id="name<%=token%>"></td>
                        <td><input type="file" name="upload" id="upload<%=token%>" /></td>
                        <td><a id="btnFilesUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">提交</a></td>
                    </tr>

                </table>
            </form>
        </div>
        <br>
        <table id="FilesTable<%=token%>"></table>
        <br>
        <div><a id="btnViewHelp<%=token%>" class="easyui-linkbutton" href="javascript:void(0)">查看帮助</a></div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="FilesUploadCloseBtn<%=token%>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">确定</a>
    </div>
</div>

</body>
</html>