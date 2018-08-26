<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/17/14
  Time: 9:21 AM
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
    ButtonPO btnAdd = new ButtonPO("btnArticleAdd" + token, "添加", "icon-add", "内容管理-文章管理-新增");
    ButtonPO btnEdit = new ButtonPO("btnArticleEdit" + token, "修改", "icon-edit","内容管理-文章管理-修改");
    ButtonPO btnArticleContentEdit = new ButtonPO("btnArticleContentEdit" + token, "修改内容", "icon-edit","内容管理-文章管理-修改");
    ButtonPO btnDel = new ButtonPO("btnArticleDelete" + token, "删除", "icon-cut","内容管理-文章管理-删除");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnArticleContentEdit);
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
                <td>标题</td>
                <td><input type="text"  id="search_Title<%=token %>" style="width:150px;" /></td>
                <td>栏目</td>
                <td><input type="text" class="easyui-combotree" id="search_ColumnId<%=token %>" style="width:150px;" /></td>
                <td>
                    <a id="btnSearchArticle<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetArticle<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ArticleTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>

</div>
</body>
</html>