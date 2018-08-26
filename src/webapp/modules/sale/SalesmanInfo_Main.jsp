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
    <div class="easyui-panel" style="padding:2px;" title="销售人员查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td><input type="text" id="search_userName<%=token %>"  style="width:100px;" /></td>
                <td>员工编号</td>
                <td><input type="text" id="search_salesman_staffCode<%=token %>" style="width:100px;" /></td>
                <td>手机号码</td>
                <td><input type="text" id="search_salesman_mobile<%=token %>" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchSalesman<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchResetSalesman<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br />
    <div class="easyui-panel" style="padding:0px;" border="false" noheader="true">
        <table id="salesManTable<%=token%>" ></table>
    </div>
     </div>
	<br />
    <div id="salesmanSelectionArea<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectSalesmanSelection<%=token%>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">选择</a>
    </div>
</div>
</body>
</html>