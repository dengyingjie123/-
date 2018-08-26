<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.wvo.sale.report.DailySalesWVO" %>
<%@ page import="com.youngbook.service.sale.report.DailySalesService" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>

<%
    String token = request.getParameter("token");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String beginDate = request.getParameter("beginDate");
    String endDate = request.getParameter("endDate");
    if(beginDate == null || "".equals(beginDate)) {
        beginDate = simpleDateFormat.format(new Date());
    }
    if(endDate == null || "".equals(endDate)) {
        endDate = simpleDateFormat.format(new Date());
    }

    Connection conn = Config.getConnection();
    try {
        DailySalesService service = new DailySalesService();
        List<DailySalesWVO> printDataList = service.getPrintData4W(beginDate + " 00:00:00", endDate + " 23:59:59", conn);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>厚币通宝每日销售额表</title>
</head>

<style>
    table, td {
        border: 1px solid;
    }

    td {
        padding: 10px;
        text-align: center;
    }

    .num {
        text-align: right;
    }
</style>

<body onload="doPrint();">

<table border=0 cellpadding=0 cellspacing=0 width=710>

    <tr>
        <td colspan="5"><h2>厚币通宝每日销售额表</h2></td>
    </tr>

    <tr>
        <td colspan="5"><strong>日期：<%=beginDate%></strong></td>
    </tr>

    <tr>
        <td><strong>产品</strong></td>
        <td width="45"><strong>销售</strong></td>
        <td width="45"><strong>配额</strong></td>
        <td width="45"><strong>当天销售比例</strong></td>
        <td width="45"><strong>累计销售比例</strong></td>
    </tr>

        <%
            for(DailySalesWVO printData : printDataList) {
        %>
            <tr>
                <td><%=printData.getProductionName()%></td>
                <td class="num"><%=printData.getSaleMoney()%></td>
                <td class="num"><%=printData.getSize()%></td>
                <td class="num"><%=printData.getDaySalesRatio()%>%</td>
                <td class="num"><%=printData.getTotalSalesRatio()%>%</td>
            </tr>
        <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    %>

</table>

<script type="application/javascript">

    // 调用打印
    function doPrint() {
        window.print();
    }

</script>

</body>
</html>