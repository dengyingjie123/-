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
    <div region='center' border='false' style='padding:10px;background:#fff;border:0px solid #ccc;'>
    <div class="easyui-panel" style="padding:2px;" title="用户查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td><input type="text" id="search_user_name<%=token %>" name="user.name" style="width:100px;" /></td>
                <td>电话</td>
                <td><input type="text" id="search_user_mobile<%=token %>" name="user.mobile" style="width:100px;" /></td>
                <td>身份号码</td>
                <td><input type="text" id="search_user_idnumber<%=token %>" name="user.idnumber" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchUser<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetUser<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br />
    <div class="easyui-panel" style="padding:0px;" border="false" noheader="true"  style='overflow:auto;'>
        <table id="userTable<%=token%>"></table>
    </div>
	<br />  </div>
	<div id="userSelectionArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectUserSelection<%=token%>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">选择</a>
        <a id="btnCancelUserSelection<%=token%>" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)">取消</a>

    </div>
</div>
</body>
</html>