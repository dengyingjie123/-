<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");

    ButtonPO btnPersonal = new ButtonPO("btnPersonalDistribution" + token, "审核", "icon-edit");
    ButtonPO btnInstitution = new ButtonPO("btnInstitutionDistribution" + token, "审核", "icon-edit");
    ToolbarPO toolbarPersonal = ToolbarPO.getInstance(request);
    ToolbarPO toolbarInstitution = ToolbarPO.getInstance(request);
    toolbarPersonal.addButton(btnPersonal);
    toolbarInstitution.addButton(btnInstitution);

%>
<html>
<head>
    <title></title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
        <div id="selectedCustomer<%=token%>" class="easyui-tabs">
            <div id="customerPersonal<%=token%>" title="个人客户" style="padding:2px;">
                <div class="easyui-panel" title="查询" iconCls="icon-search">
                    <table border="0" cellpadding="3" cellspacing="0">
                        <tr>
                            <td>姓名</td>
                            <td><input type="text"  id="search_personal_Name<%=token %>" style="width:150px;" /></td>
                            <td align="right">审核状态</td>
                            <td><input  class="easyui-combotree" id="search_personal_AuditStatus<%=token+1 %>" editable="false" style="width:100px"/></td>
                            <td>
                                <a id="btnSearchCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </td>
                            <td>
                                <a id="btnResetCustomerPersonal<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <br>
                <table id="CustomerPersonalTable<%=token%>" data-options="toolbar:toolbarPersonal"></table>
                <script type="text/javascript">
                    var toolbarPersonal = <%=toolbarPersonal.toJsonObject().getJSONArray("buttons").toString()%>
                </script>
            </div>
            <div id="customerInstitution<%=token%>" title="机构客户" style="padding:2px;">
                <div class="easyui-panel" title="查询" iconCls="icon-search">
                    <table border="0" cellpadding="3" cellspacing="0">
                        <tr>
                            <td>姓名</td>
                            <td><input type="text"  id="search_institution_Name<%=token %>" style="width:150px;" /></td>
                            <td>移动电话</td>
                            <td><input type="text" id="search_institution_Mobile<%=token %>" style="width:80px;" /></td>
                            <td align="right">审核状态</td>
                            <td><input  class="easyui-combotree" id="search_institution_AuditStatus<%=token %>" editable="false" style="width:100px"/></td>
                            <td>
                                <a id="btnSearchCustomerInstitution<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </td>
                            <td>
                                <a id="btnResetCustomerInstitution<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <br>
                <table id="CustomerInstitutionTable<%=token%>" data-options="toolbar:toolbarInstitution"></table>
                <script type="text/javascript">
                    var toolbarInstitution = <%=toolbarInstitution.toJsonObject().getJSONArray("buttons").toString()%>
                </script>
            </div>
        </div>
    </div>
</div>
</body>
</html>