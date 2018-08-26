<%--
  Created by IntelliJ IDEA.
  User: zhouhaihong
  Date: 2015/9/10
  Time: 14:40
  To change this template use File | Settings | File Templates.
  兑付申请页面
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%
    String token = request.getParameter("token");
%>
<html>
<head>
    <title></title>
</head>
<body>

<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;height:350px">

        <form id="formPaymentPlan<%=token %>" name="formPaymentPlan" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0">
                <tr>
                    <td align="right">项目名称</td>
                    <td><input type="text" id="projectName<%=token %>"
                               name="projectName" readonly="readonly" style="width:200px"/></td>
                    <td>产品名称</td>
                    <td><input  type="text" id="productName<%=token %>"
                               name="productName" readonly="readonly"
                               style="width:200px"/></td>
                </tr>
                <tr>
                    <td align="right">兑付时间</td>
                    <td>
                        <input readonly="readonly" type="text" id="paymentTime<%=token %>"
                               name="paymentTime" style="width:200px" readoly="readonlyl"/>
                    </td>
                    <td align="right">兑付总金额</td>
                    <td><input type="text" id="totalPaymentMoney<%=token %>"
                               name="totalPaymentMoney" style="width:200px" readonly="readonly"/>元
                    </td>
                </tr>
            </table>
        </form>
        <div id="contentTabs" class="easyui-tabs" fit="true" border="true" style="overflow:auto;">
            <div title="兑付详情" style="padding:5px ;">
                <table id="PaymentPlanApplyTable<%=token%>" style="height:250px;"></table>
            </div>
        </div>
    </div>


    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnApplaySubmit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
           href="javascript:void(0)">提交</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('PaymentPlanWindow<%=token%>')">取消</a>
    </div>
</div>

</body>
</html>
