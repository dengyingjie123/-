<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检修管理</title>
<script language="javascript" type="text/javascript">
function callFS() {
  var form = document.form;
  var CurrNodeID = form.CurrentNode.value;
  window.open("fs.jsp?CurrNodeID="+CurrNodeID,"","width=550,height=300,scrollbars=yes");
}

function updateJxp() {
  var form = document.form;
  form.action = "updateJxp.jsp";
  form.target = "_blank";
  submitForm(form);
}
function callOver() {
  jxpID = document.form.YWID.value;
  window.open("over.jsp?ID="+jxpID,"","width=550,height=300");
}
function callCancel() {
  jxpID = document.form.YWID.value;
  window.open("cancel.jsp?ID="+jxpID,"","width=550,height=300");
}
function callCZGZ() {
  var form = document.form;
  var YWID = form.YWID.value;
  window.open("czgz.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
}
//退回到受理申请
function callBack() {
  if (window.confirm("是否退回到受理申请？")) {
    removeAllField();
    var form = document.form;
    var YWID = form.YWID.value;
    form.ServiceType.value = "SaveForward";
    form.TargetURL.value = "/jxp/ZD/done.jsp?target=12";
    form.action = "/workflow/WorkflowService";
    form.NextNode.value = "3";
    form.target = "_self";
    buildOneField("BZ", "调度执行 退回");
    form.submit();
  }
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
	  <td height="28" class="tdbg">&nbsp;<a href="../index.jsp">退出</a> <a href="javascript:callBack()">退回</a> <a href="javascript:callCZGZ()">操作跟踪</a> <a href="javascript:callCancel()">作废</a> <a href="javascript:callOver()">该票已执行</a> 打印模板 上一个 下一个</td>
	</tr>
	<tr>
	  <td><jsp:include flush="true" page="jxpTemp.jsp"></jsp:include></td>
  </tr>
</table>
</body>
</html>
