<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>检修管理</title>
<script language="javascript">
function callFS() {
  var form = document.form;
//	var field = "JDKSHPZ";
	var CurrNodeID = form.CurrentNode.value;
//  window.alert(form.YWID.value);
//  window.open("fs.jsp?CurrNodeID="+CurrNodeID+"&Field="+field,"","width=550,height=300,scrollbars=yes");
	window.open("fs.jsp?CurrNodeID="+CurrNodeID,"","width=550,height=300,scrollbars=yes");
}

//上传附件
function callFile() {
  var form = document.form;
	var YWID = form.YWID.value;
	var WorkflowID = form.WorkflowID.value;
	window.open("../SQ/uploadFile.jsp?YWID="+YWID+"&WorkflowID="+WorkflowID+"&Address=ZDHKYJ","","width=600,height=450,scrollbars=yes");
}
</script>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include flush="true" page="title.jsp"></jsp:include></td>
  </tr>
	<tr>
	  <td height="28">退出 <a href="javascript:callFS()">发送</a> 操作跟踪 <a href="javascript:callFile()" class="txt_blue">上传（删除）附件</a> 打印模板 上一个 下一个</td>
	</tr>
	<tr>
	  <td><jsp:include flush="true" page="jxpTemp.jsp"></jsp:include></td>
  </tr>
</table>
</body>
</html>