<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-4-7
  Time: 上午 11:09
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" import="com.youngbook.common.config.*" %>
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
                <td style="text-align: right">客户名称</td>
                <td><input type="text" id="search_name<%=token %>" style="width:100px;" /></td>
                <td style="text-align: right">信息</td>
                <td><input type="text" id="search_info<%=token %>" style="width:100px;" /></td>
                <td style="text-align: right">发送时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_SendTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td style="text-align: right">发送时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_SendTime_End<%=token %>" style="width:100px;" editable="false" /></td>
            </tr>
            <tr>
                <td style="text-align: right">过期时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_ExpiredTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td style="text-align: right">过期时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_ExpiredTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td style="text-align: right">认证时间（开始时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_AuthenticationTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td style="text-align: right">认证时间（结束时间）</td>
                <td><input type="text" class="easyui-datebox" id="search_AuthenticationTime_End<%=token %>" style="width:100px;" editable="false" /></td>
            </tr>
            <tr>
                <td style="text-align: right">状态</td>
                <td><input class="easyui-combobox"  editable="false" type="text" id="search_status<%=token %>" style="width:100px"/></td>
                <td style="text-align: right">发送类型</td>
                <td><input class="easyui-combobox"  editable="false" type="text" id="search_sendType<%=token %>" style="width:100px"/></td>
                <td> </td>
                <td>
                    <a id="btnSearchAuthCode<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a> <a id="btnResetAuthCode<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="AuthCodeTable<%=token%>"></table>

</div>
</body>
</html>
