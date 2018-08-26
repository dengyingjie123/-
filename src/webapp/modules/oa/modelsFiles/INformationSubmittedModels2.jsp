<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.service.oa.Information.InformationSubmitted2Service" %>
<%@ page import="com.youngbook.entity.vo.oa.Information.InformationSubmitted2VO" %>
<%@ page import="com.youngbook.service.oa.Information.InformationSubmittedItem2Service" %>
<%@ page import="com.youngbook.entity.po.oa.Information.InformationSubmittedItem2PO" %>
<%@ page import="com.youngbook.entity.po.oa.Information.InformationSubmittedItem2Status" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    InformationSubmitted2Service service = new InformationSubmitted2Service();
    InformationSubmitted2VO InformationSubmitted2VO = service.getPrintDate(id);
    InformationSubmittedItem2Service Itemservice = new InformationSubmittedItem2Service();
    List<InformationSubmittedItem2PO> itemPOList = Itemservice.getItemData(id);
    if (InformationSubmitted2VO == null) {

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
    th{
        border:1px solid;
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
            <b><%=InformationSubmitted2VO.getControlString1()%>
            </b></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align:center;"><b>用章情况审批单</b></td>
    </tr>
    <tr>
        <td align="center">申请人</td>
        <td align='center'>
            <%=InformationSubmitted2VO.getApplicantName() == null ? "" : InformationSubmitted2VO.getApplicantName()%>
        </td>
        <td align="center">申请时间</td>
        <td align='center'>
            <%=InformationSubmitted2VO.getApplicationTime().substring(0, 19)%>
        </td>
    </tr>
    <tr>
        <td align="center">发往处</td>
        <td colspan="3" align='center'>
            <%=InformationSubmitted2VO.getSentto() == null ? "" : InformationSubmitted2VO.getSentto()%>
        </td>
    </tr>
    <tr>
        <td valign="top" align="center">申请用途</td>
        <td colspan="3" align="center">
            <%=InformationSubmitted2VO.getApplicationPurpose() == null ? "" : InformationSubmitted2VO.getApplicationPurpose()%>
        </td>
    </tr>

</table>
<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan=10 height=30 style='height:22.5pt;text-align: center;'><b>资<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>料<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>类<span
                style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>型</b></td>
    </tr>
    <tr>
        <th align='center'>资料名称</th>
        <th align='center'>资料份数</th>
        <th align='center'>资料备注</th>
        <th align='center'>是否外带</th>
        <th align='center'>外带人</th>
        <th align='center'>是否接收</th>
        <th align='center'>归还人</th>
        <th align='center'>是否归还</th>
    </tr>
    <%
        for (int i = 0; itemPOList.size() > 0 && i < itemPOList.size(); i++) {
            InformationSubmittedItem2PO itemPO = itemPOList.get(i);
    %>
    <tr>
        <td align='center'><%=itemPO.getDataName() == null ? "" : itemPO.getDataName()%>
        </td>
        <td align='center'><%=itemPO.getTopies() == null ? "" : itemPO.getTopies()%>
        </td>
        <td align='center'><%=itemPO.getDataComment() == null ? "" : itemPO.getDataComment()%>
        </td>
        <td align='center'><% int status= itemPO.getStatus();
            if(status == InformationSubmittedItem2Status.STATUS_OK){
                out.print("需要");
            }else{
                out.print("不需要");
            }
        %>
        </td>
        <td align='center'><%=itemPO.getReceiveName() == null ? "" : itemPO.getReceiveName()%>
        </td>
        <td align='center'>
            <% String reveove = itemPO.getReceiveIsConfirm();
                if(StringUtils.isEmpty(reveove)){
                    out.print("");
                }else{
                    if(reveove.equals(InformationSubmittedItem2Status.RECEIVEISCONFIRM_OK)){
                        out.print("已接收");
                    }else{
                        out.print("未接收");
                    }
                }
            %>
        </td>
        <td align='center'><%=itemPO.getOutBackName() == null ? "" : itemPO.getOutBackName()%>
        </td>
        <td align='center'>
            <% String outback = itemPO.getOutBackIsConfirm();
                if(StringUtils.isEmpty(outback)){
                    out.print("");
                }else{
                    if(outback.equals(InformationSubmittedItem2Status.OUTBACKISCONFIRM_OK)){
                        out.print("已归还");
                    }else{
                        out.print("未归还");
                    }
                }
            %>
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
        <td width="200px" style='text-align: center;'>部门负责人意见</td>
        <td style=''>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=InformationSubmitted2VO.getDepartmentLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=InformationSubmitted2VO.getDepartmentLeaderName().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getDepartmentLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=InformationSubmitted2VO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getDepartmentLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>
    <tr>
        <td style='text-align: center;margin-left: 10px;'>分管领导意见</td>
        <td >
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=InformationSubmitted2VO.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=InformationSubmitted2VO.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getChargeLeaderName() + "&nbsp;&nbsp;"%>
                日期：<%=InformationSubmitted2VO.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getChargeLeaderTime().substring(0, 10) + "&nbsp;&nbsp;"%>
            </div>

        </td>
    </tr>

    <tr>
        <td style='text-align: center;margin-left: 10px;'>印章保管人</td>
        <td >
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=InformationSubmitted2VO.getContent1()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=InformationSubmitted2VO.getName1().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getName1() + "&nbsp;&nbsp;"%>
                日期：<%=InformationSubmitted2VO.getTime1() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getTime1().substring(0, 10) + "&nbsp;&nbsp;"%>

            </div>
        </td>
    </tr>

    <tr>
        <td colspan="2" style='text-align:right;line-height: 40px;'>
            经办人:<%=InformationSubmitted2VO.getApplicantName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getApplicantName() + "&nbsp;&nbsp;"%>
            日期：<%=InformationSubmitted2VO.getApplicationTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;" : InformationSubmitted2VO.getApplicationTime().substring(0, 19) + "&nbsp;&nbsp;"%>

        </td>
    </tr>

</table>

NO: <%=InformationSubmitted2VO.getControlString3()%>


</body>
</html>