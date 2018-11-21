<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 10/16/14
  Time: 11:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.common.easyui.entity.MenuToolbar" %>
<%@ page import="com.youngbook.common.easyui.entity.IconStyle" %>

<%
    String token = request.getParameter("token") ;

    int menuToolbarIndex = 1;
    MenuToolbar menuToolbar = new MenuToolbar("toolbar1"+token, "订单管理");
    menuToolbar.newMenuButton("Sale"+token, "下单", IconStyle.ICON_005, menuToolbarIndex++)
            .addButton("btnOrderAppointment"+token, "预约", IconStyle.BLANK, 1, "订单管理_订单管理_预约")
//            .addButton("btnOrderSaleAndWaiting" + token, "下单", IconStyle.BLANK, 2, "订单管理2_订单管理2_添加")
            .addButton("btnOrderEdit" + token, "打款", IconStyle.BLANK, 2, "订单管理2_订单管理2_修改")
            .addButton("btnOrderSaleAndWaitingCancel"+token, "预约作废", IconStyle.BLANK, 3, "订单管理2_订单管理2_作废");


//    menuToolbar.newMenuButton("Appointment"+token, "预约", IconStyle.ICON_003, menuToolbarIndex++)
//            .addButton("btnOrderAppointment" + token, "预约", IconStyle.BLANK, 1, "订单管理2_订单管理2_预约")
//            .addButton("btnOrderAppointmentCancel"+token, "取消预约", IconStyle.BLANK, 2, "订单管理2_订单管理2_预约");


    int financeButtonIndex = 1;
    menuToolbar.newMenuButton("Finance" + token, "财务操作", IconStyle.ICON_059, menuToolbarIndex++)
            .addButton("btnFinanceMoneyConfirm" + token, "日终扎帐确认", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务核对")
            .addButton("btnFinanceMoneyConfirmCancel" + token, "日终扎帐取消", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务核对")
            .addButton("btnFinanceConfirm01" + token, "财务一次核对", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务核对")
            .addButton("btnFinanceConfirm02" + token, "财务二次核对", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务二次核对")
            .addButton("btnExportExcel" + token, "导出Excel", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_订单导出")
            .addButton("btnOrderTransfer"+token, "产品转让", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务核对")
            .addButton("btnOrderPayback"+token, "产品兑付", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_财务核对")
            .addButton("btnMoneyTransfer2Gongda" + token, "确认并转账到公达", IconStyle.BLANK, financeButtonIndex++, "销售管理_订单管理_转账到公达");


//    menuToolbar.newMenuButton("CallCenter"+token,  "客服操作", IconStyle.ICON_100,menuToolbarIndex++)
//            .addButton("btnOrderFeedback1" + token, "第一次回访", IconStyle.BLANK, 1, "订单管理_第一次回访")
//            .addButton("btnOrderFeedback2" + token, "第二次回访", IconStyle.BLANK, 2, "订单管理_第二次回访");




    menuToolbar.newMenuButton("Basic"+token,  "基本操作", IconStyle.ICON_001, menuToolbarIndex++)
            .addButton("btnOrderDetailShow" + token, "订单明细", IconStyle.BLANK, 1, "")
            .addButton("btnOrderContractSigned" + token, "合同签约", IconStyle.BLANK, 2, "销售管理_订单管理_合同签约")
            .addButton("btnOrderContractCancelSign" + token, "取消合同签约", IconStyle.BLANK, 3,"销售管理_订单管理_取消合同签约")
            .addButton("btnOrderSaveReferralCode"+token, "修改推荐码", IconStyle.BLANK, 4,"销售管理_订单管理_修改推荐码")
            .addButton("btnOrderGeneratePaymentPlan"+token, "生成兑付计划", IconStyle.BLANK, 5,"销售管理_订单管理_生成兑付计划")
            .addButton("btnOrderEditProduction"+token, "修改订单产品", IconStyle.BLANK, 6,"销售管理_订单管理_修改订单产品");

    /**
     * 通联金融圈菜单
     */
    int allinpayCircleButtonIndex = 1;
    menuToolbar.newMenuButton("allinpayCircle"+token,  "通联金融生态圈", IconStyle.ICON_001, menuToolbarIndex++)
            .addButton("btnAllinpayCircle_DepositByInstitution" + token, "确认充值", IconStyle.BLANK, allinpayCircleButtonIndex++, "")
            .addButton("btnAllinpayCircle_payByShare" + token, "份额支付", IconStyle.BLANK, allinpayCircleButtonIndex++, "");
%>
<html>
<head>
    <title></title>
</head>
<body>

<div class="easyui-panel" title="查询" iconCls="icon-search">
    <table border="0" cellpadding="3" cellspacing="0">
        <tr>
            <td>订单号</td>
            <td><input type="text" id="search_OrderNum<%=token %>" style="width:100px;" /></td>
            <td>客户</td>
            <td><input type="text" id="search_Customer<%=token %>" style="width:100px;" /></td>
            <td>产品</td>
            <td><input type="text" id="search_Product<%=token %>" style="width:100px;" /></td>
            <td>推荐码</td>
            <td><input type="text" id="search_ReferralCode<%=token %>" style="width:100px;" /></td>
            <td align="right">状态</td>
            <td><input  class="easyui-combotree" id="search_status<%=token %>" editable="false" style="width:150px"/></td>
            <td>
                <a id="btnSearchOrder<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </td>
            <td>
                <a id="btnResetOrder<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut">重置</a>
            </td>
        </tr>
        <tr>
            <td>打款时间起</td>
            <td><input type="text" class="easyui-datebox" id="search_PayTime_Start<%=token %>" style="width:100px;" editable="false"/></td>
            <td>打款时间止</td>
            <td><input type="text" class="easyui-datebox" id="search_PayTime_End<%=token %>" style="width:100px;" editable="false"/></td>
            <td>理财经理</td>
            <td><input type="text" id="search_salesman<%=token %>" style="width:100px;" /></td>
            <td>&nbsp;</td>
            <td><a id="btnSearchOrderSaleAndWaiting<%=token %>" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">日终扎帐</a></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
</div>
<br/>
<%=menuToolbar.printHtml(request)%>
<table id="OrderTable<%=token%>"></table>

</body>
</html>