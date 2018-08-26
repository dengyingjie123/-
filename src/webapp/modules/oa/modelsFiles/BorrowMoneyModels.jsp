<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.service.oa.borrow.BorrowMoneyService" %>
<%@ page import="com.youngbook.entity.vo.oa.borrow.BorrowMoneyVO" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    BorrowMoneyService service = new BorrowMoneyService();
    BorrowMoneyVO borrow = service.getPrintDate(id);
    if (borrow == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>借款申请</title>
</head>
<style>
    table, td {
        border: 1px solid;
    }
    @media print {
        .NoPrint { display: none; }
    }
</style>
<script type="application/javascript">

    function onclicks() {
        window.print();
    }
</script>
<body >

<input id="but" type="button" class="NoPrint" value="打印" onclick="onclicks()">
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan="2" style="text-align:center;line-height: 50px;"><b><%=borrow.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="2" style="text-align:center;"><b>借款申请</b></td>
    </tr>
    <tr>
        <td align="center">申请人</td>
        <td align="center">
                <%=borrow.getApplicantName() == null ?"":borrow.getApplicantName()%>
        </td>
    </tr>
    <tr>
        <td align="center">申请部门</td>
        <td align="center">
            <%=borrow.getControlString2() == null ?"":borrow.getControlString2()%>
        </td>
    </tr>
    <tr>
        <td align="center">申请时间</td>
        <td align="center">
            <%=borrow.getApplicationTime() == null ?"":borrow.getApplicationTime()%>
        </td>
    </tr>
    <tr>
        <td align="center">申请金额</td>
        <td align="center">
            <%=NumberToCNUtils. numberTo2(Double.parseDouble(borrow.getMoney()))%>
        </td>
    </tr>
    <tr>
        <td align="center">申请金额大写</td>
        <td align="center">
            <%=NumberToCNUtils.number2CNMontrayUnit(Double.parseDouble(borrow.getMoney()))%>
        </td>
    </tr>

    <tr>
        <td align="center">申请用途</td>
        <td align="center">

            <%=borrow.getApplicationPurpose() == null ?"":borrow.getApplicationPurpose()%>
        </td>
    </tr>
    
</table>
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan=2 height=30 class=xl737261 style='height:22.5pt;text-align: center;'><b>签<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>字<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>栏</b></td>
    </tr>
    <tr>
        <td  width="200px" style="text-align: center;margin-left: 10px;">部门负责人意见：</td>
        <td style='border-top:none;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=borrow.getDepartmentLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=borrow.getDepartmentLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getDepartmentLeaderName()+"&nbsp;&nbsp;"%>
                日期：<%=borrow.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getDepartmentLeaderTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>

        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">总经理：</td>
        <td style='border-top:none;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=borrow.getGeneralManagerContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=borrow.getGeneralManagerName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getGeneralManagerName()+"&nbsp;&nbsp;"%>
                日期：<%=borrow.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getGeneralManagerTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;'>会计审核意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=borrow.getAccountingContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=borrow.getAccountingName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getAccountingName() + "&nbsp;&nbsp;"%>
                日期：<%=borrow.getAccountingTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getAccountingTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>

    </tr>
    <tr>
    <td style='text-align: center;margin-left: 10px;'>财务总监意见：</td>
    <td style='border-top:none ;'>
        <div style="float:left;text-align: left;margin-left: 10px;">
            <%=borrow.getFinanceDirectorContent()%>
        </div>

        <div style="float:right;text-align: right;">
            姓名：<%=borrow.getFinanceDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getFinanceDirectorName() + "&nbsp;&nbsp;"%>
            日期：<%=borrow.getFinanceDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getFinanceDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>
        </div>
    </td>
</tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=borrow.getExecutiveDirectorContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=borrow.getExecutiveDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                日期：<%=borrow.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">出纳意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=borrow.getCashierContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=borrow.getCashierName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getCashierName() + "&nbsp;&nbsp;"%>
                日期：<%=borrow.getCashierTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getCashierTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2"  style='border-left:none ;text-align:right;line-height: 40px;'>
            经办人:<%=borrow.getApplicantName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getApplicantName()+"&nbsp;&nbsp;"%>
            日期：<%=borrow.getApplicationTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : borrow.getApplicationTime()+"&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
<br/>
<br/>
NO: <%=borrow.getControlString3()%>

<script type="application/javascript">
    function onclicks() {
        window.print();
    }
</script>
</body>
</html>