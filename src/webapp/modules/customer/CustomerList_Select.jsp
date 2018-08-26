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
    <div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
        <div id="selectedCustomer<%=token%>" class="easyui-tabs" style="width:auto;height:auto">
            <div id="customerPersonal<%=token%>" title="个人客户" style="padding:2px;">
                <div class="easyui-panel" title="查询" iconCls="icon-search">
                    <table border="0" cellpadding="3" cellspacing="0">
                        <tr>
                            <td>姓名</td>
                            <td><input type="text"  id="search_Name<%=token %>" style="width:140px;" /></td>
                            <td>移动电话</td>
                            <td><input type="text" id="search_Mobile<%=token %>" style="width:80px;" /></td>
                            <td>地址</td>
                            <td><input type="text" id="search_Address<%=token %>" style="width:90px;" /></td>
                            <%--<td>证件号</td>--%>
                            <%--<td><input type="text" id="search_Certificate<%=token %>" style="width:90px;" /></td>--%>
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
                <table id="CustomerPersonalTable<%=token%>"></table>

                <%--<div  style="height:180px">--%>
                    <%--<div id="personalTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">--%>
                        <%--<div  title="账户列表"  style="padding:5px ;" >--%>
                            <%--<table id="CustomerPersonalAccountTable<%=token%>" ></table>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
            <div id="customerInstitution<%=token%>" title="机构客户" style="padding:2px;">
                <div class="easyui-panel" title="查询" iconCls="icon-search">
                    <table border="0" cellpadding="3" cellspacing="0">
                        <tr>
                            <td>姓名</td>
                            <td><input type="text"  id="search_iName<%=token %>" style="width:140px;" /></td>
                            <td>移动电话</td>
                            <td><input type="text" id="search_iMobile<%=token %>" style="width:80px;" /></td>
                            <td>注册地址</td>
                            <td><input type="text" id="search_iAddress<%=token %>" style="width:90px;" /></td>
                            <%--<td>证件号</td>--%>
                            <%--<td><input type="text" id="search_iCertificate<%=token %>" style="width:90px;" /></td>--%>
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
                <table id="CustomerInstitutionTable<%=token%>"></table>
                <%--<div  style="height:180px">--%>
                    <%--<div id="institutionTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">--%>
                        <%--<div  title="账户列表"  style="padding:5px ;" >--%>
                            <%--<table id="CustomerInstitutionAccountTable<%=token%>" ></table>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnSelectedCustomer<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >选择</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('CustomerSelectWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>