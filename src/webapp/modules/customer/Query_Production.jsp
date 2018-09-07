<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2015/9/16
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String root=Config.getWebRoot();
%>
<html>
<head>
    <title></title>
</head>
<body>
<div style="padding:5px;" class="easyui-layout" fit="true">
    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>所属产品</td>
                <td><input type="text" class="easyui-combotree" id="search_ProductionName<%=token %>" style="width:150px;" /></td>
                <td>名称</td>
                <td><input type="text"  id="search_ProductionHomeName<%=token%>" style="width:150px;" /></td>

                <td>产品排序</td>
                <td><input type="text"  id="search_Order<%=token%>" class="easyui-combotree" style="width:150px;" /></td>
            </tr><tr>
            <td>状态</td>
            <td><input type="text"  id="search_Status<%=token%>" class="easyui-combotree"  data-options="url:'<%=root%>/modules/production/Production_Status.jsp',method:'get'" style="width:150px;" /></td>
            <td>
                <a id="btnSearchProduction<%=token%>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </td>
            <td>
                <a id="btnResetProduction<%=token%>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td>
        </tr>
        </table>
    </div>
    <br>

    <div>
        <table id="ProductionQueryTable<%=token%>"></table>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('queryWindow<%=token%>')">取消</a>
    </div>

</div>
</body>
</html>
