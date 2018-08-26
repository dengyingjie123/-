<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.service.oa.Information.InformationSubmittedService" %>
<%@ page import="com.youngbook.entity.vo.oa.Information.InformationSubmittedVO" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    InformationSubmittedService service = new InformationSubmittedService();
    InformationSubmittedVO informationSubmittedVO = service.getPrintData(id);
    if (informationSubmittedVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>对外资料报送</title>
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
        <td colspan="4" style="text-align:center;line-height: 50px;"><b><%=informationSubmittedVO.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>对外资料报送审批单</b></td>
    </tr>

    <tr>
        <td align="center">经办部门</td>
        <td align="center">
            <%= informationSubmittedVO.getControlString2() == null ?"":informationSubmittedVO.getControlString2()%>
        </td>
        <td align="center">经办人</td>
        <td align="center">
            <%= informationSubmittedVO.getHandlingName() == null ?"":informationSubmittedVO.getHandlingName()%>
        </td>
    </tr>
    <tr>
        <td align="center">主送单位</td>
        <td align="center">
            <%= informationSubmittedVO.getMainOrg() == null ?"":informationSubmittedVO.getMainOrg()%>
        </td>
        <td align="center">抄送单位</td>
        <td align="center" width="150px">
            <%= informationSubmittedVO.getOtherOrg() == null ?"":informationSubmittedVO.getOtherOrg()%>
        </td>
    </tr>

    <tr>
        <td align="center">报送时间</td>
        <td align="center">
            <%= informationSubmittedVO.getSubmitTime() == null ? "" : informationSubmittedVO.getSubmitTime().substring(0, 19)%>
        </td>

        <td align="center">原件移交时间</td>
        <td align="center">
            <%= informationSubmittedVO.getTransferTime() == null ? "" : informationSubmittedVO.getTransferTime().substring(0, 19)%>
        </td>
    </tr>
    <tr>
        <td align="center">原件移交人</td>
        <td align="center">
            <%= informationSubmittedVO.getTransferOperatorName() == null ?"":informationSubmittedVO.getTransferOperatorName()%>
        </td>
        <td align="center">原件移交接收人</td>
        <td>
            <%= informationSubmittedVO.getTransferRecipientName() == null ?"":informationSubmittedVO.getTransferRecipientName()%>
        </td>
    </tr>
    <tr>
        <td align="center">原件归还时间</td>
        <td align="center">
            <%= informationSubmittedVO.getRevertTime() == null ? "" : informationSubmittedVO.getRevertTime().substring(0, 19).substring(0, 19)%>
        </td>
        <td align="center">原件归还人</td>
        <td align="center">
            <%= informationSubmittedVO.getRevertOperatorName() == null ?"":informationSubmittedVO.getRevertOperatorName()%>
        </td>
    </tr>
    <tr>
        <td align="center">原件归还接收人</td>
        <td colspan="3" align="center">
            <%= informationSubmittedVO.getRevertRecipientName() == null ?"":informationSubmittedVO.getRevertRecipientName()%>
        </td>
    </tr>
    <tr>
        <td align="center">报送事由</td>
        <td colspan="3" align="center">
            <%= informationSubmittedVO.getReason() == null ?"":informationSubmittedVO.getReason()%>
        </td>
    </tr>
    <tr>
        <td align="center">报送内容</td>
        <td colspan="3" align="center">
            <%= informationSubmittedVO.getContent() == null ?"":informationSubmittedVO.getContent()%>
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
                <%=informationSubmittedVO.getDepartmentLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=informationSubmittedVO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=informationSubmittedVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;'">总经理：</td>
        <td style='border-top:none;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=informationSubmittedVO.getGeneralManagerContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=informationSubmittedVO.getGeneralManagerName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getGeneralManagerName() + "&nbsp;&nbsp;"%>
                日期：<%=informationSubmittedVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getGeneralManagerTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>分管领导意见：</td>
        <td style='border-top:none ;'>
            <div style="text-align: center;margin-left: 10px;">
                <%=informationSubmittedVO.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=informationSubmittedVO.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getChargeLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=informationSubmittedVO.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  : informationSubmittedVO.getChargeLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>

        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>执行董事意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=informationSubmittedVO.getExecutiveDirectorContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=informationSubmittedVO.getExecutiveDirectorName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getExecutiveDirectorName() + "&nbsp;&nbsp;"%>
                日期：<%=informationSubmittedVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getExecutiveDirectorTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td colspan="2" style='border-left:none ;text-align:right;line-height: 40px;'>
            经办人:<%=informationSubmittedVO.getApplicantName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getApplicantName() + "&nbsp;&nbsp;"%>
            日期：<%=informationSubmittedVO.getApplicantTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;" : informationSubmittedVO.getApplicantTime().substring(0, 19) + "&nbsp;&nbsp;"%>

        </td>
    </tr>

</table>
<br/>
<br/>
NO: <%=informationSubmittedVO.getControlString3()%>
<script type="application/javascript">
    function onclicks() {
        window.print();
    }
</script>
</body>
</html>