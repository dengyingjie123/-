<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/4/22
  Time: 11:07
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
    ButtonPO btnAdd = new ButtonPO("btnAdImageAdd" + token, "添加", "icon-add", "内容管理-图片广告-新增");
    ButtonPO btnEdit = new ButtonPO("btnAdImageEdit" + token, "修改", "icon-edit","内容管理-图片广告-修改");
    ButtonPO btnDel = new ButtonPO("btnAdImageDelete" + token, "删除", "icon-cut","内容管理-图片广告-删除");
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
                <td>启用时间</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_StartTime_End<%=token %>" style="width:100px;" editable="false" /></td>

                <td>停用时间</td>
                <td><input type="text" class="easyui-datebox" id="search_EndTime_Start<%=token %>" style="width:100px;" editable="false" /></td>
                <td>至</td>
                <td><input type="text" class="easyui-datebox" id="search_EndTime_End<%=token %>" style="width:100px;" editable="false" /></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            <tr>

                <td align="right">名称</td>
                <td><input type="text" id="search_Name<%=token %>" name="adImage.name" style="width:100px;" /></td>
                <td align="right">描述</td>
                <td><input type="text" id="search_Description<%=token %>" name="adImage.description" style="width:100px;" /></td>
                <td align="right">归类编号</td>
                <td><input type="text" id="search_CatalogId<%=token %>" class="easyui-combotree" name="adImage.catalogId" style="width:100px;" /></td>
                <td align="right">是否使用</td>
                 <td><input type="text" id="search_isAvaliable<%=token %>" name="adImage.isAvaliable"  class="easyui-combotree" style="width:100px;" /></td>
            <td>
                <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td>
            </tr>

        </table>
    </div>
    <br>
    <table id="AdImageTable<%=token%>"  data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
