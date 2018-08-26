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
//方式变更
Fsbg fsbg = new Fsbg();
if (strYWID != null && !strYWID.equals("")) {
  fsbg.setID(Integer.parseInt(strYWID));
  fsbg = fsbg.BuildObject();
  List listRL = ClientApplications.getRouteListByYWIDAndNodeIDAndStatu(intWorkflowID,Integer.MAX_VALUE, strYWID, "ACTIVE");
  RouteList rl = (RouteList)listRL.get(0);
  intRouteListID = rl.getID();
  intCurrNodeID = rl.getCurrentNode();
}
String strYJ = new String();
String strPZ = new String();
int intTitle = 0;  //0：营运科安排（退出 编辑 发送 操作跟踪 上传（下载）附件 发送到相关单位 删除变更单 打印）
                   //1：继电科（退出 发送 否决 操作跟踪 上传（下载）附件 打印）
                   //2：营运科审核、继电科审核、领导审核、调度审核（退出 发送 否决 操作跟踪 打印）
switch (intCurrNodeID) {
  case 1:
  case 2: strPZ = "YYKPZ";
          strYJ = "YYKYJ";
          intTitle = 0;
          break;
  case 3: strPZ = "YYKSHPZ";
          strYJ = "YYKSHYJ";
          intTitle = 2;
          break;
  case 4: strPZ = "JDKPZ";
          strYJ = "JDKYJ";
          intTitle = 1;
          break;
  case 5: strPZ = "JDKSHZP";
          strYJ = "JDKSHYJ";
          intTitle = 2;
          break;
  case 7: strPZ = "LDPZ";
          strYJ = "LDYJ";
          intTitle = 2;
          break;
  case 8: strPZ = "DDKPZ";
          strYJ = "DDKYJ";
          intTitle = 2;
          break;
}
//附件
FileLoad fl = new FileLoad();
fl.setWorkflowID(intWorkflowID);
fl.setYWID(strYWID);
fl.setAddress(strYJ);
List listFL = new ArrayList();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>方式变更单</title>
<script src="../script/jxp.js" type="text/javascript"></script>
<script src="../script/common.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
function callFS() {
  window.open("fsbg_fs.jsp?WorkflowID=3&CurrNodeID=<%=intCurrNodeID%>&PZ=<%=strPZ%>","","width=550,height=300");
}
function callFJ() {
  window.open("fsbg_fj.jsp?WorkflowID=3&CurrNodeID=<%=intCurrNodeID%>&PZ=<%=strPZ%>&YJ=<%=strYJ%>","","width=550,height=300");
}
function callCZGZ() {
  var form = document.form;
  var YWID = form.YWID.value;
  window.open("fsbg_czgz.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
}
function callFile() {
  var form = document.form;
  var YWID = form.YWID.value;
  var WorkflowID = form.WorkflowID.value;
  window.open("../SQ/uploadFile.jsp?YWID=<%=strYWID%>&WorkflowID=<%=intWorkflowID%>&Address=<%=strYJ%>","","width=600,height=450,scrollbars=yes");
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
    <%
    if (intTitle == 0) {
      %>
      <td height="20" class="tdbg"> &nbsp;<a href="../index.jxp">退出</a> <a href="fsbg_sq.jsp?YWID=<%=fsbg.getYWID()%>&isEdit=1">编辑</a> <a href="javascript:callFS()">发送</a> <a href="javascript:callCZGZ()">操作跟踪</a> <a href="javascript:callFile()">上传（删除）附件</a> <a href="fsbg_delete.jsp?YWID=<%=strYWID%>">删除变更单</a> 打印模板</td>
      <%
      }
      else if (intTitle == 1) {
        %>
        <td height="20" class="tdbg"> &nbsp;<a href="../index.jxp">退出</a> <a href="javascript:callFS()">发送</a> <a href="javascript:callFJ()">否决</a> <a href="javascript:callCZGZ()">操作跟踪</a> <a href="javascript:callFile()">上传（删除）附件</a></td>
        <%
        }
        else {
          %>
          <td height="20" class="tdbg"> &nbsp;<a href="../index.jxp">退出</a> <a href="javascript:callFS()">发送</a> <a href="javascript:callFJ()">否决</a> <a href="javascript:callCZGZ()">操作跟踪</a> 打印模板</td>
          <%
          }
          %>
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
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "XGDWYJ")==0?"<a href='javascript:modifyYJ(\"XGDWYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "XGDWYJ") %></td>
                        <td id="XGDWYJtd"><%=fsbg.getXGDWYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKYJ")==0?"<a href='javascript:modifyYJ(\"YYKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "YYKYJ") %></td>
                        <td>
                          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
                                <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></td>
                              </tr>
                              <%
                              }
                            %>
                          </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKSHYJ")==0?"<a href='javascript:modifyYJ(\"YYKSHYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "YYKSHYJ") %></td>
                        <td id="YYKSHYJtd"><%=fsbg.getYYKSHYJ()+ " &nbsp;"%></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKYJ")==0?"<a href='javascript:modifyYJ(\"JDKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "JDKYJ") %></td>
                        <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
                                <td><a href="../SQ/FileDown.jsp?FileName=<%=fl.getFileName()%>&ID=<%=fl.getID()%>"><%=fl.getFileName()%></td>
                              </tr>
                              <%
                              }
                            %>
                          </table></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKSHYJ")==0?"<a href='javascript:modifyYJ(\"JDKSHYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "JDKSHYJ") %></td>
                        <td id="JDKSHYJtd"><%=fsbg.getJDKSHYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LDYJ")==0?"<a href='javascript:modifyYJ(\"LDYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "LDYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "LDYJ") %></td>
                        <td id="LDYJtd"><%=fsbg.getLDYJ()+ " &nbsp;" %></td>
                      </tr>
                      <tr class="tdbg">
                        <td height="25"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDKYJ")==0?"<a href='javascript:modifyYJ(\"DDKYJ\")'>"+ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID, "DDKYJ") %></td>
                        <td id="DDKYJtd"><%=fsbg.getDDKYJ()+ " &nbsp;" %></td>
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
