<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.vo.oa.finance.FinancePayWFAVO" %>
<%@ page import="com.youngbook.service.oa.finance.FinancePayWFAService" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    FinancePayWFAService service = new FinancePayWFAService();
    FinancePayWFAVO financePayWFAVO = service.getPrintDate(id);
    if (financePayWFAVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1099/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>资金支付</title>
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
<div style="margin-left: 40px;">

<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan="2" style="text-align:center;line-height: 50px;"><b><%=financePayWFAVO.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="2" style="text-align:center;"><b>资金支付审批单</b></td>
    </tr>
    <tr>
        <td style="text-align: center;width: 200px;">资金支付项目</td>
        <td style='text-align: center;'><%=financePayWFAVO.getProjectName() == null ?"":financePayWFAVO.getProjectName()%>　</td>
    </tr>  <tr>
        <td style="text-align: center;width: 200px;">合同编号</td>
        <td style='text-align: center;'><%=financePayWFAVO.getContractNo() == null ?"":financePayWFAVO.getContractNo()%>　</td>
    </tr>
    <tr>
        <td style="text-align: center">合同名称</td>
        <td style='text-align: center;'><%=financePayWFAVO.getContractName()  == null ?"":financePayWFAVO.getContractName()%>
            　
        </td>
    </tr>
    <tr>
        <td style="text-align: center">合同金额（元）</td>
        <td style='text-align: center;'><%=NumberToCNUtils. numberTo2(financePayWFAVO.getContractMoney())+"&nbsp;&nbsp;&nbsp;"+NumberToCNUtils.number2CNMontrayUnit(financePayWFAVO.getContractMoney())%></td>
    </tr>
    <tr>
        <td style="text-align: center">累计已支付金额（元）</td>
        <td style='text-align: center;'><%=NumberToCNUtils.numberTo2(financePayWFAVO.getPaidMoney())+"&nbsp;&nbsp;&nbsp;"+NumberToCNUtils.number2CNMontrayUnit(financePayWFAVO.getPaidMoney()) %></td>
    </tr>
    <tr>
        <td style="text-align: center">本次支付金额（元）
        </td>
        <td style='text-align: center;'><%=NumberToCNUtils.numberTo2(financePayWFAVO.getMoney())+"&nbsp;&nbsp;&nbsp;"+NumberToCNUtils.number2CNMontrayUnit(financePayWFAVO.getMoney())%></td>

    </tr>
    <tr>
        <td style="text-align: center">收款方名称</td>
        <td style='text-align: center;'><%=financePayWFAVO.getPayeeName()  == null ?"":financePayWFAVO.getPayeeName()%>　</td>
    </tr>
    <tr>
        <td style="text-align: center">收款方开户行</td>
        <td style='text-align: center;'><%=financePayWFAVO.getPayeeBankName()  == null ?"":financePayWFAVO.getPayeeBankName()%>　</td>
    </tr>
    <tr>
        <td style="text-align: center">收款方帐号</td>
        <td style='text-align: center;'><%=financePayWFAVO.getPayeeBankAccount()  == null ?"":financePayWFAVO.getPayeeBankAccount()%>
        </td>
    </tr>
</table>
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan=2 style='height:22.5pt;text-align: center;'><b>签<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>字<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>栏</b></td>
    </tr>
    <tr>
        <td width="200px" style='text-align: center;'>部门负责人意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financePayWFAVO.getDepartmentLeaderContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financePayWFAVO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                    日期：<%=financePayWFAVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;'">总经理：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financePayWFAVO.getGeneralManagerContent()%>
            </div>

            <div style="float:right;text-align: right;">
                    姓名：<%=financePayWFAVO.getGeneralManagerName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getGeneralManagerName() + "&nbsp;&nbsp;"%>
                    日期：<%=financePayWFAVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;'>会计审核意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financePayWFAVO.getAccountingContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financePayWFAVO.getAccountingName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getAccountingName() + "&nbsp;&nbsp;"%>
                    日期：<%=financePayWFAVO.getAccountingTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getAccountingTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>

    </tr>
    <tr>
        <td style='text-align: center;margin-left: 10px;'>财务总监意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financePayWFAVO.getFinanceDirectorContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financePayWFAVO.getFinanceDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getFinanceDirectorName() + "&nbsp;&nbsp;"%>
                    日期：<%=financePayWFAVO.getFinanceDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getFinanceDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

        <tr>
            <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
            <td style=''>
                <div style="float:left;text-align: left;margin-left: 10px;">
                    <%=financePayWFAVO.getExecutiveDirectorContent()%>
                </div>

                <div style="text-align: right;">
                        姓名：<%=financePayWFAVO.getExecutiveDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                        日期：<%=financePayWFAVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

                </div>
            </td>
        </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">出纳意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financePayWFAVO.getCashierContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financePayWFAVO.getCashierName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getCashierName() + "&nbsp;&nbsp;"%>
                    日期：<%=financePayWFAVO.getCashierTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getCashierTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style='border-left:none ;text-align:right;line-height: 40px;'>
            经办人:<%=financePayWFAVO.getSubmitterName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getSubmitterName()+"&nbsp;&nbsp;"%>
            日期：<%=financePayWFAVO.getApplicantTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;" : financePayWFAVO.getApplicantTime().substring(0,10)+"&nbsp;&nbsp;"%>

        </td>
    </tr>

</table>
NO: <%=financePayWFAVO.getControlString3()%>

</div>
</body>
</html>