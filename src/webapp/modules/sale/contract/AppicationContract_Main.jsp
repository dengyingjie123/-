<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/12/24
  Time: 17:07
  To change this template use File | Settings | File Templates.
  销售合同申请列表页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");
    ButtonPO btnNewApplication = new ButtonPO("btnNewApplication" + token, "新建申请", "icon-add","销售合同申请_销售合同申请_新增");
    ButtonPO btnUpdateApplication = new ButtonPO("btnUpdateApplication" + token, "修改申请", "icon-edit","销售合同申请_销售合同申请_修改");
    ButtonPO btnDeleteApplication= new ButtonPO("btnDeleteApplication" + token, "删除", "icon-cut","销售合同申请_销售合同申请_删除");
    ButtonPO btnCheckApplication= new ButtonPO("btnCheckApplication" + token, "申请审批", "icon-edit","销售合同申请_销售合同申请_审批");
    ButtonPO btnCreateContractNum= new ButtonPO("btnCreateContractNum" + token, "生成合同号", "icon-edit","销售合同申请_销售合同申请_生成合同号");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnNewApplication);
    toolbar.addButton(btnUpdateApplication);
    toolbar.addButton(btnDeleteApplication);
    toolbar.addButton(btnCheckApplication);
    toolbar.addButton(btnCreateContractNum);

%>
<html>
<head>
    <title>销售合同申请</title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>产品名称</td>
                <td><input type="text" id="search_ProductionName<%=token %>" style="width:180px;"/></td>
                <td>申请人</td>
                <td><input type="text" id="search_ApplicationUserName<%=token %>" style="width:180px;"/></td>
                <td>申请开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_ApplicationTime_Start<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>
                <td>申请结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_ApplicationTime_End<%=token %>"
                           editable="false"
                           style="width:180px;"/></td>
            </tr>
        </table>
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>申请状态</td>
                <td><input type="text" class="easyui-combotree" id="search_CheckState<%=token %>" editable="false"
                           style="width:180px;"/></td>

                <td>
                    <a id="btnSearchContractApplication<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnResetContractApplication<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ContractApplicationTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
