<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ButtonPO" %>
<%@ page import="com.youngbook.entity.po.jeasyui.ToolbarPO" %>
<%
    String token = request.getParameter("token");
%>
<%
// 创建需要权限控制的按钮
//ButtonPO btnAdd = new ButtonPO("btnPaymentPlanRequestAudit" + token, "提交审核", "icon-add", "兑付计划_兑付计划申请");
//ButtonPO btnEdit = new ButtonPO("btnPaymentPlanDoAudit" + token, "审核", "icon-edit", "兑付计划_兑付计划审核");
ButtonPO btnSendSms = new ButtonPO("btnSendSms" + token, "发送短信", "icon-edit");
//// 创建不需要权限控制的按钮
ToolbarPO toolbar = ToolbarPO.getInstance(request);
toolbar.addButton(btnSendSms);
//// 直接添加按钮，后台程序会自动判断，有权限则添加，没有权限则不添加
//toolbar.addButton(btnAdd);
//toolbar.addButton(btnEdit);
%>

<html>
<head>
    <title></title>
</head>
<body>


<div class="easyui-layout" fit="true">

    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <div class="easyui-panel" title="查询" iconCls="icon-search">
            <table border="0" cellpadding="3" cellspacing="0">
                <tr>
                    <td>兑付类型</td>
                    <td><input type="text" class="easyui-combotree" id="search_Type<%=token %>" style="width:150px;"
                               editable="false"/></td>
                    <td>兑付状态</td>
                    <td><input type="text" class="easyui-combotree" id="search_status<%=token %>" style="width:150px;"
                               editable="false"/></td>
                    <td>开始兑付日期</td>
                    <td><input type="text" class="easyui-datebox" id="search_PaymentTime_Start<%=token %>"
                               style="width:150px;"
                               editable="false"/></td>
                    <td>
                    <td>结束兑付日期</td>
                    <td><input type="text" class="easyui-datebox" id="search_PaymentTime_End<%=token %>"
                               style="width:150px;"
                               editable="false"/></td>

                </tr>
                <tr>
                    <td>订单编号</td>
                    <td><input type="text" class="easyui-textbox" id="search_orderId<%=token %>" style="width:150px;"
                               editable="false"/></td>
                    <td>产品名称</td>
                    <td><input type="text" class="easyui-textbox" id="search_productName<%=token %>" style="width:150px;"
                               editable="false"/></td>
                    <td>客户名称</td>
                    <td><input type="text" class="easyui-textbox" id="search_customerName<%=token %>" style="width:150px;"
                               editable="false"/></td>
                    </tr><tr>
                    <td>
                        <a id="btnSearchPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search">查询</a>
                    <td><a id="btnResetPaymentPlan<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-cut">重置</a>
                    </td>
                    </td>
                </tr>
            </table>
        </div>
        <br/>
        <table id="PaymentPlanGuanLiTable<%=token%>" data-options="toolbar:toolbar"></table>
        <script type="text/javascript">
			var toolbar = <%=toolbar.toJsonObject().getJSONArray("buttons").toString()%>
		</script>

    </div>
    <div id="window<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">

        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('PaymentPlanWindowDetail<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>