<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检修管理</title>
<script language="javascript">
function callFS() {
  var form = document.form;
	var field = "LDPZ";
	var CurrNodeID = form.CurrentNode.value;
//  window.alert(form.YWID.value);
  window.open("fs.jsp?CurrNodeID="+CurrNodeID+"&Field="+field,"","width=550,height=300,scrollbars=yes");
}
function callCZGZ() {
  var form = document.form;
	var YWID = form.YWID.value;
	window.open("czgz.jsp?YWID="+YWID,"","width=600,height=450,scrollbars=yes");
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
	  <td height="28" class="tdbg">&nbsp;<a href="../index.jsp">退出</a> <a href="javascript:callFS()">发送</a> <a href="javascript:callCZGZ()">操作跟踪</a> 打印模板 上一个 下一个</td>
	</tr>
	<tr>
	  <td><jsp:include flush="true" page="jxpTemp.jsp"></jsp:include></td>
  </tr>
</table>
</body>
</html>