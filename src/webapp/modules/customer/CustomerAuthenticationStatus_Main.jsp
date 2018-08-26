<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/4/7
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token") ;
%>
<html>

<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>客户名称</td>
                <td><input type="text" id="search_CustomerName<%=token %>" name="customerAuthenticationStatusVO.customerName" style="width:100px;" /></td>
                <td>手机认证状态</td>
                <td><input id="search_Mobile_Status<%=token%>" class="easyui-combotree" style="width:90px;"/></td>
                <td>邮箱认证状态</td>
                <td><input id="search_Email_Status<%=token%>" class="easyui-combotree" style="width:90px;"/></td>
                <td>安全问题状态</td>
                <td><input id="search_QA_Status<%=token%>" class="easyui-combotree" style="width:90px;"/></td>
                <td>账户认证状态</td>
                <td><input id="search_Account_Status<%=token%>" class="easyui-combotree" style="width:90px;"/></td>

                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerAuthenticationStatusTable<%=token%>"></table>
</div>
</body>
</html>
