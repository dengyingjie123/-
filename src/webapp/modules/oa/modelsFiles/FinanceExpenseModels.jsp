<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.entity.vo.oa.finance.FinanceExpenseVO" %>
<%@ page import="com.youngbook.service.oa.finance.FinanceExpenseService" %>
<%@ page import="com.youngbook.entity.po.oa.finance.FinanceExpenseDetailPO" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    String expenseId = request.getParameter("expenseId");
    FinanceExpenseService service = new FinanceExpenseService();
    FinanceExpenseVO financeExpenseVO = service.getPrintDate(id);
    List<FinanceExpenseDetailPO> DetailList = service.getDetailPrintDate(expenseId);
    if (financeExpenseVO == null) {

    }
    //附件张数大写
    String numberStr ="";
    int accessoryNumber = financeExpenseVO.getAccessoryNumber();
    //判断张数是否为0张
    if(accessoryNumber == 0){
        numberStr="零";
    }else {
        String  initnumberStr =   NumberToCNUtils.number2CNMontrayUnit(accessoryNumber);
        numberStr= initnumberStr.substring(4,initnumberStr.length()-2);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1099/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>费用报销</title>
</head>

<style>
    td {
        border: 1px solid;
    }

    @media print {
        .NoPrint { display: none; }
        .PageNext{PAGE-BREAK-AFTER:always;}
    }
</style>
<script type="application/javascript">

    function onclicks() {
        window.print();
    }
</script>
<body >
<input id="but" type="button" class="NoPrint" value="打印" onclick="onclicks()">

<div style="margin-left: 30px">
<table border=0 cellpadding=0 cellspacing=0 width=710 >
    <tr>
        <td colspan="4" style="text-align:center;line-height: 50px;"><b><%=financeExpenseVO.getControlString1()%>
        </b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>费用报销审批单</b></td>
    </tr>
    <tr>
        <td align="center" style="width: 150px;">部门</td>
        <td align="center">
            <%=financeExpenseVO.getControlString2() == null ?"":financeExpenseVO.getControlString2()%>
        </td>
        <td align="center">报销人</td>
        <td align="center">

            <%=financeExpenseVO.getSubmitterName() == null ?"":financeExpenseVO.getSubmitterName()%>
        </td>
    </tr>
    <tr>
        <td align="center">报销日期</td>
        <td align="center">
            <%=financeExpenseVO.getTime().substring(0, 10)%>
        </td>
        <td align="center">附件张数</td>
        <td align="center">
            <%=numberStr%> 张
        </td>
    </tr>
    <tr>
        <td align="center">总金额</td>
        <td align="center" colspan="3">
            <%=NumberToCNUtils.numberTo2(financeExpenseVO.getMoney())%>
        </td>
    </tr>
    <tr>
        <td align="center">总金额:大写</td>
        <td align="center" colspan="3">
            <%=NumberToCNUtils.number2CNMontrayUnit(financeExpenseVO.getMoney())%>
        </td>
    </tr>

</table>
<table border=0 cellpadding=5 cellspacing=0 width=710 style="font-size: 15px">
    <tr>
        <td colspan=2 style='height:22.5pt;text-align: center;font-size: 15px'><b>签<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>字<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>栏</b></td>
    </tr>
    <tr>
        <td width="200px" style='text-align: center;'>部门负责人意见：</td>
        <td style=''>
            <div style=" float:left;text-align: left;margin-left: 10px;">
                <%=financeExpenseVO.getDepartmentLeaderContent()%>
            </div>
            <div style=" text-align: right;">
                    姓名：<%=financeExpenseVO.getDepartmentLeaderName().equals( "") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeExpenseVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>

    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>财务总监意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeExpenseVO.getFinanceDirectorContent()%>
            </div>

            <div style="text-align: right;">
                姓名：<%=financeExpenseVO.getFinanceDirectorName().equals( "") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getFinanceDirectorName() + "&nbsp;&nbsp;"%>
                日期：<%=financeExpenseVO.getFinanceDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getFinanceDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;'">副总裁意见：</td>
        <td style=''>
            <div style=" float:left;text-align: left;margin-left: 10px;">
                <%=financeExpenseVO.getGeneralManagerContent()%>
            </div>
            <div style="text-align: right;">
                    姓名：<%=financeExpenseVO.getGeneralManagerName().equals( "") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getGeneralManagerName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeExpenseVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeExpenseVO.getExecutiveDirectorContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeExpenseVO.getExecutiveDirectorName().equals( "") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeExpenseVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">出纳意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeExpenseVO.getCashierContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeExpenseVO.getCashierName().equals( "") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getCashierName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeExpenseVO.getCashierTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getCashierTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style='text-align:right;line-height: 20px;'>
            经办人:<%=financeExpenseVO.getSubmitterName() .equals( "")   ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getSubmitterName() + "&nbsp;&nbsp;"%>
            日期：<%=financeExpenseVO.getSubmitterTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeExpenseVO.getSubmitterTime().substring(0, 10) + "&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
NO: <%=financeExpenseVO.getControlString3()%>
<br/><br/><br/><br/>
    <br/>

<table border=0 cellpadding=0 cellspacing=0 class="tables" width="710" style="font-size: 12px;" >
    <tr>
        <td colspan="5" style="text-align:center;line-height: 50px;"><b><%=financeExpenseVO.getControlString1()%>
        </b></td>
    </tr>

    <tr>
        <td colspan=5 height=30 style='height:22.5pt;text-align: center;'><b>费<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>用<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>报<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>销<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>明<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>细</b>
        </td>
    </tr>
    <tr>

        <td align='center'>项目名称</td>
        <td align='center'>金额（元）</td>
        <td align='center'>项目时间</td>
        <td align='center'>用途</td>
        <td align='center'>备注</td>
    </tr>
<%
    for (int i = 0; DetailList.size() > 0 && i < DetailList.size(); i++) {
        FinanceExpenseDetailPO detaop = DetailList.get(i);
%>
    <tr >
        <td align='center'><input type="text" value="<%=detaop.getItemName()%>" style="border:0px">

        </td>
        <td align='center'><%=NumberToCNUtils.numberTo2(detaop.getMoney())%>

        </td>
        <td align='center'><%=detaop.getItemTime().substring(0, 10)%>
        </td>
        <td align='center' >
            <textarea rows="2" cols="20" style="border:none;resize:none; "><%=detaop.getPurpose()%></textarea>
        </td>
        <td align='center' colspa="">
            <textarea rows="2" cols="20" style="border:none;resize:none; "><%=detaop.getComment()%></textarea>
        </td>
    </tr>


<% }
%>
</table>
<br/>
NO: <%=financeExpenseVO.getControlString3()%>
</div>
</body>
</html>