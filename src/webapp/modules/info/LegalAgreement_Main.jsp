<%--
  Created by IntelliJ IDEA.
  User: Jiangwandong
  Date: 2015/4/1
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token") ;
%>

<%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnAgreementAdd" + token, "添加", "icon-add", "内容管理-法律协议-新增");
    ButtonPO btnEdit = new ButtonPO("btnAgreementEdit" + token, "修改", "icon-edit","内容管理-法律协议-修改");
    ButtonPO btnDel = new ButtonPO("btnAgreementDelete" + token, "删除", "icon-cut","内容管理-法律协议-删除");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnDel);
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
                <td>名称</td>
                <td><input type="text"  id="search_Name<%=token %>" style="width:150px;" editable="false" /></td>
                <td>内容</td>
                <td><input type="text"  id="search_Content<%=token %>" style="width:80px;" editable="false" /></td>
                <td>开始时间</td>
                <td><input type="text" id="search_StartTime_Start<%=token %>" class="easyui-datebox"  style="width:90px;" editable="false" /></td>
                <td>结束时间</td>
                <td><input type="text" id="search_EndTime_End<%=token %>" class="easyui-datebox" style="width:90px;" editable="false" /></td>
                <td>
                    <a id="btnSearchAgreement<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetAgreement<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="LegalAgreementTable<%=token%>"  data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>