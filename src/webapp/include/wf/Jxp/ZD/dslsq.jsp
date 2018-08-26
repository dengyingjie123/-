<%@ page contentType="text/html; charset=utf-8" %>
<%
String strYWID = request.getParameter("YWID");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检修管理</title>
<script src="../script/zd.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
function callFS() {
  var form = document.form;
	var field = "SLSQPZ";
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
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
	<tr>
	  <td height="28" class="tdbg">退出 <a href="javascript:callFS()" class="txt_blue">发送</a> <a href="javascript:callCZGZ()" class="txt_blue">操作跟踪</a> <a href="deleteJxp.jsp?YWID=<%=strYWID%>">删除申请票</a> <a href="javascript:modifyJXP()">修改申请票</a> 打印模板 上一个 下一个</td>
	</tr>
	<tr>
	  <td><jsp:include flush="true" page="jxpTemp.jsp"></jsp:include></td>
  </tr>
</table>
</body>
</html>
