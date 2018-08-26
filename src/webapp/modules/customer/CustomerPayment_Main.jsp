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
<div style="padding:5px;">

    <div class="easyui-panel" title="查询" iconCls="icon-search">
        <table border="0" cellpadding="3" cellspacing="0">
            <tr>

                <td>方式</td>
                <td><input type="text" class="easyui-combotree" id="search_typeName<%=token %>" style="width:150px;"
                           editable="false"/></td>
                <td>状态</td>
                <td>
                    <input type="text" class="easyui-combotree" id="search_status<%=token%>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>
                    支付方式
                </td>
                <td>
                    <input type="text" class="easyui-combotree" id="search_paymentTypeName<%=token%>" style="width:150px;"
                           editable="false"/>
                </td>
                <td>操作员</td>
                <td>
                    <input type="text" class="easyui-textbox" id="search_operatorName<%=token%>" style="width:150px;"/>
                </td>
                <td>
                    <a id="btnSearchCustomerPayment<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-search">查询</a>
                </td>
                <td>
                    <a id="btnCustomerPaymentSearchReset<%=token %>" href="javascript:void(0)" class="easyui-linkbutton"
                       iconCls="icon-cut">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <table id="CustomerPaymentTable<%=token%>"></table>

</div>
</body>
</html>
