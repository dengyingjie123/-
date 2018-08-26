<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.ydtf.dmis.jxp.Fsbg" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.engines.*" %>
<%@ page import="java.util.*" %>
<%
int intWorkflowID = 3;
int intCurrNodeID = 1;
int intRouteListID = 0;
String strUserName = (String)session.getAttribute("xingming");
String strYWID = request.getParameter("YWID");  //获得业务编号
Fsbg fsbg = new Fsbg();
if (strYWID != null && !strYWID.equals("")) {
  fsbg.setID(Integer.parseInt(strYWID));
  fsbg = fsbg.BuildObject();
}
else {
  strYWID = String.valueOf(Fsbg.getMaxID());
}
StringBuffer sbGbsfsj = new StringBuffer();
sbGbsfsj.append(fsbg.getGBFSSJ());
if (sbGbsfsj.length() > 19) {
  sbGbsfsj.delete(19, sbGbsfsj.length());
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新建方式变更单</title>
<script src="../script/common.js" type="text/javascript"></script>
<script src="../script/jxp.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
var isEdit = getQuery("isEdit");
function callFS() {
  var form = document.form;
  if (isEdit == "1") {
    form.action = "fsbg_save.jsp";
    form.submit();
  }
  else {
    form.action = "/workflow/WorkflowService";
    form.TargetURL.value = "/jxp/ZD/fsbg_lzsp_done.jsp";
    form.ServiceType.value = "SaveForward";
    form.NextNode.value = "2";
    form.submit();
  }
//  window.open("fsbg_fs.jsp?WorkflowID=3&CurrNodeID=1","","width=550,height=300");
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
  <tr>
    <td height="20" class="tdbg"> &nbsp;<a href="../index.jsp">退出</a> <a href="javascript:callFS()">保存</a></td>
  </tr>
  <tr>
    <td><div align="center"><img src="../image/fsbg1.jpg" width="454" height="36" alt="">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><form action="" method="post" name="form" id="form">
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr class="tdbg">
                        <td width="6%" height="25">&nbsp;<%=ClientApplications.getFieldTitle(intWorkflowID, "BZR") %></td>
                        <td width="35%" id="BZRtd"><%=fsbg.isEmptyBZR()?strUserName:fsbg.getBZR()%>
                          <input name="BZR" type="hidden" id="BZR" value="<%=strUserName%>"></td>
                        <td width="8%"><%=ClientApplications.getFieldTitle(intWorkflowID, "BZRQ") %></td>
                        <td width="16%"><%=Tools.getTime() %>
                          <input name="BZRQ" type="hidden" id="BZRQ" value="<%=Tools.getTime() %>"></td>
                        <td width="28%"><div align="right"></div></td>
                        <td width="7%">&nbsp;</td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSYY") %></td>
                        <td><input name="GBFSYY" type="text" id="GBFSYY" value="<%=fsbg.getGBFSYY()%>"></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSSJ") %></td>
                        <td><input name="GBFSSJ" type="text" id="GBFSSJ" value="<%=fsbg.isEmptyGBFSSJ()?Tools.getTime():sbGbsfsj.toString()%>"></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ") %></td>
                        <td><%=fsbg.getXGDWYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ") %></td>
                        <td><textarea name="YYKYJ" cols="50" rows="5" wrap="VIRTUAL" id="YYKYJ"><%=fsbg.getYYKYJ() %></textarea></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ") %></td>
                        <td><%=fsbg.getYYKSHYJ()+ " &nbsp;"%></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ") %></td>
                        <td><%=fsbg.getJDKYJ()+ " &nbsp;"%></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ") %></td>
                        <td><%=fsbg.getJDKSHYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "LDYJ") %></td>
                        <td><%=fsbg.getLDYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ") %></td>
                        <td><%=fsbg.getDDKYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "DDZXQK") %></td>
                        <td><%=fsbg.getDDZXQK()+ " &nbsp;" %></td>
                      </tr>
                  </table></td>
                </tr>
              </table>
    <input name="ID" type="hidden" value="<%=strYWID%>">
    <!-- ============== 流转所需的参数 ==================== -->
    <input name="TargetURL" type="hidden" id="TargetURL">
    <input name="ServiceType" type="hidden" id="ServiceType">
    <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.Fsbg">
    <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
    <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
    <input name="YWID" type="hidden" value="<%=strYWID%>"/>
    <input name="NextNode" type="hidden" value="">
    <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
    <input name="Participant" type="hidden" value="<%=Tools.toUTF8(strUserName)%>" id="Participant">
    <!-- ================================================= -->
            </form></td>
          </tr>
        </table>
    </div></td>
  </tr>
</table>
</body>
</html>
