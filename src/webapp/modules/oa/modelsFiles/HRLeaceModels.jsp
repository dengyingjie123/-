<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""
         import="com.youngbook.common.config.*" %>
<%@ page import="com.youngbook.service.oa.hr.HRLeaveService" %>
<%@ page import="com.youngbook.entity.vo.oa.hr.HRLeaveVO" %>
<%
    String token = request.getParameter("token");
    String id = request.getParameter("id");
    HRLeaveService service = new HRLeaveService();
    HRLeaveVO hrleaveVO = service.getPrintDate(id);
    if (hrleaveVO == null) {

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>休假请假</title>
</head>
<style>
    table, td {
        border: 1px solid;
    }
    td{

    }

</style>
<body onload="onclicks();">

<table border=0 cellpadding=0 cellspacing=0 width=710>
    <tr>
        <td colspan="2" style="text-align:center;line-height: 50px;"><b><%=hrleaveVO.getControlString1()%></b></td>
    </tr>
    <tr>
        <td colspan="2" style="text-align:center;"><b>请假休假审批单</b></td>
    </tr>
    <tr>
        <td align="center">申请人</td>
        <td align="center">
                <%=hrleaveVO.getOh_applicationName() == null ?"":hrleaveVO.getOh_applicationName()%>
        </td>
    </tr>
    <tr>
        <td align="center">请假类别</td>
        <td align="center">
            <%=hrleaveVO.getLeaveTypeName() == null ?"":hrleaveVO.getLeaveTypeName()%>
        </td>
    </tr>
    <tr>
        <td align="center">其他类别描述</td>
        <td align="center">
            <%=hrleaveVO.getOtherTypeDescription() == null ?"":hrleaveVO.getOtherTypeDescription()%>
        </td>
    </tr>
    <tr>
        <td align="center">天数</td>
        <td align="center">
            <%=hrleaveVO.getDays()%>
        </td>
    </tr>
    <tr>
        <td align="center">请假去处</td>
        <td align="center">
            <%=hrleaveVO.getWhereToLeave() == null ?"":hrleaveVO.getWhereToLeave()%>
        </td>
    </tr>
    <tr>
        <td align="center">开始时间</td>
        <td align="center">
            <%=hrleaveVO.getStartTime().substring(0,19)%>
        </td>
    </tr>
    <tr>
        <td align="center">结束时间</td>
        <td align="center">
            <%=hrleaveVO.getEndTime().substring(0, 19)%>
        </td>
    </tr>
    <tr>
        <td align="center">工作交接人</td>
        <td align="center">
            <%=hrleaveVO.getHandoverName() == null ?"":hrleaveVO.getHandoverName()%>
        </td>
    </tr>
    <tr>
        <td align="center">请假原因</td>
        <td align="center">
            <%=hrleaveVO.getReason() == null ?"":hrleaveVO.getReason()%>
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
                <%=hrleaveVO.getDepartmentLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=hrleaveVO.getDepartmentLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getDepartmentLeaderName()+"&nbsp;&nbsp;"%>
                日期：<%=hrleaveVO.getDepartmentLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getDepartmentLeaderTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>

        </td>
    </tr>
    <tr>
        <td style="text-align: center;margin-left: 10px;">人力行政：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=hrleaveVO.getContent1()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=hrleaveVO.getName1() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getName1()+"&nbsp;&nbsp;"%>
                日期：<%=hrleaveVO.getTime1() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getTime1().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">总经理：</td>
        <td style='border-top:none;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=hrleaveVO.getGeneralManagerContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=hrleaveVO.getGeneralManagerName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getGeneralManagerName()+"&nbsp;&nbsp;"%>
                日期：<%=hrleaveVO.getGeneralManagerTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getGeneralManagerTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">分管领导意见：</td>
        <td style='border-top:none ;'>
            <div  style="float:left;text-align: left;margin-left: 10px;">
                <%=hrleaveVO.getChargeLeaderContent()%>
            </div>

            <div style="float:right;text-align: right;">
                姓名：<%=hrleaveVO.getChargeLeaderName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getChargeLeaderName()+"&nbsp;&nbsp;"%>
                日期：<%=hrleaveVO.getChargeLeaderTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getChargeLeaderTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>

    <tr>
        <td style="text-align: center;margin-left: 10px;">执行董事意见：</td>
        <td style='border-top:none ;'>
            <div style="float:left;text-align: left;margin-left: 10px;">
                <%=hrleaveVO.getExecutiveDirectorContent()%>
            </div>
            <div style="float:right;text-align: right;">
                姓名：<%=hrleaveVO.getExecutiveDirectorName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getExecutiveDirectorName()+"&nbsp;&nbsp;"%>
                日期：<%=hrleaveVO.getExecutiveDirectorTime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getExecutiveDirectorTime().substring(0,10)+"&nbsp;&nbsp;"%>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2"  style='border-left:none ;text-align:right;line-height: 40px;'>
            经办人:<%=hrleaveVO.getOh_applicationName() == "" ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getOh_applicationName()+"&nbsp;&nbsp;"%>
            日期：<%=hrleaveVO.getOh_applicationtime() == null ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : hrleaveVO.getOh_applicationtime().substring(0,10)+"&nbsp;&nbsp;"%>

        </td>
    </tr>
</table>
<br/>
<br/>
NO: <%=hrleaveVO.getControlString3()%>
<%--<div region="south" border="false" style="text-align:center;padding:5px;background:#F4F4F4">--%>
<%--<a id="btnPrint<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"--%>
<%--href="javascript:void(0)">onclicks</a>--%>
<%--<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"--%>
<%--onClick="fwCloseWindow('PrintFinancePayWFAWindow<%=token%>')">取消</a>--%>
<%--</div>--%>
<%--</div>--%>
<script type="application/javascript">
    function onclicks() {
        window.print();
    }
</script>
</body>
</html>