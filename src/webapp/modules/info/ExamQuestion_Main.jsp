<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 4/13/2015
  Time: 2:47 PM
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
    ButtonPO btnAdd = new ButtonPO("btnExamQuestionAdd" + token, "添加", "icon-add", "培训管理-试题-新增");
    ButtonPO btnEdit = new ButtonPO("btnExamQuestionEdit" + token, "修改", "icon-edit","培训管理-试题-修改");
    ButtonPO btnDel = new ButtonPO("btnExamQuestionDelete" + token, "删除", "icon-cut","培训管理-试题-删除");
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
                <td>试卷</td>
                <td><input type="text" class="easyui-combotree" id="search_PaperId<%=token %>" style="width:150px;" editable="false" /></td>
                <td>问题</td>
                <td><input class="easyui-validatebox" type="text" id="search_Question<%=token %>"/></td>
                <td>类型</td>
                <td><input type="text" class="easyui-combotree" id="search_TypeName<%=token %>" style="width:80px;" editable="false" /></td>
                <td>
                    <a id="btnExamQuestionSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnExamQuestionSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ExamQuestionTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
