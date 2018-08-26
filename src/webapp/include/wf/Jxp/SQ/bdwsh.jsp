<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<%
int intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
String strUserID = (String)session.getAttribute("xingming");
String strOp = request.getParameter("Op");
int intCurrNodeID = Integer.MAX_VALUE;
if (strOp.equals("JXPSQ_XGAll")) {
  intCurrNodeID = 11;
}
else if (strOp.equals("JXPSQ_BDWSH") || strOp.equals("JXPSQ_CTX")) {
  intCurrNodeID = 2;
}
else {
  intCurrNodeID = 2;
}
int intRouteListID = Integer.MAX_VALUE;

String strYWID=request.getParameter("ID");
NewJxp jxp=new NewJxp();
jxp.setYWID(strYWID);
//jxp.setSQDW((String)session.getAttribute("dangwei"));
List ls=jxp.queryExact();
jxp=(NewJxp)ls.get(0);

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

List listRouteList = ClientApplications.getRouteListByYWIDAndNodeIDAndStatu(intWorkflowID,intCurrNodeID,strYWID,"ACTIVE");
RouteList rl = new RouteList();
if (listRouteList != null && listRouteList.size() > 0) {
  rl = (RouteList)listRouteList.get(0);
  intRouteListID = rl.getID();
}
%>
<HTML><HEAD><TITLE>检修票远程审核</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META http-equiv=pragma content=no-cache>

<META content="MSHTML 6.00.2900.2523" name=GENERATOR>
<script language="javascript" type="text/javascript">
var ywid = "<%=strYWID%>";
var workflowID = "<%=intWorkflowID%>";
<%
if (jxp.getSQR().equals(strUserID)) {
  out.println("var canSH = false;");
}
else {
  out.println("var canSH = true;");
}
%>

</script>
<script src="../script/common.js" type="text/javascript"></script>
<script src="../script/jxp.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function modify(mc, yj) {
  var td = document.all[mc+"td"];
  yj = Trim(yj);
  if (yj == "") {
    if (Trim(td.innerText) == "") {
      td.innerText = " ";
    }
  }
  else {
    if (td.innerText == "") {
      td.innerText += yj;
    }
    else {
      td.innerText += '\r'+ yj;
    }
  }
}
function modifyYJ(mc) {
  window.open("bdwshyj.jsp?ID="+mc,"","width=550,height=300");
}
function resetYJ(mc) {
  document.all[mc].innerText = "";
}

function submitForm(form, target) {
  var td = document.all['SQDWYJtd'];
  form.SQDWYJ.value = td.innerText;
  if (target == "ZDSH") {
    if (checkUser()) {
      form.BDWPZ.value = "1";
      form.TargetURL.value = "/jxp/SQ/Done.jsp";
      form.action = "/workflow/WorkflowService";
      form.NextNode.value = "3";
      form.ServiceType.value = "SaveForward";
      form.submit();
    }
  }
  else if (target == "Modify") {
    form.action = "sq.jsp?YWID="+ywid;
    form.submit();
  }
	else if (target == "XGDW") {
	  addAllField();
	  form.action='xgdwsh.jsp';
	  form.submit();
	}
}
function checkUser() {
  if (!canSH) {
    window.alert("申请人和审核人不能是同一人");
    return false;
  }
  else {
    return true;
  }
}

function Attach(){
  window.open("uploadFile.jsp?YWID="+ywid+"&WorkflowID="+workflowID+"&Address=GZNR","","width=550,height=300,scrollbars=yes");
}
function callXGDWYJ(id) {
  window.open("xgdwyj.jsp?ID="+id,"","width=550,height=300");
}
function deleteJxp() {
  window.open("deleteJxp.jsp?YWID="+ywid,"","width=550,height=300,scrollbars=yes");
}
</script>
<style type="text/css">
<!--
.bg {
	background-color: #333333;
	font-size: 12px;
}
.tdbg {
	background-color: #FFFFFF;
}
a:link {
	font-size: 12px;
	text-decoration: none;
}
a:hover {
	font-size: 12px;
	text-decoration: underline;
}
a:visited {
	font-size: 12px;
	text-decoration: none;
}
.style2 {font-size: 12}
.style10 {font-size: 12px}
-->
</style>
</HEAD>
<BODY text=#000000 bgColor=#ffffff leftMargin=0 topMargin=0>

<TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0 cellPadding=0
width="100%" border=0>
  <TBODY>
  <TR vAlign=top>
    <TD
    style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
    vAlign="middle" width="15%"><IMG height=83 src="../image/T1.jpg"
      width="100%" border="0" alt=""></TD>
    <TD vAlign="middle" width="88%">
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign="middle" width="100%" height=40><IMG
            src="../image/T1_WebDD.jpg" border=0 alt=""></TD></TR>
        <TR vAlign=top>
          <TD
          style="BORDER-RIGHT: rgb(0,0,0) 1px solid; BORDER-TOP: rgb(200,255,255) 1px solid; BORDER-LEFT: rgb(200,255,255) 1px solid; BORDER-BOTTOM: rgb(0,0,0) 1px solid"
          vAlign="middle" width="100%" bgColor=#339933 height=25>
            <TABLE style="FONT-SIZE: 11pt; FONT-FAMILY: 宋体" cellSpacing=0
            cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD align="center" width="10%"><A
                  href="index.jsp"><FONT
                  color=white>主页</FONT></A></TD>
                <TD align="center" width="10%"></TD>
                <TD align="center" width="10%"><a href="search.jsp"><FONT
                  color=white>检修票查询</FONT></A></TD>
                <TD align="center" width="10%"></TD>
                <TD align="center" width="10%"></TD>
                <TD align="center" width="10%"></TD>
                <TD align="center" width="10%"></TD></TR></TBODY></TABLE></TD></TR>
        <TR vAlign=top>
          <TD vAlign="middle" width="100%" bgColor=#5f5f5f height=20>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR vAlign=top>
                <TD width="58%" colSpan=2><span class="style2"><FONT
                  color=#ffffff>Welcome：<%=(String)session.getAttribute("dangwei")%></FONT> <FONT
                  color=#ffffff><%=(String)session.getAttribute("xingming")%></FONT> <FONT
                  color=#ffffff><%=Tools.getTime()%></FONT></span></TD>
                <TD width="42%">
                  <DIV align=right><a href="http://localhost/JXP_SQ.htm"
                  target=_blank><FONT color=#ffffff>帮助</FONT></A>
				  <a onclick='F_URL = "/" + document.forms[0].SYS_Path.value + "/User.nsf/ModifySQUserPassword?OpenForm&amp;UserID=" + document.forms[0].SYS_UserID.value&#13;width=400;height=250;LeftNum = (screen.width - width)/2;TopNum = (screen.height - height)/2;&#13;TX = "left=" + LeftNum + ",top=" + TopNum + ",width=" + width + ",height=" + height + ",status=no,scrollbars=no,resizable=yes";&#13;window.open(F_URL,"",TX);&#13;return false;'
                  href="http://localhost/DMIS/WebDD.nsf/"><FONT
                  color=#ffffff>修改密码</FONT></A> <a
                  onclick='SetCookie("SYS_USERUNID","")&#13;window.location.href = "/" + document.forms[0].SYS_Path.value + "/WebDD.nsf/Login?OpenForm"&#13;return false;'
                  href="loginout.jsp"><FONT
                  color=#ffffff>退出系统</FONT></A> <FONT
                color=#ffffff></FONT></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<form name="form" method="post" action="" id="form">
  <%
//菜单选择
if (strOp!=null&&strOp.equals("JXPSQ_BDWSH")||strOp.equals("JXPSQ_CTX"))
{
%>
<table style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%" border="0" cellspacing="0" cellpadding="0"> <tr valign="top"><td width="100%"><table border=0>
  <tr>
<td height="20" bgcolor="9F9FE0" STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)">
  <a href=index.jsp>&nbsp;<font color=blue>退 出</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
  <a href="javascript:submitForm(this.form,'Modify')">&nbsp;<font color=blue>修 改</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
  <a href=javascript:Attach()>&nbsp;<font color=blue>上传(删除)附件</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
  <a href=javascript:modifyYJ('SQDWYJ')>&nbsp;<font color=blue>申请单位审核意见</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
  <a href="javascript:submitForm(this.form,'ZDSH')">&nbsp;<font color=blue>审核通过并发送到中调</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">
  <a href=deleteJxp.jsp?YWID=<%=jxp.getYWID()%>>&nbsp;<font color=blue>删除该申请票</font>&nbsp;</a></td>
<td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'>
  <a target=_blank href=""><font color=blue>&nbsp;打印模板&nbsp;</font></td>
</tr></table> <%}
	if (strOp!=null&&strOp.equals("JXPSQ_XGAll"))
	{%>
	  <table style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr valign="top"><td width="100%"><table border=0><tr>
	  <td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=javascript:ExitToView()>&nbsp;<font color=blue>退出</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="javascript:submitForm(this.form, 'XGDW')">&nbsp;<font color=blue>发送到中调</font>&nbsp;</a></td>
	  <td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href="javascript:callXGDWYJ('XGDWYJ')">&nbsp;<font color=blue>相关单位填写意见</font>&nbsp;</a></td>
	  <td STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)' bgcolor='9F9FE0'><a target=_blank href=""><font color=blue>&nbsp;打印模板&nbsp;</font></td>
	  </tr></table>
	<%}%></td></tr>
</table><div align="center">
    <img src="../image/0jxp.jpg" width="406" height="36" alt=""><br>
  </div>

<div align="center">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      <td>
        <font size="2">编号 <%=jxp.getPH()%></font> <br>
        <table width="100%"  border="0" cellpadding="2" cellspacing="1" class="bg">
          <tr>
            <td width="3%" rowspan="8" class="tdbg"><p align="center">申</p>
              <p align="center">请</p></td>
            <td width="18%" height="25" class="tdbg"><div align="center">申请单位<%= ClientApplications.getFieldTitle(intWorkflowID,"SQDW") %></div></td>
            <td width="14%" class="tdbg" id="SQDW"><%=jxp.isEmptySQDW()? "&nbsp;" : jxp.getSQDW()%> </td>
            <td width="7%" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQR") %></div></td>
            <td width="10%" class="tdbg" id="SQR"><%=jxp.isEmptySQR()?"&nbsp;":jxp.getSQR() %> </td>
            <td width="9%" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"BDWSHR") %></div></td>
            <td width="12%" class="tdbg" id="BDWSHR"><%=jxp.isEmptyBDWSHR()?"&nbsp;":jxp.getBDWSHR()%> </td>
            <td width="9%" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQSJ") %></div></td>
            <td width="18%" class="tdbg" id="SQSJ"><%=jxp.isEmptySQSJ()?"&nbsp;":jxp.getSQSJ()%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDLXR") %></div></td>
            <td colspan="7" class="tdbg" id="TDLXR"><%=jxp.isEmptyTDLXR()?"&nbsp;":jxp.getTDLXR()%> ( 联系电话：<%=jxp.getLXDH()%> )</td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SBMC") %></div></td>
            <td colspan="7" class="tdbg" id="SBMC"><%=jxp.isEmptySBMC()?"&nbsp;":jxp.getSBMC()%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"GZNR") %></div></td>
            <td colspan="7" class="tdbg" id="GZNR"><%=jxp.isEmptyGZNR()?"&nbsp;":Tools.HTMLEncode(jxp.getGZNR())%><br>
			<%
                  for(int i=0;i<intGZNR;i++){
                    %>
                  <br>
                  <a href="FileDown.jsp?FileName=<%=strGZNRFile[i]%>&ID=<%=intGZNRID[i]%>"><%=strGZNRFile[i]%></a>
                  <%
                  }
                  %>
			</td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDFW") %></div></td>
            <td colspan="7" class="tdbg" id="TDFW"><%=jxp.isEmptyTDFW()?"&nbsp;":Tools.HTMLEncode(jxp.getTDFW())%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">申请停（复）电时间</div></td>
            <td colspan="7" class="tdbg"><table width="300"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="140" id="SQTDSJ"><span class="style10"><%=jxp.isEmptySQTDSJ()?"":jxp.getSQTDSJ()%></span></td>
                  <td><div align="left"><span class="style10">至 </span></div></td>
                  <td width="140" id="SQFDSJ"><span class="style10"><%=jxp.isEmptySQFDSJ()?"&nbsp;":jxp.getSQFDSJ()%></span></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"SQDWYJ") %></div></td>
            <td colspan="7" class="tdbg" id="SQDWYJtd"><%=jxp.isEmptySQDWYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getSQDWYJ())%></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%= ClientApplications.getFieldTitle(intWorkflowID,"XGDWYJ") %></div></td>
            <td colspan="7" class="tdbg" id="XGDWYJtd"><%=jxp.isEmptyXGDWYJ()?"&nbsp;":Tools.HTMLEncode(jxp.getXGDWYJ())%> </td>
          </tr>
          <tr>
            <td rowspan="7" class="tdbg"><p align="center">批</p>
              <p align="center">复</p></td>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "PZTDSJ")  == 0 ?"<a href=javascript:modifyTime('PZTFDSJ')>批准停（复）电时间</a>":"批准停（复）电时间"%></div></td>
            <td colspan="7" class="tdbg"><table width="300" border="0" cellpadding="0" cellspacing="0" >
                <tr class="tdbg">
                  <td width="140" id="PZTDSJ"><span class="style10"><%=jxp.isEmptyPZTDSJ()?"&nbsp;":jxp.getPZTDSJ()%></span></td>
                  <td><div align="left"><span class="style10">至</span></div></td>
                  <td width="140" id="PZFDSJ"><span class="style10"><%=jxp.isEmptyPZFDSJ()?"&nbsp;":jxp.getPZFDSJ()%> </span></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKYJ")  == 0 ? "<a href=javascript:modifyYJ('YYKYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"YYKYJ")+"</a>" :  ClientApplications.getFieldTitle(intWorkflowID,"YYKYJ") %></div></td>
            <td colspan="7" class="tdbg" id="YYKYJ"><%=jxp.isEmptyYYKYJ()?"&nbsp;":jxp.getYYKYJ()%><br>
			<%
                  //营运科意见   附件
                  for(int i=0;i<intYYK;i++){
                    %>
                  <a href="FileDown.jsp?FileName=<%=strYYKFile[i]%>&ID=<%=intYYKID[i]%>"><%=strYYKFile[i]%></a>
                  <%
                }
                %>
			</td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "YYKSHYJ")  == 0 ?"<a href=javascript:modifyYJ('YYKSHYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"YYKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"YYKSHYJ")%></div></td>
            <td colspan="7" class="tdbg" id="YYKSHYJ"><%=jxp.isEmptyYYKSHYJ()?"&nbsp;":jxp.getYYKSHYJ()%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKYJ")  == 0 ? "<a href=javascript:modifyYJ('JDKYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"JDKYJ")+"</a>": ClientApplications.getFieldTitle(intWorkflowID,"JDKYJ")%></div></td>
            <td colspan="7" class="tdbg" id="JDKYJ"><%=jxp.isEmptyJDKYJ()?"&nbsp;":jxp.getJDKYJ()%> <br>
			<%
                //继电科意见  附件
                  for(int i=0;i<intJDK;i++){
                    %>
                  <a href="FileDown.jsp?FileName=<%=strJDKFile[i]%>&ID=<%=intJDKID[i]%>"><%=strJDKFile[i]%></a>
                  <%
                }
                %>
			</td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "JDKSHYJ")  == 0 ?"<a href=javascript:modifyYJ('JDKSHYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"JDKSHYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"JDKSHYJ")%></div></td>
            <td colspan="7" class="tdbg" id="JDKSHYJ"><%=jxp.isEmptyJDKSHYJ()?"&nbsp;":jxp.getJDKSHYJ()%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "ZDHKYJ")  == 0 ?"<a href=javascript:modifyYJ('ZDHKYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"ZDHKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"ZDHKYJ")%></div></td>
            <td colspan="7" class="tdbg" id="ZDHKYJ"><%=jxp.isEmptyZDHKYJ()?"&nbsp;":jxp.getZDHKYJ()%> <br>
			<%
              //自动化科意见 附件
              for(int i=0;i<intZDHK;i++){
                %>
                  <a href="../SQ/FileDown.jsp?FileName=<%=strZDHKFile[i]%>&ID=<%=intZDHKID[i]%>"><%=strZDHKFile[i]%></a>
                  <%
                }
                %>
			</td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "LDYJ")  == 0 ?"<a href=javascript:modifyYJ('LDYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"LDYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"LDYJ")%></div></td>
            <td colspan="7" class="tdbg" id="LDYJ"><%=jxp.isEmptyLDYJ()?"&nbsp;":jxp.getLDYJ()%> </td>
          </tr>
          <tr>
            <td rowspan="8" class="tdbg"><p align="center">调</p>
                <p align="center">度</p>
                <p align="center">执</p>
              <p align="center">行</p></td>
            <td height="25" class="tdbg"><div align="center"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "DDKYJ")  == 0 ?"<a href=javascript:modifyYJ('DDKYJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"DDKYJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"DDKYJ")%></div></td>
            <td colspan="7" class="tdbg" id="DDKYJ"><%=jxp.isEmptyDDKYJ()?"&nbsp;":jxp.getDDKYJ()%> </td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">通知</div></td>
            <td colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZSJ")  == 0 ?"<a href=javascript:modify('TZSJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"TZSJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"TZSJ")%>：</td>
                  <td class="style10" id="TZSJ"><%=jxp.isEmptyTZSJ()?"&nbsp;":jxp.getTZSJ()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZJLR")  == 0 ?"<a href=javascript:modify('TZJLR')>"+ClientApplications.getFieldTitle(intWorkflowID,"TZJLR")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"TZJLR")%>：</td>
                  <td class="style10" id="TZJLR"><%=jxp.isEmptyTZJLR()?"&nbsp;":jxp.getTZJLR()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TZR")  == 0 ?"<a href=javascript:modify('TZR')>"+ClientApplications.getFieldTitle(intWorkflowID,"TZR")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"TZR")%>：</td>
                  <td class="style10" id="TZR"><%=jxp.isEmptyTZR()?"&nbsp;":jxp.getTZR()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">停电</div></td>
            <td colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, "TDSJ")  == 0 ?"<a href=javascript:modify('TDSJ')>"+ClientApplications.getFieldTitle(intWorkflowID,"TDSJ")+"</a>":ClientApplications.getFieldTitle(intWorkflowID,"TDSJ")%>：</td>
                  <td class="style10" id="TDSJ"><%=jxp.isEmptyTDSJ()?"&nbsp;":jxp.getTDSJ()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDSLR") %>：</td>
                  <td class="style10" id="TDSLR"><%=jxp.isEmptyTDSLR()?"&nbsp;":jxp.getTDSLR()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"TDDDY") %>：</td>
                  <td class="style10" id="TDDDY"><%=jxp.isEmptyTDDDY()?"&nbsp;":jxp.getTDDDY()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">开工</div></td>
            <td colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGSJ") %>：</td>
                  <td class="style10" id="KGSJ"><%=jxp.isEmptyKGSJ()?"&nbsp;":jxp.getKGSJ()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGSLR") %>：</td>
                  <td class="style10" id="KGSLR"><%=jxp.isEmptyKGSLR()?"&nbsp;":jxp.getKGSLR()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"KGDDY") %>：</td>
                  <td class="style10" id="KGDDY"><%=jxp.isEmptyKGDDY()?"":jxp.getKGDDY()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">完工</div></td>
            <td colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGSJ") %>：</td>
                  <td class="style10" id="WGSJ"><%=jxp.isEmptyWGSJ()?"&nbsp;":jxp.getWGSJ()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGSLR") %>：</td>
                  <td class="style10" id="WGSLR"><%=jxp.isEmptyWGSLR()?"&nbsp;":jxp.getWGSLR()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"WGDDY") %>：</td>
                  <td class="style10" id="WGDDY"><%=jxp.isEmptyWGDDY()?"&nbsp;":jxp.getWGDDY()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" class="tdbg"><div align="center">复电</div></td>
            <td colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDSJ") %>：</td>
                  <td class="style10" id="FDSJ"><%=jxp.isEmptyFDSJ()?"&nbsp;":jxp.getFDSJ()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDSLR") %>：</td>
                  <td class="style10" id="FDSLR"><%=jxp.isEmptyFDSLR()?"&nbsp;":jxp.getFDSLR()%></td>
                </tr>
            </table></td>
            <td colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"FDDDY") %>：</td>
                  <td class="style10" id="FDDDY"><%=jxp.isEmptyFDDDY()?"&nbsp;":jxp.getFDDDY()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td rowspan="2" class="tdbg"><div align="center">延期</div></td>
            <td height="25" colspan="3" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQSJ") %>：</td>
                  <td class="style10" id="YQSJ"><%=jxp.isEmptyYQSJ()?"&nbsp;":jxp.getYQSJ()%></td>
                </tr>
            </table></td>
            <td height="25" colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQSQR") %>：</td>
                  <td class="style10" id="YQSQR"><%=jxp.isEmptyYQSQR()?"&nbsp;":jxp.getYQSQR()%></td>
                </tr>
            </table></td>
            <td height="25" colspan="2" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQPZR") %>：</td>
                  <td class="style10" id="YQPZR"><%=jxp.isEmptyYQPZR()?"&nbsp;":jxp.getYQPZR()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="25" colspan="7" class="tdbg"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="style10"><%= ClientApplications.getFieldTitle(intWorkflowID,"YQYY") %>：</td>
                  <td class="style10" id="YQYY"><%=jxp.isEmptyYQYY()?"&nbsp;":jxp.getYQYY()%></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td class="tdbg"><p align="center">备</p>
              <p align="center">注</p></td>
            <td colspan="8" class="tdbg" id="BZ"><%=jxp.isEmptyBZ()?"&nbsp; ":jxp.getBZ()%> </td>
          </tr>
        </table>
        <br>
        <input name="SQDWYJ" type="hidden" id="SQDWYJ">
        <input name="BDWSHR" type="hidden" id="BDWSHR" value="<%=strUserID%>">
        <input name="BDWPZ" type="hidden" id="BDWPZ">
        <br>
        <input name="NextNode" type="hidden" id="NextNode">
        <input name="TargetURL" type="hidden" id="TargetURL">
        <input name="ServiceType" type="hidden" id="ServiceType">
        <input name="BizDaoName" type="hidden" id="BizDaoName" value="com.ydtf.dmis.jxp.NewJxp">
        <input name="CurrentNode" type="hidden" id="CurrentNode" value="<%=intCurrNodeID%>">
        <input name="WorkflowID" type="hidden" id="WorkflowID" value="<%=intWorkflowID%>">
        <input name="YWID" type="hidden" value="<%=strYWID%>"/>
        <input name="ID" type="hidden" value="<%=strYWID%>"/>
        <input name="RouteListID" type="hidden" id="RouteListID" value="<%=intRouteListID%>">
        <input name="Participant" type="hidden" value="<%=strUserID%>" id="Participant"></td>
    </tr>
  </table>

</div>
</form>
</body>
</html>
