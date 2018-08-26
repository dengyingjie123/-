<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
String strViewID = request.getParameter("ViewID");
if (strViewID == null) {
  strViewID = "TZ";
}
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
List listFSBG = fsbg.getFsbg(9,9);
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
  .p9 {font-size:9pt}  .p10 {font-size:10pt;} body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</STYLE>
  <title>检修管理</title>
<%

%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
.style1 {color: #008000}
-->
</style>
</head>
<body text="#000000" bgcolor="#FFFFFF">

<form method="post" action="/DMIS/JXP.nsf/Home?OpenForm&amp;Seq=1" name="_Home">
<input type="hidden" name="__Click" value="0">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include page="title.jsp" flush="false" /></td>
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
<a href="ddzx.jsp?ViewID=ALL"><font color=blue>检修设备一览表</font></a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
<a href="ddzx.jsp?ViewID=FSBG"><font color=blue>方式变更单(<%=listFSBG.size()%>)</font></a></td>
</tr></table></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top">
  <td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" height=400 width="100%">
	<font size="2" color="#0021bf">
			<div align="center"><font color=blue size=3><%=strTitle%></font><br>
        <%
////////////////////    检修票列表  开始     /////////////////////////////////
if (!strViewID.equals("FSBG")) {
%>

    </div>
<Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
  <tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green">
    <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
      <font color=blue>申请单位</font></td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        票号</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
          检修设备名称</td>
      <td align=center nowrap bgcolor="#C0C0C0" STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>
        工作内容</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        停电范围</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        批准开工</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
      <a href=./View_DDZX?OpenForm&ViewID=JXP_DDZX_All><font color=blue>批准完工</font></a></td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        通知</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        停电</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
         开工</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        完工</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        复电</td>
      <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
        延期</td></tr>
      <%
      for (int i = 0; list != null && i < list.size(); i++) {
        jxp = (NewJxp)list.get(i);
        //String strType = jxp.getTYPE().equals("1")?"检修票":"新设备投产";
      %>
      <tr bgcolor=#F2F2F2>
        <td align=center width=5%><%=jxp.getSQDW()%></td>
        <td align=center width=5%><%=jxp.getPH()%></td>
        <td width=15% align=center bgcolor="#F2F2F2"><%=jxp.getSBMC() %></td>
        <td width=31% bgcolor="#F2F2F2">
          <a href="dd.jsp?YWID=<%=jxp.getYWID()%>&CurrNodeID=12"><font color=black>
          <%=jxp.getGZNR()%></font></a></td>
        <td width=15%><a href="dd.jsp?YWID=<%=jxp.getYWID()%>&CurrNodeID=12"><font color=black>
          <%=jxp.getTDFW()%></font></a></td>
        <td width=5% nowrap><%=jxp.getPZTDSJ()%></td>
	<td width=5% nowrap><%=jxp.getPZFDSJ()%></td>
        <td align=center width=4% nowrap>&nbsp;</td>
        <td align=center width=4% nowrap>&nbsp;</td>
        <td align=center width=4% nowrap>
          <img src='../image/actn010.gif' border='0' height='15' width='15' alt='已填'></td>
        <td align=center width=4% nowrap>&nbsp;</td>
        <td align=center width=4% nowrap>&nbsp;</td>
        <td align=center width=4% nowrap>
          <img src='../image/actn010.gif' border='0' height='15' width='15' alt='已填'></td></tr>
        <%
       }
       %>

      <tr><td align=right colspan=13><font color=black>共<font color=blue> <%=list.size()%> </font>行</font></td>
      </tr>
    </table>
      <div align="center">
        <%
////////////////////    检修票列表  完毕     /////////////////////////////////
}
else {
  ////////////////////    方式变更  开始     /////////////////////////////////
  %>
      </div>
      <table width="100%" cellpadding="2" cellspacing="1" class="tablebg">
      <tr>
        <td width="22%" bgcolor="#C0C0C0"><div align="center" class="txt_blue">改变方式时间</div></td>
        <td width="78%" bgcolor="#C0C0C0"><font color="#008000"><center>改变方式原因</center></font></td>
      </tr>
      <%
      for (int i = 0; list != null && i < list.size(); i++) {
        fsbg = (Fsbg)list.get(i);
        %>
        <tr bgcolor="#F2F2F2">
          <td><div align="center"><a href="fsbg_dd.jsp?YWID=<%=fsbg.getYWID()%>"><%=fsbg.getGBFSSJ()%></a></div></td>
          <td><div align="center"><a href="fsbg_dd.jsp?YWID=<%=fsbg.getYWID()%>"><%=fsbg.getGBFSYY() %></a></div></td>
        </tr>
        <%
        }
      %>
      <tr>
        <td colspan="2" class="tdbg"><div align="right">共 <%=list.size() %> 页</div></td>
      </tr>
    </table></td>
  </tr>
</table>
<%
  ////////////////////    方式变更  结束     /////////////////////////////////
}
%>




</body>
</html>
