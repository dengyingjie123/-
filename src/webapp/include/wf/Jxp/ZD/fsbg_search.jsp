<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="java.util.*" %>
<%
int intWorkflowID = 3;
//获得查询范围
String strViewID = request.getParameter("ViewID");
if (strViewID == null || strViewID.equals("")) {
  strViewID = "ALL";
}

List listFsbg = new ArrayList();
String strTitle = new String();
//查询处于调度执行的方式变更单
if (strViewID.equals("DDZX")) {
  strTitle = "调度执行的方式变更单";
  List listYWID = new ArrayList();
  listYWID = ClientApplications.getYWIDByNodeAndStatu(intWorkflowID, 9, 9, "ACTIVE");
  for (int i = 0; listYWID != null && i < listYWID.size(); i++) {
    String strYWID = (String)listYWID.get(i);
    Fsbg fsbg = new Fsbg();
    fsbg.setYWID(strYWID);
    fsbg = fsbg.BuildObject();
    listFsbg.add(fsbg);
  }
}
else if (strViewID.equals("ALL")) {
//查询所有的方式变更单
  strTitle = "所有方式变更单";
  Fsbg fsbg = new Fsbg();
  listFsbg = fsbg.queryExact();
}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>方式变更</title>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style3 {font-size: 12px; color: #009900; }
.style5 {font-size: 12px; }
-->
</style></head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
		<jsp:include page="title.jsp" flush="false" />
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="20">&nbsp;<a href="../index.jsp">退出</a></td>
  </tr>
</table></td>
  </tr>
  <tr>
    <td><div align="center"><%=strTitle%><br>
      <br>
      </div>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%"  border="0">
          <tr bgcolor="#C6C3C6">
            <td width="30%" height="20"><div align="center"><span class="style3">改变方式时间</span></div></td>
            <td><div align="center"><span class="style3">改变方式原因</span></div></td>
            </tr>
          <%
          for (int i = 0; listFsbg != null && i < listFsbg.size(); i++) {
            Fsbg fsbg = (Fsbg)listFsbg.get(i);
            String strColor = "#C6EFFF";
            if ((i % 2) == 0) {
              strColor = "#F7F3F7";
            }
            %>
            <tr bgcolor="<%=strColor%>">
              <td><div align="center" class="style5"><%=fsbg.getGBFSSJ() %></div></td>
              <td><div align="center" class="style5"><a href="fsbg.jsp?YWID=<%=fsbg.getYWID()%>"><%=fsbg.getGBFSYY() %></a></div></td>
              </tr>
            <%
            }
          %>
          <tr>
            <td colspan="2"><div align="right" class="tdbg">共 <%=listFsbg.size() %> 行 </div></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
