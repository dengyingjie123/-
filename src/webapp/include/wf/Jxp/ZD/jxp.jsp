<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.processdefine.*" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
int intWorkflowID = 1;
String strYWID = request.getParameter("YWID");
NewJxp jxp = new NewJxp();
jxp.setYWID(strYWID);
jxp = jxp.BuildObject();



//根据业务编号得到上传的文件名,分为工作内容、营运科、继电科、自动化科上传的附件
FileLoad fileLoad = new FileLoad();
fileLoad.setYWID(strYWID);
List listFile = fileLoad.query();
Iterator itFile = listFile.iterator();
String strGZNRFile[] = new String[listFile.size()];
String strYYKFile[] = new String[listFile.size()];
String strJDKFile[] = new String[listFile.size()];
String strZDHKFile[] = new String[listFile.size()];
int intGZNRID[] = new int[listFile.size()];
int intYYKID[] = new int[listFile.size()];
int intJDKID[] = new int[listFile.size()];
int intZDHKID[] = new int[listFile.size()];
int intGZNR = 0;
int intYYK = 0;
int intJDK = 0;
int intZDHK = 0;
String strAddress = "";
while(itFile.hasNext()){
  fileLoad = (FileLoad)itFile.next();
  strAddress = fileLoad.getAddress();
  if(strAddress.equals("GZNR")){
    strGZNRFile[intGZNR] = fileLoad.getFileName();
    intGZNRID[intGZNR] = fileLoad.getID();
    intGZNR++;
  }
  if(strAddress.equals("YYKYJ")){
    strYYKFile[intYYK] = fileLoad.getFileName();
    intYYKID[intYYK] = fileLoad.getID();
    intYYK++;
  }
  if(strAddress.equals("JDKYJ")){
    strJDKFile[intJDK] = fileLoad.getFileName();
    intJDKID[intJDK] = fileLoad.getID();
    intJDK++;
  }
  if(strAddress.equals("ZDHKYJ")){
    strZDHKFile[intZDHK] = fileLoad.getFileName();
    intZDHKID[intZDHK] = fileLoad.getID();
    intZDHK++;
  }
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检修管理</title>
<script language="javascript">
function callFS() {
  var form = document.form;
	var field = "YYKPZ";
	var CurrNodeID = form.CurrentNode.value;
//  window.alert(form.YWID.value);
  window.open("fs.jsp?CurrNodeID="+CurrNodeID+"&Field="+field,"","width=550,height=300,scrollbars=yes");
}
function callCZGZ() {
  var form = document.form;
	var YWID = form.YWID.value;
	window.open("czgz.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
}

//上传附件
function callFile() {
  var form = document.form;
	var YWID = form.YWID.value;
	var WorkflowID = form.WorkflowID.value;
	window.open("../SQ/uploadFile.jsp?YWID="+YWID+"&WorkflowID="+WorkflowID+"&Address=YYKYJ","","width=600,height=450,scrollbars=yes");
}
function callCancel() {
	jxpID = document.form.YWID.value;
	window.open("cancel.jsp?ID="+jxpID,"","width=550,height=300");
}
function callXGDW() {
  window.open("selectHGDW.jsp?","","width=600,height=450,scrollbars=yes");
}
function setXGDW(value) {
  buildOneField("XGDWMC", value);
}
function fsSubmitForm() {
  var target = getQuery("CurrNodeID");
  form.ServiceType.value = "SaveForward";
  form.TargetURL.value = "/jxp/ZD/done.jsp?target="+target;
  form.action = "/workflow/WorkflowService";
  form.NextNode.value = "11";
	form.submit();
}
</script>
<link href="../css/common.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
		

		<jsp:include flush="false" page="title.jsp"></jsp:include>
		
		
		</td>
  </tr>
	<tr>
	  <td height="28" class="tdbg">&nbsp;<a href="../index.jsp">退出</a>  <a href="javascript:callCZGZ()" class="txt_blue">操作跟踪</a></td>
	</tr>
	<tr>
	  <td>
		
		
		
		
		
		
		
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="28"><div align="center"><img src="../image/0jxp.jpg" width="406" height="36" alt=""></div></td>
          </tr>
          <tr>
            <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="28"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr class="tdbg">
                      <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr class="tdbg">
                            <td width="52">编号：</td>
                            <td id="PHtd"><%=jxp.isEmptyPH()?"":jxp.getPH()%></td>
                        </tr>
                        </table></td>
                  </tr>
                  </table></td>
                <td><div align="right"></div></td>
              </tr>
              <tr>
                <td colspan="2">
                  <table width="100%" cellpadding="2" cellspacing="1" class="tablebg">
                    <tr class="tdbg">
                      <td width="3%" rowspan="8"><p align="center">申</p>
                          <p align="center">请</p></td>
                      <td width="18%" height="25"><div align="center">申请单位<%= ClientApplications.getFieldTitle(intWorkflowID,"SQDW") %></div></td>
                      <td width="14%" id="SQDW"><%=jxp.isEmptySQDW()? "&nbsp;" : jxp.getSQDW()%>
                      </td>
                      <td width="7%"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQR") %></td>
                      <td width="10%" id="SQR"><%=jxp.isEmptySQR()?"&nbsp;":jxp.getSQR() %>
                      </td>
                      <td width="9%"><%= ClientApplications.getFieldTitle(intWorkflowID,"BDWSHR") %></td>
                      <td width="12%" id="BDWSHR"><%=jxp.isEmptyBDWSHR()?"&nbsp;":jxp.getBDWSHR()%>
                      </td>
                      <td width="9%"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQSJ") %></td>
                      <td width="18%" id="SQSJ"><%=jxp.isEmptySQSJ()?"&nbsp;":jxp.getSQSJ()%>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDLXR") %></div></td>
                      <td colspan="7" class="tdbg" id="TDLXR"><%=jxp.isEmptyTDLXR()?"&nbsp;":jxp.getTDLXR()%>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SBMC") %></div></td>
                      <td colspan="7" class="tdbg" id="SBMC"><%=jxp.isEmptySBMC()?"&nbsp;":jxp.getSBMC()%>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg" id="GZNR"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"GZNR") %></div></td>
                      <td colspan="7" class="tdbg">					              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdbg" id="GZNRtd"><%=jxp.isEmptyGZNR()?"&nbsp;":jxp.getGZNR()%></td>
              </tr>
              <tr>
                <td><%
                  for(int i=0;i<intGZNR;i++){
                    %>
                  <br>
                  <a href="../SQ/FileDown.jsp?FileName=<%=strGZNRFile[i]%>&ID=<%=intGZNRID[i]%>"><%=strGZNRFile[i]%></a>
                  <%
                  }
                  %></td>
              </tr>
            </table>
          </td>
                    </tr>
<!--  ====== 停电范围 ============================= -->
                    <tr>
                      <td height="25" class="tdbg" id="TDFW"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDFW") %></div></td>
                      <td colspan="7" class="tdbg" id="TDFWtd"><%=jxp.isEmptyTDFW()?"&nbsp;":Tools.HTMLEncode(jxp.getTDFW())%>
                      </td>
                    </tr>
<!--  ====== 申请停（复）电时间 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center">申请停（复）电时间</div></td>
                      <td colspan="7" class="tdbg"> <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr class="tdbg">
                            <td width="20%" id="SQTDSJtd"><%=jxp.isEmptySQTDSJ()?"":jxp.getSQTDSJ()%>                              </td>
                            <td width="5%">至                              </td>
                            <td width="75%" id="SQFDSJtd"><%=jxp.isEmptySQFDSJ()?"&nbsp;":jxp.getSQFDSJ()%></td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<!--  ====== 申请单位意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQDWYJ") %></div></td>
                      <td colspan="7" class="tdbg" id="SQDWYJ"><%=jxp.isEmptySQDWYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getSQDWYJ())%>
                      </td>
                    </tr>
<!--  ====== 相关单位意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"XGDWYJ") %></div></td>
                      <td colspan="7" class="tdbg" id="XGDWYJ"><%=jxp.isEmptyXGDWYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getXGDWYJ())%>
                      </td>
                    </tr>
                    <tr class="tdbg">
                      <td rowspan="7"><p align="center">批</p>
                          <p align="center">复</p></td>
<!--  ====== 批准停（复）电时间 ============================= -->
                      <td height="25"><div align="center">批准停（复）电时间
                      </div></td>
                      <td colspan="7"> <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
                            <td width="20%" id="PZTDSJtd"><%=jxp.isEmptyPZTDSJ()?"&nbsp;":jxp.getPZTDSJ()%></td>
                            <td width="5%">至</td>
                            <td width="75%" id="PZFDSJtd"><%=jxp.isEmptyPZFDSJ()?"&nbsp;":jxp.getPZFDSJ()%> </td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<!--  ====== 营运科意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"YYKYJ") %>
                      </div></td>
                      <td colspan="7" class="tdbg">
                      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdbg" id="YYKYJtd"><%=jxp.isEmptyYYKYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getYYKYJ())%></td>
              </tr>
              <tr>
                <td>
                  <%
                  //营运科意见   附件
                  for(int i=0;i<intYYK;i++){
                    %>
                  <a href="../SQ/FileDown.jsp?FileName=<%=strYYKFile[i]%>&ID=<%=intYYKID[i]%>"><%=strYYKFile[i]%></a>
                  <%
                }
                %></td>
              </tr>
            </table>
          </td>
                    </tr>
<!--  ====== 营运科审核意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"YYKSHYJ")%>
                      </div></td>
                      <td colspan="7" class="tdbg" id="YYKSHYJtd"><%=jxp.isEmptyYYKSHYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getYYKSHYJ())%>
                      </td>
                    </tr>
<!--  ====== 继电科意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"JDKYJ")%>
                      </div></td>
                      <td colspan="7" class="tdbg">
					              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdbg" id="JDKYJtd"><%=jxp.isEmptyJDKYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getJDKYJ())%></td>
              </tr>
              <tr>
                <td><%
                //继电科意见  附件
                  for(int i=0;i<intJDK;i++){
                    %>
                  <a href="../SQ/FileDown.jsp?FileName=<%=strJDKFile[i]%>&ID=<%=intJDKID[i]%>"><%=strJDKFile[i]%></a>
                  <%
                }
                %></td>
              </tr>
            </table>
          </td>
                    </tr>
<!--  ====== 继电科审核意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"JDKSHYJ")%>
                      </div></td>
                      <td colspan="7" class="tdbg" id="JDKSHYJtd"><%=jxp.isEmptyJDKSHYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getJDKSHYJ())%>
                      </td>
                    </tr>
<!--  ====== 自动化科意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"ZDHKYJ")%>
                      </div></td>
                      <td colspan="7" class="tdbg">
                      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdbg" id="ZDHKYJtd"><%=jxp.isEmptyZDHKYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getZDHKYJ())%></td>
              </tr>
              <tr>
              <td><%
              //自动化科意见 附件
              for(int i=0;i<intZDHK;i++){
                %>
                  <a href="../SQ/FileDown.jsp?FileName=<%=strZDHKFile[i]%>&ID=<%=intZDHKID[i]%>"><%=strZDHKFile[i]%></a>
                  <%
                }
                %></td>
              </tr>
            </table>
          </td>
                    </tr>
<!--  ====== 领导意见 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"LDYJ")%>
                      </div></td>
                      <td colspan="7" class="tdbg" id="LDYJtd"><%=jxp.isEmptyLDYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getLDYJ())%>
                      </td>
                    </tr>
                    <tr class="tdbg">
                      <td rowspan="8"><p align="center">调</p>
                          <p align="center">度</p>
                          <p align="center">执</p>
                          <p align="center">行</p></td>
<!--  ====== 调度科意见 ============================= -->
                      <td height="25"><div align="center"><%=ClientApplications.getFieldTitle(intWorkflowID,"DDKYJ")%>
                      </div></td>
                      <td colspan="7" id="DDKYJtd"><%=jxp.isEmptyDDKYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getDDKYJ())%>
                      </td>
                    </tr>
<!--  ====== 通知时间 ============================= -->
                    <tr>
                      <td height="25" class="tdbg"><div align="center">通知</div></td>
                      <td colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
                            <td width="70"><%=ClientApplications.getFieldTitle(intWorkflowID,"TZSJ")%>：</td>
                            <td id="TZSJtd"><%=jxp.isEmptyTZSJ()?"&nbsp;":jxp.getTZSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
<!--  ====== 停电接令人 ============================= -->
                          <tr class="tdbg">
                            <td width="80"><%=ClientApplications.getFieldTitle(intWorkflowID,"TZJLR")%>：</td>
                            <td id="TZJLRtd"><%=jxp.isEmptyTZJLR()?"&nbsp;":jxp.getTZJLR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
<!--  ====== 通知人 ============================= -->
                          <tr class="tdbg">
                            <td width="70"><%=ClientApplications.getFieldTitle(intWorkflowID,"TZR")%>：</td>
                            <td id="TZRtd"><%=jxp.isEmptyTZR()?"&nbsp;":jxp.getTZR()%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center">停电</div></td>
                      <td colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 停电时间 ============================= -->
                            <td width="70"><%=ClientApplications.getFieldTitle(intWorkflowID,"TDSJ")%>：</td>
                            <td id="TDSJtd"><%=jxp.isEmptyTDSJ()?"&nbsp;":jxp.getTDSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 停电受令人 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDSLR") %>：</td>
                            <td id="TDSLRtd"><%=jxp.isEmptyTDSLR()?"&nbsp;":jxp.getTDSLR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 停电调度员 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDDDY") %>：</td>
                            <td id="TDDDYtd"><%=jxp.isEmptyTDDDY()?"&nbsp;":jxp.getTDDDY()%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center">开工</div></td>
                      <td colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 开工时间 ============================= -->
                            <td width="70"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGSJ") %>：</td>
                            <td id="KGSJtd"><%=jxp.isEmptyKGSJ()?"&nbsp;":jxp.getKGSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 开工受令人 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGSLR") %>：</td>
                            <td id="KGSLRtd"><%=jxp.isEmptyKGSLR()?"&nbsp;":jxp.getKGSLR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 开通调度员 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGDDY") %>：</td>
                            <td id="KGDDYtd"><%=jxp.isEmptyKGDDY()?"":jxp.getKGDDY()%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center">完工</div></td>
                      <td colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 完工时间  ============================= -->
                            <td width="70"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGSJ") %>：</td>
                            <td id="WGSJtd"><%=jxp.isEmptyWGSJ()?"&nbsp;":jxp.getWGSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ======  完工受令人 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGSLR") %>：</td>
                            <td id="WGSLRtd"><%=jxp.isEmptyWGSLR()?"&nbsp;":jxp.getWGSLR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 完工调度员 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGDDY") %>：</td>
                            <td id="WGDDYtd"><%=jxp.isEmptyWGDDY()?"&nbsp;":jxp.getWGDDY()%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="25" class="tdbg"><div align="center">复电</div></td>
                      <td colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 复电时间  ============================= -->
                            <td width="70"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDSJ") %>：</td>
                            <td id="FDSJtd"><%=jxp.isEmptyFDSJ()?"&nbsp;":jxp.getFDSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 复电受令人 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDSLR") %>：</td>
                            <td id="FDSLRtd"><%=jxp.isEmptyFDSLR()?"&nbsp;":jxp.getFDSLR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 复电调度员 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDDDY") %>：</td>
                            <td id="FDDDYtd"><%=jxp.isEmptyFDDDY()?"&nbsp;":jxp.getFDDDY()%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td rowspan="2" class="tdbg"><div align="center">延期</div></td>
                      <td height="25" colspan="3" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 延期时间 ============================= -->
                            <td width="70"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQSJ") %>：</td>
                            <td id="YQSJtd"><%=jxp.isEmptyYQSJ()?"&nbsp;":jxp.getYQSJ()%></td>
                          </tr>
                        </table>
                      </td>
                      <td height="25" colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 延期申请人  ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQSQR") %>：</td>
                            <td id="YQSQRtd"><%=jxp.isEmptyYQSQR()?"&nbsp;":jxp.getYQSQR()%></td>
                          </tr>
                        </table>
                      </td>
                      <td height="25" colspan="2" class="tdbg">
                        <table width="230" border="0" cellpadding="0" cellspacing="0" >
                          <tr class="tdbg">
<!--  ====== 延期批准人 ============================= -->
                            <td width="80"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQPZR") %>：</td>
                            <td id="YQPZRtd"><%=jxp.isEmptyYQPZR()?"&nbsp;":jxp.getYQPZR()%></td>
                          </tr>
                        </table>
                      </td>

                    </tr>
                    <tr>
                      <td height="25" colspan="7" class="tdbg"> <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                        <tr class="tdbg">
<!--  ====== 延期原因 ============================= -->
                          <td width="60"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQYY") %>：</td>
                          <td width="645" id="YQYYtd"><%=jxp.isEmptyYQYY()?"&nbsp;":jxp.getYQYY()%></td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr class="tdbg">
<!--  ====== 备注 ============================= -->
                      <td><p align="center">备</p>
                          <p align="center">注</p></td>
                      <td colspan="8" id="BZtd"><%=jxp.isEmptyBZ()?"&nbsp; ":jxp.getBZ()%>
                      </td>
                    </tr>
                  </table>

                  <form action="" method="post" name="form" id="form">
                    <input name="YWID" type="hidden" id="YWID" value="<%=jxp.getYWID()%>">
                  </form>
               
              
            </table>
            </td>
          </tr>
        </table>		
		</td>
  </tr>
</table>
</body>
</html>