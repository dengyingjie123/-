<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 14-12-24
  Time: 上午11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%
    String token = request.getParameter("token") ;
%>

<%
    // 创建需要权限控制的按钮
    ButtonPO btnAdd = new ButtonPO("btnContractApplicationAdd" + token, "添加", "icon-add", "合同申请-添加");
    ButtonPO btnEdit = new ButtonPO("btnEdit" + token, "修改", "icon-edit", "合同申请-修改");
    ButtonPO btnContractApplicationDelete = new ButtonPO("btnContractApplicationDelete" + token, "删除", "icon-cut", "合同申请-删除");
    ButtonPO btnContractApplicationCheck = new ButtonPO("btnContractApplicationCheck" + token, "审核", "icon-edit", "合同申请-审核");
    ButtonPO btnContractApplicationMix = new ButtonPO("btnContractApplicationMix" + token, "调配", "icon-edit", "合同申请-调配");
// 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnEdit);
    toolbar.addButton(btnContractApplicationDelete);
    toolbar.addButton(btnContractApplicationCheck);
    toolbar.addButton(btnContractApplicationMix);
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
                <td>组织</td>
                <td><input type="text" class="easyui-combotree" id="search_Org<%=token %>" style="width:150px;" /></td>
                <td>申请人</td>
                <td><input type="text" id="search_ApplicationUser<%=token %>" style="width:150px;" /></td>
                <td>审核人</td>
                <td><input type="text" id="search_Checke<%=token %>" style="width:150px;" /></td>
                <td>调配人</td>
                <td><input type="text" id="search_Sender<%=token %>" style="width:150px;" /></td>
            </tr>
        </table>
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>产品</td>
                <td><input type="text" id="search_Production<%=token %>" editable="false" style="width:200px;" /></td>
                <td><a href="javascript:void(0)" id="btnProduction<%=token %>" class="easyui-linkbutton" plain="true" iconCls="icon-search"></a></td>
                <td>开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SendTime_Start<%=token %>" editable="false" style="width:150px;" /></td>
                <td>结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SendTime_End<%=token %>" editable="false" style="width:150px;" /></td>

                <td>
                    <a id="btnSearchContractApplication<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetContractApplication<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ContractApplicationTable<%=token%>"  data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>