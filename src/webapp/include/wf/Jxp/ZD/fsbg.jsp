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

int intRouteListID = 0;
String strUserName = (String)session.getAttribute("xingming");
String strYWID = request.getParameter("YWID");  //获得业务编号
//方式变更
Fsbg fsbg = new Fsbg();
if (strYWID != null && !strYWID.equals("")) {
  fsbg.setID(Integer.parseInt(strYWID));
  fsbg = fsbg.BuildObject();
}
//附件
FileLoad fl = new FileLoad();
List listFL = new ArrayList();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>方式变更单</title>
<script src="../script/jxp.js" type="text/javascript"></script>
<script src="../script/common.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
function callCZGZ() {
  var form = document.form;
  var YWID = form.YWID.value;
  window.open("fsbg_czgz.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
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
    <td height="20" class="tdbg"> &nbsp;<a href="../index.jxp">退出</a> <a href="javascript:callCZGZ()">操作跟踪</a>  打印模板</td>
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
                        <td width="6%" height="20">&nbsp;<%=ClientApplications.getFieldTitle(intWorkflowID, "BZR") %></td>
                        <td width="35%" id="BZRtd"><%=fsbg.isEmptyBZR()?strUserName:fsbg.getBZR()%>
                          <input name="BZR" type="hidden" id="BZR" value="<%=strUserName%>"></td>
                        <td width="8%"><%=ClientApplications.getFieldTitle(intWorkflowID, "BZRQ") %></td>
                        <td width="16%"><%=Tools.getTime() %>
                          <input name="BZRQ" type="hidden" id="BZRQ" value="<%=Tools.getTime() %>"></td>
                        <td width="28%">&nbsp;</td>
                        <td width="7%">&nbsp;</td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
                      <tr class="tdbg">
                        <td width="140" height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSYY") %></td>
                        <td width="79%"><%=fsbg.getGBFSYY()%></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSSJ") %></td>
                        <td><%=fsbg.getGBFSSJ()%></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ") %></td>
                        <td id="XGDWYJtd"><%=Tools.HTMLEncode(fsbg.getXGDWYJ())%> &nbsp;</td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ") %></td>
                        <td>
                          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="tdbg" id="YYKYJtd"><%=Tools.HTMLEncode(fsbg.getYYKYJ()) %></td>
                            </tr>
                            <%
														fl.setWorkflowID(intWorkflowID);
                            fl.setYWID(strYWID);
                            fl.setAddress("YYKYJ");
                            listFL = fl.query();
                            for (int i = 0; listFL != null && i < listFL.size(); i++) {
                              fl = (FileLoad)listFL.get(i);
                              %>
                              <tr>
                                <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></td>
                              </tr>
                              <%
                              }
                            %>
                          </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ") %></td>
                        <td id="YYKSHYJtd"><%=Tools.HTMLEncode(fsbg.getYYKSHYJ())%> &nbsp;</td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ") %></td>
                        <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td height="20" class="tdbg" id="JDKYJtd"><%=Tools.HTMLEncode(fsbg.getJDKYJ())%> &nbsp;</td>
                            </tr>
                            <%
                            fl = new FileLoad();
                            fl.setWorkflowID(intWorkflowID);
                            fl.setYWID(strYWID);
                            fl.setAddress("JDKYJ");
                            listFL = fl.query();
                            for (int i = 0; listFL != null && i < listFL.size(); i++) {
                              fl = (FileLoad)listFL.get(i);
                              %>
                              <tr>
                                <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></td>
                              </tr>
                              <%
                              }
                            %>
                          </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ") %></td>
                        <td id="JDKSHYJtd"><%=Tools.HTMLEncode(fsbg.getJDKSHYJ())%> &nbsp;</td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "LDYJ") %></td>
                        <td id="LDYJtd"><%=Tools.HTMLEncode(fsbg.getLDYJ())%> &nbsp;</td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ") %></td>
                        <td id="DDKYJtd"><%=Tools.HTMLEncode(fsbg.getDDKYJ())+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldTitle(intWorkflowID, "DDZXQK") %></td>
                        <td><%=Tools.HTMLEncode(fsbg.getDDZXQK())+ " &nbsp;" %></td>
                      </tr>
                  </table></td>
                </tr>
              </table>
		<input name="ID" type="hidden" value="<%=strYWID%>">
    <!-- ============== 流转所需的参数 ==================== -->
    <input name="TargetURL" type="hidden" id="TargetURL">
    <input name="ServiceType" type="hidden" id="ServiceType">
    <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.Fsbg">
    <input name="CurrentNode" type="hidden" id="CurrentNode" value="">
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
