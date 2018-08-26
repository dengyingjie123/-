<%@ page contentType="text/html; charset=utf-8" %>
<%
String strYWID = request.getParameter("YWID");
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
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
	<tr>
	  <td height="28" class="tdbg">&nbsp;<a href="../index.jsp">退出</a> <a href="javascript:callFS()" class="txt_blue">发送</a> <a href="javascript:callCZGZ()" class="txt_blue">操作跟踪</a> <a href="deleteJxp.jsp?YWID=<%=strYWID%>">删除检修票</a> <a href="javascript:modifyJXP()">修改检修票</a> <a href="javascript:callFile()" class="txt_blue">上传（删除）附件</a> <a href="javascript:callXGDW()">发送到相关单位</a> <a href="javascript:callCancel()">作废</a> 打印模板 上一个 下一个</td>
	</tr>
	<tr>
	  <td><jsp:include flush="true" page="jxpTemp.jsp"></jsp:include></td>
  </tr>
</table>
</body>
</html>
<script language="javascript">
var jxpID = document.form.YWID.value;
function setPH() {
  window.open("setPH.jsp?YWID="+jxpID,"","width=550,height=300,scrollbars=yes");
}
if (document.all.PHtd.innerText == "") {
  setPH();
}
</script>