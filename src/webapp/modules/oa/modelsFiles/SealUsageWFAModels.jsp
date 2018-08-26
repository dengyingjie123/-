<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.service.oa.administration.SealUsageItemService" %>
<%@ page import="com.youngbook.entity.vo.oa.administration.SealUsageWFAVO" %>
<%@ page import="com.youngbook.service.oa.administration.SealUsageWFAService" %>
<%@ page import="com.youngbook.entity.po.oa.administration.SealUsageItemPO" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    SealUsageWFAService service = new SealUsageWFAService();
    SealUsageWFAVO sealUsageWFAVO = service.getPrintDate(id);
    SealUsageItemService Itemservice = new SealUsageItemService();
    List<SealUsageItemPO> itemPOList = Itemservice.getItemData(id);
    if (sealUsageWFAVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用章情况</title>
</head>
<style>
    td {
        border: 1px solid;
    }

    .tables {
        font-size: 5px;
    }

    @media print {
        .NoPrint {
            display: none;
        }
    }
</style>
<script type="application/javascript">

    function onclicks() {
        window.print();
    }
</script>
<body>

<input id="but" type="button" class="NoPrint" value="打印" onclick="onclicks()">

<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
    <tr>
        <td colspan="4" style="text-align:center;line-height: 50px;">
            <b><%=sealUsageWFAVO.getControlString1()%>
            </b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>用章情况审批单</b></td>
    </tr>
    <tr>
        <td align="center">申请人</td>
        <td align='center'>
            <%=sealUsageWFAVO.getApplicantName() == null ? "" : sealUsageWFAVO.getApplicantName()%>
        </td>
        <td align="center">申请时间</td>
        <td align='center'>
            <%=sealUsageWFAVO.getApplicationTime().substring(0, 19)%>
        </td>
    </tr>
    <tr>
        <td align="center">发往处</td>
        <td colspan="3" align='center'>
            <%=sealUsageWFAVO.getSentto() == null ? "" : sealUsageWFAVO.getSentto()%>
        </td>
    </tr>
    <tr>
        <td valign="top" align="center">申请用途</td>
        <td colspan="3" align="center">
            <%=sealUsageWFAVO.getApplicationPurpose() == null ? "" : sealUsageWFAVO.getApplicationPurpose()%>
        </td>
    </tr>

</table>
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan=2 height=30 style='height:22.5pt;text-align: center;'><b>用<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>章<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>类<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>型</b></td>
    </tr>
    <tr>
        <td align='center'>用章类型名称</td>
        <td align='center'>用章份数</td>
    </tr>
    <%
        for (int i = 0; itemPOList.size() > 0 && i < itemPOList.size(); i++) {
            SealUsageItemPO itemPO = itemPOList.get(i);
    %>
    <tr>
        <td align='center'><%=itemPO.getSealName() == null ? "" : itemPO.getSealName()%>
        </td>
        <td align='center'><%=itemPO.getTopies() == null ? "" : itemPO.getTopies()%>
        </td>

    </tr>
    <% }
    %>
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
                <%=sealUsageWFAVO.getDepartmentLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=sealUsageWFAVO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=sealUsageWFAVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;'">总经理：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=sealUsageWFAVO.getGeneralManagerContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=sealUsageWFAVO.getGeneralManagerName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getGeneralManagerName() + "&nbsp;&nbsp;"%>
                日期：<%=sealUsageWFAVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>分管领导意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=sealUsageWFAVO.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=sealUsageWFAVO.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getChargeLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=sealUsageWFAVO.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getChargeLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>

        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=sealUsageWFAVO.getExecutiveDirectorContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=sealUsageWFAVO.getExecutiveDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                日期：<%=sealUsageWFAVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td colspan="2" style='text-align:right;line-height: 40px;'>
            经办人:<%=sealUsageWFAVO.getApplicantName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getApplicantName() + "&nbsp;&nbsp;"%>
            日期：<%=sealUsageWFAVO.getApplicationTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;" : sealUsageWFAVO.getApplicationTime().substring(0, 19) + "&nbsp;&nbsp;"%>

        </td>
    </tr>

</table>

NO: <%=sealUsageWFAVO.getControlString3()%>


</body>
</html>