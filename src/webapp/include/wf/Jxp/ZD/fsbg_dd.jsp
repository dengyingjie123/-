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
int intCurrNodeID = 1;

int intRouteListID = 0;
String strUserName = (String)session.getAttribute("xingming");
String strYWID = request.getParameter("YWID");  //获得业务编号
Fsbg fsbg = new Fsbg();
if (strYWID != null && !strYWID.equals("")) {
  fsbg.setID(Integer.parseInt(strYWID));
  fsbg = fsbg.BuildObject();
  List listRL = ClientApplications.getRouteListByYWIDAndNodeIDAndStatu(intWorkflowID,Integer.MAX_VALUE, strYWID, "ACTIVE");
  RouteList rl = (RouteList)listRL.get(0);
  intRouteListID = rl.getID();
  intCurrNodeID = rl.getCurrentNode();
}
String strField = new String();
switch (intCurrNodeID) {
  case 1:
  case 2: strField = "YYKPZ"; break;
  case 3: strField = "YYKSHPZ"; break;
  case 4: strField = "JDKPZ"; break;
  case 5: strField = "JDKSHZP"; break;
  case 7: strField = "LDPZ"; break;
  case 8: strField = "DDKPZ"; break;
}
//附件
FileLoad fl = new FileLoad();
fl.setWorkflowID(intWorkflowID);
fl.setYWID(strYWID);
List listFL = new ArrayList();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>调度执行</title>
<script src="../script/jxp.js" type="text/javascript"></script>
<script src="../script/common.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
  function callFS() {
    window.open("fsbg_fs.jsp?WorkflowID=3&CurrNodeID=<%=intCurrNodeID%>&Field=<%=strField%>","","width=550,height=300");
  }
  function callOver() {
    if (window.confirm("是否将该方式变更单设置成已执行？")) {
      var value = "已执行";
      buildOneField("ZT", value);
      addAllField();
      form.ServiceType.value = "Over";
      form.TargetURL.value = "/jxp/ZD/done.jsp?Target=12";
      form.action = "/workflow/WorkflowService";
      form.submit();
    }
  }

  function callCancel() {
    if (window.confirm("是否将该方式变更单设置成作废？")) {
      var value = "已作废";
      buildOneField("ZT", value);
      addAllField();
      form.ServiceType.value = "Cancel";
      form.TargetURL.value = "/jxp/ZD/done.jsp?Target=12";
      form.action = "/workflow/WorkflowService";
      form.submit();
    }
  }
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
    <td height="20" class="tdbg"> &nbsp;<a href="../index.jsp">退出</a>  <a href="javascript:callFS()">发送</a> <a href="javascript:callCZGZ()">操作跟踪</a> <a href="javascript:callOver()">已执行</a> 打印模板</td>
  </tr>
  <tr>
    <td><div align="center"><img src="../image/fsbg1.jpg" width="454" height="36" alt="">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><form action="" method="post" name="form" id="form">
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="tdbg"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="6%" height="25">&nbsp;<%=ClientApplications.getFieldTitle(intWorkflowID, "BZR") %></td>
                        <td width="35%" id="BZRtd"><%=fsbg.isEmptyBZR()?strUserName:fsbg.getBZR()%>
                          <input name="BZR" type="hidden" id="BZR" value="<%=strUserName%>"></td>
                        <td width="8%"><%=ClientApplications.getFieldTitle(intWorkflowID, "BZRQ") %></td>
                        <td width="16%"><%=Tools.getTime() %>
                          <input name="BZRQ" type="hidden" id="BZRQ" value="<%=Tools.getTime() %>"></td>
                        <td width="28%">状态：</td>
                        <td width="7%">&nbsp;</td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><table width="100%"  border="0" cellpadding="2" cellspacing="1" class="tablebg">
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSYY") %></td>
                        <td><%=fsbg.getGBFSYY()%></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldTitle(intWorkflowID, "GBFSSJ") %></td>
                        <td><%=fsbg.getGBFSSJ()%></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "XGDWYJ")==0?"<a href='javascript:modifyYJ(\"XGDWYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ") %></td>
                        <td id="XGDWYJtd"><%=fsbg.getXGDWYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKYJ")==0?"<a href='javascript:modifyYJ(\"YYKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ") %></td>
                        <td id="YYKYJtd"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td class="tdbg" id="YYKYJtd"><%=Tools.HTMLEncode(fsbg.getYYKYJ()) %></td>
                          </tr>
                          <%
                            fl.setAddress("YYKYJ");
                            listFL = fl.query();
                            for (int i = 0; listFL != null && i < listFL.size(); i++) {
                              fl = (FileLoad)listFL.get(i);
                              %>
                          <tr>
                            <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></a></td>
                          </tr>
                          <%
                              }
                            %>
                        </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKSHYJ")==0?"<a href='javascript:modifyYJ(\"YYKSHYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ") %></td>
                        <td id="YYKSHYJtd"><%=fsbg.getYYKSHYJ()+ " &nbsp;"%></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKYJ")==0?"<a href='javascript:modifyYJ(\"JDKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ") %></td>
                        <td id="JDKYJtd"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="20" class="tdbg" id="JDKYJtd"><%=fsbg.getJDKYJ()+ " &nbsp;"%> </td>
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
                            <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></a></td>
                          </tr>
                          <%
                              }
                            %>
                        </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKSHYJ")==0?"<a href='javascript:modifyYJ(\"JDKSHYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ") %></td>
                        <td id="JDKSHYJtd"><%=fsbg.getJDKSHYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LDYJ")==0?"<a href='javascript:modifyYJ(\"LDYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "LDYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "LDYJ") %></td>
                        <td id="LDYJtd"><%=fsbg.getLDYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDKYJ")==0?"<a href='javascript:modifyYJ(\"DDKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ") %></td>
                        <td id="DDKYJtd"><%=fsbg.getDDKYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDZXQK")==0?"<a href='javascript:modifyYJ(\"DDZXQK\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "DDZXQK")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "DDZXQK") %></td>
                        <td id="DDZXQKtd"><%=fsbg.getDDZXQK()+ " &nbsp;" %></td>
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
