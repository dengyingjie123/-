<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.service.oa.expense.FinanceBizTripExpenseWFAService" %>
<%@ page import="com.youngbook.entity.vo.oa.expense.FinanceBizTripExpenseWFAVO" %>
<%@ page import="com.youngbook.service.oa.expense.FinanceBizTripExpenseItemService" %>
<%@ page import="com.youngbook.entity.po.oa.expense.FinanceBizTripExpenseItemPO" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%@ page import="java.util.List" %>

<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    FinanceBizTripExpenseWFAService service = new FinanceBizTripExpenseWFAService();
    FinanceBizTripExpenseWFAVO financeBizTripExpenseWFAVO = service.getPrintDate(id);
    FinanceBizTripExpenseItemService Itemservice = new FinanceBizTripExpenseItemService();
    List<FinanceBizTripExpenseItemPO> itemPOList = Itemservice.getPrintDate(id);

    if (financeBizTripExpenseWFAVO == null) {

    }
    //附件张数大写
    String numberStr ="";
    int accessoryNumber = financeBizTripExpenseWFAVO.getAccessoryNumber();
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
    <title>差旅费报销</title>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
</head>
<!--[if lte IE 9]>
<script src="<%=Config.getWebRoot()%>/w2/dist/html5/respond.min.js"></script>
<script src="<%=Config.getWebRoot()%>/w2/dist/html5/html5.js"></script>
<![endif]-->

<style type="text/css">
    td {
        border: 1px solid;
    }
    .tds{
        width:200px;
    }
    .tablse td{
        height:30px;
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
<!--startprint-->
<div style="margin-left: 40px;">
<table border=0 cellpadding=0 cellspacing=0 width=710  style="font-size: 15px;">
    <tr>
        <td colspan="4" style="text-align:center;line-height: 50px;"><b>
            <%=financeBizTripExpenseWFAVO.getControlString1()%>
        </b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>差旅费报销审批单</b></td>
    </tr>
    <tr>
        <td align='center' class="tds"  >出差人姓名</td>
        <td  colspan="3" align='center'>
            <%=financeBizTripExpenseWFAVO.getUserNames() == null ?"":financeBizTripExpenseWFAVO.getUserNames()%>
        </td>
    </tr>
    <tr>

        <td align='center'>部门组织</td>
        <td align='center'colspan ="3">

            <%=financeBizTripExpenseWFAVO.getControlString2() == null ?"":financeBizTripExpenseWFAVO.getControlString2()%>
        </td>

    </tr>
    <tr>
        <td align='center'>报销人</td>
        <td align='center'>

            <%=financeBizTripExpenseWFAVO.getReimburseName() == null ?"":financeBizTripExpenseWFAVO.getReimburseName()%>

        </td>
        <td align='center'>报销日期</td>
        <td colspan="3" align='center'>

            <%=financeBizTripExpenseWFAVO.getReimburseTime().substring(0, 10)%>
        </td>

    </tr>
    <tr>
        <td align='center'>总金额(元)</td>
        <td align='center' colspan="3">

            <%=NumberToCNUtils.numberTo2(financeBizTripExpenseWFAVO.getMoney())%>
        </td>
        </tr>
    <tr>
        <td align='center'>大写</td>
        <td align='center'colspan="3">

            <%=NumberToCNUtils.number2CNMontrayUnit(financeBizTripExpenseWFAVO.getMoney())%>
        </td>
    <tr>
        <td align='center'>事由</td>

        <td colspan="3" >
            <textarea rows="2" cols="60" style="border:none;resize:none; "><%=financeBizTripExpenseWFAVO.getComment() == null ?"":financeBizTripExpenseWFAVO.getComment()%>
            </textarea>
        </td>
    </tr>

    <tr>
        <td align='center'>附件张数</td>
        <td colspan="3" align='center'>
             <%=numberStr %> 张
        </td>

    </tr>

</table>
<table border=0 cellpadding=0 cellspacing=0 width=710  style="font-size: 15px;">
    <tr>
        <td colspan=2 style='height:22.5pt;text-align: center;'><b>签<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>字<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>栏</b></td>
    </tr>
    <tr>
        <td width="200px" style='text-align: center;'>部门负责人意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getDepartmentLeaderContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

                </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;'">总经理：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getGeneralManagerContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getGeneralManagerName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getGeneralManagerName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;'>会计审核意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getAccountingContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getAccountingName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getAccountingName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getAccountingTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getAccountingTime().substring(0, 10) + "&nbsp;&nbsp;"%>


            </div>
        </td>

    </tr>
    <tr>
        <td style='text-align: center;margin-left: 10px;'>财务总监意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getFinanceDirectorContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getFinanceDirectorName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getFinanceDirectorName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getFinanceDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getFinanceDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getExecutiveDirectorContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getExecutiveDirectorName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">出纳意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=financeBizTripExpenseWFAVO.getCashierContent()%>
            </div>

            <div style="text-align: right;">
                    姓名：<%=financeBizTripExpenseWFAVO.getCashierName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getCashierName() + "&nbsp;&nbsp;"%>
                    日期：<%=financeBizTripExpenseWFAVO.getCashierTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getCashierTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style='text-align:right;line-height: 20px;'>
            经办人:<%=financeBizTripExpenseWFAVO.getReimburseName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getReimburseName() + "&nbsp;&nbsp;"%>
            日期：<%=financeBizTripExpenseWFAVO.getReimburseTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getReimburseTime().substring(0, 10) + "&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
NO: <%=financeBizTripExpenseWFAVO.getControlString3()%>
</div>
<br/>
<br/>

<%

    for (int i = 0; itemPOList.size() > 0 && i < itemPOList.size(); i++) {
        FinanceBizTripExpenseItemPO itemPO = itemPOList.get(i);
%>

    <div style="margin-left: 40px;">
        <br/>
        <br/>
        <br/>
        <br/>
<table border=0 cellpadding=0 cellspacing=0 width=710 class="tablse">
    <tr>
        <td colspan="4" style="text-align:center;line-height: 50px;"><b>
            <%=financeBizTripExpenseWFAVO.getControlString1()%>
        </b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>差旅费报销明细单</b></td>
    </tr>
    <tr>
        <td align='center' width="80px">开始时间</td>
        <%= "<td align='center' >" + itemPO.getStartTime().substring(0, 10) + "</td>"%>

        <td align='center'>结束时间</td>
        <%="<td align='center'>" + itemPO.getEndTime().substring(0, 10) + "</td>"%>

    </tr>
    <tr>
        <td align='center'>起始地</td>
        <%="<td align='center'>" + itemPO.getStartAddress() + "</td>"%>

        <td align='center'>结束地</td>
        <%="<td align='center'>" + itemPO.getEndAddress() + "</td>"%>

    </tr>
    <tr>
        <td align='center'>过路费(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getRoadFee())+ "</td>"%>


        <td align='center'>飞机票费(元)</td>
        <%="<td align='center'>" +NumberToCNUtils.numberTo2( itemPO.getAirplaneFee()) + "</td>"%>

    </tr>
    <tr>
        <td align='center'>火车票费(元)</td>
        <%="<td align='center'>" +NumberToCNUtils.numberTo2( itemPO.getTrainFee()) + "</td>"%>


        <td align='center'>伙食补贴(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getFoodFee()) + "</td>"%>
    </tr>
    <tr>
        <td align='center'>汽车票费(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getBusFee()) + "</td>"%>

        <td align='center'>住宿费(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getLiveFee()) + "</td>"%>
    </tr>
    <tr>
        <td align='center'>其他(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getOtherFee()) + "</td>"%>

        <td align='center'>合计(元)</td>
        <%="<td align='center'>" + NumberToCNUtils.numberTo2(itemPO.getTotalFee()) + "</td>"%>
    </tr>
    <tr>
        <td align='center'>备注</td>
        <td align='center'colspan='3'>
            <textarea rows="3" cols="50" style="border:none;resize:none; "><%=itemPO.getComment()%></textarea>
              </td>
    </tr>
    <tr>
        <td colspan="4" style='text-align:right;'>
            经办人:<%=financeBizTripExpenseWFAVO.getReimburseName() .equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getReimburseName() + "&nbsp;&nbsp;"%>
            日期：<%=financeBizTripExpenseWFAVO.getReimburseTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : financeBizTripExpenseWFAVO.getReimburseTime().substring(0, 10) + "&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
NO: <%=financeBizTripExpenseWFAVO.getControlString3()%>
第 <%= i+1 %> 张，共<%= itemPOList.size() %> 张
    </div>
<%
    }
%>
<!--endprint-->
</body>
</html>