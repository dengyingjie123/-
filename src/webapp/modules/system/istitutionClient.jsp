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
<div style="padding:5px;">

    <div class="easyui-panel" style="padding:2px;" title="用户查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td><input type="text" id="search_client_name<%=token %>" name="client.name" style="width:100px;" /></td>
                <td>电话</td>
                <td><input type="text" id="search_client_mobile<%=token %>" name="client.mobile" style="width:100px;" /></td>
                <td>身份号码</td>
                <td><input type="text" id="search_client_idnumber<%=token %>" name="client.idnumber" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchClient<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetClient<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br />
    <div class="easyui-panel" style="padding:0px;" border="false" noheader="true">
        <table id="ClientGuanLiTable<%=token%>"></table>
    </div>
    <br />

</div>
</body>
</html>