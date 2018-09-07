<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.service.oa.assetFixation.AssetApplicationService" %>
<%@ page import="com.youngbook.entity.vo.oa.assetFixation.AssetApplicationVO" %>
<%@ page import="com.youngbook.entity.po.oa.assetFixation.AssetItemPO" %>
<%@ page import="com.youngbook.service.oa.assetFixation.AssetItemService" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    AssetApplicationService service = new AssetApplicationService();
    AssetItemService itemService = new AssetItemService();
    AssetApplicationVO assetApplicationvo = service.getPrintDate(id);
    List<AssetItemPO> DetailList = itemService.getPrintDate(id);
    if (assetApplicationvo == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>固定资产申请</title>
</head>
<style>

    .tables {
        font-size: 5px;
    }
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
        <td colspan="4" style="text-align:center;line-height: 50px;"><b><%=assetApplicationvo.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>固定资产申请审批单</b></td>
    </tr>
    <tr>
        <td align="center">申请部门</td>
        <td align="center">
            <%= assetApplicationvo.getControlString2()%>
        </td>
        <td align="center">请购人</td>
        <td align="center">

            <%= assetApplicationvo.getApplicantName()== null ?"":assetApplicationvo.getApplicantName()%>
        </td>
    </tr>
    <tr>
        <td align="center">名称</td>
        <td align="center">

            <%= assetApplicationvo.getProductName()== null ?"":assetApplicationvo.getProductName()%>
        </td>
        <td align="center">资产类型</td>
        <td align="center">

            <%= assetApplicationvo.getAssetTypeName()== null ?"":assetApplicationvo.getAssetTypeName()%>
        </td>
    </tr>
    <tr>
        <td align='center'>总金额</td>
        <td  align="center">
            <%=NumberToCNUtils. numberTo2(assetApplicationvo.getMoneys())%>元</td>
        <td align='center'>大写:</td>
        <td  align="center">
            <%= NumberToCNUtils.number2CNMontrayUnit(assetApplicationvo.getMoneys())%></td>
    </tr>
    <tr>
        <td align="center">申请原因</td>
        <td colspan="3" align="center">

            <%= assetApplicationvo.getPurpose()== null ?"":assetApplicationvo.getPurpose()%>
        </td>
    </tr>

</table>
<table border=0 cellpadding=0 cellspacing=0 width=710 >
    <tr>
        <td colspan=8 height=30 style='height:22.5pt;text-align: center;'><b>资<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>产<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>项<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>目</b></td>
    </tr>
    <tr>
    <tr>
        <td align="center">名称</td>
        <td align="center">申购用途</td>
        <td align="center">规格型号</td>
        <td align="center">数量</td>
        <td align="center">预计单价(元)</td>
        <td align="center">预计金额(元)</td>
        <td align="center">单价(元)</td>
        <td align="center">金额(元)</td><%--
        <td align="right">采购时间</td>
        <td align="right">存放地点</td>
        <td align="right">保管人姓名</td>--%>
    </tr>
    <%
        for (int i = 0; DetailList.size() > 0 && i < DetailList.size(); i++) {
            AssetItemPO detaop = DetailList.get(i);
    %>
    <tr>
        <td align='center'><%=detaop.getName() == null ?"":detaop.getName()%>
        </td>
        <td align='center'><%=detaop.getTusage() == null ?"":detaop.getTusage()%>
        </td>
        <td align='center'><%=detaop.getSpecification() == null ?"":detaop.getSpecification()%>
        </td>
        <td align='center'><%=detaop.getQuanity() %>
        </td>
         <td align='center'><%=NumberToCNUtils. numberTo2(detaop.getExpectedUnitPrice())%></td>
         <td align='center'><%=NumberToCNUtils. numberTo2(detaop.getExpectedMoney())%></td>
         <td align='center'><%=NumberToCNUtils. numberTo2(detaop.getUnitPrice())%></td>
         <td align='center'><%=NumberToCNUtils. numberTo2(detaop.getMoney())%></td>
         <%--<td align='center'><%=detaop.getBuyTime().substring(0, 19)%></td>--%>
         <%--<td align='center'><%=detaop.getStoragePlace()%></td>--%>
    </tr>
    <% }
    %>
</table>
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan=2 height=30 style='height:22.5pt;text-align: center;'><b>签<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>字<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>栏</b></td>
    </tr>
    <tr>
        <td  width="200px" style="text-align: center;margin-left: 10px;">使用部门意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=assetApplicationvo.getContent1() == null ?"":assetApplicationvo.getContent1()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=assetApplicationvo.getName1() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getName1() + "&nbsp;&nbsp;"%>
                日期：<%=assetApplicationvo.getTime1() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getTime1().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>

        </td>

    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">采购归口部门意见：</td>
        <td style='border-top:none ;'>
            <div  style="float:left;text-align: left;margin-left: 10px;">
                <%=assetApplicationvo.getContent2()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=assetApplicationvo.getName2() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getName2() + "&nbsp;&nbsp;"%>
                日期：<%=assetApplicationvo.getTime2() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getTime2().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">分管领导意见：</td>
        <td style='border-top:none ;'>
            <div  style="float:left;text-align: left;margin-left: 10px;">
                <%=assetApplicationvo.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=assetApplicationvo.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getChargeLeaderName()+"&nbsp;&nbsp;"%>
                日期：<%=assetApplicationvo.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getChargeLeaderTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">执行董事意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=assetApplicationvo.getExecutiveDirectorContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=assetApplicationvo.getExecutiveDirectorName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getExecutiveDirectorName()+"&nbsp;&nbsp;"%>
                日期：<%=assetApplicationvo.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getExecutiveDirectorTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style='text-align:right;line-height: 40px;'>
            经办人:<%=assetApplicationvo.getApplicantName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getApplicantName() + "&nbsp;&nbsp;"%>
            日期：<%=assetApplicationvo.getAssApplicantTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;": assetApplicationvo.getAssApplicantTime().substring(0,10) + "&nbsp;&nbsp;"%>

        </td>
    </tr>

</table>
</div>
<br/>
<br/>
NO: <%=assetApplicationvo.getControlString3()%>


</body>
</html>