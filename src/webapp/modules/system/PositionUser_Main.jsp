<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 6/2/2015
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%
    String token = request.getParameter("token");
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
                <td>用户名称</td>
                <td>
                    <input type="text" name="PositionUserVO.UserName" id="Search_UserName<%=token %>" style="width:100px"/>
                </td>
                <td>
                    <a id="btnPositionUserSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnPositionUserSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="PositionUserTable<%=token%>"  ></table>
</div>
</body>
</html>