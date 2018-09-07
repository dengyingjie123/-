<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
String strViewID = request.getParameter("ViewID");
String strTitle = new String();
int intWorkflowID = 1;
NewJxp jxp = new NewJxp();
Fsbg fsbg = new Fsbg();
List list = new ArrayList();

List listJxp = new ArrayList();
List listRL = ClientApplications.getRouteListByNodeIDAndStatu(intWorkflowID, 12, "ACTIVE");
List listTZ = new ArrayList();
List listTD = new ArrayList();
List listKG = new ArrayList();
List listWG = new ArrayList();
List listFD = new ArrayList();
List listYFD = new ArrayList();
List listFSBG = new ArrayList();
listJxp = jxp.getJxpByNodeID(12, "ACTIVE");
for (int i = 0; listJxp != null && i < listJxp.size(); i++) {
  jxp = (NewJxp)listJxp.get(i);
  if (jxp.isEmptyTZSJ()) {
    listTZ.add(jxp);

  }
  else if (!jxp.isEmptyTZSJ() && jxp.isEmptyTDSJ()) {
    listTD.add(jxp);

  }
  else if (!jxp.isEmptyTZSJ() && !jxp.isEmptyTDSJ() && jxp.isEmptyKGSJ()) {
    listKG.add(jxp);

  }
  else if (!jxp.isEmptyTZSJ() && !jxp.isEmptyTDSJ() && !jxp.isEmptyKGSJ() &&
           jxp.isEmptyWGSJ()) {
    listWG.add(jxp);

  }
  else if (!jxp.isEmptyTZSJ() && !jxp.isEmptyTDSJ() && !jxp.isEmptyKGSJ() &&
           !jxp.isEmptyWGSJ() && jxp.isEmptyFDSJ()) {
    listFD.add(jxp);

  }
  else if (!jxp.isEmptyTZSJ() && !jxp.isEmptyTDSJ() && !jxp.isEmptyKGSJ() &&
           !jxp.isEmptyWGSJ() && !jxp.isEmptyFDSJ()) {
    listYFD.add(jxp);
  }
}
/*
if (strViewID != null && strViewID.equals("TZ")) {
  list = listTZ;
  strTitle = "待 答 复 票";
}
else if (strViewID != null && strViewID.equals("TD")) {
  list = listTD;
  strTitle = "待 停 电 票";
}
else if (strViewID != null && strViewID.equals("KG")) {
  list = listKG;
  strTitle = "待 开 工 票";
}
else if (strViewID != null && strViewID.equals("WG")) {
  list = listWG;
  strTitle = "已 开 工 票";
}
else if (strViewID != null && strViewID.equals("FD")) {
  list = listFD;
  strTitle = "已 完 工 票";
}
else if (strViewID != null && strViewID.equals("YFD")) {
  list = listYFD;
  strTitle = "已 复 电 票";
}
else if (strViewID != null && strViewID.equals("ALL")) {
  list = listJxp;
  strTitle = "调 度 执 行 票";
}
else if (strViewID != null && strViewID.equals("FSBG")) {
  list = fsbg.getFsbg(9,9);
	strTitle = "方 式 变 更 单";
}
*/
list = fsbg.getFsbg(9,9);
strTitle = "方 式 变 更 单";
%>
<html>
<head>
<meta http-equiv='pragma' content='no-cache'>
<STYLE type=text/css>
  A:link {text-decoration:none;}
  A:visited {text-decoration:none;}
  A:active {text-decoration:none;}
  A:hover {text-decoration:underline;}
  A.a01:link {text-decoration:underline;}
  A.a01:visited {text-decoration:underline;}
  A.a01:active {text-decoration:underline;}
  A.a01:hover {text-decoration:underline;}
  border-right-style: 1px solid rgb(0,0,0);
  border-top-style: 1px solid rgb(200,200,255);
  border-bottom-style: 1px solid rgb(0,0,0)
  table{font-size:9pt;} tr{font-size:9pt;line-height:1.5;}
  td{font-size:9pt;font-family:"宋体";line-height:1.5;}
  .p9 {font-size:9pt}  .p10 {font-size:10pt;} </STYLE>
  <title>检修管理</title>
<%

%>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0>

<form method="post" action="/DMIS/JXP.nsf/Home?OpenForm&amp;Seq=1" name="_Home">
<input type="hidden" name="__Click" value="0">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include page="title.jsp" flush="true" />&nbsp;</td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href=index.jsp><font color=blue>退 出</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=TZ"><font color=blue>待答复票(<%=listTZ.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=TD"><font color=blue>待停电票(<%=listTD.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=KG"><font color=blue>待开工票(<%=listKG.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=WG"><font color=blue>已开工票(<%=listWG.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=FD"><font color=blue>已完工票(<%=listFD.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=YFD"><font color=blue>已复电票(<%=listYFD.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=ALL"><font color=blue>调度执行所有票(<%=listJxp.size()%>)</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href=><font color=blue>检修设备一览表</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href=fsbg_ddzx.jsp?ViewID=FSBG><font color=blue>方式变更单(<%=list.size()%>)</font></a></td>
</tr></table></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top">
  <td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" height=400 width="100%">
	<font size="2" color="#0021bf">
	  <Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
		<tr>
		  <td width="105%" align=center style="line-height:1">
			<font color=blue size=3><%=strTitle%></font>
		  </td></tr>
			 <tr><td align=right><div align="left">
			   <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
           <tr class="tdbg">
             <td width="19%"><div align="center">改变方式时间</div></td>
             <td width="81%"><div align="center">改变方式原因</div></td>
           </tr>
           <%
           for (int i = 0; list != null && i < list.size(); i++) {
             fsbg = (Fsbg)list.get(i);
             %>
             <tr class="tdbg">
               <td><div align="center"><%=fsbg.getGBFSSJ()%></div></td>
               <td><div align="center"><%=fsbg.getGBFSYY()%></div></td>
             </tr>
             <%
             }
             %>
             </table>
           </div></td></tr>
			 <tr>
			   <td align=right><font color=black>共<font color=blue> <%=list.size()%> </font>行</font></td>
		    </tr>
	  </table>
</td></tr>
</table>





</body>
</html>
