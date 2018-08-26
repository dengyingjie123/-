<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.service.report.CustomerDailyPaymentReportService" %>
<%@ page import="com.youngbook.entity.vo.report.CustomerDailyPaymentReportVO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.youngbook.common.Database" %>
<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2015/9/7
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%
    Connection conn = Config.getConnection();
    try {

        String time = request.getParameter("time");
        CustomerDailyPaymentReportService customerDailyPaymentReportService = new CustomerDailyPaymentReportService();
        List<CustomerDailyPaymentReportVO> list = customerDailyPaymentReportService.list(time, conn);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>厚币通宝每日客户兑付金额表</title>
</head>
<style>
    td {
        border: 1px solid;
    }

    .tables {
        font-size: 5px;
    }

</style>
<script type="application/javascript">
    function onclicks() {
        window.print();
    }
</script>
<body onload="onclicks()">

<table border=0 cellpadding=0 cellspacing=0 width=800>
    <tr>
        <td colspan="11" style="text-align:center;"><strong>厚币通宝每日客户兑付金额表</strong></td>
    </tr>
    <tr>
        <td align="center">客户名称</td>
        <td align="center">兑付日期</td>
        <td align="center">利息</td>
        <td align="center">本金</td>
        <td align="center">收益</td>
        <td align="center">当前期数</td>
        <td align="center">总期数</td>
        <td align="center">剩余未兑付本金</td>
        <td align="center">剩余未兑付收益</td>
        <td align="center">状态</td>
    </tr>
    <%
        for (int i = 0; i < list.size(); i++) {
    %>
    <tr>
        <td align="center"><%=list.get(i).getCustomerName()%>
        </td>
        <td align="center"><%=list.get(i).getPaymentTime()%>
        </td>
        <td align="center"><%=list.get(i).getTotalProfitMoney()%>
        </td>
        <td align="center"><%=list.get(i).getTotalPaymentMoney()%>
        </td>
        <td align="center"><%=list.get(i).getTotalPaymentPrincipalMoney()%>
        </td>
        <td align="center"><%=list.get(i).getCurrentInstallment()%>
        </td>
        <td align="center"><%=list.get(i).getTotalInstallment()%>
        </td>
        <td align="center"><%=list.get(i).getSurplusPaymentMoney()%>
        </td>
        <td align="center"><%=list.get(i).getSurplusPaymentPrincipalMoney()%>
        </td>
        <td align="center"><%=list.get(i).getStatus() == "958" ? "已兑付" : "未兑付" %>
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="2">&nbsp;&nbsp;总计：</td>
        <td align="center"><%=list.get(0).getSumTotalProfitMoney()%>
        </td>
        <td align="center"><%=list.get(0).getSumTotalPaymentMoney()%>
        </td>
        <td align="center"><%=list.get(0).getSumTotalPaymentPrincipalMoney()%>
        </td>
        <td colspan="5"></td>
    </tr>
</table>
</div>


</body>
</html>
<%
    } catch (Exception e) {
        PrintWriter printWriter = response.getWriter();
        printWriter.write(e.getMessage());
    }
    finally {
        Database.close(conn);
    }
%>