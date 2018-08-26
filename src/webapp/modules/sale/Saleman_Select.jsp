<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");
    ButtonPO btnAdd = new ButtonPO("btnAddSalesman" + token, "添加", "icon-add");
    ButtonPO btnEdit = new ButtonPO("btnUpdateSalesman" + token, "修改", "icon-edit");
    ButtonPO btnDelete= new ButtonPO("btnDeleteSalesman" + token, "删除", "icon-cut");
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDelete);
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

    <div class="easyui-panel" style="padding:2px;" title="销售人员查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>名称</td>
                <td><input type="text" id="search_salesman_name<%=token %>" name="user.name" style="width:100px;" /></td>
                <td>
                    <a id="btnSearchSalesman_select<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchResetSalesman_select<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br/>
        <table id="SelectsalesManTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectSalesmanSelection<%=token%>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">选择</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('SelectSaleManWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>