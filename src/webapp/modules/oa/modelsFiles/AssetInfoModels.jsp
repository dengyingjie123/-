<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.entity.vo.oa.assetInfo.AssetInfoVO" %>
<%@ page import="com.youngbook.service.oa.assetInfo.AssetInfoService" %>
<%@ page import="com.youngbook.common.utils.NumberToCNUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    AssetInfoService service = new AssetInfoService();
    AssetInfoVO assetInfoVO = service.getPrintDate(id);
    if (assetInfoVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>固定资产</title>
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
        <td colspan="4" style="text-align:center;"><b>固定资产单</b></td>
    </tr>
    <tr>
        <td align="center">名称</td>
        <td align="center">
            <%= assetInfoVO.getName() == null ?"":assetInfoVO.getName()%>
        </td>

        <td align="center">所属部门</td>
        <td align="center">
            <%= assetInfoVO.getDepartmentName() == null ?"":assetInfoVO.getDepartmentName()%>
        </td>
    </tr>
    <tr>
        <td align="center">数量</td>
        <td align="center">

            <%= assetInfoVO.getQuanity()%>
        </td>

        <td align="center">单价</td>
        <td align="center">
            <%=NumberToCNUtils. numberTo2(assetInfoVO.getUnitPrice())%>
        </td>
    </tr>
    <tr>
        <td align="center">金额</td>
        <td align="center">
            <%= NumberToCNUtils. numberTo2(assetInfoVO.getMoney())%>
        </td>
        <td align='center'>大写:</td>
        <td  align="center">
            <%= NumberToCNUtils.number2CNMontrayUnit(assetInfoVO.getMoney())%>
        </td>
    </tr>
    <tr>
        <td align="center">规格型号</td>
        <td align="center" >
            <%= assetInfoVO.getSpecification() == null ?"":assetInfoVO.getSpecification()%>
        </td>
        <td align="center">采购时间</td>
        <td align="center">
            <%= assetInfoVO.getBuyTime() == null ?"":assetInfoVO.getBuyTime()%>
        </td>
    </tr>

    <tr>
        <td align="center">申购用途</td>
        <td align="center" colspan="3">
            <%= assetInfoVO.getPurpose() == null ?"":assetInfoVO.getPurpose()%>
        </td>
    </tr>


    <tr>
        <td align="center">保管人</td>
        <td colspan="3" align="center">
            <%= assetInfoVO.getKeeperId() == null ?"":assetInfoVO.getKeeperId()%>
        </td>
    </tr>
    <tr>
        <td align="center">存放地点</td>
        <td colspan="3" align="center">
            <%= assetInfoVO.getStoragePlace() == null ?"":assetInfoVO.getStoragePlace()%>
        </td>
    </tr>

</table>

<br/>
<br/>
NO: 00000<%=assetInfoVO.getSid()%>

</body>
</html>