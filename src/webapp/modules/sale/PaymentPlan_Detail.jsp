<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
    String orderId = request.getParameter("orderId");
    String confirmorId = request.getParameter("confirmorId");
%>
<html>
<head>
    <title></title>
</head>
<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <table id="PaymentPlanDetailTable<%=token%>"></table>
        <form>
            <input type="hidden" id="orderId<%=token%>" value="<%=orderId%>" />
            <input type="hidden" id="confirmorId<%=token%>" value="<%=confirmorId%>" />
        </form>
    </div>
    <div id="window<%=token%>" region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnPaymentPlanConfirm<%=token%>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确认兑付计划</a>
        <a id="btnWindowClose<%=token%>" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onClick="fwCloseWindow('PaymentPlanWindowDetail<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>