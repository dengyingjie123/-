<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%
    String token = request.getParameter("token");
%>

<%
    // 创建需要权限控制的按钮
    ButtonPO btnShowDetail = new ButtonPO("btnShowDetail" + token, "显示详情", "icon-search", "合同调配管理-显示详情");
    ButtonPO btnSet = new ButtonPO("btnSet" + token, "整套合同状态设置", "icon-edit", "合同调配管理-合同状态设置");
    ButtonPO btnReceive = new ButtonPO("btnReceive" + token, "整套签收", "icon-edit", "合同调配管理-合同签收");
    ButtonPO btnTake = new ButtonPO("btnTake" + token, "整套领用", "icon-edit", "合同调配管理-合同领用");
    ButtonPO btnBackReceive = new ButtonPO("btnBackReceive" + token, "整套回寄签收", "icon-edit", "合同调配管理-回寄签收");
    ButtonPO btnRetake = new ButtonPO("btnRetake" + token, "整套合同重领", "icon-edit", "合同调配管理-合同重领");
    ButtonPO btnSendBack = new ButtonPO("btnSendBack" + token, "整套回寄合同", "icon-edit", "合同调配管理-回寄合同");
    ButtonPO btnAdd = new ButtonPO("btnAdd" + token, "添加合同", "icon-add", "合同调配管理-添加合同");
    // 创建Toolbar，并受权限控制
    ToolbarPO toolbar = ToolbarPO.getInstance(request);
    // 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
    toolbar.addButton(btnAdd);
    toolbar.addButton(btnShowDetail);
    toolbar.addButton(btnSet);
    toolbar.addButton(btnReceive);
    toolbar.addButton(btnTake);
    toolbar.addButton(btnRetake);
    toolbar.addButton(btnSendBack);
    toolbar.addButton(btnBackReceive);
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
                <td>名称
                    <input type="text" id="search_Name<%=token %>" style="width:400px;"/></td>
                <td>合同状态
                    <input type="text" id="search_Type<%=token %>" class="easyui-combotree" style="width:100px;"/></td>

                <td>合同流转状态
                    <input type="text" id="search_ContractApplication_State<%=token%>" class="easyui-combotree"
                           style="width: 100px"/></td>
                <td>
                    <a id="btnSearchSubmit<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
            <tr>
                <td colspan="10">回寄时间：
                    <input type="text" id="search_BackReceiveTime_Start<%=token %>" class="easyui-datebox"
                           editable="false" style="width:130px;"/>至
                    <input type="text" id="search_BackReceiveTime_End<%=token %>" class="easyui-datebox"
                           editable="false" style="width:130px;"/>&nbsp;
                    签收时间：
                    <input type="text" id="search_ReceiveTime_Start<%=token %>" class="easyui-datebox" editable="false"
                           style="width:130px;"/>至
                    <input type="text" id="search_ReceiveTime_End<%=token %>" class="easyui-datebox" editable="false"
                           style="width:130px;"/>&nbsp;
                    领用时间：
                    <input type="text" id="search_TakeTime_Start<%=token %>" class="easyui-datebox" editable="false"
                           style="width:130px;"/>至
                    <input type="text" id="search_TakeTime_End<%=token %>" class="easyui-datebox" editable="false"
                           style="width:130px;"/>
                </td>

            </tr>
        </table>
    </div>
    <br>
    <table id="Table<%=token%>" data-options="toolbar:toolbar"></table>
    <script type="text/javascript">
        var toolbar =
        <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
    </script>

</div>
</body>
</html>