<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.service.oa.business.BusinessTripApplicationService" %>
<%@ page import="com.youngbook.entity.vo.business.BusinessTripApplicationVO" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    BusinessTripApplicationService service = new BusinessTripApplicationService();
    BusinessTripApplicationVO businessTripApplicationVO = service.getPrintDate(id);
    if (businessTripApplicationVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>出差申请</title>
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
<div style="margin-left: 20px;margin-top:0px;">
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan="4" style="text-align:center;line-height: 50px;"><b><%=businessTripApplicationVO.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>出差申请审批单</b></td>
    </tr>
    <tr>
        <td align="center">出差人</td>
        <td align="center">
            <%=businessTripApplicationVO.getUserName() == null ?"":businessTripApplicationVO.getUserName()%>
        </td>
        <td align="center">部门</td>
        <td align="center">
            <%=businessTripApplicationVO.getControlString2() == null ?"":businessTripApplicationVO.getControlString2()%>
        </td>
    </tr>
    <tr>
        <td align="center">计划天数</td>
        <td align="center">
            <%=businessTripApplicationVO.getPlanFate()%>
        </td>
        <td align="center">实际天数</td>
        <td align="center">
            <%=businessTripApplicationVO.getActualFate()%>
        </td>
    </tr>
    <tr>

        <td align="center">费用预算(元)</td>
        <td align="center">
            <%=NumberToCNUtils. numberTo2(businessTripApplicationVO.getExpenseBudge())%>
        </td>
        <td align="center">实际费用（元）</td>
        <td align="center">
            <%=NumberToCNUtils.numberTo2(businessTripApplicationVO.getExpenseActual())%>
        </td>
    </tr>
    <tr>
        <td align="center">实际费用大写</td>
        <td align="center" colspan="3">
            <%=NumberToCNUtils.number2CNMontrayUnit(businessTripApplicationVO.getExpenseActual())%>
        </td>
    </tr>
    <tr>
        <td align="center">出差人员</td>
        <td align="center">
            <%=businessTripApplicationVO.getEvections() == null ?"":businessTripApplicationVO.getEvections()%>
        </td>
        <td align="center">经办人签字</td>
        <td align="center">
            <%=businessTripApplicationVO.getOperatorSign() == null ?"":businessTripApplicationVO.getOperatorSign()%>
        </td>
    </tr>
    <tr>
        <td align="center">出差原因</td>
        <td align="center" colspan="3">
            <%=businessTripApplicationVO.getPurpose() == null ?"":businessTripApplicationVO.getPurpose()%>
        </td>
    </tr>
    <tr>
        <td align="center">出差地点</td>
        <td align="center" colspan="3">
            <%=businessTripApplicationVO.getBusinessAddress() == null ?"":businessTripApplicationVO.getBusinessAddress()%>
        </td>
    </tr>
   <tr>
       <td align="center">申请时间</td>
       <td align="center" colspan="3">
           <%=businessTripApplicationVO.getApplicationTime() == null ?"":businessTripApplicationVO.getApplicationTime()%>
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
        <td width="200px" style='text-align: center;'>部门负责人意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getDepartmentLeaderContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getDepartmentLeaderName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
    <td style="text-align: center;margin-left: 10px;">人力行政：</td>
    <td style='border-top:none ;'>
        <div style="float:left;text-align: left;margin-left: 10px;">
            <%=businessTripApplicationVO.getContent1()%>
        </div>
        <div style="float:right;text-align: right;">
            姓名：<%=businessTripApplicationVO.getName1() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getName1()+"&nbsp;&nbsp;"%>
            日期：<%=businessTripApplicationVO.getTime1() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getTime1().substring(0,10)+"&nbsp;&nbsp;"%>
        </div>
    </td>
</tr>
    <tr>
        <td style="text-align: center;'">总经理：</td>
        <td style='border-top:none;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getGeneralManagerContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getGeneralManagerName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getGeneralManagerName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">分管领导意见：</td>
        <td style='border-top:none ;'>
            <div  style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getChargeLeaderName()+"&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getChargeLeaderTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getExecutiveDirectorContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getExecutiveDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getExecutiveDirectorName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;'>会计审核意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getAccountingContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getAccountingName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getAccountingName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getAccountingTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getAccountingTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>

            </div>
        </td>

    </tr>
    <tr>
        <td style='text-align: center;margin-left: 10px;'>财务总监意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getFinanceDirectorContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getFinanceDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getFinanceDirectorName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getFinanceDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getFinanceDirectorTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">出纳意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=businessTripApplicationVO.getCashierContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=businessTripApplicationVO.getCashierName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getCashierName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
                日期：<%=businessTripApplicationVO.getCashierTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getCashierTime().substring(0, 10) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style='border-left:none ;text-align:right;line-height: 20px;'>
            经办人:<%=businessTripApplicationVO.getUserName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getUserName() + "&nbsp;&nbsp;&nbsp;&nbsp;"%>
            日期：<%=businessTripApplicationVO.getApplicationTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : businessTripApplicationVO.getApplicationTime().substring(0, 19) + "&nbsp;&nbsp;&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
NO: <%=businessTripApplicationVO.getControlString3()%>
</div>
</body>
</html>