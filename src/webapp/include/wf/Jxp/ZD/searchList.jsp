<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>

<%
String ViewID = request.getParameter("ViewID");
String strTitle = new String();
int intNodeID = Integer.MAX_VALUE;
List list = new ArrayList();
if (ViewID.equals("KGZ")) {
//查询开工中
  strTitle = "已 开 工 票";
  NewJxp jxp = new NewJxp();
  List listJxp = new ArrayList();
  listJxp = jxp.getJxpByNodeID(intNodeID, "ACTIVE");
  for (int i = 0; listJxp != null && i < listJxp.size(); i++) {
    jxp = (NewJxp)listJxp.get(i);
    if (!jxp.isEmptyKGSJ() && jxp.isEmptyWGSJ()) {
      list.add(jxp);
    }
  }
}
else if (ViewID.equals("YZX")) {
  //查询已执行
  strTitle = "已 完 工 票";
  NewJxp jxp = new NewJxp();
  List listJxp = new ArrayList();
  listJxp = jxp.getJxpByNodeID(intNodeID, "OVER");
  for (int i = 0; listJxp != null && i < listJxp.size(); i++) {
    jxp = (NewJxp)listJxp.get(i);
    list.add(jxp);
  }
}
else if (ViewID.equals("YZF")) {
  //查询已作废
  strTitle = "已 作 废 票";
  NewJxp jxp = new NewJxp();
  List listJxp = new ArrayList();
  listJxp = jxp.getJxpByNodeID(intNodeID, "CANCEL");
  for (int i = 0; listJxp != null && i < listJxp.size(); i++) {
    jxp = (NewJxp)listJxp.get(i);
    list.add(jxp);
  }
}
else if (ViewID.equals("ALL")) {
  //查询所有检修票
  strTitle = "所 有 检 修 票";
  NewJxp jxp = new NewJxp();
  jxp.setTYPE("1");
  list = jxp.queryExact();
}
else if (ViewID.equals("XSBTC")) {
  //查询所有新设备投产
  strTitle = "所有新设备投产";
  NewJxp jxp = new NewJxp();
  jxp.setTYPE("2");
  list = jxp.queryExact();
}
else if (ViewID.equals("Form")) {
  //根据表格提供的参数进行查询
  strTitle = "符 合 条 件 的 检 修 票";
  String strYWID = request.getParameter("YWID");
  String strSQDW = request.getParameter("SQDW");
  String strSBMC = request.getParameter("SBMC");
  String strKGSJStart = request.getParameter("KGSJStart");
  String strKGSJEnd = request.getParameter("KGSJEnd");
  String strGZNR = request.getParameter("GZNR");
  NewJxp jxp = new NewJxp();
  jxp.BuildObject(request);
  list = jxp.query();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询结果</title>
<style type="text/css">
<!--
.tablebg {
	background-color: #000000;
}
.tdbg {
	font-size: 12px;
	background-color: #FFFFFF;
}
-->
</style>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style3 {font-size: 12px; color: #009900; }
.style4 {font-size: 12px}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr>
    <td height="20"><p align="left">&nbsp;<a href="../index.jsp">退出</a>
    </p>
    </td></tr>
  <tr>
    <td height="30"><div align="center"><%=strTitle %></div></td>
  </tr>
  <tr>
    <td><table width="100%"  border="0" cellpadding="2" cellspacing="1">
      <tr bgcolor="#C6C3C6">
        <td width="10%" height="20"><div align="center"><span class="style3">申请单位</span></div></td>
        <td width="6%"><div align="center"><span class="style3">票号</span></div></td>
        <td width="18%"><div align="center"><span class="style3">检修设备名称</span></div></td>
        <td width="23%"><div align="center"><span class="style3">工作内容</span></div></td>
        <td width="23%"><div align="center"><span class="style3">停电范围</span></div></td>
        <td width="10%"><div align="center"><span class="style3">批准开工</span></div></td>
        <td width="10%"><div align="center"><span class="style3">批准完工</span></div></td>
        </tr>
      <%
      for (int i = 0; list != null && i < list.size(); i++) {
			  String strColor = "#E7FFFF";
				if ((i % 2) == 0) {
				  strColor = "#F7F3F7";
				}
        NewJxp jxp = (NewJxp)list.get(i);
        %>
        <tr bgcolor="<%=strColor%>">
          <td height="20" bgcolor="<%=strColor%>"><span class="style4"><%=jxp.isEmptySQDW()?" &nbsp; ": jxp.getSQDW()+jxp.getID() %></span></td>
          <td><span class="style4"><%=jxp.isEmptyPH()?" &nbsp; ": jxp.getPH() %></span></td>
          <td bgcolor="<%=strColor%>"><span class="style4"><a href="jxp.jsp?YWID=<%=jxp.getYWID()%>"><%=jxp.isEmptySBMC()?" &nbsp; ": jxp.getSBMC() %></a></span></td>
          <td><span class="style4"><a href="jxp.jsp?YWID=<%=jxp.getYWID()%>"><%=jxp.isEmptyGZNR()?" &nbsp; ": jxp.getGZNR() %></a></span></td>
          <td><span class="style4"><a href="jxp.jsp?YWID=<%=jxp.getYWID()%>"><%=jxp.isEmptyTDFW()?" &nbsp; ": jxp.getTDFW()%></a></span></td>
          <td><span class="style4"><a href="jxp.jsp?YWID=<%=jxp.getYWID()%>"><%=jxp.isEmptyPZTDSJ()?" &nbsp; ": jxp.getPZTDSJ() %></a></span></td>
          <td><span class="style4"><a href="jxp.jsp?YWID=<%=jxp.getYWID()%>"><%=jxp.isEmptyPZFDSJ()?" &nbsp; ": jxp.getPZFDSJ() %></a></span></td>
        </tr>
        <%
        }
      %>
    </table></td>
  </tr>
</table>
</body>
</html>
