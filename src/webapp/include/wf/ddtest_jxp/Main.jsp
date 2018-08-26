








<%@ page contentType="text/html; charset=utf-8" %>
<%
/**
 * 标题：主页面
 * 描述：工作流主页面
 * 时间：2005-10-17 18:09:28
 * 版权：
 */
%>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.ydtf.dmis.ddtest.DDJxp" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="java.util.*;" %>
<%
int intWorkflowID = 7;  //获得工作流编号
int intCurrNodeID = Integer.parseInt(request.getParameter("CurrNodeID"));  //获得当前节点编号
int intRouteListID = Integer.parseInt(request.getParameter("RouteListID"));  //获得RouteList编号
String strYWID = request.getParameter("YWID");  //获得业务编号
DDJxp objDDJxp = new DDJxp();
if (strYWID != null && !strYWID.equals("")) {
  objDDJxp.setID(Integer.parseInt(strYWID));
  List listDDJxp = objDDJxp.queryExact();
  objDDJxp = (DDJxp)listDDJxp.get(0);
}
%>

<html>
<head>
<title>工作流主页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
.style1 {
	font-size: 36px;
	font-weight: bold;
	font-family: Georgia, Times New Roman, Times, serif;
}
-->
</style>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
function submitForm(form, target) {
  if (target == "SaveOnly") {
    form.TargetURL.value = "/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "SaveOnly";
    form.submit();
  }
  else if (target == "SaveForward") {
    form.TargetURL.value = "/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "SaveForward";
    form.submit();
  }
  else if (target == "Cancel") {
    form.TargetURL.value = "/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "Cancel";
    form.submit();
  }
  else if (target=="Reject"){
	form.TargetURL.value="/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
	form.target="_self";
	form.ServiceType.value="Reject";
	form.submit();
  }
    else if (target=="AutoForward"){
	form.TargetURL.value="/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
	form.target="_self";
	form.ServiceType.value="AutoForward";
	form.submit();
  }
  else if (target == "Over") {
    form.TargetURL.value = "/ddtest_jxp/Done.jsp";
    form.action = "/workflow/WorkflowService";
    form.target = "_self";
    form.ServiceType.value = "Over";
    form.submit();
  }
  else if (target == "ActionReport") {
    form.target = "_blank";
    form.YWID.value = form.ID.value;
    form.action = "ActionReport.jsp";
    form.submit();
  }
}
</script>

<body>
<p align="center" class="style1">地调Dmis检修票系统</p>
<form action="/workflow/WorkflowService" method="post" name="form" id="form">
<table width="100%"  border="0" cellpadding="2" cellspacing="1" class="table_bg_black">
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"ID") %>：    </td>
    <td><input name="ID" type = "text" size="50"   value="<%=objDDJxp.isEmptyID()? "" : String.valueOf(objDDJxp.getID())%>" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "ID")  == 0 ? "" : "readonly"%> >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TYPE") %>：    </td>
    <td><input type = "text" size="50"  name="TYPE" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TYPE")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTYPE()? "" : objDDJxp.getTYPE()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"PH") %>：    </td>
    <td><input type = "text" size="50"  name="PH" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "PH")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyPH()? "" : objDDJxp.getPH()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQDW") %>：    </td>
    <td><input type = "text" size="50"  name="SQDW" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQDW")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQDW()? "" : objDDJxp.getSQDW()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQR") %>：    </td>
    <td><input type = "text" size="50"  name="SQR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQR()? "" : objDDJxp.getSQR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQSJ") %>：    </td>
    <td><input type = "text" size="50"  name="SQSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQSJ()? Tools.getTime() : objDDJxp.getSQSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TDLXR") %>：    </td>
    <td><input type = "text" size="50"  name="TDLXR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDLXR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTDLXR()? "" : objDDJxp.getTDLXR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"LXDH") %>：    </td>
    <td><input type = "text" size="50"  name="LXDH" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LXDH")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyLXDH()? "" : objDDJxp.getLXDH()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SBMC") %>：    </td>
    <td><input type = "text" size="50"  name="SBMC" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SBMC")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySBMC()? "" : objDDJxp.getSBMC()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"GZNR") %>：    </td>
    <td><input type = "text" size="50"  name="GZNR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "GZNR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyGZNR()? "" : objDDJxp.getGZNR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TDFW") %>：    </td>
    <td><input type = "text" size="50"  name="TDFW" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDFW")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTDFW()? "" : objDDJxp.getTDFW()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQDWYJ") %>：    </td>
    <td><input type = "text" size="50"  name="SQDWYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQDWYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQDWYJ()? "" : objDDJxp.getSQDWYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQTDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="SQTDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQTDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQTDSJ()? "" : objDDJxp.getSQTDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SQFDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="SQFDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SQFDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySQFDSJ()? "" : objDDJxp.getSQFDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"PZTDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="PZTDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "PZTDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyPZTDSJ()? "" : objDDJxp.getPZTDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"PZFDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="PZFDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "PZFDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyPZFDSJ()? "" : objDDJxp.getPZFDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YYKYJ") %>：    </td>
    <td><input type = "text" size="50"  name="YYKYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYYKYJ()? "" : objDDJxp.getYYKYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YYKPZ") %>：    </td>
    <td><input type = "text" size="50"  name="YYKPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYYKPZ()? "" : objDDJxp.getYYKPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SJYJ") %>：    </td>
    <td><input type = "text" size="50"  name="SJYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SJYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySJYJ()? "" : objDDJxp.getSJYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"SJPZ") %>：    </td>
    <td><input type = "text" size="50"  name="SJPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "SJPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptySJPZ()? "" : objDDJxp.getSJPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"FJZYJ") %>：    </td>
    <td><input type = "text" size="50"  name="FJZYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "FJZYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyFJZYJ()? "" : objDDJxp.getFJZYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"FJZPZ") %>：    </td>
    <td><input type = "text" size="50"  name="FJZPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "FJZPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyFJZPZ()? "" : objDDJxp.getFJZPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"LDYJ") %>：    </td>
    <td><input type = "text" size="50"  name="LDYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LDYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyLDYJ()? "" : objDDJxp.getLDYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"LDPZ") %>：    </td>
    <td><input type = "text" size="50"  name="LDPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LDPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyLDPZ()? "" : objDDJxp.getLDPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"DDKYJ") %>：    </td>
    <td><input type = "text" size="50"  name="DDKYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDKYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyDDKYJ()? "" : objDDJxp.getDDKYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"DDKPZ") %>：    </td>
    <td><input type = "text" size="50"  name="DDKPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDKPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyDDKPZ()? "" : objDDJxp.getDDKPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"DDYYJ") %>：    </td>
    <td><input type = "text" size="50"  name="DDYYJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDYYJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyDDYYJ()? "" : objDDJxp.getDDYYJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"DDYPZ") %>：    </td>
    <td><input type = "text" size="50"  name="DDYPZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDYPZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyDDYPZ()? "" : objDDJxp.getDDYPZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TZSJ") %>：    </td>
    <td><input type = "text" size="50"  name="TZSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTZSJ()? "" : objDDJxp.getTZSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TZJLR") %>：    </td>
    <td><input type = "text" size="50"  name="TZJLR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZJLR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTZJLR()? "" : objDDJxp.getTZJLR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TZR") %>：    </td>
    <td><input type = "text" size="50"  name="TZR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTZR()? "" : objDDJxp.getTZR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="TDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTDSJ()? "" : objDDJxp.getTDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TDSLR") %>：    </td>
    <td><input type = "text" size="50"  name="TDSLR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDSLR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTDSLR()? "" : objDDJxp.getTDSLR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"TDDDY") %>：    </td>
    <td><input type = "text" size="50"  name="TDDDY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDDDY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyTDDDY()? "" : objDDJxp.getTDDDY()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"KGSJ") %>：    </td>
    <td><input type = "text" size="50"  name="KGSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "KGSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyKGSJ()? "" : objDDJxp.getKGSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"KGSLR") %>：    </td>
    <td><input type = "text" size="50"  name="KGSLR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "KGSLR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyKGSLR()? "" : objDDJxp.getKGSLR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"KGDDY") %>：    </td>
    <td><input type = "text" size="50"  name="KGDDY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "KGDDY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyKGDDY()? "" : objDDJxp.getKGDDY()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"WGSJ") %>：    </td>
    <td><input type = "text" size="50"  name="WGSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "WGSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyWGSJ()? "" : objDDJxp.getWGSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"WGSLR") %>：    </td>
    <td><input type = "text" size="50"  name="WGSLR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "WGSLR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyWGSLR()? "" : objDDJxp.getWGSLR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"WGDDY") %>：    </td>
    <td><input type = "text" size="50"  name="WGDDY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "WGDDY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyWGDDY()? "" : objDDJxp.getWGDDY()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"FDSJ") %>：    </td>
    <td><input type = "text" size="50"  name="FDSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "FDSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyFDSJ()? "" : objDDJxp.getFDSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"FDSLR") %>：    </td>
    <td><input type = "text" size="50"  name="FDSLR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "FDSLR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyFDSLR()? "" : objDDJxp.getFDSLR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"FDDDY") %>：    </td>
    <td><input type = "text" size="50"  name="FDDDY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "FDDDY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyFDDDY()? "" : objDDJxp.getFDDDY()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YQSJ") %>：    </td>
    <td><input type = "text" size="50"  name="YQSJ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YQSJ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYQSJ()? "" : objDDJxp.getYQSJ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YQSQR") %>：    </td>
    <td><input type = "text" size="50"  name="YQSQR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YQSQR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYQSQR()? "" : objDDJxp.getYQSQR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YQPZR") %>：    </td>
    <td><input type = "text" size="50"  name="YQPZR" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YQPZR")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYQPZR()? "" : objDDJxp.getYQPZR()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"YQYY") %>：    </td>
    <td><input type = "text" size="50"  name="YQYY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YQYY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyYQYY()? "" : objDDJxp.getYQYY()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"BZ") %>：    </td>
    <td><input type = "text" size="50"  name="BZ" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "BZ")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyBZ()? "" : objDDJxp.getBZ()%>" >    </td>
  </tr>
  <tr class="table_bg_white">
    <td align='right' class="td_blue"><%= ClientApplications.getFieldTitle(7,"ZFYY") %>：    </td>
    <td><input type = "text" size="50"  name="ZFYY" <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "ZFYY")  == 0 ? "" : "disabled"%>  value="<%=objDDJxp.isEmptyZFYY()? "" : objDDJxp.getZFYY()%>" >    </td>
  </tr>
<tr class="table_bg_white">
  <td align='right' class="td_blue">转发目的地：</td>
  <td><select name="NextNode" size="6" multiple id="NextNode" style="width:115pt">
    <%
    List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID);
    Iterator itNode = listNode.iterator();
    while (itNode.hasNext()) {
      Node node = (Node)itNode.next();
      String strNodeName = node.getName();
      int intNextNodeID = node.getID();
      %>
      <option value="<%=intNextNodeID%>"><%=strNodeName %></option>
      <%
      }
    %>
  </select></td>
</tr>
</table>
<div align="center">
    <input name="TargetURL" type="hidden" id="TargetURL">
    <input name="ServiceType" type="hidden" id="ServiceType">
    <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.ddtest.DDJxp">
    <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
    <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
    <input name="YWID" type="hidden" value=""/>
    <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
    <input name="Participant" type="hidden" value=<%=Tools.toUTF8(request.getParameter("UserID"))%> id="Participant">
<br>
    <input type="button" name="Button" value="保存" onClick="submitForm(this.form, 'SaveOnly')">
    <input type="button" name="Button" value="转发" onClick="submitForm(this.form, 'SaveForward')">
    <input type="button" name="Button" value="退回" onClick="submitForm(this.form, 'Reject')"/>
    <input type="button" name="Button" value="自动转发" onClick="submitForm(this.form, 'AutoForward')"/>
    <input type="button" name="Button" value="查看流转记录" onClick="submitForm(this.form, 'ActionReport')">
    <input type="button" name="Button" value="中止工作流" onClick="submitForm(this.form, 'Cancel')">
	<%
	Node node=Node.searchNodeObject(intWorkflowID,intCurrNodeID);
	if (node.getType()==2){%>
      <input type="button" name="Button" value="完成工作流" onclick="submitForm(this.form, 'Over')">
	<%
	}
	%>
    <input type="reset" name="Reset" value="重写">
  </div>

</form>
</body>
</html>

