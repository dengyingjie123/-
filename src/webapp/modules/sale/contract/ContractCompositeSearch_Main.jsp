<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%--
  Created by IntelliJ IDEA.
  User: yux
  Date: 2016/6/28
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String token = request.getParameter("token");

    ButtonPO btnListContractRouteList= new ButtonPO("btnListContractRouteList" + token, "查看合同流转", "icon-edit");
    ButtonPO btnShowContractAbstractByProduction = new ButtonPO("btnShowContractAbstractByProduction"+token, "产品分期-合同摘要", "icon-edit");
    ButtonPO btnShowContractAbstractByProductionHome = new ButtonPO("btnShowContractAbstractByProductionHome"+token, "产品-合同摘要", "icon-edit");

    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnListContractRouteList);
    toolbar.addButton(btnShowContractAbstractByProduction);
    toolbar.addButton(btnShowContractAbstractByProductionHome);


%>
<html>
<head>
    <title>销售合同调配</title>
</head>
<body>
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>&emsp;&emsp;&emsp;合同号</td>
                <td><input type="text" id="search_ContractNo<%=token %>" style="width:150px;"/></td>
                <td>&emsp;&emsp;合同状态</td>
                <td><input type="text" class="easyui-combotree" id="search_Status<%=token %>" editable="false" style="width:150px;"/></td>

                <td>&emsp;&emsp;客户</td>
                <td><input type="text" id="search_CustomerName<%=token %>" style="width:150px;"/></td>
            </tr>
        </table>

        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>产品分期名称</td>
                <td><input type="text" id="search_ProductionName<%=token %>" style="width:150px;"/></td>
                <td>&emsp;&emsp;项目名称</td>
                <td><input type="text" class="easyui-combotree" id="search_Project<%=token %>" editable="false" style="width:150px;"/></td>
                <td>财富中心</td>
                <td><input type="text" class="easyui-combotree" id="search_OrgName<%=token %>" editable="false"
                           style="width:150px;"/></td>
            </tr>
        </table>

        <table border="0" cellpadding="3" cellspacing="0">
            <tr>
                <td>签约开始时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigendTime_Start<%=token %>"
                           editable="false"
                           style="width:150px;"/></td>
                <td>&nbsp;签约结束时间</td>
                <td><input type="text" class="easyui-datebox" id="search_SigendTime_End<%=token %>"
                           editable="false"
                           style="width:150px;"/></td>

                <td>&emsp;&emsp;销售</td>
                <td><input type="text" id="search_SalemanName<%=token %>" style="width:150px;"/></td>
                <td>
                    <a id="btnSearchContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>

                <td>
                    <a id="btnResetContract<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="ContractCompositeSearchTable<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>
</div>
</body>
</html>
