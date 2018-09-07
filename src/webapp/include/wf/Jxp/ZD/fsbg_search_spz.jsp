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
List listYWID = new ArrayList();
List listFsbg = new ArrayList();
listYWID = ClientApplications.getYWIDByNodeAndStatu(intWorkflowID, 2, 8, "ACTIVE");
for (int i = 0; listYWID != null && i < listYWID.size(); i++) {
  String strYWID = (String)listYWID.get(i);
  Fsbg fsbg = new Fsbg();
  fsbg.setYWID(strYWID);
  fsbg = fsbg.BuildObject();
  listFsbg.add(fsbg);
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审批中的方式变更</title>
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
    <td><div align="center">      方式变更单流转一览表<br>
      <br>
      </div>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%"  border="0" cellpadding="2" cellspacing="1">
          <tr bgcolor="#C6C3C6">
            <td width="90" height="20"><div align="center"><span class="style3">改变方式时间</span></div></td>
            <td width="353"><div align="center"><span class="style3">改变方式原因</span></div></td>
            <td width="35"><div align="center"><span class="style3">营运</span></div></td>
            <td width="50"><div align="center"><span class="style3">营运审核</span></div></td>
            <td width="43"><div align="center"><span class="style3">保护</span></div></td>
            <td width="57"><div align="center"><span class="style3">保护审核</span></div></td>
            <td width="40"><div align="center"><span class="style3">自动化</span></div></td>
            <td width="35"><div align="center"><span class="style3">领导</span></div></td>
            <td width="52"><div align="center"><span class="style3">调度审核</span></div></td>
          </tr>
          <%
          for (int i = 0; listFsbg != null && i < listFsbg.size(); i++) {
            Fsbg fsbg = (Fsbg)listFsbg.get(i);
            List listRL = ClientApplications.getActiveYW(fsbg.getYWID(), intWorkflowID);
            RouteList rl = (RouteList)listRL.get(0);
            int node = rl.getCurrentNode();
            String strColor = "#C6EFFF";
            if ((i % 2) == 0) {
              strColor = "#F7F3F7";
            }
            %>
            <tr bgcolor="<%=strColor%>">
              <td><div align="center" class="style5"><%=fsbg.getGBFSSJ() %></div></td>
              <td><div align="center" class="style5"><%=fsbg.getGBFSYY() %></div></td>
              <td><div align="center" class="style5"><%=node==2?"<font color='green'>待办</font>":fsbg.isEmptyYYKYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
              <td><div align="center" class="style5"><%=node==3?"<font color='green'>待办</font>":fsbg.isEmptyYYKSHYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
              <td><div align="center" class="style5"><%=node==4?"<font color='green'>待办</font>":fsbg.isEmptyJDKYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
              <td><div align="center" class="style5"><%=node==5?"<font color='green'>待办</font>":fsbg.isEmptyJDKSHYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
              <td><div align="center" class="style5"><font color='red'>未转</font></div></td>
              <td><div align="center" class="style5"><%=node==7?"<font color='green'>待办</font>":fsbg.isEmptyLDYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
              <td><div align="center" class="style5"><%=node==8?"<font color='green'>待办</font>":fsbg.isEmptyDDKYJ()?"<font color='red'>未转</font>":"<font color='blue'>已办</font>" %></div></td>
            </tr>
            <%
            }
          %>
          <tr>
            <td colspan="9"><div align="right" class="tdbg">共 <%=listFsbg.size() %> 行 </div></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
