<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.po.oa.finance.FinanceExpenseDetailPO" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%@ page import="com.youngbook.entity.vo.report.PaymentPlanReportVO" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.service.sale.PaymentPlanService" %>
<%
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    String time = new String();
    if (!StringUtils.isEmpty(year)) {
        time += year;
    }
    if (!StringUtils.isEmpty(month)) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        time += month;
    }
    PaymentPlanService service = new PaymentPlanService();
    List<PaymentPlanReportVO> list = service.getPaymentPlanMonthlyReport(time);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1099/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>兑付计划报表<%=time%></title>

    <style>
        td {
            border: 1px black solid;
        }
    </style>

</head>
<body>
<div>
    <table border=0 cellpadding=0 cellspacing=0 class="tables" width="1920" style="font-size: 12px;" >
        <tr>
            <td>身份证号</td>
            <td>客户名称</td>
            <td>银行</td>
            <td>卡号</td>
            <td>产品名称</td>
            <td>认购金额</td>
            <td>预期收益率（年化）</td>
            <td>认购日期</td>
            <td>起息日</td>
            <td>兑付日</td>
            <td>兑付本金</td>
            <td>兑付利息</td>
            <td>状态</td>
        </tr>
<%
    if (list != null) {
        for (int i = 0; i < list.size(); i++) {
            PaymentPlanReportVO vo = list.get(i);
            vo.setCustomerNumber(AesEncrypt.decrypt(vo.getCustomerNumber()));
            vo.setBankNumber(AesEncrypt.decrypt(vo.getBankNumber()));
%>
        <tr>
            <td><%=vo.getCustomerNumber()%>*</td>
            <td><%=vo.getCustomerName()%></td>
            <td><%=vo.getBank()%></td>
            <td><%=vo.getBankNumber()%>*</td>
            <td><%=vo.getProductionName()%></td>
            <td><%=vo.getMoney()%></td>
            <td><%=vo.getExpectedYield()%></td>
            <td><%=vo.getPayTime()%></td>
            <td><%=vo.getValueDate()%></td>
            <td><%=vo.getPaymentTime()%></td>
            <td><%=vo.getPrincipalMoney()%></td>
            <td><%=vo.getProfitMoney()%></td>
            <td><%=vo.getPaymentStatus()%></td>
        </tr>
<%
        }
    }
%>
    </table>
</div>

</body>
</html>
