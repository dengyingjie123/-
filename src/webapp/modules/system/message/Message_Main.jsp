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

<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>主题</td>
                <td><input type="text"  id="search_Title<%=token %>" style="width:150px;" /></td>

                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SubmitterTime_Start<%=token %>"
                           style="width:150px;" editable="false"/></td>
                <td>结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SubmitterTime_End<%=token %>"
                           style="width:150px;" editable="false"/></td>

                <td>发布者</td>
                <td><input type="text"  id="search_SenderName<%=token %>" style="width:150px;" /></td>

                <td>
                    <a id="btnSearchArticle<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetArticle<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>

    <a id="btnSystemMessageAdd<%=token %>" style="margin-top:10px;" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" >新建</a>

    <div style='height: 500px;width:auto;margin-top:10px;'>
        <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
            <div title='已收到' style='padding:5px'>
                <table id="ArticleTable<%=token%>"></table>
            </div>
            <div title='已发送' style='padding:5px'>
                <table id="PublishedTable<%=token%>"></table>
            </div>
            <div title='草稿' style='padding:5px'>
                <table id="DraftTable<%=token%>"></table>
            </div>
        </div>

    </div>


</div>
</body>
</html>